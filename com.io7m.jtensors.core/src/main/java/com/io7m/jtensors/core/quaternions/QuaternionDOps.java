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

package com.io7m.jtensors.core.quaternions;

import com.io7m.junreachable.UnreachableCodeException;

final class QuaternionDOps
{
  private QuaternionDOps()
  {
    throw new UnreachableCodeException();
  }

  static double add(
    final double value0,
    final double value1)
  {
    return value0 + value1;
  }

  static double negate(
    final double value)
  {
    return -value;
  }

  static double subtract(
    final double value0,
    final double value1)
  {
    return value0 - value1;
  }

  static double multiply(
    final double value0,
    final double value1)
  {
    return value0 * value1;
  }

  static double divide(
    final double value0,
    final double value1)
  {
    return value0 / value1;
  }

  static double zero()
  {
    return 0.0;
  }

  static double zeroPointFive()
  {
    return 0.5;
  }

  static double one()
  {
    return 1.0;
  }

  static double two()
  {
    return 2.0;
  }

  static int compareLarge(
    final double value0,
    final double value1)
  {
    return Double.compare(value0, value1);
  }

  static double squareRootLarge(
    final double v)
  {
    return Math.sqrt(v);
  }

  static double sine(
    final double r)
  {
    return StrictMath.sin(r);
  }

  static double cosine(
    final double r)
  {
    return StrictMath.cos(r);
  }
}
