package entity.components;

import entity.Component;

public class TransformComponent extends Component<TransformComponent>
{
	public final static int 	TYPE	= getNextTypeID();
	public final static String 	NAME	= "Transform Component";

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
		return new TransformComponent();
	}
	
}
