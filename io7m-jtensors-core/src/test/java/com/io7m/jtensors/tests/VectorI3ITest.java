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
import com.io7m.jtensors.VectorI3I;
import org.junit.Assert;
import org.junit.Test;

public class VectorI3ITest extends VectorI3Contract
{
  public static int randomNegativeNumber()
  {
    return (int) (Math.random() * Integer.MIN_VALUE);
  }

  public static int randomPositiveNumber()
  {
    return (int) (Math.random() * Integer.MAX_VALUE);
  }

  public static int randomPositiveSmallNumber()
  {
    return (int) (Math.random() * (1 << 14));
  }

  @Override @Test public void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = (int) (Math.random() * Integer.MIN_VALUE);
      final int y = (int) (Math.random() * Integer.MIN_VALUE);
      final int z = (int) (Math.random() * Integer.MIN_VALUE);
      final VectorI3I v = new VectorI3I(x, y, z);

      final VectorI3I vr = VectorI3I.absolute(v);

      Assert.assertEquals(Math.abs(v.getXI()), vr.getXI());
      Assert.assertEquals(Math.abs(v.getYI()), vr.getYI());
      Assert.assertEquals(Math.abs(v.getZI()), vr.getZI());
    }
  }

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI3ITest.randomPositiveSmallNumber();
      final int y0 = VectorI3ITest.randomPositiveSmallNumber();
      final int z0 = VectorI3ITest.randomPositiveSmallNumber();
      final VectorI3I v0 = new VectorI3I(x0, y0, z0);

      final int x1 = VectorI3ITest.randomPositiveSmallNumber();
      final int y1 = VectorI3ITest.randomPositiveSmallNumber();
      final int z1 = VectorI3ITest.randomPositiveSmallNumber();
      final VectorI3I v1 = new VectorI3I(x1, y1, z1);

      final VectorI3I vr0 = VectorI3I.add(v0, v1);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + v1.getYI()));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + v1.getZI()));
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI3ITest.randomPositiveSmallNumber();
      final int y0 = VectorI3ITest.randomPositiveSmallNumber();
      final int z0 = VectorI3ITest.randomPositiveSmallNumber();
      final VectorI3I v0 = new VectorI3I(x0, y0, z0);

      final int x1 = VectorI3ITest.randomPositiveSmallNumber();
      final int y1 = VectorI3ITest.randomPositiveSmallNumber();
      final int z1 = VectorI3ITest.randomPositiveSmallNumber();
      final VectorI3I v1 = new VectorI3I(x1, y1, z1);

      final int r = VectorI3ITest.randomPositiveSmallNumber();

      final VectorI3I vr0 = VectorI3I.addScaled(v0, v1, r);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + (v1.getXI() * r)));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + (v1.getYI() * r)));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + (v1.getZI() * r)));
    }
  }

  @Override @Test public void testAlmostEqualNot()
  {
    // Not supported by integer vectors
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    // Not supported by integer vectors
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorI3I v = new VectorI3I(3, 5, 7);

    Assert.assertTrue(v.getXI() == v.getXI());
    Assert.assertTrue(v.getYI() == v.getYI());
    Assert.assertTrue(v.getZI() == v.getZI());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = VectorI3ITest.randomNegativeNumber();
      final int max_y = VectorI3ITest.randomNegativeNumber();
      final int max_z = VectorI3ITest.randomNegativeNumber();
      final VectorI3I maximum = new VectorI3I(max_x, max_y, max_z);

      final int x = VectorI3ITest.randomNegativeNumber();
      final int y = VectorI3ITest.randomNegativeNumber();
      final int z = VectorI3ITest.randomNegativeNumber();
      final VectorI3I v = new VectorI3I(x, y, z);

      final VectorI3I vr = VectorI3I.clampMaximumByVector(v, maximum);

      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorI3ITest.randomPositiveNumber();
      final int min_y = VectorI3ITest.randomPositiveNumber();
      final int min_z = VectorI3ITest.randomPositiveNumber();
      final VectorI3I minimum = new VectorI3I(min_x, min_y, min_z);

      final int x = VectorI3ITest.randomNegativeNumber();
      final int y = VectorI3ITest.randomNegativeNumber();
      final int z = VectorI3ITest.randomNegativeNumber();
      final VectorI3I v = new VectorI3I(x, y, z);

      final VectorI3I vr = VectorI3I.clampMinimumByVector(v, minimum);

      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorI3ITest.randomNegativeNumber();
      final int min_y = VectorI3ITest.randomNegativeNumber();
      final int min_z = VectorI3ITest.randomNegativeNumber();
      final VectorI3I minimum = new VectorI3I(min_x, min_y, min_z);

      final int max_x = VectorI3ITest.randomPositiveNumber();
      final int max_y = VectorI3ITest.randomPositiveNumber();
      final int max_z = VectorI3ITest.randomPositiveNumber();
      final VectorI3I maximum = new VectorI3I(max_x, max_y, max_z);

      final int x = VectorI3ITest.randomNegativeNumber();
      final int y = VectorI3ITest.randomPositiveNumber();
      final int z = VectorI3ITest.randomPositiveNumber();
      final VectorI3I v = new VectorI3I(x, y, z);

      final VectorI3I vr = VectorI3I.clampByVector(v, minimum, maximum);

      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = VectorI3ITest.randomNegativeNumber();

      final int x = VectorI3ITest.randomPositiveNumber();
      final int y = VectorI3ITest.randomPositiveNumber();
      final int z = VectorI3ITest.randomPositiveNumber();
      final VectorI3I v = new VectorI3I(x, y, z);

      final VectorI3I vr = VectorI3I.clampMaximum(v, maximum);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getZI() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorI3ITest.randomPositiveNumber();

      final int x = VectorI3ITest.randomNegativeNumber();
      final int y = VectorI3ITest.randomNegativeNumber();
      final int z = VectorI3ITest.randomNegativeNumber();
      final VectorI3I v = new VectorI3I(x, y, z);

      final VectorI3I vr = VectorI3I.clampMinimum(v, minimum);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorI3ITest.randomNegativeNumber();
      final int maximum = VectorI3ITest.randomPositiveNumber();

      final int x = VectorI3ITest.randomNegativeNumber();
      final int y = VectorI3ITest.randomPositiveNumber();
      final int z = VectorI3ITest.randomPositiveNumber();
      final VectorI3I v = new VectorI3I(x, y, z);

      final VectorI3I vr = VectorI3I.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() <= maximum);
      Assert.assertTrue(vr.getZI() >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorI3I vb = new VectorI3I(5, 6, 7);
    final VectorI3I va = new VectorI3I(1, 2, 3);

    Assert.assertFalse(va.getXI() == vb.getXI());
    Assert.assertFalse(va.getYI() == vb.getYI());
    Assert.assertFalse(va.getZI() == vb.getZI());

    final VectorI3I vc = new VectorI3I(va);

    Assert.assertTrue(va.getXI() == vc.getXI());
    Assert.assertTrue(va.getYI() == vc.getYI());
    Assert.assertTrue(va.getZI() == vc.getZI());

    Assert.assertFalse(vc.getXI() == vb.getXI());
    Assert.assertFalse(vc.getYI() == vb.getYI());
    Assert.assertFalse(vc.getZI() == vb.getZI());
  }

  @Override public void testCrossProductPerpendicular()
  {
    // Not applicable.
  }

  @Override public void testDefault000()
  {
    Assert.assertTrue(new VectorI3I().equals(new VectorI3I(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final VectorI3I v0 = new VectorI3I(0, 1, 0);
    final VectorI3I v1 = new VectorI3I(0, 0, 0);
    Assert.assertTrue(VectorI3I.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI3ITest.randomPositiveSmallNumber();
      final int y0 = VectorI3ITest.randomPositiveSmallNumber();
      final int z0 = VectorI3ITest.randomPositiveSmallNumber();
      final VectorI3I v0 = new VectorI3I(x0, y0, z0);

      final int x1 = VectorI3ITest.randomPositiveSmallNumber();
      final int y1 = VectorI3ITest.randomPositiveSmallNumber();
      final int z1 = VectorI3ITest.randomPositiveSmallNumber();
      final VectorI3I v1 = new VectorI3I(x1, y1, z1);

      Assert.assertTrue(VectorI3I.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorI3I v0 = new VectorI3I(10, 10, 10);
    final VectorI3I v1 = new VectorI3I(10, 10, 10);

    {
      final int p = VectorI3I.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = VectorI3I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = VectorI3I.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (Math.random() * max);
      final int y = (int) (Math.random() * max);
      final int z = (int) (Math.random() * max);
      final VectorI3I q = new VectorI3I(x, y, z);

      final double ms = VectorI3I.magnitudeSquared(q);
      final double dp = VectorI3I.dotProduct(q, q);

      
      
      

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (Math.random() * max);
      final int y = (int) (Math.random() * max);
      final int z = (int) (Math.random() * max);
      final VectorI3I q = new VectorI3I(x, y, z);
      final double dp = VectorI3I.dotProduct(q, q);

      
      

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorI3I v0 = new VectorI3I(10, 10, 10);

    {
      final int p = VectorI3I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = VectorI3I.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorI3I m0 = new VectorI3I();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI3I m0 = new VectorI3I();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI3I m0 = new VectorI3I();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI3I m0 = new VectorI3I();
      final VectorI3I m1 = new VectorI3I();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final int x = (int) (Math.random() * 1000);
    final int y = x + 1;
    final int z = y + 1;
    final int w = z + 1;
    final int q = w + 1;

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      final VectorI3I m1 = new VectorI3I(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      final VectorI3I m1 = new VectorI3I(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      final VectorI3I m1 = new VectorI3I(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      final VectorI3I m1 = new VectorI3I(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      final VectorI3I m1 = new VectorI3I(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      final VectorI3I m1 = new VectorI3I(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      final VectorI3I m1 = new VectorI3I(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      final VectorI3I m1 = new VectorI3I(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      final VectorI3I m1 = new VectorI3I(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      final VectorI3I m1 = new VectorI3I(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3I m0 = new VectorI3I(x, y, z);
      final VectorI3I m1 = new VectorI3I(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorI3I m0 = new VectorI3I();
    final VectorI3I m1 = new VectorI3I();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI3I m0 = new VectorI3I(23, 0, 0);
      final VectorI3I m1 = new VectorI3I();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3I m0 = new VectorI3I(0, 23, 0);
      final VectorI3I m1 = new VectorI3I();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3I m0 = new VectorI3I(0, 0, 23);
      final VectorI3I m1 = new VectorI3I();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorI3I v0 = new VectorI3I(1, 2, 3);
    final VectorI3I v1 = new VectorI3I(v0);

    Assert.assertTrue(v0.getXI() == v1.getXI());
    Assert.assertTrue(v0.getYI() == v1.getYI());
    Assert.assertTrue(v0.getZI() == v1.getZI());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI3ITest.randomPositiveNumber();
      final int y0 = VectorI3ITest.randomPositiveNumber();
      final int z0 = VectorI3ITest.randomPositiveNumber();
      final VectorI3I v0 = new VectorI3I(x0, y0, z0);

      final int x1 = VectorI3ITest.randomPositiveNumber();
      final int y1 = VectorI3ITest.randomPositiveNumber();
      final int z1 = VectorI3ITest.randomPositiveNumber();
      final VectorI3I v1 = new VectorI3I(x1, y1, z1);

      final VectorI3I vr0 = VectorI3I.interpolateLinear(v0, v1, 0);
      final VectorI3I vr1 = VectorI3I.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.getXI() == vr0.getXI());
      Assert.assertTrue(v0.getYI() == vr0.getYI());
      Assert.assertTrue(v0.getZI() == vr0.getZI());

      Assert.assertTrue(v1.getXI() == vr1.getXI());
      Assert.assertTrue(v1.getYI() == vr1.getYI());
      Assert.assertTrue(v1.getZI() == vr1.getZI());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI3ITest.randomPositiveSmallNumber();
      final int y = VectorI3ITest.randomPositiveSmallNumber();
      final int z = VectorI3ITest.randomPositiveSmallNumber();
      final VectorI3I v = new VectorI3I(x, y, z);

      final int m = VectorI3I.magnitude(v);
      Assert.assertTrue(m >= 1);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testMagnitudeOne()
  {
    final VectorI3I v = new VectorI3I(1, 0, 0);
    final int m = VectorI3I.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorI3I v = new VectorI3I(8, 0, 0);

    {
      final int p = VectorI3I.dotProduct(v, v);
      final int q = VectorI3I.magnitudeSquared(v);
      final int r = VectorI3I.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorI3I v = new VectorI3I(0, 0, 0);
    final int m = VectorI3I.magnitude(v);
    Assert.assertTrue(m == 0);
  }

  @Override @Test public void testNormalizeSimple()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testNormalizeZero()
  {
    // Not supported by integer vectors
  }

  @Override @Test public void testOrthonormalize()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorI3I p = new VectorI3I(1, 0, 0);
      final VectorI3I q = new VectorI3I(0, 1, 0);
      final VectorI3I u = VectorI3I.projection(p, q);

      Assert.assertTrue(VectorI3I.magnitude(u) == 0);
    }

    {
      final VectorI3I p = new VectorI3I(-1, 0, 0);
      final VectorI3I q = new VectorI3I(0, 1, 0);
      final VectorI3I u = VectorI3I.projection(p, q);

      Assert.assertTrue(VectorI3I.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI3ITest.randomPositiveNumber();
      final int y = VectorI3ITest.randomPositiveNumber();
      final int z = VectorI3ITest.randomPositiveNumber();
      final VectorI3I v = new VectorI3I(x, y, z);

      final VectorI3I vr = VectorI3I.scale(v, 1);

      Assert.assertTrue(v.getXI() == vr.getXI());
      Assert.assertTrue(v.getYI() == vr.getYI());
      Assert.assertTrue(v.getZI() == vr.getZI());
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI3ITest.randomPositiveNumber();
      final int y = VectorI3ITest.randomPositiveNumber();
      final int z = VectorI3ITest.randomPositiveNumber();
      final VectorI3I v = new VectorI3I(x, y, z);

      final VectorI3I vr = VectorI3I.scale(v, 0);

      Assert.assertTrue(vr.getXI() == 0);
      Assert.assertTrue(vr.getYI() == 0);
      Assert.assertTrue(vr.getZI() == 0);
    }
  }

  @Override @Test public void testString()
  {
    final VectorI3I v = new VectorI3I(1, 2, 3);
    Assert.assertTrue(v.toString().equals("[VectorI3I 1 2 3]"));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI3ITest.randomPositiveNumber();
      final int y0 = VectorI3ITest.randomPositiveNumber();
      final int z0 = VectorI3ITest.randomPositiveNumber();
      final VectorI3I v0 = new VectorI3I(x0, y0, z0);

      final int x1 = VectorI3ITest.randomPositiveNumber();
      final int y1 = VectorI3ITest.randomPositiveNumber();
      final int z1 = VectorI3ITest.randomPositiveNumber();
      final VectorI3I v1 = new VectorI3I(x1, y1, z1);

      final VectorI3I vr0 = VectorI3I.subtract(v0, v1);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() - v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() - v1.getYI()));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() - v1.getZI()));
    }
  }
}
