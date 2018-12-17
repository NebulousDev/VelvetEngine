package core;

import graphics.GLGraphics;
import graphics.Graphics;
import graphics.GraphicsAPI;
import input.Input;
import resource.AssetManager;
import velvet.ini.INIBuilder;
import velvet.ini.INIConfig;

public class Application
{
	private String 			name;
	private Window			window;
	private GraphicsAPI 	gfxApi;		//TODO: move to window?
	private INIConfig		config;
	private AssetManager	assetManager;
	private Game 			game;
	private boolean			initialized;
	private boolean			running;
	
	private boolean			useConfig;
	
	public Application(Game game)
	{
		this(null, game, null, true);
	}
	
	public Application(String name, Game game, GraphicsAPI api, boolean useConfig)
	{
		this.name = name;
		this.gfxApi = api;
		this.game = game;
		this.useConfig = useConfig;
	}
	
	private void initialize()
	{
		if(useConfig)
			config = createConfig();
		
		assetManager = new AssetManager();
		//assetManager.loadFolder("models");
		Graphics graphics = createGraphics();
		graphics.initialize();
		game.initialize(this, graphics);
		window = createWindow("Default Game", 640, 480, 12, 12, 10, 0, Window.CENTERED, true);
		initialized = true;
	}
	
	private INIConfig createConfig()
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
	
	private Graphics createGraphics()
	{
		if(useConfig)
		{
			this.name = config.getString("appName");
			
			switch (config.getInt("appGraphics"))
			{
				case GraphicsAPI.GRAPHICS_OPENGL_ID: 	gfxApi = GraphicsAPI.GRAPHICS_OPENGL; break;
				case GraphicsAPI.GRAPHICS_OPENGLES_ID: 	gfxApi = GraphicsAPI.GRAPHICS_OPENGLES; break;
				case GraphicsAPI.GRAPHICS_VULKAN_ID: 	gfxApi = GraphicsAPI.GRAPHICS_VULKAN; break;
				default: 								gfxApi = GraphicsAPI.GRAPHICS_OPENGL; break;
			}
		}
		
		Graphics graphics = null;
		
		switch(gfxApi)
		{
			case GRAPHICS_OPENGL: 	graphics = new GLGraphics(); break;
			case GRAPHICS_OPENGLES: graphics = new GLGraphics(); break;
			case GRAPHICS_VULKAN: 	graphics = new GLGraphics(); break;
		}
		
		return graphics;
	}
	
	private Window createWindow(String title, int width, int height, int x, int y, int bits, double hertz, int flags, boolean useConfig)
	{
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
		int windowFlags					= flags;
		
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
			
			windowFlags = 
					(windowFullscreen 		? Window.FULLSCREEN 		: 0) |
					(windowBorderless 		? Window.BORDERLESS 		: 0) |
					(windowCentered 		? Window.CENTERED 			: 0) |
					(windowResizeable 		? Window.RESIZEABLE 		: 0) |
					(windowVSync 			? Window.VSYNC				: 0) |
					(windowUseMonitorSize	? Window.USE_MONITOR_SIZE	: 0) |
					(windowUseMonitorBits	? Window.USE_MONITOR_BITS	: 0) ;
		}
		
		return new Window(windowTitle, windowWidth, windowHeight, windowPositionX, windowPositionY, windowBits, windowHertz, windowFlags);
	}
	
	public void start()
	{
		if(!initialized)
		{
			initialize();
			window.create(game.getGraphics());
		}

		if(!running)
		{
			window.show();
			game.start();
			running = true;
			run();
		}
	}
	
	private void run()
	{
		// TODO: possibly remove shouldClose() condition
		while(running && !window.shouldClose())
		{
			Input.getInstance().update();
			window.pollEvents();
			window.clear();
			
			game.update();
			
			window.swapBuffers();
		}
		
		window.hide();
		game.stop();
	}
	
	public void stop()
	{
		running = false;
	}
	
	public String getName() { return name; }

	public Window getWindow() { return window; }
	
	public GraphicsAPI getGraphicsApi() { return gfxApi; }

	public Game getGame() { return game; }

	public INIConfig getConfig() { return config; }

	public AssetManager getResourceManager() { return assetManager; }

}
