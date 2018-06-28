package core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import math.Vector2f;

public class Input
{
	public static final int MAX_BUTTONS	= 8;
	public static final int MAX_KEYS	= 512;
	
	static double[] mxPos = new double[1];
	static double[] myPos = new double[1];
	
	Window window;
	
	Vector2f mouseAbsolute;
	Vector2f mouseRelative;
	
	boolean[] buttons;
	boolean[] buttonsLast;
	boolean[] keys;
	boolean[] keysLast;
	
	boolean captured;
	
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWKeyCallback 		keyCallback;
	
	private Input() {}
	
	static Input createIntput(Window window)
	{
		Input input = new Input();
		input.mouseAbsolute = new Vector2f(0.0f);
		input.mouseRelative = new Vector2f(0.0f);
		input.window = window;
		
		input.buttons = new boolean[MAX_BUTTONS];
		input.buttonsLast = new boolean[MAX_BUTTONS];
		input.keys = new boolean[MAX_KEYS];
		input.keysLast = new boolean[MAX_KEYS];
		
		input.mouseButtonCallback = new GLFWMouseButtonCallback()
		{
			@Override
			public void invoke(long window, int button, int action, int mods)
			{
				input.buttons[button] = action != GLFW.GLFW_RELEASE;
			}
		};
		
		input.keyCallback = new GLFWKeyCallback()
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods)
			{
				input.keys[key] = action != GLFW.GLFW_RELEASE;
			}
		};
		
		GLFW.glfwSetMouseButtonCallback(window.windowLong, input.mouseButtonCallback);
		GLFW.glfwSetKeyCallback(window.windowLong, input.keyCallback);
		
		return input;
	}
	
	void freeInput()
	{
		mouseButtonCallback.free();
		keyCallback.free();
	}
	
	//TODO: make package
	public void update()
	{	
		System.arraycopy(buttons, 0, buttonsLast, 0, MAX_BUTTONS);
		System.arraycopy(keys, 0, keysLast, 0, MAX_KEYS);
		
		GLFW.glfwGetCursorPos(window.windowLong, mxPos, myPos);
		mouseAbsolute.x = (float)mxPos[0];
		mouseAbsolute.y = (float)myPos[0];
		
		Vector2f center = window.getCenter();
		mouseRelative.x = mouseAbsolute.x - center.x;
		mouseRelative.y = mouseAbsolute.y - center.y;
		
		if(captured) setMouse(window.getCenter());
	}
	
	public boolean buttonHeld(Buttons button)
	{
		return buttons[button.getId()];
	}
	
	public boolean buttonPressed(Buttons button)
	{
		return buttons[button.getId()] && !buttonsLast[button.getId()];
	}
	
	public boolean buttonRelease(Buttons button)
	{
		return !buttons[button.getId()] && buttonsLast[button.getId()];
	}
	
	public boolean keyHeld(Keys key)
	{
		return keys[key.getId()];
	}
	
	public boolean keyPressed(Keys key)
	{
		return keys[key.getId()] && !keysLast[key.getId()];
	}
	
	public boolean keyRelease(Keys key)
	{
		return !keys[key.getId()] && keysLast[key.getId()];
	}
	
	public void captureMouse(boolean visible)
	{
		captured = true;
		if(!visible) GLFW.glfwSetInputMode(window.windowLong, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	
	public void releaseMouse()
	{
		captured = false;
		GLFW.glfwSetInputMode(window.windowLong, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
	}
	
	public void setMouse(Vector2f pos)
	{
		GLFW.glfwSetCursorPos(window.windowLong, pos.x, pos.y);
	}
	
	public Vector2f getMouseAbsolute()
	{
		return mouseAbsolute;
	}
	
	public Vector2f getMouseRelative()
	{
		
		return mouseRelative;
	}
	
	public boolean isMouseCaptured()
	{
		return captured;
	}
	
}
