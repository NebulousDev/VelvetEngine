package entity.component;

import entity.Component;
import math.Matrix4f;
import math.Quaternion;
import math.Vector3f;

public class TransformComponent implements Component {

	public Vector3f position;
	public Vector3f scale;
	public Quaternion orientation;
	
	public TransformComponent(Vector3f position, Vector3f scale, Quaternion orientation)
	{
		this.position = position;
		this.scale = scale;
		this.orientation = orientation;
	}
	
	public TransformComponent(Vector3f position, Quaternion orientation)
	{
		this.position = position;
		this.scale = new Vector3f(1.0f);
		this.orientation = orientation;
	}
	
	public TransformComponent(Vector3f position, Vector3f scale)
	{
		this.position = position;
		this.scale = scale;
		this.orientation = Quaternion.Identity();
	}
	
	public TransformComponent(Vector3f position)
	{
		this.position = position;
		this.scale = new Vector3f(1.0f);
		this.orientation = Quaternion.Identity();
	}
	
	public TransformComponent()
	{
		this.position = new Vector3f(0.0f);
		this.scale = new Vector3f(1.0f);
		this.orientation = Quaternion.Identity();
	}
	
	public TransformComponent rotate(Vector3f axis, float angleDeg)
	{
		orientation.mul(Quaternion.Rotation(axis, Math.toRadians(angleDeg)));
		return this;
	}
	
	public TransformComponent rotate(Quaternion orientation)
	{
		orientation.mul(orientation);
		return this;
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
	
	public Matrix4f getView()
	{
		return Matrix4f.Identity().translate(position).mul(orientation.toMatrix());
	}
	
	public Matrix4f getModel()
	{
		return Matrix4f.Identity().scale(scale).mul(orientation.toMatrix()).translate(position);
	}
	
}
