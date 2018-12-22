package graphics;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

import core.Game;
import resource.Resource;
import resource.ResourceManager;
import utils.FileUtils;

public class Texture extends Resource
{
	int 			id		= -1;
	int				width	= 0;
	int				height	= 0;
	int 			size	= 0;
	TextureFormat	format	= null;
	TextureClamp 	clamp	= null;
	TextureFilter 	filter	= null;
	
	public int ID() { return id; }
	
	public int width() { return width; }
	
	public int height() { return height; }
	
	public int size() { return size; }
	
	public TextureFormat getFormat() { return format; }
	
	public TextureClamp getClamp() { return clamp; }
	
	public TextureFilter getFilter() { return filter; }
	
	public boolean isValid() { return id >= 0; }

	@Override
	protected Resource load(Game game, ResourceManager manager, String tag, String filepath)
	{
		Graphics gfx = game.getGraphics();
		
		if(!FileUtils.fileExists(filepath))
		{
			System.out.println("Error! Failed to register resource '" + getName() + "'. File not found at '" + filepath + "'.");
			return null;
		}
		
		ByteBuffer data 	= null;
		ByteBuffer image	= null;
		
		data = FileUtils.readFileAsByteBuffer(filepath);
		
		IntBuffer width		= BufferUtils.createIntBuffer(1);
		IntBuffer height 	= BufferUtils.createIntBuffer(1);
		IntBuffer channels 	= BufferUtils.createIntBuffer(1);
		
		STBImage.stbi_set_flip_vertically_on_load(true);
		image = STBImage.stbi_load_from_memory(data, width, height, channels, 0);
		
		if(image == null)
		{
			System.out.println("STBImage failed to read file: " + filepath + "\nREASON: " + STBImage.stbi_failure_reason());
			return null;
		}
		
		int numChannels = channels.get(0);
		TextureFormat format = null;
		
		switch (numChannels)
		{
			case 1: format = TextureFormat.TEXTURE_FORMAT_R;		break;
			case 2: format = TextureFormat.TEXTURE_FORMAT_RG;		break;
			case 3: format = TextureFormat.TEXTURE_FORMAT_RGB;		break;
			case 4: format = TextureFormat.TEXTURE_FORMAT_RGBA;		break;
			default: format = TextureFormat.TEXTURE_FORMAT_RGBA;
		}
		
		id = GL11.glGenTextures();	//TODO: remove GL call
		
		gfx.setTextureData(this, image, width.get(0), height.get(0), format, 
				TextureClamp.TEXTUTE_CLAMP_REPEAT, TextureFilter.TEXTURE_FILTER_NEAREST, false);
		
		STBImage.stbi_image_free(image);
		
		return this;
	}

	@Override
	protected void release(Game game, ResourceManager manager)
	{
		GL11.glDeleteTextures(id);
		this.id = -1;
	}
}
