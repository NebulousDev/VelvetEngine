package graphics;

import graphics.Graphics.RenderBufferType;

public class RenderBuffer {
	
	int id					= -1;
	Texture texture 		= null;
	RenderBufferType type 	= null;
	int index 				= 0;

	public Texture getTexture()
	{
		return texture;
	}
	
}
