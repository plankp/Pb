/*
The MIT License (MIT)

Copyright (c) 2016 Plankp T.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import java.util.List;
import java.util.ArrayList;

public final class DString extends Data
{
  private String val;

  public DString(String s)
  {
    val = s;
  }
  
  public DString(Object o)
  {
    val = o.toString();
  }

  @Override
  public Data plus(Data d)
  {
    val += d.toString();
    return this;
  }

  @Override
  public Data minus(Data d)
  {
    if (d instanceof DString) val = val.replaceAll(d.toString(), "");
    else if (d instanceof DNumber)
      {
	final int intrep = (int) ((DNumber) d).toDouble();
	if (intrep > 0) val = val.substring(intrep);
	else if (intrep < 0) val = val.substring(val.length() + intrep);
      }
    else
      {
	throw new RuntimeException
	  ("Cannot apply DString - " + d.getClass().getSimpleName());
      }
    return this;
  }

  @Override
  public Data times(Data d)
  {
    if (d instanceof DNumber)
      {
	val = String.valueOf
	  (new char[(int) ((DNumber) d).toDouble()]).replace("\0", val);
	return this;
      }
    throw new RuntimeException
      ("Cannot apply DString - " + d.getClass().getSimpleName());
  }

  @Override
  public Data divide(Data d)
  {
    if ((d instanceof DString) || (d instanceof DNumber))
      {
	return new DNumber(val.split(d.toString()).length);
      }
    throw new RuntimeException
      ("Cannot apply DString / " + d.getClass().getSimpleName());
  }

  @Override
  public Data modulo(Data d)
  {
    if ((d instanceof DString) || (d instanceof DNumber))
      {
	String[] arr = val.split(d.toString());
        List<Data> chunks = new ArrayList<>(arr.length);
	for (String s : arr) chunks.add(new DString(s));
	return new DTuple(chunks);
      }
    throw new RuntimeException
      ("Cannot apply DString % " + d.getClass().getSimpleName());
  }

  @Override
  public Data subscript(Data d)
  {
    if (d instanceof DNumber)
      {
	val = String.valueOf(val.charAt((int) ((DNumber) d).toDouble()));
	return this;
      }
    throw new RuntimeException
      ("Cannot apply DString : " + d.getClass().getSimpleName());
  }
  
  @Override
  public Data copy()
  {
    return new DString(val);
  }
  
  @Override
  public String toString()
  {
    return val;
  }

  @Override
  public boolean isTruthy()
  {
    return val.length() > 0;
  }
}
