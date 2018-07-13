package graphics;

import java.util.ArrayList;

public class GraphicsProgram
{
	int 				id			= -1;
	String				name		= null;
	ArrayList<GraphicsShader> 	shaders 	= null;
	boolean				finalized	= false;
	
	GraphicsProgram() {}
	
	public int ID() { return id; }
	
	public String name() { return name; }
	
	public ArrayList<GraphicsShader> shaders() { return shaders; }
	
	public boolean isFinalized() { return finalized; }
	
	public boolean isValid() { return id >= 0; }
}
