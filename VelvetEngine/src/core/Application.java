package core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import graphics.Graphics;
import graphics.GraphicsAPI;
import graphics.GraphicsContext;
import graphics.GLGraphics;
import input.Input;
import math.Vector2f;

public class Application
{
	private String 			name;
	private Window			window;
	private GraphicsAPI 	gfxApi;
	private Graphics		graphics;
	private GraphicsContext context;
	private Input			input;
	
	private Game game;
	
	public Application(String name, Game game, GraphicsAPI api)
	{
		this.name = name;
		this.gfxApi = api;
		
		switch(api)
		{
			case GRAPHICS_OPENGL2:
				this.graphics = new GLGraphics(); break;
			
			case GRAPHICS_OPENGL:
				this.graphics = new GLGraphics(); break;
				
			case GRAPHICS_OPENGLES:
				this.graphics = new GLGraphics(); break;
			
			case GRAPHICS_VULKAN:
				this.graphics = new GLGraphics(); break;
		}
		
		this.graphics.initGraphics();
		this.window = createWindow("Default Game", 1920, 1080, 0, 0, Window.CENTERED);
		
		this.game = game;
		this.game.initialize(this);
	}
	
	private Window createWindow(String title, int width, int height, int x, int y, int flags)
	{
		Window window 	= new Window();
		window.title 	= title;
		window.width 	= width;
		window.height 	= height;
		window.flags	= flags;
		
		boolean fullscreen = false;
		
		GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		
		if((flags & Window.FULLSCREEN) == Window.FULLSCREEN)
		{
			GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, mode.redBits());
			GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, mode.greenBits());
			GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, mode.blueBits());
			GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, mode.refreshRate());
			
			fullscreen = true;
		}
		
		if((flags & Window.BORDERLESS) == Window.BORDERLESS)
		{
			//TODO: implement borderless windows
		}
		
		if((flags & Window.CENTERED) == Window.CENTERED)
		{
			int monitorWidth = mode.width();
			int monitorHeight = mode.height();
			
			x = (monitorWidth / 2) - (width / 2);
			y = (monitorHeight / 2) - (height / 2);
		}
		
		if((flags & Window.NONRESIZEABLE) == Window.NONRESIZEABLE)
		{
			GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		}
		
		if(fullscreen)
			window.windowID = GLFW.nglfwCreateWindow(width, height, 0, GLFW.glfwGetPrimaryMonitor(), 0);
		else
		{
			window.windowID = GLFW.glfwCreateWindow(width, height, title, 0, 0);
			GLFW.glfwSetWindowPos(window.windowID, x, y);
		}
		
		window.center = new Vector2f(0, 0);
		window.center.x = width / 2;
		window.center.y = height / 2;
		
		this.context = graphics.createContext(window);
		graphics.setContextCurrent(context);
		graphics.createCapibilities();
		
		GLFW.glfwShowWindow(window.windowID);
		
		//TODO: find a better place for this?
		input = Input.getInstance();
		input.initIntput(window);
		
		return window;
	}
	
	public void pollEvents()
	{
		GLFW.glfwPollEvents();
	}
	
	public void swapBuffers()
	{
		graphics.swapBuffers(context);
	}
	
	public boolean shouldClose()
	{
		return GLFW.glfwWindowShouldClose(context.getContext());
	}

	public String getName() { return name; }

	public Window getWindow() { return window; }
	
	public GraphicsAPI getGraphicsApi() { return gfxApi; }

	public Graphics getGraphics() { return graphics; }

	public GraphicsContext getContext() { return context; }
	
	public Input getInput() { return input; }

	public Game getGame() { return game; }

	public void start()
	{
		window.show();
		game.start();
	}
}
