/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.parameterized;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import net.jcip.annotations.Immutable;

/**
 * <p> A four-dimensional immutable vector type with integer elements. </p> <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads. </p>
 *
 * @param <T> A phantom type parameter.
 */

@Immutable public final class PVectorI4I<T> implements PVectorReadable4IType<T>
{
  /**
   * The zero vector.
   */

  private static final PVectorI4I<?> ZERO = new PVectorI4I<Integer>(
    0, 0, 0, 0);
  private final int w;
  private final int x;
  private final int y;
  private final int z;

  /**
   * Default constructor, initializing the vector with values {@code [0, 0, 0,
   * 1]}.
   */

  public PVectorI4I()
  {
    this.x = 0;
    this.y = 0;
    this.z = 0;
    this.w = 1;
  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   * @param in_w The {@code w} value
   */

  public PVectorI4I(
    final int in_x,
    final int in_y,
    final int in_z,
    final int in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code
   * in_v}.
   *
   * @param in_v The source vector
   */

  public PVectorI4I(
    final PVectorReadable4IType<T> in_v)
  {
    this.x = in_v.getXI();
    this.y = in_v.getYI();
    this.z = in_v.getZI();
    this.w = in_v.getWI();
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   *
   * @return {@code (abs v.x, abs v.y, abs v.z, abs.w)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static <T> PVectorI4I<T> absolute(
    final PVectorReadable4IType<T> v)
    throws ArithmeticException
  {
    final int x = CheckedMath.absolute(v.getXI());
    final int y = CheckedMath.absolute(v.getYI());
    final int z = CheckedMath.absolute(v.getZI());
    final int w = CheckedMath.absolute(v.getWI());
    return new PVectorI4I<T>(x, y, z, w);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <T> PVectorI4I<T> add(
    final PVectorReadable4IType<T> v0,
    final PVectorReadable4IType<T> v1)
    throws ArithmeticException
  {
    final int x = CheckedMath.add(v0.getXI(), v1.getXI());
    final int y = CheckedMath.add(v0.getYI(), v1.getYI());
    final int z = CheckedMath.add(v0.getZI(), v1.getZI());
    final int w = CheckedMath.add(v0.getWI(), v1.getWI());
    return new PVectorI4I<T>(x, y, z, w);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param r   The scaling value
   * @param <T> A phantom type parameter.
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r),
   * v0.w + (v1.w * r))}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <T> PVectorI4I<T> addScaled(
    final PVectorReadable4IType<T> v0,
    final PVectorReadable4IType<T> v1,
    final double r)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v1.getXI(), r);
    final int my = CheckedMath.multiply(v1.getYI(), r);
    final int mz = CheckedMath.multiply(v1.getZI(), r);
    final int mw = CheckedMath.multiply(v1.getWI(), r);
    final int x = CheckedMath.add(v0.getXI(), mx);
    final int y = CheckedMath.add(v0.getYI(), my);
    final int z = CheckedMath.add(v0.getZI(), mz);
    final int w = CheckedMath.add(v0.getWI(), mw);
    return new PVectorI4I<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter.
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   *
   * @since 7.0.0
   */

  public static <T> PVectorI4I<T> clamp(
    final PVectorReadable4IType<T> v,
    final int minimum,
    final int maximum)
  {
    final int x = Math.min(Math.max(v.getXI(), minimum), maximum);
    final int y = Math.min(Math.max(v.getYI(), minimum), maximum);
    final int z = Math.min(Math.max(v.getZI(), minimum), maximum);
    final int w = Math.min(Math.max(v.getWI(), minimum), maximum);
    return new PVectorI4I<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum} and {@code maximum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param maximum The vector containing the maximum acceptable values
   * @param <T>     A phantom type parameter.
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w,
   * minimum.w), maximum.w))}
   *
   * @since 7.0.0
   */

  public static <T> PVectorI4I<T> clampByPVector(
    final PVectorReadable4IType<T> v,
    final PVectorReadable4IType<T> minimum,
    final PVectorReadable4IType<T> maximum)
  {
    final int x =
      Math.min(Math.max(v.getXI(), minimum.getXI()), maximum.getXI());
    final int y =
      Math.min(Math.max(v.getYI(), minimum.getYI()), maximum.getYI());
    final int z =
      Math.min(Math.max(v.getZI(), minimum.getZI()), maximum.getZI());
    final int w =
      Math.min(Math.max(v.getWI(), minimum.getWI()), maximum.getWI());
    return new PVectorI4I<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter.
   *
   * @return A vector with both elements equal to at most {@code maximum}
   *
   * @since 7.0.0
   */

  public static <T> PVectorI4I<T> clampMaximum(
    final PVectorReadable4IType<T> v,
    final int maximum)
  {
    final int x = Math.min(v.getXI(), maximum);
    final int y = Math.min(v.getYI(), maximum);
    final int z = Math.min(v.getZI(), maximum);
    final int w = Math.min(v.getWI(), maximum);
    return new PVectorI4I<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   * @param <T>     A phantom type parameter.
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z,
   * maximum.z), min(v.w, maximum.w))}
   *
   * @since 7.0.0
   */

  public static <T> PVectorI4I<T> clampMaximumByPVector(
    final PVectorReadable4IType<T> v,
    final PVectorReadable4IType<T> maximum)
  {
    final int x = Math.min(v.getXI(), maximum.getXI());
    final int y = Math.min(v.getYI(), maximum.getYI());
    final int z = Math.min(v.getZI(), maximum.getZI());
    final int w = Math.min(v.getWI(), maximum.getWI());
    return new PVectorI4I<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param <T>     A phantom type parameter.
   *
   * @return A vector with both elements equal to at least {@code minimum}
   *
   * @since 7.0.0
   */

  public static <T> PVectorI4I<T> clampMinimum(
    final PVectorReadable4IType<T> v,
    final int minimum)
  {
    final int x = Math.max(v.getXI(), minimum);
    final int y = Math.max(v.getYI(), minimum);
    final int z = Math.max(v.getZI(), minimum);
    final int w = Math.max(v.getWI(), minimum);
    return new PVectorI4I<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param <T>     A phantom type parameter.
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z,
   * minimum.z), max(v.w, minimum.w))}
   *
   * @since 7.0.0
   */

  public static <T> PVectorI4I<T> clampMinimumByPVector(
    final PVectorReadable4IType<T> v,
    final PVectorReadable4IType<T> minimum)
  {
    final int x = Math.max(v.getXI(), minimum.getXI());
    final int y = Math.max(v.getYI(), minimum.getYI());
    final int z = Math.max(v.getZI(), minimum.getZI());
    final int w = Math.max(v.getWI(), minimum.getWI());
    return new PVectorI4I<T>(x, y, z, w);
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return The distance between the two vectors.
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static <T> int distance(
    final PVectorReadable4IType<T> v0,
    final PVectorReadable4IType<T> v1)
    throws ArithmeticException
  {
    return PVectorI4I.magnitude(PVectorI4I.subtract(v0, v1));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return The scalar product of the two vectors
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static <T> int dotProduct(
    final PVectorReadable4IType<T> v0,
    final PVectorReadable4IType<T> v1)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v0.getXI(), v1.getXI());
    final int my = CheckedMath.multiply(v0.getYI(), v1.getYI());
    final int mz = CheckedMath.multiply(v0.getZI(), v1.getZI());
    final int mw = CheckedMath.multiply(v0.getWI(), v1.getWI());

    int k;
    k = CheckedMath.add(mx, my);
    k = CheckedMath.add(k, mz);
    k = CheckedMath.add(k, mw);
    return k;
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the amount {@code
   * alpha}.
   *
   * The {@code alpha} parameter controls the degree of interpolation, such
   * that:
   *
   * <ul> <li>{@code interpolateLinear(v0, v1, 0.0, r) → r = v0}</li> <li>{@code
   * interpolateLinear(v0, v1, 1.0, r) → r = v1}</li> </ul>
   *
   * @param v0    The left input vector.
   * @param v1    The right input vector.
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}.
   * @param <T>   A phantom type parameter.
   *
   * @return {@code (1 - alpha) * v0 + alpha * v1}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow.
   * @since 7.0.0
   */

  public static <T> PVectorI4I<T> interpolateLinear(
    final PVectorReadable4IType<T> v0,
    final PVectorReadable4IType<T> v1,
    final double alpha)
    throws ArithmeticException
  {
    final PVectorI4I<T> w0 = PVectorI4I.scale(v0, 1.0 - alpha);
    final PVectorI4I<T> w1 = PVectorI4I.scale(v1, alpha);
    return PVectorI4I.add(w0, w1);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   *
   * @return The magnitude of the input vector
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static <T> int magnitude(
    final PVectorReadable4IType<T> v)
    throws ArithmeticException
  {
    return Cast.castToInt(Math.sqrt((double) PVectorI4I.magnitudeSquared(v)));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   *
   * @return The squared magnitude of the input vector
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static <T> int magnitudeSquared(
    final PVectorReadable4IType<T> v)
    throws ArithmeticException
  {
    return PVectorI4I.dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code
   * q}.
   *
   * @param p   The left vector
   * @param q   The right vector
   * @param <T> A phantom type parameter.
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static <T> PVectorI4I<T> projection(
    final PVectorReadable4IType<T> p,
    final PVectorReadable4IType<T> q)
    throws ArithmeticException
  {
    final int dot = PVectorI4I.dotProduct(p, q);
    final int qms = PVectorI4I.magnitudeSquared(q);
    final int s = dot / qms;
    return PVectorI4I.scale(p, (double) s);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param <T> A phantom type parameter.
   *
   * @return {@code (v.x * r, v.y * r, v.z * r, vw * r)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <T> PVectorI4I<T> scale(
    final PVectorReadable4IType<T> v,
    final double r)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v.getXI(), r);
    final int my = CheckedMath.multiply(v.getYI(), r);
    final int mz = CheckedMath.multiply(v.getZI(), r);
    final int mw = CheckedMath.multiply(v.getWI(), r);
    return new PVectorI4I<T>(mx, my, mz, mw);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <T> PVectorI4I<T> subtract(
    final PVectorReadable4IType<T> v0,
    final PVectorReadable4IType<T> v1)
    throws ArithmeticException
  {
    final int x = CheckedMath.subtract(v0.getXI(), v1.getXI());
    final int y = CheckedMath.subtract(v0.getYI(), v1.getYI());
    final int z = CheckedMath.subtract(v0.getZI(), v1.getZI());
    final int w = CheckedMath.subtract(v0.getWI(), v1.getWI());
    return new PVectorI4I<T>(x, y, z, w);
  }

  /**
   * @param <T> A phantom type parameter.
   *
   * @return The zero vector.
   */

  @SuppressWarnings("unchecked") public static <T> PVectorI4I<T> zero()
  {
    return (PVectorI4I<T>) PVectorI4I.ZERO;
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
    final PVectorI4I<?> other = (PVectorI4I<?>) obj;
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    if (this.z != other.z) {
      return false;
    }
    return this.w == other.w;
  }

  @Override public int getWI()
  {
    return this.w;
  }

  @Override public int getXI()
  {
    return this.x;
  }

  @Override public int getYI()
  {
    return this.y;
  }

  @Override public int getZI()
  {
    return this.z;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    result = (prime * result) + this.z;
    result = (prime * result) + this.w;
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[PVectorI4I ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append(" ");
    builder.append(this.w);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }

}