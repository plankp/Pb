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
