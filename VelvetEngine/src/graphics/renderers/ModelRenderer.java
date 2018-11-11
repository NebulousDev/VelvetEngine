package graphics.renderers;

import graphics.Graphics;
import graphics.GraphicsMesh;
import graphics.GraphicsProgram;
import graphics.GraphicsUniform;
import graphics.GraphicsMesh.SubMesh;

@Deprecated
public class ModelRenderer
{
	public static GraphicsUniform color = null;
	
	public static void render(Graphics gfx, GraphicsProgram program, GraphicsMesh model)
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
