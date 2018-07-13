package graphics;

import graphics.GraphicsModel.SubMesh;

public class ModelRenderer
{
	public static GraphicsUniform color = null;
	
	public static void render(Graphics gfx, GraphicsProgram program, GraphicsModel model)
	{
		if(color == null) color = gfx.getUniform(program, "color");
		
		gfx.bindBuffer(model.vbo);
		gfx.bindBuffer(model.ibo);
		
		//gfx.drawElementsRange(0, model.ibo.size);
		
		for(SubMesh mesh : model.meshes)
		{
			gfx.setUniform(color, mesh.color);
			gfx.drawElementsRange(mesh.offset, mesh.count);
		}
	}
	
}
