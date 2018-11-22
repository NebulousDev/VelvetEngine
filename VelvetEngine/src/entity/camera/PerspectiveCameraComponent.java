package entity.camera;

import math.Matrix4f;

public class PerspectiveCameraComponent extends CameraComponent {

	public PerspectiveCameraComponent(float fov, float aspect, float near, float far)
	{
		super(Matrix4f.Perspective(fov, aspect, near, far));
	}

}
