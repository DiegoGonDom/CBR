import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Datos {
	
	private int n = 40;   
	private Paciente[] lista = new Paciente[n];
	public static final String SEPARADOR = ";";
	
	public Datos() {
		String datos = "Datos.csv";
		String datosPresiones = "Presiones.csv";
		String datosSf = "SF36.csv"; //Hay un error en el archivo: hay un espacio que se debe eliminar
		String datosEvas = "EVAs.csv";
        String linea = "";

        try (BufferedReader br = new BufferedReader(new FileReader(datos))) {
        	int i = 0;
        	while ((linea = br.readLine()) != null && i < 42) {
        		if (i >= 1 && i != 34) {
	        		String[] paciente = linea.split(SEPARADOR);
	        		String nombre = getNombre(paciente);
	                int sexo = getSexo(paciente);
	                double edad = getEdad(paciente);
	                double[] evaq = getEvaq(paciente);
	        	    double[] sf = getSf(paciente);
	        	    double[] oswestry = getOs(paciente);
	        	    double[] talla = getTalla(paciente);
	        	    double[] peso = getPeso(paciente);
	        	    double[] imc = getImc(paciente);
	        	    double[] ep = getEp(paciente);
	        	    double[] flex = getFlex(paciente);
	                if (i < 34)
	        	    	this.lista[i-1] = new Paciente(nombre,sexo,edad,evaq,sf,oswestry,talla,peso,imc,ep,flex);
	        	    else 
	        	    	this.lista[i-2] = new Paciente(nombre,sexo,edad,evaq,sf,oswestry,talla,peso,imc,ep,flex);
	        	}
        		i++;
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(datosPresiones))) {
        	int i = 0;
        	while ((linea = br.readLine()) != null && i < 42) {
        		if (i >= 1 && i != 34) {
	        		String[] paciente = linea.split(SEPARADOR);
	        		String nombre = getNombre(paciente);
	        		for(int j = 0; j < 40; j++) 
	        			if (this.lista[j].getNombre().equals(nombre))
	        				this.lista[j].setPresiones(getPresiones(paciente));
	        	}
        		i++;
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
		
        try (BufferedReader br = new BufferedReader(new FileReader(datosSf))) {
	    	int i = 0;
	    	while ((linea = br.readLine()) != null && i < 42) {
	    		if (i >= 1 && i != 34) {
	        		String[] paciente = linea.split(SEPARADOR);
	        		String nombre = getNombre(paciente);
	        		for(int j = 0; j < 40; j++) 
	        			if (this.lista[j].getNombre().equals(nombre))
	        				this.lista[j].setSf(getSF(paciente));
	        	}
	    		i++;
	    	}
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
        
        try (BufferedReader br = new BufferedReader(new FileReader(datosEvas))) {
	    	int i = 0;
	    	while ((linea = br.readLine()) != null && i < 41) {
	    		if (i >= 1) {
	        		String[] paciente = linea.split(SEPARADOR);
	        		String nombre = getNombre(paciente);
	        		for(int j = 0; j < 40; j++) 
	        			if (this.lista[j].getNombre().equals(nombre))
	        				this.lista[j].setEva(getEVA(paciente,j));
	        	}
	    		i++;
	    	}
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private String getNombre(String[] paciente) {
		String nombre = "";
		if (!paciente[0].isEmpty())
    		 nombre = paciente[0];
    	return nombre;
	}
	
	private int getSexo(String[] paciente) {
		int sexo = -100;
        if (!paciente[1].isEmpty()) 
			sexo = Integer.parseInt(paciente[1]);
        return sexo;
	}
	
	private double getEdad(String[] paciente) {
		double edad = -100;
		if (!paciente[2].isEmpty())
	    	edad = Integer.parseInt(paciente[2]);
		return edad;
	}
	
	private double[] getEvaq(String[] paciente) {
		double[] evaq = new double[4+1];
		evaq[4] = 0;
		for (int i = 0; i < 4; i++) {
			if (!paciente[3+i].isEmpty())
	    		evaq[i] = Double.parseDouble(paciente[3+i]);
	    	else {
	    		evaq[i] = -100;
	    		evaq[4]++;
	    	}
		}
		return evaq;
	}
	
	private double[] getSf(String[] paciente) {
		double[] sf = new double[4+1];
		sf[4] = 0;
		for (int i = 0; i < 4; i++) {
			if (!paciente[7+i].isEmpty())
	    		sf[i] = Double.parseDouble(paciente[7+i]);
	    	else {
	    		sf[i] = -100;
	    		sf[4]++;
	    	}
		}
		return sf;
	}
	
	private double[] getOs(String[] paciente) {
		double[] oswestry = new double[4+1];
		oswestry[4] = 0;
		for (int i = 0; i < 4; i++) {
			if (!paciente[11+i].isEmpty())
	    		oswestry[i] = Double.parseDouble(paciente[11+i]);
	    	else {
	    		oswestry[i] = -100;
	    		oswestry[4]++;
	    	}
		}
		return oswestry;
	}
	
	private double[] getTalla(String[] paciente) {
		double[] talla = new double[4+1];
		talla[4] = 0;
		for (int i = 0; i < 4; i++) {
			if (!paciente[15+i].isEmpty())
	    		talla[i] = Double.parseDouble(paciente[15+i]);
	    	else {
	    		talla[i] = -100;
	    		talla[4]++;
	    	}
		}
		return talla;
	}
	
	private double[] getPeso(String[] paciente) {
		double[] peso = new double[4+1];
		peso[4] = 0;
		for (int i = 0; i < 4; i++) {
			if (!paciente[19+i].isEmpty())
	    		peso[i] = Double.parseDouble(paciente[19+i]);
	    	else {
	    		peso[i] = -100;
	    		peso[4]++;
	    	}
		}
		return peso;
	}
	
	private double[] getImc(String[] paciente) {
		double[] imc = new double[4+1];
		imc[4] = 0;
		for (int i = 0; i < 4; i++) {
			if (!paciente[23+i].isEmpty())
	    		imc[i] = Double.parseDouble(paciente[23+i]);
	    	else {
	    		imc[i] = -100;
	    		imc[4] ++;
	    	}
		}
		return imc;
	}
	
	private double[] getEp(String[] paciente) {
		double[] ep = new double[3+1];
		ep[3] = 0;
		for (int i = 0; i < 3; i++) {
			if (!paciente[27+i].isEmpty())
	    		ep[i] = Double.parseDouble(paciente[27+i]);
	    	else {
	    		ep[i] = -100;
	    		ep[3]++;
	    	}
		}
		return ep;
	}
	
	private double[] getFlex(String[] paciente) {
		double[] flex = new double[60+1];
		flex[60] = 0;
		for (int i = 0; i < paciente.length-30; i++) {
			if (!paciente[30+i].isEmpty())
	    		flex[i] = Double.parseDouble(paciente[30+i]);
	    	else {
	    		flex[i] = -100;
	    		flex[60]++;
	    	}
	    }
		for (int i = paciente.length-30; i < 60; i++) {
			flex[i] = -100;
			flex[60]++;
		}			
		return flex;
	}
	
	private double[] getPresiones(String[] paciente) {
		double[] presiones = new double[25+1];
		presiones[25] = 0;
		for (int i = 0; i < paciente.length-1; i++) {
			if (!paciente[1+i].isEmpty())
	    		presiones[i] = Double.parseDouble(paciente[1+i]);
	    	else {
	    		presiones[i] = -100;
	    		presiones[25]++;
	    	}
	    }
		for (int i = paciente.length-1; i < 25; i++) {
			presiones[i] = -100;
			presiones[25]++;
		}
		return presiones;
	}
	
	private double[] getSF(String[] paciente) {
		double[] sf = new double[36+1];
		sf[36] = 0;
		for (int i = 0; i < paciente.length-1; i++) {
			if (!paciente[1+i].isEmpty()) 
				sf[i] = Double.parseDouble(paciente[1+i]);
			else {
	    		sf[i] = -100;
	    		sf[36]++;
			}
	    }
		for (int i = paciente.length-1; i < 36; i++) {
			sf[i] = -100;
			sf[36]++;
		}
		return sf;
	}
	
	private double[] getEVA(String[] paciente, int i) {
		double[] eva = new double[120+1];
		eva[120] = 0;
		for (int j = 0; j < paciente.length-2; j++) {
			if (!paciente[1+j].equals("-"))
				if (i == 4 && j >= 12) 
					eva[j-1] = Double.parseDouble(paciente[1+j]);
				else 
					eva[j] = Double.parseDouble(paciente[1+j]);
		    else {
		    	eva[j] = -100;
		    	eva[120]++;
		    }
	    }
		if (i == 4)
			eva[120]--;
		return eva;
	}
	
	public void printLista() {
		for(int i = 0; i < this.n; i++)
			this.lista[i].printP();
	}
	
	public void printLista(int n) {
		if (n >= 1 && n <= 40)
			this.lista[n-1].printP();
		else
			System.out.println("Numero erroneo");
	}
	
	public void printErrores() {
		for(int i = 0; i < this.n; i++)
			this.lista[i].printPE();
	}
	
	public void printErrores(int n) {
		if (n >= 1 && n <= 40)
			this.lista[n-1].printPE();
		else
			System.out.println("Numero erroneo");
	}
	
	public Paciente[] getLista() {
		return this.lista;
	}
	
	public Paciente getPaciente(int n) {
		if (n >= 1 && n <= 40)
			return this.lista[n-1];
		else
			System.out.println("Numero erroneo");
		return null;
	}
	
	public void setPaciente(Paciente p, int n) {
		if (n >= 1 && n <= 40)
			this.lista[n-1] = p;
		else
			System.out.println("Numero erroneo");
	}
	
	public void completarDatos() {
		for(int i = 0; i < this.n; i++)
			this.lista[i].completarPaciente();
	}
	
	public void completarDatosMedia() {
		for(int i = 0; i < this.n; i++)
			this.lista[i].completarPacienteMedia();		
	}
	
}
