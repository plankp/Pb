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

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import com.ymcmp.Pb2.Data;
import com.ymcmp.Pb2.ISizable;

public final class DTuple extends Data implements ISizable
{
  public final List<Data> data;

  public DTuple(Data... d)
  {
    data = Arrays.asList(d);
  }

  public DTuple(List<Data> d)
  {
    data = new ArrayList<>(d);
  }

  @Override
  public Data plus(Data d)
  {
    if (d instanceof DTuple)
      {
	final DTuple merge = (DTuple) d;
	data.addAll(merge.data);
      }
    else data.add(d);
    return this;
  }
  
  @Override
  public Data minus(Data d)
  {
    for (int i = 0; i < data.size(); i++)
      {
	data.set(i, data.get(i).minus(d));
      }
    return this;
  }
  
  @Override
  public Data times(Data d)
  {
    for (int i = 0; i < data.size(); i++)
      {
	data.set(i, data.get(i).times(d));
      }
    return this;
  }
  
  @Override
  public Data divide(Data d)
  {
    for (int i = 0; i < data.size(); i++)
      {
	data.set(i, data.get(i).divide(d));
      }
    return this;
  }
  
  @Override
  public Data modulo(Data d)
  {
    for (int i = 0; i < data.size(); i++)
      {
	data.set(i, data.get(i).modulo(d));
      }
    return this;
  }
  
  @Override
  public Data subscript(Data d)
  {
    if (d instanceof DNumber) return data.get((int) ((DNumber) d).toDouble());
    else throw new RuntimeException
	   ("Cannot apply DTuple : " + d.getClass().getSimpleName());
  }
  
  @Override
  public Data copy()
  {
    return new DTuple(data);
  }

  @Override
  public String toString()
  {
    if (data.isEmpty()) return "{}";

    StringBuilder sb = new StringBuilder("{");
    for (Data el : data)
      {
	sb.append(el.toString()).append(',');
      }
    sb.setCharAt(sb.length() - 1, '}');
    return sb.toString();
  }

  @Override
  public boolean isTruthy()
  {
    return !data.isEmpty();
  }
  
  @Override
  public int getSize()
  {
    return data.size();
  }
  
  @Override
  public DBoolean lessThan(Data d)
  {
    if (d instanceof ISizable)
      {
	return new DBoolean(data.size() < ((ISizable) d).getSize());
      }
    throw new RuntimeException("Cannot apply DTuple < "
			       + d.getClass().getSimpleName());
  }

  @Override
  public DBoolean moreThan(Data d)
  {
    if (d instanceof ISizable)
      {
	return new DBoolean(data.size() > ((ISizable) d).getSize());
      }
    throw new RuntimeException("Cannot apply DTuple > "
			       + d.getClass().getSimpleName());
  }

  @Override
  public boolean equals(Object o)
  {
    if (o instanceof DTuple)
      {
	return data.equals(((DTuple) o).data);
      }
    return false;
  }
}
