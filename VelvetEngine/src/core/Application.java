package core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import graphics.GLGraphics;
import graphics.Graphics;
import graphics.GraphicsAPI;
import graphics.GraphicsContext;
import input.Input;
import math.Vector2f;
import velvet.ini.INIBuilder;
import velvet.ini.INIConfig;

public class Application
{
	private String 			name;
	private Window			window;
	private GraphicsAPI 	gfxApi;
	private Graphics		graphics;
	private GraphicsContext context;
	private Input			input;
	
	private INIConfig		config;
	
	private Game game;
	
	public Application(Game game)
	{
		this(null, game, null, true);
	}
	
	public Application(String name, Game game, GraphicsAPI api, boolean useConfig)
	{
		this.config = initConfig();

		this.name = name;
		this.gfxApi = api;
		
		if(useConfig)
		{
			this.name = config.getString("appName");
			
			switch (config.getInt("appGraphics"))
			{
				case GraphicsAPI.GRAPHICS_OPENGL_ID: 	this.gfxApi = GraphicsAPI.GRAPHICS_OPENGL; break;
				case GraphicsAPI.GRAPHICS_OPENGLES_ID: 	this.gfxApi = GraphicsAPI.GRAPHICS_OPENGLES; break;
				case GraphicsAPI.GRAPHICS_VULKAN_ID: 	this.gfxApi = GraphicsAPI.GRAPHICS_VULKAN; break;
				default: 								this.gfxApi = GraphicsAPI.GRAPHICS_OPENGL; break;
			}
		}
		
		switch(this.gfxApi)
		{
			case GRAPHICS_OPENGL: this.graphics = new GLGraphics(); break;
			case GRAPHICS_OPENGLES: this.graphics = new GLGraphics(); break;
			case GRAPHICS_VULKAN: this.graphics = new GLGraphics(); break;
		}

		this.graphics.initGraphics();
		this.window = createWindow("Default Game", 640, 480, 0, 0, 10, 0, Window.CENTERED, true);
		
		this.game = game;
		this.game.initialize(this);
	}
	
	private INIConfig initConfig()
	{
		INIConfig config = new INIBuilder()
		.addStringProperty("appName", Defaults.DEFAULT_APP_NAME)
		.addIntProperty("appGraphics", Defaults.DEFAULT_APP_GRAPHICS)
		.addStringProperty("windowTitle", Defaults.DEFAULT_WINDOW_TITLE)
		.addIntProperty("windowWidth", Defaults.DEFAULT_WINDOW_WIDTH)
		.addIntProperty("windowHeight", Defaults.DEFAULT_WINDOW_HEIGHT)
		.addBooleanProperty("windowFullscreen", Defaults.DEFAULT_WINDOW_CENTERED)
		.addBooleanProperty("windowUseMonitorSize", Defaults.DEFAULT_WINDOW_USE_MONITOR_SIZE)
		.addIntProperty("windowPositionX", Defaults.DEFAULT_WINDOW_POSITION_X)
		.addIntProperty("windowPositionY", Defaults.DEFAULT_WINDOW_POSITION_Y)
		.addBooleanProperty("windowCentered", Defaults.DEFAULT_WINDOW_CENTERED)
		.addBooleanProperty("windowBorderless", Defaults.DEFAULT_WINDOW_FULLSCREEN)
		.addBooleanProperty("windowResizable", Defaults.DEFAULT_WINDOW_RESIZABLE)
		.addDoubleProperty("windowHertz", Defaults.DEFAULT_WINDOW_HERTZ)
		.addBooleanProperty("windowVSync", Defaults.DEFAULT_WINDOW_VSYNC)
		.addIntProperty("windowBits", Defaults.DEFAULT_WINDOW_BITS)
		.addBooleanProperty("windowUseMonitorBits", Defaults.DEFAULT_WINDOW_USE_MONITOR_BITS)
		.build();
		
		config.load("config.ini");
		
		return config;
	}
	
	private Window createWindow(String title, int width, int height, int x, int y, int bits, int hertz, int flags, boolean useConfig)
	{
		Window window 	= new Window();
		window.title 	= title;
		window.width 	= width;
		window.height 	= height;
		window.flags	= flags;
		
		String windowTitle 				= title;
		int windowWidth					= width;
		int windowHeight				= height;
		boolean windowFullscreen 		= (flags & Window.FULLSCREEN) != 0;
		boolean windowUseMonitorSize	= (flags & Window.USE_MONITOR_SIZE) != 0;
		int	windowPositionX 			= x;
		int windowPositionY 			= y;
		boolean windowCentered 			= (flags & Window.CENTERED) != 0;
		boolean windowBorderless		= (flags & Window.BORDERLESS) != 0;
		boolean windowResizeable		= (flags & Window.RESIZEABLE) != 0;
		double windowHertz				= hertz;
		boolean windowVSync				= (flags & Window.VSYNC) != 0;
		int windowBits					= bits;
		boolean windowUseMonitorBits	= (flags & Window.USE_MONITOR_BITS) != 0;
		
		if(useConfig)
		{
			windowTitle = config.getString("windowTitle");
			windowWidth = config.getInt("windowWidth");
			windowHeight = config.getInt("windowHeight");
			windowFullscreen = config.getBoolean("windowFullscreen");
			windowUseMonitorSize = config.getBoolean("windowUseMonitorSize");
			windowPositionX = config.getInt("windowPositionX");
			windowPositionY = config.getInt("windowPositionY");
			windowCentered = config.getBoolean("windowCentered");
			windowBorderless = config.getBoolean("windowBorderless");
			windowResizeable = config.getBoolean("windowResizable");
			windowHertz = config.getDouble("windowHertz");
			windowVSync = config.getBoolean("windowVSync");
			windowBits = config.getInt("windowBits");
			windowUseMonitorBits = config.getBoolean("windowUseMonitorBits");
		}
		
		// TODO: set values in window class
		window.center = new Vector2f(windowWidth / 2, windowHeight / 2);
		
		GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		
		if(windowUseMonitorSize)
		{
			windowWidth = mode.width();
			windowHeight = mode.height();
		}
		
		if(windowBorderless)
		{
			GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
		}
		
		if(windowCentered)
		{
			windowPositionX = (int) (window.center.x + 0.5f);
			windowPositionY = (int) (window.center.y + 0.5f);
		}
		
		if(windowResizeable)
		{
			GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		}
		
		if(windowFullscreen)
		{
			if(windowUseMonitorBits)
			{
				GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, mode.redBits());
				GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, mode.greenBits());
				GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, mode.blueBits());
			}
			
			else
			{
				GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, windowBits);
				GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, windowBits);
				GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, windowBits);
			}
			
			if(windowHertz > 0 && !windowVSync)
				GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, (int)(windowHertz + 0.5));
			else
				GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, mode.refreshRate());
			
			window.windowID = GLFW.glfwCreateWindow(windowWidth, windowHeight, windowTitle, GLFW.glfwGetPrimaryMonitor(), 0);
		}
		
		else
		{
			window.windowID = GLFW.glfwCreateWindow(windowWidth, windowHeight, windowTitle, 0, 0);
			GLFW.glfwSetWindowPos(window.windowID, windowPositionX, windowPositionY);
		}
		
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

	public INIConfig getConfig() { return config; }
	
	public void start()
	{
		window.show();
		game.start();
	}
}
