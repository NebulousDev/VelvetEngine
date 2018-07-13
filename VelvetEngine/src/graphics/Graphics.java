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
	boolean initGraphics();
	
	GraphicsContext createContext(Window window);
	
	GraphicsBuffer createBuffer();
	
	GraphicsProgram createProgram(String name);
	
	GraphicsShader createShader(String name, ShaderType type, String data);
	
	GraphicsUniform getUniform(GraphicsProgram program, String name);
	
	GraphicsTexture createTexture();
	
	boolean setContextCurrent(GraphicsContext context);
	
	boolean createCapibilities();
	
	boolean setBufferData(GraphicsBuffer buffer, BufferType type, int[] data);
	
	boolean setBufferData(GraphicsBuffer buffer, BufferType type, float[] data);
	
	boolean setBufferData(GraphicsBuffer buffer, BufferType type, IntBuffer data);
	
	boolean setBufferData(GraphicsBuffer buffer, BufferType type, FloatBuffer data);
	
	boolean bindBuffer(GraphicsBuffer buffer);
	
	boolean unbindBuffer(GraphicsBuffer buffer);
	
	boolean freeBuffer(GraphicsBuffer buffer);
	
	boolean attachShader(GraphicsProgram program, GraphicsShader shader);
	
	boolean freeShader(GraphicsShader shader);
	
	boolean finalizeProgram(GraphicsProgram program);
	
	boolean bindProgram(GraphicsProgram program);
	
	boolean unbindProgram();
	
	boolean freeProgram(GraphicsProgram program);
	
	boolean setUniform(GraphicsUniform uniform, int data);
	
	boolean setUniform(GraphicsUniform uniform, float data);
	
	boolean setUniform(GraphicsUniform uniform, Vector2f data);
	
	boolean setUniform(GraphicsUniform uniform, Vector3f data);
	
	boolean setUniform(GraphicsUniform uniform, Vector4f data);
	
	boolean setUniform(GraphicsUniform uniform, Matrix4f data);
	
	boolean bindTexture(GraphicsTexture texture);
	
	boolean unbindTexture();
	
	boolean setActiveTextureSlot(int slot);
	
	boolean setTextureData(GraphicsTexture texture, byte[] data, int width, int height, 
			TextureFormat format, TextureClamp clamp, TextureFilter filter, boolean mipmap);
	
	boolean setTextureData(GraphicsTexture texture, ByteBuffer data, int width, int height, 
			TextureFormat format, TextureClamp clamp, TextureFilter filter, boolean mipmap);
	
	boolean freeTexture(GraphicsTexture texture);
	
	boolean setClearColor(float red, float green, float blue, float alpha);
	
	boolean clearBuffers();
	
	boolean swapBuffers(GraphicsContext context);

	boolean drawElementsRange(int iboOffset, int iboLength);
}
