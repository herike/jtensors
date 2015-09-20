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

import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.MatrixReadable4x4FType;
import com.io7m.jtensors.parameterized.PMatrixM4x4F;
import com.io7m.jtensors.tests.MatrixDirect4x4FContract;
import org.junit.Assert;

public final class PMatrixM4x4FUntypedTest<T0, T1>
  extends MatrixDirect4x4FContract<PMatrixM4x4F<T0, T1>>
{
  @Override protected PMatrixM4x4F<T0, T1> newMatrix()
  {
    return new PMatrixM4x4F<T0, T1>();
  }

  @Override protected PMatrixM4x4F<T0, T1> newMatrixFrom(
    final MatrixReadable4x4FType source)
  {
    final PMatrixM4x4F<T0, T1> m = new PMatrixM4x4F<T0, T1>();
    MatrixM4x4F.copy(source, m);
    return m;
  }

  @Override
  protected void checkDirectBufferInvariants(final PMatrixM4x4F<T0, T1> m)
  {
    Assert.assertEquals(0L, (long) m.getDirectFloatBuffer().position());
  }
}
