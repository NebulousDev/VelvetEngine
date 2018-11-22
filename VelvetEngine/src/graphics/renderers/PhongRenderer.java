package graphics.renderers;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import entity.Entity;
import entity.EntityManager;
import entity.camera.CameraComponent;
import entity.camera.PerspectiveCameraComponent;
import entity.component.TransformComponent;
import graphics.Graphics;
import graphics.GraphicsUniform;
import graphics.Mesh;
import graphics.ShaderProgram;
import graphics.component.MeshComponent;
import graphics.component.PhongRenderComponent;
import math.Matrix4f;
import resource.Asset;
import resource.AssetManager;

public class PhongRenderer extends RenderSystem {

	private ShaderProgram shader;

	public PhongRenderer(AssetManager assetManager)
	{
		//TODO: This needs to change:
		Asset<ShaderProgram> program = assetManager.getAsset("shader_default");
		this.shader = program.getResource();
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
		
		Matrix4f view = Matrix4f.Identity().mul(cameraComponent.projection).mul(transformComponent.getView());
		Matrix4f mvp = null;
		
		GraphicsUniform mvpUniform = graphics.getUniform(shader, "mvp");
		
		Iterator<Long> iterator = entityManager.getComponentTable().getEntityIterator(PhongRenderComponent.class);
		
		while(iterator.hasNext())
		{
			long entityID = iterator.next();
			TransformComponent entityTransform = entityManager.getComponent(TransformComponent.class, entityID);
			MeshComponent entityMesh = entityManager.getComponent(MeshComponent.class, entityID);
			
			Mesh mesh = entityMesh.mesh; 

			mvp = Matrix4f.Identity().mul(view).mul(entityTransform.getModel());
			
			graphics.setUniform(mvpUniform, mvp);
			
			graphics.bindBuffer(mesh.vbo);
			graphics.bindBuffer(mesh.ibo);
			
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES, 0);
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
			GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES, 5 * Float.BYTES);
			
			graphics.drawElementsRange(0, mesh.ibo.size);
			
			//for(SubMesh subMesh : mesh.subMeshes)
			//	graphics.drawElementsRange(subMesh.offset, subMesh.count);
		}
	}

	@Override
	public void end(Graphics graphics, EntityManager entityManager)
	{
		shader.unbind(graphics); //NOTE: Unnecessary
	}

	@Override
	public String getLocalName()
	{
		return "PhongRenderer";
	}

}
