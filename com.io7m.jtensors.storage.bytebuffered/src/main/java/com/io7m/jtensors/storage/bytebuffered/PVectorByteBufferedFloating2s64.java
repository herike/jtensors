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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

/**
 * <p>A storage vector.</p>
 * <p>Storage component type: {@code double}</p>
 * <p>Storage component count: {@code 2}</p>
 *
 * @param <T> A phantom type parameter
 */

public final class PVectorByteBufferedFloating2s64<T>
  implements PVectorByteBufferedFloating2Type<T>
{
  private final DoubleBuffer view;
  private final ByteBuffer buffer;

  private PVectorByteBufferedFloating2s64(
    final ByteBuffer in_buffer)
  {
    this.buffer = NullCheck.notNull(in_buffer, "Buffer");
    this.view = this.buffer.asDoubleBuffer();
  }

  /**
   * @param <T> A phantom type parameter
   *
   * @return A heap-backed vector in native byte order
   */

  public static <T> PVectorByteBufferedFloating2Type<T> createHeap()
  {
    return createWith(ByteBuffer.allocate(2 * 8).order(ByteOrder.nativeOrder()));
  }

  /**
   * @param <T> A phantom type parameter
   *
   * @return A direct-memory-backed vector in native byte order
   */

  public static <T> PVectorByteBufferedFloating2Type<T> createDirect()
  {
    return createWith(ByteBuffer.allocateDirect(2 * 8).order(ByteOrder.nativeOrder()));
  }

  /**
   * @param <T> A phantom type parameter
   * @param b   A byte buffer
   *
   * @return A vector backed by the given byte buffer
   */

  public static <T> PVectorByteBufferedFloating2Type<T> createWith(
    final ByteBuffer b)
  {
    return new PVectorByteBufferedFloating2s64<>(b);
  }

  @Override
  public double x()
  {
    return this.view.get(0);
  }

  @Override
  public double y()
  {
    return this.view.get(1);
  }

  @Override
  public void setX(final double x)
  {
    this.view.put(0, x);
  }

  @Override
  public void setY(final double y)
  {
    this.view.put(1, y);
  }

  @Override
  public ByteBuffer byteBuffer()
  {
    return this.buffer;
  }
}
