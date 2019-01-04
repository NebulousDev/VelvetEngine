package graphics;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GLCapabilities;

import core.Window;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;

public class GLGraphics implements Graphics
{
	private int graphicsTypeToInt(BufferType type)
	{
		switch (type)
		{
			case VERTEX:
				return GL15.GL_ARRAY_BUFFER;
				
			case ELEMENT:
				return GL15.GL_ELEMENT_ARRAY_BUFFER;

			default:
				return -1;
		}
	}
	
	private int drawModeToInt(DrawMode mode)
	{
		switch(mode)
		{
			case TRIANGLES:			return GL11.GL_TRIANGLES;
			case TRIANGLE_STRIPS:	return GL11.GL_TRIANGLE_STRIP;
			case LINES:				return GL11.GL_LINE;
			case LINE_STRIPS:		return GL11.GL_LINE_STRIP;
			case POINTS:			return GL11.GL_POINT;
			default: return -1;
		}
	}
	
	private int shaderTypeToInt(ShaderType type)
	{
		switch (type)
		{
			case SHADER_TYPE_VERTEX:
				return GL20.GL_VERTEX_SHADER;
				
			case SHADER_TYPE_FRAGMENT:
				return GL20.GL_FRAGMENT_SHADER;
				
			case SHADER_TYPE_GEOMETRY:
				return GL32.GL_GEOMETRY_SHADER;
	
			default:
				return -1;
		}
	}
	
	private int textureClampToInt(TextureClamp clamp)
	{
		switch (clamp) {
		
			case TEXTUTE_CLAMP_REPEAT:		return GL11.GL_REPEAT;
			case TEXTURE_CLAMP_MIRRORED:	return GL14.GL_MIRRORED_REPEAT;
			case TEXTURE_CLAMP_EDGE:		return GL12.GL_CLAMP_TO_EDGE;
			case TEXTURE_CLAMP_BORDER:		return GL13.GL_CLAMP_TO_BORDER;
	
			default:
				return -1;
		}
	}
	
	private int textureFilterToInt(TextureFilter filter)
	{
		switch(filter)
		{
		
			case TEXTURE_FILTER_NEAREST:					return GL11.GL_NEAREST;
			case TEXTURE_FILTER_LINEAR:						return GL11.GL_LINEAR;
			case TEXTURE_FILTER_NEAREST_MIPMAP_NEAREST:		return GL11.GL_NEAREST_MIPMAP_NEAREST;
			case TEXTURE_FILTER_NEAREST_MIPMAP_LINEAR:		return GL11.GL_NEAREST_MIPMAP_LINEAR;
			case TEXTURE_FILTER_LINEAR_MIPMAP_NEAREST:		return GL11.GL_LINEAR_MIPMAP_NEAREST;
			case TEXTURE_FILTER_LINEAR_MIPMAP_LINEAR:		return GL11.GL_LINEAR_MIPMAP_LINEAR;
				
			default:
				return -1;
		}
	}
	
	private int textureFormatToInt(TextureFormat format)
	{
		switch (format) {

			case TEXTURE_FORMAT_R:					return GL11.GL_RED;
			case TEXTURE_FORMAT_RG:					return GL30.GL_RG;
			case TEXTURE_FORMAT_RGB:				return GL11.GL_RGB;
			case TEXTURE_FORMAT_RGBA:				return GL11.GL_RGBA;
			case TEXTURE_FORMAT_FLOAT_R:			return GL30.GL_RED;
			case TEXTURE_FORMAT_FLOAT_RG:			return GL30.GL_RG;
			case TEXTURE_FORMAT_FLOAT_RGB:			return GL30.GL_RGB;
			case TEXTURE_FORMAT_FLOAT_RGBA:			return GL30.GL_RGBA;
			case TEXTURE_FORMAT_DEPTH:				return GL11.GL_DEPTH_COMPONENT;
			case TEXTURE_FORMAT_DEPTH16:			return GL11.GL_DEPTH_COMPONENT;
			case TEXTURE_FORMAT_DEPTH24:			return GL11.GL_DEPTH_COMPONENT;
			case TEXTURE_FORMAT_DEPTH32:			return GL11.GL_DEPTH_COMPONENT;
			case TEXTURE_FORMAT_FLOAT_DEPTH32:		return GL11.GL_DEPTH_COMPONENT;
			case TEXTURE_FORMAT_DEPTH_STENCIL:		return GL30.GL_DEPTH_STENCIL;
				
			default:
				return -1;
		}
	}
	
	private int textureFormatToIntInternal(TextureFormat format)
	{
		switch (format) {

			case TEXTURE_FORMAT_R:					return GL30.GL_R8;
			case TEXTURE_FORMAT_RG:					return GL30.GL_RG8;
			case TEXTURE_FORMAT_RGB:				return GL11.GL_RGB8;
			case TEXTURE_FORMAT_RGBA:				return GL11.GL_RGBA8;
			case TEXTURE_FORMAT_FLOAT_R:			return GL30.GL_R16F;
			case TEXTURE_FORMAT_FLOAT_RG:			return GL30.GL_RG16F;
			case TEXTURE_FORMAT_FLOAT_RGB:			return GL30.GL_RGB16F;
			case TEXTURE_FORMAT_FLOAT_RGBA:			return GL30.GL_RGBA16F;
			case TEXTURE_FORMAT_DEPTH:				return GL11.GL_DEPTH_COMPONENT;
			case TEXTURE_FORMAT_DEPTH16:			return GL14.GL_DEPTH_COMPONENT16;
			case TEXTURE_FORMAT_DEPTH24:			return GL14.GL_DEPTH_COMPONENT24;
			case TEXTURE_FORMAT_DEPTH32:			return GL14.GL_DEPTH_COMPONENT32;
			case TEXTURE_FORMAT_FLOAT_DEPTH32:		return GL30.GL_DEPTH_COMPONENT32F;
			case TEXTURE_FORMAT_DEPTH_STENCIL:		return GL30.GL_DEPTH24_STENCIL8;
				
			default:
				return -1;
		}
	}
	
	private int textureFormatToPrimitiveType(TextureFormat format)
	{
		switch (format) {

			case TEXTURE_FORMAT_R:					return GL11.GL_UNSIGNED_INT;
			case TEXTURE_FORMAT_RG:					return GL11.GL_UNSIGNED_INT;
			case TEXTURE_FORMAT_RGB:				return GL11.GL_UNSIGNED_INT;
			case TEXTURE_FORMAT_RGBA:				return GL11.GL_UNSIGNED_INT;
			case TEXTURE_FORMAT_FLOAT_R:			return GL11.GL_FLOAT;
			case TEXTURE_FORMAT_FLOAT_RG:			return GL11.GL_FLOAT;
			case TEXTURE_FORMAT_FLOAT_RGB:			return GL11.GL_FLOAT;
			case TEXTURE_FORMAT_FLOAT_RGBA:			return GL11.GL_FLOAT;
			case TEXTURE_FORMAT_DEPTH:				return GL11.GL_UNSIGNED_INT;
			case TEXTURE_FORMAT_DEPTH16:			return GL11.GL_UNSIGNED_INT;
			case TEXTURE_FORMAT_DEPTH24:			return GL11.GL_UNSIGNED_INT;
			case TEXTURE_FORMAT_DEPTH32:			return GL11.GL_UNSIGNED_INT;
			case TEXTURE_FORMAT_FLOAT_DEPTH32:		return GL11.GL_FLOAT;
			case TEXTURE_FORMAT_DEPTH_STENCIL:		return GL30.GL_UNSIGNED_INT_24_8;
				
			default:
				return -1;
		}
	}
	
	private int renderBufferTypeToInt(RenderBufferType type)
	{
		switch (type) {

			case COLOR: 		return GL30.GL_COLOR_ATTACHMENT0;
			case DEPTH: 		return GL30.GL_DEPTH_ATTACHMENT;
			case STENCIL: 		return GL30.GL_STENCIL_ATTACHMENT;
			case DEPTH_STENCIL: return GL30.GL_DEPTH_STENCIL_ATTACHMENT;
				
			default:
				return -1;
		}
	}
	
	@Override
	public boolean initialize()
	{
		boolean init = GLFW.glfwInit();
		if(init)return true;
		else return false;
	}
	
	@Override
	public GraphicsContext createContext(Window window)
	{
		GraphicsContext context = new GraphicsContext(window.getWindowID());
		
		if(context.isValid()) return context;
		return null;
	}
	
	@Override
	public boolean setContextCurrent(GraphicsContext context)
	{
		GLFW.glfwMakeContextCurrent(context.getContext());
		return true;
	}

	@Override
	public boolean createCapibilities()
	{
		GLCapabilities capibilities = GL.createCapabilities();
		return capibilities != null;
	}
	
	@Override
	public GraphicsBuffer createBuffer()
	{
		GraphicsBuffer buffer = new GraphicsBuffer();
		buffer.id = GL15.glGenBuffers();
		
		if(buffer.isValid()) return buffer;
		else return null;
	}
	
	@Override
	public ShaderProgram createProgram(String name)
	{
		ShaderProgram program = new ShaderProgram();
		program.name = name;
		program.shaders = new ArrayList<Shader>();
		
		program.id = GL20.glCreateProgram();
		
		if(program.isValid()) return program;
		else return null;
	}

	@Override
	public Shader createShader(String name, ShaderType type, String data)
	{
		Shader shader = new Shader();
		shader.id = GL20.glCreateShader(shaderTypeToInt(type));
		shader.name = name;
		shader.type = type;
		
		if(shader.isValid())
		{
			GL20.glShaderSource(shader.id, data);
			GL20.glCompileShader(shader.id);
			return shader;
		}
		
		return null;
	}
	
	@Override
	public Texture createTexture()
	{
		Texture texture = new Texture();
		texture.id = GL11.glGenTextures();
		
		if(texture.isValid()) return texture;
		else return null;
	}

	@Override
	public Uniform getUniform(ShaderProgram program, String name)
	{
		Uniform uniform = new Uniform();
		uniform.name = name;
		uniform.id = GL20.glGetUniformLocation(program.id, name);
		
		if(uniform.isValid()) return uniform;
		else return null;
	}
	
	@Override
	public boolean setBufferData(GraphicsBuffer buffer, BufferType type, int[] data)
	{
		if(buffer.isValid())
		{
			int bufferType = graphicsTypeToInt(type);
			GL15.glBindBuffer(bufferType, buffer.id);
			GL15.glBufferData(bufferType, data, GL15.GL_STATIC_DRAW);
			buffer.size = data.length;
			buffer.type = type;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean setBufferData(GraphicsBuffer buffer, BufferType type, float[] data)
	{
		if(buffer.isValid())
		{
			int bufferType = graphicsTypeToInt(type);
			GL15.glBindBuffer(bufferType, buffer.id);
			GL15.glBufferData(graphicsTypeToInt(type), data, GL15.GL_STATIC_DRAW);
			buffer.size = data.length;
			buffer.type = type;
			return true;
		}
		
		return false;
	}
	
	//NOTE: setBufferData with IntBuffer does not currently work!
	
	@Override
	public boolean setBufferData(GraphicsBuffer buffer, BufferType type, IntBuffer data)
	{
		if(buffer.isValid())
		{
			data.flip();
			int bufferType = graphicsTypeToInt(type);
			GL15.glBindBuffer(bufferType, buffer.id);
			GL15.glBufferData(graphicsTypeToInt(type), data, GL15.GL_STATIC_DRAW);
			buffer.size = data.remaining();
			buffer.type = type;
			return true;
		}
		
		return false;
	}
	
	//NOTE: setBufferData with FloatBuffer does not currently work!

	@Override
	public boolean setBufferData(GraphicsBuffer buffer, BufferType type, FloatBuffer data)
	{
		if(buffer.isValid())
		{
			data.flip();
			int bufferType = graphicsTypeToInt(type);
			GL15.glBindBuffer(bufferType, buffer.id);
			GL15.glBufferData(graphicsTypeToInt(type), data, GL15.GL_STATIC_DRAW);
			buffer.size = data.remaining();
			buffer.type = type;
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean setBufferSubData(GraphicsBuffer buffer, int offset, int[] data)
	{
		if(buffer.isValid())
		{
			int bufferType = graphicsTypeToInt(buffer.type);
			GL15.glBindBuffer(bufferType, buffer.id);
			GL20.glBufferSubData(bufferType, offset, data);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean setBufferSubData(GraphicsBuffer buffer, int offset, float[] data)
	{
		if(buffer.isValid())
		{
			int bufferType = graphicsTypeToInt(buffer.type);
			GL15.glBindBuffer(bufferType, buffer.id);
			GL20.glBufferSubData(bufferType, offset, data);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean setBufferSubData(GraphicsBuffer buffer, int offset, IntBuffer data)
	{
		if(buffer.isValid())
		{
			data.flip();
			int bufferType = graphicsTypeToInt(buffer.type);
			GL15.glBindBuffer(bufferType, buffer.id);
			GL20.glBufferSubData(bufferType, offset, data);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean setBufferSubData(GraphicsBuffer buffer, int offset, FloatBuffer data)
	{
		if(buffer.isValid())
		{
			data.flip();
			int bufferType = graphicsTypeToInt(buffer.type);
			GL15.glBindBuffer(bufferType, buffer.id);
			GL20.glBufferSubData(bufferType, offset, data);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean bindBuffer(GraphicsBuffer buffer)
	{
		if(buffer.isValid())
		{
			GL15.glBindBuffer(graphicsTypeToInt(buffer.type), buffer.id);
			return true;
		}
		
		System.out.println("Failed to bind buffer!");
		
		return false;
	}

	@Override
	public boolean unbindBuffer(GraphicsBuffer buffer)
	{
		if(buffer.isValid())
		{
			GL15.glBindBuffer(graphicsTypeToInt(buffer.type), 0);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean freeBuffer(GraphicsBuffer buffer)
	{
		if(buffer.isValid())
		{
			GL15.glDeleteBuffers(buffer.id);
			buffer.id = -1;
			buffer.size = 0;
			buffer.type = null;
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean attachShader(ShaderProgram program, Shader shader)
	{
		if(program.isValid() && shader.isValid())
		{
			GL20.glAttachShader(program.id, shader.id);
			program.shaders.add(shader);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean freeShader(Shader shader)
	{
		if(shader.isValid())
		{
			GL20.glDeleteShader(shader.id);
			shader.id = -1;
			shader.type = null;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean finalizeProgram(ShaderProgram program)
	{
		if(program.isValid() && !program.isFinalized())
		{
			GL20.glLinkProgram(program.id);
			
			int success = GL20.glGetProgrami(program.id, GL20.GL_LINK_STATUS);
			if(success == GL11.GL_FALSE)
			{
				int length = GL20.glGetProgrami(program.id, GL20.GL_INFO_LOG_LENGTH);
				String error = GL20.glGetProgramInfoLog(program.id, length);
				System.out.println(error);
			}
			
			for(Shader shader : program.shaders)
			{
				GL20.glDetachShader(program.id, shader.id);
				GL20.glDeleteShader(shader.id);
				shader.id = -1;
			}
			
			program.finalized = true;
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean bindProgram(ShaderProgram program)
	{
		if(program.isValid())
		{
			GL20.glUseProgram(program.id);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean unbindProgram() {
		GL20.glUseProgram(0);
		return true;
	}
	
	@Override
	public boolean freeProgram(ShaderProgram program)
	{
		if(program.isValid())
		{
			GL20.glDeleteProgram(program.id);
			program.id = -1;
			program.finalized = false;
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean setUniform(Uniform uniform, int data)
	{
		if(uniform != null && uniform.isValid())
		{
			GL20.glUniform1i(uniform.id, data);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean setUniform(Uniform uniform, float data)
	{
		if(uniform != null && uniform.isValid())
		{
			GL20.glUniform1f(uniform.id, data);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean setUniform(Uniform uniform, Vector2f data)
	{
		if(uniform != null && uniform.isValid())
		{
			GL20.glUniform2f(uniform.id, data.x, data.y);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean setUniform(Uniform uniform, Vector3f data)
	{
		if(uniform != null && uniform.isValid())
		{
			GL20.glUniform3f(uniform.id, data.x, data.y, data.z);
			return true;
		}

		return false;
	}

	@Override
	public boolean setUniform(Uniform uniform, Vector4f data)
	{
		if(uniform != null && uniform.isValid())
		{
			GL20.glUniform4f(uniform.id, data.x, data.y, data.z, data.w);
			return true;
		}

		return false;
	}

	@Override
	public boolean setUniform(Uniform uniform, Matrix4f data)
	{
		if(uniform != null && uniform.isValid())
		{
			GL20.glUniformMatrix4fv(uniform.id, false, data.toFloatBuffer(true));
			return true;
		}
		
		return false;
	}

	@Override
	public boolean setClearColor(float red, float green, float blue, float alpha)
	{
		GL11.glClearColor(red, green, blue, alpha);
		return true;
	}

	@Override
	public boolean clearBuffers()
	{
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
		return true;
	}

	@Override
	public boolean swapBuffers(GraphicsContext context)
	{
		GLFW.glfwSwapBuffers(context.getContext());
		return true;
	}

	@Override
	public boolean drawElements(DrawMode mode, int count)
	{
		GL11.glDrawElements(drawModeToInt(mode), count, GL11.GL_UNSIGNED_INT, 0); 
		return true;
	}
	
	@Override
	public boolean drawElementsRange(DrawMode mode, int start, int count)
	{
		GL11.glDrawElements(drawModeToInt(mode), count, GL11.GL_UNSIGNED_INT, start * Integer.BYTES); 
		return true;
	}
	
	@Override
	public boolean drawBuffers(RenderBuffer... buffers)
	{
		int[] elements = new int[buffers.length];
		
		for(int i = 0; i < buffers.length; i++)
			elements[i] = renderBufferTypeToInt(buffers[i].type) + buffers[i].index;
		
		GL30.glDrawBuffers(elements);
		
		return true;
	}

	@Override
	public boolean setTextureData(Texture texture, byte[] data, int width, int height,
			TextureFormat format, TextureClamp clamp, TextureFilter filter, boolean mipmap)
	{
		if(texture.isValid())
		{
			ByteBuffer buffer = ByteBuffer.allocate(data.length);
			buffer.put(data);
			buffer.flip();
			
			bindTexture(texture);
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, textureClampToInt(clamp));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, textureClampToInt(clamp));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, textureFilterToInt(filter));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, textureFilterToInt(filter));
			if(mipmap) GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, textureFormatToIntInternal(format), 
					width, height, 0, textureFormatToInt(format), GL11.GL_UNSIGNED_BYTE, buffer);
			
			unbindTexture();
			
			texture.width 	= width;
			texture.height	= height;
			texture.size 	= buffer.limit();
			texture.format 	= format;
			texture.clamp 	= clamp;
			texture.filter 	= filter;
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean setTextureData(Texture texture, ByteBuffer data, int width, int height, 
			TextureFormat format, TextureClamp clamp, TextureFilter filter, boolean mipmap)
	{
		if(texture.isValid())
		{
			bindTexture(texture);
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, textureClampToInt(clamp));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, textureClampToInt(clamp));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, textureFilterToInt(filter));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, textureFilterToInt(filter));
			if(mipmap) GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, textureFormatToIntInternal(format), 
					width, height, 0, textureFormatToInt(format), GL11.GL_UNSIGNED_BYTE, data);
			
			unbindTexture();
			
			texture.width 	= width;
			texture.height	= height;
			texture.size 	= data.limit();
			texture.format 	= format;
			texture.clamp 	= clamp;
			texture.filter 	= filter;
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean freeTexture(Texture texture)
	{
		if(texture.isValid())
		{
			GL11.glDeleteTextures(texture.id);
			texture.id = -1;
			texture.size = 0;
			texture.clamp = null;
			texture.filter = null;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean bindTexture(Texture texture)
	{
		if(texture.isValid())
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.id);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean unbindTexture()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return true;
	}

	@Override
	public boolean setActiveTextureSlot(int slot)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + slot);
		return true;
	}

	@Override
	public FrameBuffer createFrameBuffer()
	{
		FrameBuffer buffer = new FrameBuffer();
		buffer.id = GL30.glGenFramebuffers();
		
		if(GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE)
			System.out.println("FAILED TO GENERATE FRAME BUFFER");
			
		return buffer;
	}

	@Override
	public boolean bindFrameBuffer(FrameBuffer buffer)
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, buffer.id);
		return true;
	}

	@Override
	public boolean unbindFrameBuffer()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		return true;
	}

	@Override
	public boolean attachRenderBuffer(FrameBuffer frameBuffer, RenderBuffer renderBuffer)
	{
		int attachment = renderBufferTypeToInt(renderBuffer.type);
		
		if(renderBuffer.type == RenderBufferType.COLOR)
			attachment += renderBuffer.index;
		
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderBuffer.id);
		
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, attachment, GL30.GL_RENDERBUFFER, renderBuffer.id);
		
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, attachment, GL30.GL_TEXTURE_2D, renderBuffer.texture.id, 0);
		
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
		
		return true;
	}

	@Override
	public RenderBuffer createRenderBuffer(RenderBufferType type, int index, TextureFormat format, int width, int height, int samples)
	{
		int textureFormatExt = textureFormatToInt(format);
		int textureFormatInt = textureFormatToIntInternal(format);
		int primitiveType 	= textureFormatToPrimitiveType(format);
		
		Texture renderTexture = createTexture();
		renderTexture.format = format;
		renderTexture.width = width;
		renderTexture.height = height;
		renderTexture.clamp = TextureClamp.TEXTUTE_CLAMP_REPEAT;
		renderTexture.filter = TextureFilter.TEXTURE_FILTER_NEAREST_MIPMAP_LINEAR;
		GL11.glBindTexture(GL30.GL_TEXTURE_2D, renderTexture.id);
		
		GL11.glTexImage2D(GL30.GL_TEXTURE_2D, 0, textureFormatInt, width, height, 0, textureFormatExt, primitiveType, 0);  
		
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST);
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST);
			
		RenderBuffer buffer = new RenderBuffer();
		buffer.id = GL30.glGenRenderbuffers();
		buffer.type = type;
		buffer.index = index;
		buffer.texture = renderTexture;
		
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, buffer.id);
		
		GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, samples, textureFormatInt, width, height);
		
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
		
		return buffer;
	}

	@Override
	public boolean setViewport(int x, int y, int width, int height)
	{
		GL11.glViewport(x, y, width, height);
		return false;
	}

}
