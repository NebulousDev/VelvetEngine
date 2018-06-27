package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileReader
{
	public static String readFileAsString(String filepath)
	{
		byte[] buffer = new byte[1024];
		InputStream fileStream = FileReader.class.getResourceAsStream(filepath);
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		
		try
		{
			int bytes = 0;
			while((bytes = fileStream.read(buffer)) > 0)
			{
				byteBuffer.write(buffer, 0, bytes);
			}
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}

		return new String(byteBuffer.toByteArray());
	}
}
