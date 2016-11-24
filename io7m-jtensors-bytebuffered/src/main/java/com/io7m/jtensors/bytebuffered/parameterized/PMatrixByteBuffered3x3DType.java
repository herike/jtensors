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

package com.io7m.jtensors.bytebuffered.parameterized;

import com.io7m.jtensors.bytebuffered.ByteBufferedType;
import com.io7m.jtensors.parameterized.PMatrix3x3DType;

/**
 * <p>The type of {@link PMatrix3x3DType} values that are also byte
 * buffered.</p>
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 *
 * @since 7.0.0
 */

public interface PMatrixByteBuffered3x3DType<T0, T1>
  extends PMatrix3x3DType<T0, T1>, ByteBufferedType
{
  // No extra functions
}
