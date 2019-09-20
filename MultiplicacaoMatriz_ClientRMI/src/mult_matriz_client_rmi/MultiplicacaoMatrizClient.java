package mult_matriz_client_rmi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MultiplicacaoMatrizClient {

	private final static int lin = 2;
	private final static int col = 2;

	private static long[][] matA = new long[lin][col];
	private static long[][] matB = new long[lin][col];
	private static long[][] matC = new long[lin][col];
	private static long[] linha = new long[col];

	public static void main(String[] args) {
		
		long[] linha_a = null;

		System.out.println("\nLendo arquivo da matriz A...");
		carregaMatrizA();
		System.out.println("\nLendo arquivo da matriz B...");
		carregaMatrizB();
		
		linha_a = matA[0];
		
		System.out.print("\nInicianto cliente da MultiplicacaoMatrizes...");

		try {
			MultiplicaMatrizInterface calc;

			// Registra o gerenciador de segurança
			System.out.print("\n\tRegistrando o gerenciador de segurança...");
			System.setSecurityManager(new SecurityManager());

			calc = (MultiplicaMatrizInterface)Naming.lookup("rmi://localhost:1099/MultiplicaMatriz");

			System.out.print("\n\tRESULTADO => " + calc.multLinha(linha_a, matB));

		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			System.err.print("\n\tErro: " + e.getMessage());
			System.exit(1);
		}
	}
	
	// Carrega Matrizes
		public static void carregaMatrizA() {
			int l, c;
			try {
				// Carrega a Matrix A
				FileReader file = new FileReader("src/matA2.txt");
				BufferedReader bufFile = new BufferedReader(file);

				// Lê a primeira linha
				String line = bufFile.readLine();
				l = c = 0;
				while (line != null) {
					matA[l][c] = Integer.parseInt(line);
					c++;
					if (c >= col) {
						l++;
						c = 0;
					}
					line = bufFile.readLine();
				}
				bufFile.close();
			} catch (Exception e) {
				System.err.println("Erro: " + e.getMessage());
				System.exit(1);
			}
		}

		public static void carregaMatrizB() {
			int l, c;
			try {
				// Carrega a Matrix B
				FileReader file = new FileReader("src/matB2.txt");
				BufferedReader bufFile = new BufferedReader(file);

				// Lê a primeira linha
				String line = bufFile.readLine();
				l = c = 0;
				while (line != null) {
					matB[l][c] = Integer.parseInt(line);
					c++;
					if (c >= col) {
						l++;
						c = 0;
					}
					line = bufFile.readLine();
				}
				bufFile.close();
			} catch (Exception e) {
				System.err.print("Erro: " + e.getMessage());
				System.exit(1);
			}
		}
	// FIM CARREGA MATRIZES

}
