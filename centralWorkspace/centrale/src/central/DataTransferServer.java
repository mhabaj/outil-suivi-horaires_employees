package central;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DataTransferServer implements Runnable {

	private final int APPLICATION_DEFAULT_PORT = 7100;

	private ServerSocket mainDeviceServerSocket;
	private Socket ClientToServerSocket;
	private InputStream inputStream;
	private ObjectInputStream objectInputStream;
	private ManagerController mc;
	private int serverPort;
	private volatile boolean onlineStatus;
	Thread srv;

	/**
	 * @param mainDeviceServerSocket
	 * @param clientToServerSocket
	 * @throws IOException
	 */

	public DataTransferServer(ManagerController mc, int serverPort) {

		this.mc = mc;
		this.serverPort = serverPort;
		this.onlineStatus = true;
		try {
			try {
				this.mainDeviceServerSocket = new ServerSocket(this.serverPort);
			} catch (java.net.BindException t) {
				System.out.println("Default port 7100 not available. Please change network settings to a new port");
				this.shutdown_Server();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (mainDeviceServerSocket != null) {
			this.srv = new Thread(this);
		}
	}

	public DataTransferServer(ManagerController mc) {

		this.mc = mc;
		this.serverPort = APPLICATION_DEFAULT_PORT;
		this.onlineStatus = true;
		try {
			try {
				this.mainDeviceServerSocket = new ServerSocket(this.serverPort);
			} catch (java.net.BindException t) {
				System.out.println("Default port 7100 not available. Please change network settings to a new port");
				this.shutdown_Server();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (mainDeviceServerSocket != null) {
			startup_Server();
		}
	}

	public void connect() throws IOException, ClassNotFoundException {

		System.out.println("Server listening on port:" + serverPort + " :");
		try {
			if (mainDeviceServerSocket != null) {
				this.ClientToServerSocket = mainDeviceServerSocket.accept(); // En attente de connection
				System.out.println("Connection en cours de la part de " + ClientToServerSocket + "...");

				if (ClientToServerSocket != null) {
					this.inputStream = this.ClientToServerSocket.getInputStream();
					if (inputStream != null) {
						// Lecture des donnees
						this.objectInputStream = new ObjectInputStream(inputStream);
					}
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void resetFlux() throws IOException {
		try {
			this.ClientToServerSocket = mainDeviceServerSocket.accept();
			this.inputStream = this.ClientToServerSocket.getInputStream();
			this.objectInputStream = new ObjectInputStream(inputStream);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void listenToData() throws ClassNotFoundException, IOException {
		// read the list of messages from the socket
		if (objectInputStream != null) {
			try {
				mc.parseEmulatorInput((String) objectInputStream.readObject());

			} catch (EOFException e) {
				// e.printStackTrace();
			}
		}
	}

	public void startup_Server() {
		srv = new Thread(this);
		onlineStatus = true;
		srv.start();
	}

	public void shutdown_Server() {
		onlineStatus = false;
		try {
			if (mainDeviceServerSocket != null && ClientToServerSocket != null) {
				mainDeviceServerSocket.close();
				ClientToServerSocket.close();
			}
			if (srv != null) {
				srv.join();
				System.out.println("Server offline");
			}

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

	}

	public void updateServerSettings(int newPortNumber) {

		this.shutdown_Server();

		serverPort = newPortNumber;

		try {
			try {
				this.mainDeviceServerSocket = new ServerSocket(serverPort);
			} catch (java.net.BindException t) {
				System.out.println("Selected port " + serverPort + " not available. Try Again");
				this.shutdown_Server();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (mainDeviceServerSocket != null && onlineStatus == false) {
			startup_Server();

		}

	}

	@Override
	public void run() {

		try {

			this.connect();
			while (onlineStatus == true) {
				listenToData();
				System.out.println("LAAA");
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
