package graphics.renderers;

import graphics.Graphics;
import graphics.Mesh;
import graphics.ShaderProgram;
import graphics.GraphicsUniform;
import graphics.Mesh.SubMesh;

@Deprecated
public class ModelRenderer
{
	public static GraphicsUniform color = null;
	
	public static void render(Graphics gfx, ShaderProgram program, Mesh model)
	{
		if(color == null) color = gfx.getUniform(program, "color");
		
		gfx.bindBuffer(model.vbo);
		gfx.bindBuffer(model.ibo);
		
		//gfx.drawElementsRange(0, model.ibo.size);
		
		for(SubMesh mesh : model.subMeshes)
		{
			gfx.setUniform(color, mesh.color);
			gfx.drawElementsRange(mesh.offset, mesh.count);
		}
	}
	
}
