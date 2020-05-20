package TimeKeeperApplication;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

// Pour la connexion: On peut faire une tentative toutes les 2 minutes pour
// savoir si on peut envoyer les donn�s
// On peut faire un system qui check si le serveur est UP en envoyant un paquet,
// et si c'est le
// cas, le serveur repond avec un paquet (un caract�re..Etc).

public class Emulator {
	private final int SIGNIN_STATUS = 0;
	private final int SIGNOUT_STATUS = 1;
	private ArrayList<TimeKeeper> eventsToProcess;
	private DataTransferClient<TimeKeeper> outputConnection;

	public Emulator() throws UnknownHostException, IOException {
		this.eventsToProcess = new ArrayList<TimeKeeper>();
		this.outputConnection = new DataTransferClient<TimeKeeper>();
	}

	public void workerSignIn(int id_Worker) {

		TimeKeeper workerSignIn = new TimeKeeper(id_Worker, SIGNIN_STATUS);

		try {
			this.outputConnection.send(workerSignIn);
		} catch (IOException e) {
			eventsToProcess.add(workerSignIn);

			e.printStackTrace();
		}

	}

	public void workerSignOut(int id_Worker) {

		TimeKeeper workerSignOut = new TimeKeeper(id_Worker, SIGNOUT_STATUS);

		try {
			this.outputConnection.send(workerSignOut);
		} catch (IOException e) {
			eventsToProcess.add(workerSignOut);

			e.printStackTrace();
		}

	}

}