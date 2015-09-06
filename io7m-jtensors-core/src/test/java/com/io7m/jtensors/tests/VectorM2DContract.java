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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jtensors.VectorM2D;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM2DContract extends VectorM2Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2D v = this.newVectorM2D(x, y);

      final VectorM2D vr = this.newVectorM2D();
      VectorM2D.absolute(v, vr);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getXD()), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getYD()), vr.getYD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();

        VectorM2D.absoluteInPlace(v);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, Math.abs(orig_x), v.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, Math.abs(orig_y), v.getYD()));
      }
    }
  }

  protected abstract VectorM2D newVectorM2D();

  protected abstract VectorM2D newVectorM2D(
    final double x,
    final double y);

  @Override @Test public void testAbsoluteMutation()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2D v = this.newVectorM2D(x, y);

      final double orig_x = v.getXD();
      final double orig_y = v.getYD();

      VectorM2D.absoluteInPlace(v);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_x), v.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_y), v.getYD()));

    }
  }

  @Override @Test public void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 20000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final VectorM2D v0 = this.newVectorM2D(x0, y0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final VectorM2D v1 = this.newVectorM2D(x1, y1);

      final VectorM2D vr0 = this.newVectorM2D();
      VectorM2D.add(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + v1.getYD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        VectorM2D.addInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + v1.getYD()));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM2D out = this.newVectorM2D();
    final VectorM2D v0 = this.newVectorM2D(1.0, 1.0);
    final VectorM2D v1 = this.newVectorM2D(1.0, 1.0);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);

    final VectorM2D ov0 = VectorM2D.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);

    final VectorM2D ov1 = VectorM2D.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
  }

  @Override @Test public void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 20000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final VectorM2D v0 = this.newVectorM2D(x0, y0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final VectorM2D v1 = this.newVectorM2D(x1, y1);

      final double r = Math.random() * max;

      final VectorM2D vr0 = this.newVectorM2D();
      VectorM2D.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + (v1.getXD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + (v1.getYD() * r)));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        VectorM2D.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + (v1.getXD() * r)));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + (v1.getYD() * r)));
      }
    }
  }

  @Override @Test public void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = Math.random();
    final double y = x + 1.0f;
    final double z = y + 1.0f;
    final double w = z + 1.0f;
    final double q = w + 1.0f;

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      final VectorM2D m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      final VectorM2D m1 = this.newVectorM2D(x, q);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      final VectorM2D m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      final VectorM2D m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      final VectorM2D m1 = this.newVectorM2D(q, q);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      final VectorM2D m1 = this.newVectorM2D(x, q);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v0 = this.newVectorM2D(x0, y0);
      final VectorM2D v1 = this.newVectorM2D(x0, y0);
      final VectorM2D v2 = this.newVectorM2D(x0, y0);

      Assert.assertTrue(VectorM2D.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM2D.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM2D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final double x = Math.random();
      final double y = Math.random();
      final VectorM2D v0 = this.newVectorM2D(x, y);
      final VectorM2D v1 = this.newVectorM2D(y, -x);
      VectorM2D.normalizeInPlace(v0);
      VectorM2D.normalizeInPlace(v1);
      final double angle = VectorM2D.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }

    {
      final double x = Math.random();
      final double y = Math.random();
      final VectorM2D v0 = this.newVectorM2D(x, y);
      final VectorM2D v1 = this.newVectorM2D(-y, x);
      VectorM2D.normalizeInPlace(v0);
      VectorM2D.normalizeInPlace(v1);
      final double angle = VectorM2D.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorM2D v = this.newVectorM2D(3.0f, 5.0f);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final VectorM2D maximum = this.newVectorM2D(max_x, max_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2D v = this.newVectorM2D(x, y);

      final VectorM2D vr = this.newVectorM2D();
      final VectorM2D vo = VectorM2D.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());

      {
        final VectorM2D vr0 = VectorM2D.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final VectorM2D minimum = this.newVectorM2D(min_x, min_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2D v = this.newVectorM2D(x, y);

      final VectorM2D vr = this.newVectorM2D();
      final VectorM2D vo = VectorM2D.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());

      {
        final VectorM2D vr0 = VectorM2D.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final VectorM2D minimum = this.newVectorM2D(min_x, min_y);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final VectorM2D maximum = this.newVectorM2D(max_x, max_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = this.newVectorM2D(x, y);

      final VectorM2D vr = this.newVectorM2D();
      final VectorM2D vo = VectorM2D.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());

      {
        final VectorM2D vr0 =
          VectorM2D.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = this.newVectorM2D(x, y);

      final VectorM2D vr = this.newVectorM2D();
      VectorM2D.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getYD() <= maximum);

      {
        VectorM2D.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getYD() <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2D v = this.newVectorM2D(x, y);

      final VectorM2D vr = this.newVectorM2D();
      VectorM2D.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() >= minimum);

      {
        VectorM2D.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = this.newVectorM2D(x, y);

      final VectorM2D vr = this.newVectorM2D();
      VectorM2D.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getYD() >= minimum);

      {
        VectorM2D.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getYD() >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM2D vb = this.newVectorM2D(5, 6);
    final VectorM2D va = this.newVectorM2D(1, 2);

    Assert.assertFalse(va.getXD() == vb.getXD());
    Assert.assertFalse(va.getYD() == vb.getYD());

    VectorM2D.copy(va, vb);

    Assert.assertTrue(va.getXD() == vb.getXD());
    Assert.assertTrue(va.getYD() == vb.getYD());
  }

  @Override @Test public void testCopy2Correct()
  {
    final VectorM2D v0 = this.newVectorM2D(
      Math.random() * Double.MAX_VALUE, Math.random() * Double.MAX_VALUE);
    final VectorM2D v1 = this.newVectorM2D();

    v1.copyFrom2D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0f);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0f);
  }

  @Override @Test public void testDefault00()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    Assert.assertTrue(
      VectorM2D.almostEqual(
        ec, this.newVectorM2D(), this.newVectorM2D(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2D.ContextVM2D c = new VectorM2D.ContextVM2D();
    final VectorM2D v0 = this.newVectorM2D(0.0, 1.0);
    final VectorM2D v1 = this.newVectorM2D(0.0, 0.0);
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, VectorM2D.distance(c, v0, v1), 1.0));
  }

  @Override @Test public void testDistanceOrdering()
  {
    final VectorM2D.ContextVM2D c = new VectorM2D.ContextVM2D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v0 = this.newVectorM2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v1 = this.newVectorM2D(x1, y1);

      Assert.assertTrue(VectorM2D.distance(c, v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM2D v0 = this.newVectorM2D(10.0f, 10.0f);
    final VectorM2D v1 = this.newVectorM2D(10.0f, 10.0f);

    {
      final double p = VectorM2D.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorM2D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorM2D.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final VectorM2D vpx = this.newVectorM2D(1.0f, 0.0f);
    final VectorM2D vmx = this.newVectorM2D(-1.0f, 0.0f);

    final VectorM2D vpy = this.newVectorM2D(0.0f, 1.0f);
    final VectorM2D vmy = this.newVectorM2D(0.0f, -1.0f);

    Assert.assertTrue(VectorM2D.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorM2D.dotProduct(vmx, vmy) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final VectorM2D q = this.newVectorM2D(x, y);
      final double dp = VectorM2D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM2D v0 = this.newVectorM2D(10.0, 10.0);

    {
      final double p = VectorM2D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorM2D.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM2D m0 = this.newVectorM2D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM2D m0 = this.newVectorM2D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2D m0 = this.newVectorM2D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2D m0 = this.newVectorM2D();
      final VectorM2D m1 = this.newVectorM2D();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final double x = Math.random();
    final double y = x + 1.0f;
    final double z = y + 1.0f;
    final double w = z + 1.0f;
    final double q = w + 1.0f;

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      final VectorM2D m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      final VectorM2D m1 = this.newVectorM2D(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      final VectorM2D m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      final VectorM2D m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      final VectorM2D m1 = this.newVectorM2D(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2D m0 = this.newVectorM2D(x, y);
      final VectorM2D m1 = this.newVectorM2D(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM2D m0 = this.newVectorM2D();
    final VectorM2D m1 = this.newVectorM2D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM2D m0 = this.newVectorM2D();
      final VectorM2D m1 = this.newVectorM2D();
      m1.setXD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM2D m0 = this.newVectorM2D();
      final VectorM2D m1 = this.newVectorM2D();
      m1.setYD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM2D v0 = this.newVectorM2D(1.0f, 2.0f);
    final VectorM2D v1 = new VectorM2D(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final VectorM2D.ContextVM2D c = new VectorM2D.ContextVM2D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v0 = this.newVectorM2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v1 = this.newVectorM2D(x1, y1);

      final VectorM2D vr0 = this.newVectorM2D();
      final VectorM2D vr1 = this.newVectorM2D();
      VectorM2D.interpolateLinear(c, v0, v1, 0.0, vr0);
      VectorM2D.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getXD(), vr0.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getYD(), vr0.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getXD(), vr1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getYD(), vr1.getYD()));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = this.newVectorM2D(x, y);

      final double m = VectorM2D.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorM2D v = this.newVectorM2D(x, y);

      final VectorM2D vr = this.newVectorM2D();
      VectorM2D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM2D.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2D v = this.newVectorM2D(0.0, 0.0);
    final VectorM2D vr = VectorM2D.normalizeInPlace(v);
    final double m = VectorM2D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2D v = this.newVectorM2D(1.0, 0.0);
    final double m = VectorM2D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM2D v = this.newVectorM2D(8.0, 0.0);

    {
      final double p = VectorM2D.dotProduct(v, v);
      final double q = VectorM2D.magnitudeSquared(v);
      final double r = VectorM2D.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2D v = this.newVectorM2D(0.0, 0.0);
    final double m = VectorM2D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final VectorM2D v0 = this.newVectorM2D(8.0, 0.0);
    final VectorM2D out = this.newVectorM2D();
    final VectorM2D vr = VectorM2D.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM2D.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2D qr = this.newVectorM2D();
    final VectorM2D q = this.newVectorM2D(0, 0);
    VectorM2D.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getYD()));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorM2D v0 = this.newVectorM2D(0, 1);
    final VectorM2D v1 = this.newVectorM2D(0.5f, 0.5f);
    final VectorM2D v0_out = this.newVectorM2D();
    final VectorM2D v1_out = this.newVectorM2D();

    final VectorM2D.ContextVM2D c = new VectorM2D.ContextVM2D();
    VectorM2D.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM2D(0, 1), v0_out);
    Assert.assertEquals(this.newVectorM2D(1, 0), v1_out);
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    final VectorM2D v0 = this.newVectorM2D(0f, 1f);
    final VectorM2D v1 = this.newVectorM2D(0.5f, 0.5f);
    final VectorM2D v0_out = this.newVectorM2D();
    final VectorM2D v1_out = this.newVectorM2D();

    final VectorM2D.ContextVM2D c = new VectorM2D.ContextVM2D();
    VectorM2D.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM2D(0, 1), v0);
    Assert.assertEquals(this.newVectorM2D(1, 0), v1);
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM2D p = this.newVectorM2D(1.0, 0.0);
      final VectorM2D q = this.newVectorM2D(0.0, 1.0);
      final VectorM2D r = this.newVectorM2D();
      final VectorM2D u = VectorM2D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2D.magnitude(u) == 0.0);
    }

    {
      final VectorM2D p = this.newVectorM2D(-1.0, 0.0);
      final VectorM2D q = this.newVectorM2D(0.0, 1.0);
      final VectorM2D r = this.newVectorM2D();
      final VectorM2D u = VectorM2D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2D.magnitude(u) == 0.0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM2D out = this.newVectorM2D();
    final VectorM2D v0 = this.newVectorM2D(1.0, 1.0);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);

    final VectorM2D ov0 = VectorM2D.scale(v0, 2.0, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);

    final VectorM2D ov1 = VectorM2D.scaleInPlace(v0, 2.0);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = this.newVectorM2D(x, y);

      final VectorM2D vr = this.newVectorM2D();

      VectorM2D.scale(v, 1.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, v.getXD(), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, v.getYD(), vr.getYD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();

        VectorM2D.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), orig_x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), orig_y));
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = this.newVectorM2D(x, y);

      final VectorM2D vr = this.newVectorM2D();

      VectorM2D.scale(v, 0.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getXD(), 0.0));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getYD(), 0.0));

      {
        VectorM2D.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM2D v = this.newVectorM2D(0.0, 1.0);
    Assert.assertTrue(v.toString().equals("[VectorM2D 0.0 1.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v0 = this.newVectorM2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v1 = this.newVectorM2D(x1, y1);

      final VectorM2D vr0 = this.newVectorM2D();
      VectorM2D.subtract(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() - v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() - v1.getYD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        VectorM2D.subtractInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x - v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y - v1.getYD()));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM2D out = this.newVectorM2D();
    final VectorM2D v0 = this.newVectorM2D(1.0, 1.0);
    final VectorM2D v1 = this.newVectorM2D(1.0, 1.0);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);

    final VectorM2D ov0 = VectorM2D.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);

    final VectorM2D ov1 = VectorM2D.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 0.0);
    Assert.assertTrue(ov1.getYD() == 0.0);
    Assert.assertTrue(v0.getXD() == 0.0);
    Assert.assertTrue(v0.getYD() == 0.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
  }
}
