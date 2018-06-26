package graphics;

public class Shader
{
	int 		id			= -1;
	String		name		= null;
	ShaderType 	type		= null;
	
	Shader() {}
	
	public int ID() { return id; }
	
	public String name() { return name; }
	
	public ShaderType type() { return type; }
	
	public boolean isValid() { return id >= 0; }
}
