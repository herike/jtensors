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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.io7m.jaux.functional.Option;

/**
 * A 3x3 mutable matrix type with single precision elements.
 */

public final class MatrixM3x3F implements MatrixReadable3x3F
{
  private static final float[] identity_row_0 = { 1.0f, 0.0f, 0.0f };
  private static final float[] identity_row_1 = { 0.0f, 1.0f, 0.0f };
  private static final float[] identity_row_2 = { 0.0f, 0.0f, 1.0f };
  private static final float[] zero_row       = { 0.0f, 0.0f, 0.0f };

  /**
   * Elementwise add of matrices <code>m0</code> and <code>m1</code>.
   * 
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3F add(
    final MatrixReadable3x3F m0,
    final MatrixReadable3x3F m1,
    final MatrixM3x3F out)
  {
    final FloatBuffer m0_view = m0.getFloatBuffer();
    final FloatBuffer m1_view = m1.getFloatBuffer();

    for (int index = 0; index < MatrixM3x3F.VIEW_ELEMENTS; ++index) {
      out.view.put(index, m0_view.get(index) + m1_view.get(index));
    }
    return out;
  }

  /**
   * Elementwise add of matrices <code>m0</code> and <code>m1</code>,
   * returning the result in <code>m0</code>.
   * 
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @return <code>m0</code>
   */

  public static MatrixM3x3F addInPlace(
    final MatrixM3x3F m0,
    final MatrixM3x3F m1)
  {
    return MatrixM3x3F.add(m0, m1, m0);
  }

  /**
   * Add the values in row <code>row_b</code> to the values in row
   * <code>row_a</code> scaled by <code>r</code>, saving the resulting row in
   * row <code>row_c</code> of the matrix <code>out</code>.
   * 
   * This is one of the three "elementary" operations defined on matrices.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
   * 
   * @param m
   *          The input matrix.
   * @param row_a
   *          The row on the lefthand side of the addition.
   * @param row_b
   *          The row on the righthand side of the addition.
   * @param row_c
   *          The destination row.
   * @param r
   *          The scaling value.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3F addRowScaled(
    final MatrixReadable3x3F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r,
    final MatrixM3x3F out)
  {
    return MatrixM3x3F.addRowScaledUnsafe(
      m,
      MatrixM3x3F.rowCheck(row_a),
      MatrixM3x3F.rowCheck(row_b),
      MatrixM3x3F.rowCheck(row_c),
      r,
      out);
  }

  public static MatrixM3x3F addRowScaledInPlace(
    final MatrixM3x3F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r)
  {
    return MatrixM3x3F.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  private static MatrixM3x3F addRowScaledUnsafe(
    final MatrixReadable3x3F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r,
    final MatrixM3x3F out)
  {
    final VectorM3F va = new VectorM3F();
    final VectorM3F vb = new VectorM3F();
    MatrixM3x3F.rowUnsafe(m, row_a, va);
    MatrixM3x3F.rowUnsafe(m, row_b, vb);

    VectorM3F.addScaledInPlace(va, vb, r);
    MatrixM3x3F.setRowUnsafe(out, row_c, va);
    return out;
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= MatrixM3x3F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < " + MatrixM3x3F.VIEW_COLS);
    }
    return column;
  }

  /**
   * Copy the contents of the matrix <code>input</code> to the matrix
   * <code>output</code>, completely replacing all elements.
   * 
   * @param input
   *          The input vector.
   * @param output
   *          The output vector.
   * @return <code>output</code>
   */

  public static MatrixM3x3F copy(
    final MatrixReadable3x3F input,
    final MatrixM3x3F output)
  {
    final FloatBuffer source_view = input.getFloatBuffer();
    for (int index = 0; index < MatrixM3x3F.VIEW_ELEMENTS; ++index) {
      output.view.put(index, source_view.get(index));
    }
    return output;
  }

  /**
   * Calculate the determinant of the matrix <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   */

  public static float determinant(
    final MatrixReadable3x3F m)
  {
    final float r0c0 = m.getRowColumnF(0, 0);
    final float r0c1 = m.getRowColumnF(0, 1);
    final float r0c2 = m.getRowColumnF(0, 2);

    final float r1c0 = m.getRowColumnF(1, 0);
    final float r1c1 = m.getRowColumnF(1, 1);
    final float r1c2 = m.getRowColumnF(1, 2);

    final float r2c0 = m.getRowColumnF(2, 0);
    final float r2c1 = m.getRowColumnF(2, 1);
    final float r2c2 = m.getRowColumnF(2, 2);

    float sum = 0;

    sum += r0c0 * ((r1c1 * r2c2) - (r1c2 * r2c1));
    sum -= r0c1 * ((r1c0 * r2c2) - (r1c2 * r2c0));
    sum += r0c2 * ((r1c0 * r2c1) - (r1c1 * r2c0));

    return sum;
  }

  /**
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>out</code> .
   * This is one of the three "elementary" operations defined on matrices.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
   * 
   * @param m
   *          The input matrix.
   * @param row_a
   *          The first row.
   * @param row_b
   *          The second row.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3F exchangeRows(
    final MatrixReadable3x3F m,
    final int row_a,
    final int row_b,
    final MatrixM3x3F out)
  {
    return MatrixM3x3F.exchangeRowsUnsafe(
      m,
      MatrixM3x3F.rowCheck(row_a),
      MatrixM3x3F.rowCheck(row_b),
      out);
  }

  /**
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>m</code> . This
   * is one of the three "elementary" operations defined on matrices.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
   * 
   * @param m
   *          The input matrix.
   * @param row_a
   *          The first row.
   * @param row_b
   *          The second row.
   * @return <code>m</code>
   */

  public static MatrixM3x3F exchangeRowsInPlace(
    final MatrixM3x3F m,
    final int row_a,
    final int row_b)
  {
    return MatrixM3x3F.exchangeRows(m, row_a, row_b, m);
  }

  private static MatrixM3x3F exchangeRowsUnsafe(
    final MatrixReadable3x3F m,
    final int row_a,
    final int row_b,
    final MatrixM3x3F out)
  {
    final VectorM3F va = new VectorM3F();
    final VectorM3F vb = new VectorM3F();

    MatrixM3x3F.rowUnsafe(m, row_a, va);
    MatrixM3x3F.rowUnsafe(m, row_b, vb);

    MatrixM3x3F.setRowUnsafe(out, row_a, vb);
    MatrixM3x3F.setRowUnsafe(out, row_b, va);
    return out;
  }

  /**
   * Return a read-only view of the buffer that backs this matrix.
   * 
   * @param m
   *          The input matrix.
   */

  public static FloatBuffer floatBuffer(
    final MatrixM3x3F m)
  {
    final ByteBuffer b =
      m.data.asReadOnlyBuffer().order(ByteOrder.nativeOrder());
    return b.asFloatBuffer();
  }

  /**
   * Retrieve the value from the matrix <code>m</code> at row <code>row</code>
   * , column <code>column</code>.
   */

  public static float get(
    final MatrixReadable3x3F m,
    final int row,
    final int column)
  {
    final FloatBuffer source_view = m.getFloatBuffer();
    return source_view.get(MatrixM3x3F.indexChecked(row, column));
  }

  private final static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM3x3F.indexUnsafe(
      MatrixM3x3F.rowCheck(row),
      MatrixM3x3F.columnCheck(column));
  }

  /**
   * The main function that indexes into the buffer that backs the array. The
   * body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion.
   * 
   * (row * 3) + column, corresponds to row-major storage. (column * 3) + row,
   * corresponds to column-major (OpenGL) storage.
   */

  private final static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 3) + row;
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>.
   * 
   * @see com.io7m.jtensors.MatrixM3x3F#determinant(MatrixM3x3F)
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static Option<MatrixM3x3F> invert(
    final MatrixReadable3x3F m,
    final MatrixM3x3F out)
  {
    final float d = MatrixM3x3F.determinant(m);

    if (d == 0.0) {
      return new Option.None<MatrixM3x3F>();
    }

    final float d_inv = 1 / d;

    final float r0c0 = m.getRowColumnF(0, 0);
    final float r0c1 = m.getRowColumnF(0, 1);
    final float r0c2 = m.getRowColumnF(0, 2);

    final float r1c0 = m.getRowColumnF(1, 0);
    final float r1c1 = m.getRowColumnF(1, 1);
    final float r1c2 = m.getRowColumnF(1, 2);

    final float r2c0 = m.getRowColumnF(2, 0);
    final float r2c1 = m.getRowColumnF(2, 1);
    final float r2c2 = m.getRowColumnF(2, 2);

    MatrixM3x3F.set(out, 0, 0, (r1c1 * r2c2) - (r1c2 * r2c1));
    MatrixM3x3F.set(out, 0, 1, (r0c2 * r2c1) - (r0c1 * r2c2));
    MatrixM3x3F.set(out, 0, 2, (r0c1 * r1c2) - (r0c2 * r1c1));

    MatrixM3x3F.set(out, 1, 0, (r1c2 * r2c0) - (r1c0 * r2c2));
    MatrixM3x3F.set(out, 1, 1, (r0c0 * r2c2) - (r0c2 * r2c0));
    MatrixM3x3F.set(out, 1, 2, (r0c2 * r1c0) - (r0c0 * r1c2));

    MatrixM3x3F.set(out, 2, 0, (r1c0 * r2c1) - (r1c1 * r2c0));
    MatrixM3x3F.set(out, 2, 1, (r0c1 * r2c0) - (r0c0 * r2c1));
    MatrixM3x3F.set(out, 2, 2, (r0c0 * r1c1) - (r0c1 * r1c0));

    MatrixM3x3F.scaleInPlace(out, d_inv);
    return new Option.Some<MatrixM3x3F>(out);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>m</code>. The function returns <code>Some(m)</code> iff
   * it was possible to invert the matrix, and <code>None</code> otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * <code>0</code>.
   * 
   * @see com.io7m.jtensors.MatrixM3x3F#determinant(MatrixM3x3F)
   * @param m
   *          The input matrix.
   */

  public static Option<MatrixM3x3F> invertInPlace(
    final MatrixM3x3F m)
  {
    return MatrixM3x3F.invert(m, m);
  }

  /**
   * Create a translation matrix that represents a translation by the vector
   * <code>v</code>, writing the resulting matrix to <code>out</code>.
   * 
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3F makeTranslation2F(
    final VectorReadable2F v,
    final MatrixM3x3F out)
  {
    out.setUnsafe(0, 2, v.getXF());
    out.setUnsafe(1, 2, v.getYF());
    return out;
  }

  /**
   * Create a translation matrix that represents a translation by the vector
   * <code>v</code>, writing the resulting matrix to <code>out</code>.
   * 
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3F makeTranslation2I(
    final VectorReadable2I v,
    final MatrixM3x3F out)
  {
    out.setUnsafe(0, 2, v.getXI());
    out.setUnsafe(1, 2, v.getYI());
    return out;
  }

  /**
   * Multiply the matrix <code>m0</code> with the matrix <code>m1</code>,
   * writing the result to <code>out</code>.
   * 
   * @param m0
   *          The left input vector.
   * @param m1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return <code>out</code>
   */

  public static MatrixM3x3F multiply(
    final MatrixReadable3x3F m0,
    final MatrixReadable3x3F m1,
    final MatrixM3x3F out)
  {
    float r0c0 = 0;
    r0c0 += m0.getRowColumnF(0, 0) * m1.getRowColumnF(0, 0);
    r0c0 += m0.getRowColumnF(0, 1) * m1.getRowColumnF(1, 0);
    r0c0 += m0.getRowColumnF(0, 2) * m1.getRowColumnF(2, 0);

    float r1c0 = 0;
    r1c0 += m0.getRowColumnF(1, 0) * m1.getRowColumnF(0, 0);
    r1c0 += m0.getRowColumnF(1, 1) * m1.getRowColumnF(1, 0);
    r1c0 += m0.getRowColumnF(1, 2) * m1.getRowColumnF(2, 0);

    float r2c0 = 0;
    r2c0 += m0.getRowColumnF(2, 0) * m1.getRowColumnF(0, 0);
    r2c0 += m0.getRowColumnF(2, 1) * m1.getRowColumnF(1, 0);
    r2c0 += m0.getRowColumnF(2, 2) * m1.getRowColumnF(2, 0);

    float r0c1 = 0;
    r0c1 += m0.getRowColumnF(0, 0) * m1.getRowColumnF(0, 1);
    r0c1 += m0.getRowColumnF(0, 1) * m1.getRowColumnF(1, 1);
    r0c1 += m0.getRowColumnF(0, 2) * m1.getRowColumnF(2, 1);

    float r1c1 = 0;
    r1c1 += m0.getRowColumnF(1, 0) * m1.getRowColumnF(0, 1);
    r1c1 += m0.getRowColumnF(1, 1) * m1.getRowColumnF(1, 1);
    r1c1 += m0.getRowColumnF(1, 2) * m1.getRowColumnF(2, 1);

    float r2c1 = 0;
    r2c1 += m0.getRowColumnF(2, 0) * m1.getRowColumnF(0, 1);
    r2c1 += m0.getRowColumnF(2, 1) * m1.getRowColumnF(1, 1);
    r2c1 += m0.getRowColumnF(2, 2) * m1.getRowColumnF(2, 1);

    float r0c2 = 0;
    r0c2 += m0.getRowColumnF(0, 0) * m1.getRowColumnF(0, 2);
    r0c2 += m0.getRowColumnF(0, 1) * m1.getRowColumnF(1, 2);
    r0c2 += m0.getRowColumnF(0, 2) * m1.getRowColumnF(2, 2);

    float r1c2 = 0;
    r1c2 += m0.getRowColumnF(1, 0) * m1.getRowColumnF(0, 2);
    r1c2 += m0.getRowColumnF(1, 1) * m1.getRowColumnF(1, 2);
    r1c2 += m0.getRowColumnF(1, 2) * m1.getRowColumnF(2, 2);

    float r2c2 = 0;
    r2c2 += m0.getRowColumnF(2, 0) * m1.getRowColumnF(0, 2);
    r2c2 += m0.getRowColumnF(2, 1) * m1.getRowColumnF(1, 2);
    r2c2 += m0.getRowColumnF(2, 2) * m1.getRowColumnF(2, 2);

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(0, 2, r0c2);

    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(1, 1, r1c1);
    out.setUnsafe(1, 2, r1c2);

    out.setUnsafe(2, 0, r2c0);
    out.setUnsafe(2, 1, r2c1);
    out.setUnsafe(2, 2, r2c2);

    return out;
  }

  /**
   * Multiply the matrix <code>m</code> with the vector <code>v</code>,
   * writing the resulting vector to <code>out</code>.
   * 
   * @param m
   *          The input matrix.
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @return <code>out</code>
   */

  public static VectorM3F multiplyVector3F(
    final MatrixReadable3x3F m,
    final VectorReadable3F v,
    final VectorM3F out)
  {
    final VectorM3F row = new VectorM3F();
    final VectorM3F vi = new VectorM3F(v);

    MatrixM3x3F.rowUnsafe(m, 0, row);
    out.x = VectorM3F.dotProduct(row, vi);
    MatrixM3x3F.rowUnsafe(m, 1, row);
    out.y = VectorM3F.dotProduct(row, vi);
    MatrixM3x3F.rowUnsafe(m, 2, row);
    out.z = VectorM3F.dotProduct(row, vi);

    return out;
  }

  /**
   * Multiply the matrix <code>m0</code> with the matrix <code>m1</code>,
   * writing the result to <code>m0</code>.
   * 
   * @param m0
   *          The left input vector.
   * @param m1
   *          The right input vector.
   * @return <code>out</code>
   */

  public static MatrixM3x3F multiplyInPlace(
    final MatrixM3x3F m0,
    final MatrixReadable3x3F m1)
  {
    return MatrixM3x3F.multiply(m0, m1, m0);
  }

  /**
   * Return row <code>row</code> of the matrix <code>m</code> in the vector
   * <code>out</code>.
   */

  public static VectorM3F row(
    final MatrixReadable3x3F m,
    final int row,
    final VectorM3F out)
  {
    return MatrixM3x3F.rowUnsafe(m, MatrixM3x3F.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= MatrixM3x3F.VIEW_ROWS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + MatrixM3x3F.VIEW_ROWS);
    }
    return row;
  }

  public static VectorM3F rowUnsafe(
    final MatrixReadable3x3F m,
    final int row,
    final VectorM3F out)
  {
    out.x = m.getRowColumnF(row, 0);
    out.y = m.getRowColumnF(row, 1);
    out.z = m.getRowColumnF(row, 2);
    return out;
  }

  /**
   * Scale all elements of the matrix <code>m</code> by the scaling value
   * <code>r</code>, saving the result in <code>out</code>.
   * 
   * @param m
   *          The input matrix.
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   */

  public static MatrixM3x3F scale(
    final MatrixReadable3x3F m,
    final float r,
    final MatrixM3x3F out)
  {
    final FloatBuffer source_view = m.getFloatBuffer();
    for (int index = 0; index < MatrixM3x3F.VIEW_ELEMENTS; ++index) {
      out.view.put(index, source_view.get(index) * r);
    }
    return out;
  }

  /**
   * Scale all elements of the matrix <code>m</code> by the scaling value
   * <code>r</code>, saving the result in <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   */

  public static MatrixM3x3F scaleInPlace(
    final MatrixM3x3F m,
    final float r)
  {
    return MatrixM3x3F.scale(m, r, m);
  }

  /**
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>out</code>.
   * 
   * This is one of the three "elementary" operations defined on matrices.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
   * 
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row (0 <= row < 4).
   * @param r
   *          The scaling value.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3F scaleRow(
    final MatrixReadable3x3F m,
    final int row,
    final float r,
    final MatrixM3x3F out)
  {
    return MatrixM3x3F.scaleRowUnsafe(m, MatrixM3x3F.rowCheck(row), r, out);
  }

  /**
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>m</code>.
   * 
   * This is one of the three "elementary" operations defined on matrices.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
   * 
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row (0 <= row < 4).
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   */

  public static MatrixM3x3F scaleRowInPlace(
    final MatrixM3x3F m,
    final int row,
    final float r)
  {
    return MatrixM3x3F.scaleRowUnsafe(m, MatrixM3x3F.rowCheck(row), r, m);
  }

  private static MatrixM3x3F scaleRowUnsafe(
    final MatrixReadable3x3F m,
    final int row,
    final float r,
    final MatrixM3x3F out)
  {
    final VectorM3F v = new VectorM3F();

    MatrixM3x3F.rowUnsafe(m, row, v);
    VectorM3F.scaleInPlace(v, r);

    MatrixM3x3F.setRowUnsafe(out, row, v);
    return out;
  }

  /**
   * Set the value in the matrix <code>m</code> at row <code>row</code>,
   * column <code>column</code> to <code>value</code>.
   */

  public static MatrixM3x3F set(
    final MatrixM3x3F m,
    final int row,
    final int column,
    final float value)
  {
    m.view.put(MatrixM3x3F.indexChecked(row, column), value);
    return m;
  }

  /**
   * Set the given matrix <code>m</code> to the identity matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM3x3F setIdentity(
    final MatrixM3x3F m)
  {
    m.view.clear();
    m.view.put(MatrixM3x3F.identity_row_0);
    m.view.put(MatrixM3x3F.identity_row_1);
    m.view.put(MatrixM3x3F.identity_row_2);
    return m;
  }

  private static void setRowUnsafe(
    final MatrixM3x3F m,
    final int row,
    final VectorReadable3F v)
  {
    m.setUnsafe(row, 0, v.getXF());
    m.setUnsafe(row, 1, v.getYF());
    m.setUnsafe(row, 2, v.getZF());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM3x3F setZero(
    final MatrixM3x3F m)
  {
    m.view.clear();
    m.view.put(MatrixM3x3F.zero_row);
    m.view.put(MatrixM3x3F.zero_row);
    m.view.put(MatrixM3x3F.zero_row);
    return m;
  }

  /**
   * Translate the matrix <code>m</code> by the vector <code>v</code>, storing
   * the resulting matrix in <code>out</code>.
   * 
   * @param m
   *          The input matrix.
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3F translateByVector2F(
    final MatrixReadable3x3F m,
    final VectorReadable2F v,
    final MatrixM3x3F out)
  {
    final float vx = v.getXF();
    final float vy = v.getYF();

    final float c2r0 =
      (m.getRowColumnF(0, 0) * vx) + (m.getRowColumnF(0, 1) * vy);
    final float c2r1 =
      (m.getRowColumnF(1, 0) * vx) + (m.getRowColumnF(1, 1) * vy);
    final float c2r2 =
      (m.getRowColumnF(2, 0) * vx) + (m.getRowColumnF(2, 1) * vy);

    out.setUnsafe(0, 2, out.getUnsafe(0, 2) + c2r0);
    out.setUnsafe(1, 2, out.getUnsafe(1, 2) + c2r1);
    out.setUnsafe(2, 2, out.getUnsafe(2, 2) + c2r2);

    return out;
  }

  /**
   * Translate the matrix <code>m</code> by the vector <code>v</code>, storing
   * the resulting matrix in <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   * @param v
   *          The translation vector.
   * @return <code>m</code>
   */

  public static MatrixM3x3F translateByVector2FInPlace(
    final MatrixM3x3F m,
    final VectorReadable2F v)
  {
    return MatrixM3x3F.translateByVector2F(m, v, m);
  }

  /**
   * Translate the matrix <code>m</code> by the vector <code>v</code>, storing
   * the resulting matrix in <code>out</code>.
   * 
   * @param m
   *          The input matrix.
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3F translateByVector2I(
    final MatrixReadable3x3F m,
    final VectorReadable2I v,
    final MatrixM3x3F out)
  {
    final float vx = v.getXI();
    final float vy = v.getYI();

    final float c2r0 =
      (m.getRowColumnF(0, 0) * vx) + (m.getRowColumnF(0, 1) * vy);
    final float c2r1 =
      (m.getRowColumnF(1, 0) * vx) + (m.getRowColumnF(1, 1) * vy);
    final float c2r2 =
      (m.getRowColumnF(2, 0) * vx) + (m.getRowColumnF(2, 1) * vy);

    out.setUnsafe(0, 2, out.getUnsafe(0, 2) + c2r0);
    out.setUnsafe(1, 2, out.getUnsafe(1, 2) + c2r1);
    out.setUnsafe(2, 2, out.getUnsafe(2, 2) + c2r2);

    return out;
  }

  /**
   * Translate the matrix <code>m</code> by the vector <code>v</code>, storing
   * the resulting matrix in <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   * @param v
   *          The translation vector.
   * @return <code>m</code>
   */

  public static MatrixM3x3F translateByVector2IInPlace(
    final MatrixM3x3F m,
    final VectorReadable2I v)
  {
    return MatrixM3x3F.translateByVector2I(m, v, m);
  }

  /**
   * Transpose the given matrix <code>m</code>, writing the resulting matrix
   * to <code>out</code>.
   * 
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3F transpose(
    final MatrixReadable3x3F m,
    final MatrixM3x3F out)
  {
    final FloatBuffer source_view = m.getFloatBuffer();
    for (int index = 0; index < MatrixM3x3F.VIEW_ELEMENTS; ++index) {
      out.view.put(index, source_view.get(index));
    }
    return MatrixM3x3F.transposeInPlace(out);
  }

  /**
   * Transpose the given matrix <code>m</code>, writing the resulting matrix
   * to <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public static MatrixM3x3F transposeInPlace(
    final MatrixM3x3F m)
  {
    for (int row = 0; row < (MatrixM3x3F.VIEW_ROWS - 1); row++) {
      for (int column = row + 1; column < MatrixM3x3F.VIEW_COLS; column++) {
        final float x = m.view.get((row * MatrixM3x3F.VIEW_ROWS) + column);
        m.view.put(
          (row * MatrixM3x3F.VIEW_ROWS) + column,
          m.view.get(row + (MatrixM3x3F.VIEW_COLS * column)));
        m.view.put(row + (MatrixM3x3F.VIEW_COLS * column), x);
      }
    }
    return m;
  }

  private final ByteBuffer  data;
  private final FloatBuffer view;
  private static final int  VIEW_ELEMENT_SIZE;
  private static final int  VIEW_ELEMENTS;
  private static final int  VIEW_BYTES;
  private static final int  VIEW_COLS;
  private static final int  VIEW_ROWS;

  static {
    VIEW_ROWS = 3;
    VIEW_COLS = 3;
    VIEW_ELEMENT_SIZE = 4;
    VIEW_ELEMENTS = MatrixM3x3F.VIEW_ROWS * MatrixM3x3F.VIEW_COLS;
    VIEW_BYTES = MatrixM3x3F.VIEW_ELEMENTS * MatrixM3x3F.VIEW_ELEMENT_SIZE;
  }

  public MatrixM3x3F()
  {
    this.data =
      ByteBuffer.allocateDirect(MatrixM3x3F.VIEW_BYTES).order(
        ByteOrder.nativeOrder());
    this.view = this.data.asFloatBuffer();
    MatrixM3x3F.setIdentity(this);
  }

  public MatrixM3x3F(
    final MatrixReadable3x3F source)
  {
    this.data =
      ByteBuffer.allocateDirect(MatrixM3x3F.VIEW_BYTES).order(
        ByteOrder.nativeOrder());
    this.view = this.data.asFloatBuffer();

    final FloatBuffer source_view = source.getFloatBuffer();
    for (int index = 0; index < MatrixM3x3F.VIEW_ELEMENTS; ++index) {
      this.view.put(index, source_view.get(index));
    }
  }

  public float get(
    final int row,
    final int column)
  {
    return MatrixM3x3F.get(this, row, column);
  }

  @Override public FloatBuffer getFloatBuffer()
  {
    final ByteBuffer b =
      this.data.asReadOnlyBuffer().order(ByteOrder.nativeOrder());
    return b.asFloatBuffer();
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    return MatrixM3x3F.get(this, row, column);
  }

  private float getUnsafe(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM3x3F.indexUnsafe(row, column));
  }

  public MatrixM3x3F set(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM3x3F.indexChecked(row, column), value);
    return this;
  }

  private MatrixM3x3F setUnsafe(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM3x3F.indexUnsafe(row, column), value);
    return this;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < MatrixM3x3F.VIEW_ROWS; ++row) {
      builder.append("[");
      for (int column = 0; column < MatrixM3x3F.VIEW_COLS; ++column) {
        builder.append(MatrixM3x3F.get(this, row, column));
        if (column < (MatrixM3x3F.VIEW_COLS - 1)) {
          builder.append(" ");
        }
      }
      builder.append("]\n");
    }
    return builder.toString();
  }
}
