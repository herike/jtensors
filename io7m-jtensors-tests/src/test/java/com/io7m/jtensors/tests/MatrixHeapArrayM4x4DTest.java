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

package com.io7m.jtensors.tests;

import com.io7m.jtensors.Matrix4x4DType;
import com.io7m.jtensors.MatrixHeapArrayM4x4D;
import com.io7m.jtensors.MatrixReadable4x4DType;

public final class MatrixHeapArrayM4x4DTest
  extends Matrix4x4DContract<Matrix4x4DType>
{
  @Override protected Matrix4x4DType newMatrix()
  {
    return MatrixHeapArrayM4x4D.newMatrix();
  }

  @Override protected Matrix4x4DType newMatrixFrom(
    final MatrixReadable4x4DType source)
  {
    return MatrixHeapArrayM4x4D.newMatrixFrom(source);
  }

  @Override protected void checkDirectBufferInvariants(final Matrix4x4DType m)
  {
    // Nothing required
  }
}
