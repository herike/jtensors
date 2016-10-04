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

package com.io7m.jtensors.bytebuffered;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorReadable4FType;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>A quaternion type with {@code float} elements, packed into a {@link
 * ByteBuffer}.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 */

public final class QuaternionByteBufferedM4F extends ByteBuffered
  implements QuaternionByteBuffered4FType
{
  private final ByteBuffer buffer;

  private QuaternionByteBufferedM4F(
    final ByteBuffer in_buffer,
    final AtomicLong in_base,
    final int in_offset)
  {
    super(in_base, in_offset);
    this.buffer = NullCheck.notNull(in_buffer);
  }

  /**
   * <p>Return a new vector that is backed by whatever data is at byte offset
   * {@code byte_offset} in the byte buffer {@code}.</p>
   *
   * <p>No initialization of the data is performed.</p>
   *
   * @param b           The byte buffer
   * @param byte_offset A byte offset
   *
   * @return A new buffered vector
   */

  public static QuaternionByteBuffered4FType newQuaternionFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new QuaternionByteBufferedM4F(b, new AtomicLong(byte_offset), 0);
  }

  /**
   * <p>Return a new quaternion that is backed by the given byte buffer {@code
   * b} </p>
   *
   * <p>The data for the instance will be taken from the data at the current
   * value of {@code base.get() + offset}, each time a field is requested or
   * set.</p>
   *
   * <p>No initialization of the data is performed.</p>
   *
   * @param b      The byte buffer
   * @param base   The base address
   * @param offset A constant offset
   *
   * @return A new buffered quaternion
   */

  public static QuaternionByteBuffered4FType newQuaternionFromByteBufferAndBase(
    final ByteBuffer b,
    final AtomicLong base,
    final int offset)
  {
    return new QuaternionByteBufferedM4F(b, base, offset);
  }

  private static int getByteOffsetForIndex(
    final long base,
    final int index)
  {
    final long b = CheckedMath.add(base, (long) (index * 4));
    return (int) ByteBufferRanges.checkByteOffset(b);
  }

  @Override public float getWF()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 3);
  }

  @Override public void setWF(final float w)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 3, w);
  }

  @Override public float getZF()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 2);
  }

  @Override public void setZF(final float z)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 2, z);
  }

  @Override public float getXF()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 0);
  }

  @Override public void setXF(final float x)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 0, x);
  }

  private void setAtOffsetAndIndex(
    final long o,
    final int i,
    final float x)
  {
    this.buffer.putFloat(
      QuaternionByteBufferedM4F.getByteOffsetForIndex(o, i), x);
  }

  private float getAtOffsetAndIndex(
    final long o,
    final int i)
  {
    return this.buffer.getFloat(
      QuaternionByteBufferedM4F.getByteOffsetForIndex(o, i));
  }

  @Override public float getYF()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 1);
  }

  @Override public void setYF(final float y)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 1, y);
  }

  @Override public void copyFrom4F(final VectorReadable4FType in_v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, in_v.getXF());
    this.setAtOffsetAndIndex(o, 1, in_v.getYF());
    this.setAtOffsetAndIndex(o, 2, in_v.getZF());
    this.setAtOffsetAndIndex(o, 3, in_v.getWF());
  }

  @Override public void set4F(
    final float x,
    final float y,
    final float z,
    final float w)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, x);
    this.setAtOffsetAndIndex(o, 1, y);
    this.setAtOffsetAndIndex(o, 2, z);
    this.setAtOffsetAndIndex(o, 3, w);
  }

  @Override public void copyFrom3F(final VectorReadable3FType in_v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, in_v.getXF());
    this.setAtOffsetAndIndex(o, 1, in_v.getYF());
    this.setAtOffsetAndIndex(o, 2, in_v.getZF());
  }

  @Override public void set3F(
    final float x,
    final float y,
    final float z)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, x);
    this.setAtOffsetAndIndex(o, 1, y);
    this.setAtOffsetAndIndex(o, 2, z);
  }

  @Override public void copyFrom2F(final VectorReadable2FType in_v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, in_v.getXF());
    this.setAtOffsetAndIndex(o, 1, in_v.getYF());
  }

  @Override public void set2F(
    final float x,
    final float y)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, x);
    this.setAtOffsetAndIndex(o, 1, y);
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Float.floatToIntBits(this.getWF());
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Float.floatToIntBits(this.getXF());
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Float.floatToIntBits(this.getYF());
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Float.floatToIntBits(this.getZF());
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(128);
    builder.append("[QuaternionByteBufferedM4F ");
    builder.append(this.getXF());
    builder.append(" ");
    builder.append(this.getYF());
    builder.append(" ");
    builder.append(this.getZF());
    builder.append(" ");
    builder.append(this.getWF());
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
    final QuaternionByteBufferedM4F other = (QuaternionByteBufferedM4F) obj;
    if (Float.floatToIntBits(this.getWF())
        != Float.floatToIntBits(other.getWF())) {
      return false;
    }
    if (Float.floatToIntBits(this.getXF())
        != Float.floatToIntBits(other.getXF())) {
      return false;
    }
    if (Float.floatToIntBits(this.getYF())
        != Float.floatToIntBits(other.getYF())) {
      return false;
    }
    return Float.floatToIntBits(this.getZF()) == Float.floatToIntBits(
      other.getZF());
  }
}
