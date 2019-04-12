package cap_stone;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Utility {
	static boolean writeObjectToFile(Object object, String filePath)
    {
        try
        {
            FileOutputStream fileOutput = new FileOutputStream(filePath);
            ObjectOutputStream objOutput = new ObjectOutputStream(fileOutput);
            objOutput.writeObject(object);
            objOutput.close();
            return true;
        }
        catch (IOException error)
        {
            error.printStackTrace();
        }
        return false;
    }
@SuppressWarnings("unchecked")
static <T> T readObjectFromFile(String filePath)
    {
        try
        {
            FileInputStream fileInput = new FileInputStream(new File(filePath));
            @SuppressWarnings("resource")
			ObjectInputStream objInput = new ObjectInputStream(fileInput);
            return (T) objInput.readObject();
        }
        catch(IOException | ClassNotFoundException error)
        {
            error.printStackTrace();
        }
        return null;
    }
}
