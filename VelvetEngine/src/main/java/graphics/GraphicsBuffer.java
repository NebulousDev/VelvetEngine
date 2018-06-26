package graphics;

public class GraphicsBuffer
{
	int 		id		= -1;
	int 		size	= 0;
	BufferType 	type	= null;
	
	GraphicsBuffer() {}
	
	public int ID() { return id; }
	
	public int size() { return size; }
	
	public BufferType type() { return type; }
	
	public boolean isValid() { return id >= 0; }

}
