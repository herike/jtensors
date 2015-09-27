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

package com.io7m.jtensors.tests.bytebuffered;

import com.io7m.jtensors.Vector2LType;
import com.io7m.jtensors.bytebuffered.VectorByteBufferedM2L;
import com.io7m.jtensors.tests.VectorM2LBufferedContract;

import java.nio.ByteBuffer;

public final class VectorByteBufferedM2LTest
  extends VectorM2LBufferedContract<Vector2LType>
{
  @Override protected Vector2LType newVectorM2L(
    final long x,
    final long y)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final Vector2LType v =
      VectorByteBufferedM2L.newVectorFromByteBuffer(buf, 50L);
    v.set2L(x, y);
    return v;
  }

  @Override protected Vector2LType newVectorM2L()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final Vector2LType v =
      VectorByteBufferedM2L.newVectorFromByteBuffer(buf, 50L);
    v.set2L(0L, 0L);
    return v;
  }

  @Override protected Vector2LType newVectorM2L(
    final Vector2LType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final Vector2LType vr =
      VectorByteBufferedM2L.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom2L(v);
    return vr;
  }

  @Override protected Vector2LType newVectorM2LAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return VectorByteBufferedM2L.newVectorFromByteBuffer(buf, offset);
  }
}