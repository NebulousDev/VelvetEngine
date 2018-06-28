package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Vector2f
{
	public static final int SIZE 	= 2;
	public static final int BYTES 	= 2 * Float.BYTES;
	
	public float x;
	public float y;
	
	public Vector2f(float fill)
	{
		this.x = fill;
		this.y = fill;
	}
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2f copy()
	{
		return new Vector2f(x, y);
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
	
	public Vector2f normalize()
	{
		float magnitude = magnitude();
		x /= magnitude;
		y /= magnitude;
		return this;
	}
	
	public float magnitude()
	{
		return (float)Math.sqrt((x * x) + (y * y));
	}
	
	public FloatBuffer toFloatBuffer(boolean flip)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(SIZE);
		buffer.put(x);
		buffer.put(y);
		if(flip) buffer.flip();
		return buffer;
	}
	
	public FloatBuffer toFloatBuffer()
	{
		return toFloatBuffer(false);
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}