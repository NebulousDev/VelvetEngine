package resource;

import core.Game;

public abstract class Resource {

	String 		name;
	String 		filepath;
	
	ResourceManager manager;
	
	protected abstract Resource load(Game game, ResourceManager manager, String tag, String filepath);
	
	protected abstract void release(Game game, ResourceManager manager);

	public void unload(ResourceManager manager)
	{
		manager.unload(this);
	}
	
	public String getName()
	{
		return name;
	}

	public String getFilepath()
	{
		return filepath;
	}

	public boolean isLoaded(ResourceManager manager)
	{
		return manager.isLoaded(name);
	}
	
}
