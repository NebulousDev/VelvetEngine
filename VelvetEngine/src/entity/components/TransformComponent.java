package entity.components;

import entity.Component;
import math.Quaternion;
import math.Vector3f;

public class TransformComponent extends Component<TransformComponent>
{
	public final static int 	TYPE	= getNextTypeID();
	public final static String 	NAME	= "Transform Component";

	private Vector3f 	position;
	private Quaternion 	orientation;
	
	@Override
	public int getTypeID()
	{
		return TYPE;
	}

	@Override
	public String getTypeName()
	{
		return NAME;
	}

	@Override
	public TransformComponent create()
	{
		TransformComponent transform = new TransformComponent();
		transform.orientation = new Quaternion(0f, 0f, 0f, 1.0f);
		transform.position = new Vector3f(0f);
		return transform;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public Quaternion getOrientation()
	{
		return orientation;
	}
	
}
