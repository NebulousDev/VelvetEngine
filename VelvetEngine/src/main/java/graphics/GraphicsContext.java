package graphics;

public class GraphicsContext
{
	private long context = 0;
	
	GraphicsContext(long context)
	{
		this.context = context;
	}
	
	public long getContext() { return context; }
}
