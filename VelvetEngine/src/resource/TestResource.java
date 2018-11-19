package resource;

public class TestResource extends ResourceType<TestResource>
{
	public final static int 	TYPE	= getNextTypeID();
	public final static String 	NAME	= "Test Resource";
	
	@Override
	public void load(String filepath, int flags)
	{
		System.out.println("LOADED");
	}

	@Override
	public void unload()
	{
		System.out.println("UNLOADED");
	}

	@Override
	public TestResource create()
	{
		return new TestResource();
	}

	@Override
	public int getTypeID()
	{
		return TYPE;
	}

	@Override
	public String getTypeName()
	{
		return NAME;
	}
}
