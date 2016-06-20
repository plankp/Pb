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

import java.io.Serializable;

public final class Tuple<T, U> implements Serializable
{
  private final long serialVersionUID = 942356781263L;
  
  private T left;
  
  private U right;
  
  public Tuple()
  {
  }
  
  public Tuple(T t, U u)
  {
    set(t, u);
  }
  
  public Tuple(Tuple<? extends T, ? extends U> tuple)
  {
    set(tuple);
  }
  
  public T getLeft()
  {
    return left;
  }
  
  public U getRight()
  {
    return right;
  }
  
  public void setLeft(T t)
  {
    left = t;
  }
  
  public void setRight(U u)
  {
    right = u;
  }
  
  public void set(T t, U u)
  {
    left = t;
    right = u;
  }
    
  public void set(Tuple<? extends T, ? extends U> tuple)
  {
    left = tuple.left;
    right = tuple.right;
  }
  
  @Override
  public String toString()
  {
    return "(" + left + "," + right + ")";
  }
}
