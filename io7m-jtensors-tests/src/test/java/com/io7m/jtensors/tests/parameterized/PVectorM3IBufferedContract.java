/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.parameterized.PVector3IType;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM3IBufferedContract<T, V extends PVector3IType<T>>
  extends PVectorM3IContract<T, V>
{
  protected abstract PVector3IType<T> newVectorM3IAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetZ()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getZI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetZ()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setZI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet3I()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set3I(23, 23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2I()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2I(23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom3I()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom3I(new VectorI3I(23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2I()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2I(new VectorI3I(23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 100L);
    v.getXI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 100L);
    v.getYI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetZ()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 100L);
    v.getZI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 100L);
    v.setXI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 100L);
    v.setYI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetZ()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 100L);
    v.setZI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet3I()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 100L);
    v.set3I(23, 23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2I()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 100L);
    v.set2I(23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom3I()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 100L);
    v.copyFrom3I(new VectorI3I(23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2I()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 100L);
    v.copyFrom2I(new VectorI3I(23, 23, 23));
  }

  @Test public final void testCopyFrom3IIdentity()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 0L);
    v.copyFrom3I(new VectorI3I(23, 24, 25));

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
    Assert.assertEquals(25L, (long) v.getZI());
  }

  @Test public final void testCopyFrom2IIdentity()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 0L);
    v.copyFrom2I(new VectorI3I(23, 24, 25));

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
  }

  @Test public final void testSet3IIdentity()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 0L);
    v.set3I(23, 24, 25);

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
    Assert.assertEquals(25L, (long) v.getZI());
  }

  @Test public final void testSet2IIdentity()
  {
    final PVector3IType<T> v = this.newVectorM3IAtIndexFromSize(
      100L, 0L);
    v.set2I(23, 24);

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
  }
}