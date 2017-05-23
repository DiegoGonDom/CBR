
public class Evaluacion {

	public Evaluacion() {		
	}
	
	public static double[][][] evaluacionMetodos(Datos datos) {
		double evaluacion[][][] = new double[40][3][5];
		datos.completarDatos();
		
		for (int i = 1; i <= 40; i++) { // Cada paciente (1-40)
			Paciente p = datos.getPaciente(i);			
			for (int j = 1; j <= 3; j++) { // Cada tipo de datos (Presiones, SF36, EVAs)
				double[] datos_partida;
				int vars, reps;
				if (j == 1) {
					datos_partida = p.getPresiones();
					vars = 5;
				}
				else if (j == 2) {
					datos_partida = p.getSF();
					vars = 9;
				}
				else {
					datos_partida = p.getEVA();
					vars = 4;
				}
				double[] datos_analisis = eliminarHuecos(datos_partida);
				reps = (datos_analisis.length - 1) / vars;
				
				if (i == 8 && j == 1) {
					Regresion.show(datos_partida);
					Regresion.show(datos_analisis);
				}	
				if (datos_analisis.length > 1) {	
					int N = 100 + (int)(Math.random()*101); // Numero de repeticiones para cada paciente 100 <= N <= 200
					double[] datos_erroneos;
					for (int l = 0; l < N; l++) {
						datos_erroneos = añadirHuecos(datos_analisis, reps);
						if (i == 8 && j == 1 && l == 1) 
							Regresion.show(datos_erroneos);
						for (int m = 1; m <= 5; m++) { // Tipo de regresion (linear, exponencial, polinomica, CBRc, CBRc)
							double[] datos_resueltos;
							System.out.println(i + " " + j + " " + l + " " + m);							
							if (m >= 1 && m <= 3)
								datos_resueltos = Regresion.calculoDatosRegresion(datos_erroneos, m, vars, reps, false);
							else if (m == 4)
								datos_resueltos = new double[datos_analisis.length];
							else
								datos_resueltos = new double[datos_analisis.length];
							if (i == 8 && j == 1 && l == 1) {
								//System.out.print(m + ") ");
								//Regresion.show(datos_resueltos);
							}
							evaluacion[i-1][j-1][m-1] += CBR.similitud(datos_resueltos, datos_analisis);
						}
					}
				}
				else
					for (int m = 1; m <= 5; m++)
						evaluacion[i-1][j-1][m-1] += 0;
			}
		}
		
		return evaluacion;		
	}
	
	private static double[] eliminarHuecos(double[] datos) {
		double[] nuevos_datos = new double[datos.length - (int)datos[datos.length-1]];
		int i = 0;
		for (int j = 0; j < datos.length-1; j++)
			if (datos[j] != -100) {
				nuevos_datos[i] = datos[j];
				i++;
			}
		return nuevos_datos;
	}
	
	private static double[] añadirHuecos(double[] datos, int vars) {
		double[] nuevos_datos = datos;
		int reps = (nuevos_datos.length - 1) / vars;
		if (datos.length > 0) {
			int M = 1 + (int)(Math.random()*(reps/2));  // Numero de huecos generados: 1 <= M <= reps/2 => En total M*vars huecos
			for (int j = 0; j < M; j++) {
				int i = (int)(Math.random()*reps);
				if (nuevos_datos[i*vars] != -100) {
					for (int l = 0; l < vars; l++)
						nuevos_datos[i*vars + l] = -100;
					nuevos_datos[datos.length-1] += vars;
				}
			}
		}
		return nuevos_datos;
	}
}
