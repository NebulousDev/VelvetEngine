package graphics;

public class Texture
{
	int 			id		= -1;
	int				width	= 0;
	int				height	= 0;
	int 			size	= 0;
	TextureFormat	format	= null;
	TextureClamp 	clamp	= null;
	TextureFilter 	filter	= null;
	
	public int ID() { return id; }
	
	public int width() { return width; }
	
	public int height() { return height; }
	
	public int size() { return size; }
	
	public TextureFormat format() { return format; }
	
	public TextureClamp clamp() { return clamp; }
	
	public TextureFilter filter() { return filter; }
	
	public boolean isValid() { return id >= 0; }
}
