package graphics;

public class GraphicsContext
{
	private long context = -1;
	
	GraphicsContext(long context)
	{
		this.context = context;
	}
	
	public long getContext() { return context; }
	
	public boolean isValid() { return context >= 0; }
}
