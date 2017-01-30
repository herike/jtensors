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

import com.io7m.jtensors.parameterized.PVectorM4F;
import com.io7m.jtensors.tests.VectorM4FContract;

public final class PVectorM4FUntypedTest<T>
  extends VectorM4FContract<PVectorM4F<T>>
{
  @Override protected PVectorM4F<T> newVectorM4F(
    final float x,
    final float y,
    final float z,
    final float w)
  {
    return new PVectorM4F<T>(x, y, z, w);
  }

  @Override protected PVectorM4F<T> newVectorM4FFrom(final PVectorM4F<T> v)
  {
    return new PVectorM4F<T>(v);
  }

  @Override protected PVectorM4F<T> newVectorM4F()
  {
    return new PVectorM4F<T>();
  }
}