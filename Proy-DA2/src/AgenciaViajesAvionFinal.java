import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AgenciaViajesAvionFinal {
	
	/**		--- IMPORTANTE --- 
	
	Aqui hay que modificar la ruta y poner donde se tengan los archivos de prueba almacenados,
	Tambien será donde se guarden los archivos de salida.
  
	 **/
	private	static String ruta = "C:\\Users\\Kevin\\Dropbox\\Uni\\4º Año\\DA\\Practicas\\Practica 2\\Proyectos Eclipse\\Proy-DA2\\";
	private static int[] pasajeros;
	
	/**
	 *  --- IMPORTATNE: PARTE NUEVA ---
	 *  
	 *  Esta parte nueva no estaba documentada en la memoria que se ha entregado,
	 *  se han modificado las funciones para leer los archivos de prueba correctamente,
	 *  pero finalmente se ha cambiado la forma de leer los datos, anteriormente se ejecutaban dos funciones
	 *  que devolvian dos arrays, uno de pesos y otro de euros, y que cada uno abria y cerraba
	 *  el archivo de prueba una vez, ademas para leer el N,T y P se volvia abrir y cerrar el archivo nuevamente.
	 *  
	 *  Por tanto se ha modificado esta parte añadiendo las variables globales siguientes,
	 *  VKilos, contiene los pesos en kilos de los pasajeros solicitantes, indexados en el array.
	 *  VEuros, contiene los euros dispuestos a pagar por cada pasajero, indexados en el array.
	 *  N, contiene el valor de N del archivo de prueba (Numero de viajeros solicitantes del viaje).
	 *  T, contiene el valor de T del archivo de prueba (Maximo Kilos que transporta el avion). 
	 *  P, contiene el valor de P del archivo de prueba (Numero de plazas libres en el avion).
	 *  
	 *  Debido a esto tambien puede ser que en el estudio experimental hayan podido variar los tiempos
	 *  de ejecucion del programa para los casos de prueba realizados.
	 */
	private static int[] VKilos;
	private static int[] VEuros;
	
	private static int N;
	private static int T;
	private static int P;
	
	/**
	 * Ahora para leer los datos se ha definido esta funcion, que dada la ruta del fichero
	 * (con el nombre del fichero inclusive), asigna los valores de N,T,P a las variables globales de mismo nombre,
	 * y rellena los arrays de VEuros y VKilos, el primero con los euros que pagaria cada pasajero, indexados en el array,
	 * y el segundo con los pesos en kilos que llevaria cada pasajero, indexados en el array.
	 * 
	 * @param rutaFich -> Ruta completa del archivo de prueba que queremos leer los datos (ruta+nombrefichero.txt).
	 * @throws FileNotFoundException
	 */
	public static void leerDatos(String rutaFich) throws FileNotFoundException{
		
		Scanner scan2 = new Scanner(new FileReader(rutaFich));
		
		P = scan2.nextInt();
		T = scan2.nextInt();
		N = scan2.nextInt();
		
		int i=1;
		VKilos = new int[N+1];
		VEuros = new int[N+1];
		
		VKilos[0] = 0; VEuros[0] = 0;
		while (scan2.hasNextInt()) {
			VEuros[i] = scan2.nextInt();
			VKilos[i] = scan2.nextInt();
			i++;
		}
		
		scan2.close();
		
	}
	
	/**
	 * 
	 * @param VPeso -> Vector de con los pesos de los viajeros solicitantes del viaje.
	 * @param VPrecio -> Vector con los euros dispuestos a pagar de los viajeros solicitantes del viaje.
	 * @param I -> Numero de viajeros solicitantes del viaje (N)
	 * @param K -> Numero de kilos que puede transportar el avion (T)
	 * @param P -> Numero de plazas disponibles en el avion (P)
	 * @return -> Tabla de 3 dimensiones con los valores del problema calculados mediante programación dinamica.
	 * @throws IOException
	 */
	public static int[][][] CalculaMB(int[] VPeso, int[] VPrecio, int I, int K, int P) throws IOException{
				
		int[][][] tablaMB = new int[K+1][I+1][P+1];
		
		pasajeros = new int[I+1];
		
		int[][][] objSelec = new int[K+1][I+1][P+1];
		
		for(int k=1; k < K+1; k++){
			for(int i=1; i < I+1; i++){
				for(int p=1; p < P+1; p++){		
					
					if( VPeso[i] > k){
						tablaMB[k][i][p] = tablaMB[k][i-1][p];
						objSelec[k][i][p] = 0;
					}else{
						if( tablaMB[k][i-1][p] >= tablaMB[k-VPeso[i]][i-1][p-1] + VPrecio[i]){
							tablaMB[k][i][p] = tablaMB[k][i-1][p];
							objSelec[k][i][p] = 0;
						}else{
							tablaMB[k][i][p] = tablaMB[k-VPeso[i]][i-1][p-1] + VPrecio[i];
							objSelec[k][i][p] = 1;
							
						}
					}
					
				}
			}
		}
					
		//Pruebas.imprimirTabla(objSelec, I,K,P, true);

		int cap = K;
		int plazas = P; 
		int objIsiono = objSelec[K][I][P];

		pasajeros[0] = 0;
		for(int i=I; i > 0; i--){
			if(objIsiono == 0){
				pasajeros[i] = 0;
				objIsiono = objSelec[cap][i-1][plazas];
			}else{
				pasajeros[i] = 1;
				objIsiono = objSelec[cap-VPeso[i]][i-1][plazas-1];
				cap = cap-VPeso[i]; plazas--;
			}
		}
		
		return tablaMB;
				
	}
	
	public static void main(String[] args) throws IOException{
		
		Scanner scan = new Scanner(System.in);

		System.out.print("** Introduce el nombre del fichero de pruebas a ejecutar (nombre con extension): ");
		String fichero = scan.next();
		System.out.println();
		
		scan.close();
		
		System.out.println("\n=====================================================================\n");
		
		String rutaFich = ruta + fichero;
				
		int[][][] tabla;
		int beneficio=0;
		int V=0;
		
		long start = System.nanoTime();
		
		leerDatos(rutaFich);

		tabla = CalculaMB(VKilos, VEuros, N,T,P);
		
		//Pruebas.imprimirTabla(tabla, N,T,P, true);

		beneficio = tabla[T][N][P];
		
		V = Pruebas.calculaNumPasajeros(pasajeros);
		
		Pruebas.creaSalida(V, pasajeros, beneficio, N, T, P);
		
		long end = System.nanoTime();
		
		Pruebas.Tiempo(end-start);

	}
	
}
