package mult_matriz_client_rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MultiplicaMatrizInterface extends Remote {
	public long[] multLinha(long[] linha, long[][] matB) throws RemoteException;
}
