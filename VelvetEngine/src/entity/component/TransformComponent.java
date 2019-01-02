package entity.component;

import entity.Component;
import math.Matrix4f;
import math.Quaternion;
import math.Vector3f;

public class TransformComponent implements Component {

	public Vector3f 	position;
	public Quaternion 	orientation;
	public Vector3f 	scale;
	
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
		this.orientation = new Quaternion();
	}
	
	public TransformComponent(Vector3f position)
	{
		this.position = position;
		this.scale = new Vector3f(1.0f);
		this.orientation = new Quaternion();
	}
	
	public TransformComponent()
	{
		this.position = new Vector3f(0.0f);
		this.scale = new Vector3f(1.0f);
		this.orientation = new Quaternion();
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
	
	public Matrix4f getViewMatrix(Matrix4f dest)
	{
		return dest.setRotation(orientation).translate(position);
	}
	
	public Matrix4f getViewMatrix()
	{
		return getViewMatrix(new Matrix4f());
	}
	
	public Matrix4f getModelMatrix(Matrix4f dest)
	{
		return dest.setTranslation(position).rotate(orientation).scale(scale);
	}
	
	public Matrix4f getModelMatrix()
	{
		return getViewMatrix(new Matrix4f());
	}
	
}
