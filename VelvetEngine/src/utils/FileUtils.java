package utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

public class FileUtils
{
	public static String getFullPath(String localpath)
	{
		String urlString = FileUtils.class.getResource(getResourcePath() + localpath).toExternalForm();
		return urlString.replaceFirst("file:/", "").replaceFirst("jar:", "").replaceAll("%20", " ");
	}
	
	public static ByteBuffer readFileAsByteBuffer(String filepath)
	{
		byte[] buffer = new byte[4096];
		InputStream fileStream = FileUtils.class.getResourceAsStream(filepath);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		
		if(fileStream == null)
		{
			System.out.println("Error reading file: Unable to locate file: " + filepath);
			return null;
		}
		
		try
		{
			int bytes = 0;
			while((bytes = fileStream.read(buffer)) > 0)
				byteStream.write(buffer, 0, bytes);
			fileStream.close();
		}
		
		catch(Exception e)
		{
			System.out.println("Error reading file: Unable to read bytes from: " + filepath);
			e.printStackTrace();
			return null;
		}
		
		ByteBuffer byteBuffer = BufferUtils.createByteBuffer(byteStream.size());
		byteBuffer.put(byteStream.toByteArray());
		byteBuffer.flip();
		
		return byteBuffer;
	}
	
	public static byte[] readFileAsBytes(String filepath)
	{
		byte[] buffer = new byte[4096];
		InputStream fileStream = FileUtils.class.getResourceAsStream(filepath);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		
		if(fileStream == null)
		{
			System.out.println("Error reading file: Unable to locate file: " + filepath);
			return null;
		}
		
		try
		{
			int bytes = 0;
			while((bytes = fileStream.read(buffer)) > 0)
				byteStream.write(buffer, 0, bytes);
			fileStream.close();
		}
		
		catch(Exception e)
		{
			System.out.println("Error reading file: Unable to read bytes from: " + filepath);
			e.printStackTrace();
			return null;
		}
		
		return byteStream.toByteArray();
	}
	
	public static String readFileAsString(String filepath)
	{
		return new String(readFileAsBytes(filepath));
	}
	
	public static String getContainingFolder(String filepath)
	{
		int last = filepath.lastIndexOf("/");	//TODO: Allow other methods
		return filepath.substring(0, last);
	}
	
	public static String stripExtention(String filename)
	{
		int last = filename.lastIndexOf(".");
		return filename.substring(0, last);
	}
	
	public static String getFilename(String filepath)
	{
		String[] tokens = filepath.split("/|\\\\");
		String filename = tokens[tokens.length - 1];
		return stripExtention(filename);
	}
	
	public static String getFileExtention(String filepath)
	{
		int last = filepath.lastIndexOf(".");
		return filepath.substring(last);
	}

	public static String getResourcePath()
	{
		return "/";	//TODO: do something better?
	}
}
