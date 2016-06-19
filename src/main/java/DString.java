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
}
