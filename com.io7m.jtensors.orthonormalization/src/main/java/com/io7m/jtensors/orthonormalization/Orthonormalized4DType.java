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

package com.io7m.jtensors.orthonormalization;

import com.io7m.jtensors.core.JTensorsImmutableStyleType;
import com.io7m.jtensors.core.unparameterized.vectors.Vector4D;
import org.immutables.value.Value;

/**
 * A set of vectors that are orthogonal to each other and are normalized.
 */

@JTensorsImmutableStyleType
@Value.Immutable
public interface Orthonormalized4DType
{
  /**
   * @return Vector 0
   */

  @Value.Parameter(order = 0)
  Vector4D v0();

  /**
   * @return Vector 1
   */

  @Value.Parameter(order = 1)
  Vector4D v1();

  /**
   * @return Vector 2
   */

  @Value.Parameter(order = 2)
  Vector4D v2();
}
