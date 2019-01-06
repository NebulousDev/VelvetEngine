package graphics.system;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import core.Game;
import core.Window;
import entity.Entity;
import entity.EntityManager;
import entity.camera.CameraComponent;
import entity.camera.PerspectiveCameraComponent;
import entity.component.TransformComponent;
import graphics.FrameBuffer;
import graphics.Graphics;
import graphics.Graphics.BufferType;
import graphics.Graphics.DrawMode;
import graphics.Graphics.RenderBufferType;
import graphics.GraphicsBuffer;
import graphics.Material;
import graphics.Mesh;
import graphics.RenderBuffer;
import graphics.ShaderProgram;
import graphics.Texture;
import graphics.TextureFormat;
import graphics.Uniform;
import graphics.component.DirectionalLightComponent;
import graphics.component.ModelComponent;
import graphics.component.PhongMaterialComponent;
import graphics.component.PhongRenderComponent;
import graphics.component.PointLightComponent;
import graphics.component.SpotLightComponent;
import math.Matrix4f;
import math.Vector3f;

public class PhongRenderer extends Renderer {

	private ShaderProgram 	drawShader;
	private ShaderProgram 	quadShader;
	private FrameBuffer 	frameBuffer;
	private RenderBuffer 	colorBuffer;
	private RenderBuffer	depthBuffer;
	private GraphicsBuffer	renderQuadVBO;
	private GraphicsBuffer	renderQuadIBO;
	
	private Window 			window;
	
	@Override
	public void initialize(Game game, Graphics graphics)
	{
		this.drawShader = game.getResourceManager().getResource(ShaderProgram.class, "phong");
		this.quadShader = game.getResourceManager().getResource(ShaderProgram.class, "quad");
		
		this.window = game.getApplication().getWindow();
		
//		int width = 2160;
//		int height = 1440;
		
		int width = window.getWidth();
		int height = window.getHeight();
		
		this.frameBuffer = graphics.createFrameBuffer();
		
		graphics.bindFrameBuffer(frameBuffer);
		
		this.colorBuffer = graphics.createRenderBuffer(RenderBufferType.COLOR, 0, TextureFormat.TEXTURE_FORMAT_FLOAT_RGBA, width, height, 0);
		this.depthBuffer = graphics.createRenderBuffer(RenderBufferType.DEPTH_STENCIL, 0, TextureFormat.TEXTURE_FORMAT_DEPTH_STENCIL, width, height, 0);
		
		int error = GL11.glGetError();
		if(error != 0)
			System.out.println(error);
		
		graphics.attachRenderBuffer(frameBuffer, colorBuffer);
		graphics.attachRenderBuffer(frameBuffer, depthBuffer);
		
		graphics.unbindFrameBuffer();
		
		float[] vbo =
		{ 
			-1.0f, -1.0f, 	0.0f, 0.0f,
			-1.0f,  1.0f,	0.0f, 1.0f,
			 1.0f,  1.0f,  	1.0f, 1.0f,
			 1.0f, -1.0f,	1.0f, 0.0f 
		};
		
		int[] ibo = { 0, 1, 2, 0, 2, 3 };
		
		renderQuadVBO = graphics.createBuffer();
		graphics.setBufferData(renderQuadVBO, BufferType.VERTEX, vbo);
		renderQuadIBO = graphics.createBuffer();
		graphics.setBufferData(renderQuadIBO, BufferType.ELEMENT, ibo);
		
		graphics.vsync(true);
	}
	
	@Override
	public void begin(Graphics graphics, EntityManager entityManager)
	{
		// Nothing
	}

	//TODO: pass a World instead
	//TODO: split into functions
	
	@Override
	public void render(Entity camera, Graphics graphics, EntityManager entityManager)
	{
		graphics.bindFrameBuffer(frameBuffer);
		graphics.setViewport(0, 0, colorBuffer.getTexture().width(), colorBuffer.getTexture().height());
		graphics.drawBuffers(colorBuffer, depthBuffer);
	
		graphics.setClearColor(0.0f, 0.1f, 0.12f, 0.0f);
		graphics.clearBuffers();
		
		drawShader.bind(graphics);

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LESS);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		// View-Perspective
		
		CameraComponent cameraComponent = camera.getComponent(PerspectiveCameraComponent.class);
		TransformComponent cameraTransform = camera.getComponent(TransformComponent.class);
		
		Matrix4f view = cameraTransform.getViewMatrix();
		Matrix4f perspective = cameraComponent.projection;
		Matrix4f model = new Matrix4f();
		
		Uniform modelUniform = graphics.getUniform(drawShader, "model");

		graphics.setUniform(graphics.getUniform(drawShader, "cameraPosition"), cameraTransform.position);
		
		graphics.setUniform(graphics.getUniform(drawShader, "ambientColor"), new Vector3f(1.0f, 1.0f, 1.0f));
		graphics.setUniform(graphics.getUniform(drawShader, "ambientIntensity"), 0.0f);
		
		graphics.setUniform(graphics.getUniform(drawShader, "view"), view);
		graphics.setUniform(graphics.getUniform(drawShader, "perspective"), perspective);
		
		// Directional Lights
		
		Iterator<Long> dirLightEntityIterator = entityManager.getComponentTable().getEntityIterator(DirectionalLightComponent.class);
		
		if(dirLightEntityIterator != null)
		{
			int dirLightCount = 0;
			while(dirLightEntityIterator.hasNext())
			{
				Long entityID = dirLightEntityIterator.next();
				
				DirectionalLightComponent dirLight = entityManager.getComponent(DirectionalLightComponent.class, entityID);
				
				graphics.setUniform(graphics.getUniform(drawShader, "dirLights[" + dirLightCount + "].direction"), dirLight.direction);
				graphics.setUniform(graphics.getUniform(drawShader, "dirLights[" + dirLightCount + "].color"), dirLight.color);
				graphics.setUniform(graphics.getUniform(drawShader, "dirLights[" + dirLightCount + "].intensity"), dirLight.intensity);
				
				dirLightCount++;
			}
			
			graphics.setUniform(graphics.getUniform(drawShader, "dirLightCount"), dirLightCount);
		}
		
		// Spot Lights
		
		Iterator<Long> spotLightEntityIterator = entityManager.getComponentTable().getEntityIterator(SpotLightComponent.class);
	
		if(spotLightEntityIterator != null)
		{
			int spotLightCount = 0;
			while(spotLightEntityIterator.hasNext())
			{
				Long entityID = spotLightEntityIterator.next();
				
				SpotLightComponent spotLight = entityManager.getComponent(SpotLightComponent.class, entityID);
				TransformComponent tansform = entityManager.getComponent(TransformComponent.class, entityID);
				
				graphics.setUniform(graphics.getUniform(drawShader, "spotLights[" + spotLightCount + "].position"), tansform.position);
				graphics.setUniform(graphics.getUniform(drawShader, "spotLights[" + spotLightCount + "].direction"), spotLight.direction);
				graphics.setUniform(graphics.getUniform(drawShader, "spotLights[" + spotLightCount + "].attenuation"), spotLight.attenuation);
				graphics.setUniform(graphics.getUniform(drawShader, "spotLights[" + spotLightCount + "].color"), spotLight.color);
				graphics.setUniform(graphics.getUniform(drawShader, "spotLights[" + spotLightCount + "].radius"), spotLight.radius);
				graphics.setUniform(graphics.getUniform(drawShader, "spotLights[" + spotLightCount + "].intensity"), spotLight.intensity);
				
				spotLightCount++;
			}
			
			graphics.setUniform(graphics.getUniform(drawShader, "spotLightCount"), spotLightCount);
		}
		
		// Point Lights
		
		Iterator<Long> pointLightEntityIterator = entityManager.getComponentTable().getEntityIterator(PointLightComponent.class);
	
		if(pointLightEntityIterator != null)
		{
			int pointLightCount = 0;
			while(pointLightEntityIterator.hasNext())
			{
				Long entityID = pointLightEntityIterator.next();
				
				PointLightComponent pointLight = entityManager.getComponent(PointLightComponent.class, entityID);
				TransformComponent tansform = entityManager.getComponent(TransformComponent.class, entityID);
				
				graphics.setUniform(graphics.getUniform(drawShader, "pointLights[" + pointLightCount + "].position"), tansform.position);
				graphics.setUniform(graphics.getUniform(drawShader, "pointLights[" + pointLightCount + "].attenuation"), pointLight.attenuation);
				graphics.setUniform(graphics.getUniform(drawShader, "pointLights[" + pointLightCount + "].color"), pointLight.color);
				graphics.setUniform(graphics.getUniform(drawShader, "pointLights[" + pointLightCount + "].intensity"), pointLight.intensity);
				
				pointLightCount++;
			}
			
			graphics.setUniform(graphics.getUniform(drawShader, "pointLightCount"), pointLightCount);
		}
		
		// Render Entities
		
		Iterator<Long> iterator = entityManager.getComponentTable().getEntityIterator(PhongRenderComponent.class);
		
		while(iterator.hasNext())
		{
			long entityID = iterator.next();
			
			// Material
			
			PhongMaterialComponent materialComponent = entityManager.getComponent(PhongMaterialComponent.class, entityID);
//
//			graphics.setUniform(graphics.getUniform(drawShader, "material.diffuse"), 0);
//			graphics.setUniform(graphics.getUniform(drawShader, "material.normal"), 1);
//			
//			graphics.setActiveTextureSlot(0);
//			graphics.bindTexture(material.diffuse);
//			
//			graphics.setActiveTextureSlot(1);
//			graphics.bindTexture(material.normal);
//			
//			graphics.setUniform(graphics.getUniform(drawShader, "material.intensity"), material.intensity);
//			graphics.setUniform(graphics.getUniform(drawShader, "material.exponent"), material.exponent);
			
			// Mesh
			
			TransformComponent 		transform 	= entityManager.getComponent(TransformComponent.class, entityID);
			ModelComponent 			mesh 		= entityManager.getComponent(ModelComponent.class, entityID);
			
			transform.getModelMatrix(model);
			graphics.setUniform(modelUniform, model);
			
			
			Texture diffuseTexture = null;
			Texture normalTexture = null;
			float specularExponent = 0.0f;
			float specularIntensity = 0.0f;
			
			// Draw sub-meshes
			
			for(Mesh subMesh : mesh.model.meshes)
			{
				graphics.bindBuffer(subMesh.vertexBuffer);
				graphics.bindBuffer(subMesh.indexBuffer);
				
				// Attribute Layout
				// TODO: investigate moving to begin()
				
				GL20.glEnableVertexAttribArray(0);	// Position
				GL20.glEnableVertexAttribArray(1);  // TexCoord
				GL20.glEnableVertexAttribArray(2);  // Normal
				GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES, 0);
				GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
				GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES, 5 * Float.BYTES);
				
				
				
				Material material = subMesh.material;
				
				if(material == null || material.diffuse == null)
					diffuseTexture = materialComponent.diffuse;
				else
					diffuseTexture = material.diffuse;
					
				if(material == null || material.normal == null)
					normalTexture = materialComponent.normal;
				else
					normalTexture = material.normal;	
				
				if(material == null)
					specularExponent = materialComponent.exponent;
				else
					specularExponent = material.exponent;
				
				if(material == null)
					specularIntensity = materialComponent.intensity;
				else
					specularIntensity = material.intensity;
				
				graphics.setUniform(graphics.getUniform(drawShader, "material.diffuse"), 0);
				graphics.setUniform(graphics.getUniform(drawShader, "material.normal"), 1);
				
				graphics.setActiveTextureSlot(0);
				graphics.bindTexture(diffuseTexture);
				
				graphics.setActiveTextureSlot(1);
				graphics.bindTexture(normalTexture);
				
				graphics.setUniform(graphics.getUniform(drawShader, "material.intensity"), specularIntensity);
				graphics.setUniform(graphics.getUniform(drawShader, "material.exponent"), specularExponent);
				
				
				
				graphics.drawElements(DrawMode.TRIANGLES, subMesh.indexSize);
			
			}
		}
		
		drawShader.unbind(graphics);
		
		graphics.unbindFrameBuffer();
		
		graphics.setViewport(0, 0, window.getWidth(), window.getHeight());
		graphics.setClearColor(0.0f, 1.0f, 1.0f, 1.0f);
		graphics.clearBuffers();

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		
		// Render quad
		
		quadShader.bind(graphics);
		
		graphics.bindBuffer(renderQuadVBO);
		graphics.bindBuffer(renderQuadIBO);
		
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
		
		graphics.setActiveTextureSlot(0);
		graphics.bindTexture(colorBuffer.getTexture());
		
		graphics.drawElements(DrawMode.TRIANGLES, 6);
		
		quadShader.unbind(graphics);
	}

	@Override
	public void end(Graphics graphics, EntityManager entityManager)
	{
		// nothing
	}

}
