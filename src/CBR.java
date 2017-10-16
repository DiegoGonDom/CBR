
public class CBR {
	
	public CBR() {
	}
	
	public static double similitud(double[] datos_paciente, double[] datos_vecino) {
		int n = datos_paciente.length-1;
		int m = 0;
		double similitud = 0;
		for (int i = 0; i < n; i++) {
			if (datos_paciente[i] != -100 && datos_vecino[i] != -100) {
				m++;
				if (datos_paciente[i] == 0 && datos_vecino[i] == 0)
					similitud += 1;
				else 
					similitud += 1 - Math.abs(datos_paciente[i]-datos_vecino[i]) / Math.max(Math.abs(datos_paciente[i]),Math.abs(datos_vecino[i]));
			}
		}
		if (m == 0)
			return 0;
		similitud /= m;
		
		return similitud;
	}
	
	public static double[] calculoVecinosSimple(Datos datos, double[] datos_paciente, int paciente, int tipo) {
		double[] datos_vecino;
		double[] similitud = new double[datos.getLista().length];
		
		for (int i = 1; i <= similitud.length; i++) {
			if (i == paciente)
				similitud[i-1] = 0;
			else{
				if (tipo == 1)
					datos_vecino = datos.getPaciente(i).getPresiones();
				else if (tipo == 2)
					datos_vecino = datos.getPaciente(i).getSF();
				else
					datos_vecino = datos.getPaciente(i).getEVA();
				similitud[i-1] = similitud(datos_paciente, datos_vecino);
			}
		}
		return similitud;		
	}
	
	public static double[] calculoVecinosCompleto(Datos datos, double[] datos_paciente, int paciente, int tipo, double[] coefs) {
		double[] similitud_simple = calculoVecinosSimple(datos, datos_paciente, paciente, tipo);
		int n = datos.getLista().length;
		double[] similitud_sexo = new double[n];
		double[] similitud_edad = new double[n];
		double[] similitud_evaq = new double[n];
		double[] similitud_sf = new double[n];
		double[] similitud_oswestry = new double[n];
		double[] similitud_talla = new double[n];
		double[] similitud_peso = new double[n];
		double[] similitud_imc = new double[n];
		double[] similitud_ep = new double[n];
		double[] similitud_flex = new double[n];
		double[] similitud = new double[n];
		
		Paciente p = datos.getPaciente(paciente);
		for (int i = 1; i <= similitud.length; i++) {
			if (i == paciente)
				similitud[i-1] = 0;
			else {
				Paciente v = datos.getPaciente(i);
				// SEXO
				if (v.getSexo() == p.getSexo())
					similitud_sexo[i-1] = 1;
				else
					similitud_sexo[i-1] = 0;
				// EDAD
				if (v.getEdad() == -100 || p.getEdad() == -100)
					similitud_edad[i-1] = 0;
				else
					similitud_edad[i-1] = 1 - Math.abs(p.getEdad()-v.getEdad()) / Math.max(Math.abs(p.getEdad()),Math.abs(v.getEdad()));
				// EVAQ
				similitud_evaq[i-1] = similitud(p.getEvaq(), v.getEvaq());
				// SF
				similitud_sf[i-1] = similitud(p.getSf(), v.getSf());
				// OSWESTRY
				similitud_oswestry[i-1] = similitud(p.getOswestry(), v.getOswestry());
				// TALLA
				similitud_talla[i-1] = similitud(p.getTalla(), v.getTalla());
				// PESO
				similitud_peso[i-1] = similitud(p.getPeso(), v.getPeso());
				// IMC
				similitud_imc[i-1] = similitud(p.getImc(), v.getImc());
				// EP
				similitud_ep[i-1] = similitud(p.getEp(), v.getEp());
				// FLEX
				similitud_flex[i-1] = similitud(p.getFlex(), v.getFlex());
				
				similitud[i-1] = similitud_sexo[i-1] * coefs[0] +
							similitud_edad[i-1] * coefs[1] +
							similitud_evaq[i-1] * coefs[2] +
							similitud_sf[i-1] * coefs[3] +
							similitud_oswestry[i-1] * coefs[4] +
							similitud_talla[i-1] * coefs[5] +
							similitud_peso[i-1] * coefs[6] +
							similitud_imc[i-1] * coefs[7] +
							similitud_ep[i-1] * coefs[8] +
							similitud_flex[i-1] * coefs[9] +
							similitud_simple[i-1] * coefs[10];
			}
		}
				
		return similitud;		
	}
	
	public static int[] actualizarMaximo(int[] m, double[] dato, int in) {
		int[] max = m;
		
		if (max[max.length-1] < max.length-1) {
			max[max.length-2] = in+1;
			for(int i = max.length-2; i > 0; i--)
				if (max[i-1] == 0 || dato[max[i]-1] < dato[max[i-1]-1]) {
					int aux = max[i];
					max[i] = max[i-1];
					max[i-1] = aux;
				}
			max[max.length-1]++;
		} else {
			if (dato[in] > dato[max[0]-1]) {
				max[0] = in+1;
				for(int i = 0; i < max.length-2; i++)
					if (dato[max[i]-1] > dato[max[i+1]-1]) {
						int aux = max[i];
						max[i] = max[i+1];
						max[i+1] = aux;
					}
			}
		}
		
		return max;
	}
	
	public static double[] calculoDatosCBR(Datos datos, double[] datos_paciente, int paciente, int tipo, int vecinos, int cbr, double[] coefs) {
		// datos: lista con los pacientes
		// paciente: numero del paciente a estudiar
		// tipo: 1 para presiones, 2 para SF36, 3 para EVAs
		// vecinos: numero de vecinos con mayor similitud
		// cbr: 1 para CBR simple, 2 para CBR completo
		// coefs: pesos relativos de cada elemento de la similitud
		
		double[] similitud;
		
		if (cbr == 1) //CBR simple
			similitud = calculoVecinosSimple(datos, datos_paciente, paciente, tipo);
		else
			similitud = calculoVecinosCompleto(datos, datos_paciente, paciente, tipo, coefs);
		int[] max = new int[vecinos+1];
		for (int i = 0; i < similitud.length; i++) 
			max = actualizarMaximo(max, similitud, i);
		
		double[][] datos_vecinos;
		if (tipo == 1) {
			datos_vecinos = new double[max.length-1][datos_paciente.length];
			for (int i = 0; i < datos_vecinos.length; i++)
				datos_vecinos[i] = datos.getPaciente(max[i]).getPresiones();
		} else if (tipo == 2) {
			datos_vecinos = new double[max.length-1][datos_paciente.length];
			for (int i = 0; i < datos_vecinos.length; i++)
				datos_vecinos[i] = datos.getPaciente(max[i]).getSF();
		}else {
			datos_vecinos = new double[max.length-1][datos_paciente.length];
			for (int i = 0; i < datos_vecinos.length; i++)
				datos_vecinos[i] = datos.getPaciente(max[i]).getEVA();
		}
		
		double[] salida_paciente = new double[datos_paciente.length];
		salida_paciente[datos_paciente.length - 1] = 0;
		for (int i = 0; i < salida_paciente.length-1; i++) {
			if (datos_paciente[i] != -100) 
				salida_paciente[i] = datos_paciente[i];
			else {
				double x = 0, y = 0;
				for (int j = 0; j < datos_vecinos.length; j++) {
					if (datos_vecinos[j][i] != -100) {
						x += similitud[max[j]-1]*datos_vecinos[j][i];
						y += similitud[max[j]-1];
					}
				}
				if (y == 0) {
					salida_paciente[datos_paciente.length - 1]++;
					salida_paciente[i] = -100;
				} else 
					salida_paciente[i] = x / y;
			}
		}
		
		return salida_paciente;
	}
		
}
