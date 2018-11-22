package entity.camera;

import math.Matrix4f;

public class OrthoCameraComponent extends CameraComponent {

	public OrthoCameraComponent(float zoom, float aspect, float near, float far)
	{
		super(Matrix4f.Orthographic(-1.0f * aspect * zoom, 1.0f * aspect * zoom, -1.0f * zoom, 1.0f * zoom, near, far));
	}

}
