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
	
	public enum RenderBufferType
	{
		COLOR,
		DEPTH,
		STENCIL,
		DEPTH_STENCIL
	}
	
	public enum BufferType
	{
		VERTEX,
		ELEMENT,
		UNIFORM,
		GENERIC
	}
	
	public enum DrawMode
	{
		TRIANGLES,
		TRIANGLE_STRIPS,
		LINES,
		LINE_STRIPS,
		POINTS
	}
	
	boolean initialize();
	
	GraphicsContext createContext(Window window);
	
	GraphicsBuffer createBuffer();
	
	ShaderProgram createProgram(String name);
	
	Shader createShader(String name, ShaderType type, String data);
	
	Texture createTexture();

	FrameBuffer createFrameBuffer();
	
	RenderBuffer createRenderBuffer(RenderBufferType type, int index,
			TextureFormat format, int width, int height, int samples);
	
	boolean setContextCurrent(GraphicsContext context);
	
	boolean createCapibilities();
	
	boolean setBufferData(GraphicsBuffer buffer, BufferType type, int[] data);
	
	boolean setBufferData(GraphicsBuffer buffer, BufferType type, float[] data);
	
	boolean setBufferData(GraphicsBuffer buffer, BufferType type, IntBuffer data);
	
	boolean setBufferData(GraphicsBuffer buffer, BufferType type, FloatBuffer data);
	
	boolean setBufferSubData(GraphicsBuffer buffer, int offset, int[] data);
	
	boolean setBufferSubData(GraphicsBuffer buffer, int offset, float[] data);
	
	boolean setBufferSubData(GraphicsBuffer buffer, int offset, IntBuffer data);
	
	boolean setBufferSubData(GraphicsBuffer buffer, int offset, FloatBuffer data);
	
	boolean bindBuffer(GraphicsBuffer buffer);
	
	boolean unbindBuffer(GraphicsBuffer buffer);
	
	boolean freeBuffer(GraphicsBuffer buffer);
	
	boolean attachShader(ShaderProgram program, Shader shader);
	
	boolean freeShader(Shader shader);
	
	boolean finalizeProgram(ShaderProgram program);
	
	boolean bindProgram(ShaderProgram program);
	
	boolean unbindProgram();
	
	boolean freeProgram(ShaderProgram program);
	
	boolean setUniform(Uniform uniform, int data);
	
	boolean setUniform(Uniform uniform, float data);
	
	boolean setUniform(Uniform uniform, Vector2f data);
	
	boolean setUniform(Uniform uniform, Vector3f data);
	
	boolean setUniform(Uniform uniform, Vector4f data);
	
	boolean setUniform(Uniform uniform, Matrix4f data);
	
	Uniform getUniform(ShaderProgram program, String name);
	
	boolean bindTexture(Texture texture);
	
	boolean bindFrameBuffer(FrameBuffer buffer);
	
	boolean unbindFrameBuffer();
	
	boolean attachRenderBuffer(FrameBuffer frameBuffer, RenderBuffer renderBuffer);
	
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

	boolean drawElements(DrawMode mode, int count);
	
	boolean drawElementsRange(DrawMode mode, int iboOffset, int iboLength);

	boolean drawBuffers(RenderBuffer... buffers);
	
	boolean setViewport(int x, int y, int width, int height);

	boolean vsync(boolean vsync);
}
