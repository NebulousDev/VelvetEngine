package loaders;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class FileLoader
{
	public static byte[] readFileAsBytes(String filepath)
	{
		byte[] buffer = new byte[1024];
		InputStream fileStream = FileLoader.class.getResourceAsStream(filepath);
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		
		System.out.println("Reading : " + filepath);
		
		try
		{
			int bytes = 0;
			while((bytes = fileStream.read(buffer)) > 0)
				byteBuffer.write(buffer, 0, bytes);
			fileStream.close();
		}
		
		catch(Exception e)
		{
			System.out.println("Error! Unable to locate file: " + filepath);
			e.printStackTrace();
			return null;
		}
		
		return byteBuffer.toByteArray();
	}
	
	public static String readFileAsString(String filepath)
	{
		return new String(readFileAsBytes(filepath));
	}
}
