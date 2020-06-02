package controller;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * 
 * Manages network connections and communications with Clients
 * 
 * @author Alhabaj Mahmod/ Belda Tom/ Dakroub MohamadAli
 * 
 * 
 * 
 */
public class ServerController implements Runnable {

	private ServerSocket mainDeviceServerSocket; // Server main socket
	private Socket ClientToServerSocket; // Clients socket
	private InputStream inputStream; // streams resources
	private ObjectInputStream objectInputStream;// streams resources
	private MainController mc;
	private int serverPort;
	private boolean status_Server; // true = up, false = down

	/**
	 * Constructor of DataTransferServer
	 * 
	 * @param mc         App Controller
	 * @param serverPort Server port number
	 */

	public ServerController(MainController mc, int serverPort) {

		this.serverPort = serverPort;
		this.mc = mc;

	}

	/**
	 * Initializing server sockets and resources and updates server status
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {

		try {
			this.mainDeviceServerSocket = new ServerSocket(this.serverPort); // start server socket with designated
			serverUp();
		} catch (java.net.BindException e) {

			System.out.println(
					"Selected Server port already in use (did you start twice the program?) or unavailable. Try relauching the JVM/Eclipse or change port in Settings tab"
							+ System.lineSeparator());

			JOptionPane.showMessageDialog(null,
					"Selected Server port already in use (did you start twice the program?) or unavailable. Try relauching the JVM/Eclipse or change port in Settings tab");

			serverDown();

		}

	}
	//

	/**
	 * 
	 * Prepares server to accept data from client
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void connectAndRetrieveData() throws IOException, ClassNotFoundException {

		if (status_Server == true) {
			this.ClientToServerSocket = mainDeviceServerSocket.accept(); // Client socket init. and wait for client to
																			// connect

			this.inputStream = this.ClientToServerSocket.getInputStream(); // resources init.

			this.objectInputStream = new ObjectInputStream(inputStream); // object stream init on inputStream

			while (!ClientToServerSocket.isClosed()) {
				listenToData(); // retrieve data
			}
		}

	}

	/**
	 * Change server status to up
	 */
	public void serverUp() {
		status_Server = true;
		mc.getManagerView().setServerStatus(true);

	}

	/**
	 * Changes server status to down
	 */
	public void serverDown() {
		status_Server = false;
		this.mc.getManagerView().setServerStatus(false);
	}

	/**
	 * Reset client connections and data streams for new clients
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void resetFlux() throws IOException, ClassNotFoundException {
		ClientToServerSocket.close();
		this.ClientToServerSocket = mainDeviceServerSocket.accept(); // reset Client socket init. and wait for client to
																		// connect
		this.inputStream = this.ClientToServerSocket.getInputStream(); // reset data streams for new clients
		this.objectInputStream = new ObjectInputStream(inputStream);
	}

	/**
	 * 
	 * Read data from stream and call Parser on them for injection into data base.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void listenToData() throws ClassNotFoundException, IOException {
		// read the list of messages from the socket
		try {
			mc.parseEmulatorInput((String) objectInputStream.readObject()); // get data, send into parser for further
																			// treatment

		} catch (EOFException e) {
			resetFlux();
		}
	}

	/**
	 * Sets server status flag to false and stops server
	 * 
	 */
	public void stopCurrentServer() {
		System.out.println("Server stopped");
		serverDown();

	}

	/**
	 * sets server port
	 * 
	 * @param newPort
	 */
	public void setPort(int newPort) {
		this.serverPort = newPort;
	}

	/**
	 * get current configured port on server
	 * 
	 * @return current server Port
	 */
	public int getPort() {
		return serverPort;
	}

	@Override
	public void run() {
		System.out.println("Server starting up server on port: " + serverPort + System.lineSeparator());
		try {
			this.init(); // starting up and init sockets

			while (status_Server == true) {
				connectAndRetrieveData(); // retrieve data
			}

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Server exception: " + e.getMessage());
			e.printStackTrace();
		}

	}

}