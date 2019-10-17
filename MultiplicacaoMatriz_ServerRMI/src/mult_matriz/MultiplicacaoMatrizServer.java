package mult_matriz;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MultiplicacaoMatrizServer {
	
	public static void main(String[] args) throws NotBoundException {
		System.out.println("\nIniciando servidor mult_matriz...");
		
		try {
			//Intancia o gerador de segurança
			System.out.println("\tRegistrando serviço de segurança...");
			System.setSecurityManager(new SecurityManager());
			
			//Instancia o objeto
			MultiplicaMatriz calc = new MultiplicaMatriz();
			
			//Registra o Objeto no RMI Registry
			System.out.println("\tRegistrando objeto no rmi registry...");
			System.setProperty("java.rmi.server.hostname", "10.151.33.114");
			Naming.rebind("rmi://localhost:1099/MultiplicaMatriz", calc);
			
			System.out.println("Aguardando requisicoes...");
		} catch (MalformedURLException | RemoteException e) {
			System.err.print("\n\tErro: " + e.getMessage());
			System.exit(1);
		}
	}

}
