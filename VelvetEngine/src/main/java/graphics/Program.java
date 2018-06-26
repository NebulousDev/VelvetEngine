package graphics;

import java.util.ArrayList;

public class Program
{
	int 				id			= -1;
	String				name		= null;
	ArrayList<Shader> 	shaders 	= null;
	boolean				finalized	= false;
	
	Program() {}
	
	public int ID() { return id; }
	
	public String name() { return name; }
	
	public ArrayList<Shader> shaders() { return shaders; }
	
	public boolean isFinalized() { return finalized; }
	
	public boolean isValid() { return id >= 0; }
}
