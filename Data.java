import java.io.Serializable;

public abstract class Data implements Serializable
{
  private final long serialVersionUID = -128348263L;

  public abstract Data plus(Data d);

  public abstract Data minus(Data d);

  public abstract Data times(Data d);

  public abstract Data divide(Data d);

  public abstract Data modulo(Data d);

  public abstract Data subscript(Data d);

  public abstract Data copy();
}
