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

import com.io7m.jtensors.Vector3IType;
import com.io7m.jtensors.bytebuffered.VectorByteBufferedM3I;
import com.io7m.jtensors.tests.VectorM3IBufferedContract;

import java.nio.ByteBuffer;

public final class VectorByteBufferedM3ITest
  extends VectorM3IBufferedContract<Vector3IType>
{
  @Override protected Vector3IType newVectorM3I(
    final int x,
    final int y,
    final int z)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final Vector3IType v =
      VectorByteBufferedM3I.newVectorFromByteBuffer(buf, 50L);
    v.set3I(x, y, z);
    return v;
  }

  @Override protected Vector3IType newVectorM3I()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final Vector3IType v =
      VectorByteBufferedM3I.newVectorFromByteBuffer(buf, 50L);
    v.set3I((int) 0L, (int) 0L, (int) 0L);
    return v;
  }

  @Override protected Vector3IType newVectorM3I(
    final Vector3IType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final Vector3IType vr =
      VectorByteBufferedM3I.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom3I(v);
    return vr;
  }

  @Override protected Vector3IType newVectorM3IAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return VectorByteBufferedM3I.newVectorFromByteBuffer(buf, offset);
  }
}