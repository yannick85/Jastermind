package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerLauncher {

	private static ExecutorService executorService =  Executors.newFixedThreadPool(10);
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(4637);
			Socket socket = null;
			//On atend de recevoir une ouverture de socket par un client
			while (true) {	
				try {
					//Nouveau Socket
					socket = serverSocket.accept();
					//On lance le thread avec le socket associé
					executorService.execute(new MastermindServer(socket));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket != null) serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
