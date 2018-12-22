package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.lwjgl.BufferUtils;

public class FileUtils
{
	public static final String RESOURCE_PATH = "/";
	
	public static String getFullPath(String localpath)
	{
		return FileSystems.getDefault().getPath(localpath).toAbsolutePath().toString();
	}
	
	public static String getFullInternalPath(String localpath)
	{
		String urlString = FileUtils.class.getResource(RESOURCE_PATH + localpath).toExternalForm();
		return urlString.replaceFirst("file:/", "").replaceFirst("jar:", "").replaceAll("%20", " ");
	}
	
	public static ByteBuffer readFileAsByteBuffer(String filepath)
	{
		byte[] buffer = new byte[1024];
		Path path = FileSystems.getDefault().getPath(filepath);
		InputStream fileStream;
		ByteArrayOutputStream byteStream;
		
		try
		{
			fileStream = Files.newInputStream(path);
			byteStream = new ByteArrayOutputStream();
		} 
		
		catch (IOException e1)
		{
			System.out.println("Error reading file: Unable to open file stream for '" + filepath + "'.");
			e1.printStackTrace();
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
	
	public static ByteBuffer readInternalFileAsByteBuffer(String filepath)
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
		
		Path path = FileSystems.getDefault().getPath(filepath);
		InputStream fileStream;
		ByteArrayOutputStream byteStream;
		
		try
		{
			fileStream = Files.newInputStream(path);
			byteStream = new ByteArrayOutputStream();
		} 
		
		catch (IOException e1)
		{
			System.out.println("[VELVET OBJ] Error reading file: Unable to open file stream for '" + filepath + "'.");
			e1.printStackTrace();
			return null;
		}
		
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
	
	public static boolean fileExists(String filepath)
	{
		return Files.exists(FileSystems.getDefault().getPath(filepath));
	}

	public static boolean internalFileExists(String filepath)
	{
		InputStream fileStream = FileUtils.class.getResourceAsStream(filepath);
		if(fileStream != null) return true;
		else return false;
	}
	
	public static String readFileAsString(String filepath)
	{
		return new String(readFileAsBytes(filepath));
	}
	
	public static String getContainingFolder(String filepath)
	{
		int last = filepath.lastIndexOf("\\");	//TODO: Allow other methods
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
}
