package entity.camera;

import entity.Component;
import entity.Require;
import entity.component.TransformComponent;
import graphics.component.MeshComponent;
import math.Matrix4f;

@Require(MeshComponent.class)
@Require(TransformComponent.class)

public class CameraComponent implements Component {

	public Matrix4f perspective;
	
	public CameraComponent()
	{
		
	}
	
}
