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

package com.io7m.jtensors.tests.parameterized;

import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.MatrixReadable4x4DType;
import com.io7m.jtensors.parameterized.PMatrix4x4DType;
import com.io7m.jtensors.parameterized.PMatrixHeapArrayM4x4D;
import com.io7m.jtensors.tests.Matrix4x4DContract;

public final class PMatrixHeapArrayM4x4DUntypedTest<T0, T1>
  extends Matrix4x4DContract<PMatrix4x4DType<T0, T1>>
{
  @Override protected PMatrix4x4DType<T0, T1> newMatrix()
  {
    return PMatrixHeapArrayM4x4D.newMatrix();
  }

  @Override protected PMatrix4x4DType<T0, T1> newMatrixFrom(
    final MatrixReadable4x4DType m)
  {
    final PMatrix4x4DType<T0, T1> r = PMatrixHeapArrayM4x4D.newMatrix();
    MatrixM4x4D.copy(m, r);
    return r;
  }

  @Override
  protected void checkDirectBufferInvariants(final PMatrix4x4DType<T0, T1> m)
  {
    // Nothing
  }
}