package mult_matriz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MultiplicacaoMatrizClient {

	private final static int lin = 3;
	private final static int col = 3;

	private static long[][] matA = new long[lin][col];
	private static long[][] matB = new long[lin][col];
	private static long[][] matC = new long[lin][col];
	//private static long[] linha = new long[col];
	private static int posicao = 0;
	private static boolean terminou = false;

	public static void main(String[] args) {

		long[] linha_a = null;

		System.out.println("\nLendo arquivo da matriz A...");
		carregaMatrizA();
		System.out.println("\nLendo arquivo da matriz B...");
		carregaMatrizB();

		// linha_a = matA[0];

		System.out.print("\nInicianto cliente da MultiplicacaoMatrizes...");

		new Thread(t1).start();
		//new Thread(t2).start();
		while(!terminou) {
			System.out.print("");
		}
		for (int i = 0; i < lin; i++) {
			for (int j = 0; j < col; j++) {
				System.out.println("Mat C: " + matC[i][j]);
			}
		}

//		try {
//			MultiplicaMatrizInterface calc;
//
//			// Registra o gerenciador de segurança
//			System.out.print("\n\tRegistrando o gerenciador de segurança...");
//			System.setSecurityManager(new SecurityManager());
//
//			calc = (MultiplicaMatrizInterface) Naming.lookup("rmi://localhost:1099/MultiplicaMatriz");
//
//			for (int i = 0; i < col; i++) {
//				matC[i] = calc.multLinha(matA[i], matB);
//			}
//
//			// System.out.print("\n\tRESULTADO => " + matC[0][0] + " " + matC[0][1] + " " +
//			// matC[1][0] + " " + matC[1][1]);
//
//		} catch (MalformedURLException | RemoteException | NotBoundException e) {
//			System.err.print("\n\tErro: " + e.getMessage());
//			System.exit(1);
//		}
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

	private static Runnable t1 = new Runnable() {
		public void run() {
			try {
				MultiplicaMatrizInterface calc;

				// Registra o gerenciador de segurança
				System.out.print("\n\tRegistrando o gerenciador de segurança...");
				System.setSecurityManager(new SecurityManager());

				calc = (MultiplicaMatrizInterface) Naming.lookup("rmi://10.151.33.132:1099/MultiplicaMatriz");
				
				while (posicao < lin) {
					posicao++;
					matC[posicao - 1] = calc.multLinha(matA[posicao - 1], matB);
					System.out.println("Executando...");
				}
				terminou = true;
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				System.err.print("\n\tErro: " + e.getMessage());
				System.exit(1);
			}
		}
	};
	
	private static Runnable t2 = new Runnable() {
		public void run() {
			try {
				MultiplicaMatrizInterface calc2;

				// Registra o gerenciador de segurança
				System.out.print("\n\tRegistrando o gerenciador de segurança...");
				System.setSecurityManager(new SecurityManager());

				calc2 = (MultiplicaMatrizInterface) Naming.lookup("rmi://10.151.33.143:1099/MultiplicaMatriz");
				
				while (posicao < lin-1) {
					posicao++;
					matC[posicao - 1] = calc2.multLinha(matA[posicao - 1], matB);
				}

			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				System.err.print("\n\tErro: " + e.getMessage());
				System.exit(1);
			}
		}
	};

}
