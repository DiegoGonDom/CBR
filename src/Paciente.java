
public class Paciente {
	
	//Datos
	private String nombre;
	private int sexo;
	private double edad;
	private double[] evaq = new double[4+1];
    private double[] sf = new double[4+1];
    private double[] oswestry = new double[4+1];
    private double[] talla = new double[4+1];
    private double[] peso = new double[4+1];
    private double[] imc = new double[4+1];
    private double[] ep = new double[3+1];
    private double[] flex = new double[60+1];
    //Presiones
    private double[] presiones = new double[25+1];
    //SF36
    private double[] sf36 = new double[36+1];
    //EVAs
    private double[] eva = new double[120+1];
       
    public Paciente() {
    }
	
    public Paciente(String nombre, int sexo, double edad, double[] evaq, double[] sf, double[] oswestry, double[] talla, double[] peso, double[] imc, double[] ep, double[] flex, double[] presiones, double[] sf36, double[] eva) {
    	this.nombre = nombre;
    	this.sexo = sexo;
    	this.edad = edad;
    	this.evaq = evaq;
    	this.sf = sf;
    	this.oswestry = oswestry;
    	this.talla = talla;
    	this.peso = peso;
    	this.imc = imc;
    	this.ep = ep;
    	this.flex = flex;
    	this.presiones = presiones;
    	this.sf36 = sf36;
    	this.eva = eva;
    }
    
    public Paciente(String nombre, int sexo, double edad, double[] evaq, double[] sf, double[] oswestry, double[] talla, double[] peso, double[] imc, double[] ep, double[] flex) {
    	this.nombre = nombre;
    	this.sexo = sexo;
    	this.edad = edad;
    	this.evaq = evaq;
    	this.sf = sf;
    	this.oswestry = oswestry;
    	this.talla = talla;
    	this.peso = peso;
    	this.imc = imc;
    	this.ep = ep;
    	this.flex = flex;
    }
    
    public String getNombre() {
    	return this.nombre;
    }
    
    public int getSexo() {
    	return this.sexo;
    }
    
    public double getEdad() {
    	return this.edad;
    }
    
    public double[] getEvaq() {
    	return this.evaq;
    }
    
    public double[] getSf() {
    	return this.sf;
    }
    
    public double[] getOswestry() {
    	return this.oswestry;
    }
    
    public double[] getTalla() {
    	return this.talla;
    }
    
    public double[] getPeso() {
    	return this.peso;
    }
    
    public double[] getImc() {
    	return this.imc;
    }
    
    public double[] getEp() {
    	return this.ep;
    }
    
    public double[] getFlex() {
    	return this.flex;
    }
    
    public double[] getPresiones() {
    	return this.presiones;
    }
    
    public double[] getSF() {
    	return this.sf36;
    }
    
    public double[] getEVA() {
    	return this.eva;
    }
    
    public void setPresiones(double[] presiones) {
    	this.presiones = presiones;
    }
    
    public void setSf(double[] sf) {
    	this.sf36 = sf;
    }
    
    public void setEva(double[] eva) {
    	this.eva = eva;
    }
    
    public void printN() {
    	System.out.println(this.nombre);
    }  
    
    public void printS(int s) {
    	if (s == 1)
    		System.out.print("Hombre ");
    	else
    		System.out.print("Mujer ");
    }
    
    public void printF(double[] v) {
    	int n = v.length - 1;
    	for(int i = 0; i < n; i++) {
    		System.out.print(v[i] + " ");
    	}
    }
    
    public void printE(double[] v) {
    	int n = v.length;
    	System.out.print(v[n-1] + " ");
    }
    
    public void printD() {
    	System.out.println(nombre);
    	System.out.print("Datos: ");
    	this.printS(sexo);
    	System.out.print(edad + " ");
    	this.printF(evaq);
    	this.printF(sf);
    	this.printF(oswestry);
    	this.printF(talla);
    	this.printF(peso);
    	this.printF(imc);
    	this.printF(ep);
    	this.printF(flex);
    	System.out.println("");
    }
    
    public void printP() {
    	this.printD();
    	System.out.print("Presiones: ");
    	this.printF(presiones);
    	System.out.println("");
    	System.out.print("SF36: ");
    	this.printF(sf36);
    	System.out.println("");
    	System.out.print("EVAs: ");
    	this.printF(eva);
    	System.out.println("");
    	System.out.println("");
    }
    
    public void printPE() {
    	System.out.print("Huecos: ");
    	if (this.edad == -100)
    		System.out.print("Edad ");
    	this.printE(evaq);
    	this.printE(sf);
    	this.printE(oswestry);
    	this.printE(talla);
    	this.printE(peso);
    	this.printE(imc);
    	this.printE(ep);
    	this.printE(flex);
    	this.printE(presiones);
    	this.printE(sf36);
    	this.printE(eva);
    	System.out.println("");
    }
    
    public void completarPaciente() {
    	this.evaq = Regresion.calculoDatosRegresion(this.evaq, 1, 1, 4, false);
    	this.sf = Regresion.calculoDatosRegresion(this.sf, 1, 1, 4, false);
    	this.oswestry = Regresion.calculoDatosRegresion(this.oswestry, 1, 1, 4, false);
    	this.talla = Regresion.calculoDatosRegresion(this.talla, 1, 1, 4, false);
    	this.peso = Regresion.calculoDatosRegresion(this.peso, 1, 1, 4, false);
    	this.imc = Regresion.calculoDatosRegresion(this.imc, 1, 1, 4, false);
    	this.ep = Regresion.calculoDatosRegresion(this.ep, 1, 1, 3, false);
    	this.flex = Regresion.calculoDatosRegresion(this.flex, 1, 2, 30, true);
    }
    
}
