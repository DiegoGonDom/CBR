
public class Regresion {
	
	public Regresion() {
	}

	public static double[] regresion(double[] x_origen, double[] y_origen, double[] x_objetivo, int n) {
		// n = 1: Metodo de regresion lineal
		// n = 2: Metodo de regresion exponencial
		// n = 3: Metodo de regresion polinomica
		// n = 4: Metodo de regresion logaritmica
		
		double[] y_objetivo = new double[x_objetivo.length];
		switch(n) {
			case 1: 
				y_objetivo = regresionLineal(x_origen, y_origen, x_objetivo);
				break;
			case 2: 
				y_objetivo = regresionExponencial(x_origen, y_origen, x_objetivo);
				break;
			case 3: 
				y_objetivo = regresionPolinomica(x_origen, y_origen, x_objetivo);
				break;
			case 4: 
				y_objetivo = regresionLogaritmica(x_origen, y_origen, x_objetivo);
				break;
			default:
				y_objetivo = regresionLineal(x_origen, y_origen, x_objetivo);
				break;	
		}
			
		return y_objetivo;
	}
	
	public static double[] regresionLineal(double[] x_origen, double[] y_origen, double[] x_objetivo) {
		int l_origen = x_origen.length;
		int l_objetivo = x_objetivo.length;
		double[] y_objetivo = new double[l_objetivo]; //+ 1]; // El último es el coef. de regresión
		//Y = A·X + B
		double x_suma = 0, y_suma = 0, x_media = 0, y_media = 0, v_x = 0, v_y = 0, v_xy = 0, A = 0, B = 0; 
		
		for (int i = 0; i < l_origen; i++) {
			x_suma += x_origen[i];
			y_suma += y_origen[i];
			v_x += Math.pow(x_origen[i],2);
			v_y += Math.pow(y_origen[i],2);
			v_xy += x_origen[i] * y_origen[i];
		}
		x_media = x_suma / l_origen;
		y_media = y_suma / l_origen;
		v_xy = (v_xy / l_origen) - (x_media * y_media);
		v_x = Math.sqrt(v_x / l_origen - Math.pow(x_media, 2));
		v_y = Math.sqrt(v_y / l_origen - Math.pow(y_media, 2));
				
		for (int i = 0; i < l_origen; i++) {
			A += (x_origen[i]-x_media)*(y_origen[i]-y_media);
			B += Math.pow((x_origen[i]-x_media),2);
		}
		A = A / B;
		B = y_media - A * x_media;
		
		for(int i = 0; i < l_objetivo; i++) {
			if (l_origen > 1)
				y_objetivo[i] = A * x_objetivo[i] + B;
			else if (l_origen == 1) 
				y_objetivo[i] = y_origen[0];
			else
				y_objetivo[i] = 0;
		}
		//y_objetivo[l_objetivo] = Math.pow(v_xy/v_x/v_y,2); // r^2
		
		return y_objetivo;
	}
	
	public static double[] regresionExponencial(double[] x_origen, double[] y_origen, double[] x_objetivo) {
		int l_origen = x_origen.length;
		int l_objetivo = x_objetivo.length;
		double[] y_objetivo = new double[l_objetivo]; //+ 1]; // El último es el coef. de regresión
		// Y = A·e(B·X)
		double x_suma = 0, y_suma = 0, x_media = 0, y_media = 0, v_x = 0, v_y = 0, v_xy = 0, A = 0, B = 0; 
		
		for (int i = 0; i < l_origen; i++) {
			x_suma += x_origen[i];
			y_suma += Math.log(y_origen[i]);
			v_x += Math.pow(x_origen[i], 2);
			v_y += Math.pow(Math.log(y_origen[i]), 2);
			v_xy += x_origen[i] * Math.log(y_origen[i]);
		}
		x_media = x_suma / l_origen;
		y_media = y_suma / l_origen;
				
		B = (v_xy - y_media * x_suma) / (v_x - x_media * x_suma);
		A = Math.exp(y_media - B * x_media);
		
		v_xy = (v_xy / l_origen) - (x_media * y_media);
		v_x = Math.sqrt(v_x / l_origen - Math.pow(x_media, 2));
		v_y = Math.sqrt(v_y / l_origen - Math.pow(y_media, 2));
		
		for(int i = 0; i < l_objetivo; i++) {
			if (l_origen > 1)
				y_objetivo[i] = A * Math.exp(B * x_objetivo[i]);
			else if (l_origen == 1) 
				y_objetivo[i] = y_origen[0];
			else
				y_objetivo[i] = 0;
		}
		//y_objetivo[l_objetivo] = Math.pow(v_xy/v_x/v_y, 2); // r^2
		
		return y_objetivo;
	}
	
	public static double[] regresionLogaritmica(double[] x_origen, double[] y_origen, double[] x_objetivo) {
		int l_origen = x_origen.length;
		int l_objetivo = x_objetivo.length;
		double[] y_objetivo = new double[l_objetivo]; //+ 1]; // El último es el coef. de regresión
		// Y = A + B·ln(X)
		double x_suma = 0, y_suma = 0, x_media = 0, y_media = 0, v_x = 0, v_y = 0, v_xy = 0, A = 0, B = 0; 
		
		for (int i = 0; i < l_origen; i++) {
			x_suma += Math.log(x_origen[i]);
			y_suma += y_origen[i];
			v_x += Math.pow(Math.log(x_origen[i]), 2);
			v_y += Math.pow(y_origen[i], 2);
			v_xy += Math.log(x_origen[i]) * y_origen[i];
		}
		x_media = x_suma / l_origen;
		y_media = y_suma / l_origen;
		B = (v_xy - y_media * x_suma) / (v_x - x_media * x_suma);
		A = y_media - B * x_media;
		v_xy = (v_xy / l_origen) - (x_media * y_media);
		v_x = Math.sqrt(v_x / l_origen - Math.pow(x_media, 2));
		v_y = Math.sqrt(v_y / l_origen - Math.pow(y_media, 2));
		
		for(int i = 0; i < l_objetivo; i++) {
			if (l_origen > 1)
				y_objetivo[i] = A + B * Math.log(x_objetivo[i]);
			else if (l_origen == 1) 
				y_objetivo[i] = y_origen[0];
			else
				y_objetivo[i] = 0;
		}
		//y_objetivo[l_objetivo] = Math.pow(v_xy/v_x/v_y,2); // r^2
		
		return y_objetivo;
	}
	
	public static double[][] gaussJordan(double[][] X) {
		int f = X.length;
		int c = X[0].length;
		double[][] Y = new double[f][c];
		double a = 1, b = 1;
		
		for (int i = 0; i < f; i++) 
			for (int j = 0; j < c; j++) 
				Y[i][j] = X[i][j];
		for (int i = 0; i < f; i++) {
			a = Y[i][i];
			for (int j = 0; j < c; j++) 
				Y[i][j] = Y[i][j] / a;
			for (int l = i+1; l < f; l++) {
				b = Y[l][i] / Y[i][i];
				for (int j = i; j < c; j++) 
					Y[l][j] = Y[l][j] - b * Y[i][j];
			}
		}
		for (int i = f-1; i >= 0; i--) {
			a = Y[i][i];
			for (int l = i-1; l >= 0; l--) {
				b = Y[l][i];
				for (int j = i; j < c; j++) 
					Y[l][j] = Y[l][j] - b * Y[i][j];
			}
		}
		return Y; 
	}
	
	public static double[] regresionPolinomica(double[] x_origen, double[] y_origen, double[] x_objetivo) {
		int l_origen = x_origen.length;
		int l_objetivo = x_objetivo.length;
		double[] y_objetivo = new double[l_objetivo]; //+ 1]; // El último es el coef. de regresión
		// Y = a0 + a1·X + a2·X2 + ... an·Xn
		double[][] X = new double[l_origen][l_origen + 1];
				
		for (int i = 0; i < l_origen; i++) {
			for (int j = 0; j <= l_origen; j++)
				X[i][j] = 0;
			X[i][l_origen] = 0;
		}
		for (int a = 0; a < l_origen; a++) {
			for (int i = 0; i < l_origen; i++) {
				for (int j = 0; j < l_origen; j++)
					X[i][j] += Math.pow(x_origen[a], i+j);
				X[i][l_origen] += Math.pow(x_origen[a], i) * y_origen[a];
			}
		}
		
		if (l_origen > 1) {
			double[][] Y = gaussJordan(X);
			
			for (int j = 0; j < l_origen; j++)
				for (int i = 0; i < l_objetivo; i++)
					y_objetivo[i] += Y[j][l_origen] * Math.pow(x_objetivo[i], j);
		} else if (l_origen == 1) {
			for (int i = 0; i < l_objetivo; i++)
				y_objetivo[i] = y_origen[0];
		} else {
			for (int i = 0; i < l_objetivo; i++)
				y_objetivo[i] = 0;
		}
		
		return y_objetivo;
	}
	
	public static void show(double[] X) {
		for (int i = 0; i < X.length; i++)
			System.out.print(X[i] + " ");
		System.out.println(" ");
	}
	
	public static void show(double[][] X) {
		for (int i = 0; i < X.length; i++)
			for (int j = 0; j < X[0].length; j++) 
				System.out.print(X[i][j] + " ");
		System.out.println(" ");
	}
	
	public static double[] calculoDatosRegresion(double[] datos, int n, int variables, int rep, boolean negativos) {
		// n = 1: Metodo de regresion lineal
		// n = 2: Metodo de regresion exponencial
		// n = 3: Metodo de regresion polinomica
		// n = 4: Metodo de regresion logaritmica
		// variables: Numero de datos diferentes en la serie de datos
		// rep: Numero de mediciones de cada variable
		// Numero total de datos en la serie = var*rep
		// iniciales: true si faltan los datos finales (false si faltan los iniciales)
		// negativos: true si puede haber datos negativos, si es false estos valores se ponen a cero
		
		if ((int)datos[variables*rep] == 0) 
			return datos;
		int num_datos = rep - (int)datos[variables*rep] / variables;
		
		double[] x_origen = new double[num_datos];
		double[] x_objetivo = new double[rep - num_datos];
		double[][] datos_origen = new double[variables][num_datos];
		double[][] nuevos_datos = new double[variables][rep - num_datos];
		double[] datos_salida = new double[datos.length];
		
		int l = 0, m = 0;
		for (int i = 0; i < rep; i++)
			if (datos[i*variables] != -100) {
				System.out.println(" " + i*variables + " " + datos[i*variables] + " " + x_objetivo.length + " " + x_origen.length);
				x_origen[l] = i + 1;
				for (int j = 0; j < variables; j++)
					datos_origen[j][l] = datos[i*variables + j];
				l++;
			} else {
				System.out.println(" " + i*variables  + " " + datos[i*variables] + " " + x_objetivo.length + " " + x_origen.length);	
				x_objetivo[m] = i + 1;
				m++;
			}
		for (int i = 0; i < variables; i++) 
			nuevos_datos[i] = regresion(x_origen, datos_origen[i], x_objetivo, n);
		
		int o = 0;
		for (int j = 0; j < rep; j++) {
			for (int i = 0; i < variables; i++) 			 
				if (datos[i + j*variables] != -100)
					datos_salida[i + j*variables] = datos[i + j*variables];
				else /*
					if (!negativos && nuevos_datos[i][o] < 0)
						datos_salida[i + j*variables] = 0;
					else*/
						datos_salida[i + j*variables] = nuevos_datos[i][o];
			if (datos[j*variables] == -100)
				o++;
		}
		datos_salida[variables*rep] = 0;
		
		return datos_salida;
	}
}
