package math;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

/**
 * A container (vector) of 2 integer values
 * 
 * @author Ben Ratcliff (NebulousDev)
 * 
 * @see Vector4i
 * @see Vector3i
 */

public class Vector2i {

	/**
	 * The x component
	 */
	public int x;
	
	/**
	 * The y component
	 */
	public int y;
	
	/**
	 * Construct a 2-integer vector
	 */
	public Vector2i()
	{
		this(0, 0);
	}
	
	/**
	 * Construct a 2-integer vector
	 * 
	 * @param value - x, and y components
	 */
	public Vector2i(int value)
	{
		this(value, value);
	}
	
	/**
	 * Construct a 2-integer vector
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 */
	public Vector2i(int vx, int vy)
	{
		x = vx;
		y = vy;
	}
	
	/**
	 * Construct a 2-integer vector
	 * 
	 * @param vec2f - x and y components
	 * @param vz - z component
	 */
	public Vector2i(Vector2f vec2f)
	{
		this((int)vec2f.x, (int)vec2f.y);
	}
	
	/**
	 * Construct a 2-integer vector
	 * 
	 * @param vec2i - x, y, and z components
	 * @param vw - w component
	 */
	public Vector2i(Vector2i vec2i)
	{
		this(vec2i.x, vec2i.y);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * 
	 * @return this
	 */
	public Vector2i set(int vx, int vy)
	{
		this.x = vx;
		this.y = vy;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2f - x and y components
	 * 
	 * @return this
	 */
	public Vector2i set(Vector2f vec2f)
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
	public Vector2i set(Vector2i vec2i)
	{
		this.x = vec2i.x;
		this.y = vec2i.y;
		
		return this;
	}
	
	/**
	 * Set a component by index
	 * 
	 * @param index - component index
	 * @param value - component value
	 * @throws IndexOutOfBoundsException if index < 0 or index > 1
	 */
	public void set(int index, Integer value)
	{
		switch (index)
		{
			case 0: x = value; return;
			case 1: y = value; return;
			default: break;
		}
		
		throw new IndexOutOfBoundsException();
	}
	
	/**
	 * Retrieve a component by index
	 * 
	 * @param index - component index
	 * @throws IndexOutOfBoundsException if index < 0 or index > 1
	 * @return value at index
	 */
	public float get(int index)
	{
		switch (index)
		{
			case 0: return x;
			case 1: return y;
			default: break;
		}
		
		throw new IndexOutOfBoundsException();
	}
	
	/**
	 * Copies components to a new instance
	 * 
	 * @return new copy
	 */
	public Vector2i copy()
	{
		return new Vector2i(x, y);
	}
	
	/**
	 * Copies components to destination
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2i copy(Vector2i dest)
	{
		return dest.set(this);
	}
	
	/**
	 * Add component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2i add(int vx, int vy, Vector2i dest)
	{
		dest.x = x + vx;
		dest.y = y + vy;
		
		return this;
	}
	
	/**
	 * Add component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * 
	 * @return this
	 */
	public Vector2i add(int vx, int vy)
	{
		return add(vx, vy, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2i add(Vector2f vec2f, Vector2i dest)
	{
		dest.x = x + (int)vec2f.x;
		dest.y = y + (int)vec2f.y;
		
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
	public Vector2i add(Vector2i vec2i, Vector2i dest)
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
	public Vector2i add(Vector2f vec2f)
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
	public Vector2i add(Vector2i vec2i)
	{
		return add(vec2i, this);
	}
	
	/**
	 * Subtract component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2i sub(int vx, int vy, Vector2i dest)
	{
		dest.x = x - vx;
		dest.y = y - vy;
		
		return this;
	}
	
	/**
	 * Subtract component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * 
	 * @return this
	 */
	public Vector2i sub(int vx, int vy)
	{
		return sub(vx, vy, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2i sub(Vector2f vec2f, Vector2i dest)
	{
		dest.x = x - (int)vec2f.x;
		dest.y = y - (int)vec2f.y;
		
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
	public Vector2i sub(Vector2i vec2i, Vector2i dest)
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
	public Vector2i sub(Vector2f vec2f)
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
	public Vector2i sub(Vector2i vec2i)
	{
		return sub(vec2i, this);
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param scalar - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2i mul(int scalar, Vector2i dest)
	{
		dest.x = x * scalar;
		dest.y = y * scalar;
		
		return this;
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param scalar - x, and y components
	 * 
	 * @return this
	 */
	public Vector2i mul(int scalar)
	{
		return mul(scalar, this);
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2i mul(int vx, int vy, Vector2i dest)
	{
		dest.x = x * vx;
		dest.y = y * vy;
		
		return this;
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * 
	 * @return this
	 */
	public Vector2i mul(int vx, int vy)
	{
		return mul(vx, vy, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2i mul(Vector2f vec2f, Vector2i dest)
	{
		dest.x = x * (int)vec2f.x;
		dest.y = y * (int)vec2f.y;
		
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
	public Vector2i mul(Vector2i vec2i, Vector2i dest)
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
	public Vector2i mul(Vector2f vec2f)
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
	public Vector2i mul(Vector2i vec2i)
	{
		return mul(vec2i, this);
	}
	
	/**
	 * Divide component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2i div(int vx, int vy, Vector2i dest)
	{
		dest.x = x / vx;
		dest.y = y / vy;
		
		return this;
	}
	
	/**
	 * Divide component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * 
	 * @return this
	 */
	public Vector2i div(int vx, int vy)
	{
		return div(vx, vy, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2i div(Vector2f vec2f, Vector2i dest)
	{
		dest.x = x / (int)vec2f.x;
		dest.y = y / (int)vec2f.y;
		
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
	public Vector2i div(Vector2i vec2i, Vector2i dest)
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
	public Vector2i div(Vector2f vec2f)
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
	public Vector2i div(Vector2i vec2i)
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
		return x * x + y * y;
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
	public Vector2i normalize(Vector2i dest)
	{
		float inv = 1.0f / magnitude();
		dest.x = (int)(x * inv);
		dest.y = (int)(y * inv);
		
		return this;
	}
	
	/**
	 * Normalize components
	 * 
	 * @return this
	 */
	public Vector2i normalize()
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
	public int dot(Vector2i vec2i)
	{
		return x * vec2i.x + y * vec2i.y;
	}
	
	/**
	 * The size in bytes
	 * 
	 * @return size
	 */
	public int sizeInBytes()
	{
		return Integer.SIZE * 2;
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
	public Vector2i putIntBuffer(IntBuffer buffer, boolean flip)
	{
		buffer.put(x);
		buffer.put(y);
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
	public Vector2i putIntBuffer(IntBuffer buffer)
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
		return "[" + x + ", " + y + "]";
	}
	
}
