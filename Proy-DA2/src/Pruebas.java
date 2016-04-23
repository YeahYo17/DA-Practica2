import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Pruebas {

	/**		--- IMPORTANTE --- 
	
	Aqui hay que modificar la ruta y poner donde se tengan los archivos de prueba almacenados,
	Tambien será donde se guarden los archivos de salida.
  
	 **/
	private static 	String ruta = "C:\\Users\\Kevin\\Dropbox\\Uni\\4º Año\\DA\\Practicas\\Practica 2\\Proyectos Eclipse\\Proy-DA2\\";
	
	/**
	 * 
	 * @param l -> Tiempo en nanosegundos.
	 */
	public static void Tiempo(long l){
		
		System.out.println();
		System.out.println("================================");
		System.out.println("     TIEMPO -- " + l + " ns");
		System.out.println("================================");
		System.out.println();

	}
	
	/**
	 * 
	 * @param I -> Numero de viajeros solicitantes del viaje (N)
	 * @param K -> Numero de kilos que puede transportar el avion (T)
	 * @param P -> Numero de plazas disponibles en el avion (P)
	 * @throws IOException
	 */
	public static void creaPruebas(int I, int K, int P) throws IOException{
		String nombreFich = "PruebaN"+I+"T"+K+"P"+P+".txt";
		
	    File archivo = new File(ruta + nombreFich);
	    BufferedWriter bw;
	    bw = new BufferedWriter(new FileWriter(archivo));
	    
	    int dinero, kilos = 0;

	    Random rnd = new Random();

	    dinero = (int)(rnd.nextDouble() * 100);
	    kilos = (int)(rnd.nextDouble() * 100);
	    	    
	    bw.write(P + " " + K + " " + I);
	    bw.newLine();
		for(int j=0; j < I; j++){
			bw.write(dinero + " " + kilos);
			bw.newLine();
			
		    dinero = (int)(rnd.nextDouble() * 100);
		    kilos = (int)(rnd.nextDouble() * 100);
		    
		}

	    bw.close();
	    
	    System.out.println(" --- Se ha creado el archivo satisfactoriamente. ---");
	}
	
	/**
	 * 
	 * @param V -> Numero de pasajeros que finalmente van al viaje (V).
	 * @param pasajeros -> Array de pasajeros, de tamaño igual al numero de viajeros solicitantes del viaje (N),
	 * 						 los indices con valor '1' son los pasajeros que entrarian en el avion, '0' para los demas
	 * 							(el indice 0 del vector no se utiliza)
	 * @param beneficio -> Maximo beneficio que se obtiene del avion.
	 * @param I -> Numero de viajeros solicitantes del viaje (N)
	 * @param K -> Numero de kilos que puede transportar el avion (T)
	 * @param P -> Numero de plazas disponibles en el avion (P)
	 * @throws IOException
	 */
	public static void creaSalida(int V, int[] pasajeros, int beneficio, int I, int K, int P) throws IOException{
		
		Random rnd = new Random();
		int num = (int)(rnd.nextDouble() * 10000);
		
		String nombreFich = "SolucionV"+V+".N"+I+"T"+K+"P"+P+"-"+num+".txt";
		
	    File archivo = new File(ruta + nombreFich);
	    BufferedWriter bw;
	    bw = new BufferedWriter(new FileWriter(archivo));
	    	    
	    bw.write(String.valueOf(V));
	    bw.newLine();
	    for(int i=1; i < pasajeros.length; i++){

	    	if(pasajeros[i] == 1){
	    		bw.write(String.valueOf(i));
	    		bw.newLine();
	    	}
	    	
	    }
	    bw.write(String.valueOf(beneficio));
	    bw.newLine();

	    bw.close();
	    
	    System.out.println(" --- Se ha creado el archivo satisfactoriamente. ---");
	}
	
	/**
	 * Antiguo metodo de recopilar los datos de los euros dispuestos a pagar los viajeros,
	 * del archivo de prueba a ejecutar.
	 * ------------------------------------------------------------------------------------------------------
	 * 
	 * Lee el fichero asociado al caso de prueba y devuelve en un vector los pesos de los viajeros,
	 * menos el primer elemento del fichero (el indice 0 del vector no se utiliza).
	 * 
	 * @param ruta -> Ruta donde se encuentra nuestro fichero que queremos leer.
	 * @return -> Vector con los pesos del archivo.
	 */
	public static int[] leerVPrecio(String ruta, int N) throws IOException{
				
		int[] a = new int[N+1];
		int i=1; int j=0;
		
		Scanner in = new Scanner(new FileReader(ruta));

		in.nextInt(); in.nextInt(); in.nextInt();
		
		a[0] = 0;
		while (in.hasNextInt() && i < N+1) {
			a[i] = in.nextInt();
			i++;
			while(in.hasNextInt() && j < 1){
				in.nextInt();
				j++;
			}
			j=0;
		}
		in.close();
		
		return a;
	}
	
	/**
	 * Antiguo metodo de recopilar los datos de los pesos de los viajeros, del archivo de prueba a ejecutar.
	 * ------------------------------------------------------------------------------------------------------
	 * 
	 * Lee el fichero asociado al caso de prueba y devuelve en un vector los euros dispuestos a pagar de los viajeros,
	 * menos el primer elemento del fichero (el indice 0 del vector no se utiliza).
	 * 
	 * @param ruta -> Ruta donde se encuentra nuestro fichero que queremos leer.
	 * @return -> Vector con todos los elementos del fichero excepto el primero (elementos del vector + num. menores).
	 */
	public static int[] leerVPeso(String ruta, int N) throws IOException{
		
		int[] a = new int[N+1];
		int i=1; int j=0;
		
		Scanner in = new Scanner(new FileReader(ruta));

		in.nextInt(); in.nextInt(); in.nextInt(); in.nextInt();
		
		a[0] = 0;
		while (in.hasNextInt() && i < N+1) {
			a[i] = in.nextInt();
			i++;
			while(in.hasNextInt() && j < 1){
				in.nextInt();
				j++;
			}
			j=0;
		}
		in.close();
		
		return a;
	}
	
	/**
	 * 
	 * @param tabla -> tabla de 3 dimensiones que se va a imprimir.
	 * @param I -> Numero de viajeros solicitantes del viaje (N)
	 * @param K -> Numero de kilos que puede transportar el avion (T)
	 * @param P -> Numero de plazas disponibles en el avion (P)
	 * @param imprimir -> 'True' para imprimir en un archivo externo, 'False' para imprimir por pantalla.
	 * @throws IOException
	 */
	public static void imprimirTabla(int[][][] tabla, int I, int K, int P, boolean imprimir) throws IOException{

		if(imprimir == false){
			for(int p=0; p < P+1; p++){
				System.out.println("\t\t(0...I)");
				System.out.print("p="+p + "\t");
				for(int i=0; i < I+1; i++)
					System.out.print("\t"+i);
				System.out.println("\n\t---------------------------------------------------------------------");
				for(int c=0; c < K+1; c++){
					System.out.print( c + "\t| \t");
					for(int i=0; i < I+1; i++){
						System.out.print( tabla[c][i][p] + " \t");
					}
					System.out.println();
				}
				System.out.println("\n");
			}
		}else{
			Random rnd = new Random();
			int num = (int)(rnd.nextDouble() * 10000);
			
			String nombreFich = "TablaN"+I+"T"+K+"P"+P+"-"+num+".txt";
		    File archivo = new File(ruta + nombreFich);
		    BufferedWriter bw;
		    bw = new BufferedWriter(new FileWriter(archivo));
		    
			for(int p=0; p < P+1; p++){
				bw.write("\t(0...I)\n");
				bw.write("p="+p);
				for(int i=0; i < I+1; i++)
					bw.write(" \t"+i);
				bw.write("\n\t---------------------------------------------------------------------\n");
				for(int c=0; c < K+1; c++){
					bw.write( c + "\t| \t");
					for(int i=0; i < I+1; i++){
						bw.write( tabla[c][i][p] + " \t");
					}
					bw.newLine();
				}
				bw.newLine();bw.newLine();
			}
			
			bw.close();
		}
		
	}
	
	/**
	 * 
	 * @param pasajeros -> Array de pasajeros, de tamaño igual al numero de viajeros solicitantes del viaje (N),
	 * 						 los indices con valor '1' son los pasajeros que entrarian en el avion, '0' para los demas
	 * 							(el indice 0 del vector no se utiliza)
	 * @return -> Devuelve un entero con el numero de pasajeros que irian en el viaje.
	 */
	public static int calculaNumPasajeros(int[] pasajeros){
		
		int cont =0;
		
		for(int i=0; i < pasajeros.length; i++){
			if(pasajeros[i] == 1){
				cont++;
			}
		}
		return cont;
	}
	
	public static void main(String[] args) throws IOException{
		
		//creaPruebas(500,1000,200);

	    System.out.println("        --- DONE! --- ");
	}
}