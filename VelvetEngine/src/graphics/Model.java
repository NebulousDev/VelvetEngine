package graphics;

import java.util.ArrayList;

import loaders.OBJLoader.OBJModel;
import loaders.OBJLoader.OBJObject;
import math.Vector3f;

public class Model
{
	public class Material
	{
		Vector3f color;
	}
	
	public class Mesh
	{
		public Material material;
		public int 		iboOffset;
		public int 		iboCount;
	}
	
	GraphicsBuffer 	vbo;
	GraphicsBuffer 	ibo;
	Uniform 		uniformColor;

	ArrayList<Mesh> meshes;
	
	public static Model loadModelFromOBJ(Graphics gfx, OBJModel obj)
	{
		Model model = new Model();
		model.vbo = gfx.createBuffer();
		model.ibo = gfx.createBuffer();
		model.meshes = new ArrayList<>();
		
		if(!obj.useObjectList)
		{
			// DO STUFF
		}
		
		else
		{
			for(OBJObject object : obj.objects)
			{
				
			}
		}
		
		return model;
	}
	
	public void draw(Graphics gfx)
	{
		gfx.bindBuffer(vbo);
		gfx.bindBuffer(ibo);
		
		for(Mesh mesh : meshes)
		{
			gfx.setUniform(uniformColor, mesh.material.color);
			//gfx.drawElementsRange(mesh.iboOffset, mesh.iboLength);
		}
	}
}
