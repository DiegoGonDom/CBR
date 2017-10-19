import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class AlGen {
	
	public AlGen() {	
	}
	
	public static double[] normalizaCoefs(double[] coefs) {
		double total = 0;
		double[] resultado = new double [coefs.length];
		for (int i = 0; i < coefs.length; i++) 
			total += coefs[i];
		for (int i = 0; i < coefs.length; i++) 
			resultado[i] = coefs[i] / total; // La suma de los coeficientes debe ser 1
		
		return resultado;
	}
	
	public static double[] generaCoefs(int n) {
		double[] coefs = new double[n];
		for (int i = 0; i < coefs.length; i++) 
			coefs[i] = Math.random(); // Coeficientes aleatorios

		return normalizaCoefs(coefs);
	}
	
	public static double evaluaCoefs(double[] evaluacion) {
		double resultado;
		resultado = 0;
		for(int k = 0; k < 32; k++)
			if (k != 26 && k != 8 && k != 6)
			resultado += evaluacion[k];
		resultado /= 29;

		return resultado;
	}
	
	public static double funcCoefs(double[] coefs, int tipo, int modelo) {
		/*
		 * double p = 0;
		for (int i = 0; i < coefs.length; i++)
			if (coefs[i] > p)
				p = coefs[i];
		return p;
		*/		
		
		return evaluaCoefs(Evaluacion.evaluacionMetodos(coefs,tipo,modelo));		
	}
	
	public static double[][] seleccionaCoefs(double[][] coefs, double[] evaluacion) {
		int F = 4; // Factor de selección
		int N = coefs.length;
		int M = coefs[0].length;
		double[][] resultadoS = new double[N][M];
		double[] ordenados = new double[N];
		
		double total = 0;
		for (int i = 0; i < N; i++) {
			total += Math.pow(evaluacion[i], F);
		}
		ordenados[0] =  Math.pow(evaluacion[0], F) / total;
		for (int i = 1; i < N; i++) 
			ordenados[i] = ordenados[i-1] + Math.pow(evaluacion[i], F) / total;	
		
		for (int i = 0; i < N; i++) {
			double ran = Math.random();
			int j = 0;
			boolean k = true;
			do  {
				if (ran <= ordenados[j]) {
					resultadoS[i] = coefs[j];
					k = false;
				}
				j++;
			} while (j < N && k);
		}
 
		return resultadoS;
	}
	
	public static double[][] cruzaCoefs(double cruce, double[][] coefs) {
		int n = coefs.length / 2;
		int N = coefs.length;
		int M = coefs[0].length;
		double[][] resultadoC = new double[N][M];
		for (int i = 0; i < n; i++) {
			resultadoC[2*i] = coefs[2*i];
			resultadoC[2*i+1] = coefs[2*i+1];
			double ran = Math.random();
			if (ran < cruce) {
				int r = (int)(Math.random()*M);
				for (int j = r; j < M; j++) {
					double aux = resultadoC[2*i][j];
					resultadoC[2*i][j] = resultadoC[2*i+1][j];
					resultadoC[2*i+1][j] = aux;
				}				
			}
			resultadoC[2*i] = normalizaCoefs(resultadoC[2*i]);
			resultadoC[2*i+1] = normalizaCoefs(resultadoC[2*i+1]);
		}
		
		return resultadoC;
	}
	
	public static double[][] mutaCoefs(double mutacion, double[][] coefs) {
		int N = coefs.length;
		int M = coefs[0].length;
		double[][] resultadoM = new double[N][M];
		
		for (int i = 0; i < N; i++) {
			resultadoM[i] = coefs[i];
			double ran = Math.random();			
			if (ran < mutacion) {
				int r = (int)(Math.random() * M);				
				resultadoM[i][r] += 0.05;
				if (resultadoM[i][r] > 1)
					resultadoM[i][r] = 1;
				r = (int)(Math.random() * M);
				resultadoM[i][r] -= 0.05;
				if (resultadoM[i][r] < 0)
					resultadoM[i][r] = 0;				
			}
			resultadoM[i] = normalizaCoefs(resultadoM[i]);
		}
		
		return resultadoM;
	}
		
	
	public static void lanzaAlgoritmo(int tipo, int modelo) {
		// Tipo: 1=Presiones, 2=SF36, 3=EVAs
		// Modelo: 1=CBRs1, 2=CBRs2, 3=CBRs3, 4=CBRs5, ... , 12=CBRcs5
		
		int M = 11; // Nº de coeficientes
		int N = 20; // Tamaño de la población
		int G = 3000; // Numero de generaciones
		double cruce = 0.1; // Probabilidad de cruce
		double mutacion = 0.1; // Probabilidad de mutación
		double[][] coefs = new double[N][M]; // Población
		double[][] coefsH = new double[N][M]; // Población nueva
		for(int i = 0; i < N; i++)  // Inicialización de la población
    		coefs[i] = generaCoefs(M);
		//for(int i = 0; i < n; i++)  // Coef perfectos
    	//	coefs[0][i] = 0;
		//coefs[0][0] = 1;
		double[] evaluacion = new double[N]; // Vector que guarda la evaluación de los coeficientes.
		double eval, evalH; // Evaluación promedio de todos los coeficientes de la generación actual
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("AlGen.xls", "Cp1252");
			writer.println("Generacion\tEvaluacion");		
		
			int i = 1;
			do { // Diferentes generaciones
				eval = 0;
				System.out.print("Generación " + i + ": ");				
				for(int j = 0; j < N; j++) {  // Evaluación 
					evaluacion[j] = funcCoefs(coefs[j],tipo,modelo);
					//System.out.print((j+1) + ") ");
					eval += evaluacion[j];
					//showCoefs(coefs[j], tipo, modelo);
				}
				eval /= N;
				writer.println(String.format("%d\t%.5f", i, eval));
				//System.out.println("---------------------------------------------------------------------------------------------------------------------");
				coefsH = seleccionaCoefs(coefs, evaluacion); // Selección
				coefsH = cruzaCoefs(cruce, coefsH); // Cruce
				coefsH = mutaCoefs(mutacion, coefsH); // Mutación
				evalH = 0;
				for(int j = 0; j < N; j++) { // Evaluación 
					evaluacion[j] = funcCoefs(coefsH[j],tipo,modelo);
					//System.out.print((j+1) + ") ");
					evalH += evaluacion[j];
					//showCoefs(coefsH[j], tipo, modelo);
				}
				evalH /= N;
				System.out.println(String.format("Eval = %.5f, EvalH = %.5f", eval, evalH));
				if (evalH > eval) {
					coefs = coefsH;
				}
				//System.out.println(" ");
				i++;
			} while (i <= G && eval < 0.99);
			
			if (i > G)
				System.out.println("\nFIN: NO HA CONVERGIDO :C");
			else 
				System.out.println("\nFIN : ¡¡ HAY SOLUCION OPTIMA !!");
			showCoefs(coefs[0], tipo, modelo);
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void showCoefs(double[] coefs, int tipo, int modelo) {
		double total = 0;
		for(int i = 0; i < coefs.length; i++) {
			System.out.print(String.format("%.5f ", coefs[i]));
			total += coefs[i];
		}
		System.out.print(String.format(" =>  %.5f ", funcCoefs(coefs,tipo,modelo)));
		if (total <= 1.01 && total >= 0.99)
			System.out.println("(Normalizado)");
		else
			System.out.println(String.format("(No válido: %.3f)",total));
	}
	
}
