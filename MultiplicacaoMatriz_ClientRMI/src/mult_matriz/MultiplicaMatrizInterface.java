package mult_matriz;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MultiplicaMatrizInterface extends Remote {
	public long[] multLinha(long[] linha_a, long[][] matB) throws RemoteException;
}