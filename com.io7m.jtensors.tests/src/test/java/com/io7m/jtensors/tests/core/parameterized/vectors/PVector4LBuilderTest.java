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

package com.io7m.jtensors.tests.core.parameterized.vectors;

import com.io7m.jtensors.core.parameterized.vectors.PVector2L;
import com.io7m.jtensors.core.parameterized.vectors.PVector3L;
import com.io7m.jtensors.core.parameterized.vectors.PVector4L;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public final class PVector4LBuilderTest
{
  @Rule public ExpectedException expected = ExpectedException.none();

  @Test
  public void testBuilder2L()
  {
    final PVector4L<Object> v =
      PVector4L.builder()
        .from(PVector2L.of(0L, 1L))
        .setZ(2L)
        .setW(3L)
        .build();
    Assert.assertEquals(0L, v.x());
    Assert.assertEquals(1L, v.y());
    Assert.assertEquals(2L, v.z());
    Assert.assertEquals(3L, v.w());
  }

  @Test
  public void testBuilder2LMissing()
  {
    this.expected.expect(IllegalStateException.class);

    PVector4L.builder()
      .from(PVector2L.of(0L, 1L))
      .build();
    Assert.fail();
  }

  @Test
  public void testBuilder3L()
  {
    final PVector4L<Object> v =
      PVector4L.builder()
        .from(PVector3L.of(0L, 1L, 2L))
        .setW(3L)
        .build();
    Assert.assertEquals(0L, v.x());
    Assert.assertEquals(1L, v.y());
    Assert.assertEquals(2L, v.z());
    Assert.assertEquals(3L, v.w());
  }

  @Test
  public void testBuilder3LMissing()
  {
    this.expected.expect(IllegalStateException.class);

    PVector4L.builder()
      .from(PVector3L.of(0L, 1L, 2L))
      .build();
    Assert.fail();
  }

  @Test
  public void testBuilder4L()
  {
    final PVector4L<Object> v =
      PVector4L.builder()
        .from(PVector4L.of(0L, 1L, 2L, 3L))
        .build();
    Assert.assertEquals(0L, v.x());
    Assert.assertEquals(1L, v.y());
    Assert.assertEquals(2L, v.z());
    Assert.assertEquals(3L, v.w());
  }
}
