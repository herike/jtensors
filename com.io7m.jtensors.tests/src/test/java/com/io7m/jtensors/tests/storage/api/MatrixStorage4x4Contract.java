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

package com.io7m.jtensors.tests.storage.api;

import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4F;
import com.io7m.jtensors.generators.Matrix4x4DGenerator;
import com.io7m.jtensors.generators.Matrix4x4FGenerator;
import com.io7m.jtensors.storage.api.unparameterized.matrices.MatrixStorage4x4Type;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class MatrixStorage4x4Contract
{
  protected abstract void checkAlmostEqual(
    double a,
    double b);

  protected abstract MatrixStorage4x4Type createIdentity();

  private static Generator<Matrix4x4D> createGeneratorP4x4D()
  {
    return Matrix4x4DGenerator.create();
  }

  private static Generator<Matrix4x4F> createGeneratorP4x4F()
  {
    return Matrix4x4FGenerator.create();
  }

  @Test
  public final void testGetSetV4D()
  {
    final Generator<Matrix4x4D> gen = createGeneratorP4x4D();
    final Matrix4x4D m = gen.next();
    final MatrixStorage4x4Type sv = this.createIdentity();
    sv.setMatrix4x4D(m);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        this.checkAlmostEqual(
          m.rowColumn(row, column),
          sv.rowColumn(row, column));
      }
    }
    
    this.checkAlmostEqual(m.r0c0(), sv.r0c0());
    this.checkAlmostEqual(m.r0c1(), sv.r0c1());
    this.checkAlmostEqual(m.r0c2(), sv.r0c2());
    this.checkAlmostEqual(m.r0c3(), sv.r0c3());

    this.checkAlmostEqual(m.r1c0(), sv.r1c0());
    this.checkAlmostEqual(m.r1c1(), sv.r1c1());
    this.checkAlmostEqual(m.r1c2(), sv.r1c2());
    this.checkAlmostEqual(m.r1c3(), sv.r1c3());

    this.checkAlmostEqual(m.r2c0(), sv.r2c0());
    this.checkAlmostEqual(m.r2c1(), sv.r2c1());
    this.checkAlmostEqual(m.r2c2(), sv.r2c2());
    this.checkAlmostEqual(m.r2c3(), sv.r2c3());

    this.checkAlmostEqual(m.r3c0(), sv.r3c0());
    this.checkAlmostEqual(m.r3c1(), sv.r3c1());
    this.checkAlmostEqual(m.r3c2(), sv.r3c2());
    this.checkAlmostEqual(m.r3c3(), sv.r3c3());
  }

  @Test
  public final void testGetSetV4F()
  {
    final Generator<Matrix4x4F> gen = createGeneratorP4x4F();
    final Matrix4x4F m = gen.next();
    final MatrixStorage4x4Type sv = this.createIdentity();
    sv.setMatrix4x4F(m);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        this.checkAlmostEqual(
          (double) m.rowColumn(row, column),
          sv.rowColumn(row, column));
      }
    }

    this.checkAlmostEqual((double) m.r0c0(), sv.r0c0());
    this.checkAlmostEqual((double) m.r0c1(), sv.r0c1());
    this.checkAlmostEqual((double) m.r0c2(), sv.r0c2());
    this.checkAlmostEqual((double) m.r0c3(), sv.r0c3());

    this.checkAlmostEqual((double) m.r1c0(), sv.r1c0());
    this.checkAlmostEqual((double) m.r1c1(), sv.r1c1());
    this.checkAlmostEqual((double) m.r1c2(), sv.r1c2());
    this.checkAlmostEqual((double) m.r1c3(), sv.r1c3());

    this.checkAlmostEqual((double) m.r2c0(), sv.r2c0());
    this.checkAlmostEqual((double) m.r2c1(), sv.r2c1());
    this.checkAlmostEqual((double) m.r2c2(), sv.r2c2());
    this.checkAlmostEqual((double) m.r2c3(), sv.r2c3());

    this.checkAlmostEqual((double) m.r3c0(), sv.r3c0());
    this.checkAlmostEqual((double) m.r3c1(), sv.r3c1());
    this.checkAlmostEqual((double) m.r3c2(), sv.r3c2());
    this.checkAlmostEqual((double) m.r3c3(), sv.r3c3());
  }
}
