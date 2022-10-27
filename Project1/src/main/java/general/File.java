package general;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class File<T>
{
	private String fileName;
	
	public File(String fileName)
	{
		this.fileName = fileName;
	}
	
	public void write(T object)
	{
		try
		{
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.fileName));
			
			outputStream.writeObject(object);
			outputStream.close();
			
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public T read()
	{
		try
		{
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.fileName));
			
			@SuppressWarnings("unchecked")
			T result = (T) inputStream.readObject();
			
			inputStream.close();
			
			return result;
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		return null;
	}
}
