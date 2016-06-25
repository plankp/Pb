/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Plankp T.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package coreTypes;

import org.junit.*;
import static org.junit.Assert.*;

public class DNumberTest
{
  private static final double DELTA = 1e-15;
  
  @Test
  public void plusOperation()
  {
    DNumber left = new DNumber(10);
    left.plus(new DNumber(5.2));
    assertEquals(15.2, left.toDouble(), DELTA);
  }

  @Test
  public void minusOperation()
  {
    DNumber left = new DNumber(10);
    left.minus(new DNumber(5.2));
    assertEquals(4.8, left.toDouble(), DELTA);
  }
  
  @Test
  public void timesOperation()
  {
    DNumber left = new DNumber(10);
    left.times(new DNumber(5.2));
    assertEquals(52, left.toDouble(), DELTA);
  }

  @Test
  public void divideOperation()
  {
    DNumber left = new DNumber(10);
    left.divide(new DNumber(0.5));
    assertEquals(20, left.toDouble(), DELTA);
  }

  @Test
  public void moduloOperation()
  {
    DNumber left = new DNumber(10);
    left.modulo(new DNumber(3));
    assertEquals(1, left.toDouble(), DELTA);
  }

  @Test
  public void subscriptOperation()
  {
    DNumber left = new DNumber(15);
    assertEquals("5", left.subscript(new DNumber(1)).toString());
  }
}
