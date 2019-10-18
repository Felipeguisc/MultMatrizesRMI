package mult_matriz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.MessageDigest;

public class MultiplicacaoMatrizClient {

	private final static int lin = 4096;
	private final static int col = 4096;

	private static long[][] matA = new long[lin][col];
	private static long[][] matB = new long[lin][col];
	private static long[][] matC = new long[lin][col];

	private static int posicao = 0;

	public static void main(String[] args) throws InterruptedException {


		System.out.println("\nLendo arquivo da matriz A...");
		carregaMatrizA();
		System.out.println("\nLendo arquivo da matriz B...");
		carregaMatrizB();

		System.out.print("\nInicianto cliente da MultiplicacaoMatrizes...");
		
		long startTime = System.currentTimeMillis();

		Thread tt1 = new Thread(t1);
		Thread tt2 = new Thread(t2);
		Thread tt3 = new Thread(t3);
		Thread tt4 = new Thread(t4);
		//Thread tt5 = new Thread(t5);
		
		
		tt1.start();
		tt2.start();
		tt3.start();
		tt4.start();
		//tt5.start();
		
		while (posicao<col) {
			System.out.println(posicao);
		}
		
		tt1.join();
		tt2.join();
		tt3.join();
		tt4.join();
		//tt5.join();
		

		long stopTime = System.currentTimeMillis();
		long totalTime = stopTime - startTime;
		// Imprime tempo de execução
		System.out.println("\tTempo de execução: " + totalTime + " ms");
		System.out.println("\tTempo de execução: " + totalTime / 1000 + " s");
		System.out.println("\tTempo de execução: " + (totalTime / 1000) / 60 + " ms");
		
		// Grava um arquivo com a matriz C
				System.out.println("Gravando arquivo da matriz C...");
				try {
					File fOut = new File("src/matC.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(fOut));
					for (int i = 0; i < lin; i++) {
						for (int j = 0; j < col; j++) {
							writer.write(String.valueOf(matC[i][j]));
							if ((i == lin - 1) && (j == col - 1)) {
								continue;
							} else {
								writer.newLine();
							}
						}
					}
					writer.flush();
					writer.close();
				} catch (Exception e) {
					System.err.print("\n\tErro : " + e.getMessage());
					System.exit(1);
				}

				// Gera o MD5
				System.out.print("\n\tGerando o MD5 da matriz C...");
				try {
					File matCFile = new File("src/matC.txt");
					int matCSize = (int) matCFile.length();
					byte[] matCBytes = new byte[matCSize];
					matCBytes = getFileBytes(matCFile);
					MessageDigest md = MessageDigest.getInstance("MD5");
					byte[] hash = md.digest(matCBytes);
					System.out.println("\nHash: " + toHexFormat(hash));
					System.out.println("Gravando arquivo matC.md5...");
					File md5File = new File("src/matC.md5");
					BufferedWriter writer = new BufferedWriter(new FileWriter(md5File));
					writer.write(toHexFormat(hash) + "  matC.txt");
					writer.flush();
					writer.close();

				} catch (Exception e) {
					System.err.print("\n\tErro : " + e.getMessage());
					System.exit(1);
				}
		

	}

	// Carrega Matrizes
	public static void carregaMatrizA() {
		int l, c;
		try {
			// Carrega a Matrix A
			FileReader file = new FileReader("src/matA.txt");
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
			FileReader file = new FileReader("src/matB.txt");
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

				calc = (MultiplicaMatrizInterface) Naming.lookup("rmi://localhost:1099/MultiplicaMatriz");
				
				calc.defineMatB(matB,lin);

				while (posicao < lin) {
					posicao++;
					matC[posicao - 1] = calc.multLinha(matA[posicao - 1]);
					System.out.println("Executando...");
				}
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

				calc2 = (MultiplicaMatrizInterface) Naming.lookup("rmi://10.151.33.132:1099/MultiplicaMatriz");
				
				calc2.defineMatB(matB,lin);

				while (posicao < lin) {
					posicao++;
					matC[posicao - 1] = calc2.multLinha(matA[posicao - 1]);
					System.out.println("Executando...");
				}
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				System.err.print("\n\tErro: " + e.getMessage());
				System.exit(1);
			}
		}
	};
	
	private static Runnable t3 = new Runnable() {
		public void run() {
			try {
				MultiplicaMatrizInterface calc3;

				// Registra o gerenciador de segurança
				System.out.print("\n\tRegistrando o gerenciador de segurança...");
				System.setSecurityManager(new SecurityManager());

				calc3 = (MultiplicaMatrizInterface) Naming.lookup("rmi://10.151.33.143:1099/MultiplicaMatriz");
				
				calc3.defineMatB(matB,lin);

				while (posicao < lin) {
					posicao++;
					matC[posicao - 1] = calc3.multLinha(matA[posicao - 1]);
					System.out.println("Executando...");
				}
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				System.err.print("\n\tErro: " + e.getMessage());
				System.exit(1);
			}
		}
	};
	
	private static Runnable t4 = new Runnable() {
		public void run() {
			try {
				MultiplicaMatrizInterface calc4;

				// Registra o gerenciador de segurança
				System.out.print("\n\tRegistrando o gerenciador de segurança...");
				System.setSecurityManager(new SecurityManager());

				calc4 = (MultiplicaMatrizInterface) Naming.lookup("rmi://10.151.33.108:1099/MultiplicaMatriz");
				
				calc4.defineMatB(matB,lin);

				while (posicao < lin) {
					posicao++;
					matC[posicao - 1] = calc4.multLinha(matA[posicao - 1]);
					System.out.println("Executando...");
				}
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				System.err.print("\n\tErro: " + e.getMessage());
				System.exit(1);
			}
		}
	};
	
	private static Runnable t5 = new Runnable() {
		public void run() {
			try {
				MultiplicaMatrizInterface calc5;

				// Registra o gerenciador de segurança
				System.out.print("\n\tRegistrando o gerenciador de segurança...");
				System.setSecurityManager(new SecurityManager());

				calc5 = (MultiplicaMatrizInterface) Naming.lookup("rmi://10.151.33.143:1099/MultiplicaMatriz");
				
				calc5.defineMatB(matB,lin);

				while (posicao < lin) {
					posicao++;
					matC[posicao - 1] = calc5.multLinha(matA[posicao - 1]);
					System.out.println("Executando...");
				}
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				System.err.print("\n\tErro: " + e.getMessage());
				System.exit(1);
			}
		}
	};
	
	public static String toHexFormat(final byte[] bytes) {
		final StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	public static byte[] getFileBytes(File file) {
		int len = (int) file.length();
		byte[] sendBuf = new byte[len];
		FileInputStream inFile = null;

		try {
			inFile = new FileInputStream(file);
			inFile.read(sendBuf, 0, len);
		} catch (Exception e) {
			System.err.print("\n\tErro : " + e.getMessage());
			System.exit(1);
		}
		return sendBuf;
	}

}
