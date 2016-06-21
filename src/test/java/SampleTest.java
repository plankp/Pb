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

import java.io.PrintStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class SampleTest
{
  StoredOutputStream from_SYSOUT;

  public SampleTest()
  {
    from_SYSOUT = new StoredOutputStream();
    System.setOut(new PrintStream(from_SYSOUT));
  }

  @Test
  public void EmptyIsHelloWorld()
  {
    App.readFile("sample/HelloWorld.pb");
    assertEquals("Hello, world!", from_SYSOUT.getAndClearBuf().trim());
  }

  @Test
  public void LegitHelloWorld()
  {
    App.readFile("sample/Legit_HelloWorld.pb");
    assertEquals("Hello, world!", from_SYSOUT.getAndClearBuf().trim());
  }
}

class StoredOutputStream extends OutputStream
{
  private StringBuffer buf = new StringBuffer();
  
  @Override
  public void write(int b)
  {
    synchronized (buf)
      {
	buf.append((char) b);
      }
  }

  public void clearBuf()
  {
    synchronized (buf)
      {
	buf.setLength(0);
      }
  }
  
  public String getBuf()
  {
    return buf.toString();
  }

  @Override
  public String toString()
  {
    return buf.toString();
  }

  public String getAndClearBuf()
  {
    synchronized (buf)
      {
	String dat = buf.toString();
	buf.setLength(0);
	return dat;
      }
  }
}
