package resource;

public class Resource<Type extends ResourceType<Type>> {
	
	public static final int LOADED		= 0x1;
	public static final int PERMENENT  	= 0x3;	
	
	private String 	localName;
	private String 	filepath;
	private int		flags;
	
	private Type resource;

	Resource(ResourceType<Type> type, String localName, String filepath, int flags)
	{
		this.localName = localName;
		this.filepath = filepath;
		this.flags = flags;
		this.resource = type.create();
	}
	
	void load()
	{
		resource.load(filepath, flags);
	}
	
	void unload()
	{
		resource.unload();
	}
	
	void increment()
	{
		ResourceManager.incrementReference(localName);
	}
	
	void decrement()
	{
		ResourceManager.incrementReference(localName);
	}
	
	public boolean hasRefereneces()
	{
		return ResourceManager.hasReferences(localName);
	}
	
	public int getReferenceCount()
	{
		return ResourceManager.getReferenceCount(localName);
	}
	
	public boolean isLoaded()
	{
		return (flags & LOADED) != 0;
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
	
	public Type getRawResource()
	{
		return resource;
	}

}
