package graphics.system;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import core.Game;
import entity.Entity;
import entity.EntityManager;
import entity.camera.CameraComponent;
import entity.camera.PerspectiveCameraComponent;
import entity.component.TransformComponent;
import graphics.Graphics;
import graphics.Graphics.DrawMode;
import graphics.GraphicsUniform;
import graphics.Mesh;
import graphics.Mesh.SubMesh;
import graphics.ShaderProgram;
import graphics.component.MeshComponent;
import graphics.component.PhongRenderComponent;
import math.Matrix4f;

public class PhongRenderer extends Renderer {

	private ShaderProgram shader;

	public PhongRenderer(Game game)
	{
		this.shader = game.getResourceManager().getResource(ShaderProgram.class, "simple");
	}
	
	@Override
	public void begin(Graphics graphics, EntityManager entityManager)
	{
		//TODO: add state checks
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LESS);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		shader.bind(graphics);
	}

	//TODO: pass a World instead
	
	@Override
	public void render(Entity camera, Graphics graphics, EntityManager entityManager)
	{
		CameraComponent cameraComponent = camera.getComponent(PerspectiveCameraComponent.class);
		TransformComponent transformComponent = camera.getComponent(TransformComponent.class);
		
		Matrix4f view = transformComponent.getViewMatrix();
		Matrix4f perspective = cameraComponent.projection;
		Matrix4f model = new Matrix4f();
		
		GraphicsUniform viewUniform = graphics.getUniform(shader, "view");
		GraphicsUniform perspectiveUniform = graphics.getUniform(shader, "perspective");
		GraphicsUniform modelUniform = graphics.getUniform(shader, "model");
		
		graphics.setUniform(viewUniform, view);
		graphics.setUniform(perspectiveUniform, perspective);
		
		Iterator<Long> iterator = entityManager.getComponentTable().getEntityIterator(PhongRenderComponent.class);
		
		while(iterator.hasNext())
		{
			long entityID = iterator.next();
			TransformComponent entityTransform = entityManager.getComponent(TransformComponent.class, entityID);
			MeshComponent entityMesh = entityManager.getComponent(MeshComponent.class, entityID);
			
			Mesh mesh = entityMesh.mesh; 

			entityTransform.getModelMatrix(model);
			graphics.setUniform(modelUniform, model);
			
			graphics.bindBuffer(mesh.vbo);
			graphics.bindBuffer(mesh.ibo);
			
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES, 0);
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
			GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES, 5 * Float.BYTES);
			
			//graphics.drawElementsRange(0, mesh.ibo.size);
			
			for(SubMesh subMesh : mesh.subMeshes)
				graphics.drawElementsRange(DrawMode.TRIANGLES, subMesh.offset, subMesh.count);
		}
	}

	@Override
	public void end(Graphics graphics, EntityManager entityManager)
	{
		//NOTE: Unnecessary
		
		shader.unbind(graphics); 
	}

}
