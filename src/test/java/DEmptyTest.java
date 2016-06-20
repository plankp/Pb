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

import org.junit.*;
import static org.junit.Assert.*;

public class DEmptyTest
{

  @Test
  public void ShouldFailPlus()
  {
    try
      {
	DEmpty.getInstance().plus(null);
	fail("Plus is supposed to generate RuntimeException");
      }
    catch (RuntimeException ex)
      {
      }
  }
  
  @Test
  public void ShouldFailMinus()
  {
    try
      {
	DEmpty.getInstance().minus(null);
	fail("Plus is supposed to generate RuntimeException");
      }
    catch (RuntimeException ex)
      {
      }
  }

  @Test
  public void ShouldFailTimes()
  {
    try
      {
	DEmpty.getInstance().times(null);
	fail("Plus is supposed to generate RuntimeException");
      }
    catch (RuntimeException ex)
      {
      }
  }

  @Test
  public void ShouldFailDivide()
  {
    try
      {
	DEmpty.getInstance().divide(null);
	fail("Plus is supposed to generate RuntimeException");
      }
    catch (RuntimeException ex)
      {
      }
  }

  @Test
  public void ShouldFailModulo()
  {
    try
      {
	DEmpty.getInstance().modulo(null);
	fail("Plus is supposed to generate RuntimeException");
      }
    catch (RuntimeException ex)
      {
      }
  }

  @Test
  public void ShouldFailSubscript()
  {
    try
      {
	DEmpty.getInstance().subscript(null);
	fail("Plus is supposed to generate RuntimeException");
      }
    catch (RuntimeException ex)
      {
      }
  }
}
