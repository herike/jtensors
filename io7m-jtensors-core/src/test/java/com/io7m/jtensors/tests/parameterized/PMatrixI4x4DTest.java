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

package com.io7m.jtensors.tests.parameterized;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jtensors.VectorI4D;
import com.io7m.jtensors.VectorM4D;
import com.io7m.jtensors.parameterized.PMatrixI4x4D;
import com.io7m.jtensors.parameterized.PMatrixM4x4D;

@SuppressWarnings("static-method") public class PMatrixI4x4DTest
{
  @Test public void testEquals()
  {
    final PMatrixM4x4D<Object, Object> m0 =
      new PMatrixM4x4D<Object, Object>();

    int index = 0;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.set(row, col, index);
        ++index;
      }
    }

    final PMatrixI4x4D<Object, Object> im0 = PMatrixI4x4D.newFromReadable(m0);
    final PMatrixI4x4D<Object, Object> im1 = PMatrixI4x4D.newFromReadable(m0);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        Assert.assertEquals(
          im0.getRowColumnD(row, col),
          m0.getRowColumnD(row, col),
          0.0);
      }
    }

    Assert.assertEquals(im0, im0);
    Assert.assertEquals(im0.hashCode(), im0.hashCode());
    Assert.assertEquals(im0, im1);
    Assert.assertFalse(im0.equals(null));
    Assert.assertFalse(im0.equals(Integer.valueOf(23)));

    index = 100;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.set(row, col, index);
        ++index;
      }
    }

    final PMatrixI4x4D<Object, Object> im2 = PMatrixI4x4D.newFromReadable(m0);
    Assert.assertFalse(im0.equals(im2));
  }

  @Test public void testFromColumns()
  {
    final PMatrixM4x4D<Object, Object> m0 =
      new PMatrixM4x4D<Object, Object>();

    m0.set(0, 0, 0.0f);
    m0.set(1, 0, 0.1f);
    m0.set(2, 0, 0.2f);
    m0.set(3, 0, 0.3f);

    m0.set(0, 1, 10.0f);
    m0.set(1, 1, 10.1f);
    m0.set(2, 1, 10.2f);
    m0.set(3, 1, 10.3f);

    m0.set(0, 2, 20.0f);
    m0.set(1, 2, 20.1f);
    m0.set(2, 2, 20.2f);
    m0.set(3, 2, 20.3f);

    m0.set(0, 3, 30.0f);
    m0.set(1, 3, 30.1f);
    m0.set(2, 3, 30.2f);
    m0.set(3, 3, 30.3f);

    final PMatrixI4x4D<Object, Object> im0 = PMatrixI4x4D.newFromReadable(m0);

    final VectorI4D column_0 = new VectorI4D(0.0f, 0.1f, 0.2f, 0.3f);
    final VectorI4D column_1 = new VectorI4D(10.0f, 10.1f, 10.2f, 10.3f);
    final VectorI4D column_2 = new VectorI4D(20.0f, 20.1f, 20.2f, 20.3f);
    final VectorI4D column_3 = new VectorI4D(30.0f, 30.1f, 30.2f, 30.3f);

    final PMatrixI4x4D<Object, Object> im1 =
      PMatrixI4x4D.newFromColumns(column_0, column_1, column_2, column_3);

    Assert.assertEquals(im0, im1);
  }

  @Test public void testFromRows()
  {
    final PMatrixM4x4D<Object, Object> m0 =
      new PMatrixM4x4D<Object, Object>();

    m0.set(0, 0, 0.0f);
    m0.set(1, 0, 0.1f);
    m0.set(2, 0, 0.2f);
    m0.set(3, 0, 0.3f);

    m0.set(0, 1, 10.0f);
    m0.set(1, 1, 10.1f);
    m0.set(2, 1, 10.2f);
    m0.set(3, 1, 10.3f);

    m0.set(0, 2, 20.0f);
    m0.set(1, 2, 20.1f);
    m0.set(2, 2, 20.2f);
    m0.set(3, 2, 20.3f);

    m0.set(0, 3, 30.0f);
    m0.set(1, 3, 30.1f);
    m0.set(2, 3, 30.2f);
    m0.set(3, 3, 30.3f);

    final PMatrixI4x4D<Object, Object> im = PMatrixI4x4D.newFromReadable(m0);

    final VectorM4D row = new VectorM4D();

    im.getRow4D(0, row);
    Assert.assertEquals(0.0f, row.getXD(), 0.0);
    Assert.assertEquals(10.0f, row.getYD(), 0.0);
    Assert.assertEquals(20.0f, row.getZD(), 0.0);
    Assert.assertEquals(30.0f, row.getWD(), 0.0);

    im.getRow4D(1, row);
    Assert.assertEquals(0.1f, row.getXD(), 0.0);
    Assert.assertEquals(10.1f, row.getYD(), 0.0);
    Assert.assertEquals(20.1f, row.getZD(), 0.0);
    Assert.assertEquals(30.1f, row.getWD(), 0.0);

    im.getRow4D(2, row);
    Assert.assertEquals(0.2f, row.getXD(), 0.0);
    Assert.assertEquals(10.2f, row.getYD(), 0.0);
    Assert.assertEquals(20.2f, row.getZD(), 0.0);
    Assert.assertEquals(30.2f, row.getWD(), 0.0);

    im.getRow4D(3, row);
    Assert.assertEquals(0.3f, row.getXD(), 0.0);
    Assert.assertEquals(10.3f, row.getYD(), 0.0);
    Assert.assertEquals(20.3f, row.getZD(), 0.0);
    Assert.assertEquals(30.3f, row.getWD(), 0.0);
  }

  @Test public void testIdentity()
  {
    final PMatrixM4x4D<Object, Object> m0 =
      new PMatrixM4x4D<Object, Object>();
    final PMatrixI4x4D<Object, Object> im0 = PMatrixI4x4D.identity();
    final PMatrixI4x4D<Object, Object> im1 = PMatrixI4x4D.newFromReadable(m0);
    Assert.assertEquals(im1, im0);
  }

  @Test public void testMakeMatrix4x4D()
  {
    final PMatrixM4x4D<Object, Object> m0 =
      new PMatrixM4x4D<Object, Object>();
    final PMatrixM4x4D<Object, Object> m1 =
      new PMatrixM4x4D<Object, Object>();

    int index = 0;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.set(row, col, index);
        ++index;
      }
    }

    final PMatrixI4x4D<Object, Object> im = PMatrixI4x4D.newFromReadable(m0);
    im.makeMatrixM4x4D(m1);
    Assert.assertEquals(m0, m1);
  }

  @Test public void testToString()
  {
    final PMatrixM4x4D<Object, Object> m0 =
      new PMatrixM4x4D<Object, Object>();
    final PMatrixM4x4D<Object, Object> m1 =
      new PMatrixM4x4D<Object, Object>();

    int index = 0;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.set(row, col, index);
        m1.set(row, col, index);
        ++index;
      }
    }

    final PMatrixI4x4D<Object, Object> im0 = PMatrixI4x4D.newFromReadable(m0);
    final PMatrixI4x4D<Object, Object> im1 = PMatrixI4x4D.newFromReadable(m1);
    Assert.assertEquals(im0.toString(), im1.toString());
  }
}
