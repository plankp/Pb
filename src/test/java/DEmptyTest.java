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
