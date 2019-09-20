package mult_matriz_server_rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MultiplicaMatriz extends UnicastRemoteObject implements MultiplicaMatrizInterface {

	private static final long serialVersionUID = 1L;
	private long[] linha_c;

	public MultiplicaMatriz() throws RemoteException {
	};

	@Override
	public long[] multLinha(long[] linha_a, long[][] matB) throws RemoteException {
		int size = matB.length;
		linha_c = new long[size];
		int k = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				linha_c[k] = linha_c[k] + (linha_a[j] * matB[j][i]);
			}
			k++;
		}
		return linha_c;
	}

}
