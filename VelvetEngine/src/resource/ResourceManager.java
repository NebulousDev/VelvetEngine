package resource;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import core.Game;
import utils.FileUtils;

public class ResourceManager {

	private Game game;
	
	private HashMap<String, String> 	resourceFilepaths;
	private ArrayList<String> 			resourceFolders;

	private HashMap<String, Resource> 	loadedResources;
	
	public ResourceManager(Game game)
	{
		this.game = game;
		this.resourceFilepaths = new HashMap<>();
		this.resourceFolders = new ArrayList<>();
		this.loadedResources = new HashMap<>();
	}
	
	public void addPath(String filepath)
	{
		Path path = FileSystems.getDefault().getPath(filepath);
		
		if(Files.isDirectory(path))
		{
			File folder = path.toFile();
			for(File f : folder.listFiles())
			{
				if(f.isDirectory())
					addPath(f.getPath());
				else
					resourceFilepaths.put(FileUtils.stripExtention(f.getName()), f.getPath());
			}
			
			resourceFolders.add(filepath);
		}
		
		else
			System.out.println("Failure : Unable to add resource path: '" + filepath + "' is not a directory or does not exist.");
	}
	
	public void addResource(String tag, String filepath)
	{
		if(FileUtils.fileExists(filepath))
			resourceFilepaths.put(tag, filepath);
		
		else
			System.out.println("Failure : Unable to add resource path: '" + filepath + "' does not exist.");
	}
	
	public <T extends Resource> T getResource(Class<T> type, String tag)
	{
		T res = type.cast(loadedResources.get(tag));
		
		if(res != null)
			return res;
		
		else
			return load(type, tag);
	}

	public <T extends Resource> T load(Class<T> type, String tag)
	{
		if(!loadedResources.containsKey(tag))
		{
			if(resourceFilepaths.containsKey(tag))
			{
				try
				{
					String path = resourceFilepaths.get(tag);
					
					T resource = type.newInstance();
					resource.manager = this;
					resource.filepath = path;
					
					return type.cast(resource.load(game, this, tag, path));
				} 
				
				catch (InstantiationException | IllegalAccessException e)
				{
					e.printStackTrace();
					return null;
				}
			}
			else
			{
				System.out.println("Failure : No filepath associated with '" + tag + "'.");
				return null;
			}
		}
		
		return type.cast(loadedResources.get(tag));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public <T extends Resource> void unload(T resource)
	{
		if(loadedResources.containsKey(resource))
		{
			loadedResources.remove(resource.getName());
			resource.release(game, this);
		}
	}
	
	public boolean isLoaded(String tag)
	{
		return loadedResources.containsKey(tag);
	}
}
