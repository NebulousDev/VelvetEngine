package graphics;

public class GraphicsShader
{
	int 		id			= -1;
	String		name		= null;
	ShaderType 	type		= null;
	
	GraphicsShader() {}
	
	public int ID() { return id; }
	
	public String name() { return name; }
	
	public ShaderType type() { return type; }
	
	public boolean isValid() { return id >= 0; }
}
