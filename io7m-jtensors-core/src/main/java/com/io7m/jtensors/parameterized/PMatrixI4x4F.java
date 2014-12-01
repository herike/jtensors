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

package com.io7m.jtensors.parameterized;

import java.util.Arrays;

import com.io7m.jequality.annotations.EqualityStructural;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorReadable4FType;
import com.io7m.jtensors.VectorWritable4FType;

/**
 * An immutable 4x4 matrix type.
 *
 * @param <T0>
 *          A phantom type parameter.
 * @param <T1>
 *          A phantom type parameter.
 */

@EqualityStructural public final class PMatrixI4x4F<T0, T1> implements
  PMatrixReadable4x4FType<T0, T1>
{
  private static final float[][]              IDENTITY  = PMatrixI4x4F
                                                          .makeIdentity();
  private static PMatrixI4x4F<Object, Object> IDENTITYM = PMatrixI4x4F
                                                          .makeIdentityM();

  /**
   * @return The identity matrix
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  @SuppressWarnings("unchecked") public static
    <T0, T1>
    PMatrixI4x4F<T0, T1>
    identity()
  {
    return (PMatrixI4x4F<T0, T1>) PMatrixI4x4F.IDENTITYM;
  }

  private static float[][] makeIdentity()
  {
    final float[][] m = new float[4][4];
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        if (row == col) {
          m[row][col] = 1.0f;
        } else {
          m[row][col] = 0.0f;
        }
      }
    }
    return m;
  }

  private static PMatrixI4x4F<Object, Object> makeIdentityM()
  {
    return new PMatrixI4x4F<Object, Object>(PMatrixI4x4F.IDENTITY);
  }

  /**
   * Construct a new immutable 4x4 matrix from the given columns.
   *
   * @param column_0
   *          The first column
   * @param column_1
   *          The second column
   * @param column_2
   *          The third column
   * @param column_3
   *          The fourth column
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   *
   * @return A new 4x4 matrix
   */

  public static <T0, T1> PMatrixI4x4F<T0, T1> newFromColumns(
    final VectorReadable4FType column_0,
    final VectorReadable4FType column_1,
    final VectorReadable4FType column_2,
    final VectorReadable4FType column_3)
  {
    final float[][] e = new float[4][4];

    e[0][0] = column_0.getXF();
    e[1][0] = column_0.getYF();
    e[2][0] = column_0.getZF();
    e[3][0] = column_0.getWF();

    e[0][1] = column_1.getXF();
    e[1][1] = column_1.getYF();
    e[2][1] = column_1.getZF();
    e[3][1] = column_1.getWF();

    e[0][2] = column_2.getXF();
    e[1][2] = column_2.getYF();
    e[2][2] = column_2.getZF();
    e[3][2] = column_2.getWF();

    e[0][3] = column_3.getXF();
    e[1][3] = column_3.getYF();
    e[2][3] = column_3.getZF();
    e[3][3] = column_3.getWF();

    return new PMatrixI4x4F<T0, T1>(e);
  }

  /**
   * Construct a new immutable 4x4 matrix from the given readable 4x4 matrix.
   *
   * @param m
   *          The original matrix
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   *
   * @return A new 4x4 matrix
   */

  public static <T0, T1> PMatrixI4x4F<T0, T1> newFromReadable(
    final PMatrixReadable4x4FType<T0, T1> m)
  {
    final float[][] e = new float[4][4];

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        e[row][col] = m.getRowColumnF(row, col);
      }
    }

    return new PMatrixI4x4F<T0, T1>(e);
  }

  private final float[][] elements;

  private PMatrixI4x4F(
    final float[][] e)
  {
    this.elements = e;
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
    final PMatrixI4x4F<?, ?> other = (PMatrixI4x4F<?, ?>) obj;
    if (!Arrays.deepEquals(this.elements, other.elements)) {
      return false;
    }
    return true;
  }

  @Override public <V extends VectorWritable4FType> void getRow4F(
    final int row,
    final V out)
  {
    out.set4F(
      this.elements[row][0],
      this.elements[row][1],
      this.elements[row][2],
      this.elements[row][3]);
  }

  /**
   * @param row
   *          The row
   * @param col
   *          The column
   * @return The value at the given row and column
   */

  @Override public float getRowColumnF(
    final int row,
    final int col)
  {
    return this.elements[row][col];
  }

  @Override public int hashCode()
  {
    return Arrays.hashCode(this.elements);
  }

  /**
   * Write the current matrix into the given mutable matrix.
   *
   * @param m
   *          The mutable matrix
   */

  public void makeMatrixM4x4F(
    final PMatrixM4x4F<T0, T1> m)
  {
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m.set(row, col, this.elements[row][col]);
      }
    }
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < 4; ++row) {
      final String text =
        String.format(
          "[%+.6f %+.6f %+.6f %+.6f]\n",
          Float.valueOf(this.elements[row][0]),
          Float.valueOf(this.elements[row][1]),
          Float.valueOf(this.elements[row][2]),
          Float.valueOf(this.elements[row][3]));
      builder.append(text);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
