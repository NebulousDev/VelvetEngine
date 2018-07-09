package input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import core.Window;
import math.Vector2f;

public class Input
{
	public static final int MAX_BUTTONS	= 8;
	public static final int MAX_KEYS	= 512;
	
	static double[] mxPos = new double[1];
	static double[] myPos = new double[1];
	
	static Window window;
	
	static Vector2f mouseAbsolute;
	static Vector2f mouseRelative;
	
	static boolean[] buttons;
	static boolean[] buttonsLast;
	static boolean[] keys;
	static boolean[] keysLast;
	
	static boolean captured;
	
	private static GLFWMouseButtonCallback 	mouseButtonCallback;
	private static GLFWKeyCallback 			keyCallback;
	
	private static Input	instance;
	private static boolean 	initialized;
	
	private Input() {}
	
	public static Input getInstance()
	{
		if(instance == null)
			instance = new Input();
		
		return instance;
	}
	
	public Input initIntput(Window wind)
	{
		mouseAbsolute = new Vector2f(0.0f);
		mouseRelative = new Vector2f(0.0f);
		window = wind;
		
		buttons = new boolean[MAX_BUTTONS];
		buttonsLast = new boolean[MAX_BUTTONS];
		keys = new boolean[MAX_KEYS];
		keysLast = new boolean[MAX_KEYS];
		
		mouseButtonCallback = new GLFWMouseButtonCallback()
		{
			@Override
			public void invoke(long window, int button, int action, int mods)
			{
				Input.buttons[button] = action != GLFW.GLFW_RELEASE;
			}
		};
		
		keyCallback = new GLFWKeyCallback()
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods)
			{
				Input.keys[key] = action != GLFW.GLFW_RELEASE;
			}
		};
		
		GLFW.glfwSetMouseButtonCallback(window.getWindowLong(), mouseButtonCallback);
		GLFW.glfwSetKeyCallback(window.getWindowLong(), keyCallback);
		
		initialized = true;
		
		return getInstance();
	}
	
	public void freeInput()
	{
		mouseButtonCallback.free();
		keyCallback.free();
		
		initialized = false;
	}
	
	//TODO: make package
	public void update()
	{	
		System.arraycopy(buttons, 0, buttonsLast, 0, MAX_BUTTONS);
		System.arraycopy(keys, 0, keysLast, 0, MAX_KEYS);
		
		GLFW.glfwGetCursorPos(window.getWindowLong(), mxPos, myPos);
		mouseAbsolute.x = (float)mxPos[0];
		mouseAbsolute.y = (float)myPos[0];
		
		Vector2f center = window.getCenter();
		mouseRelative.x = mouseAbsolute.x - center.x;
		mouseRelative.y = mouseAbsolute.y - center.y;
		
		if(captured) setMouse(window.getCenter());
	}
	
	public static boolean buttonHeld(Buttons button)
	{
		return buttons[button.getId()];
	}
	
	public static boolean buttonPressed(Buttons button)
	{
		return buttons[button.getId()] && !buttonsLast[button.getId()];
	}
	
	public static boolean buttonRelease(Buttons button)
	{
		return !buttons[button.getId()] && buttonsLast[button.getId()];
	}
	
	public static boolean keyHeld(Keys key)
	{
		return keys[key.getId()];
	}
	
	public static boolean keyPressed(Keys key)
	{
		return keys[key.getId()] && !keysLast[key.getId()];
	}
	
	public static boolean keyRelease(Keys key)
	{
		return !keys[key.getId()] && keysLast[key.getId()];
	}
	
	public static void captureMouse(boolean visible)
	{
		captured = true;
		if(!visible) GLFW.glfwSetInputMode(window.getWindowLong(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	
	public static void releaseMouse()
	{
		captured = false;
		GLFW.glfwSetInputMode(window.getWindowLong(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
	}
	
	public static void setMouse(Vector2f pos)
	{
		GLFW.glfwSetCursorPos(window.getWindowLong(), pos.x, pos.y);
	}
	
	public static Vector2f getMouseAbsolute()
	{
		return mouseAbsolute;
	}
	
	public static Vector2f getMouseRelative()
	{
		
		return mouseRelative;
	}
	
	public static boolean isMouseCaptured()
	{
		return captured;
	}
	
	public static boolean isInitialized()
	{
		return initialized;
	}
	
}
