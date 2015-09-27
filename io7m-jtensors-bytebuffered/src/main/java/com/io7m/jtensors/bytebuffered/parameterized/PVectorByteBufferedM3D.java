/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
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
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.parameterized.PVector3DType;
import com.io7m.jtensors.parameterized.PVectorReadable2DType;
import com.io7m.jtensors.parameterized.PVectorReadable3DType;

import java.nio.ByteBuffer;

/**
 * <p>A three-element vector type with {@code double} elements, packed into a
 * {@link ByteBuffer}.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 *
 * @param <T> A phantom type parameter
 */

public final class PVectorByteBufferedM3D<T> implements PVector3DType<T>
{
  private final ByteBuffer buffer;
  private final long       offset;

  private PVectorByteBufferedM3D(
    final ByteBuffer in_buffer,
    final long in_offset)
  {
    this.buffer = NullCheck.notNull(in_buffer);
    this.offset = in_offset;
  }

  /**
   * <p>Return a new vector that is backed by whatever data is at byte offset
   * {@code byte_offset} in the byte buffer {@code}.</p>
   *
   * <p>No initialization of the data is performed.</p>
   *
   * @param <T>         A phantom type parameter
   * @param b           The byte buffer
   * @param byte_offset A byte offset
   *
   * @return A new buffered vector
   */

  public static <T> PVector3DType<T> newVectorFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new PVectorByteBufferedM3D<T>(b, byte_offset);
  }

  private static int getByteOffsetForIndex(
    final long base,
    final int index)
  {
    final long b = CheckedMath.add(base, (long) (index * 8));
    if (b >= (long) Integer.MAX_VALUE) {
      throw new IndexOutOfBoundsException(Long.toString(b));
    }
    return (int) b;
  }

  @Override public double getZD()
  {
    return this.getAtOffsetAndIndex(this.offset, 2);
  }

  @Override public void setZD(final double z)
  {
    this.setAtOffsetAndIndex(this.offset, 2, z);
  }

  @Override public double getXD()
  {
    return this.getAtOffsetAndIndex(this.offset, 0);
  }

  @Override public void setXD(final double x)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
  }

  private void setAtOffsetAndIndex(
    final long o,
    final int i,
    final double x)
  {
    this.buffer.putDouble(
      PVectorByteBufferedM3D.getByteOffsetForIndex(o, i),
      x);
  }

  private double getAtOffsetAndIndex(
    final long o,
    final int i)
  {
    return this.buffer.getDouble(
      PVectorByteBufferedM3D.getByteOffsetForIndex(o, i));
  }

  @Override public double getYD()
  {
    return this.getAtOffsetAndIndex(this.offset, 1);
  }

  @Override public void setYD(final double y)
  {
    this.setAtOffsetAndIndex(this.offset, 1, y);
  }

  @Override public void copyFrom3D(final VectorReadable3DType in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXD());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYD());
    this.setAtOffsetAndIndex(this.offset, 2, in_v.getZD());
  }

  @Override public void set3D(
    final double x,
    final double y,
    final double z)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
    this.setAtOffsetAndIndex(this.offset, 1, y);
    this.setAtOffsetAndIndex(this.offset, 2, z);
  }

  @Override public void copyFrom2D(final VectorReadable2DType in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXD());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYD());
  }

  @Override public void set2D(
    final double x,
    final double y)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
    this.setAtOffsetAndIndex(this.offset, 1, y);
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(this.getXD());
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.getYD());
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.getZD());
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(128);
    builder.append("[PVectorByteBufferedM3D ");
    builder.append(this.getXD());
    builder.append(" ");
    builder.append(this.getYD());
    builder.append(" ");
    builder.append(this.getZD());
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
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
    final PVectorByteBufferedM3D<?> other = (PVectorByteBufferedM3D<?>) obj;
    if (Double.doubleToLongBits(this.getXD())
        != Double.doubleToLongBits(other.getXD())) {
      return false;
    }
    if (Double.doubleToLongBits(this.getYD())
        != Double.doubleToLongBits(other.getYD())) {
      return false;
    }
    return Double.doubleToLongBits(this.getZD()) == Double.doubleToLongBits(
      other.getZD());
  }

  @Override public void copyFromTyped3D(final PVectorReadable3DType<T> in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXD());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYD());
    this.setAtOffsetAndIndex(this.offset, 2, in_v.getZD());
  }

  @Override public void copyFromTyped2D(final PVectorReadable2DType<T> in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXD());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYD());
  }
}