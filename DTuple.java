import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public final class DTuple extends Data
{
  public final List<Data> data;

  public DTuple(Data... d)
  {
    data = Arrays.asList(d);
  }

  public DTuple(List<Data> d)
  {
    data = new ArrayList<>(d);
  }

  @Override
  public Data plus(Data d)
  {
    if (d instanceof DTuple)
      {
	final DTuple merge = (DTuple) d;
	data.addAll(merge.data);
      }
    else data.add(d);
    return this;
  }
  
  @Override
  public Data minus(Data d)
  {
    for (int i = 0; i < data.size(); i++)
      {
	data.set(i, data.get(i).minus(d));
      }
    return this;
  }
  
  @Override
  public Data times(Data d)
  {
    for (int i = 0; i < data.size(); i++)
      {
	data.set(i, data.get(i).times(d));
      }
    return this;
  }
  
  @Override
  public Data divide(Data d)
  {
    for (int i = 0; i < data.size(); i++)
      {
	data.set(i, data.get(i).divide(d));
      }
    return this;
  }
  
  @Override
  public Data modulo(Data d)
  {
    for (int i = 0; i < data.size(); i++)
      {
	data.set(i, data.get(i).modulo(d));
      }
    return this;
  }
  
  @Override
  public Data subscript(Data d)
  {
    if (d instanceof DNumber) return data.get((int) ((DNumber) d).toDouble());
    else throw new RuntimeException
	   ("Cannot apply DTuple : " + d.getClass().getSimpleName());
  }
  
  @Override
  public Data copy()
  {
    return new DTuple(data);
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder("{");
    for (Data el : data)
      {
	sb.append(el.toString()).append(',');
      }
    sb.setCharAt(sb.length() - 1, '}');
    return sb.toString();
  }
}
