/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jnull.Nullable;

/**
 * A four-dimensional mutable quaternion type with double precision elements.
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 */

public final class QuaternionM4D
  implements QuaternionReadable4DType, QuaternionWritable4DType
{
  private double w;
  private double x;
  private double y;
  private double z;

  /**
   * Default constructor, initializing the quaternion with values {@code [0.0,
   * 0.0, 0.0, 1.0]}
   */

  public QuaternionM4D()
  {
    this(0.0, 0.0, 0.0, 1.0);
  }

  /**
   * Construct a quaternion initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   * @param in_w The {@code w} value
   */

  public QuaternionM4D(
    final double in_x,
    final double in_y,
    final double in_z,
    final double in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  /**
   * Construct a quaternion initialized with the values given in {@code q}.
   *
   * @param q The source quaternion
   */

  public QuaternionM4D(
    final QuaternionReadable4DType q)
  {
    this(q.getXD(), q.getYD(), q.getZD(), q.getWD());
  }

  /**
   * Calculate the element-wise sum of the quaternions {@code q0} and {@code
   * q1}, saving the result to {@code qr}.
   *
   * @param q0  The left input quaternion
   * @param q1  The right input quaternion
   * @param out The output quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return {@code (q0.x + q1.x, q0.y + q1.y, q0.z + q1.z, q0.w + q1.w)}
   */

  public static <Q extends QuaternionWritable4DType> Q add(
    final QuaternionReadable4DType q0,
    final QuaternionReadable4DType q1,
    final Q out)
  {
    final double x = q0.getXD() + q1.getXD();
    final double y = q0.getYD() + q1.getYD();
    final double z = q0.getZD() + q1.getZD();
    final double w = q0.getWD() + q1.getWD();
    out.set4D(x, y, z, w);
    return out;
  }

  /**
   * Calculate the element-wise sum of the quaternions {@code q0} and {@code
   * q1}, saving the result to {@code q0}.
   *
   * @param q0  The left input quaternion
   * @param q1  The right input quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return {@code (q0.x + q1.x, q0.y + q1.y, q0.z + q1.z, q0.w + q1.w)}
   */

  public static <Q extends QuaternionWritable4DType &
    QuaternionReadable4DType> Q addInPlace(
    final Q q0,
    final QuaternionReadable4DType q1)
  {
    return QuaternionM4D.add(q0, q1, q0);
  }

  /**
   * Determine whether or not the quaternions {@code qa} and {@code qb} are
   * equal to within the degree of error given in {@code context}.
   *
   * @param context The equality context
   * @param qa      The left input quaternion
   * @param qb      The right input quaternion
   *
   * @return {@code true} if the quaternions are almost equal
   *
   * @see AlmostEqualDouble#almostEqual(ContextRelative, double, double)
   * @since 5.0.0
   */

  public static boolean almostEqual(
    final ContextRelative context,
    final QuaternionReadable4DType qa,
    final QuaternionReadable4DType qb)
  {
    final boolean xs =
      AlmostEqualDouble.almostEqual(context, qa.getXD(), qb.getXD());
    final boolean ys =
      AlmostEqualDouble.almostEqual(context, qa.getYD(), qb.getYD());
    final boolean zs =
      AlmostEqualDouble.almostEqual(context, qa.getZD(), qb.getZD());
    final boolean ws =
      AlmostEqualDouble.almostEqual(context, qa.getWD(), qb.getWD());
    return xs && ys && zs && ws;
  }

  /**
   * Calculate the conjugate of the input quaternion {@code q}, saving the
   * result to {@code out}.
   *
   * @param q   The input quaternion
   * @param out The output quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return {@code out}
   */

  public static <Q extends QuaternionWritable4DType> Q conjugate(
    final QuaternionReadable4DType q,
    final Q out)
  {
    out.set4D(-q.getXD(), -q.getYD(), -q.getZD(), q.getWD());
    return out;
  }

  /**
   * Calculate the conjugate of the input quaternion {@code q}, modifying {@code
   * q} in place.
   *
   * @param q   The input quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return {@code q}
   */

  public static <Q extends QuaternionWritable4DType &
    QuaternionReadable4DType> Q conjugateInPlace(
    final Q q)
  {
    return QuaternionM4D.conjugate(q, q);
  }

  /**
   * Copy the contents of the quaternion {@code q} to {@code out}.
   *
   * @param q   The input quaternion
   * @param out The output quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return {@code out}
   */

  public static <Q extends QuaternionWritable4DType> Q copy(
    final QuaternionReadable4DType q,
    final Q out)
  {
    out.set4D(q.getXD(), q.getYD(), q.getZD(), q.getWD());
    return out;
  }

  /**
   * Calculate the scalar product of the quaternions {@code q0} and {@code q1}.
   *
   * @param q0 The left input quaternion
   * @param q1 The right input quaternion
   *
   * @return The scalar product of the two quaternions
   */

  public static double dotProduct(
    final QuaternionReadable4DType q0,
    final QuaternionReadable4DType q1)
  {
    final double x = q0.getXD() * q1.getXD();
    final double y = q0.getYD() * q1.getYD();
    final double z = q0.getZD() * q1.getZD();
    final double w = q0.getWD() * q1.getWD();
    return x + y + z + w;
  }

  /**
   * Linearly interpolate between {@code q0} and {@code q1} by the amount {@code
   * alpha}, such that:
   *
   * <ul> <li>{@code interpolateLinear(q0, q1, 0.0, r) -> r = q0}</li>
   * <li>{@code interpolateLinear(q0, q1, 1.0, r) -> r = q1}</li> </ul>
   *
   * @param c     Preallocated storage
   * @param q0    The left input quaternion
   * @param q1    The right input quaternion
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}
   * @param r     The output quaternion
   * @param <Q>   The precise type of quaternion
   *
   * @return {@code (1 - alpha) * q0 + alpha * q1}
   */

  public static <Q extends QuaternionWritable4DType> Q interpolateLinear(
    final ContextQM4D c,
    final QuaternionReadable4DType q0,
    final QuaternionReadable4DType q1,
    final double alpha,
    final Q r)
  {
    QuaternionM4D.scale(q0, 1.0 - alpha, c.qa);
    QuaternionM4D.scale(q1, alpha, c.qb);
    return QuaternionM4D.add(c.qa, c.qb, r);
  }

  /**
   * Return {@code true} iff {@code qa} is the negation of {@code qb}.
   *
   * <p> Each element is compared with {@link AlmostEqualDouble#almostEqual
   * (ContextRelative, double, double)}. </p>
   *
   * @param context The equality context
   * @param qa      The left quaternion
   * @param qb      The right quaternion
   *
   * @return {@code true} iff {@code qa} is the negation of {@code qb}
   *
   * @since 5.0.0
   */

  public static boolean isNegationOf(
    final AlmostEqualDouble.ContextRelative context,
    final QuaternionReadable4DType qa,
    final QuaternionReadable4DType qb)
  {
    final double xa = qa.getXD();
    final double ya = qa.getYD();
    final double za = qa.getZD();
    final double wa = qa.getWD();

    final double xb = -qb.getXD();
    final double yb = -qb.getYD();
    final double zb = -qb.getZD();
    final double wb = -qb.getWD();

    final boolean xs = AlmostEqualDouble.almostEqual(context, xa, xb);
    final boolean ys = AlmostEqualDouble.almostEqual(context, ya, yb);
    final boolean zs = AlmostEqualDouble.almostEqual(context, za, zb);
    final boolean ws = AlmostEqualDouble.almostEqual(context, wa, wb);

    return xs && ys && zs && ws;
  }

  /**
   * Produce a quaternion that represents a rotation that "looks at" the point
   * at {@code target} assuming the viewer is at {@code origin}, using {@code
   * up} as the "up" vector, saving the result to {@code q}.
   *
   * <p> The function uses storage preallocated in {@code context} to avoid any
   * new allocations. </p>
   *
   * @param context Preallocated storage
   * @param q       The output quaternion
   * @param origin  The origin point
   * @param target  The target point
   * @param up      The up vector
   * @param <Q>     The precise type of quaternion
   *
   * @return {@code q}
   *
   * @since 5.0.0
   */

  public static <Q extends QuaternionWritable4DType> Q lookAtWithContext(
    final ContextQM4D context,
    final VectorReadable3DType origin,
    final VectorReadable3DType target,
    final VectorReadable3DType up,
    final Q q)
  {
    final MatrixM3x3D m = context.getM3A();
    final VectorM3D t = context.getV3A();
    final MatrixM3x3D.ContextM3D mc = context.getContext();

    MatrixM3x3D.lookAtWithContext(mc, origin, target, up, m, t);
    QuaternionM4D.makeFromRotationMatrix3x3(m, q);
    return q;
  }

  /**
   * Calculate the magnitude of the quaternion {@code q}.
   *
   * Correspondingly, {@code magnitude(normalize(q)) == 1.0}.
   *
   * @param q The input quaternion
   *
   * @return The magnitude of the input quaternion
   */

  public static double magnitude(
    final QuaternionReadable4DType q)
  {
    return Math.sqrt(QuaternionM4D.magnitudeSquared(q));
  }

  /**
   * Calculate the squared magnitude of the quaternion {@code q}.
   *
   * @param q The input quaternion
   *
   * @return The squared magnitude of the input quaternion
   */

  public static double magnitudeSquared(
    final QuaternionReadable4DType q)
  {
    return QuaternionM4D.dotProduct(q, q);
  }

  /**
   * Produce a quaternion that represents a rotation of {@code angle} degrees
   * around the axis specified by {@code axis}, saving the result to {@code
   * out}. {@code axis} is assumed to be of unit length.
   *
   * @param c     Preallocated storage
   * @param axis  The normalized quaternion representing the axis
   * @param angle The angle to rotate, in radians
   * @param out   The output quaternion
   * @param <Q>   The precise type of quaternion
   *
   * @return A quaternion representing the rotation
   *
   * @see VectorI3D#normalize(VectorReadable3DType)
   * @see VectorI4D#normalize(VectorReadable4DType)
   * @see VectorM3D#normalize(VectorReadable3DType, VectorWritable3DType)
   * @see VectorM4D#normalize(VectorReadable4DType, VectorWritable4DType)
   * @since 7.0.0
   */

  public static <Q extends QuaternionWritable4DType> Q makeFromAxisAngle(
    final ContextQM4D c,
    final VectorReadable3DType axis,
    final double angle,
    final Q out)
  {
    final double angle_r = angle * 0.5;
    final double sa = Math.sin(angle_r);

    final double x = axis.getXD() * sa;
    final double y = axis.getYD() * sa;
    final double z = axis.getZD() * sa;
    final double w = Math.cos(angle_r);

    c.qa.set4D(x, y, z, w);
    QuaternionM4D.normalize(c.qa, out);
    return out;
  }

  /**
   * Produce a quaternion equivalent to the rotation matrix {@code m}, writing
   * the result to {@code out}.
   *
   * @param m   The rotation matrix
   * @param out The output quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return {@code out}
   *
   * @since 5.0.0
   */

  public static <Q extends QuaternionWritable4DType> Q
  makeFromRotationMatrix3x3(
    final MatrixReadable3x3DType m,
    final Q out)
  {
    final double m00 = m.getRowColumnD(0, 0);
    final double m01 = m.getRowColumnD(0, 1);
    final double m02 = m.getRowColumnD(0, 2);
    final double m10 = m.getRowColumnD(1, 0);
    final double m11 = m.getRowColumnD(1, 1);
    final double m12 = m.getRowColumnD(1, 2);
    final double m20 = m.getRowColumnD(2, 0);
    final double m21 = m.getRowColumnD(2, 1);
    final double m22 = m.getRowColumnD(2, 2);

    final double trace = MatrixM3x3D.trace(m);

    final double x;
    final double y;
    final double z;
    final double w;
    if (trace > 0.0) {
      // S = 4 * qw
      final double s = Math.sqrt(trace + 1.0) * 2.0;
      w = 0.25 * s;
      x = (m21 - m12) / s;
      y = (m02 - m20) / s;
      z = (m10 - m01) / s;
    } else if ((m00 > m11) && (m00 > m22)) {
      // S = 4 * qx
      final double s = Math.sqrt((1.0 + m00) - m11 - m22) * 2.0;
      w = (m21 - m12) / s;
      x = 0.25 * s;
      y = (m01 + m10) / s;
      z = (m02 + m20) / s;
    } else if (m11 > m22) {
      // S = 4 * qy
      final double s = Math.sqrt((1.0 + m11) - m00 - m22) * 2.0;
      w = (m02 - m20) / s;
      x = (m01 + m10) / s;
      y = 0.25 * s;
      z = (m12 + m21) / s;
    } else {
      // S = 4 * qz
      final double s = Math.sqrt((1.0 + m22) - m00 - m11) * 2.0;
      w = (m10 - m01) / s;
      x = (m02 + m20) / s;
      y = (m12 + m21) / s;
      z = 0.25 * s;
    }

    out.set4D(x, y, z, w);
    return out;
  }

  /**
   * Produce a quaternion equivalent to the rotation matrix {@code m}, writing
   * the result to {@code out}.
   *
   * @param m   The rotation matrix
   * @param out The output quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return {@code out}
   *
   * @since 5.0.0
   */

  public static <Q extends QuaternionWritable4DType> Q
  makeFromRotationMatrix4x4(
    final MatrixReadable4x4DType m,
    final Q out)
  {
    final double m00 = m.getRowColumnD(0, 0);
    final double m01 = m.getRowColumnD(0, 1);
    final double m02 = m.getRowColumnD(0, 2);
    final double m10 = m.getRowColumnD(1, 0);
    final double m11 = m.getRowColumnD(1, 1);
    final double m12 = m.getRowColumnD(1, 2);
    final double m20 = m.getRowColumnD(2, 0);
    final double m21 = m.getRowColumnD(2, 1);
    final double m22 = m.getRowColumnD(2, 2);

    /**
     * Explicitly ignore the bottom right element of the matrix, as this
     * affects the magnitude of the created quaternion.
     */

    final double trace = m00 + m11 + m22;

    final double x;
    final double y;
    final double z;
    final double w;

    if (trace > 0.0) {
      // S = 4 * qw
      final double s = Math.sqrt(trace + 1.0) * 2.0;
      w = 0.25 * s;
      x = (m21 - m12) / s;
      y = (m02 - m20) / s;
      z = (m10 - m01) / s;
    } else if ((m00 > m11) && (m00 > m22)) {
      // S = 4 * qx
      final double s = Math.sqrt((1.0 + m00) - m11 - m22) * 2.0;
      w = (m21 - m12) / s;
      x = 0.25 * s;
      y = (m01 + m10) / s;
      z = (m02 + m20) / s;
    } else if (m11 > m22) {
      // S = 4 * qy
      final double s = Math.sqrt((1.0 + m11) - m00 - m22) * 2.0;
      w = (m02 - m20) / s;
      x = (m01 + m10) / s;
      y = 0.25 * s;
      z = (m12 + m21) / s;
    } else {
      // S = 4 * qz
      final double s = Math.sqrt((1.0 + m22) - m00 - m11) * 2.0;
      w = (m10 - m01) / s;
      x = (m02 + m20) / s;
      y = (m12 + m21) / s;
      z = 0.25 * s;
    }

    out.set4D(x, y, z, w);
    return out;
  }

  /**
   * Produce a rotation matrix from the quaternion {@code q}, saving the result
   * to {@code m}.
   *
   * @param q   The input quaternion
   * @param m   The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   *
   * @since 5.0.0
   */

  public static <M extends MatrixWritable3x3DType> M makeRotationMatrix3x3(
    final QuaternionReadable4DType q,
    final M m)
  {
    final double xx = q.getXD() * q.getXD();
    final double xy = q.getXD() * q.getYD();
    final double xz = q.getXD() * q.getZD();
    final double yy = q.getYD() * q.getYD();
    final double yz = q.getYD() * q.getZD();
    final double zz = q.getZD() * q.getZD();
    final double wx = q.getWD() * q.getXD();
    final double wy = q.getWD() * q.getYD();
    final double wz = q.getWD() * q.getZD();

    final double r0c0 = 1.0 - (2.0 * yy) - (2.0 * zz);
    final double r0c1 = (2.0 * xy) - (2.0 * wz);
    final double r0c2 = (2.0 * xz) + (2.0 * wy);

    final double r1c0 = (2.0 * xy) + (2.0 * wz);
    final double r1c1 = 1.0 - (2.0 * xx) - (2.0 * zz);
    final double r1c2 = (2.0 * yz) - (2.0 * wx);

    final double r2c0 = (2.0 * xz) - (2.0 * wy);
    final double r2c1 = (2.0 * yz) + (2.0 * wx);
    final double r2c2 = 1.0 - (2.0 * xx) - (2.0 * yy);

    m.setRowColumnD(0, 0, r0c0);
    m.setRowColumnD(0, 1, r0c1);
    m.setRowColumnD(0, 2, r0c2);

    m.setRowColumnD(1, 0, r1c0);
    m.setRowColumnD(1, 1, r1c1);
    m.setRowColumnD(1, 2, r1c2);

    m.setRowColumnD(2, 0, r2c0);
    m.setRowColumnD(2, 1, r2c1);
    m.setRowColumnD(2, 2, r2c2);

    return m;
  }

  /**
   * Produce a rotation matrix from the quaternion {@code q}, saving the result
   * to {@code m}.
   *
   * @param q The input quaternion
   * @param m The output matrix
   *          @param <M> The precise type of matrix
   *
   * @return {@code m}
   *
   * @since 5.0.0
   */

  public static <M extends MatrixWritable4x4DType> M makeRotationMatrix4x4(
    final QuaternionReadable4DType q,
    final M m)
  {
    final double xx = q.getXD() * q.getXD();
    final double xy = q.getXD() * q.getYD();
    final double xz = q.getXD() * q.getZD();
    final double yy = q.getYD() * q.getYD();
    final double yz = q.getYD() * q.getZD();
    final double zz = q.getZD() * q.getZD();
    final double wx = q.getWD() * q.getXD();
    final double wy = q.getWD() * q.getYD();
    final double wz = q.getWD() * q.getZD();

    final double r0c0 = 1.0 - (2.0 * yy) - (2.0 * zz);
    final double r0c1 = (2.0 * xy) - (2.0 * wz);
    final double r0c2 = (2.0 * xz) + (2.0 * wy);
    final double r0c3 = 0.0;

    final double r1c0 = (2.0 * xy) + (2.0 * wz);
    final double r1c1 = 1.0 - (2.0 * xx) - (2.0 * zz);
    final double r1c2 = (2.0 * yz) - (2.0 * wx);
    final double r1c3 = 0.0;

    final double r2c0 = (2.0 * xz) - (2.0 * wy);
    final double r2c1 = (2.0 * yz) + (2.0 * wx);
    final double r2c2 = 1.0 - (2.0 * xx) - (2.0 * yy);
    final double r2c3 = 0.0;

    final double r3c0 = 0.0;
    final double r3c1 = 0.0;
    final double r3c2 = 0.0;
    final double r3c3 = 1.0;

    m.setRowColumnD(0, 0, r0c0);
    m.setRowColumnD(0, 1, r0c1);
    m.setRowColumnD(0, 2, r0c2);
    m.setRowColumnD(0, 3, r0c3);

    m.setRowColumnD(1, 0, r1c0);
    m.setRowColumnD(1, 1, r1c1);
    m.setRowColumnD(1, 2, r1c2);
    m.setRowColumnD(1, 3, r1c3);

    m.setRowColumnD(2, 0, r2c0);
    m.setRowColumnD(2, 1, r2c1);
    m.setRowColumnD(2, 2, r2c2);
    m.setRowColumnD(2, 3, r2c3);

    m.setRowColumnD(3, 0, r3c0);
    m.setRowColumnD(3, 1, r3c1);
    m.setRowColumnD(3, 2, r3c2);
    m.setRowColumnD(3, 3, r3c3);

    return m;
  }

  /**
   * Multiply the quaternion {@code q0} by the quaternion {@code q1} , saving
   * the result to {@code qr}.
   *
   * <p> Note that this operation is not commutative. </p>
   *
   * <p> The function is most often used to concatenate quaternions to combine
   * rotations. As an example, assuming that: </p>
   *
   * <ul> <li>{@code qx} represents some rotation around the X axis</li>
   * <li>{@code qy} represents some rotation around the Y axis</li> <li>{@code
   * qz} represents some rotation around the Z axis</li> </ul>
   *
   * <p> The following code produces a quaternion {@code qr} that represents a
   * rotation around the X axis, followed by a rotation around the Y axis,
   * followed by a rotation around the Z axis: </p>
   *
   * <pre>
   * QuaternionM4D qr = new QuaternionM4D();
   * QuaternionM4D.multiply(qy, qx, qr);
   * QuaternionM4D.multiply(qz, qr, qr);
   * </pre>
   *
   * @param q0  The left input quaternion
   * @param q1  The right input quaternion
   * @param qr  The output quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return {@code qr}
   */

  public static <Q extends QuaternionWritable4DType> Q multiply(
    final QuaternionReadable4DType q0,
    final QuaternionReadable4DType q1,
    final Q qr)
  {
    final double rx = ((q0.getWD() * q1.getXD()) + (q0.getXD() * q1.getWD()) + (
      q0.getYD()
      * q1.getZD())) - (q0.getZD() * q1.getYD());
    final double ry =
      ((q0.getWD() * q1.getYD()) - (q0.getXD() * q1.getZD())) + (q0.getYD() * q1
        .getWD()) + (q0.getZD() * q1.getXD());
    final double rz =
      (((q0.getWD() * q1.getZD()) + (q0.getXD() * q1.getYD())) - (q0.getYD()
                                                                  * q1.getXD()))
      + (q0.getZD() * q1.getWD());
    final double rw = (q0.getWD() * q1.getWD()) - (q0.getXD() * q1.getXD()) - (
      q0.getYD()
      * q1.getYD()) - (q0.getZD() * q1.getZD());

    qr.set4D(rx, ry, rz, rw);
    return qr;
  }

  /**
   * Multiply the quaternion {@code q0} by the quaternion {@code q1} , saving
   * the result to {@code q0}.
   *
   * @param q0  The left quaternion
   * @param q1  The right quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return q0
   *
   * @see QuaternionM4D#multiply(QuaternionReadable4DType,
   * QuaternionReadable4DType, QuaternionWritable4DType)
   */

  public static <Q extends QuaternionWritable4DType &
    QuaternionReadable4DType> Q multiplyInPlace(
    final Q q0,
    final QuaternionReadable4DType q1)
  {
    return QuaternionM4D.multiply(q0, q1, q0);
  }

  /**
   * Negate the elements of {@code qa}, writing the resulting quaternion to
   * {@code out}.
   *
   * @param qa  The input quaternion
   * @param out The output quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return out
   *
   * @since 5.0.0
   */

  public static <Q extends QuaternionWritable4DType> Q negate(
    final QuaternionReadable4DType qa,
    final Q out)
  {
    final double x = -qa.getXD();
    final double y = -qa.getYD();
    final double z = -qa.getZD();
    final double w = -qa.getWD();
    out.set4D(x, y, z, w);
    return out;
  }

  /**
   * Negate the elements of {@code q}, modifying the quaternion in-place.
   *
   * @param q   The input quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return q
   *
   * @since 5.0.0
   */

  public static <Q extends QuaternionWritable4DType &
    QuaternionReadable4DType> Q negateInPlace(
    final Q q)
  {
    return QuaternionM4D.negate(q, q);
  }

  /**
   * Returns a quaternion with the same orientation as {@code q} but with
   * magnitude equal to {@code 1.0} in {@code out}. The function returns the
   * zero quaternion iff the input is the zero quaternion.
   *
   * @param out The output quaternion
   * @param q   The input quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return out
   */

  public static <Q extends QuaternionWritable4DType> Q normalize(
    final QuaternionReadable4DType q,
    final Q out)
  {
    final double m = QuaternionM4D.magnitudeSquared(q);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return QuaternionM4D.scale(q, reciprocal, out);
    }
    out.set4D(q.getXD(), q.getYD(), q.getZD(), q.getWD());
    return out;
  }

  /**
   * Returns a quaternion with the same orientation as {@code q} but with
   * magnitude equal to {@code 1.0} in {@code q}. The function returns the zero
   * quaternion iff the input is the zero quaternion.
   *
   * @param q   The input quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return q
   */

  public static <Q extends QuaternionWritable4DType &
    QuaternionReadable4DType> Q normalizeInPlace(
    final Q q)
  {
    return QuaternionM4D.normalize(q, q);
  }

  /**
   * Scale the quaternion {@code q} by the scalar {@code r}, saving the result
   * to {@code out}.
   *
   * @param q   The input quaternion
   * @param r   The scaling value
   * @param out The output quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return {@code (q.x * r, q.y * r, q.z * r, q.w * r)}
   */

  public static <Q extends QuaternionWritable4DType> Q scale(
    final QuaternionReadable4DType q,
    final double r,
    final Q out)
  {
    final double x = q.getXD() * r;
    final double y = q.getYD() * r;
    final double z = q.getZD() * r;
    final double w = q.getWD() * r;
    out.set4D(x, y, z, w);
    return out;
  }

  /**
   * Scale the quaternion {@code q} by the scalar {@code r}, saving the result
   * to {@code q}.
   *
   * @param q   The input quaternion
   * @param r   The scaling value
   * @param <Q> The precise type of quaternion
   *
   * @return {@code (q.x * r, q.y * r, q.z * r, q.w * r)}
   */

  public static <Q extends QuaternionWritable4DType &
    QuaternionReadable4DType> Q scaleInPlace(
    final Q q,
    final double r)
  {
    return QuaternionM4D.scale(q, r, q);
  }

  /**
   * Subtract the quaternion {@code q0} from the quaternion {@code q1}, saving
   * the result to {@code out}.
   *
   * @param q0  The left input quaternion
   * @param q1  The right input quaternion
   * @param out The output quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return {@code (q0.x - q1.x, q0.y - q1.y, q0.z - q1.z, q0.w - q1.w)}
   */

  public static <Q extends QuaternionWritable4DType> Q subtract(
    final QuaternionReadable4DType q0,
    final QuaternionReadable4DType q1,
    final Q out)
  {
    final double x = q0.getXD() - q1.getXD();
    final double y = q0.getYD() - q1.getYD();
    final double z = q0.getZD() - q1.getZD();
    final double w = q0.getWD() - q1.getWD();
    out.set4D(x, y, z, w);
    return out;
  }

  /**
   * Subtract the quaternion {@code q0} from the quaternion {@code q1}, saving
   * the result to {@code q0}.
   *
   * @param q0  The left input quaternion
   * @param q1  The right input quaternion
   * @param <Q> The precise type of quaternion
   *
   * @return {@code (q0.x - q1.x, q0.y - q1.y, q0.z - q1.z, q0.w - q1.w)}
   */

  public static <Q extends QuaternionWritable4DType &
    QuaternionReadable4DType> Q subtractInPlace(
    final Q q0,
    final QuaternionReadable4DType q1)
  {
    return QuaternionM4D.subtract(q0, q1, q0);
  }

  @Override public void copyFrom2D(
    final VectorReadable2DType in_v)
  {
    VectorM2D.copy(in_v, this);
  }

  @Override public void copyFrom3D(
    final VectorReadable3DType in_v)
  {
    VectorM3D.copy(in_v, this);
  }

  @Override public void copyFrom4D(
    final VectorReadable4DType in_v)
  {
    VectorM4D.copy(in_v, this);
  }

  @Override public boolean equals(
    final @Nullable Object obj)
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
    final QuaternionM4D other = (QuaternionM4D) obj;
    if (Double.doubleToLongBits(this.w) != Double.doubleToLongBits(other.w)) {
      return false;
    }
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
      return false;
    }
    return Double.doubleToLongBits(this.z) == Double.doubleToLongBits(other.z);
  }

  @Override public double getWD()
  {
    return this.w;
  }

  @Override public void setWD(
    final double in_w)
  {
    this.w = in_w;
  }

  @Override public double getXD()
  {
    return this.x;
  }

  @Override public void setXD(
    final double in_x)
  {
    this.x = in_x;
  }

  @Override public double getYD()
  {
    return this.y;
  }

  @Override public void setYD(
    final double in_y)
  {
    this.y = in_y;
  }

  @Override public double getZD()
  {
    return this.z;
  }

  @Override public void setZD(
    final double in_z)
  {
    this.z = in_z;
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

  @Override public void set2D(
    final double in_x,
    final double in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override public void set3D(
    final double in_x,
    final double in_y,
    final double in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  @Override public void set4D(
    final double in_x,
    final double in_y,
    final double in_z,
    final double in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
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
    final String r = builder.toString();
    assert r != null;
    return r;
  }

  /**
   * The {@code ContextQM4D} type contains the minimum storage required for all
   * of the functions of the {@code QuaternionM4D} class.
   *
   * <p> The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   *
   * <p> The user should allocate one {@code ContextQM4D} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextQM4D} value will not generate garbage. </p>
   *
   * @since 7.0.0
   */

  public static class ContextQM4D
  {
    private final MatrixM3x3D.ContextM3D m_context =
      new MatrixM3x3D.ContextM3D();
    private final MatrixM3x3D            m3a       = new MatrixM3x3D();
    private final VectorM3D              v3a       = new VectorM3D();
    private final QuaternionM4D          qa        = new QuaternionM4D();
    private final QuaternionM4D          qb        = new QuaternionM4D();

    /**
     * Construct a new context.
     */

    public ContextQM4D()
    {

    }

    final MatrixM3x3D.ContextM3D getContext()
    {
      return this.m_context;
    }

    final MatrixM3x3D getM3A()
    {
      return this.m3a;
    }

    final VectorM3D getV3A()
    {
      return this.v3a;
    }
  }
}
