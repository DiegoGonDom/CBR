public class Main {
	
	public static void main(String[] args) {
		
		/*
		Datos d = new Datos();
		int P = 28; 
		d.completarDatos();
		*/
		
		//d.getPaciente(P).printP();
		//Regresion.show(d.getPaciente(P).getEVA());
		
		/*
		Regresion.show(paciente.getPresiones());
		double[] coefs = {0.05,0.05,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1};
		Regresion.show(CBR.calculoDatosCBR(d, P, 1, 3, 2, coefs));
		*/
		
		/*
		Datos d = new Datos();
		for (int P = 40; P <= 32; P++) {
			System.out.println(P + ": ");
			double[] p = d.getPaciente(P).getPresiones();
			Regresion.show(p);
			Regresion.show(Regresion.calculoDatosRegresion(p, 2, 5, 5, false));
		} for (int P = 40; P <= 32; P++) {
			System.out.println(P + ": ");
			double[] p = d.getPaciente(P).getEVA();
			Regresion.show(p);
			Regresion.show(Regresion.calculoDatosRegresion(p, 2, 4, 30, true));
		} for (int P = 6; P <= 6; P++) {
			System.out.println(P + ": ");
			double[] p = d.getPaciente(P).getSF();
			Regresion.show(p);
			Regresion.show(Regresion.calculoDatosRegresion(p, 2, 9, 4, false));
		}
		*/
		
		//System.out.println(String.format("%.5f",Math.random()));
		
		//AlGen.lanzaAlgoritmo(1,8);
		
		
		double[] coefs = {0.05,0.05,0.05,0.05,0.1,0.05,0.05,0.1,0.1,0.1,0.3};
		Evaluacion.evaluacionMetodos(coefs);
		
		
		
		
		
		
						
	}
}
