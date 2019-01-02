package entity.camera;

import entity.Component;
import math.Matrix4f;

public class CameraComponent implements Component {

	public Matrix4f projection;

	protected CameraComponent(Matrix4f perspective)
	{
		this.projection = perspective;
	}
	
}
