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

import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.functional.Option;
import com.io7m.jaux.functional.Option.Some;
import com.io7m.jaux.functional.Option.Type;

public class MatrixM2x2FTTest
{
  @SuppressWarnings("static-method") @Test public <A> void testAdd()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> mr = new MatrixM2x2FT<A>();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.set(row, column, 1.0f);
        m1.set(row, column, 3.0f);
      }
    }

    final MatrixM2x2FT<A> mk = MatrixM2x2FT.add(m0, m1, mr);
    Assert.assertSame(mr, mk);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(mk).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == 1.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testAddMutate()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.set(row, column, 1.0f);
        m1.set(row, column, 3.0f);
      }
    }

    final MatrixM2x2FT<A> mr = MatrixM2x2FT.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(mr).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == 4.0f);
        Assert.assertTrue(mr.get(row, column) == 4.0f);
        Assert.assertTrue(m1.get(row, column) == 3.0f);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testAddRowScaled()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 3.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 5.0f);

    MatrixM2x2FT.addRowScaled(m0, 0, 1, 1, 2.0f, m1);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 0.0);

    Assert.assertTrue(m1.get(1, 0) == 13.0);
    Assert.assertTrue(m1.get(1, 1) == 13.0);

    MatrixM2x2FT.addRowScaled(m0, 0, 1, 1, 2.0f);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m0).position());

    Assert.assertTrue(m0.get(0, 0) == 3.0);
    Assert.assertTrue(m0.get(0, 1) == 3.0);

    Assert.assertTrue(m0.get(1, 0) == 13.0);
    Assert.assertTrue(m0.get(1, 1) == 13.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledOverflowA()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.addRowScaled(m, 2, 0, 0, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledOverflowB()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.addRowScaled(m, 0, 2, 0, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledOverflowC()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.addRowScaled(m, 0, 0, 2, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledUnderflowA()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.addRowScaled(m, -1, 0, 0, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledUnderflowB()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.addRowScaled(m, 0, -1, 0, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledUnderflowC()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.addRowScaled(m, 0, 0, -1, 1.0f);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testBufferEndianness()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    final FloatBuffer b = MatrixM2x2FT.floatBuffer(m);

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @SuppressWarnings("static-method") @Test public <A> void testCopy()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);

    m0.set(1, 0, 4.0f);
    m0.set(1, 1, 5.0f);

    MatrixM2x2FT.copy(m0, m1);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);

    Assert.assertTrue(m1.get(1, 0) == 4.0);
    Assert.assertTrue(m1.get(1, 1) == 5.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testDeterminantIdentity()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    Assert.assertTrue(MatrixM2x2FT.determinant(m) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testDeterminantOther()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();

    m.set(0, 0, 2.0f);
    m.set(1, 1, 2.0f);

    Assert.assertTrue(MatrixM2x2FT.determinant(m) == 4.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testDeterminantScale()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();

    m.set(0, 0, 2.0f);

    Assert.assertTrue(MatrixM2x2FT.determinant(m) == 2.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testDeterminantScaleNegative()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();

    m.set(0, 0, -2.0f);

    Assert.assertTrue(MatrixM2x2FT.determinant(m) == -2.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testDeterminantZero()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.setZero(m);
    Assert.assertTrue(MatrixM2x2FT.determinant(m) == 0.0);
  }

  @SuppressWarnings("static-method") @Test public <A> void testEqualsCase0()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    Assert.assertTrue(m0.equals(m0));
  }

  @SuppressWarnings("static-method") @Test public <A> void testEqualsCase1()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    Assert.assertFalse(m0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public <A> void testEqualsCase2()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public <A> void testEqualsCase3()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();
    Assert.assertTrue(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testEqualsNeqExhaustive()
  {
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
        final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();
        m1.set(row, col, 256);
        Assert.assertFalse(m0.equals(m1));
        Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m1).position());
      }
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testExchangeRows()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(1, 0, 3.0f);
    m0.set(1, 1, 4.0f);

    MatrixM2x2FT.exchangeRows(m0, 0, 1, m1);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 3.0);
    Assert.assertTrue(m1.get(0, 1) == 4.0);

    Assert.assertTrue(m1.get(1, 0) == 1.0);
    Assert.assertTrue(m1.get(1, 1) == 2.0);

    MatrixM2x2FT.exchangeRows(m1, 0, 1);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);

    Assert.assertTrue(m1.get(1, 0) == 3.0);
    Assert.assertTrue(m1.get(1, 1) == 4.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testExchangeRowsAOverflow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.exchangeRows(m, 2, 0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testExchangeRowsAUnderflow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.exchangeRows(m, -1, 0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testExchangeRowsBOverflow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.exchangeRows(m, 0, 2);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testExchangeRowsBUnderflow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.exchangeRows(m, 0, -1);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testHashcodeNeqExhaustive()
  {
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
        final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();
        Assert.assertTrue(m0.hashCode() == m1.hashCode());
        m1.set(row, col, 256);
        Assert.assertFalse(m0.hashCode() == m1.hashCode());
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testInitializationFrom()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 5.0f);

    m0.set(1, 0, 11.0f);
    m0.set(1, 1, 13.0f);

    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>(m0);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 3.0);
    Assert.assertTrue(m1.get(0, 1) == 5.0);

    Assert.assertTrue(m1.get(1, 0) == 11.0);
    Assert.assertTrue(m1.get(1, 1) == 13.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testInitializationIdentity()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();

    Assert.assertTrue(m.get(0, 0) == 1.0);
    Assert.assertTrue(m.get(0, 1) == 0.0);

    Assert.assertTrue(m.get(1, 0) == 0.0);
    Assert.assertTrue(m.get(1, 1) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testInvertIdentity()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();

    {
      final Option<MatrixM2x2FT<A>> r = MatrixM2x2FT.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2FT<A>> s = (Some<MatrixM2x2FT<A>>) r;
      final MatrixM2x2FT<A> rm = s.value;
      Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(rm).position());

      Assert.assertTrue(MatrixM2x2FT.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM2x2FT.get(rm, 0, 1) == 0.0);

      Assert.assertTrue(MatrixM2x2FT.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM2x2FT.get(rm, 1, 1) == 1.0);
    }

    {
      final Option<MatrixM2x2FT<A>> r = MatrixM2x2FT.invert(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2FT<A>> s = (Some<MatrixM2x2FT<A>>) r;
      final MatrixM2x2FT<A> rm = s.value;
      Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(rm).position());

      Assert.assertTrue(MatrixM2x2FT.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM2x2FT.get(rm, 0, 1) == 0.0);

      Assert.assertTrue(MatrixM2x2FT.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM2x2FT.get(rm, 1, 1) == 1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testInvertSimple()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();

    m0.set(0, 0, 2.0f);
    m0.set(0, 1, 0.0f);

    m0.set(1, 0, 0.0f);
    m0.set(1, 1, 2.0f);

    {
      final Option<MatrixM2x2FT<A>> r = MatrixM2x2FT.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2FT<A>> s = (Some<MatrixM2x2FT<A>>) r;
      final MatrixM2x2FT<A> rm = s.value;
      Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(rm).position());

      Assert.assertTrue(rm.get(0, 0) == 0.5);
      Assert.assertTrue(rm.get(0, 1) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 0.5);
    }

    {
      final Option<MatrixM2x2FT<A>> r = MatrixM2x2FT.invert(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2FT<A>> s = (Some<MatrixM2x2FT<A>>) r;
      final MatrixM2x2FT<A> rm = s.value;
      Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(rm).position());

      Assert.assertTrue(rm.get(0, 0) == 2);
      Assert.assertTrue(rm.get(0, 1) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 2);
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testInvertZero()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();

    MatrixM2x2FT.setZero(m0);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m0).position());

    {
      final Option<MatrixM2x2FT<A>> r = MatrixM2x2FT.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }

    {
      final Option<MatrixM2x2FT<A>> r = MatrixM2x2FT.invert(m0);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testMultiplyIdentity()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> mr = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> r = MatrixM2x2FT.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(mr).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == mr.get(row, column));
        Assert.assertTrue(m1.get(row, column) == mr.get(row, column));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testMultiplyMutateIdentity()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }

    final MatrixM2x2FT<A> r = MatrixM2x2FT.multiply(m0, m1);
    Assert.assertSame(m0, r);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(r).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testMultiplyMutateSimple()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();

    MatrixM2x2FT.set(m0, 0, 0, 1.0f);
    MatrixM2x2FT.set(m0, 0, 1, 2.0f);
    MatrixM2x2FT.set(m0, 1, 0, 3.0f);
    MatrixM2x2FT.set(m0, 1, 1, 4.0f);

    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>(m0);
    final MatrixM2x2FT<A> r = MatrixM2x2FT.multiply(m0, m1);
    Assert.assertSame(r, m0);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(r).position());

    Assert.assertTrue(MatrixM2x2FT.get(r, 0, 0) == 7.0);
    Assert.assertTrue(MatrixM2x2FT.get(r, 0, 1) == 10.0);
    Assert.assertTrue(MatrixM2x2FT.get(r, 1, 0) == 15.0);
    Assert.assertTrue(MatrixM2x2FT.get(r, 1, 1) == 22.0);

    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(r).position());
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testMultiplySimple()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();

    MatrixM2x2FT.set(m0, 0, 0, 1.0f);
    MatrixM2x2FT.set(m0, 0, 1, 2.0f);
    MatrixM2x2FT.set(m0, 1, 0, 3.0f);
    MatrixM2x2FT.set(m0, 1, 1, 4.0f);

    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>(m0);
    final MatrixM2x2FT<A> mr = new MatrixM2x2FT<A>();

    final MatrixM2x2FT<A> r = MatrixM2x2FT.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(r).position());

    Assert.assertTrue(MatrixM2x2FT.get(r, 0, 0) == 7.0);
    Assert.assertTrue(MatrixM2x2FT.get(r, 0, 1) == 10.0);
    Assert.assertTrue(MatrixM2x2FT.get(r, 1, 0) == 15.0);
    Assert.assertTrue(MatrixM2x2FT.get(r, 1, 1) == 22.0);

    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(r).position());
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testMultiplyVectorSimpleND()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();

    MatrixM2x2FT.set(m0, 0, 0, 1.0f);
    MatrixM2x2FT.set(m0, 0, 1, 2.0f);
    MatrixM2x2FT.set(m0, 1, 0, 3.0f);
    MatrixM2x2FT.set(m0, 1, 1, 4.0f);

    final VectorI2F v = new VectorI2F(1.0f, 2.0f);
    final VectorM2F out = new VectorM2F();

    final VectorM2F r = MatrixM2x2FT.multiplyVector2F(m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertTrue(out.x == 5.0);
    Assert.assertTrue(out.y == 11.0);
  }

  @SuppressWarnings("static-method") @Test public <A> void testMultiplyZero()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> mr = new MatrixM2x2FT<A>();

    MatrixM2x2FT.setZero(m1);

    final MatrixM2x2FT<A> r = MatrixM2x2FT.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(r).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(mr.get(row, column) == 0.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testOutOfRangeNegativeColumn()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    m.get(0, -1);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testOutOfRangeNegativeRow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    m.get(-1, 0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testOutOfRangeOverflowColumn()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    m.get(0, 2);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testOutOfRangeOverflowRow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    m.get(2, 0);
  }

  @SuppressWarnings("static-method") @Test public <A> void testRow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    final VectorM2F v = new VectorM2F();

    MatrixM2x2FT.row(m, 0, v);
    Assert.assertTrue(v.x == 1.0);
    Assert.assertTrue(v.y == 0.0);

    MatrixM2x2FT.row(m, 1, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testRowOverflow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.row(m, 3, new VectorM2F());
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testRowUnderflow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.row(m, -1, new VectorM2F());
  }

  @SuppressWarnings("static-method") @Test public <A> void testScale()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> mr = new MatrixM2x2FT<A>();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.set(row, column, 3.0f);
      }
    }

    final MatrixM2x2FT<A> mk = MatrixM2x2FT.scale(m0, 5.0f, mr);
    Assert.assertSame(mr, mk);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(mr).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testScaleMutate()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m.set(row, column, 3.0f);
      }
    }

    final MatrixM2x2FT<A> mr = MatrixM2x2FT.scale(m, 5.0f);
    Assert.assertSame(mr, m);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(mr).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m.get(row, column) == 15.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testScaleRow()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);

    MatrixM2x2FT.scaleRow(m0, 0, 2.0f, m1);
    MatrixM2x2FT.scaleRow(m0, 1, 4.0f, m1);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 2.0);
    Assert.assertTrue(m1.get(0, 1) == 4.0);

    Assert.assertTrue(m1.get(1, 0) == 20.0);
    Assert.assertTrue(m1.get(1, 1) == 24.0);

    MatrixM2x2FT.scaleRow(m0, 0, 2.0f);
    MatrixM2x2FT.scaleRow(m0, 1, 4.0f);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m1).position());

    Assert.assertTrue(m0.get(0, 0) == 2.0);
    Assert.assertTrue(m0.get(0, 1) == 4.0);

    Assert.assertTrue(m0.get(1, 0) == 20.0);
    Assert.assertTrue(m0.get(1, 1) == 24.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testScaleRowMutateOverflow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.scaleRow(m, 2, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testScaleRowMutateUnderflow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.scaleRow(m, -1, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testScaleRowOverflow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> r = new MatrixM2x2FT<A>();
    MatrixM2x2FT.scaleRow(m, 2, 1.0f, r);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testScaleRowUnderflow()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> r = new MatrixM2x2FT<A>();
    MatrixM2x2FT.scaleRow(m, -1, 1.0f, r);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testSetGetIdentity()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();

    Assert.assertTrue(m.set(0, 0, 3.0f).get(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0f).get(0, 1) == 5.0);

    Assert.assertTrue(m.set(1, 0, 13.0f).get(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0f).get(1, 1) == 17.0);

    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m).position());
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testSetGetInterfaceIdentity()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();

    Assert.assertTrue(m.set(0, 0, 3.0f).getRowColumnF(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0f).getRowColumnF(0, 1) == 5.0);

    Assert.assertTrue(m.set(1, 0, 13.0f).getRowColumnF(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0f).getRowColumnF(1, 1) == 17.0);

    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m).position());
  }

  @SuppressWarnings("static-method") @Test public <A> void testStorage()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();

    m.set(0, 0, 0);
    m.set(0, 1, 1);

    m.set(1, 0, 100);
    m.set(1, 1, 101);

    {
      final FloatBuffer b = MatrixM2x2FT.floatBuffer(m);

      Assert.assertTrue(b.order() == ByteOrder.nativeOrder());
      Assert.assertEquals(0, b.position());

      Assert.assertTrue(b.get(0) == 0.0);
      Assert.assertTrue(b.get(1) == 100.0);

      Assert.assertTrue(b.get(2) == 1.0);
      Assert.assertTrue(b.get(3) == 101.0);
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testString()
  {
    final MatrixM2x2FT<A> m0 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m1 = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> m2 = new MatrixM2x2FT<A>();
    m2.set(0, 0, 2.0f);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));
  }

  @SuppressWarnings({ "static-method" }) @Test public <A> void testTrace()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    final float t = MatrixM2x2FT.trace(m);
    Assert.assertTrue(2.0f == t);
  }

  @SuppressWarnings("static-method") @Test public <A> void testTranspose()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    final MatrixM2x2FT<A> r = new MatrixM2x2FT<A>();

    m.set(0, 0, 0.0f);
    m.set(0, 1, 1.0f);

    m.set(1, 0, 4.0f);
    m.set(1, 1, 5.0f);

    final MatrixM2x2FT<A> k = MatrixM2x2FT.transpose(m, r);
    Assert.assertSame(k, r);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(r).position());

    Assert.assertTrue(m.get(0, 0) == 0.0);
    Assert.assertTrue(m.get(0, 1) == 1.0);

    Assert.assertTrue(m.get(1, 0) == 4.0);
    Assert.assertTrue(m.get(1, 1) == 5.0);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(m).position());

    Assert.assertTrue(r.get(0, 0) == 0.0);
    Assert.assertTrue(r.get(0, 1) == 4.0);

    Assert.assertTrue(r.get(1, 0) == 1.0);
    Assert.assertTrue(r.get(1, 1) == 5.0);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(r).position());
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testTransposeMutate()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();

    m.set(0, 0, 0.0f);
    m.set(0, 1, 1.0f);

    m.set(1, 0, 4.0f);
    m.set(1, 1, 5.0f);

    final MatrixM2x2FT<A> r = MatrixM2x2FT.transpose(m);
    Assert.assertSame(m, r);
    Assert.assertEquals(0, MatrixM2x2FT.floatBuffer(r).position());

    Assert.assertTrue(r.get(0, 0) == 0.0);
    Assert.assertTrue(r.get(0, 1) == 4.0);

    Assert.assertTrue(r.get(1, 0) == 1.0);
    Assert.assertTrue(r.get(1, 1) == 5.0);
  }

  @SuppressWarnings("static-method") @Test public <A> void testZero()
  {
    final MatrixM2x2FT<A> m = new MatrixM2x2FT<A>();
    MatrixM2x2FT.setZero(m);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m.get(row, column) == 0.0);
      }
    }
  }
}
