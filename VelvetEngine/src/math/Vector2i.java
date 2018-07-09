package math;

import java.nio.IntBuffer;

public class Vector2i
{
	public static final int SIZE 	= 2;
	public static final int BYTES 	= 2 * Integer.BYTES;
	
	public int x;
	public int y;
	
	public Vector2i(int fill)
	{
		this.x = fill;
		this.y = fill;
	}
	
	public Vector2i(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2i(int[] array)
	{
		this.x = array[0];
		this.y = array[1];
	}
	
	public Vector2i set(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Vector2i set(Vector2i vec2)
	{
		this.x = vec2.x;
		this.y = vec2.y;
		return this;
	}
	
	public Vector2i copy()
	{
		return new Vector2i(x, y);
	}
	
	public Vector2i add(Vector2i vec2)
	{
		x += vec2.x;
		y += vec2.y;
		return this;
	}
	
	public Vector2i sub(Vector2i vec2)
	{
		x -= vec2.x;
		y -= vec2.y;
		return this;
	}
	
	public Vector2i mul(Vector2i vec2)
	{
		x *= vec2.x;
		y *= vec2.y;
		return this;
	}
	
	public Vector2i mul(int num)
	{
		x *= num;
		y *= num;
		return this;
	}
	
	public Vector2i div(Vector2i vec2)
	{
		x /= vec2.x;
		y /= vec2.y;
		return this;
	}
	
	public Vector2i normalize()
	{
		float magnitude = length();
		x /= magnitude;
		y /= magnitude;
		return this;
	}
	
	public float length()
	{
		return (float)Math.sqrt((x * x) + (y * y));
	}
	
	public IntBuffer toIntBuffer(boolean flip)
	{
		IntBuffer buffer = IntBuffer.allocate(SIZE);
		buffer.put(x);
		buffer.put(y);
		if(flip) buffer.flip();
		return buffer;
	}
	
	public IntBuffer toIntBuffer()
	{
		return toIntBuffer(false);
	}
	
	public int[] toArray()
	{
		return new int[] { x, y};
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}