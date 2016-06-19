public final class DNumber extends Data
{
  private double val;

  public DNumber(double d)
  {
    val = d;
  }

  public double toDouble()
  {
    return val;
  }

  @Override
  public Data plus(Data d)
  {
    if (d instanceof DNumber)
      {
	val += ((DNumber) d).val;
	return this;
      }
    else if (d instanceof DTuple)
      {
	final DTuple tuple = (DTuple) d;
	tuple.data.add(0, this);
	return tuple;
      }
    else return new DString(this.toString() + d.toString());
  }

  @Override
  public Data minus(Data d)
  {
    if (d instanceof DNumber)
      {
	val -= ((DNumber) d).val;
	return this;
      }
    else if (d instanceof DTuple)
      {
	final DTuple tuple = (DTuple) d;
	for (int i = 0; i < tuple.data.size(); i++)
	  {
	    tuple.data.set(i, this.copy().minus(tuple.data.get(i)));
	  }
	return tuple;
      }
    else throw new RuntimeException
	   ("Cannot apply DNumber - " + d.getClass().getSimpleName());
  }

  @Override
  public Data times(Data d)
  {
    if (d instanceof DNumber)
      {
	val *= ((DNumber) d).val;
	return this;
      }
    else if (d instanceof DTuple)
      {
	final DTuple tuple = (DTuple) d;
	for (int i = 0; i < tuple.data.size(); i++)
	  {
	    tuple.data.set(i, this.copy().times(tuple.data.get(i)));
	  }
	return tuple;
      }
    else throw new RuntimeException
	   ("Cannot apply DNumber * " + d.getClass().getSimpleName());
  }

  @Override
  public Data divide(Data d)
  {
    if (d instanceof DNumber)
      {
	val /= ((DNumber) d).val;
	return this;
      }
    else if (d instanceof DTuple)
      {
	final DTuple tuple = (DTuple) d;
	for (int i = 0; i < tuple.data.size(); i++)
	  {
	    tuple.data.set(i, this.copy().divide(tuple.data.get(i)));
	  }
	return tuple;
      }
    else throw new RuntimeException
	   ("Cannot apply DNumber / " + d.getClass().getSimpleName());
  }

  @Override
  public Data modulo(Data d)
  {
    if (d instanceof DNumber)
      {
	val = ((int) val) % ((int) ((DNumber) d).val);
	return this;
      }
    else if (d instanceof DTuple)
      {
	final DTuple tuple = (DTuple) d;
	for (int i = 0; i < tuple.data.size(); i++)
	  {
	    tuple.data.set(i, this.copy().modulo(tuple.data.get(i)));
	  }
	return tuple;
      }
    else throw new RuntimeException
	   ("Cannot apply DNumber % " + d.getClass().getSimpleName());
  }

  @Override
  public Data subscript(Data d)
  {
    if (d instanceof DNumber)
      {
	return new DString
	  (String.valueOf(String.valueOf(val)
			  .charAt((int) ((DNumber) d).val)));
      }
    else if (d instanceof DTuple)
      {
	final DTuple tuple = (DTuple) d;
	for (int i = 0; i < tuple.data.size(); i++)
	  {
	    tuple.data.set(i, this.copy().subscript(tuple.data.get(i)));
	  }
	return tuple;
      }
    else throw new RuntimeException
	   ("Cannot apply DNumber : " + d.getClass().getSimpleName());
  }
  
  @Override
  public Data copy()
  {
    return new DNumber(val);
  }

  @Override
  public String toString()
  {
    return String.valueOf(val);
  }
}
