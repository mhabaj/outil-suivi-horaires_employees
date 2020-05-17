package centralApplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class DataTransferServer {

	private ServerSocket mainDeviceServerSocket;
	private Socket ClientToServerSocket;
	ArrayList<Object> listOfUpdates;
	InputStream inputStream;
	ObjectInputStream objectInputStream;

	/**
	 * @param mainDeviceServerSocket
	 * @param clientToServerSocket
	 * @throws IOException
	 */
	public DataTransferServer() throws IOException {
		this.listOfUpdates = new ArrayList<Object>();

	}

	public void setupConnection() throws IOException {

		this.mainDeviceServerSocket = new ServerSocket(7778); // machine local donc pas d'adress

	}

	public void connect() throws IOException, ClassNotFoundException {
		System.out.println("En attente");
		this.ClientToServerSocket = mainDeviceServerSocket.accept(); // En attente de connection
		System.out.println("Connection en cours de la part de " + ClientToServerSocket + "...");

		this.inputStream = this.ClientToServerSocket.getInputStream();
		// Lecture des donnees
		this.objectInputStream = new ObjectInputStream(inputStream);

		// read the list of messages from the socket
		listOfUpdates.add(objectInputStream.readObject());
		System.out.println("Recu [" + listOfUpdates.size() + "] entrees de: " + ClientToServerSocket);
		

		System.out.println("Closing sockets.");
		mainDeviceServerSocket.close();
		ClientToServerSocket.close();
	}
	
	public ArrayList<Object> getListOfUpdates() {
		return this.listOfUpdates;
	}

}