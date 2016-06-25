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

public class DNumber extends Data
{
  protected double val;

  public DNumber(double d)
  {
    val = d;
  }

  public double toDouble()
  {
    return val;
  }

  @Override
  public Data plus(Data d)
  {
    if (d instanceof DNumber)
      {
	val += ((DNumber) d).val;
	return this;
      }
    else if (d instanceof DTuple)
      {
	final DTuple tuple = (DTuple) d;
	tuple.data.add(0, this);
	return tuple;
      }
    else return new DString(this.toString() + d.toString());
  }

  @Override
  public Data minus(Data d)
  {
    if (d instanceof DNumber)
      {
	val -= ((DNumber) d).val;
	return this;
      }
    else if (d instanceof DTuple)
      {
	final DTuple tuple = (DTuple) d;
	for (int i = 0; i < tuple.data.size(); i++)
	  {
	    tuple.data.set(i, this.copy().minus(tuple.data.get(i)));
	  }
	return tuple;
      }
    else throw new UnsupportedOperationException
	   ("Cannot apply DNumber - " + d.getClass().getSimpleName());
  }

  @Override
  public Data times(Data d)
  {
    if (d instanceof DNumber)
      {
	val *= ((DNumber) d).val;
	return this;
      }
    else if (d instanceof DTuple)
      {
	final DTuple tuple = (DTuple) d;
	for (int i = 0; i < tuple.data.size(); i++)
	  {
	    tuple.data.set(i, this.copy().times(tuple.data.get(i)));
	  }
	return tuple;
      }
    else throw new UnsupportedOperationException
	   ("Cannot apply DNumber * " + d.getClass().getSimpleName());
  }

  @Override
  public Data divide(Data d)
  {
    if (d instanceof DNumber)
      {
	val /= ((DNumber) d).val;
	return this;
      }
    else if (d instanceof DTuple)
      {
	final DTuple tuple = (DTuple) d;
	for (int i = 0; i < tuple.data.size(); i++)
	  {
	    tuple.data.set(i, this.copy().divide(tuple.data.get(i)));
	  }
	return tuple;
      }
    else throw new UnsupportedOperationException
	   ("Cannot apply DNumber / " + d.getClass().getSimpleName());
  }

  @Override
  public Data modulo(Data d)
  {
    if (d instanceof DNumber)
      {
	int rightSide = (int) val;
	int leftSide = (int) ((DNumber) d).val;
	if (leftSide == 0) val = rightSide;
	else val = rightSide % leftSide;
	return this;
      }
    else if (d instanceof DTuple)
      {
	final DTuple tuple = (DTuple) d;
	for (int i = 0; i < tuple.data.size(); i++)
	  {
	    tuple.data.set(i, this.copy().modulo(tuple.data.get(i)));
	  }
	return tuple;
      }
    else throw new UnsupportedOperationException
	   ("Cannot apply DNumber % " + d.getClass().getSimpleName());
  }

  @Override
  public Data subscript(Data d)
  {
    if (d instanceof DNumber)
      {
	return new DString
	  (String.valueOf(String.valueOf(val)
			  .charAt((int) ((DNumber) d).val)));
      }
    else if (d instanceof DTuple)
      {
	final DTuple tuple = (DTuple) d;
	for (int i = 0; i < tuple.data.size(); i++)
	  {
	    tuple.data.set(i, this.copy().subscript(tuple.data.get(i)));
	  }
	return tuple;
      }
    else throw new UnsupportedOperationException
	   ("Cannot apply DNumber : " + d.getClass().getSimpleName());
  }
  
  @Override
  public Data copy()
  {
    return new DNumber(val);
  }

  @Override
  public String toString()
  {
    return String.valueOf(val);
  }

  @Override
  public boolean isTruthy()
  {
    return val != 0 && !Double.isInfinite(val) && !Double.isNaN(val);
  }

  @Override
  public DBoolean lessThan(Data d)
  {
    if (d instanceof DNumber)
      {
	return new DBoolean(val < ((DNumber) d).val);
      }
    throw new UnsupportedOperationException("Cannot apply DNumber < " +
					    d.getClass().getSimpleName());
  }

  @Override
  public DBoolean moreThan(Data d)
  {
    if (d instanceof DNumber)
      {
	return new DBoolean(val > ((DNumber) d).val);
      }
    throw new UnsupportedOperationException("Cannot apply DNumber > " +
					    d.getClass().getSimpleName());
  }

  @Override
  public boolean equals(Object o)
  {
    if (o instanceof DNumber)
      {
	return val == ((DNumber) o).val;
      }
    return false;
  }
}
