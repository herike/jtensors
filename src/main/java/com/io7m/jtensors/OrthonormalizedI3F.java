/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Three immutable orthonormal vectors.
 * 
 * @since 5.2.0
 */

@Immutable public final class OrthonormalizedI3F
{
  private final @Nonnull VectorI3F rv0;
  private final @Nonnull VectorI3F rv1;
  private final @Nonnull VectorI3F rv2;

  /**
   * Orthonormalize and store the vectors <code>v0</code>, <code>v1</code>,
   * and <code>v2</code>.
   */

  public OrthonormalizedI3F(
    final @Nonnull VectorI3F v0,
    final @Nonnull VectorI3F v1,
    final @Nonnull VectorI3F v2)
  {
    this.rv0 = VectorI3F.normalize(v0);

    {
      final float v0_dot_v1 = VectorI3F.dotProduct(this.rv0, v1);
      final VectorI3F v0_s = VectorI3F.scale(this.rv0, v0_dot_v1);
      this.rv1 = VectorI3F.normalize(VectorI3F.subtract(v1, v0_s));
    }

    {
      final float v1_dot_v2 = VectorI3F.dotProduct(this.rv1, v2);
      final float v0_dot_v2 = VectorI3F.dotProduct(this.rv0, v2);

      final VectorI3F v0_s = VectorI3F.scale(this.rv0, v0_dot_v2);
      final VectorI3F v2_s = VectorI3F.scale(this.rv1, v1_dot_v2);
      final VectorI3F vs = VectorI3F.add(v0_s, v2_s);
      this.rv2 = VectorI3F.normalize(VectorI3F.subtract(v2, vs));
    }
  }

  @Override public boolean equals(
    final Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final OrthonormalizedI3F other = (OrthonormalizedI3F) obj;
    if (!this.rv0.equals(other.rv0)) {
      return false;
    }
    if (!this.rv1.equals(other.rv1)) {
      return false;
    }
    if (!this.rv2.equals(other.rv2)) {
      return false;
    }
    return true;
  }

  /**
   * Retrieve the first vector passed to
   * {@link #OrthonormalizedI3F(VectorI3F, VectorI3F, VectorI3F)},
   * orthonormalized with respect to the other two vectors.
   */

  public @Nonnull VectorI3F getV0()
  {
    return this.rv0;
  }

  /**
   * Retrieve the second vector passed to
   * {@link #OrthonormalizedI3F(VectorI3F, VectorI3F, VectorI3F)},
   * orthonormalized with respect to the other two vectors.
   */

  public @Nonnull VectorI3F getV1()
  {
    return this.rv1;
  }

  /**
   * Retrieve the third vector passed to
   * {@link #OrthonormalizedI3F(VectorI3F, VectorI3F, VectorI3F)},
   * orthonormalized with respect to the other two vectors.
   */

  public @Nonnull VectorI3F getV2()
  {
    return this.rv2;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.rv0.hashCode();
    result = (prime * result) + this.rv1.hashCode();
    result = (prime * result) + this.rv2.hashCode();
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[OrthonormalizedI3F ");
    builder.append(this.rv0);
    builder.append(" ");
    builder.append(this.rv1);
    builder.append(" ");
    builder.append(this.rv2);
    builder.append("]");
    return builder.toString();
  }
}