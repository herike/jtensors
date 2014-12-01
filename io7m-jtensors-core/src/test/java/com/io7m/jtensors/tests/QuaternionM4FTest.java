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

package com.io7m.jtensors.tests;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.QuaternionM4D;
import com.io7m.jtensors.QuaternionM4F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorReadable3FType;

@SuppressWarnings("static-method") public class QuaternionM4FTest extends
  QuaternionM4Contract
{
  private static final VectorReadable3FType AXIS_X = new VectorI3F(1, 0, 0);
  private static final VectorReadable3FType AXIS_Y = new VectorI3F(0, 1, 0);
  private static final VectorReadable3FType AXIS_Z = new VectorI3F(0, 0, 1);

  @Override @Test public void testAdd()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v0 = new QuaternionM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v1 = new QuaternionM4F(x1, y1, z1, w1);

      final QuaternionM4F vr0 = new QuaternionM4F();
      QuaternionM4F.add(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr0.getXF(),
        v0.getXF() + v1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr0.getYF(),
        v0.getYF() + v1.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr0.getZF(),
        v0.getZF() + v1.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr0.getWF(),
        v0.getWF() + v1.getWF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        final float orig_w = v0.getWF();
        QuaternionM4F.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v0.getXF(),
          orig_x + v1.getXF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v0.getYF(),
          orig_y + v1.getYF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v0.getZF(),
          orig_z + v1.getZF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v0.getWF(),
          orig_w + v1.getWF()));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final QuaternionM4F out = new QuaternionM4F();
    final QuaternionM4F v0 = new QuaternionM4F(1.0f, 1.0f, 1.0f, 1.0f);
    final QuaternionM4F v1 = new QuaternionM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(out.getZF() == 0.0f);
    Assert.assertTrue(out.getWF() == 1.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v0.getZF() == 1.0f);
    Assert.assertTrue(v0.getWF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
    Assert.assertTrue(v1.getZF() == 1.0f);
    Assert.assertTrue(v1.getWF() == 1.0f);

    final QuaternionM4F ov0 = QuaternionM4F.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(out.getZF() == 2.0);
    Assert.assertTrue(out.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v0.getZF() == 1.0f);
    Assert.assertTrue(v0.getWF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
    Assert.assertTrue(v1.getZF() == 1.0f);
    Assert.assertTrue(v1.getWF() == 1.0f);

    final QuaternionM4F ov1 = QuaternionM4F.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0);
    Assert.assertTrue(ov1.getYF() == 2.0);
    Assert.assertTrue(ov1.getZF() == 2.0);
    Assert.assertTrue(ov1.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 2.0);
    Assert.assertTrue(v0.getYF() == 2.0);
    Assert.assertTrue(v0.getZF() == 2.0);
    Assert.assertTrue(v0.getWF() == 2.0);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
    Assert.assertTrue(v1.getZF() == 1.0f);
    Assert.assertTrue(v1.getWF() == 1.0f);
  }

  @Override @Test public void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final QuaternionM4F m0 = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F m1 = new QuaternionM4F(q, y, z, w);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F m1 = new QuaternionM4F(x, q, z, w);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F m1 = new QuaternionM4F(x, y, q, w);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F m1 = new QuaternionM4F(x, y, z, q);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F m1 = new QuaternionM4F(q, q, z, w);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F m1 = new QuaternionM4F(q, y, q, w);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F m1 = new QuaternionM4F(q, y, z, q);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F m1 = new QuaternionM4F(q, q, q, w);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F m1 = new QuaternionM4F(q, q, z, q);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F m1 = new QuaternionM4F(q, q, q, q);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F m1 = new QuaternionM4F(x, q, q, q);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F m1 = new QuaternionM4F(x, y, q, q);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v0 = new QuaternionM4F(x0, y0, z0, w0);
      final QuaternionM4F v1 = new QuaternionM4F(x0, y0, z0, w0);
      final QuaternionM4F v2 = new QuaternionM4F(x0, y0, z0, w0);

      Assert.assertTrue(QuaternionM4F.almostEqual(ec, v0, v1));
      Assert.assertTrue(QuaternionM4F.almostEqual(ec, v1, v2));
      Assert.assertTrue(QuaternionM4F.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final QuaternionM4F v = new QuaternionM4F(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
    Assert.assertTrue(v.getWF() == v.getWF());
  }

  @Override @Test public void testConjugate()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F e = new QuaternionM4F(-1.0f, -2.0f, -3.0f, 4.0f);
    final QuaternionM4F q = new QuaternionM4F(1.0f, 2.0f, 3.0f, 4.0f);
    final QuaternionM4F r = new QuaternionM4F();
    final QuaternionM4F u = QuaternionM4F.conjugate(q, r);
    final boolean t = QuaternionM4F.almostEqual(context, e, r);

    Assert.assertTrue(t);
    Assert.assertSame(r, u);
  }

  @Override @Test public void testConjugateInPlace()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F e = new QuaternionM4F(-1.0f, -2.0f, -3.0f, 4.0f);
    final QuaternionM4F q = new QuaternionM4F(1.0f, 2.0f, 3.0f, 4.0f);
    final QuaternionM4F r = new QuaternionM4F(q);
    final QuaternionM4F u = QuaternionM4F.conjugateInPlace(r);
    final boolean t = QuaternionM4F.almostEqual(context, e, r);

    Assert.assertTrue(t);
    Assert.assertSame(r, u);
  }

  @Override @Test public void testConjugateInvertible()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) ((Math.random() * 200) - 100);
      final float y = (float) ((Math.random() * 200) - 100);
      final float z = (float) ((Math.random() * 200) - 100);
      final float w = (float) ((Math.random() * 200) - 100);

      final QuaternionM4F q = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F qc0 = new QuaternionM4F();
      final QuaternionM4F qc1 = new QuaternionM4F();
      QuaternionM4F.conjugate(q, qc0);
      QuaternionM4F.conjugate(qc0, qc1);

      eq = AlmostEqualFloat.almostEqual(context, q.getXF(), qc1.getXF());
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context, q.getYF(), qc1.getYF());
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context, q.getZF(), qc1.getZF());
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context, q.getWF(), qc1.getWF());
      Assert.assertTrue(eq);
    }
  }

  @Override @Test public void testCopy()
  {
    final QuaternionM4F vb = new QuaternionM4F(5, 6, 7, 8);
    final QuaternionM4F va = new QuaternionM4F(1, 2, 3, 4);

    Assert.assertFalse(va.getXF() == vb.getXF());
    Assert.assertFalse(va.getYF() == vb.getYF());
    Assert.assertFalse(va.getZF() == vb.getZF());
    Assert.assertFalse(va.getWF() == vb.getWF());

    QuaternionM4F.copy(va, vb);

    Assert.assertTrue(va.getXF() == vb.getXF());
    Assert.assertTrue(va.getYF() == vb.getYF());
    Assert.assertTrue(va.getZF() == vb.getZF());
    Assert.assertTrue(va.getWF() == vb.getWF());
  }

  @Test public void testCopy2Correct()
  {
    final QuaternionM4F v0 =
      new QuaternionM4F(
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE);
    final QuaternionM4F v1 = new QuaternionM4F();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(0, v1.getZF(), 0.0f);
    Assert.assertEquals(1, v1.getWF(), 0.0f);
  }

  @Test public void testCopy3Correct()
  {
    final QuaternionM4F v0 =
      new QuaternionM4F(
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE);
    final QuaternionM4F v1 = new QuaternionM4F();

    v1.copyFrom4F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);
    Assert.assertEquals(v0.getWF(), v1.getWF(), 0.0f);
  }

  @Test public void testCopy4Correct()
  {
    final QuaternionM4F v0 =
      new QuaternionM4F(
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE);
    final QuaternionM4F v1 = new QuaternionM4F();

    v1.copyFrom3F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);
    Assert.assertEquals(1.0f, v1.getWF(), 0.0f);
  }

  @Override @Test public void testDefault0001()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    Assert.assertTrue(QuaternionM4F.almostEqual(
      ec,
      new QuaternionM4F(),
      new QuaternionM4F(0, 0, 0, 1)));
  }

  @Override @Test public void testDotProduct()
  {
    final QuaternionM4F v0 = new QuaternionM4F(10.0f, 10.0f, 10.0f, 10.0f);
    final QuaternionM4F v1 = new QuaternionM4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = QuaternionM4F.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v0.getWF() == 10.0f);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(v1.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }

    {
      final double p = QuaternionM4F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v0.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }

    {
      final double p = QuaternionM4F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(v1.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final float z = (float) Math.random();
      final float w = (float) Math.random();
      final QuaternionM4D q = new QuaternionM4D(x, y, z, w);
      final double dp = QuaternionM4D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final QuaternionM4F v0 = new QuaternionM4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = QuaternionM4F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v0.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }

    {
      final double p = QuaternionM4F.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v0.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final QuaternionM4F m0 = new QuaternionM4F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F();
      final QuaternionM4F m1 = new QuaternionM4F();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    {
      final QuaternionM4F m0 = new QuaternionM4F();
      final QuaternionM4F m1 = new QuaternionM4F();
      m1.setXF(23.0f);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F();
      final QuaternionM4F m1 = new QuaternionM4F();
      m1.setYF(23.0f);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F();
      final QuaternionM4F m1 = new QuaternionM4F();
      m1.setZF(23.0f);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F();
      final QuaternionM4F m1 = new QuaternionM4F();
      m1.setWF(23.0f);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final QuaternionM4F m0 = new QuaternionM4F();
      final QuaternionM4F m1 = new QuaternionM4F();
      m1.setXF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F();
      final QuaternionM4F m1 = new QuaternionM4F();
      m1.setYF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F();
      final QuaternionM4F m1 = new QuaternionM4F();
      m1.setZF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionM4F m0 = new QuaternionM4F();
      final QuaternionM4F m1 = new QuaternionM4F();
      m1.setWF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final QuaternionM4F v0 = new QuaternionM4F(1.0f, 2.0f, 3.0f, 4.0f);
    final QuaternionM4F v1 = new QuaternionM4F(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
    Assert.assertTrue(v0.getWF() == v1.getWF());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v0 = new QuaternionM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v1 = new QuaternionM4F(x1, y1, z1, w1);

      final QuaternionM4F vr0 = new QuaternionM4F();
      final QuaternionM4F vr1 = new QuaternionM4F();
      QuaternionM4F.interpolateLinear(v0, v1, 0.0f, vr0);
      QuaternionM4F.interpolateLinear(v0, v1, 1.0f, vr1);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        v0.getXF(),
        vr0.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        v0.getYF(),
        vr0.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        v0.getZF(),
        vr0.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        v0.getWF(),
        vr0.getWF()));

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        v1.getXF(),
        vr1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        v1.getYF(),
        vr1.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        v1.getZF(),
        vr1.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        v1.getWF(),
        vr1.getWF()));
    }
  }

  @Override @Test public void testLookAtConsistent_Origin_NegativeX()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.Context mc = new MatrixM4x4F.Context();
    final QuaternionM4F.Context qc = new QuaternionM4F.Context();

    final MatrixM4x4F mr = new MatrixM4x4F();
    final MatrixM4x4F mqr = new MatrixM4x4F();
    final QuaternionM4F qr = new QuaternionM4F();

    final VectorReadable3FType origin = new VectorI3F(0, 0, 0);
    final VectorReadable3FType target = new VectorI3F(-1, 0, 0);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_Y;

    MatrixM4x4F.lookAtWithContext(mc, origin, target, axis, mr);
    QuaternionM4F.lookAtWithContext(qc, origin, target, axis, qr);
    QuaternionM4F.makeRotationMatrix4x4(qr, mqr);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mqr: ");
    System.out.println(mqr);

    boolean eq = false;

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mqr.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(ec, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testLookAtConsistent_Origin_PositiveX()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.Context mc = new MatrixM4x4F.Context();
    final QuaternionM4F.Context qc = new QuaternionM4F.Context();

    final MatrixM4x4F mr = new MatrixM4x4F();
    final MatrixM4x4F mqr = new MatrixM4x4F();
    final QuaternionM4F qr = new QuaternionM4F();

    final VectorReadable3FType origin = new VectorI3F(0, 0, 0);
    final VectorReadable3FType target = new VectorI3F(1, 0, 0);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_Y;

    MatrixM4x4F.lookAtWithContext(mc, origin, target, axis, mr);
    QuaternionM4F.lookAtWithContext(qc, origin, target, axis, qr);
    QuaternionM4F.makeRotationMatrix4x4(qr, mqr);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mqr: ");
    System.out.println(mqr);

    boolean eq = false;

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mqr.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(ec, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testLookAtMatrixEquivalentAxisY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F.Context qc = new QuaternionM4F.Context();
    final MatrixM4x4F.Context mc = new MatrixM4x4F.Context();

    final MatrixM4x4F ml = new MatrixM4x4F();
    final QuaternionM4F lq = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float origin_x =
        (float) ((Math.random() * 100) - (Math.random() * 100));
      final float origin_y =
        (float) ((Math.random() * 100) - (Math.random() * 100));
      final float origin_z =
        (float) ((Math.random() * 100) - (Math.random() * 100));

      final float target_x =
        (float) ((Math.random() * 100) - (Math.random() * 100));
      final float target_y =
        (float) ((Math.random() * 100) - (Math.random() * 100));
      final float target_z =
        (float) ((Math.random() * 100) - (Math.random() * 100));

      final VectorI3F origin = new VectorI3F(origin_x, origin_y, origin_z);
      final VectorI3F target = new VectorI3F(target_x, target_y, target_z);

      MatrixM4x4F.lookAtWithContext(
        mc,
        origin,
        target,
        QuaternionM4FTest.AXIS_Y,
        ml);
      QuaternionM4F.lookAtWithContext(
        qc,
        origin,
        target,
        QuaternionM4FTest.AXIS_Y,
        lq);
      QuaternionM4F.makeRotationMatrix4x4(lq, mq);

      System.out.println("ml : ");
      System.out.println(ml);
      System.out.println("mq : ");
      System.out.println(mq);

      for (int row = 0; row < 3; ++row) {
        for (int col = 0; col < 3; ++col) {
          final float x = ml.getRowColumnF(row, col);
          final float y = mq.getRowColumnF(row, col);

          final boolean eq = AlmostEqualFloat.almostEqual(ec, x, y);
          Assert.assertTrue(eq);
        }
      }
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v = new QuaternionM4F(x, y, z, w);

      final double m = QuaternionM4F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final float y =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final float z =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final float w =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final QuaternionM4F v = new QuaternionM4F(x, y, z, w);

      final QuaternionM4F vr = new QuaternionM4F();
      QuaternionM4F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = QuaternionM4F.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4F v = new QuaternionM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final QuaternionM4F vr = QuaternionM4F.normalizeInPlace(v);
    final double m = QuaternionM4F.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4F v = new QuaternionM4F(1.0f, 0.0f, 0.0f, 0.0f);
    final double m = QuaternionM4F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final QuaternionM4F v = new QuaternionM4F(8.0f, 0.0f, 0.0f, 0.0f);

    {
      final double p = QuaternionM4F.dotProduct(v, v);
      final double q = QuaternionM4F.magnitudeSquared(v);
      final double r = QuaternionM4F.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4F v = new QuaternionM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final double m = QuaternionM4F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public void testMakeAxisAngleNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final VectorI3F axis_r =
        new VectorI3F(
          (float) Math.random(),
          (float) Math.random(),
          (float) Math.random());
      final VectorI3F axis_n = VectorI3F.normalize(axis_r);

      final QuaternionM4F q = new QuaternionM4F();
      QuaternionM4F.makeFromAxisAngle(
        axis_n,
        Math.toRadians(Math.random() * 360),
        q);

      final double m = QuaternionM4F.magnitude(q);

      System.out.println("axis_r : " + axis_r);
      System.out.println("axis_n : " + axis_n);
      System.out.println("m      : " + m);
      System.out.println("q      : " + q);
      System.out.println("--");

      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Override @Test public void testMakeAxisAngleX_45()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis = new VectorI3F(1.0f, 0.0f, 0.0f);
    final QuaternionM4F q = new QuaternionM4F();
    final QuaternionM4F r =
      QuaternionM4F.makeFromAxisAngle(axis, (float) Math.toRadians(45), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      q.getXF(),
      0.3826834323650898f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getYF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getZF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      q.getWF(),
      0.9238795325112867f));
  }

  @Override @Test public void testMakeAxisAngleX_90()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis = new VectorI3F(1.0f, 0.0f, 0.0f);
    final QuaternionM4F q = new QuaternionM4F();
    final QuaternionM4F r =
      QuaternionM4F.makeFromAxisAngle(axis, (float) Math.toRadians(90), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      q.getXF(),
      0.7071067811865475f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getYF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getZF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      q.getWF(),
      0.7071067811865475f));
  }

  @Override @Test public void testMakeAxisAngleY_45()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    final QuaternionM4F q = new QuaternionM4F();
    final QuaternionM4F r =
      QuaternionM4F.makeFromAxisAngle(axis, (float) Math.toRadians(45), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getXF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      q.getYF(),
      0.3826834323650898f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getZF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      q.getWF(),
      0.9238795325112867f));
  }

  @Override @Test public void testMakeAxisAngleY_90()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    final QuaternionM4F q = new QuaternionM4F();
    final QuaternionM4F r =
      QuaternionM4F.makeFromAxisAngle(axis, (float) Math.toRadians(90), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getXF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      q.getYF(),
      0.7071067811865475f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getZF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      q.getWF(),
      0.7071067811865475f));
  }

  @Override @Test public void testMakeAxisAngleZ_45()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis = new VectorI3F(0.0f, 0.0f, 1.0f);
    final QuaternionM4F q = new QuaternionM4F();
    final QuaternionM4F r =
      QuaternionM4F.makeFromAxisAngle(axis, (float) Math.toRadians(45), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getXF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getYF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      q.getZF(),
      0.3826834323650898f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      q.getWF(),
      0.9238795325112867f));
  }

  @Override @Test public void testMakeAxisAngleZ_90()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis = new VectorI3F(0.0f, 0.0f, 1.0f);
    final QuaternionM4F q = new QuaternionM4F();
    final QuaternionM4F r =
      QuaternionM4F.makeFromAxisAngle(axis, (float) Math.toRadians(90), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getXF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getYF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      q.getZF(),
      0.7071067811865475f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      q.getWF(),
      0.7071067811865475f));
  }

  @Override @Test public void testMakeFromMatrix3x3Exhaustive()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    final QuaternionM4F qfm = new QuaternionM4F();
    final QuaternionM4F qaa = new QuaternionM4F();
    final MatrixM3x3F m = new MatrixM3x3F();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float degrees = (float) ((2 * Math.random() * 360.0f) - 360.0f);
      final float angle = (float) Math.toRadians(degrees);
      final float axis_x = (float) Math.random();
      final float axis_y = (float) Math.random();
      final float axis_z = (float) Math.random();
      final VectorM3F axis = new VectorM3F(axis_x, axis_y, axis_z);
      VectorM3F.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      QuaternionM4F.makeFromAxisAngle(axis, angle, qaa);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM3x3F.makeRotationInto(angle, axis, m);
      QuaternionM4F.makeFromRotationMatrix3x3(m, qfm);

      final double mag_qfm = QuaternionM4F.magnitude(qfm);
      final double mag_qaa = QuaternionM4F.magnitude(qaa);

      System.out.println("mag_qfm : " + mag_qfm);
      System.out.println("mag_qaa : " + mag_qaa);
      System.out.println("axis    : " + axis);
      System.out.println("angle   : " + angle);
      System.out.println("m       : ");
      System.out.println(m);
      System.out.println("qfm     : " + qfm);
      System.out.println("qaa     : " + qaa);
      System.out.println("--");

      /**
       * The resulting quaternions are unit quaternions.
       */

      eq = AlmostEqualDouble.almostEqual(context_d, mag_qfm, 1.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, mag_qaa, 1.0);
      Assert.assertTrue(eq);

      /**
       * The resulting quaternions match.
       */

      if (QuaternionM4F.almostEqual(context_f, qfm, qaa)) {
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionM4F.isNegationOf(context_f, qfm, qaa)) {
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @Override @Test public void testMakeFromMatrix4x4Exhaustive()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F qfm = new QuaternionM4F();
    final QuaternionM4F qaa = new QuaternionM4F();
    final MatrixM4x4F m = new MatrixM4x4F();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float degrees = (float) ((2 * Math.random() * 360.0f) - 360.0f);
      final float angle = (float) Math.toRadians(degrees);
      final float axis_x = (float) Math.random();
      final float axis_y = (float) Math.random();
      final float axis_z = (float) Math.random();
      final VectorM3F axis = new VectorM3F(axis_x, axis_y, axis_z);
      VectorM3F.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      QuaternionM4F.makeFromAxisAngle(axis, angle, qaa);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM4x4F.makeRotationInto(angle, axis, m);
      QuaternionM4F.makeFromRotationMatrix4x4(m, qfm);

      final double mag_qfm = QuaternionM4F.magnitude(qfm);
      final double mag_qaa = QuaternionM4F.magnitude(qaa);

      System.out.println("mag_qfm : " + mag_qfm);
      System.out.println("mag_qaa : " + mag_qaa);
      System.out.println("axis    : " + axis);
      System.out.println("angle   : " + angle);
      System.out.println("m       : ");
      System.out.println(m);
      System.out.println("qfm     : " + qfm);
      System.out.println("qaa     : " + qaa);
      System.out.println("--");

      /**
       * The resulting quaternions are unit quaternions.
       */

      eq = AlmostEqualDouble.almostEqual(context_d, mag_qfm, 1.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, mag_qaa, 1.0);
      Assert.assertTrue(eq);

      /**
       * The resulting quaternions match.
       */

      if (QuaternionM4F.almostEqual(context, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionM4F.isNegationOf(context, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @Override @Test public void testMakeMatrix3x3_45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_X;

    MatrixM3x3F.makeRotationInto(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix3x3_45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_Y;

    MatrixM3x3F.makeRotationInto(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix3x3_45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_Z;

    MatrixM3x3F.makeRotationInto(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix3x3_Identity()
  {
    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F m = new MatrixM3x3F();

    QuaternionM4F.makeRotationMatrix3x3(q, m);

    Assert.assertTrue(1.0f == m.getRowColumnF(0, 0));
    Assert.assertTrue(0.0f == m.getRowColumnF(0, 1));
    Assert.assertTrue(0.0f == m.getRowColumnF(0, 2));

    Assert.assertTrue(0.0f == m.getRowColumnF(1, 0));
    Assert.assertTrue(1.0f == m.getRowColumnF(1, 1));
    Assert.assertTrue(0.0f == m.getRowColumnF(1, 2));

    Assert.assertTrue(0.0f == m.getRowColumnF(2, 0));
    Assert.assertTrue(0.0f == m.getRowColumnF(2, 1));
    Assert.assertTrue(1.0f == m.getRowColumnF(2, 2));
  }

  @Override @Test public void testMakeMatrix3x3_Minus45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_X;

    MatrixM3x3F.makeRotationInto(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix3x3_Minus45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_Y;

    MatrixM3x3F.makeRotationInto(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix3x3_Minus45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_Z;

    MatrixM3x3F.makeRotationInto(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix4x4_45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_X;

    MatrixM4x4F.makeRotationInto(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix4x4_45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_Y;

    MatrixM4x4F.makeRotationInto(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix4x4_45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_Z;

    MatrixM4x4F.makeRotationInto(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix4x4_Identity()
  {
    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F m = new MatrixM4x4F();

    QuaternionM4F.makeRotationMatrix4x4(q, m);

    Assert.assertTrue(1.0f == m.getRowColumnF(0, 0));
    Assert.assertTrue(0.0f == m.getRowColumnF(0, 1));
    Assert.assertTrue(0.0f == m.getRowColumnF(0, 2));
    Assert.assertTrue(0.0f == m.getRowColumnF(0, 3));

    Assert.assertTrue(0.0f == m.getRowColumnF(1, 0));
    Assert.assertTrue(1.0f == m.getRowColumnF(1, 1));
    Assert.assertTrue(0.0f == m.getRowColumnF(1, 2));
    Assert.assertTrue(0.0f == m.getRowColumnF(1, 3));

    Assert.assertTrue(0.0f == m.getRowColumnF(2, 0));
    Assert.assertTrue(0.0f == m.getRowColumnF(2, 1));
    Assert.assertTrue(1.0f == m.getRowColumnF(2, 2));
    Assert.assertTrue(0.0f == m.getRowColumnF(2, 3));

    Assert.assertTrue(0.0f == m.getRowColumnF(3, 0));
    Assert.assertTrue(0.0f == m.getRowColumnF(3, 1));
    Assert.assertTrue(0.0f == m.getRowColumnF(3, 2));
    Assert.assertTrue(1.0f == m.getRowColumnF(3, 3));
  }

  @Override @Test public void testMakeMatrix4x4_Minus45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_X;

    MatrixM4x4F.makeRotationInto(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix4x4_Minus45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_Y;

    MatrixM4x4F.makeRotationInto(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix4x4_Minus45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3FType axis = QuaternionM4FTest.AXIS_Z;

    MatrixM4x4F.makeRotationInto(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMultiply()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);
    final QuaternionM4F qx = new QuaternionM4F();
    final QuaternionM4F qxr =
      QuaternionM4F.makeFromAxisAngle(axis_x, Math.toRadians(45), qx);
    final QuaternionM4F qy = new QuaternionM4F();
    final QuaternionM4F qyr =
      QuaternionM4F.makeFromAxisAngle(axis_y, Math.toRadians(45), qy);

    Assert.assertSame(qx, qxr);
    Assert.assertSame(qy, qyr);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final QuaternionM4F qr = new QuaternionM4F();
    QuaternionM4F.multiply(qy, qx, qr);

    System.out.println("qx : " + qx);
    System.out.println("qy : " + qy);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getXF(),
      0.3535533905932738f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getYF(),
      0.3535533905932738f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getZF(),
      -0.14644660940672624f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getWF(),
      0.8535533905932737f));
  }

  @Override @Test public void testMultiplyInPlace()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);

    final QuaternionM4F qx = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_x, (float) Math.toRadians(45), qx);
    final QuaternionM4F qy = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_y, (float) Math.toRadians(45), qy);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final QuaternionM4F qr = new QuaternionM4F();
    QuaternionM4F.multiplyInPlace(qr, qy);
    QuaternionM4F.multiplyInPlace(qr, qx);

    System.out.println("qx : " + qx);
    System.out.println("qy : " + qy);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getXF(),
      0.3535533983204287f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getYF(),
      0.3535533983204287f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getZF(),
      -0.14644661713388138f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getWF(),
      0.8535533828661185f));
  }

  @Override @Test public void testMultiplyInPlaceOther()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorI3F axis_z = new VectorI3F(0.0f, 0.0f, 1.0f);

    final QuaternionM4F qx = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_x, (float) Math.toRadians(45), qx);
    final QuaternionM4F qy = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_y, (float) Math.toRadians(45), qy);
    final QuaternionM4F qz = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_z, (float) Math.toRadians(45), qz);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis,
     * followed by a 45 degree rotation around the global Z axis.
     */

    final QuaternionM4F qr = new QuaternionM4F();
    QuaternionM4F.multiplyInPlace(qr, qz);
    QuaternionM4F.multiplyInPlace(qr, qy);
    QuaternionM4F.multiplyInPlace(qr, qx);

    System.out.println("qx : " + qx);
    System.out.println("qy : " + qy);
    System.out.println("qz : " + qz);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getXF(),
      0.1913417153164435f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getYF(),
      0.4619397784426109f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getZF(),
      0.1913417153164436f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getWF(),
      0.8446231923478736f));
  }

  @Override @Test public void testMultiplyOther()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorI3F axis_z = new VectorI3F(0.0f, 0.0f, 1.0f);

    final QuaternionM4F qx = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_x, (float) Math.toRadians(45), qx);
    final QuaternionM4F qy = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_y, (float) Math.toRadians(45), qy);
    final QuaternionM4F qz = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_z, (float) Math.toRadians(45), qz);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis,
     * followed by a 45 degree rotation around the global Z axis.
     */

    final QuaternionM4F qr = new QuaternionM4F();
    QuaternionM4F.multiply(qy, qx, qr);
    QuaternionM4F.multiply(qz, qr, qr);

    System.out.println("qx : " + qx);
    System.out.println("qy : " + qy);
    System.out.println("qz : " + qz);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getXF(),
      0.1913417153164435f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getYF(),
      0.4619397784426109f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getZF(),
      0.1913417153164436f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      qr.getWF(),
      0.8446231923478736f));
  }

  @Override @Test public void testNegation()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) ((Math.random() * 2) - Math.random());
      final float y = (float) ((Math.random() * 2) - Math.random());
      final float z = (float) ((Math.random() * 2) - Math.random());
      final float w = (float) ((Math.random() * 2) - Math.random());
      final QuaternionM4F qi = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F qn = new QuaternionM4F(-x, -y, -z, -w);
      final QuaternionM4F qr = new QuaternionM4F();

      QuaternionM4F.negate(qi, qr);

      System.out.println("qi : " + qi);
      System.out.println("qn : " + qn);
      System.out.println("qr : " + qr);
      System.out.println("--");

      Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qr));
      Assert.assertTrue(QuaternionM4F.almostEqual(context, qn, qr));
    }
  }

  @Override @Test public void testNegationCases()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F qi = new QuaternionM4F(1, 2, 3, 4);
    final QuaternionM4F qnx = new QuaternionM4F(-1, 2, 3, 4);
    final QuaternionM4F qny = new QuaternionM4F(-1, -2, 3, 4);
    final QuaternionM4F qnz = new QuaternionM4F(-1, -2, -3, 4);
    final QuaternionM4F qnw = new QuaternionM4F(-1, -2, -3, -4);

    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qi) == false);
    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qnx) == false);
    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qny) == false);
    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qnz) == false);
    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qnw) == true);

    QuaternionM4F.negateInPlace(qnw);
    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qnw) == false);
    Assert.assertTrue(QuaternionM4F.almostEqual(context, qi, qnw));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final QuaternionM4F v0 = new QuaternionM4F(8.0f, 0.0f, 0.0f, 0.0f);
    final QuaternionM4F out = new QuaternionM4F();
    final QuaternionM4F vr = QuaternionM4F.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = QuaternionM4F.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F qr = new QuaternionM4F();
    final QuaternionM4F q = new QuaternionM4F(0, 0, 0, 0);
    QuaternionM4F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getYF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getZF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getWF()));
  }

  @Override @Test public void testScaleMutation()
  {
    final QuaternionM4F out = new QuaternionM4F();
    final QuaternionM4F v0 = new QuaternionM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(out.getZF() == 0.0f);
    Assert.assertTrue(out.getWF() == 1.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v0.getZF() == 1.0f);
    Assert.assertTrue(v0.getWF() == 1.0f);

    final QuaternionM4F ov0 = QuaternionM4F.scale(v0, 2.0, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(out.getZF() == 2.0);
    Assert.assertTrue(out.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v0.getZF() == 1.0f);
    Assert.assertTrue(v0.getWF() == 1.0f);

    final QuaternionM4F ov1 = QuaternionM4F.scaleInPlace(v0, 2.0);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0);
    Assert.assertTrue(ov1.getYF() == 2.0);
    Assert.assertTrue(ov1.getZF() == 2.0);
    Assert.assertTrue(ov1.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 2.0);
    Assert.assertTrue(v0.getYF() == 2.0);
    Assert.assertTrue(v0.getZF() == 2.0);
    Assert.assertTrue(v0.getWF() == 2.0);
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v = new QuaternionM4F(x, y, z, w);

      final QuaternionM4F vr = new QuaternionM4F();

      QuaternionM4F.scale(v, 1.0f, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        v.getXF(),
        vr.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        v.getYF(),
        vr.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        v.getZF(),
        vr.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        v.getWF(),
        vr.getWF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();
        final float orig_z = v.getZF();
        final float orig_w = v.getWF();

        QuaternionM4F.scaleInPlace(v, 1.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v.getXF(),
          orig_x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v.getYF(),
          orig_y));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v.getZF(),
          orig_z));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v.getWF(),
          orig_w));
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v = new QuaternionM4F(x, y, z, w);

      final QuaternionM4F vr = new QuaternionM4F();

      QuaternionM4F.scale(v, 0.0f, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getXF(),
        0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getYF(),
        0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getZF(),
        0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getWF(),
        0.0f));

      {
        QuaternionM4F.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v.getXF(),
          0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v.getYF(),
          0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v.getZF(),
          0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v.getWF(),
          0.0f));
      }
    }
  }

  @Override @Test public void testString()
  {
    final QuaternionM4F v = new QuaternionM4F(1.0f, 2.0f, 3.0f, 4.0f);
    System.out.println(v);
    Assert.assertTrue(v.toString().equals("[QuaternionM4F 1.0 2.0 3.0 4.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v0 = new QuaternionM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v1 = new QuaternionM4F(x1, y1, z1, w1);

      final QuaternionM4F vr0 = new QuaternionM4F();
      QuaternionM4F.subtract(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr0.getXF(),
        v0.getXF() - v1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr0.getYF(),
        v0.getYF() - v1.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr0.getZF(),
        v0.getZF() - v1.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr0.getWF(),
        v0.getWF() - v1.getWF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        final float orig_w = v0.getWF();
        QuaternionM4F.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v0.getXF(),
          orig_x - v1.getXF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v0.getYF(),
          orig_y - v1.getYF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v0.getZF(),
          orig_z - v1.getZF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          context,
          v0.getWF(),
          orig_w - v1.getWF()));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final QuaternionM4F out = new QuaternionM4F();
    final QuaternionM4F v0 = new QuaternionM4F(1.0f, 1.0f, 1.0f, 1.0f);
    final QuaternionM4F v1 = new QuaternionM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(out.getZF() == 0.0f);
    Assert.assertTrue(out.getWF() == 1.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v0.getZF() == 1.0f);
    Assert.assertTrue(v0.getWF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
    Assert.assertTrue(v1.getZF() == 1.0f);
    Assert.assertTrue(v1.getWF() == 1.0f);

    final QuaternionM4F ov0 = QuaternionM4F.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(out.getZF() == 0.0f);
    Assert.assertTrue(out.getWF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v0.getZF() == 1.0f);
    Assert.assertTrue(v0.getWF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
    Assert.assertTrue(v1.getZF() == 1.0f);
    Assert.assertTrue(v1.getWF() == 1.0f);

    final QuaternionM4F ov1 = QuaternionM4F.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 0.0f);
    Assert.assertTrue(ov1.getYF() == 0.0f);
    Assert.assertTrue(ov1.getZF() == 0.0f);
    Assert.assertTrue(ov1.getWF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 0.0f);
    Assert.assertTrue(v0.getYF() == 0.0f);
    Assert.assertTrue(v0.getZF() == 0.0f);
    Assert.assertTrue(v0.getWF() == 0.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
    Assert.assertTrue(v1.getZF() == 1.0f);
    Assert.assertTrue(v1.getWF() == 1.0f);
  }
}