package resource;

public abstract class ResourceType<Type> {
	
	abstract void load(String filepath, int flags);
	
	abstract void unload();
	
	abstract Type create();
	
	public abstract int getTypeID();
	public abstract String getTypeName();
	
	private static int nextTypeID = 0;
	
	protected static int getNextTypeID()
	{
		return ++nextTypeID;
	}
	
}
