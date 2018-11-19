package graphics;

import java.util.ArrayList;

import org.lwjgl.opengl.GL20;

import core.Game;
import resource.ResourceType;
import utils.FileUtils;

public class ShaderProgram extends ResourceType<ShaderProgram>
{
	public final static int 	TYPE	= getNextTypeID();
	public final static String 	NAME	= "Shader Resource";
	
	int 				id			= -1;
	String				name		= null;
	ArrayList<Shader> 	shaders 	= null;
	boolean				finalized	= false;
	
	public int ID() { return id; }
	
	public String name() { return name; }
	
	public ArrayList<Shader> shaders() { return shaders; }
	
	public boolean isFinalized() { return finalized; }
	
	public boolean isValid() { return id >= 0; }

	@Override
	public boolean load(Game game, String localName, String filepath, int flags)
	{
		name = localName;
		shaders = new ArrayList<Shader>();
		
		Graphics gfx = game.getGraphics();
		
		if(!FileUtils.fileExists(filepath + ".vert") || !FileUtils.fileExists(filepath + ".frag"))
		{
			System.out.println("Error! Failed to register resource '" + localName + "'. File not found at '" + filepath + "'.");
			return false;
		}

		id = GL20.glCreateProgram();
		
		String vert = FileUtils.readFileAsString(filepath + ".vert");
		String frag = FileUtils.readFileAsString(filepath + ".frag");
		
		Shader vertex = gfx.createShader(localName + "_vertex", ShaderType.SHADER_TYPE_VERTEX, vert);
		Shader fragment = gfx.createShader(localName + "_fragment", ShaderType.SHADER_TYPE_FRAGMENT, frag);
		
		boolean result = false;
		
		result = gfx.attachShader(this, vertex);
		result = gfx.attachShader(this, fragment);
		
		result = gfx.finalizeProgram(this);
		
		return result;
	}

	@Override
	public boolean unload(Game game)
	{
		// TODO: Unload shaders 
		return false;
	}

	@Override
	public ShaderProgram create(Game game)
	{
		return new ShaderProgram();
	}

	@Override
	public int getTypeID()
	{
		return TYPE;
	}

	@Override
	public String getTypeName()
	{
		return NAME;
	}
}
