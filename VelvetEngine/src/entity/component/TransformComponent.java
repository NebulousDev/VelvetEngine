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
	
	public Matrix4f calcModelMatrix()
	{
		return Matrix4f.Translation(position).mul(orientation.toMatrix()).mul(Matrix4f.Scale(scale));
	}
	
}
