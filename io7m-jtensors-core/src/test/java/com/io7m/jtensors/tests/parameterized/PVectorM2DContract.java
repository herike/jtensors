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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jtensors.parameterized.PVector2DType;
import com.io7m.jtensors.parameterized.PVectorM2D;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM2DContract<T, V extends PVector2DType<T>>
{
  protected static double getRandomSmall()
  {
    return PVectorM2DContract.getRandom() * Double.MIN_VALUE;
  }

  protected static double getRandom()
  {
    return Math.random();
  }

  protected static double getRandomLarge()
  {
    return PVectorM2DContract.getRandom() * Double.MAX_VALUE;
  }

  protected abstract V newVectorM2D(V v);

  protected abstract V newVectorM2D();

  protected abstract V newVectorM2D(
    final double x1,
    final double y1);

  @Test public final void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = PVectorM2DContract.getRandomSmall();
      final double y = PVectorM2DContract.getRandomSmall();
      final V v = this.newVectorM2D(x, y);

      final V vr = this.newVectorM2D();
      PVectorM2D.absolute(v, vr);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getXD()), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getYD()), vr.getYD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();

        PVectorM2D.absoluteInPlace(v);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, Math.abs(orig_x), v.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, Math.abs(orig_y), v.getYD()));
      }
    }
  }

  @Test public final void testAbsoluteMutation()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = PVectorM2DContract.getRandomSmall();
      final double y = PVectorM2DContract.getRandomSmall();
      final V v = this.newVectorM2D(x, y);

      final double orig_x = v.getXD();
      final double orig_y = v.getYD();

      PVectorM2D.absoluteInPlace(v);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_x), v.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_y), v.getYD()));

    }
  }

  @Test public final void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 20000.0;
      final double x0 = PVectorM2DContract.getRandom() * max;
      final double y0 = PVectorM2DContract.getRandom() * max;
      final V v0 = this.newVectorM2D(x0, y0);

      final double x1 = PVectorM2DContract.getRandom() * max;
      final double y1 = PVectorM2DContract.getRandom() * max;
      final V v1 = this.newVectorM2D(x1, y1);

      final V vr0 = this.newVectorM2D();
      PVectorM2D.add(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + v1.getYD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        PVectorM2D.addInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + v1.getYD()));
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final V out = this.newVectorM2D();
    final V v0 = this.newVectorM2D(1.0, 1.0);
    final V v1 = this.newVectorM2D(1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);

    final V ov0 = PVectorM2D.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), 0.0);
    Assert.assertEquals(2.0, out.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);

    final V ov1 = PVectorM2D.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), 0.0);
    Assert.assertEquals(2.0, ov1.getYD(), 0.0);
    Assert.assertEquals(2.0, v0.getXD(), 0.0);
    Assert.assertEquals(2.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
  }

  @Test public final void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 20000.0;
      final double x0 = PVectorM2DContract.getRandom() * max;
      final double y0 = PVectorM2DContract.getRandom() * max;
      final V v0 = this.newVectorM2D(x0, y0);

      final double x1 = PVectorM2DContract.getRandom() * max;
      final double y1 = PVectorM2DContract.getRandom() * max;
      final V v1 = this.newVectorM2D(x1, y1);

      final double r = PVectorM2DContract.getRandom() * max;

      final V vr0 = this.newVectorM2D();
      PVectorM2D.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + (v1.getXD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + (v1.getYD() * r)));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        PVectorM2D.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + (v1.getXD() * r)));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + (v1.getYD() * r)));
      }
    }
  }

  @Test public final void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = PVectorM2DContract.getRandom();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final V m0 = this.newVectorM2D(x, y);
      final V m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(PVectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM2D(x, y);
      final V m1 = this.newVectorM2D(x, q);
      Assert.assertFalse(PVectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM2D(x, y);
      final V m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(PVectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM2D(x, y);
      final V m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(PVectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM2D(x, y);
      final V m1 = this.newVectorM2D(q, q);
      Assert.assertFalse(PVectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM2D(x, y);
      final V m1 = this.newVectorM2D(x, q);
      Assert.assertFalse(PVectorM2D.almostEqual(ec, m0, m1));
    }
  }

  @Test public final void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = PVectorM2DContract.getRandomLarge();
      final double y0 = PVectorM2DContract.getRandomLarge();
      final V v0 = this.newVectorM2D(x0, y0);
      final V v1 = this.newVectorM2D(x0, y0);
      final V v2 = this.newVectorM2D(x0, y0);

      Assert.assertTrue(PVectorM2D.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorM2D.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorM2D.almostEqual(ec, v0, v2));
    }
  }

  @Test public final void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final double x = PVectorM2DContract.getRandom();
      final double y = PVectorM2DContract.getRandom();
      final V v0 = this.newVectorM2D(x, y);
      final V v1 = this.newVectorM2D(y, -x);
      PVectorM2D.normalizeInPlace(v0);
      PVectorM2D.normalizeInPlace(v1);
      final double angle = PVectorM2D.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90.0)));
    }

    {
      final double x = PVectorM2DContract.getRandom();
      final double y = PVectorM2DContract.getRandom();
      final V v0 = this.newVectorM2D(x, y);
      final V v1 = this.newVectorM2D(-y, x);
      PVectorM2D.normalizeInPlace(v0);
      PVectorM2D.normalizeInPlace(v1);
      final double angle = PVectorM2D.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90.0)));
    }
  }

  @Test public final void testCheckInterface()
  {
    final V v = this.newVectorM2D(3.0, 5.0);

    Assert.assertEquals(v.getXD(), v.getXD(), 0.0);
    Assert.assertEquals(v.getYD(), v.getYD(), 0.0);
  }

  @Test public final void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = PVectorM2DContract.getRandomSmall();
      final double max_y = PVectorM2DContract.getRandomSmall();
      final V maximum = this.newVectorM2D(max_x, max_y);

      final double x = PVectorM2DContract.getRandomSmall();
      final double y = PVectorM2DContract.getRandomSmall();
      final V v = this.newVectorM2D(x, y);

      final V vr = this.newVectorM2D();
      final V vo = PVectorM2D.clampMaximumByPVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());

      {
        final V vr0 = PVectorM2D.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
      }
    }
  }

  @Test public final void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = PVectorM2DContract.getRandomLarge();
      final double min_y = PVectorM2DContract.getRandomLarge();
      final V minimum = this.newVectorM2D(min_x, min_y);

      final double x = PVectorM2DContract.getRandomSmall();
      final double y = PVectorM2DContract.getRandomSmall();
      final V v = this.newVectorM2D(x, y);

      final V vr = this.newVectorM2D();
      final V vo = PVectorM2D.clampMinimumByPVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());

      {
        final V vr0 = PVectorM2D.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
      }
    }
  }

  @Test public final void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = PVectorM2DContract.getRandomSmall();
      final double min_y = PVectorM2DContract.getRandomSmall();
      final V minimum = this.newVectorM2D(min_x, min_y);

      final double max_x = PVectorM2DContract.getRandomLarge();
      final double max_y = PVectorM2DContract.getRandomLarge();
      final V maximum = this.newVectorM2D(max_x, max_y);

      final double x = PVectorM2DContract.getRandomSmall();
      final double y = PVectorM2DContract.getRandomLarge();
      final V v = this.newVectorM2D(x, y);

      final V vr = this.newVectorM2D();
      final V vo = PVectorM2D.clampByPVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());

      {
        final V vr0 = PVectorM2D.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
      }
    }
  }

  @Test public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = PVectorM2DContract.getRandomSmall();

      final double x = PVectorM2DContract.getRandomLarge();
      final double y = PVectorM2DContract.getRandomLarge();
      final V v = this.newVectorM2D(x, y);

      final V vr = this.newVectorM2D();
      PVectorM2D.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getYD() <= maximum);

      {
        PVectorM2D.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getYD() <= maximum);
      }
    }
  }

  @Test public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = PVectorM2DContract.getRandomLarge();

      final double x = PVectorM2DContract.getRandomSmall();
      final double y = PVectorM2DContract.getRandomSmall();
      final V v = this.newVectorM2D(x, y);

      final V vr = this.newVectorM2D();
      PVectorM2D.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() >= minimum);

      {
        PVectorM2D.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() >= minimum);
      }
    }
  }

  @Test public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = PVectorM2DContract.getRandomSmall();
      final double maximum = PVectorM2DContract.getRandomLarge();

      final double x = PVectorM2DContract.getRandomSmall();
      final double y = PVectorM2DContract.getRandomLarge();
      final V v = this.newVectorM2D(x, y);

      final V vr = this.newVectorM2D();
      PVectorM2D.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getYD() >= minimum);

      {
        PVectorM2D.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getYD() >= minimum);
      }
    }
  }

  @Test public final void testCopy()
  {
    final V vb = this.newVectorM2D(5.0, 6.0);
    final V va = this.newVectorM2D(1.0, 2.0);

    Assert.assertNotEquals(vb.getXD(), va.getXD(), 0.0);
    Assert.assertNotEquals(vb.getYD(), va.getYD(), 0.0);

    PVectorM2D.copy(va, vb);

    Assert.assertEquals(vb.getXD(), va.getXD(), 0.0);
    Assert.assertEquals(vb.getYD(), va.getYD(), 0.0);
  }

  @Test public final void testCopy2Correct()
  {
    final V v0 = this.newVectorM2D(
      PVectorM2DContract.getRandomLarge(), PVectorM2DContract.getRandomLarge());
    final V v1 = this.newVectorM2D();
    final V v2 = this.newVectorM2D();

    v1.copyFrom2D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0);

    v2.copyFromTyped2D(v1);

    Assert.assertEquals(v0.getXD(), v2.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v2.getYD(), 0.0);
  }

  @Test public final void testDefault00()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    Assert.assertTrue(
      PVectorM2D.almostEqual(
        ec, this.newVectorM2D(), this.newVectorM2D(0.0, 0.0)));
  }

  @Test public final void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final PVectorM2D.ContextPVM2D c = new PVectorM2D.ContextPVM2D();

    final V v0 = this.newVectorM2D(0.0, 1.0);
    final V v1 = this.newVectorM2D(0.0, 0.0);
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, PVectorM2D.distance(c, v0, v1), 1.0));
  }

  @Test public final void testDistanceOrdering()
  {
    final PVectorM2D.ContextPVM2D c = new PVectorM2D.ContextPVM2D();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = PVectorM2DContract.getRandomLarge();
      final double y0 = PVectorM2DContract.getRandomLarge();
      final V v0 = this.newVectorM2D(x0, y0);

      final double x1 = PVectorM2DContract.getRandomLarge();
      final double y1 = PVectorM2DContract.getRandomLarge();
      final V v1 = this.newVectorM2D(x1, y1);

      Assert.assertTrue(PVectorM2D.distance(c, v0, v1) >= 0.0);
    }
  }

  @Test public final void testDotProduct()
  {
    final V v0 = this.newVectorM2D(10.0, 10.0);
    final V v1 = this.newVectorM2D(10.0, 10.0);

    {
      final double p = PVectorM2D.dotProduct(v0, v1);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(10.0, v1.getXD(), 0.0);
      Assert.assertEquals(10.0, v1.getYD(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }

    {
      final double p = PVectorM2D.dotProduct(v0, v0);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }

    {
      final double p = PVectorM2D.dotProduct(v1, v1);
      Assert.assertEquals(10.0, v1.getXD(), 0.0);
      Assert.assertEquals(10.0, v1.getYD(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }
  }

  @Test public final void testDotProductPerpendicular()
  {
    final V vpx = this.newVectorM2D(1.0, 0.0);
    final V vmx = this.newVectorM2D(-1.0, 0.0);

    final V vpy = this.newVectorM2D(0.0, 1.0);
    final V vmy = this.newVectorM2D(0.0, -1.0);

    Assert.assertEquals(0.0, PVectorM2D.dotProduct(vpx, vpy), 0.0);
    Assert.assertEquals(0.0, PVectorM2D.dotProduct(vmx, vmy), 0.0);
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = PVectorM2DContract.getRandom();
      final double y = PVectorM2DContract.getRandom();
      final V q = this.newVectorM2D(x, y);
      final double dp = PVectorM2D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final V v0 = this.newVectorM2D(10.0, 10.0);

    {
      final double p = PVectorM2D.dotProduct(v0, v0);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }

    {
      final double p = PVectorM2D.magnitudeSquared(v0);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final V m0 = this.newVectorM2D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final V m0 = this.newVectorM2D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM2D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM2D();
      final V m1 = this.newVectorM2D();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final double x = PVectorM2DContract.getRandom();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final V m0 = this.newVectorM2D(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM2D(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM2D(x, y);
      final V m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2D(x, y);
      final V m1 = this.newVectorM2D(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2D(x, y);
      final V m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2D(x, y);
      final V m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2D(x, y);
      final V m1 = this.newVectorM2D(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2D(x, y);
      final V m1 = this.newVectorM2D(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final V m0 = this.newVectorM2D();
    final V m1 = this.newVectorM2D();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final V m0 = this.newVectorM2D();
      final V m1 = this.newVectorM2D();
      m1.setXD(23.0);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }

    {
      final V m0 = this.newVectorM2D();
      final V m1 = this.newVectorM2D();
      m1.setYD(23.0);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }
  }

  @Test public final void testInitializeReadable()
  {
    final V v0 = this.newVectorM2D(1.0, 2.0);
    final V v1 = this.newVectorM2D(v0);

    Assert.assertEquals(v1.getXD(), v0.getXD(), 0.0);
    Assert.assertEquals(v1.getYD(), v0.getYD(), 0.0);
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final PVectorM2D.ContextPVM2D c = new PVectorM2D.ContextPVM2D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = PVectorM2DContract.getRandomLarge();
      final double y0 = PVectorM2DContract.getRandomLarge();
      final V v0 = this.newVectorM2D(x0, y0);

      final double x1 = PVectorM2DContract.getRandomLarge();
      final double y1 = PVectorM2DContract.getRandomLarge();
      final V v1 = this.newVectorM2D(x1, y1);

      final V vr0 = this.newVectorM2D();
      final V vr1 = this.newVectorM2D();
      PVectorM2D.interpolateLinear(c, v0, v1, 0.0, vr0);
      PVectorM2D.interpolateLinear(c, v0, v1, 1.0, vr1);

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

  @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = PVectorM2DContract.getRandomLarge();
      final double y = PVectorM2DContract.getRandomLarge();
      final V v = this.newVectorM2D(x, y);

      final double m = PVectorM2D.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x =
        PVectorM2DContract.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double y =
        PVectorM2DContract.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final V v = this.newVectorM2D(x, y);

      final V vr = this.newVectorM2D();
      PVectorM2D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = PVectorM2D.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final V v = this.newVectorM2D(0.0, 0.0);
    final V vr = PVectorM2D.normalizeInPlace(v);
    final double m = PVectorM2D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Test public final void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final V v = this.newVectorM2D(1.0, 0.0);
    final double m = PVectorM2D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Test public final void testMagnitudeSimple()
  {
    final V v = this.newVectorM2D(8.0, 0.0);

    {
      final double p = PVectorM2D.dotProduct(v, v);
      final double q = PVectorM2D.magnitudeSquared(v);
      final double r = PVectorM2D.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final V v = this.newVectorM2D(0.0, 0.0);
    final double m = PVectorM2D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Test public final void testNormalizeSimple()
  {
    final V v0 = this.newVectorM2D(8.0, 0.0);
    final V out = this.newVectorM2D();
    final V vr = PVectorM2D.normalize(v0, out);

    Assert.assertEquals(out, vr);
    Assert.assertSame(out, vr);

    final double m = PVectorM2D.magnitude(out);
    Assert.assertEquals(1.0, m, 0.0);
  }

  @Test public final void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final V qr = this.newVectorM2D();
    final V q = this.newVectorM2D(0.0, 0.0);
    PVectorM2D.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getYD()));
  }

  @Test public final void testOrthonormalize()
  {
    final PVectorM2D.ContextPVM2D c = new PVectorM2D.ContextPVM2D();
    final V v0 = this.newVectorM2D(0.0, 1.0);
    final V v0_out = this.newVectorM2D();
    final V v1 = this.newVectorM2D(0.5, 0.5);
    final V v1_out = this.newVectorM2D();

    PVectorM2D.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM2D(0.0, 1.0), v0_out);
    Assert.assertEquals(this.newVectorM2D(1.0, 0.0), v1_out);
  }

  @Test public final void testOrthonormalizeMutation()
  {
    final PVectorM2D.ContextPVM2D c = new PVectorM2D.ContextPVM2D();
    final V v0 = this.newVectorM2D(0.0, 1.0);
    final V v1 = this.newVectorM2D(0.5, 0.5);

    PVectorM2D.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM2D(0.0, 1.0), v0);
    Assert.assertEquals(this.newVectorM2D(1.0, 0.0), v1);
  }

  @Test public final void testProjectionPerpendicularZero()
  {
    {
      final V p = this.newVectorM2D(1.0, 0.0);
      final V q = this.newVectorM2D(0.0, 1.0);
      final V r = this.newVectorM2D();
      final V u = PVectorM2D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, PVectorM2D.magnitude(u), 0.0);
    }

    {
      final V p = this.newVectorM2D(-1.0, 0.0);
      final V q = this.newVectorM2D(0.0, 1.0);
      final V r = this.newVectorM2D();
      final V u = PVectorM2D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, PVectorM2D.magnitude(u), 0.0);
    }
  }

  @Test public final void testScaleMutation()
  {
    final V out = this.newVectorM2D();
    final V v0 = this.newVectorM2D(1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);

    final V ov0 = PVectorM2D.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), 0.0);
    Assert.assertEquals(2.0, out.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);

    final V ov1 = PVectorM2D.scaleInPlace(v0, 2.0);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), 0.0);
    Assert.assertEquals(2.0, ov1.getYD(), 0.0);
    Assert.assertEquals(2.0, v0.getXD(), 0.0);
    Assert.assertEquals(2.0, v0.getYD(), 0.0);
  }

  @Test public final void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = PVectorM2DContract.getRandomLarge();
      final double y = PVectorM2DContract.getRandomLarge();
      final V v = this.newVectorM2D(x, y);

      final V vr = this.newVectorM2D();

      PVectorM2D.scale(v, 1.0, vr);

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

        PVectorM2D.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), orig_x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), orig_y));
      }
    }
  }

  @Test public final void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = PVectorM2DContract.getRandomLarge();
      final double y = PVectorM2DContract.getRandomLarge();
      final V v = this.newVectorM2D(x, y);

      final V vr = this.newVectorM2D();

      PVectorM2D.scale(v, 0.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getXD(), 0.0));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getYD(), 0.0));

      {
        PVectorM2D.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
      }
    }
  }

  @Test public final void testString()
  {
    final V v = this.newVectorM2D(0.0, 1.0);
    Assert.assertTrue(v.toString().endsWith("0.0 1.0]"));
  }

  @Test public final void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = PVectorM2DContract.getRandomLarge();
      final double y0 = PVectorM2DContract.getRandomLarge();
      final V v0 = this.newVectorM2D(x0, y0);

      final double x1 = PVectorM2DContract.getRandomLarge();
      final double y1 = PVectorM2DContract.getRandomLarge();
      final V v1 = this.newVectorM2D(x1, y1);

      final V vr0 = this.newVectorM2D();
      PVectorM2D.subtract(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() - v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() - v1.getYD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        PVectorM2D.subtractInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x - v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y - v1.getYD()));
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final V out = this.newVectorM2D();
    final V v0 = this.newVectorM2D(1.0, 1.0);
    final V v1 = this.newVectorM2D(1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);

    final V ov0 = PVectorM2D.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);

    final V ov1 = PVectorM2D.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0.0, ov1.getXD(), 0.0);
    Assert.assertEquals(0.0, ov1.getYD(), 0.0);
    Assert.assertEquals(0.0, v0.getXD(), 0.0);
    Assert.assertEquals(0.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
  }

}