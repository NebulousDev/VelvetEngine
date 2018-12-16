package core;

import org.lwjgl.glfw.GLFW;

import math.Vector2f;

public class Window
{
	public static final int FULLSCREEN 			= 0x01;
	public static final int BORDERLESS 			= 0x02;
	public static final int CENTERED			= 0x04;
	public static final int NONRESIZEABLE 		= 0x08;
	public static final int VSYNC				= 0x10;
	public static final int USE_MONITOR_SIZE 	= 0x20;
	public static final int RESIZEABLE 			= 0x40;
	public static final int USE_MONITOR_BITS 	= 0x80;	
	
	String 	title;
	int 	x, y;
	int 	width, height;
	int 	flags;
	long	windowID;
	
	Vector2f center;
	
	Window() {}
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.center.x = width / 2;
		this.center.y = height / 2;
		GLFW.glfwSetWindowSize(windowID, width, height);
	}
	
	public void show()
	{
		GLFW.glfwShowWindow(windowID);
	}
	
	public void hide()
	{
		GLFW.glfwHideWindow(windowID);
	}
	
	public void moveCenter()
	{
		
	}

	public void reset()
	{
		//reset to creation flags
	}
	
	public String getTitle() { return title; }

	public int getX() { return x; }

	public int getY() { return y; }

	public int getWidth() { return width; }

	public int getHeight() { return height; }
	
	public float getAspect() { return (float)width / (float)height; }

	public int getFlags() { return flags; }

	public long getWindowID() { return windowID; }

	public Vector2f getCenter() { return center; }

}
