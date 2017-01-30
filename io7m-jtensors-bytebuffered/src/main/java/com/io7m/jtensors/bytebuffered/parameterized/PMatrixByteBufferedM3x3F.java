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

package com.io7m.jtensors.bytebuffered.parameterized;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorWritable2FType;
import com.io7m.jtensors.VectorWritable3FType;
import com.io7m.jtensors.bytebuffered.ByteBufferRanges;
import com.io7m.jtensors.bytebuffered.ByteBuffered;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>A 3x3 matrix type with {@code float} elements, packed into a {@link
 * ByteBuffer}.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 */

public final class PMatrixByteBufferedM3x3F<T0, T1> extends ByteBuffered
  implements PMatrixByteBuffered3x3FType<T0, T1>
{
  private final ByteBuffer buffer;

  private PMatrixByteBufferedM3x3F(
    final ByteBuffer in_buffer,
    final AtomicLong in_base,
    final int in_offset)
  {
    super(in_base, in_offset);
    this.buffer = NullCheck.notNull(in_buffer);
  }

  /**
   * <p>Return a new matrix that is backed by whatever data is at byte offset
   * {@code byte_offset} in the byte buffer {@code}.</p>
   *
   * <p>No initialization of the data is performed.</p>
   *
   * @param <T0>        A phantom type parameter
   * @param <T1>        A phantom type parameter
   * @param b           The byte buffer
   * @param byte_offset A byte offset
   *
   * @return A new buffered matrix
   */

  public static <T0, T1> PMatrixByteBuffered3x3FType<T0, T1>
  newMatrixFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new PMatrixByteBufferedM3x3F<T0, T1>(
      b, new AtomicLong(byte_offset), 0);
  }

  /**
   * <p>Return a new matrix that is backed by the given byte buffer {@code b}
   * </p>
   *
   * <p>The data for the instance will be taken from the data at the current
   * value of {@code base.get() + offset}, each time a field is requested or
   * set.</p>
   *
   * <p>No initialization of the data is performed.</p>
   *
   * @param <T0>   A phantom type parameter
   * @param <T1>   A phantom type parameter
   * @param b      The byte buffer
   * @param base   The base address
   * @param offset A constant offset
   *
   * @return A new buffered matrix
   */

  public static <T0, T1> PMatrixByteBuffered3x3FType<T0, T1>
  newMatrixFromByteBufferAndBase(
    final ByteBuffer b,
    final AtomicLong base,
    final int offset)
  {
    return new PMatrixByteBufferedM3x3F<T0, T1>(b, base, offset);
  }

  private static int checkColumn(
    final int column)
  {
    if ((column < 0) || (column >= 3)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < 3");
    }
    return column;
  }

  /**
   * <p> The main function that indexes into the buffer that backs the array.
   * The body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion. </p>
   *
   * <p> (row * 3) + column, corresponds to row-major storage. (column * 3) +
   * row, corresponds to column-major (OpenGL) storage. </p>
   */

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 3) + row;
  }

  private static int checkRow(
    final int row)
  {
    if ((row < 0) || (row >= 3)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < 3");
    }
    return row;
  }

  private static int getByteOffsetForIndex(
    final long base,
    final int index)
  {
    final long b = CheckedMath.add(base, (long) (index * 4));
    return (int) ByteBufferRanges.checkByteOffset(b);
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    MatrixM3x3F.showElements(this, builder);
    return builder.toString();
  }

  private void setAtOffsetAndRowColumn(
    final long o,
    final int row,
    final int col,
    final float x)
  {
    final int i = PMatrixByteBufferedM3x3F.getByteOffsetForIndex(
      o, PMatrixByteBufferedM3x3F.indexUnsafe(row, col));
    this.buffer.putFloat(i, x);
  }

  private float getAtOffsetAndRowColumn(
    final long o,
    final int row,
    final int col)
  {
    return this.buffer.getFloat(
      PMatrixByteBufferedM3x3F.getByteOffsetForIndex(
        o, PMatrixByteBufferedM3x3F.indexUnsafe(row, col)));
  }

  @Override public int hashCode()
  {
    return MatrixM3x3F.hashElements(this);
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

    final PMatrixByteBufferedM3x3F<?, ?> other =
      (PMatrixByteBufferedM3x3F<?, ?>) obj;
    return MatrixM3x3F.compareElements(this, other);
  }

  @Override public <V extends VectorWritable3FType> void getRow3F(
    final int row,
    final V out)
  {
    this.getRow3FUnsafe(PMatrixByteBufferedM3x3F.checkRow(row), out);
  }

  @Override public <V extends VectorWritable3FType> void getRow3FUnsafe(
    final int row,
    final V out)
  {
    final long o = super.getIndex();
    out.set3F(
      this.getAtOffsetAndRowColumn(o, row, 0),
      this.getAtOffsetAndRowColumn(o, row, 1),
      this.getAtOffsetAndRowColumn(o, row, 2));
  }

  @Override public float getR0C2F()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 0, 2);
  }

  @Override public void setR0C2F(final float x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 0, 2, x);
  }

  @Override public float getR1C2F()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 1, 2);
  }

  @Override public void setR1C2F(final float x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 1, 2, x);
  }

  @Override public float getR2C0F()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 2, 0);
  }

  @Override public void setR2C0F(final float x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 2, 0, x);
  }

  @Override public float getR2C1F()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 2, 1);
  }

  @Override public void setR2C1F(final float x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 2, 1, x);
  }

  @Override public float getR2C2F()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 2, 2);
  }

  @Override public void setR2C2F(final float x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 2, 2, x);
  }

  @Override public <V extends VectorWritable2FType> void getRow2F(
    final int row,
    final V out)
  {
    this.getRow2FUnsafe(PMatrixByteBufferedM3x3F.checkRow(row), out);
  }

  @Override public <V extends VectorWritable2FType> void getRow2FUnsafe(
    final int row,
    final V out)
  {
    final long o = super.getIndex();
    out.set2F(
      this.getAtOffsetAndRowColumn(o, row, 0),
      this.getAtOffsetAndRowColumn(o, row, 1));
  }

  @Override public float getR0C0F()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 0, 0);
  }

  @Override public void setR0C0F(final float x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 0, 0, x);
  }

  @Override public float getR1C0F()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 1, 0);
  }

  @Override public void setR1C0F(final float x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 1, 0, x);
  }

  @Override public float getR0C1F()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 0, 1);
  }

  @Override public void setR0C1F(final float x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 0, 1, x);
  }

  @Override public float getR1C1F()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 1, 1);
  }

  @Override public void setR1C1F(final float x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 1, 1, x);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    PMatrixByteBufferedM3x3F.checkRow(row);
    PMatrixByteBufferedM3x3F.checkColumn(column);
    return this.getAtOffsetAndRowColumn(super.getIndex(), row, column);
  }

  @Override public void setRowWith3F(
    final int row,
    final VectorReadable3FType v)
  {
    PMatrixByteBufferedM3x3F.checkRow(row);
    this.setRowWith3FUnsafe(row, v);
  }

  @Override public void setRowWith3FUnsafe(
    final int row,
    final VectorReadable3FType v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndRowColumn(o, row, 0, v.getXF());
    this.setAtOffsetAndRowColumn(o, row, 1, v.getYF());
    this.setAtOffsetAndRowColumn(o, row, 2, v.getZF());
  }

  @Override public void setRowWith2F(
    final int row,
    final VectorReadable2FType v)
  {
    PMatrixByteBufferedM3x3F.checkRow(row);
    this.setRowWith2FUnsafe(row, v);
  }

  @Override public void setRowWith2FUnsafe(
    final int row,
    final VectorReadable2FType v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndRowColumn(o, row, 0, v.getXF());
    this.setAtOffsetAndRowColumn(o, row, 1, v.getYF());
  }

  @Override public void setRowColumnF(
    final int row,
    final int column,
    final float value)
  {
    PMatrixByteBufferedM3x3F.checkRow(row);
    PMatrixByteBufferedM3x3F.checkColumn(column);
    this.setAtOffsetAndRowColumn(super.getIndex(), row, column, value);
  }
}