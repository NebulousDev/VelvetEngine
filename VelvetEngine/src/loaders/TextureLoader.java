package loaders;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import graphics.Graphics;
import graphics.Texture;
import graphics.TextureClamp;
import graphics.TextureFilter;
import graphics.TextureFormat;
import utils.FileUtils;

public class TextureLoader
{
	private TextureLoader() {}
	
	public static Texture loadFromFile(Graphics gfx, String filepath)
	{
		ByteBuffer data 	= null;
		ByteBuffer image	= null;
		
		//data = ByteBuffer.wrap(FileUtils.readFileAsBytes(FileUtils.getResourcePath() + filepath));
		//data.flip();
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
		
		Texture texture = gfx.createTexture();
		gfx.setTextureData(texture, image, width.get(0), height.get(0), format, 
				TextureClamp.TEXTUTE_CLAMP_REPEAT, TextureFilter.TEXTURE_FILTER_NEAREST, false);
		
		STBImage.stbi_image_free(image);
		
		return texture;
	}
}
