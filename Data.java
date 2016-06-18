import java.io.Serializable;

public final class Data implements Serializable
{

  // Use this as placeholder value
  public static final Data EMPTY_DATA = new Data("");
  
  private final long serialVersionUID = -128348263L;

  private Object data;

  private boolean numberFlag;
  
  public Data (double d)
  {
    numberFlag = true;
    data = d;
  }

  public Data (String s)
  {
    numberFlag = false;
    data = s;
  }

  public boolean isNumber()
  {
    return numberFlag;
  }

  public void set (String s)
  {
    numberFlag = false;
    data = s;
  }

  public void set (double d)
  {
    numberFlag = true;
    data = d;
  }

  public void plus (Data d)
  {
    if (d.numberFlag) plus((double) d.data);
    else plus(d.data.toString());
  }
  
  public void minus (Data d)
  {
    if (d.numberFlag) minus((double) d.data);
    else minus(d.data.toString());
  }
  
  public void times (Data d)
  {
    if (d.numberFlag) times((double) d.data);
    else times(d.data.toString());
  }
  
  public void divide (Data d)
  {
    if (d.numberFlag) divide((double) d.data);
    else divide(d.data.toString());
  }
  
  public void modulo (Data d)
  {
    if (d.numberFlag) modulo((double) d.data);
    else modulo(d.data.toString());
  }

  public void subscript (Data d)
  {
    if (d.numberFlag) subscript((double) d.data);
    else subscript(d.data.toString());
  }
  
  public void plus (String s)
  {
    numberFlag = false;
    data += s;
  }

  public void plus (double d)
  {
    if (numberFlag) data = ((double) data) + d;
    else data = ((String) data) + d;
  }

  public void minus (String s)
  {
    if (numberFlag) throw new RuntimeException("Cannot apply Number - String");
    throw new RuntimeException("Cannot apply String - String");
  }

  public void minus (double d)
  {
    if (numberFlag) data = ((double) data) - d;
    else
      {
	if (d == 0) return;
	else if (d > 0) data = ((String) data).substring((int) d);
	else
	  {
	    String tmp = (String) data;
	    data = tmp.substring(0, (int) (tmp.length() + d));
	  }
      }
  }

  public void times (String s)
  {
    if (numberFlag)
      {
	numberFlag = false;
	data = new String(new char[(int) (double) data]).replace("\0", s);
      }
    else throw new RuntimeException("Cannot apply String * String");
  }

  public void times (double d)
  {
    if (numberFlag) data = ((double) data) * d;
    else data = new String(new char[(int) d]).replace("\0", (String) data);
  }

  public void divide (String s)
  {
    if (numberFlag) throw new RuntimeException("Cannot apply Number / String");
    numberFlag = true;
    data = ((String) data).split(s).length;
  }

  public void divide (double d)
  {
    if (numberFlag) data = ((double) data) / d;
    else
      {
	numberFlag = true;
	data = ((String) data).split(String.valueOf(d)).length;
      }
  }

  public void modulo (String s)
  {
    if (numberFlag) throw new RuntimeException("Cannot apply Number % String");
    data = ((String) data).replaceAll(s, "");
  }

  public void modulo (double d)
  {
    if (numberFlag) data = ((int) data) % d;
    else
      {
	data = ((String) data).replaceAll(String.valueOf(d), "");
      }
  }

  public void subscript (String s)
  {
    numberFlag = true;
    data = data.toString().indexOf(s) + 0.0;
  }
  
  public void subscript (double d)
  {
    if (numberFlag)
      {
	numberFlag = false;
	data = String.valueOf(data.toString().charAt((int) d));
      }
    else data = String.valueOf(((String) data).charAt((int) d));
  }
  
  public Data copy()
  {
    if (numberFlag) return new Data((double) data);
    return new Data(String.valueOf(data));
  }
    
  @Override
  public String toString()
  {
    return data.toString();
  }
}
