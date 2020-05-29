package central;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DataTransferServer implements Runnable {

	private ServerSocket mainDeviceServerSocket;
	private Socket ClientToServerSocket;
	private InputStream inputStream;
	private ObjectInputStream objectInputStream;
	private ManagerController mc;
	private int serverPort;
	private boolean available;
	Thread srv;

	/**
	 * @param mainDeviceServerSocket
	 * @param clientToServerSocket
	 * @throws IOException
	 */

	public DataTransferServer(ManagerController mc, int serverPort) {

		this.mc = mc;
		this.serverPort = serverPort;
		this.available = true;
		this.srv = new Thread(this);

		try {
			this.mainDeviceServerSocket = new ServerSocket(serverPort);
			Runtime.getRuntime().addShutdownHook(new Thread() { // Une routine de nettoyage afin d'éviter l'occupation
																// des ports
				public void run() {
					try {
						mainDeviceServerSocket.close();
					} catch (IOException e) {

					}
				}
			});
		} catch (IOException e) {
			System.out.println("Erreur: ServerSocket initialization");
		} // machine local donc pas d'adress

	}

	public void connect() throws IOException, ClassNotFoundException {

		System.out.println("En attente");
		try {
			this.ClientToServerSocket = mainDeviceServerSocket.accept(); // En attente de connection
			System.out.println("Connection en cours de la part de " + ClientToServerSocket + "...");

			this.inputStream = this.ClientToServerSocket.getInputStream();
			// Lecture des donnees
			this.objectInputStream = new ObjectInputStream(inputStream);
		} catch (NullPointerException e) {
			resetFlux();
		}
	}

	public void resetFlux() throws IOException, ClassNotFoundException {
		try {
			this.ClientToServerSocket = mainDeviceServerSocket.accept();
			this.inputStream = this.ClientToServerSocket.getInputStream();
			this.objectInputStream = new ObjectInputStream(inputStream);
		} catch (NullPointerException e) {
			System.out.println();
		}
	}

	public void listenToData() throws ClassNotFoundException, IOException {
		// read the list of messages from the socket
		try {
			mc.parseEmulatorInput((String) objectInputStream.readObject());

		} catch (EOFException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void finalize() {

		try {
			try {
				resetFlux();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			objectInputStream.close();
			inputStream.close();

		} catch (IOException e) {
			System.out.println("Couldn't stop server");
		}

	}

	public void startup_Server() {
		srv = new Thread(this);
		available = true;
		srv.start();
	}

	public void shutdown_Server() {
		available = false;
		srv.interrupt();
	}

	@Override
	public void run() {
		while (available == true) {
			try {
				this.connect();
				while (true) {
					listenToData();
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		finalize();

	}

}
