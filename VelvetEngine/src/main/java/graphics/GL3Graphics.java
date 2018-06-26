package graphics;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GLCapabilities;

import core.Window;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;

public class GL3Graphics implements Graphics
{
	private int graphicsTypeToInt(BufferType type)
	{
		switch (type)
		{
			case GRAPHICS_BUFFER_VERTEX:
				return GL15.GL_ARRAY_BUFFER;
				
			case GRAPHICS_BUFFER_INDEX:
				return GL15.GL_ELEMENT_ARRAY_BUFFER;

			default:
				return -1;
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
	
	@Override
	public boolean initGraphics()
	{
		boolean init = GLFW.glfwInit();
		if(init)return true;
		else return false;
	}
	
	@Override
	public GraphicsContext createContext(Window window)
	{
		GraphicsContext context = new GraphicsContext(window.getWindowLong());
		
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
	public GraphicsBuffer createBuffer(BufferType type, int size)
	{
		GraphicsBuffer buffer = new GraphicsBuffer();
		buffer.id = GL15.glGenBuffers();
		buffer.size = size;
		buffer.type = type;
		
		if(buffer.isValid()) return buffer;
		else return null;
	}
	
	@Override
	public Program createProgram(String name)
	{
		Program program = new Program();
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
		}
		
		if(shader.isValid()) return shader;
		else return null;
	}

	@Override
	public Uniform getUniform(Program program, String name)
	{
		Uniform uniform = new Uniform();
		uniform.name = name;
		uniform.id = GL20.glGetUniformLocation(program.id, name);
		
		if(uniform.isValid()) return uniform;
		else return null;
	}
	
	@Override
	public boolean setBufferData(GraphicsBuffer buffer, int[] data)
	{
		if(buffer.isValid())
		{
			GL15.glBufferData(graphicsTypeToInt(buffer.type), data, GL15.GL_STATIC_DRAW);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean setBufferData(GraphicsBuffer buffer, float[] data)
	{
		if(buffer.isValid())
		{
			GL15.glBufferData(graphicsTypeToInt(buffer.type), data, GL15.GL_STATIC_DRAW);
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
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean attachShader(Program program, Shader shader)
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
	public boolean finalizeProgram(Program program)
	{
		if(program.isValid() && !program.isFinalized())
		{
			GL20.glLinkProgram(program.id);
			program.finalized = true;
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean bindProgram(Program program)
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
	public boolean freeProgram(Program program)
	{
		if(program.isValid())
		{
			GL20.glDeleteProgram(program.id);
			program.id = -1;
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean setUniform(Uniform uniform, int data)
	{
		if(uniform.isValid())
		{
			GL20.glUniform1i(uniform.id, data);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean setUniform(Uniform uniform, float data)
	{
		if(uniform.isValid())
		{
			GL20.glUniform1f(uniform.id, data);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean setUniform(Uniform uniform, Vector2f data)
	{
		if(uniform.isValid())
		{
			GL20.glUniform2f(uniform.id, data.x, data.y);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean setUniform(Uniform uniform, Vector3f data)
	{
		if(uniform.isValid())
		{
			GL20.glUniform3f(uniform.id, data.x, data.y, data.z);
			return true;
		}

		return false;
	}

	@Override
	public boolean setUniform(Uniform uniform, Vector4f data)
	{
		if(uniform.isValid())
		{
			GL20.glUniform4f(uniform.id, data.x, data.y, data.z, data.w);
			return true;
		}

		return false;
	}

	@Override
	public boolean setUniform(Uniform uniform, Matrix4f data)
	{
		if(uniform.isValid())
		{
			GL20.glUniform4fv(uniform.id, data.toFloatBuffer(true));
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
}
