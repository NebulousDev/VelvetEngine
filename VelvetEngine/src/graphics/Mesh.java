package graphics;

public class Mesh
{
	public String 			name;
	public Material			material;
	public GraphicsBuffer 	vertexBuffer;
	public GraphicsBuffer 	indexBuffer;
	public int				vertexSize;
	public int				indexSize;
	
	public void bind(Graphics gfx)
	{
		gfx.bindBuffer(vertexBuffer);
		gfx.bindBuffer(indexBuffer);
	}
	
	public void unbind(Graphics gfx)
	{
		gfx.unbindBuffer(vertexBuffer);
		gfx.unbindBuffer(indexBuffer);
	}
	
	public void dispose(Graphics gfx)
	{
		gfx.freeBuffer(vertexBuffer);
		gfx.freeBuffer(indexBuffer);
	}

}
