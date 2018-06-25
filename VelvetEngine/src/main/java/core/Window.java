package core;

public class Window
{
	public static final int WINDOWED 		= 0x01;
	public static final int FULLSCREEN 		= 0x02;
	public static final int BORDERLESS 		= 0x04;
	public static final int CENTERED		= 0x08;
	public static final int NONRESIZEABLE 	= 0x10;
	public static final int VSYNC			= 0x20;	
	
	String 	title;
	int 	x, y;
	int 	width, height;
	int 	flags;
	long	windowLong;
	
	Window() {}

	public String getTitle() { return title; }

	public int getX() { return x; }

	public int getY() { return y; }

	public int getWidth() { return width; }

	public int getHeight() { return height; }

	public int getFlags() { return flags; }

	public long getWindowLong() { return windowLong; }
	
}
