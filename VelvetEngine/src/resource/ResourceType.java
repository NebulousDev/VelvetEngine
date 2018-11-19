package resource;

import core.Game;

public abstract class ResourceType<Type> {
	
	public abstract boolean load(Game game, String localName, String filepath, int flags);
	
	public abstract boolean unload(Game game);
	
	public abstract Type create(Game game);
	
	public abstract int getTypeID();
	public abstract String getTypeName();
	
	private static int nextTypeID = 0;
	
	protected static int getNextTypeID()
	{
		return ++nextTypeID;
	}
	
}
