package graphics;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import core.Window;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;

public interface Graphics
{
	boolean initialize();
	
	GraphicsContext createContext(Window window);
	
	GraphicsBuffer createBuffer();
	
	ShaderProgram createProgram(String name);
	
	Shader createShader(String name, ShaderType type, String data);
	
	GraphicsUniform getUniform(ShaderProgram program, String name);
	
	Texture createTexture();
	
	boolean setContextCurrent(GraphicsContext context);
	
	boolean createCapibilities();
	
	boolean setBufferData(GraphicsBuffer buffer, BufferType type, int[] data);
	
	boolean setBufferData(GraphicsBuffer buffer, BufferType type, float[] data);
	
	boolean setBufferData(GraphicsBuffer buffer, BufferType type, IntBuffer data);
	
	boolean setBufferData(GraphicsBuffer buffer, BufferType type, FloatBuffer data);
	
	boolean bindBuffer(GraphicsBuffer buffer);
	
	boolean unbindBuffer(GraphicsBuffer buffer);
	
	boolean freeBuffer(GraphicsBuffer buffer);
	
	boolean attachShader(ShaderProgram program, Shader shader);
	
	boolean freeShader(Shader shader);
	
	boolean finalizeProgram(ShaderProgram program);
	
	boolean bindProgram(ShaderProgram program);
	
	boolean unbindProgram();
	
	boolean freeProgram(ShaderProgram program);
	
	boolean setUniform(GraphicsUniform uniform, int data);
	
	boolean setUniform(GraphicsUniform uniform, float data);
	
	boolean setUniform(GraphicsUniform uniform, Vector2f data);
	
	boolean setUniform(GraphicsUniform uniform, Vector3f data);
	
	boolean setUniform(GraphicsUniform uniform, Vector4f data);
	
	boolean setUniform(GraphicsUniform uniform, Matrix4f data);
	
	boolean bindTexture(Texture texture);
	
	boolean unbindTexture();
	
	boolean setActiveTextureSlot(int slot);
	
	boolean setTextureData(Texture texture, byte[] data, int width, int height, 
			TextureFormat format, TextureClamp clamp, TextureFilter filter, boolean mipmap);
	
	boolean setTextureData(Texture texture, ByteBuffer data, int width, int height, 
			TextureFormat format, TextureClamp clamp, TextureFilter filter, boolean mipmap);
	
	boolean freeTexture(Texture texture);
	
	boolean setClearColor(float red, float green, float blue, float alpha);
	
	boolean clearBuffers();
	
	boolean swapBuffers(GraphicsContext context);

	boolean drawElementsRange(int iboOffset, int iboLength);
}
