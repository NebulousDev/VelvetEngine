package core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import graphics.Graphics;
import graphics.GraphicsContext;
import input.Input;
import math.Vector2i;

public class Window
{
	public static final int FULLSCREEN 			= 0x01;
	public static final int BORDERLESS 			= 0x02;
	public static final int CENTERED			= 0x04;
	public static final int RESIZEABLE 			= 0x08;
	public static final int VSYNC				= 0x10;
	public static final int USE_MONITOR_SIZE 	= 0x20;
	public static final int USE_MONITOR_BITS 	= 0x40;	
	
	protected String 			title;
	protected int 				width, height;
	protected int 				x, y;
	protected int				bits;
	protected double			hertz;
	protected int 				flags;
	protected long				windowID;
	
	protected Vector2i 			center;
	
	private Graphics			graphics;
	private GraphicsContext 	context;
	private Input				input;
	
	public Window(String title, int width, int height, int x, int y, int bits, double hertz, int flags)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.bits = bits;
		this.hertz = hertz;
		this.flags = flags;

		this.center = new Vector2i(width / 2, height / 2);
		
		GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		
		if((flags & USE_MONITOR_SIZE) != 0)
		{
			this.width = mode.width();
			this.height = mode.height();
		}
		
		if((flags & BORDERLESS) != 0)
		{
			GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
		}
		
		if((flags & CENTERED) != 0)
		{
			this.x = mode.width() / 2 - center.x;
			this.y = mode.height() / 2 - center.y;
		}
		
		if((flags & RESIZEABLE) != 0)
		{
			GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		}
		
		if((flags & FULLSCREEN) != 0)
		{
			if((flags & USE_MONITOR_BITS) != 0)
			{
				GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, mode.redBits());
				GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, mode.greenBits());
				GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, mode.blueBits());
			}
			
			else
			{
				GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, bits);
				GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, bits);
				GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, bits);
			}
			
			if(hertz > 0 && (flags & FULLSCREEN) == 0)
				GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, (int)(hertz + 0.5));
			else
				GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, mode.refreshRate());
		}
		
		if((flags & VSYNC) != 0)
		{
			GLFW.glfwSwapInterval(1);
		}
		
		//TODO: allow multiple Input instances
		input = Input.getInstance();
	}
	
	public void create(Graphics graphics)
	{
		if((flags & FULLSCREEN) != 0)
		{
			windowID = GLFW.glfwCreateWindow(width, height, title, GLFW.glfwGetPrimaryMonitor(), 0);
		}
		else
		{
			windowID = GLFW.glfwCreateWindow(width, height, title, 0, 0);
			GLFW.glfwSetWindowPos(windowID, x, y);
		}
		
		this.graphics = graphics;
		this.context = graphics.createContext(this);
		graphics.setContextCurrent(context);
		graphics.createCapibilities(); //TODO: add check if capibilities is already created

		input.initIntput(this);
		
		GLFW.glfwShowWindow(windowID);
	}
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.center.x = width / 2;
		this.center.y = height / 2;
		GLFW.glfwSetWindowSize(windowID, width, height);
	}
	
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
		GLFW.glfwSetWindowPos(windowID, x, y);
	}
	
	public void show()
	{
		GLFW.glfwShowWindow(windowID);
	}
	
	public void hide()
	{
		GLFW.glfwHideWindow(windowID);
	}
	
	public void setCenter()
	{
		setPosition(center.x, center.y);
	}
	
	public void swapBuffers()
	{
		graphics.swapBuffers(context);
	}
	
	public boolean shouldClose()
	{
		return GLFW.glfwWindowShouldClose(context.getContext());
	}
	
	public void pollEvents()
	{
		GLFW.glfwPollEvents();
	}
	
	public void clear()
	{
		graphics.clearBuffers();
	}
	
	public String getTitle() { return title; }

	public int getX() { return x; }

	public int getY() { return y; }

	public int getWidth() { return width; }

	public int getHeight() { return height; }
	
	public float getAspect() { return (float)width / (float)height; }

	public int getFlags() { return flags; }

	public long getWindowID() { return windowID; }

	public Vector2i getCenter() { return center; }

}
