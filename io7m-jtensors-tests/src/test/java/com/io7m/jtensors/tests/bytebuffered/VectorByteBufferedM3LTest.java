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

import com.io7m.jtensors.bytebuffered.VectorByteBuffered3LType;
import com.io7m.jtensors.bytebuffered.VectorByteBufferedM3L;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class VectorByteBufferedM3LTest
  extends VectorByteBufferedM3LContract<VectorByteBuffered3LType>
{
  @Override
  protected VectorByteBuffered3LType newVectorM3L(
    final long x,
    final long y,
    final long z)
  {
    final ByteBuffer buf = ByteBuffer.allocate(3 * 8);
    final VectorByteBuffered3LType v =
      VectorByteBufferedM3L.newVectorFromByteBuffer(buf, 0L);
    v.set3L(x, y, z);
    return v;
  }

  @Override
  protected VectorByteBuffered3LType newVectorM3L()
  {
    final ByteBuffer buf = ByteBuffer.allocate(3 * 8);
    final VectorByteBuffered3LType v =
      VectorByteBufferedM3L.newVectorFromByteBuffer(buf, 0L);
    v.set3L(0L, 0L, 0L);
    return v;
  }

  @Override
  protected VectorByteBuffered3LType newVectorM3L(
    final VectorByteBuffered3LType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(3 * 8);
    final VectorByteBuffered3LType vr =
      VectorByteBufferedM3L.newVectorFromByteBuffer(buf, 0L);
    vr.copyFrom3L(v);
    return vr;
  }

  @Override
  protected VectorByteBuffered3LType newVectorM3LAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return VectorByteBufferedM3L.newVectorFromByteBuffer(buf, offset);
  }

  @Override
  protected VectorByteBuffered3LType newVectorM3LWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return VectorByteBufferedM3L.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test
  public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(3 * 8);
    b.order(ByteOrder.BIG_ENDIAN);

    final VectorByteBuffered3LType v =
      VectorByteBufferedM3L.newVectorFromByteBuffer(b, 0L);
    v.set3L(
      0x1020304011223344L,
      0x5060708051525354L,
      0x90a0b0c091a1b1c1L);

    Assert.assertEquals(0x1020304011223344L, b.getLong(0));
    Assert.assertEquals(0x5060708051525354L, b.getLong(8));
    Assert.assertEquals(0x90a0b0c091a1b1c1L, b.getLong(16));
  }
}
