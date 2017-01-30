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

package com.io7m.jtensors.tests.bytebuffered;

import com.io7m.jtensors.bytebuffered.VectorByteBuffered2LType;
import com.io7m.jtensors.bytebuffered.VectorByteBufferedM2L;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class VectorByteBufferedM2LTest
  extends VectorByteBufferedM2LContract<VectorByteBuffered2LType>
{
  @Override protected VectorByteBuffered2LType newVectorM2L(
    final long x,
    final long y)
  {
    final ByteBuffer buf = ByteBuffer.allocate(2 * 8);
    final VectorByteBuffered2LType v =
      VectorByteBufferedM2L.newVectorFromByteBuffer(buf, 0L);
    v.set2L(x, y);
    return v;
  }

  @Override protected VectorByteBuffered2LType newVectorM2L()
  {
    final ByteBuffer buf = ByteBuffer.allocate(2 * 8);
    final VectorByteBuffered2LType v =
      VectorByteBufferedM2L.newVectorFromByteBuffer(buf, 0L);
    v.set2L(0L, 0L);
    return v;
  }

  @Override protected VectorByteBuffered2LType newVectorM2L(
    final VectorByteBuffered2LType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(2 * 8);
    final VectorByteBuffered2LType vr =
      VectorByteBufferedM2L.newVectorFromByteBuffer(buf, 0L);
    vr.copyFrom2L(v);
    return vr;
  }

  @Override protected VectorByteBuffered2LType newVectorM2LAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return VectorByteBufferedM2L.newVectorFromByteBuffer(buf, offset);
  }

  @Override protected VectorByteBuffered2LType newVectorM2LWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return VectorByteBufferedM2L.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(2 * 8);
    b.order(ByteOrder.BIG_ENDIAN);

    final VectorByteBuffered2LType v =
      VectorByteBufferedM2L.newVectorFromByteBuffer(b, 0L);
    v.set2L(
      0x1020304011223344L,
      0x5060708051525354L);

    Assert.assertEquals(0x1020304011223344L, b.getLong(0));
    Assert.assertEquals(0x5060708051525354L, b.getLong(8));
  }
}