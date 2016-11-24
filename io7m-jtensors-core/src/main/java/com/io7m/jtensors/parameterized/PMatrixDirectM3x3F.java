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

import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.MatrixReadable3x3FType;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorWritable2FType;
import com.io7m.jtensors.VectorWritable3FType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * The default implementation of the {@link PMatrixDirect3x3FType} interface.
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 *
 * @since 7.0.0
 */

public final class PMatrixDirectM3x3F<T0, T1>
  implements PMatrixDirect3x3FType<T0, T1>
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 3;
    VIEW_COLS = 3;
    VIEW_ELEMENT_SIZE = 4;
    VIEW_ELEMENTS = PMatrixDirectM3x3F.VIEW_ROWS * PMatrixDirectM3x3F.VIEW_COLS;
    VIEW_BYTES =
      PMatrixDirectM3x3F.VIEW_ELEMENTS * PMatrixDirectM3x3F.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer  data;
  private final FloatBuffer view;

  private PMatrixDirectM3x3F(final @Nullable MatrixReadable3x3FType m)
  {
    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;

    final ByteBuffer b =
      ByteBuffer.allocateDirect(PMatrixDirectM3x3F.VIEW_BYTES);
    b.order(order);

    final FloatBuffer v = b.asFloatBuffer();

    this.data = b;
    this.view = v;
    this.view.clear();

    if (m == null) {
      MatrixM3x3F.setIdentity(this);
    } else {
      MatrixM3x3F.copy(m, this);
    }
  }

  /**
   * @param <T0> A phantom type parameter
   * @param <T1> A phantom type parameter
   *
   * @return A new identity matrix
   */

  public static <T0, T1> PMatrixDirect3x3FType<T0, T1> newMatrix()
  {
    return new PMatrixDirectM3x3F<T0, T1>(null);
  }

  /**
   * @param <T0> A phantom type parameter
   * @param <T1> A phantom type parameter
   * @param m    The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static <T0, T1> PMatrixDirect3x3FType<T0, T1> newMatrixFrom(
    final PMatrixReadable3x3FType<T0, T1> m)
  {
    NullCheck.notNull(m);
    return new PMatrixDirectM3x3F<T0, T1>(m);
  }

  /**
   * @param <T0> A phantom type parameter
   * @param <T1> A phantom type parameter
   * @param m    The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static <T0, T1> PMatrixDirect3x3FType<T0, T1> newMatrixFromUntyped(
    final MatrixReadable3x3FType m)
  {
    NullCheck.notNull(m);
    return new PMatrixDirectM3x3F<T0, T1>(m);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= PMatrixDirectM3x3F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < "
        + PMatrixDirectM3x3F.VIEW_COLS);
    }
    return column;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return PMatrixDirectM3x3F.indexUnsafe(
      PMatrixDirectM3x3F.rowCheck(row), PMatrixDirectM3x3F.columnCheck(column));
  }

  /**
   * <p> The main function that indexes into the buffer that backs the array.
   * The body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion. </p> <p>
   * (row * 4) + column, corresponds to row-major storage. (column * 4) + row,
   * corresponds to column-major (OpenGL) storage. </p>
   */

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * PMatrixDirectM3x3F.VIEW_COLS) + row;
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= PMatrixDirectM3x3F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + PMatrixDirectM3x3F.VIEW_COLS);
    }
    return row;
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

    final PMatrixDirectM3x3F<?, ?> other = (PMatrixDirectM3x3F<?, ?>) obj;
    return MatrixM3x3F.compareElements(this, other);
  }

  @Override public FloatBuffer getDirectFloatBuffer()
  {
    return this.view;
  }

  @Override public int hashCode()
  {
    return MatrixM3x3F.hashElements(this);
  }

  @Override public void setRowColumnF(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(PMatrixDirectM3x3F.indexChecked(row, column), value);
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    MatrixM3x3F.showElements(this, builder);
    return builder.toString();
  }

  @Override public <V extends VectorWritable3FType> void getRow3F(
    final int row,
    final V out)
  {
    PMatrixDirectM3x3F.rowCheck(row);
    this.getRow3FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable3FType> void getRow3FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.view.get(PMatrixDirectM3x3F.indexUnsafe(row, 0));
    final float y = this.view.get(PMatrixDirectM3x3F.indexUnsafe(row, 1));
    final float z = this.view.get(PMatrixDirectM3x3F.indexUnsafe(row, 2));
    out.set3F(x, y, z);
  }

  @Override public float getR0C2F()
  {
    return this.view.get(PMatrixDirectM3x3F.indexUnsafe(0, 2));
  }

  @Override public void setR0C2F(final float x)
  {
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(0, 2), x);
  }

  @Override public void setRowWith3F(
    final int row,
    final VectorReadable3FType v)
  {
    PMatrixDirectM3x3F.rowCheck(row);
    this.setRowWith3FUnsafe(row, v);
  }

  @Override public void setRowWith3FUnsafe(
    final int row,
    final VectorReadable3FType v)
  {
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(row, 0), v.getXF());
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(row, 1), v.getYF());
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(row, 2), v.getZF());
  }

  @Override public float getR1C2F()
  {
    return this.view.get(PMatrixDirectM3x3F.indexUnsafe(1, 2));
  }

  @Override public void setR1C2F(final float x)
  {
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(1, 2), x);
  }

  @Override public float getR2C0F()
  {
    return this.view.get(PMatrixDirectM3x3F.indexUnsafe(2, 0));
  }

  @Override public void setR2C0F(final float x)
  {
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(2, 0), x);
  }

  @Override public float getR2C1F()
  {
    return this.view.get(PMatrixDirectM3x3F.indexUnsafe(2, 1));
  }

  @Override public void setR2C1F(final float x)
  {
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(2, 1), x);
  }

  @Override public float getR2C2F()
  {
    return this.view.get(PMatrixDirectM3x3F.indexUnsafe(2, 2));
  }

  @Override public void setR2C2F(final float x)
  {
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(2, 2), x);
  }

  @Override public <V extends VectorWritable2FType> void getRow2F(
    final int row,
    final V out)
  {
    PMatrixDirectM3x3F.rowCheck(row);
    this.getRow2FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2FType> void getRow2FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.view.get(PMatrixDirectM3x3F.indexUnsafe(row, 0));
    final float y = this.view.get(PMatrixDirectM3x3F.indexUnsafe(row, 1));
    out.set2F(x, y);
  }

  @Override public float getR0C0F()
  {
    return this.view.get(PMatrixDirectM3x3F.indexUnsafe(0, 0));
  }

  @Override public void setR0C0F(final float x)
  {
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(0, 0), x);
  }

  @Override public void setRowWith2F(
    final int row,
    final VectorReadable2FType v)
  {
    PMatrixDirectM3x3F.rowCheck(row);
    this.setRowWith2FUnsafe(row, v);
  }

  @Override public void setRowWith2FUnsafe(
    final int row,
    final VectorReadable2FType v)
  {
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(row, 0), v.getXF());
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(row, 1), v.getYF());
  }

  @Override public float getR1C0F()
  {
    return this.view.get(PMatrixDirectM3x3F.indexUnsafe(1, 0));
  }

  @Override public void setR1C0F(final float x)
  {
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(1, 0), x);
  }

  @Override public float getR0C1F()
  {
    return this.view.get(PMatrixDirectM3x3F.indexUnsafe(0, 1));
  }

  @Override public void setR0C1F(final float x)
  {
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(0, 1), x);
  }

  @Override public float getR1C1F()
  {
    return this.view.get(PMatrixDirectM3x3F.indexUnsafe(1, 1));
  }

  @Override public void setR1C1F(final float x)
  {
    this.view.put(PMatrixDirectM3x3F.indexUnsafe(1, 1), x);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    return this.view.get(PMatrixDirectM3x3F.indexChecked(row, column));
  }
}
