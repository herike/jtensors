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

import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jfunctional.OptionType;
import com.io7m.jfunctional.Some;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.VectorI2F;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorI4F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorM4F;
import com.io7m.jtensors.VectorReadable3FType;

public class MatrixM4x4FTest extends MatrixM4x4Contract
{
  private static final VectorReadable3FType AXIS_X = new VectorI3F(1, 0, 0);
  private static final VectorReadable3FType AXIS_Y = new VectorI3F(0, 1, 0);
  private static final VectorReadable3FType AXIS_Z = new VectorI3F(0, 0, 1);

  @Override @Test public void testTranslationMakeEquivalent2Integer()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorI2I v = new VectorI2I(1, 2);

    {
      final MatrixM4x4F r = new MatrixM4x4F();
      final MatrixM4x4F t = MatrixM4x4F.makeTranslation2I(v);
      MatrixM4x4F.multiply(m, t, r);

      Assert.assertEquals(0, m.getDirectFloatBuffer().position());
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());

      Assert.assertTrue(r.getRowColumnF(0, 0) == 1.0);
      Assert.assertTrue(r.getRowColumnF(0, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnF(0, 2) == 0.0);
      Assert.assertTrue(r.getRowColumnF(0, 3) == 1.0);

      Assert.assertTrue(r.getRowColumnF(1, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnF(1, 1) == 1.0);
      Assert.assertTrue(r.getRowColumnF(1, 2) == 0.0);
      Assert.assertTrue(r.getRowColumnF(1, 3) == 2.0);

      Assert.assertTrue(r.getRowColumnF(2, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnF(2, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnF(2, 2) == 1.0);
      Assert.assertTrue(r.getRowColumnF(2, 3) == 0.0);

      Assert.assertTrue(r.getRowColumnF(3, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnF(3, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnF(3, 2) == 0.0);
      Assert.assertTrue(r.getRowColumnF(3, 3) == 1.0);

      Assert.assertEquals(0, m.getDirectFloatBuffer().position());
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testTranslationMakeEquivalent2Real()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorI2F v = new VectorI2F(1.0f, 2.0f);

    {
      final MatrixM4x4F r = new MatrixM4x4F();
      final MatrixM4x4F t = MatrixM4x4F.makeTranslation2F(v);
      MatrixM4x4F.multiply(m, t, r);

      Assert.assertEquals(0, m.getDirectFloatBuffer().position());
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());

      Assert.assertTrue(r.getRowColumnF(0, 0) == 1.0f);
      Assert.assertTrue(r.getRowColumnF(0, 1) == 0.0f);
      Assert.assertTrue(r.getRowColumnF(0, 2) == 0.0f);
      Assert.assertTrue(r.getRowColumnF(0, 3) == 1.0f);

      Assert.assertTrue(r.getRowColumnF(1, 0) == 0.0f);
      Assert.assertTrue(r.getRowColumnF(1, 1) == 1.0f);
      Assert.assertTrue(r.getRowColumnF(1, 2) == 0.0f);
      Assert.assertTrue(r.getRowColumnF(1, 3) == 2.0f);

      Assert.assertTrue(r.getRowColumnF(2, 0) == 0.0f);
      Assert.assertTrue(r.getRowColumnF(2, 1) == 0.0f);
      Assert.assertTrue(r.getRowColumnF(2, 2) == 1.0f);
      Assert.assertTrue(r.getRowColumnF(2, 3) == 0.0f);

      Assert.assertTrue(r.getRowColumnF(3, 0) == 0.0f);
      Assert.assertTrue(r.getRowColumnF(3, 1) == 0.0f);
      Assert.assertTrue(r.getRowColumnF(3, 2) == 0.0f);
      Assert.assertTrue(r.getRowColumnF(3, 3) == 1.0f);

      Assert.assertEquals(0, m.getDirectFloatBuffer().position());
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());
    }
  }

  private static void isRotationMatrixX(
    final AlmostEqualFloat.ContextRelative context,
    final MatrixM4x4F r)
  {
    boolean eq;

    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        -0.707106781187f,
        r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  private static void isRotationMatrixY(
    final AlmostEqualFloat.ContextRelative context,
    final MatrixM4x4F r)
  {
    boolean eq;

    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 3));
    Assert.assertTrue(eq);

    eq =
      AlmostEqualFloat.almostEqual(
        context,
        -0.707106781187f,
        r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  private static void isRotationMatrixZ(
    final AlmostEqualFloat.ContextRelative context,
    final MatrixM4x4F r)
  {
    boolean eq;

    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        -0.707106781187f,
        r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 3));
    Assert.assertTrue(eq);

    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public void testAdd()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.set(row, column, 1.0f);
        m1.set(row, column, 3.0f);
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    final MatrixM4x4F mk = MatrixM4x4F.add(m0, m1, mr);
    Assert.assertSame(mr, mk);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.getRowColumnF(row, column) == 1.0);
        Assert.assertTrue(m1.getRowColumnF(row, column) == 3.0);
        Assert.assertTrue(mr.getRowColumnF(row, column) == 4.0);
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());
  }

  @Override @Test public void testAddMutate()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.set(row, column, 1.0f);
        m1.set(row, column, 3.0f);
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    final MatrixM4x4F mr = MatrixM4x4F.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.getRowColumnF(row, column) == 4.0);
        Assert.assertTrue(mr.getRowColumnF(row, column) == 4.0);
        Assert.assertTrue(m1.getRowColumnF(row, column) == 3.0);
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());
  }

  @Override @Test public void testAddRowScaled()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 3.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 3.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 5.0f);
    m0.set(1, 2, 5.0f);
    m0.set(1, 3, 5.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    MatrixM4x4F.addRowScaled(m0, 0, 1, 2, 2.0f, m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(0, 3) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 3) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(2, 3) == 13.0);

    Assert.assertTrue(m1.getRowColumnF(3, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(3, 1) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(3, 2) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(3, 3) == 1.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    MatrixM4x4F.addRowScaledInPlace(m0, 0, 1, 2, 2.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m0.getRowColumnF(0, 0) == 3.0);
    Assert.assertTrue(m0.getRowColumnF(0, 1) == 3.0);
    Assert.assertTrue(m0.getRowColumnF(0, 2) == 3.0);
    Assert.assertTrue(m0.getRowColumnF(0, 3) == 3.0);

    Assert.assertTrue(m0.getRowColumnF(1, 0) == 5.0);
    Assert.assertTrue(m0.getRowColumnF(1, 1) == 5.0);
    Assert.assertTrue(m0.getRowColumnF(1, 2) == 5.0);
    Assert.assertTrue(m0.getRowColumnF(1, 3) == 5.0);

    Assert.assertTrue(m0.getRowColumnF(2, 0) == 13.0);
    Assert.assertTrue(m0.getRowColumnF(2, 1) == 13.0);
    Assert.assertTrue(m0.getRowColumnF(2, 2) == 13.0);
    Assert.assertTrue(m0.getRowColumnF(2, 3) == 13.0);

    Assert.assertTrue(m0.getRowColumnF(3, 0) == 0.0);
    Assert.assertTrue(m0.getRowColumnF(3, 1) == 0.0);
    Assert.assertTrue(m0.getRowColumnF(3, 2) == 0.0);
    Assert.assertTrue(m0.getRowColumnF(3, 3) == 1.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Override @Test public void testAddRowScaledContextEquivalent()
  {
    final MatrixM4x4F.Context context = new MatrixM4x4F.Context();
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 3.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 3.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 5.0f);
    m0.set(1, 2, 5.0f);
    m0.set(1, 3, 5.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    MatrixM4x4F.addRowScaledWithContext(context, m0, 0, 1, 2, 2.0f, m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(0, 3) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 3) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(2, 3) == 13.0);

    Assert.assertTrue(m1.getRowColumnF(3, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(3, 1) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(3, 2) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(3, 3) == 1.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowA()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.addRowScaledInPlace(m, 4, 0, 0, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowB()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.addRowScaledInPlace(m, 0, 4, 0, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowC()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.addRowScaledInPlace(m, 0, 0, 4, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowA()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.addRowScaledInPlace(m, -1, 0, 0, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowB()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.addRowScaledInPlace(m, 0, -1, 0, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowC()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.addRowScaledInPlace(m, 0, 0, -1, 1.0f);
  }

  @Override @Test public void testBufferEndianness()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    final FloatBuffer b = m.getDirectFloatBuffer();

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @Override @Test public void testCopy()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 4.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);
    m0.set(1, 3, 8.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);
    m0.set(2, 3, 12.0f);

    m0.set(3, 0, 13.0f);
    m0.set(3, 1, 14.0f);
    m0.set(3, 2, 15.0f);
    m0.set(3, 3, 16.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    MatrixM4x4F.copy(m0, m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 2.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 3.0);
    Assert.assertTrue(m1.getRowColumnF(0, 3) == 4.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 5.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 6.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 7.0);
    Assert.assertTrue(m1.getRowColumnF(1, 3) == 8.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 9.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 10.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 11.0);
    Assert.assertTrue(m1.getRowColumnF(2, 3) == 12.0);

    Assert.assertTrue(m1.getRowColumnF(3, 0) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(3, 1) == 14.0);
    Assert.assertTrue(m1.getRowColumnF(3, 2) == 15.0);
    Assert.assertTrue(m1.getRowColumnF(3, 3) == 16.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Override @Test public void testDeterminantIdentity()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    Assert.assertTrue(MatrixM4x4F.determinant(m) == 1.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Override @Test public void testDeterminantOther()
  {
    final MatrixM4x4F m = new MatrixM4x4F();

    m.set(0, 0, 2.0f);
    m.set(1, 1, 2.0f);
    m.set(2, 2, 2.0f);
    m.set(3, 3, 2.0f);

    Assert.assertTrue(MatrixM4x4F.determinant(m) == 16.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Override @Test public void testDeterminantScale()
  {
    final MatrixM4x4F m = new MatrixM4x4F();

    m.set(0, 0, 2.0f);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertTrue(MatrixM4x4F.determinant(m) == 2.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Override @Test public void testDeterminantScaleNegative()
  {
    final MatrixM4x4F m = new MatrixM4x4F();

    m.set(0, 0, -2.0f);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertTrue(MatrixM4x4F.determinant(m) == -2.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Override @Test public void testDeterminantZero()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    MatrixM4x4F.setZero(m);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertTrue(MatrixM4x4F.determinant(m) == 0.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final MatrixM4x4F m0 = new MatrixM4x4F();
      Assert.assertTrue(m0.equals(m0));
      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    }

    {
      final MatrixM4x4F m0 = new MatrixM4x4F();
      Assert.assertFalse(m0.equals(null));
      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    }

    {
      final MatrixM4x4F m0 = new MatrixM4x4F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    }

    {
      final MatrixM4x4F m0 = new MatrixM4x4F();
      final MatrixM4x4F m1 = new MatrixM4x4F();
      Assert.assertTrue(m0.equals(m1));
      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testEqualsNotEqualsCorrect()
  {
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final MatrixM4x4F m0 = new MatrixM4x4F();
        final MatrixM4x4F m1 = new MatrixM4x4F();
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
        m1.set(row, col, 256);
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
        Assert.assertFalse(m0.equals(m1));
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      }
    }
  }

  @Override @Test public void testExchangeRows()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 4.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);
    m0.set(1, 3, 8.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);
    m0.set(2, 3, 12.0f);

    m0.set(3, 0, 13.0f);
    m0.set(3, 1, 14.0f);
    m0.set(3, 2, 15.0f);
    m0.set(3, 3, 16.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    MatrixM4x4F.exchangeRows(m0, 0, 3, m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 14.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 15.0);
    Assert.assertTrue(m1.getRowColumnF(0, 3) == 16.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 3) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(2, 3) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(3, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(3, 1) == 2.0);
    Assert.assertTrue(m1.getRowColumnF(3, 2) == 3.0);
    Assert.assertTrue(m1.getRowColumnF(3, 3) == 4.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    MatrixM4x4F.exchangeRowsInPlace(m1, 0, 3);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 2.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 3.0);
    Assert.assertTrue(m1.getRowColumnF(0, 3) == 4.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 3) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(2, 3) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(3, 0) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(3, 1) == 14.0);
    Assert.assertTrue(m1.getRowColumnF(3, 2) == 15.0);
    Assert.assertTrue(m1.getRowColumnF(3, 3) == 16.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAOverflow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.exchangeRowsInPlace(m, 4, 0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAUnderflow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.exchangeRowsInPlace(m, -1, 0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBOverflow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.exchangeRowsInPlace(m, 0, 4);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBUnderflow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.exchangeRowsInPlace(m, 0, -1);
  }

  @Override @Test public void testExchangeRowsContextEquivalent()
  {
    final MatrixM4x4F.Context context = new MatrixM4x4F.Context();
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 4.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);
    m0.set(1, 3, 8.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);
    m0.set(2, 3, 12.0f);

    m0.set(3, 0, 13.0f);
    m0.set(3, 1, 14.0f);
    m0.set(3, 2, 15.0f);
    m0.set(3, 3, 16.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    MatrixM4x4F.exchangeRowsWithContext(context, m0, 0, 3, m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 14.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 15.0);
    Assert.assertTrue(m1.getRowColumnF(0, 3) == 16.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 3) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(2, 3) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(3, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(3, 1) == 2.0);
    Assert.assertTrue(m1.getRowColumnF(3, 2) == 3.0);
    Assert.assertTrue(m1.getRowColumnF(3, 3) == 4.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    MatrixM4x4F.exchangeRowsInPlaceWithContext(context, m1, 0, 3);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 2.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 3.0);
    Assert.assertTrue(m1.getRowColumnF(0, 3) == 4.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 3) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(2, 3) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(3, 0) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(3, 1) == 14.0);
    Assert.assertTrue(m1.getRowColumnF(3, 2) == 15.0);
    Assert.assertTrue(m1.getRowColumnF(3, 3) == 16.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Override @Test public void testHashcodeNeqExhaustive()
  {
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final MatrixM4x4F m0 = new MatrixM4x4F();
        final MatrixM4x4F m1 = new MatrixM4x4F();
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
        Assert.assertTrue(m0.hashCode() == m1.hashCode());
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
        m1.set(row, col, 256);
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
        Assert.assertFalse(m0.hashCode() == m1.hashCode());
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      }
    }
  }

  @Override @Test public void testInitializationFrom()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 5.0f);
    m0.set(0, 2, 7.0f);
    m0.set(0, 3, 11.0f);

    m0.set(1, 0, 13.0f);
    m0.set(1, 1, 17.0f);
    m0.set(1, 2, 19.0f);
    m0.set(1, 3, 23.0f);

    m0.set(2, 0, 29.0f);
    m0.set(2, 1, 31.0f);
    m0.set(2, 2, 37.0f);
    m0.set(2, 3, 41.0f);

    m0.set(3, 0, 43.0f);
    m0.set(3, 1, 47.0f);
    m0.set(3, 2, 53.0f);
    m0.set(3, 3, 59.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());

    final MatrixM4x4F m1 = new MatrixM4x4F(m0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 3.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 5.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 7.0);
    Assert.assertTrue(m1.getRowColumnF(0, 3) == 11.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 17.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 19.0);
    Assert.assertTrue(m1.getRowColumnF(1, 3) == 23.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 29.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 31.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 37.0);
    Assert.assertTrue(m1.getRowColumnF(2, 3) == 41.0);

    Assert.assertTrue(m1.getRowColumnF(3, 0) == 43.0);
    Assert.assertTrue(m1.getRowColumnF(3, 1) == 47.0);
    Assert.assertTrue(m1.getRowColumnF(3, 2) == 53.0);
    Assert.assertTrue(m1.getRowColumnF(3, 3) == 59.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Override @Test public void testInitializationIdentity()
  {
    final MatrixM4x4F m = new MatrixM4x4F();

    Assert.assertTrue(m.getRowColumnF(0, 0) == 1.0);
    Assert.assertTrue(m.getRowColumnF(0, 1) == 0.0);
    Assert.assertTrue(m.getRowColumnF(0, 2) == 0.0);
    Assert.assertTrue(m.getRowColumnF(0, 3) == 0.0);

    Assert.assertTrue(m.getRowColumnF(1, 0) == 0.0);
    Assert.assertTrue(m.getRowColumnF(1, 1) == 1.0);
    Assert.assertTrue(m.getRowColumnF(1, 2) == 0.0);
    Assert.assertTrue(m.getRowColumnF(1, 3) == 0.0);

    Assert.assertTrue(m.getRowColumnF(2, 0) == 0.0);
    Assert.assertTrue(m.getRowColumnF(2, 1) == 0.0);
    Assert.assertTrue(m.getRowColumnF(2, 2) == 1.0);
    Assert.assertTrue(m.getRowColumnF(2, 3) == 0.0);

    Assert.assertTrue(m.getRowColumnF(3, 0) == 0.0);
    Assert.assertTrue(m.getRowColumnF(3, 1) == 0.0);
    Assert.assertTrue(m.getRowColumnF(3, 2) == 0.0);
    Assert.assertTrue(m.getRowColumnF(3, 3) == 1.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Override @Test public void testInvertIdentity()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    {
      final OptionType<MatrixM4x4F> r = MatrixM4x4F.invert(m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM4x4F> s = (Some<MatrixM4x4F>) r;
      final MatrixM4x4F rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      Assert.assertTrue(rm.getRowColumnF(0, 0) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(0, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(0, 2) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(0, 3) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(1, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(1, 1) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(1, 2) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(1, 3) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(2, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(2, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(2, 2) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(2, 3) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(3, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(3, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(3, 2) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(3, 3) == 1.0);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }

    {
      final OptionType<MatrixM4x4F> r = MatrixM4x4F.invertInPlace(m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM4x4F> s = (Some<MatrixM4x4F>) r;
      final MatrixM4x4F rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      Assert.assertTrue(rm.getRowColumnF(0, 0) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(0, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(0, 2) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(0, 3) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(1, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(1, 1) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(1, 2) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(1, 3) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(2, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(2, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(2, 2) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(2, 3) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(3, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(3, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(3, 2) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(3, 3) == 1.0);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testInvertIdentityContextEquivalent()
  {
    final MatrixM4x4F.Context context = new MatrixM4x4F.Context();
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    {
      final OptionType<MatrixM4x4F> r =
        MatrixM4x4F.invertWithContext(context, m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM4x4F> s = (Some<MatrixM4x4F>) r;
      final MatrixM4x4F rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      Assert.assertTrue(rm.getRowColumnF(0, 0) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(0, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(0, 2) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(0, 3) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(1, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(1, 1) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(1, 2) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(1, 3) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(2, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(2, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(2, 2) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(2, 3) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(3, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(3, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(3, 2) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(3, 3) == 1.0);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }

    {
      final OptionType<MatrixM4x4F> r =
        MatrixM4x4F.invertInPlaceWithContext(context, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM4x4F> s = (Some<MatrixM4x4F>) r;
      final MatrixM4x4F rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      Assert.assertTrue(rm.getRowColumnF(0, 0) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(0, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(0, 2) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(0, 3) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(1, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(1, 1) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(1, 2) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(1, 3) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(2, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(2, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(2, 2) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(2, 3) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(3, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(3, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(3, 2) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(3, 3) == 1.0);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testInvertSimple()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    m0.set(0, 0, 2.0f);
    m0.set(0, 1, 0.0f);
    m0.set(0, 2, 0.0f);
    m0.set(0, 3, 0.0f);

    m0.set(1, 0, 0.0f);
    m0.set(1, 1, 2.0f);
    m0.set(1, 2, 0.0f);
    m0.set(1, 3, 0.0f);

    m0.set(2, 0, 0.0f);
    m0.set(2, 1, 0.0f);
    m0.set(2, 2, 2.0f);
    m0.set(2, 3, 0.0f);

    m0.set(3, 0, 0.0f);
    m0.set(3, 1, 0.0f);
    m0.set(3, 2, 0.0f);
    m0.set(3, 3, 2.0f);

    {
      final OptionType<MatrixM4x4F> r = MatrixM4x4F.invert(m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM4x4F> s = (Some<MatrixM4x4F>) r;
      final MatrixM4x4F rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      Assert.assertTrue(rm.getRowColumnF(0, 0) == 0.5);
      Assert.assertTrue(rm.getRowColumnF(0, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(0, 2) == 0);
      Assert.assertTrue(rm.getRowColumnF(0, 3) == 0);

      Assert.assertTrue(rm.getRowColumnF(1, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(1, 1) == 0.5);
      Assert.assertTrue(rm.getRowColumnF(1, 2) == 0);
      Assert.assertTrue(rm.getRowColumnF(1, 3) == 0);

      Assert.assertTrue(rm.getRowColumnF(2, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(2, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(2, 2) == 0.5);
      Assert.assertTrue(rm.getRowColumnF(2, 3) == 0);

      Assert.assertTrue(rm.getRowColumnF(3, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(3, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(3, 2) == 0);
      Assert.assertTrue(rm.getRowColumnF(3, 3) == 0.5);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }

    {
      final OptionType<MatrixM4x4F> r = MatrixM4x4F.invertInPlace(m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM4x4F> s = (Some<MatrixM4x4F>) r;
      final MatrixM4x4F rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      Assert.assertTrue(rm.getRowColumnF(0, 0) == 2);
      Assert.assertTrue(rm.getRowColumnF(0, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(0, 2) == 0);
      Assert.assertTrue(rm.getRowColumnF(0, 3) == 0);

      Assert.assertTrue(rm.getRowColumnF(1, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(1, 1) == 2);
      Assert.assertTrue(rm.getRowColumnF(1, 2) == 0);
      Assert.assertTrue(rm.getRowColumnF(1, 3) == 0);

      Assert.assertTrue(rm.getRowColumnF(2, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(2, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(2, 2) == 2);
      Assert.assertTrue(rm.getRowColumnF(2, 3) == 0);

      Assert.assertTrue(rm.getRowColumnF(3, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(3, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(3, 2) == 0);
      Assert.assertTrue(rm.getRowColumnF(3, 3) == 2);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testInvertSimple2()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext3dp();

    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();
    boolean eq = false;

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 0.0f);
    m0.set(0, 2, 5.0f);
    m0.set(0, 3, 0.0f);

    m0.set(1, 0, 0.0f);
    m0.set(1, 1, 2.0f);
    m0.set(1, 2, 0.0f);
    m0.set(1, 3, 11.0f);

    m0.set(2, 0, 7.0f);
    m0.set(2, 1, 0.0f);
    m0.set(2, 2, 3.0f);
    m0.set(2, 3, 0.0f);

    m0.set(3, 0, 0.0f);
    m0.set(3, 1, 13.0f);
    m0.set(3, 2, 0.0f);
    m0.set(3, 3, 4.0f);

    {
      final OptionType<MatrixM4x4F> r = MatrixM4x4F.invert(m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM4x4F> s = (Some<MatrixM4x4F>) r;
      final MatrixM4x4F rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(rm);

      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(0, 0),
          -0.09375f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 1), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(0, 2),
          0.15625f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 0), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(1, 1),
          -0.0296f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 2), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(1, 3),
          0.0814f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(2, 0),
          0.21875f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 1), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(2, 2),
          -0.03125f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 0), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(3, 1),
          0.096f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 2), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(3, 3),
          -0.01481f);
      Assert.assertTrue(eq);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }

    {
      final OptionType<MatrixM4x4F> r = MatrixM4x4F.invertInPlace(m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM4x4F> s = (Some<MatrixM4x4F>) r;
      final MatrixM4x4F rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(rm);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 0), 1.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 1), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 2), 5.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 0), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 1), 2.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 2), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat
          .almostEqual(context_f, rm.getRowColumnF(1, 3), 11.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 0), 7.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 1), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 2), 3.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 0), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat
          .almostEqual(context_f, rm.getRowColumnF(3, 1), 13.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 2), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 3), 4.0f);
      Assert.assertTrue(eq);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testInvertSimple2ContextEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext3dp();

    final MatrixM4x4F.Context context = new MatrixM4x4F.Context();
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();
    boolean eq = false;

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 0.0f);
    m0.set(0, 2, 5.0f);
    m0.set(0, 3, 0.0f);

    m0.set(1, 0, 0.0f);
    m0.set(1, 1, 2.0f);
    m0.set(1, 2, 0.0f);
    m0.set(1, 3, 11.0f);

    m0.set(2, 0, 7.0f);
    m0.set(2, 1, 0.0f);
    m0.set(2, 2, 3.0f);
    m0.set(2, 3, 0.0f);

    m0.set(3, 0, 0.0f);
    m0.set(3, 1, 13.0f);
    m0.set(3, 2, 0.0f);
    m0.set(3, 3, 4.0f);

    {
      final OptionType<MatrixM4x4F> r =
        MatrixM4x4F.invertWithContext(context, m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM4x4F> s = (Some<MatrixM4x4F>) r;
      final MatrixM4x4F rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(rm);

      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(0, 0),
          -0.09375f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 1), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(0, 2),
          0.15625f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 0), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(1, 1),
          -0.0296f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 2), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(1, 3),
          0.0814f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(2, 0),
          0.21875f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 1), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(2, 2),
          -0.03125f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 0), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(3, 1),
          0.096f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 2), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(
          context_f,
          rm.getRowColumnF(3, 3),
          -0.01481f);
      Assert.assertTrue(eq);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }

    {
      final OptionType<MatrixM4x4F> r =
        MatrixM4x4F.invertInPlaceWithContext(context, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM4x4F> s = (Some<MatrixM4x4F>) r;
      final MatrixM4x4F rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(rm);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 0), 1.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 1), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 2), 5.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 0), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 1), 2.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 2), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat
          .almostEqual(context_f, rm.getRowColumnF(1, 3), 11.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 0), 7.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 1), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 2), 3.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 0), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat
          .almostEqual(context_f, rm.getRowColumnF(3, 1), 13.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 2), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 3), 4.0f);
      Assert.assertTrue(eq);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testInvertSimpleContextEquivalent()
  {
    final MatrixM4x4F.Context context = new MatrixM4x4F.Context();
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    m0.set(0, 0, 2.0f);
    m0.set(0, 1, 0.0f);
    m0.set(0, 2, 0.0f);
    m0.set(0, 3, 0.0f);

    m0.set(1, 0, 0.0f);
    m0.set(1, 1, 2.0f);
    m0.set(1, 2, 0.0f);
    m0.set(1, 3, 0.0f);

    m0.set(2, 0, 0.0f);
    m0.set(2, 1, 0.0f);
    m0.set(2, 2, 2.0f);
    m0.set(2, 3, 0.0f);

    m0.set(3, 0, 0.0f);
    m0.set(3, 1, 0.0f);
    m0.set(3, 2, 0.0f);
    m0.set(3, 3, 2.0f);

    {
      final OptionType<MatrixM4x4F> r =
        MatrixM4x4F.invertWithContext(context, m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM4x4F> s = (Some<MatrixM4x4F>) r;
      final MatrixM4x4F rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      Assert.assertTrue(rm.getRowColumnF(0, 0) == 0.5);
      Assert.assertTrue(rm.getRowColumnF(0, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(0, 2) == 0);
      Assert.assertTrue(rm.getRowColumnF(0, 3) == 0);

      Assert.assertTrue(rm.getRowColumnF(1, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(1, 1) == 0.5);
      Assert.assertTrue(rm.getRowColumnF(1, 2) == 0);
      Assert.assertTrue(rm.getRowColumnF(1, 3) == 0);

      Assert.assertTrue(rm.getRowColumnF(2, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(2, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(2, 2) == 0.5);
      Assert.assertTrue(rm.getRowColumnF(2, 3) == 0);

      Assert.assertTrue(rm.getRowColumnF(3, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(3, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(3, 2) == 0);
      Assert.assertTrue(rm.getRowColumnF(3, 3) == 0.5);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }

    {
      final OptionType<MatrixM4x4F> r =
        MatrixM4x4F.invertInPlaceWithContext(context, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM4x4F> s = (Some<MatrixM4x4F>) r;
      final MatrixM4x4F rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      Assert.assertTrue(rm.getRowColumnF(0, 0) == 2);
      Assert.assertTrue(rm.getRowColumnF(0, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(0, 2) == 0);
      Assert.assertTrue(rm.getRowColumnF(0, 3) == 0);

      Assert.assertTrue(rm.getRowColumnF(1, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(1, 1) == 2);
      Assert.assertTrue(rm.getRowColumnF(1, 2) == 0);
      Assert.assertTrue(rm.getRowColumnF(1, 3) == 0);

      Assert.assertTrue(rm.getRowColumnF(2, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(2, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(2, 2) == 2);
      Assert.assertTrue(rm.getRowColumnF(2, 3) == 0);

      Assert.assertTrue(rm.getRowColumnF(3, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(3, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(3, 2) == 0);
      Assert.assertTrue(rm.getRowColumnF(3, 3) == 2);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testInvertZero()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    MatrixM4x4F.setZero(m0);

    {
      final OptionType<MatrixM4x4F> r = MatrixM4x4F.invert(m0, m1);
      Assert.assertTrue(r.isNone());
      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    }

    {
      final OptionType<MatrixM4x4F> r = MatrixM4x4F.invertInPlace(m0);
      Assert.assertTrue(r.isNone());
      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testInvertZeroContextEquivalent()
  {
    final MatrixM4x4F.Context context = new MatrixM4x4F.Context();
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    MatrixM4x4F.setZero(m0);

    {
      final OptionType<MatrixM4x4F> r =
        MatrixM4x4F.invertWithContext(context, m0, m1);
      Assert.assertTrue(r.isNone());
      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    }

    {
      final OptionType<MatrixM4x4F> r =
        MatrixM4x4F.invertInPlaceWithContext(context, m0);
      Assert.assertTrue(r.isNone());
      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testLookAt_NoTranslation_NegativeX_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.Context mc = new MatrixM4x4F.Context();
    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorI3F origin = new VectorI3F(0, 0, 0);
    final VectorI3F target = new VectorI3F(-1, 0, 0);
    final VectorI3F axis = new VectorI3F(0, 1, 0);
    MatrixM4x4F.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -1.0f, m.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public void testLookAt_NoTranslation_NegativeZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.Context mc = new MatrixM4x4F.Context();
    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorI3F origin = new VectorI3F(0, 0, 0);
    final VectorI3F target = new VectorI3F(0, 0, -1);
    final VectorI3F axis = new VectorI3F(0, 1, 0);
    MatrixM4x4F.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public void testLookAt_NoTranslation_PositiveX_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.Context mc = new MatrixM4x4F.Context();
    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorI3F origin = new VectorI3F(0, 0, 0);
    final VectorI3F target = new VectorI3F(1, 0, 0);
    final VectorI3F axis = new VectorI3F(0, 1, 0);
    MatrixM4x4F.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, -1.0f, m.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public void testLookAt_NoTranslation_PositiveZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.Context mc = new MatrixM4x4F.Context();
    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorI3F origin = new VectorI3F(0, 0, 0);
    final VectorI3F target = new VectorI3F(0, 0, 1);
    final VectorI3F axis = new VectorI3F(0, 1, 0);
    MatrixM4x4F.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, -1.0f, m.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -1.0f, m.getRowColumnF(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public
    void
    testLookAt_Translation102030_NegativeZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.Context mc = new MatrixM4x4F.Context();
    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorI3F origin = new VectorI3F(20 + 0, 30 + 0, 40 + 0);
    final VectorI3F target = new VectorI3F(20 + 0, 30 + 0, 40 + -1);
    final VectorI3F axis = new VectorI3F(0, 1, 0);
    MatrixM4x4F.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, -20.0f, m.getRowColumnF(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -30.0f, m.getRowColumnF(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -40.0f, m.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public void testMultiplyIdentity()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    final MatrixM4x4F r = MatrixM4x4F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.getRowColumnF(row, column) == mr.getRowColumnF(
          row,
          column));
        Assert.assertTrue(m1.getRowColumnF(row, column) == mr.getRowColumnF(
          row,
          column));
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
        Assert.assertEquals(0, mr.getDirectFloatBuffer().position());
      }
    }
  }

  @Override @Test public void testMultiplyMutateIdentity()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.getRowColumnF(row, column) == m1.getRowColumnF(
          row,
          column));
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      }
    }

    final MatrixM4x4F r = MatrixM4x4F.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.getRowColumnF(row, column) == m1.getRowColumnF(
          row,
          column));
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      }
    }
  }

  @Override @Test public void testMultiplyMutateSimple()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();

    MatrixM4x4F.set(m0, 0, 0, 1.0f);
    MatrixM4x4F.set(m0, 0, 1, 2.0f);
    MatrixM4x4F.set(m0, 0, 2, 3.0f);
    MatrixM4x4F.set(m0, 0, 3, 4.0f);
    MatrixM4x4F.set(m0, 1, 0, 5.0f);
    MatrixM4x4F.set(m0, 1, 1, 6.0f);
    MatrixM4x4F.set(m0, 1, 2, 7.0f);
    MatrixM4x4F.set(m0, 1, 3, 8.0f);
    MatrixM4x4F.set(m0, 2, 0, 9.0f);
    MatrixM4x4F.set(m0, 2, 1, 10.0f);
    MatrixM4x4F.set(m0, 2, 2, 11.0f);
    MatrixM4x4F.set(m0, 2, 3, 12.0f);
    MatrixM4x4F.set(m0, 3, 0, 13.0f);
    MatrixM4x4F.set(m0, 3, 1, 14.0f);
    MatrixM4x4F.set(m0, 3, 2, 15.0f);
    MatrixM4x4F.set(m0, 3, 3, 16.0f);

    final MatrixM4x4F m1 = new MatrixM4x4F(m0);
    final MatrixM4x4F r = MatrixM4x4F.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(r.getRowColumnF(0, 0) == 90.0);
    Assert.assertTrue(r.getRowColumnF(0, 1) == 100.0);
    Assert.assertTrue(r.getRowColumnF(0, 2) == 110.0);
    Assert.assertTrue(r.getRowColumnF(0, 3) == 120.0);
    Assert.assertTrue(r.getRowColumnF(1, 0) == 202.0);
    Assert.assertTrue(r.getRowColumnF(1, 1) == 228.0);
    Assert.assertTrue(r.getRowColumnF(1, 2) == 254.0);
    Assert.assertTrue(r.getRowColumnF(1, 3) == 280.0);
    Assert.assertTrue(r.getRowColumnF(2, 0) == 314.0);
    Assert.assertTrue(r.getRowColumnF(2, 1) == 356.0);
    Assert.assertTrue(r.getRowColumnF(2, 2) == 398.0);
    Assert.assertTrue(r.getRowColumnF(2, 3) == 440.0);
    Assert.assertTrue(r.getRowColumnF(3, 0) == 426.0);
    Assert.assertTrue(r.getRowColumnF(3, 1) == 484.0);
    Assert.assertTrue(r.getRowColumnF(3, 2) == 542.0);
    Assert.assertTrue(r.getRowColumnF(3, 3) == 600.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());
  }

  @Override @Test public void testMultiplySimple()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();

    MatrixM4x4F.set(m0, 0, 0, 1.0f);
    MatrixM4x4F.set(m0, 0, 1, 2.0f);
    MatrixM4x4F.set(m0, 0, 2, 3.0f);
    MatrixM4x4F.set(m0, 0, 3, 4.0f);
    MatrixM4x4F.set(m0, 1, 0, 5.0f);
    MatrixM4x4F.set(m0, 1, 1, 6.0f);
    MatrixM4x4F.set(m0, 1, 2, 7.0f);
    MatrixM4x4F.set(m0, 1, 3, 8.0f);
    MatrixM4x4F.set(m0, 2, 0, 9.0f);
    MatrixM4x4F.set(m0, 2, 1, 10.0f);
    MatrixM4x4F.set(m0, 2, 2, 11.0f);
    MatrixM4x4F.set(m0, 2, 3, 12.0f);
    MatrixM4x4F.set(m0, 3, 0, 13.0f);
    MatrixM4x4F.set(m0, 3, 1, 14.0f);
    MatrixM4x4F.set(m0, 3, 2, 15.0f);
    MatrixM4x4F.set(m0, 3, 3, 16.0f);

    final MatrixM4x4F m1 = new MatrixM4x4F(m0);
    final MatrixM4x4F mr = new MatrixM4x4F();

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    final MatrixM4x4F r = MatrixM4x4F.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    Assert.assertTrue(r.getRowColumnF(0, 0) == 90.0);
    Assert.assertTrue(r.getRowColumnF(0, 1) == 100.0);
    Assert.assertTrue(r.getRowColumnF(0, 2) == 110.0);
    Assert.assertTrue(r.getRowColumnF(0, 3) == 120.0);
    Assert.assertTrue(r.getRowColumnF(1, 0) == 202.0);
    Assert.assertTrue(r.getRowColumnF(1, 1) == 228.0);
    Assert.assertTrue(r.getRowColumnF(1, 2) == 254.0);
    Assert.assertTrue(r.getRowColumnF(1, 3) == 280.0);
    Assert.assertTrue(r.getRowColumnF(2, 0) == 314.0);
    Assert.assertTrue(r.getRowColumnF(2, 1) == 356.0);
    Assert.assertTrue(r.getRowColumnF(2, 2) == 398.0);
    Assert.assertTrue(r.getRowColumnF(2, 3) == 440.0);
    Assert.assertTrue(r.getRowColumnF(3, 0) == 426.0);
    Assert.assertTrue(r.getRowColumnF(3, 1) == 484.0);
    Assert.assertTrue(r.getRowColumnF(3, 2) == 542.0);
    Assert.assertTrue(r.getRowColumnF(3, 3) == 600.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());
  }

  @Override @Test public void testMultiplyVectorSimple()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();

    MatrixM4x4F.set(m0, 0, 0, 1.0f);
    MatrixM4x4F.set(m0, 0, 1, 2.0f);
    MatrixM4x4F.set(m0, 0, 2, 3.0f);
    MatrixM4x4F.set(m0, 0, 3, 4.0f);
    MatrixM4x4F.set(m0, 1, 0, 5.0f);
    MatrixM4x4F.set(m0, 1, 1, 6.0f);
    MatrixM4x4F.set(m0, 1, 2, 7.0f);
    MatrixM4x4F.set(m0, 1, 3, 8.0f);
    MatrixM4x4F.set(m0, 2, 0, 9.0f);
    MatrixM4x4F.set(m0, 2, 1, 10.0f);
    MatrixM4x4F.set(m0, 2, 2, 11.0f);
    MatrixM4x4F.set(m0, 2, 3, 12.0f);
    MatrixM4x4F.set(m0, 3, 0, 13.0f);
    MatrixM4x4F.set(m0, 3, 1, 14.0f);
    MatrixM4x4F.set(m0, 3, 2, 15.0f);
    MatrixM4x4F.set(m0, 3, 3, 16.0f);

    final VectorI4F v = new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorM4F out = new VectorM4F();

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());

    final VectorM4F r = MatrixM4x4F.multiplyVector4F(m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());

    Assert.assertTrue(out.getXF() == 30.0);
    Assert.assertTrue(out.getYF() == 70.0);
    Assert.assertTrue(out.getZF() == 110.0);
    Assert.assertTrue(out.getWF() == 150.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
  }

  @Override @SuppressWarnings({}) @Test public
    void
    testMultiplyVectorSimpleContextEquivalent()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();

    MatrixM4x4F.set(m0, 0, 0, 1.0f);
    MatrixM4x4F.set(m0, 0, 1, 2.0f);
    MatrixM4x4F.set(m0, 0, 2, 3.0f);
    MatrixM4x4F.set(m0, 0, 3, 4.0f);
    MatrixM4x4F.set(m0, 1, 0, 5.0f);
    MatrixM4x4F.set(m0, 1, 1, 6.0f);
    MatrixM4x4F.set(m0, 1, 2, 7.0f);
    MatrixM4x4F.set(m0, 1, 3, 8.0f);
    MatrixM4x4F.set(m0, 2, 0, 9.0f);
    MatrixM4x4F.set(m0, 2, 1, 10.0f);
    MatrixM4x4F.set(m0, 2, 2, 11.0f);
    MatrixM4x4F.set(m0, 2, 3, 12.0f);
    MatrixM4x4F.set(m0, 3, 0, 13.0f);
    MatrixM4x4F.set(m0, 3, 1, 14.0f);
    MatrixM4x4F.set(m0, 3, 2, 15.0f);
    MatrixM4x4F.set(m0, 3, 3, 16.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());

    final VectorI4F v = new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorM4F out = new VectorM4F();
    final MatrixM4x4F.Context context = new MatrixM4x4F.Context();

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());

    final VectorM4F r =
      MatrixM4x4F.multiplyVector4FWithContext(context, m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertTrue(out.getXF() == 30.0);
    Assert.assertTrue(out.getYF() == 70.0);
    Assert.assertTrue(out.getZF() == 110.0);
    Assert.assertTrue(out.getWF() == 150.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
  }

  @Override @Test public void testMultiplyZero()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();

    MatrixM4x4F.setZero(m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    final MatrixM4x4F r = MatrixM4x4F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(mr.getRowColumnF(row, column) == 0.0);
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeColumn()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    m.getRowColumnF(0, -1);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeRow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    m.getRowColumnF(-1, 0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowColumn()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    m.getRowColumnF(0, 4);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowRow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    m.getRowColumnF(4, 0);
  }

  /**
   * All rotation matrices have a determinant of 1.0 and are orthogonal.
   */

  @Override @Test public void testRotateDeterminantOrthogonal()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F m = new MatrixM4x4F();
    final MatrixM4x4F mt = new MatrixM4x4F();
    final MatrixM4x4F mi = new MatrixM4x4F();
    final VectorM3F axis = new VectorM3F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      float angle = (float) (Math.random() * (2 * Math.PI));
      axis.set3F(
        (float) Math.random(),
        (float) Math.random(),
        (float) Math.random());

      if (Math.random() > 0.5) {
        angle = -angle;
      }
      if (Math.random() > 0.5) {
        axis.setXF(-axis.getXF());
      }
      if (Math.random() > 0.5) {
        axis.setYF(-axis.getYF());
      }
      if (Math.random() > 0.5) {
        axis.setZF(-axis.getZF());
      }
      VectorM3F.normalizeInPlace(axis);

      System.out.println("axis  : " + axis);
      System.out.println("angle : " + angle);

      MatrixM4x4F.makeRotationInto(angle, axis, m);

      final double det = MatrixM4x4F.determinant(m);
      System.out.println("det   : " + det);

      AlmostEqualDouble.almostEqual(context, det, 1.0);

      MatrixM4x4F.invert(m, mi);
      MatrixM4x4F.transpose(m, mt);

      for (int row = 0; row < 4; ++row) {
        for (int col = 0; col < 4; ++col) {
          final float mx = mi.getRowColumnF(row, col);
          final float my = mt.getRowColumnF(row, col);
          final boolean eq = AlmostEqualFloat.almostEqual(context_f, mx, my);

          System.out.println("mi(" + row + ", " + col + ") == " + mx);
          System.out.println("mt(" + row + ", " + col + ") == " + my);
          System.out.println(eq);

          Assert.assertTrue(eq);
        }
      }

      System.out.println("--");
    }
  }

  /**
   * A rotation of 0 degrees around the X axis has no effect.
   */

  @Override @Test public void testRotateVector0X()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorM4F v_in = new VectorM4F(0, 0, -1, 1);
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_exp = new VectorM4F(0, 0, -1, 1);

    MatrixM4x4F.makeRotationInto(0, MatrixM4x4FTest.AXIS_X, m);
    MatrixM4x4F.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM4F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Y axis has no effect.
   */

  @Override @Test public void testRotateVector0Y()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorM4F v_in = new VectorM4F(0, 0, -1, 1);
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_exp = new VectorM4F(0, 0, -1, 1);

    MatrixM4x4F.makeRotationInto(0, MatrixM4x4FTest.AXIS_Y, m);
    MatrixM4x4F.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM4F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Z axis has no effect.
   */

  @Override @Test public void testRotateVector0Z()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorM4F v_in = new VectorM4F(0, 0, -1, 1);
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_exp = new VectorM4F(0, 0, -1, 1);

    MatrixM4x4F.makeRotationInto(0, MatrixM4x4FTest.AXIS_Z, m);
    MatrixM4x4F.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM4F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 90 degrees around the X axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Override @Test public void testRotateVector90X()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_in = new VectorM4F(0, 1, 0, 1);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorM4F v_exp = new VectorM4F(0, 6.1232339957367E-17f, 1, 1);

    MatrixM4x4F.makeRotationInto(
      Math.toRadians(90),
      MatrixM4x4FTest.AXIS_X,
      m);
    System.out.println(m);
    MatrixM4x4F.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of 90 degrees around the Y axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Override @Test public void testRotateVector90Y()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_in = new VectorM4F(0, 0, -1, 1);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorM4F v_exp = new VectorM4F(-1, 0, -6.1232339957367E-17f, 1);

    MatrixM4x4F.makeRotationInto(
      Math.toRadians(90),
      MatrixM4x4FTest.AXIS_Y,
      m);
    MatrixM4x4F.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of 90 degrees around the Z axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Override @Test public void testRotateVector90Z()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_in = new VectorM4F(0, 1, 0, 1);
    final VectorM4F v_exp = new VectorM4F(-1, 6.123233995736766E-17f, 0, 1);

    MatrixM4x4F.makeRotationInto(
      Math.toRadians(90),
      MatrixM4x4FTest.AXIS_Z,
      m);
    MatrixM4x4F.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the X axis gives the correct clockwise
   * rotation of the vector.
   */

  @Override @Test public void testRotateVectorMinus90X()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_in = new VectorM4F(0, 1, 0, 1);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorM4F v_exp = new VectorM4F(0, 6.1232339957367E-17f, -1, 1);

    MatrixM4x4F.makeRotationInto(
      Math.toRadians(-90),
      MatrixM4x4FTest.AXIS_X,
      m);
    System.out.println(m);
    MatrixM4x4F.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the Y axis gives the correct clockwise
   * rotation of the vector.
   */

  @Override @Test public void testRotateVectorMinus90Y()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_in = new VectorM4F(0, 0, -1, 1);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorM4F v_exp = new VectorM4F(1, 0, -6.1232339957367E-17f, 1);

    MatrixM4x4F.makeRotationInto(
      Math.toRadians(-90),
      MatrixM4x4FTest.AXIS_Y,
      m);
    MatrixM4x4F.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the Z axis gives the correct clockwise
   * rotation of the vector.
   */

  @Override @Test public void testRotateVectorMinus90Z()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_in = new VectorM4F(0, 1, 0, 1);
    final VectorM4F v_exp = new VectorM4F(1, 6.123233995736766E-17f, 0, 1);

    MatrixM4x4F.makeRotationInto(
      Math.toRadians(-90),
      MatrixM4x4FTest.AXIS_Z,
      m);
    MatrixM4x4F.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  @Override @Test public void testRotateXMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    {
      final MatrixM4x4F r =
        MatrixM4x4F.makeRotation(Math.toRadians(45), MatrixM4x4FTest.AXIS_X);
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());

      System.out.println(r);

      MatrixM4x4FTest.isRotationMatrixX(context, r);

      Assert.assertEquals(0, r.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testRotateYMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    {
      final MatrixM4x4F r =
        MatrixM4x4F.makeRotation(Math.toRadians(45), MatrixM4x4FTest.AXIS_Y);
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());

      System.out.println(r);

      MatrixM4x4FTest.isRotationMatrixY(context, r);

      Assert.assertEquals(0, r.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testRotateZMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    {
      final MatrixM4x4F r =
        MatrixM4x4F.makeRotation(Math.toRadians(45), MatrixM4x4FTest.AXIS_Z);
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());

      System.out.println(r);

      MatrixM4x4FTest.isRotationMatrixZ(context, r);

      Assert.assertEquals(0, r.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testRow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorM4F v = new VectorM4F();

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    MatrixM4x4F.row(m, 0, v);
    Assert.assertTrue(v.getXF() == 1.0);
    Assert.assertTrue(v.getYF() == 0.0);
    Assert.assertTrue(v.getZF() == 0.0);
    Assert.assertTrue(v.getWF() == 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    MatrixM4x4F.row(m, 1, v);
    Assert.assertTrue(v.getXF() == 0.0);
    Assert.assertTrue(v.getYF() == 1.0);
    Assert.assertTrue(v.getZF() == 0.0);
    Assert.assertTrue(v.getWF() == 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    MatrixM4x4F.row(m, 2, v);
    Assert.assertTrue(v.getXF() == 0.0);
    Assert.assertTrue(v.getYF() == 0.0);
    Assert.assertTrue(v.getZF() == 1.0);
    Assert.assertTrue(v.getWF() == 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    MatrixM4x4F.row(m, 3, v);
    Assert.assertTrue(v.getXF() == 0.0);
    Assert.assertTrue(v.getYF() == 0.0);
    Assert.assertTrue(v.getZF() == 0.0);
    Assert.assertTrue(v.getWF() == 1.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Override @Test public void testRow4()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorM4F v = new VectorM4F();
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    m.getRow4F(0, v);
    Assert.assertTrue(v.getXF() == 1.0);
    Assert.assertTrue(v.getYF() == 0.0);
    Assert.assertTrue(v.getZF() == 0.0);
    Assert.assertTrue(v.getWF() == 0.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    m.getRow4F(1, v);
    Assert.assertTrue(v.getXF() == 0.0);
    Assert.assertTrue(v.getYF() == 1.0);
    Assert.assertTrue(v.getZF() == 0.0);
    Assert.assertTrue(v.getWF() == 0.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    m.getRow4F(2, v);
    Assert.assertTrue(v.getXF() == 0.0);
    Assert.assertTrue(v.getYF() == 0.0);
    Assert.assertTrue(v.getZF() == 1.0);
    Assert.assertTrue(v.getWF() == 0.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    m.getRow4F(3, v);
    Assert.assertTrue(v.getXF() == 0.0);
    Assert.assertTrue(v.getYF() == 0.0);
    Assert.assertTrue(v.getZF() == 0.0);
    Assert.assertTrue(v.getWF() == 1.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRow4Overflow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    m.getRow4F(4, new VectorM4F());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRow4Underflow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    m.getRow4F(-1, new VectorM4F());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRowOverflow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.row(m, 4, new VectorM4F());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRowUnderflow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.row(m, -1, new VectorM4F());
  }

  @Override @Test public void testScale()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.set(row, column, 3.0f);
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    final MatrixM4x4F mk = MatrixM4x4F.scale(m0, 5.0f, mr);
    Assert.assertSame(mr, mk);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.getRowColumnF(row, column) == 3.0);
        Assert.assertTrue(mr.getRowColumnF(row, column) == 15.0);
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());
  }

  @Override @Test public void testScaleMutate()
  {
    final MatrixM4x4F m = new MatrixM4x4F();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m.set(row, column, 3.0f);
      }
    }

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    final MatrixM4x4F mr = MatrixM4x4F.scaleInPlace(m, 5.0f);
    Assert.assertSame(mr, m);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m.getRowColumnF(row, column) == 15.0);
        Assert.assertTrue(mr.getRowColumnF(row, column) == 15.0);
      }
    }

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());
  }

  @Override @Test public void testScaleRow()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 4.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);
    m0.set(1, 3, 8.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);
    m0.set(2, 3, 12.0f);

    m0.set(3, 0, 13.0f);
    m0.set(3, 1, 14.0f);
    m0.set(3, 2, 15.0f);
    m0.set(3, 3, 16.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    MatrixM4x4F.scaleRow(m0, 0, 2.0f, m1);
    MatrixM4x4F.scaleRow(m0, 1, 4.0f, m1);
    MatrixM4x4F.scaleRow(m0, 2, 8.0f, m1);
    MatrixM4x4F.scaleRow(m0, 3, 16.0f, m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 2.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 4.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 6.0);
    Assert.assertTrue(m1.getRowColumnF(0, 3) == 8.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 20.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 24.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 28.0);
    Assert.assertTrue(m1.getRowColumnF(1, 3) == 32.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 72.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 80.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 88.0);
    Assert.assertTrue(m1.getRowColumnF(2, 3) == 96.0);

    Assert.assertTrue(m1.getRowColumnF(3, 0) == 208.0);
    Assert.assertTrue(m1.getRowColumnF(3, 1) == 224.0);
    Assert.assertTrue(m1.getRowColumnF(3, 2) == 240.0);
    Assert.assertTrue(m1.getRowColumnF(3, 3) == 256.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    MatrixM4x4F.scaleRowInPlace(m0, 0, 2.0f);
    MatrixM4x4F.scaleRowInPlace(m0, 1, 4.0f);
    MatrixM4x4F.scaleRowInPlace(m0, 2, 8.0f);
    MatrixM4x4F.scaleRowInPlace(m0, 3, 16.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m0.getRowColumnF(0, 0) == 2.0);
    Assert.assertTrue(m0.getRowColumnF(0, 1) == 4.0);
    Assert.assertTrue(m0.getRowColumnF(0, 2) == 6.0);
    Assert.assertTrue(m0.getRowColumnF(0, 3) == 8.0);

    Assert.assertTrue(m0.getRowColumnF(1, 0) == 20.0);
    Assert.assertTrue(m0.getRowColumnF(1, 1) == 24.0);
    Assert.assertTrue(m0.getRowColumnF(1, 2) == 28.0);
    Assert.assertTrue(m0.getRowColumnF(1, 3) == 32.0);

    Assert.assertTrue(m0.getRowColumnF(2, 0) == 72.0);
    Assert.assertTrue(m0.getRowColumnF(2, 1) == 80.0);
    Assert.assertTrue(m0.getRowColumnF(2, 2) == 88.0);
    Assert.assertTrue(m0.getRowColumnF(2, 3) == 96.0);

    Assert.assertTrue(m0.getRowColumnF(3, 0) == 208.0);
    Assert.assertTrue(m0.getRowColumnF(3, 1) == 224.0);
    Assert.assertTrue(m0.getRowColumnF(3, 2) == 240.0);
    Assert.assertTrue(m0.getRowColumnF(3, 3) == 256.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Override @Test public void testScaleRowContextEquivalent()
  {
    final MatrixM4x4F.Context context = new MatrixM4x4F.Context();
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 4.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);
    m0.set(1, 3, 8.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);
    m0.set(2, 3, 12.0f);

    m0.set(3, 0, 13.0f);
    m0.set(3, 1, 14.0f);
    m0.set(3, 2, 15.0f);
    m0.set(3, 3, 16.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    MatrixM4x4F.scaleRowWithContext(context, m0, 0, 2.0f, m1);
    MatrixM4x4F.scaleRowWithContext(context, m0, 1, 4.0f, m1);
    MatrixM4x4F.scaleRowWithContext(context, m0, 2, 8.0f, m1);
    MatrixM4x4F.scaleRowWithContext(context, m0, 3, 16.0f, m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 2.0f);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 4.0f);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 6.0f);
    Assert.assertTrue(m1.getRowColumnF(0, 3) == 8.0f);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 20.0f);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 24.0f);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 28.0f);
    Assert.assertTrue(m1.getRowColumnF(1, 3) == 32.0f);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 72.0f);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 80.0f);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 88.0f);
    Assert.assertTrue(m1.getRowColumnF(2, 3) == 96.0f);

    Assert.assertTrue(m1.getRowColumnF(3, 0) == 208.0f);
    Assert.assertTrue(m1.getRowColumnF(3, 1) == 224.0f);
    Assert.assertTrue(m1.getRowColumnF(3, 2) == 240.0f);
    Assert.assertTrue(m1.getRowColumnF(3, 3) == 256.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    MatrixM4x4F.scaleRowInPlaceWithContext(context, m0, 0, 2.0f);
    MatrixM4x4F.scaleRowInPlaceWithContext(context, m0, 1, 4.0f);
    MatrixM4x4F.scaleRowInPlaceWithContext(context, m0, 2, 8.0f);
    MatrixM4x4F.scaleRowInPlaceWithContext(context, m0, 3, 16.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m0.getRowColumnF(0, 0) == 2.0f);
    Assert.assertTrue(m0.getRowColumnF(0, 1) == 4.0f);
    Assert.assertTrue(m0.getRowColumnF(0, 2) == 6.0f);
    Assert.assertTrue(m0.getRowColumnF(0, 3) == 8.0f);

    Assert.assertTrue(m0.getRowColumnF(1, 0) == 20.0f);
    Assert.assertTrue(m0.getRowColumnF(1, 1) == 24.0f);
    Assert.assertTrue(m0.getRowColumnF(1, 2) == 28.0f);
    Assert.assertTrue(m0.getRowColumnF(1, 3) == 32.0f);

    Assert.assertTrue(m0.getRowColumnF(2, 0) == 72.0f);
    Assert.assertTrue(m0.getRowColumnF(2, 1) == 80.0f);
    Assert.assertTrue(m0.getRowColumnF(2, 2) == 88.0f);
    Assert.assertTrue(m0.getRowColumnF(2, 3) == 96.0f);

    Assert.assertTrue(m0.getRowColumnF(3, 0) == 208.0f);
    Assert.assertTrue(m0.getRowColumnF(3, 1) == 224.0f);
    Assert.assertTrue(m0.getRowColumnF(3, 2) == 240.0f);
    Assert.assertTrue(m0.getRowColumnF(3, 3) == 256.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateOverflow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.scaleRowInPlace(m, 4, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateUnderflow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.scaleRowInPlace(m, -1, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowOverflow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    final MatrixM4x4F r = new MatrixM4x4F();
    MatrixM4x4F.scaleRow(m, 4, 1.0f, r);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowUnderflow()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    final MatrixM4x4F r = new MatrixM4x4F();
    MatrixM4x4F.scaleRow(m, -1, 1.0f, r);
  }

  @Override @Test public void testSetGetIdentity()
  {
    final MatrixM4x4F m = new MatrixM4x4F();

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertTrue(m.set(0, 0, 3.0f).getRowColumnF(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0f).getRowColumnF(0, 1) == 5.0);
    Assert.assertTrue(m.set(0, 2, 7.0f).getRowColumnF(0, 2) == 7.0);
    Assert.assertTrue(m.set(0, 3, 11.0f).getRowColumnF(0, 3) == 11.0);

    Assert.assertTrue(m.set(1, 0, 13.0f).getRowColumnF(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0f).getRowColumnF(1, 1) == 17.0);
    Assert.assertTrue(m.set(1, 2, 19.0f).getRowColumnF(1, 2) == 19.0);
    Assert.assertTrue(m.set(1, 3, 23.0f).getRowColumnF(1, 3) == 23.0);

    Assert.assertTrue(m.set(2, 0, 29.0f).getRowColumnF(2, 0) == 29.0);
    Assert.assertTrue(m.set(2, 1, 31.0f).getRowColumnF(2, 1) == 31.0);
    Assert.assertTrue(m.set(2, 2, 37.0f).getRowColumnF(2, 2) == 37.0);
    Assert.assertTrue(m.set(2, 3, 41.0f).getRowColumnF(2, 3) == 41.0);

    Assert.assertTrue(m.set(3, 0, 43.0f).getRowColumnF(3, 0) == 43.0);
    Assert.assertTrue(m.set(3, 1, 47.0f).getRowColumnF(3, 1) == 47.0);
    Assert.assertTrue(m.set(3, 2, 53.0f).getRowColumnF(3, 2) == 53.0);
    Assert.assertTrue(m.set(3, 3, 59.0f).getRowColumnF(3, 3) == 59.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Override @Test public void testSetGetInterfaceIdentity()
  {
    final MatrixM4x4F m = new MatrixM4x4F();

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    m.setRowColumnF(0, 0, 3.0f);
    Assert.assertTrue(m.getRowColumnF(0, 0) == 3.0f);
    m.setRowColumnF(0, 1, 5.0f);
    Assert.assertTrue(m.getRowColumnF(0, 1) == 5.0f);
    m.setRowColumnF(0, 2, 7.0f);
    Assert.assertTrue(m.getRowColumnF(0, 2) == 7.0f);
    m.setRowColumnF(0, 3, 11.0f);
    Assert.assertTrue(m.getRowColumnF(0, 3) == 11.0f);

    m.setRowColumnF(1, 0, 13.0f);
    Assert.assertTrue(m.getRowColumnF(1, 0) == 13.0f);
    m.setRowColumnF(1, 1, 17.0f);
    Assert.assertTrue(m.getRowColumnF(1, 1) == 17.0f);
    m.setRowColumnF(1, 2, 19.0f);
    Assert.assertTrue(m.getRowColumnF(1, 2) == 19.0f);
    m.setRowColumnF(1, 3, 23.0f);
    Assert.assertTrue(m.getRowColumnF(1, 3) == 23.0f);

    m.setRowColumnF(2, 0, 29.0f);
    Assert.assertTrue(m.getRowColumnF(2, 0) == 29.0f);
    m.setRowColumnF(2, 1, 31.0f);
    Assert.assertTrue(m.getRowColumnF(2, 1) == 31.0f);
    m.setRowColumnF(2, 2, 37.0f);
    Assert.assertTrue(m.getRowColumnF(2, 2) == 37.0f);
    m.setRowColumnF(2, 3, 41.0f);
    Assert.assertTrue(m.getRowColumnF(2, 3) == 41.0f);

    m.setRowColumnF(3, 0, 43.0f);
    Assert.assertTrue(m.getRowColumnF(3, 0) == 43.0);
    m.setRowColumnF(3, 1, 47.0f);
    Assert.assertTrue(m.getRowColumnF(3, 1) == 47.0);
    m.setRowColumnF(3, 2, 53.0f);
    Assert.assertTrue(m.getRowColumnF(3, 2) == 53.0);
    m.setRowColumnF(3, 3, 59.0f);
    Assert.assertTrue(m.getRowColumnF(3, 3) == 59.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Override @Test public void testSetIdentity()
  {
    final MatrixM4x4F m = new MatrixM4x4F();

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m.set(row, col, (float) Math.random());
      }
    }

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    MatrixM4x4F.setIdentity(m);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Override @Test public void testStorage()
  {
    final MatrixM4x4F m = new MatrixM4x4F();

    m.set(0, 0, 0);
    m.set(0, 1, 1);
    m.set(0, 2, 2);
    m.set(0, 3, 3);

    m.set(1, 0, 100);
    m.set(1, 1, 101);
    m.set(1, 2, 102);
    m.set(1, 3, 103);

    m.set(2, 0, 200);
    m.set(2, 1, 201);
    m.set(2, 2, 202);
    m.set(2, 3, 203);

    m.set(3, 0, 300);
    m.set(3, 1, 301);
    m.set(3, 2, 302);
    m.set(3, 3, 303);

    {
      final FloatBuffer b = m.getDirectFloatBuffer();

      Assert.assertTrue(b.order() == ByteOrder.nativeOrder());
      Assert.assertEquals(0, b.position());

      Assert.assertTrue(b.get(0) == 0.0);
      Assert.assertTrue(b.get(1) == 100.0);
      Assert.assertTrue(b.get(2) == 200.0);
      Assert.assertTrue(b.get(3) == 300.0);

      Assert.assertTrue(b.get(4) == 1.0);
      Assert.assertTrue(b.get(5) == 101.0);
      Assert.assertTrue(b.get(6) == 201.0);
      Assert.assertTrue(b.get(7) == 301.0);

      Assert.assertTrue(b.get(8) == 2.0);
      Assert.assertTrue(b.get(9) == 102.0);
      Assert.assertTrue(b.get(10) == 202.0);
      Assert.assertTrue(b.get(11) == 302.0);

      Assert.assertTrue(b.get(12) == 3.0);
      Assert.assertTrue(b.get(13) == 103.0);
      Assert.assertTrue(b.get(14) == 203.0);
      Assert.assertTrue(b.get(15) == 303.0);
    }
  }

  @Override @Test public void testString()
  {
    final MatrixM4x4F m0 = new MatrixM4x4F();
    final MatrixM4x4F m1 = new MatrixM4x4F();
    final MatrixM4x4F m2 = new MatrixM4x4F();
    m2.set(0, 0, 2.0f);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m2.getDirectFloatBuffer().position());
  }

  @Override @Test public void testTrace()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    final double t = MatrixM4x4F.trace(m);
    Assert.assertTrue(4.0 == t);
  }

  @Override @Test public void testTranslate3_4_Equivalent()
  {
    final MatrixM3x3F m3 = new MatrixM3x3F();
    final MatrixM4x4F m4 = new MatrixM4x4F();
    final VectorI3F v = new VectorI3F(3.0f, 7.0f, 0.0f);
    final VectorM3F v3i = new VectorM3F(1, 1, 1);
    final VectorM3F v3o = new VectorM3F();
    final VectorM4F w3i = new VectorM4F(1, 1, 1, 1);
    final VectorM4F w3o = new VectorM4F();

    MatrixM3x3F.makeTranslation2F(v, m3);
    MatrixM4x4F.makeTranslation3FInto(v, m4);

    MatrixM3x3F.multiplyVector3F(m3, v3i, v3o);
    MatrixM4x4F.multiplyVector4F(m4, w3i, w3o);

    Assert.assertTrue(v3o.getXF() == w3o.getXF());
    Assert.assertTrue(v3o.getYF() == w3o.getYF());
  }

  @Override @Test public void testTranslationMakeEquivalent3Integer()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorI3I v = new VectorI3I(1, 2, 3);

    {
      final MatrixM4x4F r = new MatrixM4x4F();
      final MatrixM4x4F t = MatrixM4x4F.makeTranslation3I(v);
      MatrixM4x4F.multiply(m, t, r);

      Assert.assertEquals(0, m.getDirectFloatBuffer().position());
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());
      Assert.assertEquals(0, t.getDirectFloatBuffer().position());

      Assert.assertTrue(r.getRowColumnF(0, 0) == 1.0);
      Assert.assertTrue(r.getRowColumnF(0, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnF(0, 2) == 0.0);
      Assert.assertTrue(r.getRowColumnF(0, 3) == 1.0);

      Assert.assertTrue(r.getRowColumnF(1, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnF(1, 1) == 1.0);
      Assert.assertTrue(r.getRowColumnF(1, 2) == 0.0);
      Assert.assertTrue(r.getRowColumnF(1, 3) == 2.0);

      Assert.assertTrue(r.getRowColumnF(2, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnF(2, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnF(2, 2) == 1.0);
      Assert.assertTrue(r.getRowColumnF(2, 3) == 3.0);

      Assert.assertTrue(r.getRowColumnF(3, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnF(3, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnF(3, 2) == 0.0);
      Assert.assertTrue(r.getRowColumnF(3, 3) == 1.0);

      Assert.assertEquals(0, m.getDirectFloatBuffer().position());
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());
      Assert.assertEquals(0, t.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testTranslationMakeEquivalent3Real()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    final VectorI3F v = new VectorI3F(1.0f, 2.0f, 3.0f);

    {
      final MatrixM4x4F r = new MatrixM4x4F();
      final MatrixM4x4F t = MatrixM4x4F.makeTranslation3F(v);
      MatrixM4x4F.multiply(m, t, r);

      Assert.assertEquals(0, m.getDirectFloatBuffer().position());
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());
      Assert.assertEquals(0, t.getDirectFloatBuffer().position());

      Assert.assertTrue(r.getRowColumnF(0, 0) == 1.0);
      Assert.assertTrue(r.getRowColumnF(0, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnF(0, 2) == 0.0);
      Assert.assertTrue(r.getRowColumnF(0, 3) == 1.0);

      Assert.assertTrue(r.getRowColumnF(1, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnF(1, 1) == 1.0);
      Assert.assertTrue(r.getRowColumnF(1, 2) == 0.0);
      Assert.assertTrue(r.getRowColumnF(1, 3) == 2.0);

      Assert.assertTrue(r.getRowColumnF(2, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnF(2, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnF(2, 2) == 1.0);
      Assert.assertTrue(r.getRowColumnF(2, 3) == 3.0);

      Assert.assertTrue(r.getRowColumnF(3, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnF(3, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnF(3, 2) == 0.0);
      Assert.assertTrue(r.getRowColumnF(3, 3) == 1.0);

      Assert.assertEquals(0, m.getDirectFloatBuffer().position());
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());
      Assert.assertEquals(0, t.getDirectFloatBuffer().position());
    }
  }

  @Override @Test public void testTranspose()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    final MatrixM4x4F r = new MatrixM4x4F();

    m.set(0, 0, 0.0f);
    m.set(0, 1, 1.0f);
    m.set(0, 2, 2.0f);
    m.set(0, 3, 3.0f);
    m.set(1, 0, 4.0f);
    m.set(1, 1, 5.0f);
    m.set(1, 2, 6.0f);
    m.set(1, 3, 7.0f);
    m.set(2, 0, 8.0f);
    m.set(2, 1, 9.0f);
    m.set(2, 2, 10.0f);
    m.set(2, 3, 11.0f);
    m.set(3, 0, 12.0f);
    m.set(3, 1, 13.0f);
    m.set(3, 2, 14.0f);
    m.set(3, 3, 15.0f);

    final MatrixM4x4F k = MatrixM4x4F.transpose(m, r);
    Assert.assertSame(k, r);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(m.getRowColumnF(0, 0) == 0.0);
    Assert.assertTrue(m.getRowColumnF(0, 1) == 1.0);
    Assert.assertTrue(m.getRowColumnF(0, 2) == 2.0);
    Assert.assertTrue(m.getRowColumnF(0, 3) == 3.0);
    Assert.assertTrue(m.getRowColumnF(1, 0) == 4.0);
    Assert.assertTrue(m.getRowColumnF(1, 1) == 5.0);
    Assert.assertTrue(m.getRowColumnF(1, 2) == 6.0);
    Assert.assertTrue(m.getRowColumnF(1, 3) == 7.0);
    Assert.assertTrue(m.getRowColumnF(2, 0) == 8.0);
    Assert.assertTrue(m.getRowColumnF(2, 1) == 9.0);
    Assert.assertTrue(m.getRowColumnF(2, 2) == 10.0);
    Assert.assertTrue(m.getRowColumnF(2, 3) == 11.0);
    Assert.assertTrue(m.getRowColumnF(3, 0) == 12.0);
    Assert.assertTrue(m.getRowColumnF(3, 1) == 13.0);
    Assert.assertTrue(m.getRowColumnF(3, 2) == 14.0);
    Assert.assertTrue(m.getRowColumnF(3, 3) == 15.0);

    Assert.assertTrue(r.getRowColumnF(0, 0) == 0.0);
    Assert.assertTrue(r.getRowColumnF(0, 1) == 4.0);
    Assert.assertTrue(r.getRowColumnF(0, 2) == 8.0);
    Assert.assertTrue(r.getRowColumnF(0, 3) == 12.0);
    Assert.assertTrue(r.getRowColumnF(1, 0) == 1.0);
    Assert.assertTrue(r.getRowColumnF(1, 1) == 5.0);
    Assert.assertTrue(r.getRowColumnF(1, 2) == 9.0);
    Assert.assertTrue(r.getRowColumnF(1, 3) == 13.0);
    Assert.assertTrue(r.getRowColumnF(2, 0) == 2.0);
    Assert.assertTrue(r.getRowColumnF(2, 1) == 6.0);
    Assert.assertTrue(r.getRowColumnF(2, 2) == 10.0);
    Assert.assertTrue(r.getRowColumnF(2, 3) == 14.0);
    Assert.assertTrue(r.getRowColumnF(3, 0) == 3.0);
    Assert.assertTrue(r.getRowColumnF(3, 1) == 7.0);
    Assert.assertTrue(r.getRowColumnF(3, 2) == 11.0);
    Assert.assertTrue(r.getRowColumnF(3, 3) == 15.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());
  }

  @Override @Test public void testTransposeMutate()
  {
    final MatrixM4x4F m = new MatrixM4x4F();

    m.set(0, 0, 0.0f);
    m.set(0, 1, 1.0f);
    m.set(0, 2, 2.0f);
    m.set(0, 3, 3.0f);
    m.set(1, 0, 4.0f);
    m.set(1, 1, 5.0f);
    m.set(1, 2, 6.0f);
    m.set(1, 3, 7.0f);
    m.set(2, 0, 8.0f);
    m.set(2, 1, 9.0f);
    m.set(2, 2, 10.0f);
    m.set(2, 3, 11.0f);
    m.set(3, 0, 12.0f);
    m.set(3, 1, 13.0f);
    m.set(3, 2, 14.0f);
    m.set(3, 3, 15.0f);

    final MatrixM4x4F r = MatrixM4x4F.transposeInPlace(m);
    Assert.assertSame(m, r);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(r.getRowColumnF(0, 0) == 0.0);
    Assert.assertTrue(r.getRowColumnF(0, 1) == 4.0);
    Assert.assertTrue(r.getRowColumnF(0, 2) == 8.0);
    Assert.assertTrue(r.getRowColumnF(0, 3) == 12.0);
    Assert.assertTrue(r.getRowColumnF(1, 0) == 1.0);
    Assert.assertTrue(r.getRowColumnF(1, 1) == 5.0);
    Assert.assertTrue(r.getRowColumnF(1, 2) == 9.0);
    Assert.assertTrue(r.getRowColumnF(1, 3) == 13.0);
    Assert.assertTrue(r.getRowColumnF(2, 0) == 2.0);
    Assert.assertTrue(r.getRowColumnF(2, 1) == 6.0);
    Assert.assertTrue(r.getRowColumnF(2, 2) == 10.0);
    Assert.assertTrue(r.getRowColumnF(2, 3) == 14.0);
    Assert.assertTrue(r.getRowColumnF(3, 0) == 3.0);
    Assert.assertTrue(r.getRowColumnF(3, 1) == 7.0);
    Assert.assertTrue(r.getRowColumnF(3, 2) == 11.0);
    Assert.assertTrue(r.getRowColumnF(3, 3) == 15.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());
  }

  @Override @Test public void testZero()
  {
    final MatrixM4x4F m = new MatrixM4x4F();
    MatrixM4x4F.setZero(m);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m.getRowColumnF(row, column) == 0.0);
      }
    }

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }
}