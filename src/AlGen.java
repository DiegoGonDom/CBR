
public class AlGen {
	
	public AlGen() {	
	}
	
	public static double[] generaCoefs() {
		double[] coefs = new double[11];
		double total = 0;
		for (int i = 0; i < coefs.length; i++) {
			coefs[i] = Math.random(); // Coeficientes aleatorios
			total += coefs[i];
		}
		for (int i = 0; i < coefs.length; i++) 
			coefs[i] /= total; // La suma de los coeficientes debe ser 1
				
		return coefs;
	}
	
	public static double evaluaCoefs(int tipo, int modelo, double[][][] evaluacion) {
		double resultado;
		resultado = 0;
		for(int k = 0; k < 40; k++)
			resultado += evaluacion[k][tipo][modelo+2];
		resultado /= 40;

		return resultado;
	}
	
	public static double[][] seleccionaCoefs(double[][] coefs, double[] evaluacion) {
		int N = coefs.length;
		int M = coefs[0].length;
		double[][] resultado = new double[N][M];
		double[] ordenados = new double[N];
		
		double total = 0;
		for (int i = 0; i < N; i++) {
			total += evaluacion[i];
		}
		ordenados[0] = evaluacion[0] / total;
		for (int i = 1; i < N; i++) 
			ordenados[i] = ordenados[i-1] + evaluacion[i] / total;	
		
		for (int i = 0; i < N; i++) {
			double ran = Math.random();
			int j = 0;
			boolean k = true;
			do  {
				if (ran <= ordenados[j]) {
					resultado[i] = coefs[j];
					k = false;
				}
				j++;
			} while (j < N && k);
		}
 
		return resultado;
	}
	
	public static double[][] cruzaCoefs(double cruce, double[][] coefs) {
		int N = coefs.length;
		int M = coefs[0].length;
		double[][] resultado = new double[N][M];
		int n = N / 2;
		for (int i = 0; i < n; i++) {
			double ran = Math.random();
			if (ran < cruce) {
				int r = (int)(Math.random()*11);
				for (int j = r; j < N; j++) {
					double aux = coefs[i][r];
					coefs[i][r] = coefs[i+1][r];
					coefs[i+1][r] = aux;
				}
			}
		}
		
		return resultado;
	}
	
	public static double[][] mutaCoefs(double mutacion, double[][] coefs) {
		int N = coefs.length;
		int M = coefs[0].length;
		double[][] resultado = new double[N][M];
		
		
		return resultado;
	}
		
	
	public static void lanzaAlgoritmo(int tipo, int modelo) {
		// Tipo: 1=Presiones, 2=SF36, 3=EVAs
		// Modelo: 1=CBRs1, 2=CBRs2, 3=CBRs3, 4=CBRs5, ... , 12=CBRcs5
		
		int N = 10; // Tamaño de la población
		int G = 100; // Numero de generaciones
		double[][] coefs = new double[N][11]; // Población
		for(int i = 0; i < N; i++)  // Inicialización de la población
    		coefs[i] = generaCoefs();
		double[] evaluacion = new double[N]; // Vector que guarda la evaluación de los coeficientes.
		double eval = 0; // Evaluación promedio de todos los coeficientes de la generación actual
		double evalAnterior = 0; // Evaluación promedio de todos los coeficientes de la generación anterior
		double dif = 0; // Diefrencia entre generaciones de las evaluaciones promedio
		double min = 0.000000; // Minima diferencia entre generaciones
    	
		int i = 1;
		do { // Diferentes generaciones
			eval = 0;
			System.out.println("Generación " + i);
			/*
			for(int j = 0; j < N; j++) {  // Evaluación 
				evaluacion[j] = evaluaCoefs(tipo, modelo, Evaluacion.evaluacionMetodos(coefs[j]));
				eval += evaluacion[j];
			}*/
			System.out.print("Eval: ");
			for(int j = 0; j < N; j++) {  // Evaluación 
				evaluacion[j] = prueba(coefs[j]);
				eval += evaluacion[j];
				System.out.print(evaluacion[j] + " ");
			}
			System.out.println(" ");
			eval /= N;
			dif = Math.abs(eval - evalAnterior);
			evalAnterior = eval;
			
			coefs = seleccionaCoefs(coefs, evaluacion); // Selección
			
			double cruce = 0.25; // Probabilidad de cruce
			coefs = cruzaCoefs(cruce, coefs); // Cruce
			
			double mutacion = 0.1; // Probabilidad de mutación
			coefs = mutaCoefs(mutacion, coefs); // Mutación
			
			i++;
		} while (i <= G && dif >= min);
		
		System.out.println(" ");
		System.out.println("FIN!!!");
		
	}
	
	public static double prueba(double[] coefs) {
		double p = 0;
		for (int i = 0; i < coefs.length; i++) {
			p +=  Math.pow(coefs[i], 2);
		}
		
		return p;
	}
	
}
