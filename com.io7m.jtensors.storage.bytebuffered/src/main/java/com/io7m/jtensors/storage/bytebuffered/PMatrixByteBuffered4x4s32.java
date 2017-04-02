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

package com.io7m.jtensors.storage.bytebuffered;

import com.io7m.jnull.NullCheck;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix4x4D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix4x4F;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4F;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * <p>A storage matrix.</p>
 * <p>Storage component type: {@code float}</p>
 * <p>Storage component count: {@code 4x4}</p>
 *
 * @param <A> A phantom type parameter (possibly representing a source
 *            coordinate system)
 * @param <B> A phantom type parameter (possibly representing a target
 *            coordinate system)
 */

public final class PMatrixByteBuffered4x4s32<A, B> implements
  PMatrixByteBuffered4x4Type<A, B>
{
  private final FloatBuffer view;
  private final ByteBuffer buffer;

  private PMatrixByteBuffered4x4s32(
    final ByteBuffer bb)
  {
    this.buffer = NullCheck.notNull(bb, "Buffer");
    this.view = this.buffer.asFloatBuffer();
  }

  /**
   * @param <A> A phantom type parameter (possibly representing a source
   *            coordinate system)
   * @param <B> A phantom type parameter (possibly representing a target
   *            coordinate system)
   *
   * @return A heap-backed matrix in native byte order
   */

  public static <A, B> PMatrixByteBuffered4x4Type<A, B> createHeap()
  {
    return createWith(ByteBuffer.allocate((4 * 4) * 4).order(ByteOrder.nativeOrder()));
  }

  /**
   * @param <A> A phantom type parameter (possibly representing a source
   *            coordinate system)
   * @param <B> A phantom type parameter (possibly representing a target
   *            coordinate system)
   *
   * @return A direct-memory matrix in native byte order
   */

  public static <A, B> PMatrixByteBuffered4x4Type<A, B> createDirect()
  {
    return createWith(ByteBuffer.allocateDirect((4 * 4) * 4).order(ByteOrder.nativeOrder()));
  }

  /**
   * @param <A> A phantom type parameter (possibly representing a source
   *            coordinate system)
   * @param <B> A phantom type parameter (possibly representing a target
   *            coordinate system)
   * @param b   A byte buffer
   *
   * @return A matrix backed by the given byte buffer
   */

  public static <A, B> PMatrixByteBuffered4x4Type<A, B> createWith(
    final ByteBuffer b)
  {
    return new PMatrixByteBuffered4x4s32<>(b);
  }

  private static int index(
    final int row,
    final int column)
  {
    return (column * 4) + row;
  }

  @Override
  public double r0c0()
  {
    return (double) this.view.get(index(0, 0));
  }

  @Override
  public double r0c1()
  {
    return (double) this.view.get(index(0, 1));
  }

  @Override
  public double r0c2()
  {
    return (double) this.view.get(index(0, 2));
  }

  @Override
  public double r0c3()
  {
    return (double) this.view.get(index(0, 3));
  }

  @Override
  public double r1c0()
  {
    return (double) this.view.get(index(1, 0));
  }

  @Override
  public double r1c1()
  {
    return (double) this.view.get(index(1, 1));
  }

  @Override
  public double r1c2()
  {
    return (double) this.view.get(index(1, 2));
  }

  @Override
  public double r1c3()
  {
    return (double) this.view.get(index(1, 3));
  }

  @Override
  public double r2c0()
  {
    return (double) this.view.get(index(2, 0));
  }

  @Override
  public double r2c1()
  {
    return (double) this.view.get(index(2, 1));
  }

  @Override
  public double r2c2()
  {
    return (double) this.view.get(index(2, 2));
  }

  @Override
  public double r2c3()
  {
    return (double) this.view.get(index(2, 3));
  }

  @Override
  public double r3c0()
  {
    return (double) this.view.get(index(3, 0));
  }

  @Override
  public double r3c1()
  {
    return (double) this.view.get(index(3, 1));
  }

  @Override
  public double r3c2()
  {
    return (double) this.view.get(index(3, 2));
  }

  @Override
  public double r3c3()
  {
    return (double) this.view.get(index(3, 3));
  }

  @Override
  public void setMatrix4x4D(
    final Matrix4x4D m)
  {
    this.view.put(index(0, 0), (float) m.r0c0());
    this.view.put(index(0, 1), (float) m.r0c1());
    this.view.put(index(0, 2), (float) m.r0c2());
    this.view.put(index(0, 3), (float) m.r0c3());

    this.view.put(index(1, 0), (float) m.r1c0());
    this.view.put(index(1, 1), (float) m.r1c1());
    this.view.put(index(1, 2), (float) m.r1c2());
    this.view.put(index(1, 3), (float) m.r1c3());

    this.view.put(index(2, 0), (float) m.r2c0());
    this.view.put(index(2, 1), (float) m.r2c1());
    this.view.put(index(2, 2), (float) m.r2c2());
    this.view.put(index(2, 3), (float) m.r2c3());

    this.view.put(index(3, 0), (float) m.r3c0());
    this.view.put(index(3, 1), (float) m.r3c1());
    this.view.put(index(3, 2), (float) m.r3c2());
    this.view.put(index(3, 3), (float) m.r3c3());
  }

  @Override
  public void setMatrix4x4F(
    final Matrix4x4F m)
  {
    this.view.put(index(0, 0), m.r0c0());
    this.view.put(index(0, 1), m.r0c1());
    this.view.put(index(0, 2), m.r0c2());
    this.view.put(index(0, 3), m.r0c3());

    this.view.put(index(1, 0), m.r1c0());
    this.view.put(index(1, 1), m.r1c1());
    this.view.put(index(1, 2), m.r1c2());
    this.view.put(index(1, 3), m.r1c3());

    this.view.put(index(2, 0), m.r2c0());
    this.view.put(index(2, 1), m.r2c1());
    this.view.put(index(2, 2), m.r2c2());
    this.view.put(index(2, 3), m.r2c3());

    this.view.put(index(3, 0), m.r3c0());
    this.view.put(index(3, 1), m.r3c1());
    this.view.put(index(3, 2), m.r3c2());
    this.view.put(index(3, 3), m.r3c3());
  }

  @Override
  public void setPMatrix4x4D(
    final PMatrix4x4D<A, B> m)
  {
    this.view.put(index(0, 0), (float) m.r0c0());
    this.view.put(index(0, 1), (float) m.r0c1());
    this.view.put(index(0, 2), (float) m.r0c2());
    this.view.put(index(0, 3), (float) m.r0c3());

    this.view.put(index(1, 0), (float) m.r1c0());
    this.view.put(index(1, 1), (float) m.r1c1());
    this.view.put(index(1, 2), (float) m.r1c2());
    this.view.put(index(1, 3), (float) m.r1c3());

    this.view.put(index(2, 0), (float) m.r2c0());
    this.view.put(index(2, 1), (float) m.r2c1());
    this.view.put(index(2, 2), (float) m.r2c2());
    this.view.put(index(2, 3), (float) m.r2c3());

    this.view.put(index(3, 0), (float) m.r3c0());
    this.view.put(index(3, 1), (float) m.r3c1());
    this.view.put(index(3, 2), (float) m.r3c2());
    this.view.put(index(3, 3), (float) m.r3c3());
  }

  @Override
  public void setPMatrix4x4F(
    final PMatrix4x4F<A, B> m)
  {
    this.view.put(index(0, 0), m.r0c0());
    this.view.put(index(0, 1), m.r0c1());
    this.view.put(index(0, 2), m.r0c2());
    this.view.put(index(0, 3), m.r0c3());

    this.view.put(index(1, 0), m.r1c0());
    this.view.put(index(1, 1), m.r1c1());
    this.view.put(index(1, 2), m.r1c2());
    this.view.put(index(1, 3), m.r1c3());

    this.view.put(index(2, 0), m.r2c0());
    this.view.put(index(2, 1), m.r2c1());
    this.view.put(index(2, 2), m.r2c2());
    this.view.put(index(2, 3), m.r2c3());

    this.view.put(index(3, 0), m.r3c0());
    this.view.put(index(3, 1), m.r3c1());
    this.view.put(index(3, 2), m.r3c2());
    this.view.put(index(3, 3), m.r3c3());
  }

  @Override
  public ByteBuffer byteBuffer()
  {
    return this.buffer;
  }
}