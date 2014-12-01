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

import java.util.Arrays;

import com.io7m.jequality.annotations.EqualityStructural;
import com.io7m.jnull.Nullable;

/**
 * An immutable 3x3 matrix type.
 */

@EqualityStructural public final class MatrixI3x3D implements
  MatrixReadable3x3DType
{
  private static final double[][] IDENTITY  = MatrixI3x3D.makeIdentity();
  private static MatrixI3x3D      IDENTITYM = MatrixI3x3D.makeIdentityM();

  /**
   * @return The identity matrix
   */

  public static MatrixI3x3D identity()
  {
    return MatrixI3x3D.IDENTITYM;
  }

  private static double[][] makeIdentity()
  {
    final double[][] m = new double[3][3];
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        if (row == col) {
          m[row][col] = 1.0f;
        } else {
          m[row][col] = 0.0f;
        }
      }
    }
    return m;
  }

  private static MatrixI3x3D makeIdentityM()
  {
    return new MatrixI3x3D(MatrixI3x3D.IDENTITY);
  }

  /**
   * Construct a new immutable 3x3 matrix from the given columns.
   *
   * @param column_0
   *          The first column
   * @param column_1
   *          The second column
   * @param column_2
   *          The third column
   *
   * @return A new 3x3 matrix
   */

  public static MatrixI3x3D newFromColumns(
    final VectorReadable3DType column_0,
    final VectorReadable3DType column_1,
    final VectorReadable3DType column_2)
  {
    final double[][] e = new double[3][3];

    e[0][0] = column_0.getXD();
    e[1][0] = column_0.getYD();
    e[2][0] = column_0.getZD();

    e[0][1] = column_1.getXD();
    e[1][1] = column_1.getYD();
    e[2][1] = column_1.getZD();

    e[0][2] = column_2.getXD();
    e[1][2] = column_2.getYD();
    e[2][2] = column_2.getZD();

    return new MatrixI3x3D(e);
  }

  /**
   * Construct a new immutable 3x3 matrix from the given readable 3x3 matrix.
   *
   * @param m
   *          The original matrix
   *
   * @return A new 3x3 matrix
   */

  public static MatrixI3x3D newFromReadable(
    final MatrixReadable3x3DType m)
  {
    final double[][] e = new double[3][3];

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        e[row][col] = m.getRowColumnD(row, col);
      }
    }

    return new MatrixI3x3D(e);
  }

  private final double[][] elements;

  private MatrixI3x3D(
    final double[][] e)
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
    final MatrixI3x3D other = (MatrixI3x3D) obj;
    if (!Arrays.deepEquals(this.elements, other.elements)) {
      return false;
    }
    return true;
  }

  @Override public <V extends VectorWritable3DType> void getRow3D(
    final int row,
    final V out)
  {
    out.set3D(
      this.elements[row][0],
      this.elements[row][1],
      this.elements[row][2]);
  }

  /**
   * @param row
   *          The row
   * @param col
   *          The column
   * @return The value at the given row and column
   */

  @Override public double getRowColumnD(
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

  public void makeMatrixM3x3D(
    final MatrixM3x3D m)
  {
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        m.set(row, col, this.elements[row][col]);
      }
    }
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < 3; ++row) {
      final String text =
        String.format(
          "[%+.15f %+.15f %+.15f]\n",
          Double.valueOf(this.elements[row][0]),
          Double.valueOf(this.elements[row][1]),
          Double.valueOf(this.elements[row][2]));
      builder.append(text);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}