package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader {

	public static enum FileType
	{
		BINARY,
		TEXT
	}
	
	private FileType 		fileType;
	private String 			filepath;
	private BufferedReader	reader;
	private boolean			open;
	
	private FileReader(FileType fileType, String filepath, BufferedReader reader)
	{
		this.fileType = fileType;
		this.filepath = filepath;
		this.reader = reader;
		
		if(reader != null)
			this.open = true;
	}
	
	public static FileReader getFileReader(FileType fileType, String filepath)
	{
		if(!FileUtils.fileExists(filepath))
		{
			System.out.println("Error reading file : File not found - '" + filepath + "'.");
			return null;
		}
		
		FileInputStream inputStream = null;
		BufferedReader reader = null;
		
		try
		{
			inputStream = new FileInputStream(filepath);
			reader = new BufferedReader(new InputStreamReader(inputStream));
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println("Error reading file : Failed to create file stream - '" + filepath + "'.");
			e.printStackTrace();
			return null;
		}
		
		return new FileReader(fileType, filepath, reader);
	}
	
	public boolean hasLine()
	{
		if(open)
		{
			try
			{
				return reader.ready();
			}
			
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public String readLine()
	{
		if(open)
		{
			try
			{
				return reader.readLine();
			} 
			
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public void close()
	{
		if(open)
		{
			try
			{
				reader.close();
				open = false;
			}
			
			catch (IOException e)
			{
				System.out.println("Error closing file : Failed to close file stream - '" + filepath + "'.");
				e.printStackTrace();
			}
		}
	}
	
	public boolean isOpen()
	{
		return open;
	}

	public FileType getFileType()
	{
		return fileType;
	}

	public String getFilepath()
	{
		return filepath;
	}
	
}
