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
