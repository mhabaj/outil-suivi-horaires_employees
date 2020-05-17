package centralApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataManager {

	private final String fileUrl = "src" + File.separator + "centralApplication" + File.separator + "ressources"
			+ File.separator + "serialize.ser";

	private FileOutputStream fos;
	private ObjectOutputStream oos;

	private FileInputStream fis;
	private ObjectInputStream ois;

	/**
	 * @throws IOException
	 */
	public DataManager() throws FileNotFoundException, IOException {

	}

	public void serialiseObject(Object ObjectToSerialise) throws IOException {

		this.fos = new FileOutputStream(fileUrl);
		this.oos = new ObjectOutputStream(this.fos);
		this.oos.writeObject(ObjectToSerialise);
		this.oos.close();
		this.fos.close();

	}

	public Object deserialiseObject() throws IOException, ClassNotFoundException {
		this.fis = new FileInputStream(fileUrl);
		this.ois = new ObjectInputStream(this.fis);

		Object obj = this.ois.readObject();
		this.ois.close();
		this.fis.close();

		return obj;
	}

}
