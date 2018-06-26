package math;

public class Vector2f
{
	public static final int SIZE 	= 2;
	public static final int BYTES 	= 2 * Float.BYTES;
	
	public float x;
	public float y;
	
	Vector2f(float fill)
	{
		this.x = fill;
		this.y = fill;
	}
	
	Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2f add(Vector2f vec2)
	{
		x += vec2.x;
		y += vec2.y;
		return this;
	}
	
	public Vector2f sub(Vector2f vec2)
	{
		x -= vec2.x;
		y -= vec2.y;
		return this;
	}
	
	public Vector2f mul(Vector2f vec2)
	{
		x *= vec2.x;
		y *= vec2.y;
		return this;
	}
	
	public Vector2f mul(float num)
	{
		x *= num;
		y *= num;
		return this;
	}
	
	public Vector2f div(Vector2f vec2)
	{
		x /= vec2.x;
		y /= vec2.y;
		return this;
	}
	
	public float magnitude()
	{
		return (float)Math.sqrt((x * x) + (y * y));
	}
}