package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * A 4-float quaternion vector
 * 
 * @author Ben Ratcliff (NebulousDev)
 * 
 * @see Matrix4f
 * @see Vector4f
 * @see Vector3f
 * @see Vector2f
 */

public class Quaternion {
	
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
	 * The w component
	 */
	public float w;
	
	/**
	 * Construct a quaternion
	 */
	public Quaternion()
	{
		this(0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	/**
	 * Construct a quaternion
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 */
	public Quaternion(float vx, float vy, float vz, float vw)
	{
		x = vx;
		y = vy;
		z = vz;
		w = vw;
	}
	
	/**
	 * Construct a quaternion
	 * 
	 * @param quat - x, y, z, and w components
	 */
	public Quaternion(Quaternion quat)
	{
		this(quat.x, quat.y, quat.z, quat.w);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 * 
	 * @return this
	 */
	public Quaternion set(float vx, float vy, float vz, float vw)
	{
		x = vx;
		y = vy;
		z = vz;
		w = vw;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param quat - x, y, z, w components
	 * 
	 * @return this
	 */
	public Quaternion set(Quaternion quat)
	{
		return set(quat.x, quat.y, quat.z, quat.w);
	}
	
	/**
	 * Set orientation by axis-angle
	 * 
	 * @param ax - x axis
	 * @param ay - y axis
	 * @param az - z axis
	 * @param theta - angle
	 * 
	 * @return this
	 */
	public Quaternion setAxisAngle(float ax, float ay, float az, float theta)
	{
		float halfSin = (float)Math.sin(Math.toRadians(theta) * 0.5f);
		float halfCos = (float)Math.cos(Math.toRadians(theta) * 0.5f);
		return set(ax * halfSin, ay * halfSin, az * halfSin, halfCos);
	}
	
	/**
	 * Set orientation by axis-angle
	 * 
	 * @param axis - x, y, and z axis
	 * @param theta - angle
	 * 
	 * @return this
	 */
	public Quaternion setAxisAngle(Vector3f axis, float theta)
	{
		return setAxisAngle(axis.x, axis.y, axis.z, theta);
	}
	
	/**
	 * Copy components to destination
	 * 
	 * @param dest - result quaternion
	 * 
	 * @return this
	 */
	public Quaternion copy(Quaternion dest)
	{
		dest.x = x;
		dest.y = y;
		dest.z = z;
		dest.w = w;
		
		return this;
	}
	
	/**
	 * Copy components to new instance
	 * 
	 * @return this
	 */
	public Quaternion copy()
	{
		return new Quaternion(x, y, z, w);
	}
	
	/**
	 * Multiply quaternions
	 * 
	 * @param quat - multiplied quaternion
	 * @param dest - result quaternion
	 * 
	 * @return this
	 */
	public Quaternion mul(Quaternion quat, Quaternion dest)
	{
		dest.x = w * quat.x + x * quat.w + y * quat.z - z * quat.y;
		dest.y = w * quat.y - x * quat.z + y * quat.w + z * quat.x;
		dest.z = w * quat.z + x * quat.y - y * quat.x + z * quat.w;
		dest.w = w * quat.w - x * quat.x - y * quat.y - z * quat.z;
		
		return this;
	}
	
	/**
	 * Multiply quaternions
	 * 
	 * @param quat - multiplied quaternion
	 * 
	 * @return this
	 */
	public Quaternion mul(Quaternion quat)
	{
		return mul(quat, this);
	}
	
	/**
	 * Rotate quaternion on local axis
	 * 
	 * @param quat - orientation
	 * @param dest - result quaternion
	 * 
	 * @return this
	 */
	public Quaternion rotateLocal(Quaternion quat, Quaternion dest)
	{
		quat.mul(this, dest);
		return this;
	}
	
	/**
	 * Rotate quaternion by local orientation
	 * 
	 * @param quat - orientation
	 * 
	 * @return this
	 */
	public Quaternion rotateLocal(Quaternion quat)
	{
		return rotateLocal(quat, this);
	}
	
	/**
	 * Rotate quaternion by orientation
	 * 
	 * @param quat - orientation
	 * @param dest - result quaternion
	 * 
	 * @return this
	 */
	public Quaternion rotate(Quaternion quat, Quaternion dest)
	{
		this.mul(quat, dest);
		return this;
	}
	
	/**
	 * Rotate quaternion by orientation
	 * 
	 * @param quat - orientation
	 * 
	 * @return this
	 */
	public Quaternion rotate(Quaternion quat)
	{
		return rotate(quat, this);
	}
	
	/**
	 * Rotate by axis-angle
	 * 
	 * @param ax - x axis
	 * @param ay - y axis
	 * @param az - z axis
	 * @param theta - angle
	 * @param dest - result quaternion
	 * 
	 * @return this
	 */
	public Quaternion rotate(float ax, float ay, float az, float theta, Quaternion dest)
	{
		float halfAngle = (float)Math.toRadians(theta) * 0.5f;
		float halfSin = (float)Math.sin(halfAngle);
		float halfCos = (float)Math.cos(halfAngle);
		float inverse = 1.0f / magnitude();
		
		float rx = ax * inverse * halfSin;
		float ry = ay * inverse * halfSin;
		float rz = az * inverse * halfSin;
		float rw = halfCos;
		
		dest.x = w * rx + x * rw + y * rz - z * ry;
		dest.y = w * ry - x * rz + y * rw + z * rx;
		dest.z = w * rz + x * ry - y * rx + z * rw;
		dest.w = w * rw - x * rx - y * ry - z * rz;
		
		return this;
	}
	
	/**
	 * Rotate by axis-angle
	 * 
	 * @param axis - x, y, and z axis
	 * @param theta - angle
	 * @param dest - result quaternion
	 * 
	 * @return this
	 */
	public Quaternion rotate(Vector3f axis, float theta, Quaternion dest)
	{
		return rotate(axis.x, axis.y, axis.z, theta, dest);
	}
	
	/**
	 * Rotate by axis-angle
	 * 
	 * @param axis - x, y, and z axis
	 * @param theta - angle
	 * 
	 * @return this
	 */
	public Quaternion rotate(Vector3f axis, float theta)
	{
		return rotate(axis.x, axis.y, axis.z, theta, this);
	}

	/**
	 * Conjugate quaternion
	 * 
	 * @param dest - result quaternion
	 * 
	 * @return this
	 */
	public Quaternion conjugate(Quaternion dest)
	{
		dest.x = -x;
		dest.y = -y;
		dest.z = -z;
		
		return this;
	}
	
	/**
	 * Conjugate quaternion
	 * 
	 * @return this
	 */
	public Quaternion conjugate()
	{
		return conjugate(this);
	}
	
	/**
     * Get forward vector
     * 
     * @param dest - result vector
     * 
     * @return this
     * 
     * @see Vector3f
     */
	public Quaternion getForward(Vector3f dest)
	{
		dest.set(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		return this;
	}
	
	/**
     * Get forward vector
     * 
     * @return forward
     * 
     * @see Vector3f
     */
	public Vector3f getForward()
	{
		return new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
	}
	
	/**
     * Get back vector
     * 
     * @param dest - result vector
     * 
     * @return this
     * 
     * @see Vector3f
     */
	public Quaternion getBack(Vector3f dest)
	{
		dest.set(-2.0f * (x * z - w * y), -2.0f * (y * z + w * x), -(1.0f - 2.0f * (x * x + y * y)));
		return this;
	}

	/**
     * Get back vector
     * 
     * @return back
     * 
     * @see Vector3f
     */
	public Vector3f getBack()
	{
		return new Vector3f(-2.0f * (x * z - w * y), -2.0f * (y * z + w * x), -(1.0f - 2.0f * (x * x + y * y)));
	}
	
	/**
     * Get up vector
     * 
     * @param dest - result vector
     * 
     * @return this
     * 
     * @see Vector3f
     */
	public Quaternion getUp(Vector3f dest)
	{
		dest.set(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		return this;
	}

	/**
     * Get up vector
     * 
     * @return up
     * 
     * @see Vector3f
     */
	public Vector3f getUp()
	{
		return new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
	}

	/**
     * Get down vector
     * 
     * @param dest - result vector
     * 
     * @return this
     * 
     * @see Vector3f
     */
	public Quaternion getDown(Vector3f dest)
	{
		dest.set(-2.0f * (x * y + w * z), -(1.0f - 2.0f * (x * x + z * z)), -2.0f * (y * z - w * x));
		return this;
	}
	
	/**
     * Get down vector
     * 
     * @return down
     * 
     * @see Vector3f
     */
	public Vector3f getDown()
	{
		return new Vector3f(-2.0f * (x * y + w * z), -(1.0f - 2.0f * (x * x + z * z)), -2.0f * (y * z - w * x));
	}

	/**
     * Get right vector
     * 
     * @param dest - result vector
     * 
     * @return this
     * 
     * @see Vector3f
     */
	public Quaternion getRight(Vector3f dest)
	{
		dest.set(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));
		return this;
	}
	
	/**
     * Get right vector
     * 
     * @return right
     * 
     * @see Vector3f
     */
	public Vector3f getRight()
	{
		return new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));
	}
	
	/**
     * Get left vector
     * 
     * @param dest - result vector
     * 
     * @return this
     * 
     * @see Vector3f
     */
	public Quaternion getLeft(Vector3f dest)
	{
		dest.set(-(1.0f - 2.0f * (y * y + z * z)), -2.0f * (x * y - w * z), -2.0f * (x * z + w * y));
		return this;
	}

	/**
     * Get left vector
     * 
     * @return left
     * 
     * @see Vector3f
     */
	public Vector3f getLeft()
	{
		return new Vector3f(-(1.0f - 2.0f * (y * y + z * z)), -2.0f * (x * y - w * z), -2.0f * (x * z + w * y));
	}
	
	/**
	 * The squared magnitude
	 * 
	 * @return magnitude
	 */
	public float magnitudeSquared()
	{
		return x * x + y * y + z * z + w * w;
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
	public Quaternion normalize(Quaternion dest)
	{
		float inv = 1.0f / magnitude();
		dest.x = x * inv;
		dest.y = y * inv;
		dest.z = z * inv;
		dest.w = w * inv;
		
		return this;
	}
	
	/**
	 * Normalize components
	 * 
	 * @return this
	 */
	public Quaternion normalize()
	{
		return normalize(this);
	}
	
	/**
	 * Return quaternion as a Vector4f
	 * 
	 * @return quaternion vector
	 */
	public Vector4f toVector4f()
	{
		return new Vector4f(x, y, z, w);
	}

	/**
	 * Compute rotation matrix
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Quaternion toMatrix(Matrix4f dest)
	{
		dest.elements[0 + 0 * 4] = 1.0f - 2.0f * (y * y + z * z);
		dest.elements[0 + 1 * 4] = 2.0f * (x * y - w * z);
		dest.elements[0 + 2 * 4] = 2.0f * (x * z + w * y);
		dest.elements[0 + 3 * 4] = 0.0f;

		dest.elements[1 + 0 * 4] = 2.0f * (x * y + w * z);
		dest.elements[1 + 1 * 4] = 1.0f - 2.0f * (x * x + z * z);
		dest.elements[1 + 2 * 4] = 2.0f * (y * z - w * x);
		dest.elements[1 + 3 * 4] = 0.0f;
		
		dest.elements[2 + 0 * 4] = 2.0f * (x * z - w * y);
		dest.elements[2 + 1 * 4] = 2.0f * (y * z + w * x);
		dest.elements[2 + 2 * 4] = 1.0f - 2.0f * (x * x + y * y);
		dest.elements[2 + 3 * 4] = 0.0f;
		
		dest.elements[3 + 0 * 4] = 0.0f;
		dest.elements[3 + 1 * 4] = 0.0f;
		dest.elements[3 + 2 * 4] = 0.0f;
		dest.elements[3 + 3 * 4] = 1.0f;
		
		return this;
	}
	
	/**
	 * Computer and return rotation matrix
	 * 
	 * @return this
	 */
	public Matrix4f toMatrix()
	{
		Matrix4f result = new Matrix4f();
		toMatrix(result);
		return result;
	}
	
	/**
	 * The size in bytes
	 * 
	 * @return size
	 */
	public int sizeInBytes()
	{
		return Float.SIZE * 4;
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
	public Quaternion putFloatBuffer(FloatBuffer buffer, boolean flip)
	{
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		buffer.put(w);
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
	public Quaternion putFloatBuffer(FloatBuffer buffer)
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
		buffer.put(w);
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
		return "[" + x + ", " + y + ", " + z + ", " + w + "]";
	}
	
}
