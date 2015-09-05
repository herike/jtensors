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

import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jfunctional.Pair;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p>
 * A four-dimensional mutable vector type with single precision elements.
 * </p>
 *
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 */

public final class VectorM4F implements VectorReadable4FType, VectorWritable4FType
{
  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * saving the result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z, abs.w)}
   */

  public static VectorM4F absolute(
    final VectorReadable4FType v,
    final VectorM4F out)
  {
    final float x = Math.abs(v.getXF());
    final float y = Math.abs(v.getYF());
    final float z = Math.abs(v.getZF());
    final float w = Math.abs(v.getWF());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * modifying the vector in-place.
   *
   * @param v
   *          The input vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z, abs v.w)}
   */

  public static VectorM4F absoluteInPlace(
    final VectorM4F v)
  {
    return VectorM4F.absolute(v, v);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and
   * {@code v1}, saving the result to {@code out}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)}
   */

  public static VectorM4F add(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1,
    final VectorM4F out)
  {
    final float x = v0.getXF() + v1.getXF();
    final float y = v0.getYF() + v1.getYF();
    final float z = v0.getZF() + v1.getZF();
    final float w = v0.getWF() + v1.getWF();
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and
   * {@code v1}, saving the result to {@code v0}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)}
   */

  public static VectorM4F addInPlace(
    final VectorM4F v0,
    final VectorReadable4FType v1)
  {
    return VectorM4F.add(v0, v1, v0);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}, saving the
   * result to {@code out}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   * @param r
   *          The scaling value
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))}
   */

  public static VectorM4F addScaled(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1,
    final double r,
    final VectorM4F out)
  {
    final float x = (float) ((double) v0.getXF() + ((double) v1.getXF() * r));
    final float y = (float) ((double) v0.getYF() + ((double) v1.getYF() * r));
    final float z = (float) ((double) v0.getZF() + ((double) v1.getZF() * r));
    final float w = (float) ((double) v0.getWF() + ((double) v1.getWF() * r));
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}, saving the
   * result to {@code v0}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param r
   *          The scaling value
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))}
   */

  public static VectorM4F addScaledInPlace(
    final VectorM4F v0,
    final VectorReadable4FType v1,
    final double r)
  {
    return VectorM4F.addScaled(v0, v1, r, v0);
  }

  /**
   * Determine whether or not the vectors {@code va} and {@code vb}
   * are equal to within the degree of error given in {@code context}.
   *
   * @see AlmostEqualFloat#almostEqual(AlmostEqualFloat.ContextRelative, float, float)
   *
   * @param context
   *          The equality context
   * @param va
   *          The left input vector
   * @param vb
   *          The right input vector
   * @since 5.0.0
   * @return {@code true} if the vectors are almost equal
   */

  public static boolean almostEqual(
    final AlmostEqualFloat.ContextRelative context,
    final VectorReadable4FType va,
    final VectorReadable4FType vb)
  {
    final boolean xs =
      AlmostEqualFloat.almostEqual(context, va.getXF(), vb.getXF());
    final boolean ys =
      AlmostEqualFloat.almostEqual(context, va.getYF(), vb.getYF());
    final boolean zs =
      AlmostEqualFloat.almostEqual(context, va.getZF(), vb.getZF());
    final boolean ws =
      AlmostEqualFloat.almostEqual(context, va.getWF(), vb.getWF());
    return xs && ys && zs && ws;
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. maximum]} inclusive, saving the result to
   * {@code out}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   * @param out
   *          The output vector
   *
   * @return A vector with both elements equal to at most {@code maximum}
   *         and at least {@code minimum}
   */

  public static VectorM4F clamp(
    final VectorReadable4FType v,
    final double minimum,
    final double maximum,
    final VectorM4F out)
  {
    final float x = (float) Math.min(Math.max((double) v.getXF(), minimum), maximum);
    final float y = (float) Math.min(Math.max((double) v.getYF(), minimum), maximum);
    final float z = (float) Math.min(Math.max((double) v.getZF(), minimum), maximum);
    final float w = (float) Math.min(Math.max((double) v.getWF(), minimum), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum} and
   * {@code maximum}, saving the result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @param out
   *          The output vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))}
   */

  public static VectorM4F clampByVector(
    final VectorReadable4FType v,
    final VectorReadable4FType minimum,
    final VectorReadable4FType maximum,
    final VectorM4F out)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    final float z =
      Math.min(Math.max(v.getZF(), minimum.getZF()), maximum.getZF());
    final float w =
      Math.min(Math.max(v.getWF(), minimum.getWF()), maximum.getWF());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum} and
   * {@code maximum}, saving the result to {@code v}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))}
   */

  public static VectorM4F clampByVectorInPlace(
    final VectorM4F v,
    final VectorReadable4FType minimum,
    final VectorReadable4FType maximum)
  {
    return VectorM4F.clampByVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. maximum]} inclusive, saving the result to
   * {@code v}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   *         and at least {@code minimum}, in {@code v}
   */

  public static VectorM4F clampInPlace(
    final VectorM4F v,
    final float minimum,
    final float maximum)
  {
    return VectorM4F.clamp(v, (double) minimum, (double) maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [-Infinity .. maximum]} inclusive, saving the result to
   * {@code out}.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static VectorM4F clampMaximum(
    final VectorReadable4FType v,
    final float maximum,
    final VectorM4F out)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    final float z = Math.min(v.getZF(), maximum);
    final float w = Math.min(v.getWF(), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code maximum}, saving the
   * result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @param out
   *          The output vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))}
   */

  public static VectorM4F clampMaximumByVector(
    final VectorReadable4FType v,
    final VectorReadable4FType maximum,
    final VectorM4F out)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    final float z = Math.min(v.getZF(), maximum.getZF());
    final float w = Math.min(v.getWF(), maximum.getWF());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code maximum}, saving the
   * result to {@code v}.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))}
   */

  public static VectorM4F clampMaximumByVectorInPlace(
    final VectorM4F v,
    final VectorReadable4FType maximum)
  {
    return VectorM4F.clampMaximumByVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [-Infinity .. maximum]} inclusive, saving the result to
   * {@code v}.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   *         , in {@code v}
   */

  public static VectorM4F clampMaximumInPlace(
    final VectorM4F v,
    final float maximum)
  {
    return VectorM4F.clampMaximum(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. Infinity]} inclusive, saving the result to
   * {@code out}.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param minimum
   *          The minimum allowed value
   *
   * @return A vector with both elements equal to at least
   *         {@code minimum}
   */

  public static VectorM4F clampMinimum(
    final VectorReadable4FType v,
    final float minimum,
    final VectorM4F out)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    final float z = Math.max(v.getZF(), minimum);
    final float w = Math.max(v.getWF(), minimum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum}, saving the
   * result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))}
   */

  public static VectorM4F clampMinimumByVector(
    final VectorReadable4FType v,
    final VectorReadable4FType minimum,
    final VectorM4F out)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    final float z = Math.max(v.getZF(), minimum.getZF());
    final float w = Math.max(v.getWF(), minimum.getWF());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum}, saving the
   * result to {@code v}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))}
   *         , in {@code v}
   */

  public static VectorM4F clampMinimumByVectorInPlace(
    final VectorM4F v,
    final VectorReadable4FType minimum)
  {
    return VectorM4F.clampMinimumByVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. Infinity]} inclusive, saving the result to
   * {@code v}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   *
   * @return A vector with both elements equal to at least
   *         {@code minimum}, in {@code v}.
   */

  public static VectorM4F clampMinimumInPlace(
    final VectorM4F v,
    final float minimum)
  {
    return VectorM4F.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector
   * {@code output}.
   *
   * @param <T>
   *          The specific type of vector
   * @param input
   *          The input vector
   * @param output
   *          The output vector
   *
   * @return output
   */

  public static <T extends VectorWritable4FType> T copy(
    final VectorReadable4FType input,
    final T output)
  {
    output.set4F(input.getXF(), input.getYF(), input.getZF(), input.getWF());
    return output;
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and
   * {@code v1}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return The distance between the two vectors.
   */

  public static double distance(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1)
  {
    final VectorM4F vr = new VectorM4F();
    return VectorM4F.magnitude(VectorM4F.subtract(v0, v1, vr));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and
   * {@code v1}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return The scalar product of the two vectors
   */

  public static double dotProduct(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1)
  {
    final double x = (double) (v0.getXF() * v1.getXF());
    final double y = (double) (v0.getYF() * v1.getYF());
    final double z = (double) (v0.getZF() * v1.getZF());
    final double w = (double) (v0.getWF() * v1.getWF());
    return x + y + z + w;
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the
   * amount {@code alpha}, saving the result to {@code r}.
   *
   * The {@code alpha} parameter controls the degree of interpolation,
   * such that:
   *
   * <ul>
   * <li>{@code interpolateLinear(v0, v1, 0.0, r) -> r = v0}</li>
   * <li>{@code interpolateLinear(v0, v1, 1.0, r) -> r = v1}</li>
   * </ul>
   *
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param alpha
   *          The interpolation value, between {@code 0.0} and
   *          {@code 1.0}.
   * @param r
   *          The result vector.
   *
   * @return {@code r}
   */

  public static VectorM4F interpolateLinear(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1,
    final double alpha,
    final VectorM4F r)
  {
    final VectorM4F w0 = new VectorM4F();
    final VectorM4F w1 = new VectorM4F();

    VectorM4F.scale(v0, 1.0 - alpha, w0);
    VectorM4F.scale(v1, alpha, w1);

    return VectorM4F.add(w0, w1, r);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v
   *          The input vector
   *
   * @return The magnitude of the input vector
   */

  public static double magnitude(
    final VectorReadable4FType v)
  {
    return Math.sqrt(VectorM4F.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return The squared magnitude of the input vector
   */

  public static double magnitudeSquared(
    final VectorReadable4FType v)
  {
    return VectorM4F.dotProduct(v, v);
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with
   * magnitude equal to {@code 1.0} in {@code out}. The function
   * returns the zero vector iff the input is the zero vector.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   *
   * @return out
   */

  public static VectorM4F normalize(
    final VectorReadable4FType v,
    final VectorM4F out)
  {
    final double m = VectorM4F.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorM4F.scale(v, reciprocal, out);
    }
    out.x = v.getXF();
    out.y = v.getYF();
    out.z = v.getZF();
    out.w = v.getWF();
    return out;
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with
   * magnitude equal to {@code 1.0} in {@code v}. The function
   * returns the zero vector iff the input is the zero vector.
   *
   * @param v
   *          The input vector
   *
   * @return v
   */

  public static VectorM4F normalizeInPlace(
    final VectorM4F v)
  {
    return VectorM4F.normalize(v, v);
  }

  /**
   * <p>
   * Orthonormalize and return the vectors {@code v0} and {@code v1}
   * .
   * </p>
   * <p>
   * See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   *
   * @return A pair {@code (v0, v1)}, orthonormalized.
   * @since 5.0.0
   * @param v0
   *          The left vector
   * @param v1
   *          The right vector
   */

  public static Pair<VectorM4F, VectorM4F> orthoNormalize(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1)
  {
    final VectorM4F v0n = new VectorM4F();
    final VectorM4F vr = new VectorM4F();
    final VectorM4F vp = new VectorM4F();

    VectorM4F.normalize(v0, v0n);
    VectorM4F.scale(v0n, VectorM4F.dotProduct(v1, v0n), vp);
    VectorM4F.normalizeInPlace(VectorM4F.subtract(v1, vp, vr));
    return Pair.pair(v0n, vr);
  }

  /**
   * <p>
   * Orthonormalize and the vectors {@code v0} and {@code v1}.
   * </p>
   * <p>
   * See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   *
   * @since 5.0.0
   * @param v0
   *          The left vector
   * @param v1
   *          The right vector
   */

  public static void orthoNormalizeInPlace(
    final VectorM4F v0,
    final VectorM4F v1)
  {
    final VectorM4F projection = new VectorM4F();

    VectorM4F.normalizeInPlace(v0);
    VectorM4F.scale(v0, VectorM4F.dotProduct(v1, v0), projection);
    VectorM4F.subtractInPlace(v1, projection);
    VectorM4F.normalizeInPlace(v1);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector
   * {@code q}, saving the result in {@code r}.
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   * @param p
   *          The left vector
   * @param q
   *          The right vector
   * @param r
   *          The output vector
   */

  public static VectorM4F projection(
    final VectorReadable4FType p,
    final VectorReadable4FType q,
    final VectorM4F r)
  {
    final double dot = VectorM4F.dotProduct(p, q);
    final double qms = VectorM4F.magnitudeSquared(q);
    final double s = dot / qms;

    return VectorM4F.scale(p, s, r);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the
   * result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * @param out
   *          The output vector
   *
   * @return {@code (v.x * r, v.y * r, v.z * r, v.w * r)}
   */

  public static VectorM4F scale(
    final VectorReadable4FType v,
    final double r,
    final VectorM4F out)
  {
    final float x = (float) ((double) v.getXF() * r);
    final float y = (float) ((double) v.getYF() * r);
    final float z = (float) ((double) v.getZF() * r);
    final float w = (float) ((double) v.getWF() * r);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the
   * result to {@code v}.
   *
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   *
   * @return {@code (v.x * r, v.y * r, v.z * r, v.w * r)}
   */

  public static VectorM4F scaleInPlace(
    final VectorM4F v,
    final double r)
  {
    return VectorM4F.scale(v, r, v);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0},
   * saving the result to {@code out}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   */

  public static VectorM4F subtract(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1,
    final VectorM4F out)
  {
    final float x = v0.getXF() - v1.getXF();
    final float y = v0.getYF() - v1.getYF();
    final float z = v0.getZF() - v1.getZF();
    final float w = v0.getWF() - v1.getWF();
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0},
   * saving the result to {@code v0}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   */

  public static VectorM4F subtractInPlace(
    final VectorM4F v0,
    final VectorReadable4FType v1)
  {
    return VectorM4F.subtract(v0, v1, v0);
  }

  private float w = 1.0f;
  private float x;
  private float y;
  private float z;

  /**
   * Default constructor, initializing the vector with values
   * {@code [0.0, 0.0, 0.0, 1.0]}.
   */

  public VectorM4F()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x
   *          The {@code x} value
   * @param in_y
   *          The {@code y} value
   * @param in_z
   *          The {@code z} value
   * @param in_w
   *          The {@code w} value
   */

  public VectorM4F(
    final float in_x,
    final float in_y,
    final float in_z,
    final float in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * {@code in_v}.
   *
   * @param in_v
   *          The source vector
   */

  public VectorM4F(
    final VectorReadable4FType in_v)
  {
    this.x = in_v.getXF();
    this.y = in_v.getYF();
    this.z = in_v.getZF();
    this.w = in_v.getWF();
  }

  @Override public void copyFrom2F(
    final VectorReadable2FType in_v)
  {
    VectorM2F.copy(in_v, this);
  }

  @Override public void copyFrom3F(
    final VectorReadable3FType in_v)
  {
    VectorM3F.copy(in_v, this);
  }

  @Override public void copyFrom4F(
    final VectorReadable4FType in_v)
  {
    VectorM4F.copy(in_v, this);
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
    final VectorM4F other = (VectorM4F) obj;
    if (Float.floatToIntBits(this.w) != Float.floatToIntBits(other.w)) {
      return false;
    }
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
      return false;
    }
    if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
      return false;
    }
    return true;
  }

  @Override public float getWF()
  {
    return this.w;
  }

  @Override public float getXF()
  {
    return this.x;
  }

  @Override public float getYF()
  {
    return this.y;
  }

  @Override public float getZF()
  {
    return this.z;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.w);
    result = (prime * result) + Float.floatToIntBits(this.x);
    result = (prime * result) + Float.floatToIntBits(this.y);
    result = (prime * result) + Float.floatToIntBits(this.z);
    return result;
  }

  @Override public void set2F(
    final float in_x,
    final float in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override public void set3F(
    final float in_x,
    final float in_y,
    final float in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  @Override public void set4F(
    final float in_x,
    final float in_y,
    final float in_z,
    final float in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  @Override public void setWF(
    final float in_w)
  {
    this.w = in_w;
  }

  @Override public void setXF(
    final float in_x)
  {
    this.x = in_x;
  }

  @Override public void setYF(
    final float in_y)
  {
    this.y = in_y;
  }

  @Override public void setZF(
    final float in_z)
  {
    this.z = in_z;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM4F ");
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
