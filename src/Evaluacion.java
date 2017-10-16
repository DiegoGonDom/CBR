import java.io.IOException;
import java.io.PrintWriter;

public class Evaluacion {

	public Evaluacion() {		
	}
	
	public static void evaluacionMetodos() {
		double evaluacion[][][] = new double[40][3][15];
		Datos datos = new Datos();
		datos.completarDatos(); // Los datos que faltan se rellenan usando regresión lineal
		Datos datos2 = new Datos();
		datos2.completarDatosMedia(); // Los rellenan todos los datos con la media de los datos disponibles
		double[] coefs = {0.05,0.05,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1};
		
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
				
				if (datos_analisis.length > 1) {	
					int N = 100 + (int)(Math.random()*101); // Numero de repeticiones para cada paciente 100 <= N <= 200
					double[] datos_erroneos;
					for (int l = 0; l < N; l++) {
						datos_erroneos = añadirHuecos(datos_analisis, vars);
						for (int m = 1; m <= 15; m++) { // Tipo de regresion (linear, exponencial, polinomica, CBRs1, CBRs2, CBRs3, CBRs5, CBRc1, CBRc2, CBRc3, CBRc5, CBRcs1, CBRcs2, CBRcs3, CBRcs5)
							double[] datos_resueltos;
							// System.out.println(i + " " + j + " " + l + " " + m);							
							if (m >= 1 && m <= 3) 
								datos_resueltos = Regresion.calculoDatosRegresion(datos_erroneos, m, vars, reps, false);
							else if (m >= 4 && m <= 15) {
								int v = (m - 3) % 4;
								if (v == 0)
									v = 5;
								int cbr = m / 4;
								if (m <= 11)
									datos_resueltos = CBR.calculoDatosCBR(datos, datos_erroneos, i, j, v, cbr, coefs);
								else
									datos_resueltos = CBR.calculoDatosCBR(datos2, datos_erroneos, i, j, v, cbr, coefs);
							}							
							else
								datos_resueltos = new double[datos_analisis.length];
							evaluacion[i-1][j-1][m-1] += CBR.similitud(datos_resueltos, datos_analisis);
						}
					}
					for (int m = 1; m <= 15; m++)
						evaluacion[i-1][j-1][m-1] /= N;
				}
				else 
					for (int m = 1; m <= 15; m++)
						evaluacion[i-1][j-1][m-1] = 0;
			}
		}
		
		showResults(evaluacion);	
		exportResults(evaluacion, datos);
	}
	
	private static double[] eliminarHuecos(double[] datos) {
		double[] nuevos_datos = new double[datos.length - (int)datos[datos.length-1]];
		int i = 0;
		for (int j = 0; j < datos.length-1; j++)
			if (datos[j] != -100) {
				nuevos_datos[i] = datos[j];
				i++;
			}
		nuevos_datos[nuevos_datos.length-1] = 0;
		return nuevos_datos;
	}
	
	private static double[] añadirHuecos(double[] datos, int vars) {
		double[] nuevos_datos = new double[datos.length];
		int reps = (nuevos_datos.length - 1) / vars;
		if (datos.length > 0) {
			int M = 1 + (int)(Math.random()*(reps/2+1));  // Numero de huecos generados: 1 <= M <= reps/2 => En total M*vars huecos
			for (int j = 0; j < M; j++) {
				int i = (int)(Math.random()*reps);
				if (nuevos_datos[i*vars] != -100) {
					for (int l = 0; l < vars; l++)
						nuevos_datos[i*vars + l] = -100;
					nuevos_datos[datos.length-1] += vars;
				} 
			}
			for (int i = 0; i < nuevos_datos.length-1; i++)
				if (nuevos_datos[i] != -100)
					nuevos_datos[i] = datos[i];
		}
		return nuevos_datos;
	}
	
	private static void showResults(double[][][] evaluacion) {
		for (int j = 1; j <= 3; j++) {
			System.out.println("(" + j + ")\t  Lin\t  Exp\t Poli\t CBRs1\t CBRs2\t CBRs3\t CBRs5\t CBRc1\t CBRc2\t CBRc3\t CBRc5\tCBRcs1\tCBRcs2\tCBRcs3\tCBRcs5");
			for (int i = 1; i <= 40; i++) {
				System.out.print(String.format("%02d",i)+":\t");
				for (int k = 0; k < 15; k++) {
					if (!Double.isNaN(evaluacion[i-1][j-1][k]))
						System.out.print(String.format("%.5f", evaluacion[i-1][j-1][k])+"\t");
					else
						System.out.print(String.format("%7s", evaluacion[i-1][j-1][k])+"\t");
				}
				System.out.println("");		
			}
			System.out.println("");
		}
	}
	
	private static void exportResults(double[][][] evaluacion, Datos datos) {
		try{
		    PrintWriter writer = new PrintWriter("resultados.xls", "UTF-8");
		    for (int j = 1; j <= 3; j++) {
		    	writer.println("- " + j + " -\tNombre\tLin\tExp\tPoli\tCBRs1\tCBRs2\tCBRs3\tCBRs5\tCBRc1\tCBRc2\tCBRc3\tCBRc5");
				for (int i = 1; i <= 40; i++) {
					writer.print(String.format("%02d",i)+"\t"+datos.getPaciente(i).getNombre()+"\t");
					for (int k = 0; k < 15; k++) {
						if (!Double.isNaN(evaluacion[i-1][j-1][k]))
							writer.print(String.format("%.5f", evaluacion[i-1][j-1][k])+"\t");
						else
							writer.print(String.format("%7s", evaluacion[i-1][j-1][k])+"\t");
					}
					writer.println("");		
				}
				writer.println("");
			}
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
		
	}
}
