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
import graphics.Graphics.BufferType;
import graphics.Graphics.DrawMode;
import graphics.GraphicsBuffer;
import graphics.Uniform;
import graphics.ShaderProgram;
import graphics.component.LineRenderComponent;
import math.Matrix4f;

public class LineRenderer extends Renderer {

	private ShaderProgram 	shader;
	private GraphicsBuffer	lineVBO;
	private GraphicsBuffer	lineIBO;
	
	@Override
	public void initialize(Game game, Graphics graphics)
	{
		shader = game.getResourceManager().getResource(ShaderProgram.class, "line");
		
		float[] vbo = { 0,0,0, 0,0,0 };
		int[] 	ibo = { 0, 1 };
		
		lineVBO = graphics.createBuffer();
		graphics.setBufferData(lineVBO, BufferType.VERTEX, vbo);
		lineIBO = graphics.createBuffer();
		graphics.setBufferData(lineIBO, BufferType.ELEMENT, ibo);
	}
	
	@Override
	public void begin(Graphics graphics, EntityManager entityManager)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LESS);
		//GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		shader.bind(graphics);
	}

	@Override
	public void render(Entity camera, Graphics graphics, EntityManager entityManager)
	{
		CameraComponent cameraComponent = camera.getComponent(PerspectiveCameraComponent.class);
		TransformComponent transformComponent = camera.getComponent(TransformComponent.class);
		
		Matrix4f view = transformComponent.getViewMatrix();
		Matrix4f perspective = cameraComponent.projection;
		Matrix4f model = new Matrix4f();
		
		Uniform viewUniform = graphics.getUniform(shader, "view");
		Uniform perspectiveUniform = graphics.getUniform(shader, "perspective");
		Uniform modelUniform = graphics.getUniform(shader, "model");
		Uniform colorUniform = graphics.getUniform(shader, "color");
		
		graphics.setUniform(viewUniform, view);
		graphics.setUniform(perspectiveUniform, perspective);
		
		Iterator<Long> iterator = entityManager.getComponentTable().getEntityIterator(LineRenderComponent.class);
		
		while(iterator.hasNext())
		{
			graphics.bindBuffer(lineVBO);
			graphics.bindBuffer(lineIBO);
			
			long entityID = iterator.next();
			
			TransformComponent entityTransform = entityManager.getComponent(TransformComponent.class, entityID);
			LineRenderComponent lineComponent = entityManager.getComponent(LineRenderComponent.class, entityID);
			
			entityTransform.getModelMatrix(model);
			graphics.setUniform(modelUniform, model);
			graphics.setUniform(colorUniform, lineComponent.color);
			
			GL20.glEnableVertexAttribArray(0);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 3 * Float.BYTES, 0);
			
			float[] data = 
			{
				lineComponent.p1.x,
				lineComponent.p1.y,
				lineComponent.p1.z,
				lineComponent.p2.x,
				lineComponent.p2.y,
				lineComponent.p2.z,
			};
			
			graphics.setBufferSubData(lineVBO, 0, data);
			
			GL11.glLineWidth(2.0f);
			graphics.drawElements(DrawMode.LINE_STRIPS, 2);
		}
	}

	@Override
	public void end(Graphics graphics, EntityManager entityManager)
	{
		graphics.unbindBuffer(lineVBO);
		graphics.unbindBuffer(lineIBO);
		shader.unbind(graphics);
	}

}
