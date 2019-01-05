package graphics;

import java.util.ArrayList;

import org.lwjgl.opengl.GL20;

import core.Game;
import resource.Resource;
import resource.ResourceManager;
import utils.FileUtils;

public class ShaderProgram extends Resource
{
	int 				id			= -1;
	String				name		= null;
	ArrayList<Shader> 	shaders 	= null;
	boolean				finalized	= false;
	
	public int ID() { return id; }
	
	public String name() { return name; }
	
	public ArrayList<Shader> shaders() { return shaders; }
	
	public boolean isFinalized() { return finalized; }
	
	public boolean isValid() { return id >= 0; }
	
	public void bind(Graphics graphics)
	{
		graphics.bindProgram(this);
	}
	
	public void unbind(Graphics graphics)
	{
		graphics.unbindProgram();
	}

	//TODO: This badly needs to be re-written
	
	@Override
	protected Resource load(Game game, ResourceManager manager, String tag, String filepath)
	{
		name = tag;
		shaders = new ArrayList<Shader>();
		
		Graphics gfx = game.getGraphics();
		
		String filename = FileUtils.stripExtention(filepath);
		
		if(!FileUtils.fileExists(filename + ".vert") || !FileUtils.fileExists(filename + ".frag"))
		{
			System.out.println("Error! Failed to register resource '" + tag + "'. File not found at '" + filepath + "'.");
			return null;
		}

		id = GL20.glCreateProgram();
		
		String vert = FileUtils.readFileAsString(filename + ".vert");
		String frag = FileUtils.readFileAsString(filename + ".frag");
		String geom = null;

		if(FileUtils.fileExists(filename + ".geom"))
			geom = FileUtils.readFileAsString(filename + ".geom");
		
		Shader vertex = gfx.createShader(tag + "_vertex", ShaderType.SHADER_TYPE_VERTEX, vert);
		Shader fragment = gfx.createShader(tag + "_fragment", ShaderType.SHADER_TYPE_FRAGMENT, frag);
		Shader geometry = null;
		
		if(geom != null)
			geometry = gfx.createShader(tag + "_geometry", ShaderType.SHADER_TYPE_GEOMETRY, geom);
		
		boolean result = false;
		
		result = gfx.attachShader(this, vertex);
		result = gfx.attachShader(this, fragment);
		
		if(geometry != null)
			result = gfx.attachShader(this, geometry);
		
		result = gfx.finalizeProgram(this);
		
		return result ? this : null;
	}

	@Override
	protected void release(Game game, ResourceManager manager)
	{
		
	}

}
