package math;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

/**
 * A container (vector) of 3 integer values
 * 
 * @author Ben Ratcliff (NebulousDev)
 * 
 * @see Vector4i
 * @see Vector2i
 */

public class Vector3i {
	
	/**
	 * The x component
	 */
	public int x;
	
	/**
	 * The y component
	 */
	public int y;
	
	/**
	 * The z component
	 */
	public int z;
	
	/**
	 * Construct a 3-integer vector
	 */
	public Vector3i()
	{
		this(0, 0, 0);
	}
	
	/**
	 * Construct a 3-integer vector
	 * 
	 * @param value - x, y, and z components
	 */
	public Vector3i(int value)
	{
		this(value, value, value);
	}
	
	/**
	 * Construct a 3-integer vector
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 */
	public Vector3i(int vx, int vy, int vz)
	{
		x = (int)vx;
		y = (int)vy;
		z = (int)vz;
	}
	
	/**
	 * Construct a 3-integer vector
	 * 
	 * @param vec3f - x, y and z components
	 */
	public Vector3i(Vector3f vec3f)
	{
		this((int)vec3f.x, (int)vec3f.y, (int)vec3f.z);
	}
	
	/**
	 * Construct a 3-integer vector
	 * 
	 * @param vec3i - x, y and z components
	 */
	public Vector3i(Vector3i vec3i)
	{
		this(vec3i.x, vec3i.y, vec3i.z);
	}
	
	/**
	 * Construct a 3-integer vector
	 * 
	 * @param vec2f - x and y components
	 * @param vz - z component
	 */
	public Vector3i(Vector2f vec2f, int vz)
	{
		this((int)vec2f.x, (int)vec2f.y, vz);
	}
	
	/**
	 * Construct a 3-integer vector
	 * 
	 * @param vec2i - x, y, and z components
	 * @param vw - w component
	 */
	public Vector3i(Vector2i vec2i, int vz)
	{
		this(vec2i.x, vec2i.y, vz);
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
	public Vector3i set(int vx, int vy, int vz)
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
	public Vector3i set(Vector3f vec3f)
	{
		this.x = (int)vec3f.x;
		this.y = (int)vec3f.y;
		this.z = (int)vec3f.z;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec3i - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3i set(Vector3i vec3i)
	{
		this.x = vec3i.x;
		this.y = vec3i.y;
		this.z = vec3i.z;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2f - x and y components
	 * 
	 * @return this
	 */
	public Vector3i set(Vector2f vec2f)
	{
		this.x = (int)vec2f.x;
		this.y = (int)vec2f.y;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2i - x and y components
	 * 
	 * @return this
	 */
	public Vector3i set(Vector2i vec2i)
	{
		this.x = vec2i.x;
		this.y = vec2i.y;
		
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
	public Vector3i set(Vector2f vec2f, int vz)
	{
		return set((int)vec2f.x, (int)vec2f.y, vz);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2i - x and y components
	 * @param vz - the z component
	 * 
	 * @return this
	 */
	public Vector3i set(Vector2i vec2i, int vz)
	{
		return set(vec2i.x, vec2i.y, vz);
	}
	
	/**
	 * Set a component by index
	 * 
	 * @param index - component index
	 * @param value - component value
	 * @throws IndexOutOfBoundsException if index < 0 or index > 2
	 */
	public void set(int index, int value)
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
	public Vector3i copy()
	{
		return new Vector3i(x, y, z);
	}
	
	/**
	 * Copies components to destination
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3i copy(Vector3i dest)
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
	public Vector3i add(int vx, int vy, int vz, Vector3i dest)
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
	public Vector3i add(int vx, int vy, int vz)
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
	public Vector3i add(Vector3f vec3f, Vector3i dest)
	{
		dest.x = x + (int)vec3f.x;
		dest.y = y + (int)vec3f.y;
		dest.z = z + (int)vec3f.z;
		
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
	public Vector3i add(Vector3i vec3i, Vector3i dest)
	{
		dest.x = x + vec3i.x;
		dest.y = y + vec3i.y;
		dest.z = z + vec3i.z;
		
		return this;
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3i add(Vector3f vec3f)
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
	public Vector3i add(Vector3i vec3i)
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
	public Vector3i add(Vector2f vec2f, Vector3i dest)
	{
		dest.x = (int)(x + vec2f.x);
		dest.y = (int)(y + vec2f.y);
		
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
	public Vector3i add(Vector2i vec2i, Vector3i dest)
	{
		dest.x = x + vec2i.x;
		dest.y = y + vec2i.y;
		
		return this;
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector3i add(Vector2f vec2f)
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
	public Vector3i add(Vector2i vec2i)
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
	public Vector3i sub(int vx, int vy, int vz, Vector3i dest)
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
	public Vector3i sub(int vx, int vy, int vz)
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
	public Vector3i sub(Vector3f vec3f, Vector3i dest)
	{
		dest.x = x - (int)vec3f.x;
		dest.y = y - (int)vec3f.y;
		dest.z = z - (int)vec3f.z;
		
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
	public Vector3i sub(Vector3i vec3i, Vector3i dest)
	{
		dest.x = x - vec3i.x;
		dest.y = y - vec3i.y;
		dest.z = z - vec3i.z;
		
		return this;
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3i sub(Vector3f vec3f)
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
	public Vector3i sub(Vector3i vec3i)
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
	public Vector3i sub(Vector2f vec2f, Vector3i dest)
	{
		dest.x = (int)(x - vec2f.x);
		dest.y = (int)(y - vec2f.y);
		
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
	public Vector3i sub(Vector2i vec2i, Vector3i dest)
	{
		dest.x = x - vec2i.x;
		dest.y = y - vec2i.y;
		
		return this;
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector3i sub(Vector2f vec2f)
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
	public Vector3i sub(Vector2i vec2i)
	{
		return sub(vec2i, this);
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param scalar - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3i mul(int scalar, Vector3i dest)
	{
		dest.x = x * scalar;
		dest.y = y * scalar;
		dest.z = z * scalar;
		
		return this;
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param scalar - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3i mul(int scalar)
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
	public Vector3i mul(int vx, int vy, int vz, Vector3i dest)
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
	public Vector3i mul(int vx, int vy, int vz)
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
	public Vector3i mul(Vector3f vec3f, Vector3i dest)
	{
		dest.x = x * (int)vec3f.x;
		dest.y = y * (int)vec3f.y;
		dest.z = z * (int)vec3f.z;
		
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
	public Vector3i mul(Vector3i vec3i, Vector3i dest)
	{
		dest.x = x * vec3i.x;
		dest.y = y * vec3i.y;
		dest.z = z * vec3i.z;
		
		return this;
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3i mul(Vector3f vec3f)
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
	public Vector3i mul(Vector3i vec3i)
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
	public Vector3i mul(Vector2f vec2f, Vector3i dest)
	{
		dest.x = (int)(x * vec2f.x);
		dest.y = (int)(y * vec2f.y);
		
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
	public Vector3i mul(Vector2i vec2i, Vector3i dest)
	{
		dest.x = x * vec2i.x;
		dest.y = y * vec2i.y;
		
		return this;
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector3i mul(Vector2f vec2f)
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
	public Vector3i mul(Vector2i vec2i)
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
	public Vector3i div(int vx, int vy, int vz, Vector3i dest)
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
	public Vector3i div(int vx, int vy, int vz)
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
	public Vector3i div(Vector3f vec3f, Vector3i dest)
	{
		dest.x = x / (int)vec3f.x;
		dest.y = y / (int)vec3f.y;
		dest.z = z / (int)vec3f.z;
		
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
	public Vector3i div(Vector3i vec3i, Vector3i dest)
	{
		dest.x = x / vec3i.x;
		dest.y = y / vec3i.y;
		dest.z = z / vec3i.z;
		
		return this;
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector3i div(Vector3f vec3f)
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
	public Vector3i div(Vector3i vec3i)
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
	public Vector3i div(Vector2f vec2f, Vector3i dest)
	{
		dest.x = (int)(x / vec2f.x);
		dest.y = (int)(y / vec2f.y);
		
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
	public Vector3i div(Vector2i vec2i, Vector3i dest)
	{
		dest.x = x / vec2i.x;
		dest.y = y / vec2i.y;
		
		return this;
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector3i div(Vector2f vec2f)
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
	public Vector3i div(Vector2i vec2i)
	{
		return div(vec2i, this);
	}
	
	/**
	 * The squared magnitude
	 * 
	 * @return magnitude
	 */
	public int magnitudeSquared()
	{
		return x * x + y * y + z * z;
	}
	
	/**
	 * The magnitude
	 * 
	 * @return magnitude
	 */
	public int magnitude()
	{
		return (int)Math.sqrt((double)magnitudeSquared() + 0.5);
	}
	
	/**
	 * Normalize components
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3i normalize(Vector3i dest)
	{
		float inv = 1.0f / magnitude();
		dest.x = (int)(x * inv);
		dest.y = (int)(y * inv);
		dest.z = (int)(z * inv);
		
		return this;
	}
	
	/**
	 * Normalize components
	 * 
	 * @return this
	 */
	public Vector3i normalize()
	{
		return normalize(this);
	}
	
	/**
	 * Dot product
	 * 
	 * @param vec3i - dotted vector
	 * 
	 * @return dot product
	 */
	public float dot(Vector3i vec3i)
	{
		return x * (int)vec3i.x + y * (int)vec3i.y + z * (int)vec3i.z;
	}
	
	/**
	 * Cross product
	 * 
	 * @param vec3f - crossed vector
	 * @param dest - result vector
	 * @return this
	 */
	public Vector3i cross(Vector3i vec3i, Vector3i dest)
	{
		dest.x = y * vec3i.z - z * vec3i.y;
		dest.y = z * vec3i.x - x * vec3i.z;
		dest.z = x * vec3i.y - y * vec3i.x;
		return this;
	}
	
	/**
	 * Cross product
	 * 
	 * @param vec3f - crossed vector
	 * @return result vector
	 */
	public Vector3i cross(Vector3i vec3i)
	{
		int cx = y * vec3i.z - z * vec3i.y;
		int cy = z * vec3i.x - x * vec3i.z;
		int cz = x * vec3i.y - y * vec3i.x;
		return new Vector3i(cx, cy, cz);
	}
	
	/**
	 * Swizzle to Vector2i
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector3i xy(Vector2i dest)
	{
		dest.x = x;
		dest.y = y;
		
		return this;
	}
	
	/**
	 * Swizzle to Vector2i
	 * 
	 * @param dest - result vector
	 * 
	 * @return swizzled Vector2f
	 */
	public Vector2i xy()
	{
		return new Vector2i(x, y);
	}
	
	/**
	 * The size in bytes
	 * 
	 * @return size
	 */
	public int sizeInBytes()
	{
		return Integer.SIZE * 3;
	}
	
	/**
	 * Store components in a IntBuffer
	 * 
	 * @param flip - automatically flip the buffer
	 * 
	 * @return this
	 * 
	 * @see IntBuffer
	 */
	public Vector3i putIntBuffer(IntBuffer buffer, boolean flip)
	{
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		if(flip) buffer.flip();
		return this;
	}
	
	/**
	 * Store components in a IntBuffer
	 * 
	 * @return this
	 * 
	 * @see IntBuffer
	 */
	public Vector3i putIntBuffer(IntBuffer buffer)
	{
		return putIntBuffer(buffer, false);
	}
	
	/**
	 * Return new IntBuffer of components
	 * 
	 * @param flip - automatically flip the buffer
	 * 
	 * @return new buffer
	 * 
	 * @see IntBuffer
	 */
	public IntBuffer toIntBuffer(boolean flip)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(sizeInBytes());
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		if(flip) buffer.flip();
		return buffer;
	}
	
	/**
	 * Return new IntBuffer of components
	 * 
	 * @return new buffer
	 * 
	 * @see IntBuffer
	 */
	public IntBuffer toIntBuffer()
	{
		return toIntBuffer(false);
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
