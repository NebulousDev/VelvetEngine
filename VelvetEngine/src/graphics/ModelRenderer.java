package graphics;

import graphics.Model.SubMesh;

public class ModelRenderer
{
	public static void render(Graphics gfx, Model model)
	{
		gfx.bindBuffer(model.vbo);
		gfx.bindBuffer(model.ibo);
		
		//gfx.drawElementsRange(0, 1400);
		
		for(SubMesh mesh : model.meshes)
		{
			gfx.drawElementsRange(mesh.offset, mesh.count);
		}
	}
	
}
