package math;

import java.nio.IntBuffer;

public class Vector3i
{
	public static final int SIZE 	= 3;
	public static final int BYTES 	= 3 * Integer.BYTES;
	
	public int x;
	public int y;
	public int z;
	
	public Vector3i(int fill)
	{
		this.x = fill;
		this.y = fill;
		this.z = fill;
	}
	
	public Vector3i(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3i(int[] array)
	{
		this.x = array[0];
		this.y = array[1];
		this.z = array[2];
	}
	
	public Vector3i set(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vector3i set(Vector3i vec3)
	{
		this.x = vec3.x;
		this.y = vec3.y;
		this.z = vec3.z;
		return this;
	}
	
	public Vector3i copy()
	{
		return new Vector3i(x, y, z);
	}
	
	public Vector3i(Vector2i vec2, int z)
	{
		this.x = vec2.x;
		this.y = vec2.y;
		this.z = z;
	}
	
	public Vector3i add(Vector3i vec3)
	{
		x += vec3.x;
		y += vec3.y;
		z += vec3.z;
		return this;
	}
	
	public Vector3i add(Vector2i vec2)
	{
		x += vec2.x;
		y += vec2.y;
		return this;
	}
	
	public Vector3i sub(Vector3i vec3)
	{
		x -= vec3.x;
		y -= vec3.y;
		z -= vec3.z;
		return this;
	}
	
	public Vector3i sub(Vector2i vec2)
	{
		x -= vec2.x;
		y -= vec2.y;
		return this;
	}
	
	public Vector3i mul(Vector3i vec3)
	{
		x *= vec3.x;
		y *= vec3.y;
		z *= vec3.z;
		return this;
	}
	
	public Vector3i mul(Vector2i vec2)
	{
		x *= vec2.x;
		y *= vec2.y;
		return this;
	}
	
	public Vector3i mul(int num)
	{
		x *= num;
		y *= num;
		z *= num;
		return this;
	}
	
	public Vector3i div(Vector3i vec3)
	{
		x /= vec3.x;
		y /= vec3.y;
		z /= vec3.z;
		return this;
	}
	
	public Vector3i div(Vector2i vec2)
	{
		x /= vec2.x;
		y /= vec2.y;
		return this;
	}
	
	public Vector3i normalize()
	{
		float magnitude = length();
		x /= magnitude;
		y /= magnitude;
		z /= magnitude;
		return this;
	}
	
	public float length()
	{
		return (float)Math.sqrt((x * x) + (y * y) + (z * z));
	}
	
	public IntBuffer toIntBuffer(boolean flip)
	{
		IntBuffer buffer = IntBuffer.allocate(SIZE);
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		if(flip) buffer.flip();
		return buffer;
	}
	
	public IntBuffer toIntBuffer()
	{
		return toIntBuffer(false);
	}
	
	public int[] toArray()
	{
		return new int[] { x, y, z };
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}
}
