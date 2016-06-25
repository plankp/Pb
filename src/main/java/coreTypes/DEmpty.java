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

import com.ymcmp.Pb2.Data;

public final class DEmpty extends Data
{
  private static DEmpty instance;

  private DEmpty()
  {
  }

  public static DEmpty getInstance()
  {
    synchronized (DEmpty.class)
      {
	if (instance == null)
	  {
	    instance = new DEmpty();
	  }
	return instance;
      }
  }
  
  @Override
  public Data plus(Data d)
  {
    throw new RuntimeException("Cannot apply Empty + Data");
  }

  @Override
  public Data minus(Data d)
  {
    throw new RuntimeException("Cannot apply Empty - Data");
  }

  @Override
  public Data times(Data d)
  {
    throw new RuntimeException("Cannot apply Empty * Data");
  }

  @Override
  public Data divide(Data d)
  {
    throw new RuntimeException("Cannot apply Empty / Data");
  }

  @Override
  public Data modulo(Data d)
  {
    throw new RuntimeException("Cannot apply Empty % Data");
  }

  @Override
  public Data subscript(Data d)
  {
    throw new RuntimeException("Cannot apply Empty : Data");
  }

  @Override
  public Data copy()
  {
    return instance;
  }

  @Override
  public String toString()
  {
    return "<EMPTY@" + hashCode() + ">";
  }

  @Override
  public boolean isTruthy()
  {
    return false;
  }

  @Override
  public DBoolean lessThan(Data d)
  {
    throw new RuntimeException("Cannot apply Empty < Data");
  }

  @Override
  public DBoolean moreThan(Data d)
  {
    throw new RuntimeException("Cannot apply Empty > Data");
  }

  @Override
  public boolean equals(Object o)
  {
    if (o instanceof DEmpty) return true;
    return false;
  }
}
