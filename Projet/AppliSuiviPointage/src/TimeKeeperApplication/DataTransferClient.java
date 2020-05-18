package TimeKeeperApplication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class DataTransferClient<Element> {

	private Socket socket;
	private OutputStream outputStream;
	private ObjectOutputStream objectOutputStream;

	public DataTransferClient() {
		// Initialisation du socket d'envoie
		try {
			this.socket = new Socket("127.0.0.1", 7778);
			// Flux de donnees:
			this.outputStream = socket.getOutputStream();
			// Envoie des donnes via objectOutputStream:
			objectOutputStream = new ObjectOutputStream(outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void send(ArrayList<Element> ObjectsToSendArray) throws UnknownHostException, IOException {

		objectOutputStream.writeObject(ObjectsToSendArray);

		System.out.println("Fin de la tache, Fermeture de connexion");
	}

	public void send(Element ObjectToSend) throws UnknownHostException, IOException {

		objectOutputStream.writeObject(ObjectToSend);

		System.out.println("Fin de la tache, Fermeture de connexion");
	}

	public void connectionShutdown() throws IOException {
		this.socket.close();

	}

}
