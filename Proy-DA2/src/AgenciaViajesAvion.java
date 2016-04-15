import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AgenciaViajesAvion {
	
	/**		--- IMPORTANTE --- 
	
	Aqui hay que modificar la ruta y poner donde se tengan los archivos de prueba almacenados,
	Tambien será donde se guarden los archivos de salida.
  
	 **/
	private	static String ruta = "C:\\Users\\Kevin\\Dropbox\\Uni\\4º Año\\DA\\Practicas\\Practica 2\\Proyectos Eclipse\\Proy-DA2\\";
	private static int[] pasajeros;
	
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

		int P=0;
		int N=0;
		int T=0;
		
		String rutaFich = ruta + fichero;
		Scanner scan2 = new Scanner(new FileReader(rutaFich));
		
		while (scan2.hasNextInt()) {
			P = scan2.nextInt();
			T = scan2.nextInt();
			N = scan2.nextInt();
			break;
		}
		
		scan2.close();
		
		
		int[] VPrecio = Pruebas.leerVPrecio(rutaFich, N);
		
		System.out.print("VPrecio: \t");
		for(int i=0; i < VPrecio.length; i++){
			System.out.print(VPrecio[i] + " ");
		}
		System.out.println();
		System.out.println();
		
		int[] VPeso = Pruebas.leerVPeso(rutaFich, N);
		
		System.out.print("VPeso: \t\t");
		for(int i=0; i < VPeso.length; i++){
			System.out.print(VPeso[i] + " ");
		}
		System.out.println();
		System.out.println();

		System.out.println("=====================================================================\n");

		int[][][] tabla = new int[T+1][N+1][P+1];
				
		long start = System.nanoTime();
		tabla = CalculaMB(VPeso,VPrecio, N,T,P);
		long end = System.nanoTime();
	
		Pruebas.Tiempo(end-start);

		//Pruebas.imprimirTabla(tabla, N,T,P, true);
		
		System.out.println("\n=====================================================================\n");

		int beneficio=0;
		int cont=0;

		beneficio = tabla[T][N][P];
		
		System.out.println("\n*********************************************************************");
		System.out.println("***  SOLUCION: ");
		System.out.println("***\t - Max. Beneficio = " + beneficio);
		System.out.print("***\t - Array Pasajeros = [");
		for(int i=0; i < pasajeros.length; i++){
			System.out.print(pasajeros[i] + " ");
		}
		System.out.print("]\n");
		System.out.print("***\t - Pasajeros = [");
		for(int i=0; i < pasajeros.length; i++){
			if(pasajeros[i] == 1){
				System.out.print(i + " ");
				cont++;
			}
		}
		System.out.print("]");
		System.out.println("\n***\t - Num. Pasajeros = " + cont);
		System.out.println("*********************************************************************\n");
		
		Pruebas.creaSalida(cont, pasajeros, beneficio, N,T,P);

	}
	
}
