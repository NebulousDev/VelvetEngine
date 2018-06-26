package graphics;

import core.Window;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;

public interface Graphics
{
	boolean initGraphics();
	
	GraphicsContext createContext(Window window);
	
	GraphicsBuffer createBuffer(BufferType type, int size);
	
	Program createProgram(String name);
	
	Shader createShader(String name, ShaderType type, String data);
	
	Uniform getUniform(Program program, String name);
	
	boolean setContextCurrent(GraphicsContext context);
	
	boolean createCapibilities();
	
	boolean setBufferData(GraphicsBuffer buffer, int[] data);
	
	boolean setBufferData(GraphicsBuffer buffer, float[] data);
	
	boolean bindBuffer(GraphicsBuffer buffer);
	
	boolean unbindBuffer(GraphicsBuffer buffer);
	
	boolean freeBuffer(GraphicsBuffer buffer);
	
	boolean attachShader(Program program, Shader shader);
	
	boolean freeShader(Shader shader);
	
	boolean finalizeProgram(Program program);
	
	boolean bindProgram(Program program);
	
	boolean unbindProgram();
	
	boolean freeProgram(Program program);
	
	boolean setUniform(Uniform uniform, int data);
	
	boolean setUniform(Uniform uniform, float data);
	
	boolean setUniform(Uniform uniform, Vector2f data);
	
	boolean setUniform(Uniform uniform, Vector3f data);
	
	boolean setUniform(Uniform uniform, Vector4f data);
	
	boolean setUniform(Uniform uniform, Matrix4f data);
	
	boolean setClearColor(float red, float green, float blue, float alpha);
	
	boolean clearBuffers();
	
	boolean swapBuffers(GraphicsContext context);
}
