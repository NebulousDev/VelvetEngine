package graphics;

public class ModelRenderer
{

	public static void render(Graphics gfx, Model model)
	{
		gfx.bindBuffer(model.vbo);
		gfx.bindBuffer(model.ibo);
		
		gfx.drawElementsRange(0, model.ibo.size);
		
		/*
		for(SubMesh mesh : model.meshes)
		{
			gfx.drawElementsRange(mesh.offset, mesh.count);
		}
		*/
	}
	
}
