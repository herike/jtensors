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

package com.io7m.jtensors.bytebuffered;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.Quaternion4FType;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorReadable4FType;

import java.nio.ByteBuffer;

/**
 * <p>A quaternion type with {@code float} elements, packed into a {@link
 * ByteBuffer}.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 */

public final class QuaternionByteBufferedM4F implements Quaternion4FType
{
  private final ByteBuffer buffer;
  private final long       offset;

  private QuaternionByteBufferedM4F(
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
   * @param b           The byte buffer
   * @param byte_offset A byte offset
   *
   * @return A new buffered vector
   */

  public static Quaternion4FType newQuaternionFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new QuaternionByteBufferedM4F(b, byte_offset);
  }

  private static int getByteOffsetForIndex(
    final long base,
    final int index)
  {
    final long b = CheckedMath.add(base, (long) (index * 4));
    if (b >= (long) Integer.MAX_VALUE) {
      throw new IndexOutOfBoundsException(Long.toString(b));
    }
    return (int) b;
  }

  @Override public float getWF()
  {
    return this.getAtOffsetAndIndex(this.offset, 3);
  }

  @Override public void setWF(final float w)
  {
    this.setAtOffsetAndIndex(this.offset, 3, w);
  }

  @Override public float getZF()
  {
    return this.getAtOffsetAndIndex(this.offset, 2);
  }

  @Override public void setZF(final float z)
  {
    this.setAtOffsetAndIndex(this.offset, 2, z);
  }

  @Override public float getXF()
  {
    return this.getAtOffsetAndIndex(this.offset, 0);
  }

  @Override public void setXF(final float x)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
  }

  private void setAtOffsetAndIndex(
    final long o,
    final int i,
    final float x)
  {
    this.buffer.putFloat(
      QuaternionByteBufferedM4F.getByteOffsetForIndex(o, i),
      x);
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
    return this.getAtOffsetAndIndex(this.offset, 1);
  }

  @Override public void setYF(final float y)
  {
    this.setAtOffsetAndIndex(this.offset, 1, y);
  }

  @Override public void copyFrom4F(final VectorReadable4FType in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXF());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYF());
    this.setAtOffsetAndIndex(this.offset, 2, in_v.getZF());
    this.setAtOffsetAndIndex(this.offset, 3, in_v.getWF());
  }

  @Override public void set4F(
    final float x,
    final float y,
    final float z,
    final float w)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
    this.setAtOffsetAndIndex(this.offset, 1, y);
    this.setAtOffsetAndIndex(this.offset, 2, z);
    this.setAtOffsetAndIndex(this.offset, 3, w);
  }

  @Override public void copyFrom3F(final VectorReadable3FType in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXF());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYF());
    this.setAtOffsetAndIndex(this.offset, 2, in_v.getZF());
  }

  @Override public void set3F(
    final float x,
    final float y,
    final float z)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
    this.setAtOffsetAndIndex(this.offset, 1, y);
    this.setAtOffsetAndIndex(this.offset, 2, z);
  }

  @Override public void copyFrom2F(final VectorReadable2FType in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXF());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYF());
  }

  @Override public void set2F(
    final float x,
    final float y)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
    this.setAtOffsetAndIndex(this.offset, 1, y);
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