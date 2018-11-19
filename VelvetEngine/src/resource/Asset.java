package resource;

import core.Game;

public class Asset<Type extends ResourceType<Type>> {
	
	public static final int PERMENENT = 0x1;	
	
	private String 	localName;
	private String 	filepath;
	private boolean	loaded;
	private int		flags;
	
	private AssetManager manager;
	
	private Type resource;

	Asset(Game game, AssetManager manager, ResourceType<Type> type, String localName, String filepath, int flags)
	{
		this.localName = localName;
		this.filepath = filepath;
		this.flags = flags;
		this.resource = type.create(game);
		this.manager = manager;
	}
	
	void load(Game game)
	{
		if(!loaded)
		{
			if(!resource.load(game, localName, filepath, flags))
				System.err.println("Error! : Failed to load " + resource.getTypeName() + " '" + localName + "'.");
			else
			{
				System.out.println("Success : Successfully loaded " + resource.getTypeName() +  " '" + localName + "'.");
				loaded = true;
			}
		}
	}
	
	void unload(Game game)
	{
		if(loaded)
		{
			if(!resource.unload(game))
				System.err.println("Error! : Failed to unload " + resource.getTypeName() + " '" + localName + "'.");
			else
			{
				System.out.println("Success : Successfully unloaded " + resource.getTypeName() +  " '" + localName + "'.");
				loaded = false;
			}
		}
	}
	
	void increment()
	{
		manager.incrementUser(localName);
	}
	
	void decrement()
	{
		manager.incrementUser(localName);
	}
	
	public boolean hasRefereneces()
	{
		return manager.hasUsers(localName);
	}
	
	public int getReferenceCount()
	{
		return manager.getUserCount(localName);
	}
	
	public boolean isLoaded()
	{
		return loaded;
	}
	
	public boolean isPermenent()
	{
		return (flags & PERMENENT) != 0;
	}

	public String getLocalName()
	{
		return localName;
	}

	public String getFilepath()
	{
		return filepath;
	}

	public int getFlags()
	{
		return flags;
	}
	
	public Type getResource()
	{
		return resource;
	}

}
