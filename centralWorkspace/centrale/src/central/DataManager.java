package central;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataManager<Type> {

	private final String fileUrl = "src" + File.separator + "centralApplication" + File.separator + "ressources"
			+ File.separator + "DataBase.ser";

	private FileOutputStream fos;
	private ObjectOutputStream oos;

	private FileInputStream fis;
	private ObjectInputStream ois;

	
	public DataManager() {

	}

	public void serialiseObject(Type ObjectToSerialise) throws IOException {

		this.fos = new FileOutputStream(fileUrl);
		this.oos = new ObjectOutputStream(this.fos);
		this.oos.writeObject(ObjectToSerialise);
		this.oos.close();
		this.fos.close();
	}

	public Type deserialiseObject() {
		try {
			this.fis = new FileInputStream(fileUrl);
			this.ois = new ObjectInputStream(this.fis);
			Type obj = (Type) this.ois.readObject();
			this.ois.close();
			this.fis.close();
			return obj;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

}
