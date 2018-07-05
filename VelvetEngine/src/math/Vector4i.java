package math;

import java.nio.IntBuffer;

public class Vector4i
{
	public static final int SIZE 	= 4;
	public static final int BYTES 	= 4 * Integer.BYTES;
	
	public int x;
	public int y;
	public int z;
	public int w;
	
	public Vector4i(int fill)
	{
		this.x = fill;
		this.y = fill;
		this.z = fill;
		this.w = fill;
	}
	
	public Vector4i(int x, int y, int z, int w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4i set(int x, int y, int z, int w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	public Vector4i set(Vector4i vec4)
	{
		this.x = vec4.x;
		this.y = vec4.y;
		this.z = vec4.z;
		this.w = vec4.w;
		return this;
	}
	
	public Vector4i copy()
	{
		return new Vector4i(x, y, z, w);
	}
	
	public Vector4i(Vector3i vec3, int w)
	{
		this.x = vec3.x;
		this.y = vec3.y;
		this.z = vec3.z;
		this.w = w;
	}
	
	public Vector4i(Vector2i vec2, int z, int w)
	{
		this.x = vec2.x;
		this.y = vec2.y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4i add(Vector4i vec4)
	{
		x += vec4.x;
		y += vec4.y;
		z += vec4.z;
		w += vec4.w;
		return this;
	}
	
	public Vector4i add(Vector3i vec3)
	{
		x += vec3.x;
		y += vec3.y;
		z += vec3.z;
		return this;
	}
	
	public Vector4i add(Vector2i vec2)
	{
		x += vec2.x;
		y += vec2.y;
		return this;
	}
	
	public Vector4i sub(Vector4i vec4)
	{
		x -= vec4.x;
		y -= vec4.y;
		z -= vec4.z;
		w -= vec4.w;
		return this;
	}
	
	public Vector4i sub(Vector3i vec3)
	{
		x -= vec3.x;
		y -= vec3.y;
		z -= vec3.z;
		return this;
	}
	
	public Vector4i sub(Vector2i vec2)
	{
		x -= vec2.x;
		y -= vec2.y;
		return this;
	}
	
	public Vector4i mul(Vector4i vec4)
	{
		x *= vec4.x;
		y *= vec4.y;
		z *= vec4.z;
		w *= vec4.w;
		return this;
	}
	
	public Vector4i mul(Vector3i vec3)
	{
		x *= vec3.x;
		y *= vec3.y;
		z *= vec3.z;
		return this;
	}
	
	public Vector4i mul(Vector2i vec2)
	{
		x *= vec2.x;
		y *= vec2.y;
		return this;
	}
	
	public Vector4i mul(int num)
	{
		x *= num;
		y *= num;
		z *= num;
		w *= num;
		return this;
	}
	
	public Vector4i div(Vector4i vec4)
	{
		x /= vec4.x;
		y /= vec4.y;
		z /= vec4.z;
		w /= vec4.w;
		return this;
	}
	
	public Vector4i div(Vector3i vec3)
	{
		x /= vec3.x;
		y /= vec3.y;
		z /= vec3.z;
		return this;
	}
	
	public Vector4i div(Vector2i vec2)
	{
		x /= vec2.x;
		y /= vec2.y;
		return this;
	}
	
	public Vector4i normalize()
	{
		float magnitude = length();
		x /= magnitude;
		y /= magnitude;
		z /= magnitude;
		w /= magnitude;
		return this;
	}
	
	public float length()
	{
		return (float)Math.sqrt((x * x) + (y * y) + (z * z) + (w * w));
	}
	
	public IntBuffer toIntBuffer(boolean flip)
	{
		IntBuffer buffer = IntBuffer.allocate(SIZE);
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		buffer.put(w);
		if(flip) buffer.flip();
		return buffer;
	}
	
	public IntBuffer toIntBuffer()
	{
		return toIntBuffer(false);
	}
	
	public int[] toArray()
	{
		return new int[] { x, y, z, w };
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + ", " + z + ", " + w + "]";
	}
}
