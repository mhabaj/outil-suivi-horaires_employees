package controller;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * @author Alhabaj Mahmod/ Belda Tom/ Dakroub MohamadAli
 *
 * @param <Type> Serializable Object type.
 * 
 *               Manages serialization and data treatment/imports/exports of the
 *               Company components
 * 
 */
public class DataController<Type> {

	private String fileUrl;

	private FileOutputStream fos;
	private ObjectOutputStream oos;

	private FileInputStream fis;
	private ObjectInputStream ois;

	private MainController mc;

	/**
	 * Constructor of DataManager
	 * 
	 * @param fileURL of serialization and deserialization operations
	 * @param mc      App Controller
	 */
	public DataController(String fileURL, MainController mc) {
		this.mc = mc;
		this.fileUrl = fileURL;
	}

	/**
	 * @param objectToSerialise Object of type Type to serialize
	 * @throws IOException
	 */
	public void serialiseObject(Type objectToSerialise) throws IOException {

		this.fos = new FileOutputStream(fileUrl); // Initializing ressources
		this.oos = new ObjectOutputStream(this.fos);
		this.oos.writeObject(objectToSerialise); // serializing the object
		this.oos.close(); // closing ressources
		this.fos.close();

	}

	/**
	 * Looks for object of type Type inside the file to deserialize
	 * 
	 * @return null if operation failed or if nothing found or if found object
	 *         doesn't apply to Type
	 */
	@SuppressWarnings("unchecked")
	public Type deserializeObject() {
		try {
			try {
				this.fis = new FileInputStream(fileUrl); // Initializing ressources
				this.ois = new ObjectInputStream(this.fis);

				Type obj = (Type) this.ois.readObject(); // reading object from file
				this.ois.close();// closing ressources
				this.fis.close();
				return obj;
			} catch (EOFException e) {
				this.ois.close();// closing ressources
				this.fis.close();
				return null;
			}
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}

	}

	/**
	 * 
	 * Imports new Entries from a given file name
	 * 
	 * @param importedFilePath
	 */
	public void importEntriesFromFile(String importedFilePath) {

		try {
			File file = new File(importedFilePath); // Preparing file
			if (file.exists()) { // if it exists
				Scanner reader = new Scanner(file);
				while (reader.hasNextLine()) {
					mc.parseEmulatorInput(reader.nextLine()); // read and Parse imported data
				}
				reader.close();// closing ressources
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found" + System.lineSeparator());
		}

	}

	

}