package graphics;

import java.util.ArrayList;

import math.Vector3f;

public class Model
{
	public static class SubMesh
	{
		public String 	name;
		public int 		offset;
		public int 		count;
		public Vector3f color;
	}
	
	public GraphicsBuffer vbo;
	public GraphicsBuffer ibo;
	
	public ArrayList<SubMesh> meshes;
	
	public void dispose(Graphics gfx)
	{
		gfx.freeBuffer(vbo);
		gfx.freeBuffer(ibo);
	}
	
}
