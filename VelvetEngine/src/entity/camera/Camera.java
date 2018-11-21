package entity.camera;

import math.Matrix4f;
import math.Quaternion;
import math.Vector3f;

public class Camera
{
	private Vector3f 	position;
	private Quaternion 	orientation;
	private Matrix4f 	projection;
	
	private Camera() {}
	
	public static Camera createCamera(Matrix4f projection)
	{
		Camera camera 		= new Camera();
		camera.projection 	= projection;
		camera.position 	= new Vector3f(0.0f);
		camera.orientation 	= Quaternion.Identity();
		return camera;
	}
	
	public Camera rotate(Vector3f axis, float angleDeg)
	{
		orientation.mul(Quaternion.Rotation(axis, Math.toRadians(angleDeg)));
		return this;
	}
	
	public Vector3f getPosition()
	{
		return position;
	}

	public Vector3f getForward()
	{
		return orientation.getForward();
	}

	public Vector3f getBack()
	{
		return orientation.getBack();
	}

	public Vector3f getUp()
	{
		return orientation.getUp();
	}

	public Vector3f getDown()
	{
		return orientation.getDown();
	}

	public Vector3f getRight()
	{
		return orientation.getRight();
	}

	public Vector3f getLeft()
	{
		return orientation.getLeft();
	}
	
	public Quaternion getOrientation()
	{
		return orientation;
	}

	public Matrix4f getProjection()
	{
		return projection;
	}
	
	public Matrix4f getView()
	{
		return orientation.toMatrix().mul(Matrix4f.Translation(position));
	}

}
