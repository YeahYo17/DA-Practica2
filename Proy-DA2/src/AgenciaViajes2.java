import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AgenciaViajes2 {
	
	private	static String ruta = "C:\\Users\\Kevin\\Dropbox\\Uni\\4º Año\\DA\\Practicas\\Practica 2\\Proyectos Eclipse\\Proy-DA2\\";
	private static ArrayList<Integer> viajerosRec = new ArrayList<Integer>();
	private static int[] pasajeros;
	
	public static int MBRecursiva(int[] VPeso, int[] VPrecio, int i, int K, int p){
		
		
		if(i==0 || K==0 || p == 0){
			return 0;
		}else if( VPeso[i] > K){
			return MBRecursiva(VPeso, VPrecio, i-1, K, p);
		}else{
			
			if( MBRecursiva(VPeso, VPrecio, i-1, K, p) < MBRecursiva(VPeso, VPrecio, i-1, K-VPeso[i], p-1) + VPrecio[i] ){
				viajerosRec.add(i);
				return MBRecursiva(VPeso, VPrecio, i-1, K-VPeso[i], p-1) + VPrecio[i];
			}else{
				return MBRecursiva(VPeso, VPrecio, i-1, K, p);
			}	
		}
		
	}
	
	public static int[][][] CalculaMB(int[] VPeso, int[] VPrecio, int I, int K, int P) throws IOException{ //, int[] viajeros){
				
		int[][][] tablaMB = new int[K+1][I+1][P+1];
		
		pasajeros = new int[I+1];


		for(int j=0; j < K+1; j++){
			for(int i=0; i < I+1; i++){
				tablaMB[j][i][0] = 0;
			}
		}
		
		for(int p=1; p < P+1; p++){
			for(int i=0; i < I+1; i++){
				tablaMB[0][i][p] = 0;
			}
		}	
		
		for(int p=1; p < P+1; p++){
			for(int c=0; c < K+1; c++){
				tablaMB[c][0][p] = 0;
			}
		}	
		
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
					
		System.out.println("=========================================================");
		System.out.println("=========================================================\n");
		System.out.println("\t -------------------------------");
		System.out.println("\t ----- Tabla Objetos Selec -----");
		System.out.println("\t -------------------------------\n");
		imprimirTabla(objSelec, I,K,P, true);
		System.out.println("\n=========================================================");
		System.out.println("=========================================================\n");

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
			
			String nombreFich = num+"-TablaN"+I+"T"+K+"P"+P+".txt";
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

		int[][][] tabla = new int[T+1][N+1][P+1];
		
		
		tabla = CalculaMB(VPeso,VPrecio, N,T,P);
				
		imprimirTabla(tabla, N,T,P, true);
		
		System.out.println("\n=====================================================================\n");

		int beneficio=0;

		beneficio = tabla[T][N][P];
		
		System.out.print("Viajeros Num: \t\t[");
		for(int i=0; i < pasajeros.length; i++){
			System.out.print(pasajeros[i] + ", ");
		}
		
		System.out.println("]\nMax. Beneficio: \t" + beneficio);
		
		System.out.println("\n=====================================================================\n");
		
		int cont=0;
		
		System.out.println("\n*********************************************************************");
		System.out.println("***  SOLUCION: ");
		System.out.println("***\t - Max. Beneficio = " + beneficio);
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






	}
	
}
