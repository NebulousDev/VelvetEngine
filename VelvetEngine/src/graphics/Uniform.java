package graphics;

public class Uniform
{
	int 	id		= -1;
	String 	name	= null;
	
	Uniform() {}
	
	public int ID() { return id; }
	
	public String name() { return name; }
	
	public boolean isValid() { return id >= 0; }
}
