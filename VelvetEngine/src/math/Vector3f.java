package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * A container (vector) of 3 float values
 * 
 * @author Ben Ratcliff (NebulousDev)
 * 
 * @see Matrix4f
 * @see Vector4f
 * @see Vector2f
 */

public class Vector3f {
	
	/**
	 * The x component
	 */
	public float x;
	
	/**
	 * The y component
	 */
	public float y;
	
	/**
	 * The z component
	 */
	public float z;
	
	/**
	 * Construct a 3-float vector
	 */
	public Vector3f()
	{
		this(0, 0, 0);
	}
	
	/**
	 * Construct a 3-float vector
	 * 
	 * @param value - x, y, and z components
	 */
	public Vector3f(float value)
	{
		this(value, value, value);
	}
	
	/**
	 * Construct a 3-float vector
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 */
	public Vector3f(float vx, float vy, float vz)
	{
		x = vx;
		y = vy;
		z = vz;
	}
	
	/**
	 * Construct a 3-float vector
	 * 
	 * @param vec3f - x, y and z components
	 */
	public Vector3f(Vector3f vec3f)
	{
		this(vec3f.x, vec3f.y, vec3f.z);
	}
	
	/**
	 * Construct a 3-float vector
	 * 
	 * @param vec3i - x, y and z components
	 */
	public Vector3f(Vector3i vec3i)
	{
		this((float)vec3i.x, (float)vec3i.y, (float)vec3i.z);
	}
	
	/**
	 * Construct a 3-float vector
	 * 
	 * @param vec2f - x and y components
	 * @param vz - z component
	 */
	public Vector3f(Vector2f vec2f, float vz)
	{
		this(vec2f.x, vec2f.y, vz);
	}
	
	/**
	 * Construct a 3-float vector
	 * 
	 * @param vec2i - x, y, and z components
	 * @param vw - w component
	 */
	public Vector3f(Vector2i vec2i, float vz)
	{
		this((float)vec2i.x, (float)vec2i.y, vz);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * 
	 * @return this
	 */
	public Vector3f set(float vx, float vy, float vz)
	{
		this.x = vx;
		this.y = vy;
		this.z = vz;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3f set(Vector3f vec3f)
	{
		this.x = vec3f.x;
		this.y = vec3f.y;
		this.z = vec3f.z;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec3i - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3f set(Vector3i vec3i)
	{
		this.x = (float)vec3i.x;
		this.y = (float)vec3i.y;
		this.z = (float)vec3i.z;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2f - x and y components
	 * 
	 * @return this
	 */
	public Vector3f set(Vector2f vec2f)
	{
		this.x = vec2f.x;
		this.y = vec2f.y;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2i - x and y components
	 * 
	 * @return this
	 */
	public Vector3f set(Vector2i vec2i)
	{
		this.x = (float)vec2i.x;
		this.y = (float)vec2i.y;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2f - x and y components
	 * @param vz - the z component
	 * 
	 * @return this
	 */
	public Vector3f set(Vector2f vec2f, float vz)
	{
		return set(vec2f.x, vec2f.y, vz);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2i - x and y components
	 * @param vz - the z component
	 * 
	 * @return this
	 */
	public Vector3f set(Vector2i vec2i, float vz)
	{
		return set((float)vec2i.x, (float)vec2i.y, vz);
	}
	
	/**
	 * Set a component by index
	 * 
	 * @param index - component index
	 * @param value - component value
	 * @throws IndexOutOfBoundsException if index < 0 or index > 2
	 */
	public void set(int index, float value)
	{
		switch (index)
		{
			case 0: x = value; return;
			case 1: y = value; return;
			case 2: z = value; return;
			default: break;
		}
		
		throw new IndexOutOfBoundsException();
	}
	
	/**
	 * Retrieve a component by index
	 * 
	 * @param index - component index
	 * @throws IndexOutOfBoundsException if index < 0 or index > 2
	 * @return value at index
	 */
	public float get(int index)
	{
		switch (index)
		{
			case 0: return x;
			case 1: return y;
			case 2: return z;
			default: break;
		}
		
		throw new IndexOutOfBoundsException();
	}
	
	/**
	 * Copies components to a new instance
	 * 
	 * @return new copy
	 */
	public Vector3f copy()
	{
		return new Vector3f(x, y, z);
	}
	
	/**
	 * Copies components to destination
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f copy(Vector3f dest)
	{
		return dest.set(this);
	}
	
	/**
	 * Add component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f add(float vx, float vy, float vz, Vector3f dest)
	{
		dest.x = x + vx;
		dest.y = y + vy;
		dest.z = z + vz;
		
		return this;
	}
	
	/**
	 * Add component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * 
	 * @return this
	 */
	public Vector3f add(float vx, float vy, float vz)
	{
		return add(vx, vy, vz, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f add(Vector3f vec3f, Vector3f dest)
	{
		dest.x = x + vec3f.x;
		dest.y = y + vec3f.y;
		dest.z = z + vec3f.z;
		
		return this;
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f add(Vector3i vec3i, Vector3f dest)
	{
		dest.x = x + (float)vec3i.x;
		dest.y = y + (float)vec3i.y;
		dest.z = z + (float)vec3i.z;
		
		return this;
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3f add(Vector3f vec3f)
	{
		return add(vec3f, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3f add(Vector3i vec3i)
	{
		return add(vec3i, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f add(Vector2f vec2f, Vector3f dest)
	{
		dest.x = x + vec2f.x;
		dest.y = y + vec2f.y;
		
		return this;
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec2i - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f add(Vector2i vec2i, Vector3f dest)
	{
		dest.x = x + (float)vec2i.x;
		dest.y = y + (float)vec2i.y;
		
		return this;
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector3f add(Vector2f vec2f)
	{
		return add(vec2f, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec2i - x, and y components
	 * 
	 * @return this
	 */
	public Vector3f add(Vector2i vec2i)
	{
		return add(vec2i, this);
	}
	
	/**
	 * Subtract component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f sub(float vx, float vy, float vz, Vector3f dest)
	{
		dest.x = x - vx;
		dest.y = y - vy;
		dest.z = z - vz;
		
		return this;
	}
	
	/**
	 * Subtract component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * 
	 * @return this
	 */
	public Vector3f sub(float vx, float vy, float vz)
	{
		return sub(vx, vy, vz, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f sub(Vector3f vec3f, Vector3f dest)
	{
		dest.x = x - vec3f.x;
		dest.y = y - vec3f.y;
		dest.z = z - vec3f.z;
		
		return this;
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f sub(Vector3i vec3i, Vector3f dest)
	{
		dest.x = x - (float)vec3i.x;
		dest.y = y - (float)vec3i.y;
		dest.z = z - (float)vec3i.z;
		
		return this;
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3f sub(Vector3f vec3f)
	{
		return sub(vec3f, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3f sub(Vector3i vec3i)
	{
		return sub(vec3i, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f sub(Vector2f vec2f, Vector3f dest)
	{
		dest.x = x - vec2f.x;
		dest.y = y - vec2f.y;
		
		return this;
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2i - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f sub(Vector2i vec2i, Vector3f dest)
	{
		dest.x = x - (float)vec2i.x;
		dest.y = y - (float)vec2i.y;
		
		return this;
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector3f sub(Vector2f vec2f)
	{
		return sub(vec2f, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2i - x, and y components
	 * 
	 * @return this
	 */
	public Vector3f sub(Vector2i vec2i)
	{
		return sub(vec2i, this);
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param value - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f mul(float scalar, Vector3f dest)
	{
		dest.x = x * scalar;
		dest.y = y * scalar;
		dest.z = z * scalar;
		
		return this;
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param value - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3f mul(float scalar)
	{
		return mul(scalar, this);
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f mul(float vx, float vy, float vz, Vector3f dest)
	{
		dest.x = x * vx;
		dest.y = y * vy;
		dest.z = z * vz;
		
		return this;
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * 
	 * @return this
	 */
	public Vector3f mul(float vx, float vy, float vz)
	{
		return mul(vx, vy, vz, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f mul(Vector3f vec3f, Vector3f dest)
	{
		dest.x = x * vec3f.x;
		dest.y = y * vec3f.y;
		dest.z = z * vec3f.z;
		
		return this;
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f mul(Vector3i vec3i, Vector3f dest)
	{
		dest.x = x * (float)vec3i.x;
		dest.y = y * (float)vec3i.y;
		dest.z = z * (float)vec3i.z;
		
		return this;
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3f mul(Vector3f vec3f)
	{
		return mul(vec3f, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3f mul(Vector3i vec3i)
	{
		return mul(vec3i, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f mul(Vector2f vec2f, Vector3f dest)
	{
		dest.x = x * vec2f.x;
		dest.y = y * vec2f.y;
		
		return this;
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec2i - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f mul(Vector2i vec2i, Vector3f dest)
	{
		dest.x = x * (float)vec2i.x;
		dest.y = y * (float)vec2i.y;
		
		return this;
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector3f mul(Vector2f vec2f)
	{
		return mul(vec2f, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec2i - x, and y components
	 * 
	 * @return this
	 */
	public Vector3f mul(Vector2i vec2i)
	{
		return mul(vec2i, this);
	}
	
	/**
	 * Divide component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f div(float vx, float vy, float vz, Vector3f dest)
	{
		dest.x = x / vx;
		dest.y = y / vy;
		dest.z = z / vz;
		
		return this;
	}
	
	/**
	 * Divide component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * 
	 * @return this
	 */
	public Vector3f div(float vx, float vy, float vz)
	{
		return div(vx, vy, vz, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f div(Vector3f vec3f, Vector3f dest)
	{
		dest.x = x / vec3f.x;
		dest.y = y / vec3f.y;
		dest.z = z / vec3f.z;
		
		return this;
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f div(Vector3i vec3i, Vector3f dest)
	{
		dest.x = x / (float)vec3i.x;
		dest.y = y / (float)vec3i.y;
		dest.z = z / (float)vec3i.z;
		
		return this;
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3f div(Vector3f vec3f)
	{
		return div(vec3f, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3f div(Vector3i vec3i)
	{
		return div(vec3i, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f div(Vector2f vec2f, Vector3f dest)
	{
		dest.x = x / vec2f.x;
		dest.y = y / vec2f.y;
		
		return this;
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec2i - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f div(Vector2i vec2i, Vector3f dest)
	{
		dest.x = x / (float)vec2i.x;
		dest.y = y / (float)vec2i.y;
		
		return this;
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector3f div(Vector2f vec2f)
	{
		return div(vec2f, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec2i - x, and y components
	 * 
	 * @return this
	 */
	public Vector3f div(Vector2i vec2i)
	{
		return div(vec2i, this);
	}
	
	/**
	 * The squared magnitude
	 * 
	 * @return magnitude
	 */
	public float magnitudeSquared()
	{
		return x * x + y * y + z * z;
	}
	
	/**
	 * The magnitude
	 * 
	 * @return magnitude
	 */
	public float magnitude()
	{
		return (float)Math.sqrt((double)magnitudeSquared() + 0.5);
	}
	
	/**
	 * Normalize components
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f normalize(Vector3f dest)
	{
		float inv = 1.0f / magnitude();
		dest.x = x * inv;
		dest.y = y * inv;
		dest.z = z * inv;
		
		return this;
	}
	
	/**
	 * Normalize components
	 * 
	 * @return this
	 */
	public Vector3f normalize()
	{
		return normalize(this);
	}
	
	/**
	 * Dot product
	 * 
	 * @param vec3f - dotted vector
	 * 
	 * @return dot product
	 */
	public float dot(Vector3f vec3f)
	{
		return x * vec3f.x + y * vec3f.y + z * vec3f.z;
	}
	
	/**
	 * Cross product
	 * 
	 * @param vec3f - crossed vector
	 * @param dest - result vector
	 * @return this
	 */
	public Vector3f cross(Vector3f vec3f, Vector3f dest)
	{
		dest.x = y * vec3f.z - z * vec3f.y;
		dest.y = z * vec3f.x - x * vec3f.z;
		dest.z = x * vec3f.y - y * vec3f.x;
		return this;
	}
	
	/**
	 * Cross product
	 * 
	 * @param vec3f - crossed vector
	 * @return result vector
	 */
	public Vector3f cross(Vector3f vec3f)
	{
		float cx = y * vec3f.z - z * vec3f.y;
		float cy = z * vec3f.x - x * vec3f.z;
		float cz = x * vec3f.y - y * vec3f.x;
		return new Vector3f(cx, cy, cz);
	}
	
	/**
	 * Swizzle to Vector2f
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3f xy(Vector2f dest)
	{
		dest.x = x;
		dest.y = y;
		
		return this;
	}
	
	/**
	 * Swizzle to Vector2f
	 * 
	 * @param dest - result vector
	 * 
	 * @return swizzled Vector2f
	 */
	public Vector2f xy()
	{
		return new Vector2f(x, y);
	}
	
	/**
	 * The size in bytes
	 * 
	 * @return size
	 */
	public int sizeInBytes()
	{
		return Float.SIZE * 3;
	}
	
	/**
	 * Store components in a FloatBuffer
	 * 
	 * @param flip - automatically flip the buffer
	 * 
	 * @return this
	 * 
	 * @see FloatBuffer
	 */
	public Vector3f putFloatBuffer(FloatBuffer buffer, boolean flip)
	{
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		if(flip) buffer.flip();
		return this;
	}
	
	/**
	 * Store components in a FloatBuffer
	 * 
	 * @return this
	 * 
	 * @see FloatBuffer
	 */
	public Vector3f putFloatBuffer(FloatBuffer buffer)
	{
		return putFloatBuffer(buffer, false);
	}
	
	/**
	 * Return new FloatBuffer of components
	 * 
	 * @param flip - automatically flip the buffer
	 * 
	 * @return new buffer
	 * 
	 * @see FloatBuffer
	 */
	public FloatBuffer toFloatBuffer(boolean flip)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(sizeInBytes());
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		if(flip) buffer.flip();
		return buffer;
	}
	
	/**
	 * Return new FloatBuffer of components
	 * 
	 * @return new buffer
	 * 
	 * @see FloatBuffer
	 */
	public FloatBuffer toFloatBuffer()
	{
		return toFloatBuffer(false);
	}
	
	/**
	 * Return string value
	 */
	@Override
	public String toString()
	{
		return "[" + x + ", " + y + ", " + z + "]";
	}
	
}
