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

package com.io7m.jtensors.tests.parameterized;

import com.io7m.jtensors.parameterized.PMatrixDirect3x3FType;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;

//@formatter:off
public abstract class PMatrixDirect3x3FContract<
  T0,
  T1,
  T2,
  T           extends PMatrixDirect3x3FType<T0, T1>,
  TMULTRIGHT  extends PMatrixDirect3x3FType<T0, T1>,
  TMULTLEFT   extends PMatrixDirect3x3FType<T1, T2>,
  TMULTRESULT extends PMatrixDirect3x3FType<T0, T2>,
  TINVERSE    extends PMatrixDirect3x3FType<T1, T0>>
  extends PMatrix3x3FContract<
  T0,
  T1,
  T2,
  T,
  TMULTRIGHT,
  TMULTLEFT,
  TMULTRESULT,
  TINVERSE>
{
  //@formatter:on

  protected abstract T newMatrix();

  @Test public final void testBufferEndianness()
  {
    final T m = this.newMatrix();
    final FloatBuffer b = m.getDirectFloatBuffer();

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @Test public final void testStorage()
  {
    final T m = this.newMatrix();

    m.setRowColumnF(0, 0, 0.0f);
    m.setRowColumnF(0, 1, 1.0f);
    m.setRowColumnF(0, 2, 2.0f);

    m.setRowColumnF(1, 0, 100.0f);
    m.setRowColumnF(1, 1, 101.0f);
    m.setRowColumnF(1, 2, 102.0f);

    m.setRowColumnF(2, 0, 200.0f);
    m.setRowColumnF(2, 1, 201.0f);
    m.setRowColumnF(2, 2, 202.0f);

    {
      final FloatBuffer b = m.getDirectFloatBuffer();

      Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
      Assert.assertEquals(0L, (long) b.position());

      Assert.assertEquals(0.0, (double) b.get(0), 0.0);
      Assert.assertEquals(100.0, (double) b.get(1), 0.0);
      Assert.assertEquals(200.0, (double) b.get(2), 0.0);

      Assert.assertEquals(1.0, (double) b.get(3), 0.0);
      Assert.assertEquals(101.0, (double) b.get(4), 0.0);
      Assert.assertEquals(201.0, (double) b.get(5), 0.0);

      Assert.assertEquals(2.0, (double) b.get(6), 0.0);
      Assert.assertEquals(102.0, (double) b.get(7), 0.0);
      Assert.assertEquals(202.0, (double) b.get(8), 0.0);
    }
  }
}