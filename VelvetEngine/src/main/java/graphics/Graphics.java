package graphics;

import core.Window;

public interface Graphics
{
	boolean initGraphics();
	
	GraphicsContext createContext(Window window);
	
	boolean setContextCurrent(GraphicsContext context);
	
	boolean createCapibilities();
	
	GraphicsBuffer createBuffer(GraphicsBufferType type, int size);
	
	boolean setBufferData(GraphicsBuffer buffer, int[] data);
	
	boolean setBufferData(GraphicsBuffer buffer, float[] data);
	
	boolean bindBuffer(GraphicsBuffer buffer);
	
	boolean unbindBuffer(GraphicsBuffer buffer);
	
	boolean setClearColor(float red, float green, float blue, float alpha);
	
	boolean clearBuffers();
	
	boolean swapBuffers(GraphicsContext context);
}
