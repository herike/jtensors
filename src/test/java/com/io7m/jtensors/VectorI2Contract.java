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

/**
 * Properties of 2D vectors.
 */

public abstract class VectorI2Contract
{
  /**
   * <code>∀v. w = absolute(v) → w.x = abs(v.x) ∧ w.y = abs(v.y)</code>
   */

  public abstract void testAbsolute();

  /**
   * Vector addition is correct.
   */

  public abstract void testAdd();

  /**
   * Addition of scaled vectors is correct.
   */

  public abstract void testAddScaled();

  /**
   * Differing vectors are not almost equal.
   */

  public abstract void testAlmostEqualNot();

  /**
   * <code>∀v r s. v ≃ r ∧ r ≃ s → v ≃ s</code>
   */

  public abstract void testAlmostEqualTransitive();

  /**
   * The angle function is correct.
   */

  public abstract void testAngle();

  /**
   * Interface functions work as expected.
   */

  public abstract void testCheckInterface();

  public abstract void testClampByVectorMaximumOrdering();

  public abstract void testClampByVectorMinimumOrdering();

  public abstract void testClampByVectorOrdering();

  public abstract void testClampMaximumOrdering();

  public abstract void testClampMinimumOrdering();

  public abstract void testClampOrdering();

  /**
   * Copying vectors works correctly.
   */

  public abstract void testCopy();

  /**
   * The default value for 2D vectors is (0,0)
   */

  public abstract void testDefault00();

  public abstract void testDistance();

  public abstract void testDistanceOrdering();

  /**
   * The <code>dotProduct</code> function gives expected results.
   */

  public abstract void testDotProduct();

  public abstract void testDotProductPerpendicular();

  /**
   * <code>∀v. dotProduct(v, v) = 1</code>
   */

  public abstract void testDotProductSelf();

  /**
   * <code>∀v. dotProduct(v, v) = magnitude(v)²</code>
   */

  public abstract void testDotProductSelfMagnitudeSquared();

  /**
   * Equality is correct.
   */

  public abstract void testEqualsCorrect();

  /**
   * Inequality is correct.
   */

  public abstract void testEqualsNotEqualCorrect();

  /**
   * Two equal vectors have the same hashcode.
   */

  public abstract void testHashCodeEqualsCorrect();

  /**
   * All parts of a vector affect the hashcode.
   */

  public abstract void testHashCodeNotEqualCorrect();

  public abstract void testInitializeReadable();

  /**
   * <p>
   * <code>∀v r s. interpolateLinear(v, r, 1.0) = r</code>
   * </p>
   * <p>
   * <code>∀v r s. interpolateLinear(v, r, 0.0) = v</code>
   * </p>
   */

  public abstract void testInterpolateLinearLimits();

  /**
   * The magnitude of any large random vector is greater than zero.
   */

  public abstract void testMagnitudeNonzero();

  /**
   * <p>
   * <code>∀v. magnitude(normalize(v)) = 1</code>
   * </p>
   */

  public abstract void testMagnitudeNormal();

  /**
   * <p>
   * <code>magnitude(normalize((0,0)) = 0</code>
   * </p>
   */

  public abstract void testMagnitudeNormalizeZero();

  /**
   * <p>
   * <code>magnitude((1,0)) = 1</code>
   * </p>
   */

  public abstract void testMagnitudeOne();

  /**
   * Simple magnitude tests.
   */

  public abstract void testMagnitudeSimple();

  /**
   * <p>
   * <code>magnitude((0,0)) = 0</code>
   * </p>
   */

  public abstract void testMagnitudeZero();

  public abstract void testNormalizeSimple();

  /**
   * Normalizing a zero vector results in a zero vector.
   */

  public abstract void testNormalizeZero();

  /**
   * Orthonormalization results in a pair of orthonormal vectors.
   */

  public abstract void testOrthonormalize();

  /**
   * Projecting a vector onto a perpendicular vector results in a vector with
   * zero magnitude.
   */

  public abstract void testProjectionPerpendicularZero();

  /**
   * <p>
   * <code>∀v. scale(v, 1) = v</code>
   * </p>
   */

  public abstract void testScaleOne();

  /**
   * <p>
   * <code>∀v. scale(v, 0) = (0,0)</code>
   * </p>
   */

  public abstract void testScaleZero();

  /**
   * toString() gives useful results.
   */

  public abstract void testString();

  /**
   * Vector subtraction is correct.
   */

  public abstract void testSubtract();
}
