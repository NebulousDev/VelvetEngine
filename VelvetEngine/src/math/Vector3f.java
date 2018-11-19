package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Vector3f
{
	public static final int SIZE 	= 3;
	public static final int BYTES 	= 3 * Float.BYTES;
	
	public float x;
	public float y;
	public float z;
	
	public Vector3f(float fill)
	{
		this.x = fill;
		this.y = fill;
		this.z = fill;
	}
	
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(Vector2f vec2, float z)
	{
		this.x = vec2.x;
		this.y = vec2.y;
		this.z = z;
	}
	
	public Vector3f(float[] array)
	{
		this.x = array[0];
		this.y = array[1];
		this.z = array[2];
	}

	public Vector3f set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vector3f set(Vector3f vec3)
	{
		this.x = vec3.x;
		this.y = vec3.y;
		this.z = vec3.z;
		return this;
	}
	
	public Vector3f copy()
	{
		return new Vector3f(x, y, z);
	}
	
	public Vector3f add(float dx, float dy, float dz)
	{
		x += dx;
		y += dy;
		z += dz;
		return this;
	}
	
	public Vector3f add(Vector3f vec3)
	{
		x += vec3.x;
		y += vec3.y;
		z += vec3.z;
		return this;
	}
	
	public Vector3f add(Vector2f vec2)
	{
		x += vec2.x;
		y += vec2.y;
		return this;
	}
	
	public Vector3f sub(float dx, float dy, float dz)
	{
		x -= dx;
		y -= dy;
		z -= dz;
		return this;
	}
	
	public Vector3f sub(Vector3f vec3)
	{
		x -= vec3.x;
		y -= vec3.y;
		z -= vec3.z;
		return this;
	}
	
	public Vector3f sub(Vector2f vec2)
	{
		x -= vec2.x;
		y -= vec2.y;
		return this;
	}
	
	public Vector3f mul(float dx, float dy, float dz)
	{
		x *= dx;
		y *= dy;
		z *= dz;
		return this;
	}
	
	public Vector3f mul(Vector3f vec3)
	{
		x *= vec3.x;
		y *= vec3.y;
		z *= vec3.z;
		return this;
	}
	
	public Vector3f mul(Vector2f vec2)
	{
		x *= vec2.x;
		y *= vec2.y;
		return this;
	}
	
	public Vector3f mul(float num)
	{
		x *= num;
		y *= num;
		z *= num;
		return this;
	}
	
	public Vector3f div(float dx, float dy, float dz)
	{
		x /= dx;
		y /= dy;
		z /= dz;
		return this;
	}
	
	public Vector3f div(Vector3f vec3)
	{
		x /= vec3.x;
		y /= vec3.y;
		z /= vec3.z;
		return this;
	}
	
	public Vector3f div(Vector2f vec2)
	{
		x /= vec2.x;
		y /= vec2.y;
		return this;
	}
	
	public Vector3f rotate(Vector3f axis, float angle)
	{
		float sinAngle = (float)Math.sin(-angle);
		float cosAngle = (float)Math.cos(-angle);

		return this.cross(axis.copy().mul(sinAngle))
			.add((this.copy().mul(cosAngle))
			.add(axis.copy().mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	public Vector3f rotate(Quaternion rot)
	{
		Quaternion conjugate = rot.copy().conjugate();
		Quaternion result = rot.copy().mul(this).mul(conjugate);
		return new Vector3f(result.x, result.y, result.z);
	}
	
	public Vector3f normalize()
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
	
	public float dot(Vector3f vec3)
	{
		return (x * vec3.x) + (y * vec3.y) + (z * vec3.z);
	}
	
	//TODO: look into cross modifying calling class
	public Vector3f cross(Vector3f vec3)
	{
		float rx = (y * vec3.z) - (z * vec3.y);
		float ry = (z * vec3.x) - (x * vec3.z);
		float rz = (x * vec3.y) - (y * vec3.x);
		return new Vector3f(rx, ry, rz);
	}
	
	public FloatBuffer toFloatBuffer(boolean flip)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(SIZE);
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		if(flip) buffer.flip();
		return buffer;
	}
	
	public FloatBuffer toFloatBuffer()
	{
		return toFloatBuffer(false);
	}
	
	public float[] toArray()
	{
		return new float[] { x, y, z };
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}
}
