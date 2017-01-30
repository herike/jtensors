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

import com.io7m.junreachable.UnreachableCodeException;

//@formatter:off

/**
 * <p>
 * Functions over 2x2 mutable matrix types with {@code float} elements.
 * </p>
 * <p>
 * See "Mathematics for 3D Game Programming and Computer Graphics" 2nd Ed
 * for the derivations of most of the code in this class (ISBN: 1-58450-277-0).
 * </p>
 * <p>
 * See http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations
 * for the three <i>elementary</i> operations defined on matrices.
 * </p>
 */

//@formatter:on

public final class MatrixM2x2F
{
  private MatrixM2x2F()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1}.
   *
   * @param <M> The precise type of matrix
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M add(
    final MatrixReadable2x2FType m0,
    final MatrixReadable2x2FType m1,
    final M out)
  {
    final float r0c0 = m0.getR0C0F() + m1.getR0C0F();
    final float r1c0 = m0.getR1C0F() + m1.getR1C0F();

    final float r0c1 = m0.getR0C1F() + m1.getR0C1F();
    final float r1c1 = m0.getR1C1F() + m1.getR1C1F();

    out.setR0C0F(r0c0);
    out.setR1C0F(r1c0);

    out.setR0C1F(r0c1);
    out.setR1C1F(r1c1);
    return out;
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1}, returning the result
   * in {@code m0}.
   *
   * @param <M> The precise type of matrix
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   *
   * @return {@code m0}
   *
   * @since 5.0.0
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  addInPlace(
    final M m0,
    final MatrixReadable2x2FType m1)
  {
    return MatrixM2x2F.add(m0, m1, m0);
  }

  /**
   * <p> Add the values in row {@code row_b} to the values in row {@code row_a}
   * scaled by {@code r}, saving the resulting row in row {@code row_c} of the
   * matrix {@code out}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param <M>   The precise type of matrix
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The row on the lefthand side of the addition
   * @param row_b The row on the righthand side of the addition
   * @param row_c The destination row
   * @param r     The scaling value
   * @param out   The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M addRowScaled(
    final ContextMM2F c,
    final MatrixReadable2x2FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r,
    final M out)
  {
    return MatrixM2x2F.addRowScaledUnsafe(
      m,
      MatrixM2x2F.checkRow(row_a),
      MatrixM2x2F.checkRow(row_b),
      MatrixM2x2F.checkRow(row_c),
      (double) r,
      c.v2a,
      c.v2b,
      out);
  }

  /**
   * <p> Add the values in row {@code row_b} to the values in row {@code row_a}
   * scaled by {@code r}, saving the resulting row in row {@code row_c} of the
   * matrix {@code m}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param <M>   The precise type of matrix
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The row on the lefthand side of the addition
   * @param row_b The row on the righthand side of the addition
   * @param row_c The destination row
   * @param r     The scaling value
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  addRowScaledInPlace(
    final ContextMM2F c,
    final M m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r)
  {
    return MatrixM2x2F.addRowScaled(c, m, row_a, row_b, row_c, r, m);
  }

  private static <M extends MatrixWritable2x2FType> M addRowScaledUnsafe(
    final MatrixReadable2x2FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final VectorM2F va,
    final VectorM2F vb,
    final M out)
  {
    m.getRow2FUnsafe(row_a, va);
    m.getRow2FUnsafe(row_b, vb);
    VectorM2F.addScaledInPlace(va, vb, r);
    out.setRowWith2FUnsafe(row_c, va);
    return out;
  }

  /**
   * Copy the contents of the matrix {@code input} to the matrix {@code output},
   * completely replacing all elements.
   *
   * @param <M>    The precise type of matrix
   * @param input  The input vector
   * @param output The output vector
   *
   * @return {@code output}
   */

  public static <M extends MatrixWritable2x2FType> M copy(
    final MatrixReadable2x2FType input,
    final M output)
  {
    output.setR0C0F(input.getR0C0F());
    output.setR1C0F(input.getR1C0F());
    output.setR0C1F(input.getR0C1F());
    output.setR1C1F(input.getR1C1F());
    return output;
  }

  /**
   * Calculate the determinant of the matrix {@code m}.
   *
   * @param m The input matrix
   *
   * @return The determinant
   */

  public static float determinant(
    final MatrixReadable2x2FType m)
  {
    final float r0c0 = m.getR0C0F();
    final float r0c1 = m.getR0C1F();
    final float r1c0 = m.getR1C0F();
    final float r1c1 = m.getR1C1F();

    return (r0c0 * r1c1) - (r0c1 * r1c0);
  }

  /**
   * <p> Exchange two rows {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code out}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param <M>   The precise type of matrix
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The first row
   * @param row_b The second row
   * @param out   The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M exchangeRows(
    final ContextMM2F c,
    final MatrixReadable2x2FType m,
    final int row_a,
    final int row_b,
    final M out)
  {
    return MatrixM2x2F.exchangeRowsUnsafe(
      m,
      MatrixM2x2F.checkRow(row_a),
      MatrixM2x2F.checkRow(row_b),
      c.v2a,
      c.v2b,
      out);
  }

  /**
   * <p> Exchange two rows {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code m}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param <M>   The precise type of matrix
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The first row
   * @param row_b The second row
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  exchangeRowsInPlace(
    final ContextMM2F c,
    final M m,
    final int row_a,
    final int row_b)
  {
    return MatrixM2x2F.exchangeRows(c, m, row_a, row_b, m);
  }

  private static <M extends MatrixWritable2x2FType> M exchangeRowsUnsafe(
    final MatrixReadable2x2FType m,
    final int row_a,
    final int row_b,
    final VectorM2F va,
    final VectorM2F vb,
    final M out)
  {
    m.getRow2FUnsafe(row_a, va);
    m.getRow2FUnsafe(row_b, vb);
    out.setRowWith2FUnsafe(row_a, vb);
    out.setRowWith2FUnsafe(row_b, va);
    return out;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code out}. The function returns {@code Some(out)} iff it was possible
   * to invert the matrix, and {@code None} otherwise. It is not possible to
   * invert a matrix that has a determinant of {@code 0}. If the function
   * returns {@code None}, {@code m} is untouched.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param out The output matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM2x2F#determinant(MatrixReadable2x2FType)
   */

  public static <M extends MatrixWritable2x2FType> boolean invert(
    final MatrixReadable2x2FType m,
    final M out)
  {
    final float d = MatrixM2x2F.determinant(m);

    if (d == 0.0F) {
      return false;
    }

    final float d_inv = 1.0F / d;

    final float r0c0 = m.getR1C1F() * d_inv;
    final float r0c1 = -m.getR0C1F() * d_inv;
    final float r1c0 = -m.getR1C0F() * d_inv;
    final float r1c1 = m.getR0C0F() * d_inv;

    out.setR0C0F(r0c0);
    out.setR0C1F(r0c1);
    out.setR1C0F(r1c0);
    out.setR1C1F(r1c1);

    return true;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code m}. The function returns {@code Some(m)} iff it was possible to
   * invert the matrix, and {@code None} otherwise. It is not possible to invert
   * a matrix that has a determinant of {@code 0}. If the function returns
   * {@code None}, {@code m} is untouched.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM2x2F#determinant(MatrixReadable2x2FType)
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType>
  boolean invertInPlace(
    final M m)
  {
    return MatrixM2x2F.invert(m, m);
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code out}.
   *
   * @param <M> The precise type of matrix
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M multiply(
    final MatrixReadable2x2FType m0,
    final MatrixReadable2x2FType m1,
    final M out)
  {
    final float r0c0_l = m0.getR0C0F();
    final float r1c0_l = m0.getR1C0F();
    final float r0c1_l = m0.getR0C1F();
    final float r1c1_l = m0.getR1C1F();

    final float r0c0_r = m1.getR0C0F();
    final float r0c1_r = m1.getR0C1F();
    final float r1c0_r = m1.getR1C0F();
    final float r1c1_r = m1.getR1C1F();

    final float r0c0 = (r0c0_l * r0c0_r) + (r1c0_l * r0c1_r);
    final float r0c1 = (r0c1_l * r0c0_r) + (r1c1_l * r0c1_r);
    final float r1c0 = (r0c0_l * r1c0_r) + (r1c0_l * r1c1_r);
    final float r1c1 = (r0c1_l * r1c0_r) + (r1c1_l * r1c1_r);

    out.setR0C0F(r0c0);
    out.setR0C1F(r0c1);
    out.setR1C0F(r1c0);
    out.setR1C1F(r1c1);

    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code m0}.
   *
   * @param <M> The precise type of matrix
   * @param m0  The left input vector
   * @param m1  The right input vector
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  multiplyInPlace(
    final M m0,
    final MatrixReadable2x2FType m1)
  {
    return MatrixM2x2F.multiply(m0, m1, m0);
  }

  /**
   * Multiply the matrix {@code m} with the vector {@code v}, writing the
   * resulting vector to {@code out}.
   *
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param v   The input vector
   * @param out The output vector
   * @param <V> The precise type of writable vector
   *
   * @return {@code out}
   */

  public static <V extends VectorWritable2FType> V multiplyVector2F(
    final ContextMM2F c,
    final MatrixReadable2x2FType m,
    final VectorReadable2FType v,
    final V out)
  {
    final VectorM2F row = c.v2a;

    m.getRow2FUnsafe(0, row);
    out.setXF(VectorM2F.dotProduct(row, v));
    m.getRow2FUnsafe(1, row);
    out.setYF(VectorM2F.dotProduct(row, v));
    return out;
  }

  private static int checkRow(
    final int row)
  {
    if ((row < 0) || (row >= 2)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < 2");
    }
    return row;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code out}.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param r   The scaling value
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M scale(
    final MatrixReadable2x2FType m,
    final float r,
    final M out)
  {
    out.setR0C0F(m.getR0C0F() * r);
    out.setR1C0F(m.getR1C0F() * r);
    out.setR0C1F(m.getR0C1F() * r);
    out.setR1C1F(m.getR1C1F() * r);
    return out;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code m}.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param r   The scaling value
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  scaleInPlace(
    final M m,
    final float r)
  {
    return MatrixM2x2F.scale(m, r, m);
  }

  /**
   * <p> Scale row {@code r} of the matrix {@code m} by {@code r}, saving the
   * result to row {@code r} of {@code out}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param <M> The precise type of matrix
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param row The index of the row {@code (0 <= row < 2)}
   * @param r   The scaling value
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M scaleRow(
    final ContextMM2F c,
    final MatrixReadable2x2FType m,
    final int row,
    final float r,
    final M out)
  {
    return MatrixM2x2F.scaleRowUnsafe(
      m, MatrixM2x2F.checkRow(row), (double) r, c.v2a, out);
  }

  /**
   * <p> Scale row {@code r} of the matrix {@code m} by {@code r}, saving the
   * result to row {@code r} of {@code m}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param <M> The precise type of matrix
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param row The index of the row {@code (0 <= row < 2)}
   * @param r   The scaling value
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  scaleRowInPlace(
    final ContextMM2F c,
    final M m,
    final int row,
    final float r)
  {
    return MatrixM2x2F.scaleRowUnsafe(
      m, MatrixM2x2F.checkRow(row), (double) r, c.v2a, m);
  }

  private static <M extends MatrixWritable2x2FType> M scaleRowUnsafe(
    final MatrixReadable2x2FType m,
    final int row,
    final double r,
    final VectorM2F tmp,
    final M out)
  {
    m.getRow2FUnsafe(row, tmp);
    VectorM2F.scaleInPlace(tmp, r);
    out.setRowWith2FUnsafe(row, tmp);
    return out;
  }

  /**
   * Set the given matrix {@code m} to the identity matrix.
   *
   * @param <M> The precise type of matrix
   * @param m   The matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType> M setIdentity(
    final M m)
  {
    m.setR0C0F(1.0f);
    m.setR0C1F(0.0f);
    m.setR1C0F(0.0f);
    m.setR1C1F(1.0f);
    return m;
  }

  /**
   * Set the given matrix {@code m} to the zero matrix.
   *
   * @param <M> The precise type of matrix
   * @param m   The matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType> M setZero(
    final M m)
  {
    m.setR0C0F(0.0f);
    m.setR0C1F(0.0f);
    m.setR1C0F(0.0f);
    m.setR1C1F(0.0f);
    return m;
  }

  /**
   * Return the trace of the matrix {@code m}. The trace is defined as the sum
   * of the diagonal elements of the matrix.
   *
   * @param m The input matrix
   *
   * @return The trace of the matrix
   *
   * @since 5.0.0
   */

  public static float trace(
    final MatrixReadable2x2FType m)
  {
    return m.getR0C0F() + m.getR1C1F();
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix to
   * {@code out}.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M transpose(
    final MatrixReadable2x2FType m,
    final M out)
  {
    final float r0c0 = m.getR0C0F();
    final float r1c0 = m.getR1C0F();

    final float r0c1 = m.getR0C1F();
    final float r1c1 = m.getR1C1F();

    out.setR0C0F(r0c0);
    out.setR1C0F(r0c1); // swap 0

    out.setR0C1F(r1c0); // swap 0
    out.setR1C1F(r1c1);

    return out;
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix to
   * {@code m}.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  transposeInPlace(
    final M m)
  {
    final float r1c0 = m.getR1C0F();
    final float r0c1 = m.getR0C1F();

    m.setR1C0F(r0c1); // swap 0
    m.setR0C1F(r1c0); // swap 0
    return m;
  }

  /**
   * Compare matrices.
   *
   * @param m0 The left matrix
   * @param m1 The right matrix
   *
   * @return {@code true} if all elements of {@code m0} are equal to {@code m1}.
   *
   * @since 7.0.0
   */

  public static boolean compareElements(
    final MatrixReadable2x2FType m0,
    final MatrixReadable2x2FType m1)
  {
    if (!MatrixM2x2F.compareRow0(m0, m1)) {
      return false;
    }
    return MatrixM2x2F.compareRow1(m0, m1);
  }

  /**
   * Hash matrices.
   *
   * @param m The input matrix
   *
   * @return The hash of all the elements of {@code m}
   *
   * @since 7.0.0
   */

  public static int hashElements(final MatrixReadable2x2FType m)
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateFloatHash(m.getR0C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(m.getR1C0F(), prime, r);

    r = HashUtility.accumulateFloatHash(m.getR0C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(m.getR1C1F(), prime, r);

    return r;
  }

  /**
   * Show matrices. Print all of the elements of {@code m} in square-bracketed
   * matrix form.
   *
   * @param m  The input matrix
   * @param sb The string builder
   *
   * @since 7.0.0
   */

  public static void showElements(
    final MatrixReadable2x2FType m,
    final StringBuilder sb)
  {
    final String row0 = String.format(
      "[%+.6f %+.6f]\n", m.getR0C0F(), m.getR0C1F());
    final String row1 = String.format(
      "[%+.6f %+.6f]\n", m.getR1C0F(), m.getR1C1F());
    sb.append(row0);
    sb.append(row1);
  }

  private static boolean compareRow0(
    final MatrixReadable2x2FType m0,
    final MatrixReadable2x2FType m1)
  {
    if (m0.getR0C0F() != m1.getR0C0F()) {
      return false;
    }
    return m0.getR0C1F() == m1.getR0C1F();
  }

  private static boolean compareRow1(
    final MatrixReadable2x2FType m0,
    final MatrixReadable2x2FType m1)
  {
    if (m0.getR1C0F() != m1.getR1C0F()) {
      return false;
    }
    return m0.getR1C1F() == m1.getR1C1F();
  }

  /**
   * <p>The {@code ContextMM2F} type contains the minimum storage required for
   * all of the functions of the {@code MatrixM2x2F} class.</p>
   *
   * <p> The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical
   * code.</p>
   *
   * <p> The user should allocate one {@code ContextMM2F} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextMM2F} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static final class ContextMM2F
  {
    private final VectorM2F v2a = new VectorM2F();
    private final VectorM2F v2b = new VectorM2F();

    /**
     * Construct a new context.
     */

    public ContextMM2F()
    {

    }
  }
}