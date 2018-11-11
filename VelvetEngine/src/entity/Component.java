package entity;

public abstract class Component<T> implements ComponentInitializer<T>
{	
	protected int id;
	
	void setID(int id)
	{
		this.id = id;
	}
	
	public int getID()
	{
		return id;
	}

	public abstract int getTypeID();
	public abstract String getTypeName();
	
	private static int nextTypeID = 0;
	
	protected static int getNextTypeID()
	{
		return ++nextTypeID;
	}
}