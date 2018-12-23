package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Vector4f
{
	public static final int SIZE 	= 4;
	public static final int BYTES 	= 4 * Float.BYTES;
	
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Vector4f(float fill)
	{
		this.x = fill;
		this.y = fill;
		this.z = fill;
		this.w = fill;
	}
	
	public Vector4f(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4f(float[] array)
	{
		this.x = array[0];
		this.y = array[1];
		this.z = array[2];
		this.w = array[3];
	}
	
	public Vector4f set(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	public Vector4f set(Vector4f vec4)
	{
		this.x = vec4.x;
		this.y = vec4.y;
		this.z = vec4.z;
		this.w = vec4.w;
		return this;
	}
	
	public Vector4f copy()
	{
		return new Vector4f(x, y, z, w);
	}
	
	public Vector4f(Vector3f vec3, float w)
	{
		this.x = vec3.x;
		this.y = vec3.y;
		this.z = vec3.z;
		this.w = w;
	}
	
	public Vector4f(Vector2f vec2, float z, float w)
	{
		this.x = vec2.x;
		this.y = vec2.y;
		this.z = z;
		this.w = w;
	}

	public Vector4f add(Vector4f vec4)
	{
		x += vec4.x;
		y += vec4.y;
		z += vec4.z;
		w += vec4.w;
		return this;
	}
	
	public Vector4f add(Vector3f vec3)
	{
		x += vec3.x;
		y += vec3.y;
		z += vec3.z;
		return this;
	}
	
	public Vector4f add(Vector2f vec2)
	{
		x += vec2.x;
		y += vec2.y;
		return this;
	}
	
	public Vector4f sub(Vector4f vec4)
	{
		x -= vec4.x;
		y -= vec4.y;
		z -= vec4.z;
		w -= vec4.w;
		return this;
	}
	
	public Vector4f sub(Vector3f vec3)
	{
		x -= vec3.x;
		y -= vec3.y;
		z -= vec3.z;
		return this;
	}
	
	public Vector4f sub(Vector2f vec2)
	{
		x -= vec2.x;
		y -= vec2.y;
		return this;
	}
	
	public Vector4f mul(Matrix4f mat4)
	{
		x = x * mat4.elements[0 + 0 * 4] + y * mat4.elements[1 + 0 * 4] + z * mat4.elements[2 + 0 * 4] + w * mat4.elements[3 + 0 * 4];
		y = x * mat4.elements[0 + 1 * 4] + y * mat4.elements[1 + 1 * 4] + z * mat4.elements[2 + 1 * 4] + w * mat4.elements[3 + 1 * 4];
		z = x * mat4.elements[0 + 2 * 4] + y * mat4.elements[1 + 2 * 4] + z * mat4.elements[2 + 2 * 4] + w * mat4.elements[3 + 2 * 4];
		return this;
	}
	
	public Vector4f mul(Vector4f vec4)
	{
		x *= vec4.x;
		y *= vec4.y;
		z *= vec4.z;
		w *= vec4.w;
		return this;
	}
	
	public Vector4f mul(Vector3f vec3)
	{
		x *= vec3.x;
		y *= vec3.y;
		z *= vec3.z;
		return this;
	}
	
	public Vector4f mul(Vector2f vec2)
	{
		x *= vec2.x;
		y *= vec2.y;
		return this;
	}
	
	public Vector4f mul(float num)
	{
		x *= num;
		y *= num;
		z *= num;
		w *= num;
		return this;
	}
	
	public Vector4f div(Vector4f vec4)
	{
		x /= vec4.x;
		y /= vec4.y;
		z /= vec4.z;
		w /= vec4.w;
		return this;
	}
	
	public Vector4f div(Vector3f vec3)
	{
		x /= vec3.x;
		y /= vec3.y;
		z /= vec3.z;
		return this;
	}
	
	public Vector4f div(Vector2f vec2)
	{
		x /= vec2.x;
		y /= vec2.y;
		return this;
	}
	
	public Vector4f normalize()
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
	
	public FloatBuffer toFloatBuffer(boolean flip)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(SIZE);
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		buffer.put(w);
		if(flip) buffer.flip();
		return buffer;
	}
	
	public FloatBuffer toFloatBuffer()
	{
		return toFloatBuffer(false);
	}
	
	public float[] toArray()
	{
		return new float[] { x, y, z, w };
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + ", " + z + ", " + w + "]";
	}
}
