/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.tests.storage.bytebuffered;

import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating2Type;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating3Type;
import com.io7m.jtensors.storage.bytebuffered.VectorByteBufferedFloating3s64;
import com.io7m.jtensors.tests.core.TestDOps;
import com.io7m.jtensors.tests.storage.api.VectorStorage3Contract;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public final class VectorByteBufferedFloating3s64Test
  extends VectorStorage3Contract
{
  @Override
  protected void checkAlmostEqual(
    final double a,
    final double b)
  {
    TestDOps.checkAlmostEquals(a, b);
  }

  @Override
  protected VectorStorageFloating2Type createWith2(
    final double x,
    final double y)
  {
    return VectorByteBufferedFloating3s64.createHeap();
  }

  @Override
  protected VectorStorageFloating3Type createWith3(
    final double x,
    final double y,
    final double z)
  {
    return VectorByteBufferedFloating3s64.createHeap();
  }

  @Test
  public void testByteBufferIdentity()
  {
    final ByteBuffer b = ByteBuffer.allocate(1024);
    Assert.assertEquals(
      b,
      VectorByteBufferedFloating3s64.createWith(b).byteBuffer());
  }
}
