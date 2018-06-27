package core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import graphics.GL3Graphics;
import graphics.GraphicsAPI;
import graphics.GraphicsContext;
import graphics.Graphics;

public class Application
{
	private String 			name;
	private GraphicsAPI 	gfxApi;
	private Graphics		graphics;
	private GraphicsContext context;
	private Window			window;
	
	private Application() {}
	
	public static Application createApplication(String name, GraphicsAPI api)
	{
		Application app = new Application();
		app.name = name;
		app.gfxApi = api;
		
		switch(api)
		{
			case GRAPHICS_OPENGL2:
				app.graphics = new GL3Graphics(); break;
			
			case GRAPHICS_OPENGL:
				app.graphics = new GL3Graphics(); break;
				
			case GRAPHICS_OPENGLES:
				app.graphics = new GL3Graphics(); break;
			
			case GRAPHICS_VULKAN:
				app.graphics = new GL3Graphics(); break;
		}
		
		app.graphics.initGraphics();
		
		return app;
	}
	
	public Window createWindow(String title, int width, int height, int x, int y, int flags)
	{
		if(window == null)
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
				window.windowLong = GLFW.nglfwCreateWindow(width, height, GLFW.glfwGetPrimaryMonitor(), 0, 0);
			else
			{
				window.windowLong = GLFW.glfwCreateWindow(width, height, title, 0, 0);
				GLFW.glfwSetWindowPos(window.windowLong, x, y);				
			}
			
			context = graphics.createContext(window);
			graphics.setContextCurrent(context);
			graphics.createCapibilities();
			
			GLFW.glfwShowWindow(window.windowLong);
			
			return window;
		}
		else
		{
			//TODO: Error, only one window instance allowed
			return null;
		}
		
	}
	
	public void update()
	{
		graphics.swapBuffers(context);
		GLFW.glfwPollEvents();
	}
	
	public boolean shouldClose()
	{
		return GLFW.glfwWindowShouldClose(context.getContext());
	}

	public String getName() { return name; }

	public GraphicsAPI getGraphicsApi() { return gfxApi; }

	public Graphics getGraphics() { return graphics; }

	public GraphicsContext getContext() { return context; }

	public Window getWindow() { return window; }
	
}
