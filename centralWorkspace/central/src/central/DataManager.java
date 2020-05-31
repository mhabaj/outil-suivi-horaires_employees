package central;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataManager<Type> {

    private String fileUrl;

    private FileOutputStream fos;
    private ObjectOutputStream oos;

    private FileInputStream fis;
    private ObjectInputStream ois;

    public DataManager(String fileURL) {
        this.fileUrl = fileURL;
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
            try {
                this.fis = new FileInputStream(fileUrl);
                this.ois = new ObjectInputStream(this.fis);
                Type obj = (Type) this.ois.readObject();
                this.ois.close();
                this.fis.close();
                return obj;
            } catch (EOFException e) {
                this.ois.close();
                this.fis.close();
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }

    }

}