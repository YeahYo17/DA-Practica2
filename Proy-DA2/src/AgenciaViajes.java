import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AgenciaViajes {
	
	private	static String ruta = "C:\\Users\\Kevin\\Dropbox\\Uni\\4º Año\\DA\\Practicas\\Practica 2\\Proyectos Eclipse\\Proy-DA2\\";
	private static ArrayList<Integer> viajerosRec = new ArrayList<Integer>();
	private static ArrayList<Integer> viajeros = new ArrayList<Integer>();
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
	
	public static int[][][] CalcMB(int[] VPeso, int[] VPrecio, int I, int K, int P){ //, int[] viajeros){
				
		int[][][] tablaMB = new int[K+1][I+1][P+1];
		
		int numP=0;
		int posminprecio=0;
		
		pasajeros = new int[I+1];
		
		int pesototal =0;


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
		
		for(int k=1; k < K+1; k++){
			for(int i=1; i < I+1; i++){
				for(int p=1; p < P+1; p++){		
					
					if( VPeso[i] > k){
						tablaMB[k][i][p] = tablaMB[k][i-1][p];
						//viajeros[i] = 0;
					}else{
						if( tablaMB[k][i-1][p] >= tablaMB[k-VPeso[i]][i-1][p-1] + VPrecio[i]){
							tablaMB[k][i][p] = tablaMB[k][i-1][p];
							//viajeros[i] = 0;
						}else{
							tablaMB[k][i][p] = tablaMB[k-VPeso[i]][i-1][p-1] + VPrecio[i];
							
							if(k == K && p == P && numP < P){
								
								if(numP == 0) posminprecio = i;
								
								if( VPrecio[i] < VPrecio[posminprecio]){
									posminprecio = i;
								}
								
								numP++;
								//viajeros[i] = 1;
								//viajeros.add(i);
								pasajeros[i] = 1;
							}else if(k == K && p == P && numP == P){
								//viajeros.remove(0);
								//viajeros.add(i);
								
								if( VPrecio[i] > VPrecio[posminprecio]){
									//viajeros.remove(posminprecio-1);
									//viajeros.add(i);
									
									
									/*
									for(int j=0; j < viajeros.size(); j++){
										if( VPrecio[viajeros.get(j)] < VPrecio[posminprecio]){
											posminprecio = j;
										}
									}
									*/
									/*
									for(int j=0; j < pasajeros.length; j++){
										if( VPrecio[pasajeros[j]] < VPrecio[posminprecio]){
											posminprecio = j;
										}
									}
									*/
									
									pasajeros[posminprecio] = 0;
									pasajeros[i] = 1;
									posminprecio++;
																		
									for(int j=1; j < pasajeros.length; j++){
										if( VPrecio[j] < VPrecio[posminprecio] && pasajeros[j] > 0 && pasajeros[posminprecio] > 0){
											posminprecio = j;
										}
									}
									
									
								}
								

								

								
							}
						}
					}
					
				}
			}
		}			
					
		return tablaMB;
				
	}
	
	public static void imprimirTabla(int[][][] tabla, int I, int K, int P){

		for(int p=0; p < P+1; p++){
			System.out.println("\t(0...I)");
			System.out.print("p="+p);
			for(int i=0; i < I+1; i++)
				System.out.print(" \t"+i);
			System.out.println("\n\t---------------------------------------------------------------------");
			for(int c=0; c < K+1; c++){
				System.out.print( c + "| \t");
				for(int i=0; i < I+1; i++){
					System.out.print( tabla[c][i][p] + " \t");
				}
				System.out.println();
			}
			System.out.println("\n");
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
		
		int beneficioRec=0;

		beneficioRec = MBRecursiva(VPeso, VPrecio, N, T, P);

		System.out.println("=====================================================================\n");

		System.out.print("Viajeros Num (Recursiva): \t[");
		for(int i=0; i < viajerosRec.size(); i++){
			System.out.print(viajerosRec.get(i) + ", ");
		}
		System.out.println("]\nMax. Beneficio (Recursiva): \t" + beneficioRec);
		
		System.out.println("\n=====================================================================\n");

		int[][][] tabla = new int[T+1][N+1][P+1];
		
		//int[] viajeros = new int[N+1];
		
		//viajeros[0] = 7777;
		
		tabla = CalcMB(VPeso,VPrecio, N,T,P); //, viajeros);
				
		imprimirTabla(tabla, N,T,P);
		
		System.out.println("\n=====================================================================\n");

		int beneficio=0;

		beneficio = tabla[T][N][P];
		
		
		System.out.print("Viajeros Num: \t\t[");
		for(int i=0; i < pasajeros.length; i++){
			System.out.print(pasajeros[i] + ", ");
		}
		
		/*
		System.out.print("Viajeros Num: \t\t[");
		for(int i=0; i < viajeros.size(); i++){
			System.out.print(viajeros.get(i) + ", ");
		}
		*/
		
		System.out.println("]\nMax. Beneficio: \t" + beneficio);
		
		System.out.println("\n=====================================================================\n");


	}
	
}
