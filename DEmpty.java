public final class DEmpty extends Data
{
  private static DEmpty instance;

  private DEmpty()
  {
  }

  public static DEmpty getInstance()
  {
    synchronized (DEmpty.class)
      {
	if (instance == null)
	  {
	    instance = new DEmpty();
	  }
	return instance;
      }
  }
  
  @Override
  public Data plus(Data d)
  {
    throw new RuntimeException("Cannot apply Empty + Data");
  }

  @Override
  public Data minus(Data d)
  {
    throw new RuntimeException("Cannot apply Empty - Data");
  }

  @Override
  public Data times(Data d)
  {
    throw new RuntimeException("Cannot apply Empty * Data");
  }

  @Override
  public Data divide(Data d)
  {
    throw new RuntimeException("Cannot apply Empty / Data");
  }

  @Override
  public Data modulo(Data d)
  {
    throw new RuntimeException("Cannot apply Empty % Data");
  }

  @Override
  public Data subscript(Data d)
  {
    throw new RuntimeException("Cannot apply Empty : Data");
  }

  @Override
  public Data copy()
  {
    return instance;
  }
}
