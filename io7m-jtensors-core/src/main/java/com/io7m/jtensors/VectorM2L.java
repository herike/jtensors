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

package com.io7m.jtensors;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p> A two-dimensional mutable vector type with integer elements. </p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 *
 * @since 5.3.0
 */

public final class VectorM2L implements Vector2LType
{
  private long x;
  private long y;

  /**
   * Default constructor, initializing the vector with values {@code [0, 0,
   * 0]}.
   */

  public VectorM2L()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   */

  public VectorM2L(
    final long in_x,
    final long in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code
   * in_v}.
   *
   * @param in_v The source vector
   */

  public VectorM2L(
    final VectorReadable2LType in_v)
  {
    this.x = in_v.getXL();
    this.y = in_v.getYL();
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}, saving
   * the result to {@code out}.
   *
   * @param v   The input vector
   * @param out The output vector
   * @param <V> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable2LType> V absolute(
    final VectorReadable2LType v,
    final V out)
    throws ArithmeticException
  {
    final long x = CheckedMath.absolute(v.getXL());
    final long y = CheckedMath.absolute(v.getYL());
    out.set2L(x, y);
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}, saving
   * the result to {@code v}.
   *
   * @param v   The input vector
   * @param <V> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable2LType & VectorReadable2LType> V
  absoluteInPlace(
    final V v)
    throws ArithmeticException
  {
    return VectorM2L.absolute(v, v);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1},
   * saving the result to {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable2LType> V add(
    final VectorReadable2LType v0,
    final VectorReadable2LType v1,
    final V out)
    throws ArithmeticException
  {
    final long x = CheckedMath.add(v0.getXL(), v1.getXL());
    final long y = CheckedMath.add(v0.getYL(), v1.getYL());
    out.set2L(x, y);
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1},
   * saving the result to {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable2LType & VectorReadable2LType> V
  addInPlace(
    final V v0,
    final VectorReadable2LType v1)
    throws ArithmeticException
  {
    return VectorM2L.add(v0, v1, v0);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}, saving the result to
   * {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param r   The scaling value
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable2LType> V addScaled(
    final VectorReadable2LType v0,
    final VectorReadable2LType v1,
    final double r,
    final V out)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v1.getXL(), r);
    final long my = CheckedMath.multiply(v1.getYL(), r);
    final long x = CheckedMath.add(v0.getXL(), mx);
    final long y = CheckedMath.add(v0.getYL(), my);
    out.set2L(x, y);
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}, saving the result to
   * {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param r   The scaling value
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable2LType & VectorReadable2LType> V
  addScaledInPlace(
    final V v0,
    final VectorReadable2LType v1,
    final double r)
    throws ArithmeticException
  {
    return VectorM2L.addScaled(v0, v1, r, v0);
  }

  /**
   * Calculate the angle between vectors {@code v0} and {@code v1}, in radians.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The angle between the two vectors, in radians.
   */

  public static double angle(
    final VectorReadable2LType v0,
    final VectorReadable2LType v1)
  {
    final double m0 = (double) VectorM2L.magnitude(v0);
    final double m1 = (double) VectorM2L.magnitude(v1);
    return Math.acos((double) VectorM2L.dotProduct(v0, v1) / (m0 * m1));
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param out     The output vector
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   */

  public static <V extends VectorWritable2LType> V clamp(
    final VectorReadable2LType v,
    final long minimum,
    final long maximum,
    final V out)
  {
    final long x = Math.min(Math.max(v.getXL(), minimum), maximum);
    final long y = Math.min(Math.max(v.getYL(), minimum), maximum);
    out.set2L(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum} and {@code maximum}, saving
   * the result to {@code out}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param maximum The vector containing the maximum acceptable values
   * @param out     The output vector
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   */

  public static <V extends VectorWritable2LType> V clampByVector(
    final VectorReadable2LType v,
    final VectorReadable2LType minimum,
    final VectorReadable2LType maximum,
    final V out)
  {
    final long x =
      Math.min(Math.max(v.getXL(), minimum.getXL()), maximum.getXL());
    final long y =
      Math.min(Math.max(v.getYL(), minimum.getYL()), maximum.getYL());
    out.set2L(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum} and {@code maximum}, saving
   * the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param maximum The vector containing the maximum acceptable values
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   */

  public static <V extends VectorWritable2LType & VectorReadable2LType> V
  clampByVectorInPlace(
    final V v,
    final VectorReadable2LType minimum,
    final VectorReadable2LType maximum)
  {
    return VectorM2L.clampByVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}, in {@code v}
   */

  public static <V extends VectorWritable2LType & VectorReadable2LType> V
  clampInPlace(
    final V v,
    final long minimum,
    final long maximum)
  {
    return VectorM2L.clamp(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param out     The output vector
   * @param maximum The maximum allowed value
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static <V extends VectorWritable2LType> V clampMaximum(
    final VectorReadable2LType v,
    final long maximum,
    final V out)
  {
    final long x = Math.min(v.getXL(), maximum);
    final long y = Math.min(v.getYL(), maximum);
    out.set2L(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}, saving the result to {@code
   * out}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   * @param out     The output vector
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z,
   * maximum.z))}
   */

  public static <V extends VectorWritable2LType> V clampMaximumByVector(
    final VectorReadable2LType v,
    final VectorReadable2LType maximum,
    final V out)
  {
    final long x = Math.min(v.getXL(), maximum.getXL());
    final long y = Math.min(v.getYL(), maximum.getYL());
    out.set2L(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}, saving the result to {@code
   * v}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z,
   * maximum.z))}
   */

  public static <V extends VectorWritable2LType & VectorReadable2LType> V
  clampMaximumByVectorInPlace(
    final V v,
    final VectorReadable2LType maximum)
  {
    return VectorM2L.clampMaximumByVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} , in
   * {@code v}
   */

  public static <V extends VectorWritable2LType & VectorReadable2LType> V
  clampMaximumInPlace(
    final V v,
    final long maximum)
  {
    return VectorM2L.clampMaximum(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param out     The output vector
   * @param minimum The minimum allowed value
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at least {@code minimum}
   */

  public static <V extends VectorWritable2LType> V clampMinimum(
    final VectorReadable2LType v,
    final long minimum,
    final V out)
  {
    final long x = Math.max(v.getXL(), minimum);
    final long y = Math.max(v.getYL(), minimum);
    out.set2L(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}, saving the result to {@code
   * out}.
   *
   * @param v       The input vector
   * @param out     The output vector
   * @param minimum The vector containing the minimum acceptable values
   * @param <V>     The precise type of vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z,
   * minimum.z))}
   */

  public static <V extends VectorWritable2LType> V clampMinimumByVector(
    final VectorReadable2LType v,
    final VectorReadable2LType minimum,
    final V out)
  {
    final long x = Math.max(v.getXL(), minimum.getXL());
    final long y = Math.max(v.getYL(), minimum.getYL());
    out.set2L(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}, saving the result to {@code
   * v}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param <V>     The precise type of vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z,
   * minimum.z))} , in {@code v}
   */

  public static <V extends VectorWritable2LType & VectorReadable2LType> V
  clampMinimumByVectorInPlace(
    final V v,
    final VectorReadable2LType minimum)
  {
    return VectorM2L.clampMinimumByVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at least {@code minimum}, in
   * {@code v}.
   */

  public static <V extends VectorWritable2LType & VectorReadable2LType> V
  clampMinimumInPlace(
    final V v,
    final long minimum)
  {
    return VectorM2L.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector {@code
   * output}.
   *
   * @param <V>    The specific vector type
   * @param input  The input vector
   * @param output The output vector
   *
   * @return output
   */

  public static <V extends VectorWritable2LType> V copy(
    final VectorReadable2LType input,
    final V output)
  {
    output.set2L(input.getXL(), input.getYL());
    return output;
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param c  Preallocated storage
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The distance between the two vectors.
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static long distance(
    final ContextVM2L c,
    final VectorReadable2LType v0,
    final VectorReadable2LType v1)
    throws ArithmeticException
  {
    return VectorM2L.magnitude(VectorM2L.subtract(v0, v1, c.v2a));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The scalar product of the two vectors
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static long dotProduct(
    final VectorReadable2LType v0,
    final VectorReadable2LType v1)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v0.getXL(), v1.getXL());
    final long my = CheckedMath.multiply(v0.getYL(), v1.getYL());
    return CheckedMath.add(mx, my);
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the amount {@code
   * alpha}, saving the result to {@code r}.
   *
   * The {@code alpha} parameter controls the degree of interpolation, such
   * that:
   *
   * <ul> <li>{@code interpolateLinear(v0, v1, 0.0, r) -> r = v0}</li>
   * <li>{@code interpolateLinear(v0, v1, 1.0, r) -> r = v1}</li> </ul>
   *
   * @param c     Preallocated storage
   * @param v0    The left input vector.
   * @param v1    The right input vector.
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}.
   * @param r     The result vector.
   * @param <V>   The precise type of vector
   *
   * @return {@code r}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow.
   * @since 7.0.0
   */

  public static <V extends VectorWritable2LType> V interpolateLinear(
    final ContextVM2L c,
    final VectorReadable2LType v0,
    final VectorReadable2LType v1,
    final double alpha,
    final V r)
    throws ArithmeticException
  {
    VectorM2L.scale(v0, 1.0 - alpha, c.v2a);
    VectorM2L.scale(v1, alpha, c.v2b);
    return VectorM2L.add(c.v2a, c.v2b, r);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v The input vector
   *
   * @return The magnitude of the input vector
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static long magnitude(
    final VectorReadable2LType v)
    throws ArithmeticException
  {
    return Cast.castToLong(Math.sqrt((double) VectorM2L.magnitudeSquared(v)));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v The input vector
   *
   * @return The squared magnitude of the input vector
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static long magnitudeSquared(
    final VectorReadable2LType v)
    throws ArithmeticException
  {
    return VectorM2L.dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code q},
   * saving the result in {@code r}.
   *
   * @param p   The left vector
   * @param q   The right vector
   * @param r   The output vector
   * @param <V> The precise type of vector
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable2LType> V projection(
    final VectorReadable2LType p,
    final VectorReadable2LType q,
    final V r)
    throws ArithmeticException
  {
    final long dot = VectorM2L.dotProduct(p, q);
    final long qms = VectorM2L.magnitudeSquared(q);
    final long s = dot / qms;

    return VectorM2L.scale(p, (double) s, r);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code out}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param out The output vector
   * @param <V> The precise type of vector
   *
   * @return {@code (v.x * r, v.y * r, v.z * r)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable2LType> V scale(
    final VectorReadable2LType v,
    final double r,
    final V out)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v.getXL(), r);
    final long my = CheckedMath.multiply(v.getYL(), r);
    out.set2L(mx, my);
    return out;
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code v}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param <V> The precise type of vector
   *
   * @return {@code (v.x * r, v.y * r, v.z * r)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable2LType & VectorReadable2LType> V
  scaleInPlace(
    final V v,
    final long r)
    throws ArithmeticException
  {
    return VectorM2L.scale(v, (double) r, v);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}, saving the
   * result to {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable2LType> V subtract(
    final VectorReadable2LType v0,
    final VectorReadable2LType v1,
    final V out)
    throws ArithmeticException
  {
    final long mx = CheckedMath.subtract(v0.getXL(), v1.getXL());
    final long my = CheckedMath.subtract(v0.getYL(), v1.getYL());
    out.set2L(mx, my);
    return out;
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}, saving the
   * result to {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable2LType & VectorReadable2LType> V
  subtractInPlace(
    final V v0,
    final VectorReadable2LType v1)
    throws ArithmeticException
  {
    return VectorM2L.subtract(v0, v1, v0);
  }

  @Override public void copyFrom2L(
    final VectorReadable2LType in_v)
  {
    VectorM2L.copy(in_v, this);
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
    final VectorM2L other = (VectorM2L) obj;
    if (this.x != other.x) {
      return false;
    }
    return this.y == other.y;
  }

  @Override public long getXL()
  {
    return this.x;
  }

  @Override public void setXL(
    final long in_x)
  {
    this.x = in_x;
  }

  @Override public long getYL()
  {
    return this.y;
  }

  @Override public void setYL(
    final long in_y)
  {
    this.y = in_y;
  }

  @Override public int hashCode()
  {
    final long prime = 31L;
    long result = 1L;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    return (int) result;
  }

  @Override public void set2L(
    final long in_x,
    final long in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM2L ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }

  /**
   * Preallocated storage to allow all vector functions to run without
   * allocating.
   *
   * @since 7.0.0
   */

  public static final class ContextVM2L
  {
    private final VectorM2L v2a;
    private final VectorM2L v2b;

    /**
     * Construct preallocated storage.
     */

    public ContextVM2L()
    {
      this.v2a = new VectorM2L();
      this.v2b = new VectorM2L();
    }
  }
}
