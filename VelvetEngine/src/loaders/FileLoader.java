package loaders;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class FileLoader
{
	public static String readFileAsString(String filepath)
	{
		byte[] buffer = new byte[1024];
		InputStream fileStream = FileLoader.class.getResourceAsStream(filepath);
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		
		try
		{
			int bytes = 0;
			while((bytes = fileStream.read(buffer)) > 0)
			{
				byteBuffer.write(buffer, 0, bytes);
			}
		}
		
		catch(Exception e)
		{
			System.out.println("Error! Unable to locate file: " + filepath);
			e.printStackTrace();
			return null;
		}

		return new String(byteBuffer.toByteArray());
	}
}
