/*
 * Copyright © 2012 http://io7m.com
 * 
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jtensors;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.io7m.jaux.ApproximatelyEqualDouble;

/**
 * A four-dimensional mutable quaternion type with double precision elements.
 * 
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 */

@NotThreadSafe public final class QuaternionM4D implements
  QuaternionReadable4D
{
  /**
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * @param out
   *          The output quaternion
   * 
   * @return <code>(q0.x + q1.x, q0.y + q1.y, q0.z + q1.z, q0.w + q1.w)</code>
   */

  public static @Nonnull QuaternionM4D add(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1,
    final @Nonnull QuaternionM4D out)
  {
    final double x = q0.x + q1.x;
    final double y = q0.y + q1.y;
    final double z = q0.z + q1.z;
    final double w = q0.w + q1.w;
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * 
   * @return <code>(q0.x + q1.x, q0.y + q1.y, q0.z + q1.z, q0.w + q1.w)</code>
   *         , saving the result in <code>q0</code>
   */

  public static @Nonnull QuaternionM4D addInPlace(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1)
  {
    return QuaternionM4D.add(q0, q1, q0);
  }

  /**
   * @see ApproximatelyEqualDouble
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * 
   * @return true, iff <code>q0</code> is approximately equal to
   *         <code>q1</code> , within an appropriate degree of error for
   *         double precision floating point values.
   */

  public static boolean approximatelyEqual(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1)
  {
    final boolean ex =
      ApproximatelyEqualDouble.approximatelyEqual(q0.x, q1.x);
    final boolean ey =
      ApproximatelyEqualDouble.approximatelyEqual(q0.y, q1.y);
    final boolean ez =
      ApproximatelyEqualDouble.approximatelyEqual(q0.z, q1.z);
    final boolean ew =
      ApproximatelyEqualDouble.approximatelyEqual(q0.w, q1.w);
    return ex && ey && ez && ew;
  }

  /**
   * Calculate the conjugate of the input quaternion <code>q</code>, saving
   * the result to <code>out</code>.
   * 
   * @param q
   *          The input quaternion
   * @param out
   *          The output quaternion
   * 
   * @return <code>out</code>
   */

  public static @Nonnull QuaternionM4D conjugate(
    final @Nonnull QuaternionM4D q,
    final @Nonnull QuaternionM4D out)
  {
    out.x = -q.x;
    out.y = -q.y;
    out.z = -q.z;
    out.w = q.w;
    return out;
  }

  /**
   * Calculate the conjugate of the input quaternion <code>q</code>, modifying
   * <code>q</code> in place.
   * 
   * @param q
   *          The input quaternion
   * 
   * @return <code>q</code>
   */

  public static @Nonnull QuaternionM4D conjugateInPlace(
    final @Nonnull QuaternionM4D q)
  {
    return QuaternionM4D.conjugate(q, q);
  }

  /**
   * Copy the contents of the quaternion <code>q</code> to <code>out</code>.
   * 
   * @param q
   *          The input quaternion
   * @param out
   *          The output quaternion
   * @return <code>out</code>
   */

  public static @Nonnull QuaternionM4D copy(
    final @Nonnull QuaternionM4D q,
    final @Nonnull QuaternionM4D out)
  {
    out.x = q.x;
    out.y = q.y;
    out.z = q.z;
    out.w = q.w;
    return out;
  }

  /**
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * 
   * @return The scalar product of the two quaternions
   */

  public static double dotProduct(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1)
  {
    final double x = q0.x * q1.x;
    final double y = q0.y * q1.y;
    final double z = q0.z * q1.z;
    final double w = q0.w * q1.w;
    return x + y + z + w;
  }

  /**
   * Linearly interpolate between <code>q0</code> and <code>q1</code> by the
   * amount <code>alpha</code>, such that:
   * 
   * <ul>
   * <li><code>interpolateLinear(q0, q1, 0.0, r) -> r = q0</code></li>
   * <li><code>interpolateLinear(q0, q1, 1.0, r) -> r = q1</code></li>
   * </ul>
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * @param alpha
   *          The interpolation value, between <code>0.0</code> and
   *          <code>1.0</code>
   * 
   * @return <code>(1 - alpha) * q0 + alpha * q1</code>
   */

  public static @Nonnull QuaternionM4D interpolateLinear(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1,
    final double alpha,
    final @Nonnull QuaternionM4D r)
  {
    final @Nonnull QuaternionM4D w0 = new QuaternionM4D();
    final @Nonnull QuaternionM4D w1 = new QuaternionM4D();

    QuaternionM4D.scale(q0, 1.0 - alpha, w0);
    QuaternionM4D.scale(q1, alpha, w1);

    return QuaternionM4D.add(w0, w1, r);
  }

  /**
   * @param q
   *          The input quaternion
   * 
   * @return The magnitude of the input quaternion.
   *         <code>magnitude(normalize(q)) == 1.0</code>
   */

  public static double magnitude(
    final @Nonnull QuaternionM4D q)
  {
    return Math.sqrt(QuaternionM4D.magnitudeSquared(q));
  }

  /**
   * @param q
   *          The input quaternion
   * 
   * @return The squared magnitude of the input quaternion
   */

  public static double magnitudeSquared(
    final @Nonnull QuaternionM4D q)
  {
    return QuaternionM4D.dotProduct(q, q);
  }

  /**
   * Produce a quaternion that represents a rotation of <code>angle</code>
   * degrees around the axis specified by <code>axis</code>, saving the result
   * to <code>out</code>. <code>axis</code> is assumed to be of unit length.
   * 
   * @see VectorI3D#normalize(VectorI3D)
   * @see VectorI4D#normalize(VectorI4D)
   * @see VectorM3D#normalize(VectorM3D, VectorM3D)
   * @see VectorM4D#normalize(VectorM4D, VectorM4D)
   * 
   * @param axis
   *          The normalized vector representing the axis
   * @param angle
   *          The angle to rotate, in radians
   * @param out
   *          The output quaternion
   * 
   * @return A quaternion representing the rotation
   */

  public static @Nonnull QuaternionM4D makeFromAxisAngle(
    final @Nonnull VectorReadable3D axis,
    final double angle,
    final @Nonnull QuaternionM4D out)
  {
    final double angle_r = angle * 0.5;
    final double sa = Math.sin(angle_r);

    out.x = axis.getXD() * sa;
    out.y = axis.getYD() * sa;
    out.z = axis.getZD() * sa;
    out.w = Math.cos(angle_r);
    return out;
  }

  /**
   * Multiply the quaternion <code>q0</code> by the quaternion <code>q1</code>
   * , saving the result to <code>qr</code>.
   * 
   * Note that this operation is not commutative.
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * @param qr
   *          The output quaternion
   * 
   * @return <code>qr</code>
   */

  public static @Nonnull QuaternionM4D multiply(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1,
    final @Nonnull QuaternionM4D qr)
  {
    qr.x = ((q0.w * q1.x) + (q0.x * q1.w) + (q0.y * q1.z)) - (q0.z * q1.y);
    qr.y = ((q0.w * q1.y) - (q0.x * q1.z)) + (q0.y * q1.w) + (q0.z * q1.x);
    qr.z = (((q0.w * q1.z) + (q0.x * q1.y)) - (q0.y * q1.x)) + (q0.z * q1.w);
    qr.w = (q0.w * q1.w) - (q0.x * q1.x) - (q0.y * q1.y) - (q0.z * q1.z);

    return qr;
  }

  /**
   * Returns a quaternion with the same orientation as <code>q</code> but with
   * magnitude equal to <code>1.0</code> in <code>out</code>. The function
   * returns the zero quaternion iff the input is the zero quaternion.
   * 
   * @param q
   *          The input quaternion
   * 
   * @return out
   */

  public static @Nonnull QuaternionM4D normalize(
    final @Nonnull QuaternionM4D q,
    final @Nonnull QuaternionM4D out)
  {
    final double m = QuaternionM4D.magnitudeSquared(q);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return QuaternionM4D.scale(q, reciprocal, out);
    }
    out.x = q.x;
    out.y = q.y;
    out.z = q.z;
    out.w = q.w;
    return out;
  }

  /**
   * Returns a quaternion with the same orientation as <code>q</code> but with
   * magnitude equal to <code>1.0</code> in <code>q</code>. The function
   * returns the zero quaternion iff the input is the zero quaternion.
   * 
   * @param q
   *          The input quaternion
   * 
   * @return q
   */

  public static @Nonnull QuaternionM4D normalizeInPlace(
    final @Nonnull QuaternionM4D q)
  {
    return QuaternionM4D.normalize(q, q);
  }

  /**
   * @param q
   *          The input quaternion
   * @param out
   *          The output quaternion
   * @param r
   *          The scaling value
   * 
   * @return <code>(q.x * r, q.y * r, q.z * r, q.w * r)</code>
   */

  public static @Nonnull QuaternionM4D scale(
    final @Nonnull QuaternionM4D q,
    final double r,
    final @Nonnull QuaternionM4D out)
  {
    final double x = q.x * r;
    final double y = q.y * r;
    final double z = q.z * r;
    final double w = q.w * r;
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * @param q
   *          The input quaternion
   * @param r
   *          The scaling value
   * 
   * @return <code>(q.x * r, q.y * r, q.z * r, q.w * r)</code>, saving the
   *         result into <code>q</code>
   */

  public static @Nonnull QuaternionM4D scaleInPlace(
    final @Nonnull QuaternionM4D q,
    final double r)
  {
    return QuaternionM4D.scale(q, r, q);
  }

  /**
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * @param out
   *          The output quaternion
   * 
   * @return <code>(q0.x - q1.x, q0.y - q1.y, q0.z - q1.z, q0.w - q1.w)</code>
   */

  public static @Nonnull QuaternionM4D subtract(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1,
    final @Nonnull QuaternionM4D out)
  {
    final double x = q0.x - q1.x;
    final double y = q0.y - q1.y;
    final double z = q0.z - q1.z;
    final double w = q0.w - q1.w;
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * 
   * @return <code>(q0.x - q1.x, q0.y - q1.y, q0.z - q1.z, q0.w - q1.w)</code>
   *         , saving the result into <code>out</code>
   */

  public static @Nonnull QuaternionM4D subtractInPlace(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1)
  {
    return QuaternionM4D.subtract(q0, q1, q0);
  }

  public double x;
  public double y;
  public double z;
  public double w;

  /**
   * Default constructor, initializing the quaternion with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>
   */

  public QuaternionM4D()
  {
    this(0.0, 0.0, 0.0, 1.0);
  }

  public QuaternionM4D(
    final double x,
    final double y,
    final double z,
    final double w)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public QuaternionM4D(
    final @Nonnull QuaternionReadable4D q)
  {
    this(q.getXD(), q.getYD(), q.getZD(), q.getWD());
  }

  @Override public boolean equals(
    final Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final @Nonnull QuaternionM4D other = (QuaternionM4D) obj;
    if (Double.doubleToLongBits(this.w) != Double.doubleToLongBits(other.w)) {
      return false;
    }
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
      return false;
    }
    if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
      return false;
    }
    return true;
  }

  @Override public double getWD()
  {
    return this.w;
  }

  @Override public double getXD()
  {
    return this.x;
  }

  @Override public double getYD()
  {
    return this.y;
  }

  @Override public double getZD()
  {
    return this.z;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(this.w);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.x);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.y);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.z);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[QuaternionM4D ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append(" ");
    builder.append(this.w);
    builder.append("]");
    return builder.toString();
  }
}
