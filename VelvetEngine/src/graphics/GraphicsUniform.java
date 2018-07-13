package graphics;

public class GraphicsUniform
{
	int 	id		= -1;
	String 	name	= null;
	
	GraphicsUniform() {}
	
	public int ID() { return id; }
	
	public String name() { return name; }
	
	public boolean isValid() { return id >= 0; }
}
