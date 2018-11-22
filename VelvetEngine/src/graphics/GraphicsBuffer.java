package graphics;

public class GraphicsBuffer
{
	public int 			id		= -1;
	public int 			size	= 0;
	public BufferType 	type	= null;
	
	GraphicsBuffer() {}
	
	public int ID() { return id; }
	
	public int size() { return size; }
	
	public BufferType type() { return type; }
	
	public boolean isValid() { return id >= 0; }

}
