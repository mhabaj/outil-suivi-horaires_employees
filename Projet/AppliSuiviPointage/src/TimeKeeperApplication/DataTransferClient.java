package TimeKeeperApplication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class DataTransferClient {

	private Socket socket;
	private OutputStream outputStream;
	private ObjectOutputStream objectOutputStream;
	private ArrayList<Object> ObjectsToSend;

	/**
	 * @param socket
	 */
	public DataTransferClient() {
		this.ObjectsToSend = new ArrayList<>();
	}

	public void send(ArrayList<Object> ObjectsToSendArray) throws UnknownHostException, IOException {

		// Initialisation du socket d'envoie
		this.socket = new Socket("127.0.0.1", 7778);
		System.out.println("connexion reussi au serveur!");
		// Flux de donnees:
		outputStream = socket.getOutputStream();
		// Envoie des donnes via objectOutputStream:
		objectOutputStream = new ObjectOutputStream(outputStream);
		this.ObjectsToSend = ObjectsToSendArray;
		System.out.println("Envoie d'objets au serveur");
		objectOutputStream.writeObject(ObjectsToSend);

		System.out.println("Fin de la tache, Fermeture de connexion");
		socket.close();
	}

}