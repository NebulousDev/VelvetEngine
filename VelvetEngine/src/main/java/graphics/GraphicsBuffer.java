package graphics;

public class GraphicsBuffer
{
	protected int 					id;
	protected int 					size;
	protected GraphicsBufferType 	type;
	
	GraphicsBuffer(int id, int size, GraphicsBufferType type)
	{
		this.id = id;
		this.size = size;
		this.type = type;
	}
	
	public int getID() { return id; }
	
	public int getSize() { return size; }
	
	public GraphicsBufferType getBufferType() { return type; }

}
