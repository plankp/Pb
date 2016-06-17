import java.util.Map;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Lexer
{
  public static enum TokenType
  {
    TEXT("# .*[\r\n]?"),
    ASSIGN(".="),
    BINARYOP("\\*|/|\\+|-|%"),
    NUMBER("_?(0|([1-9][0-9]*))(\\.[0-9]+)?"),
    WHITESPACE("[ \t\f\r\n]+"),
    READ("@."),
    COMMENT("."); // Ok... Anything we don't care is a comment right?

    public final String pattern;

    private TokenType(String pattern)
    {
      this.pattern = pattern;
    }
  }

  public static class Token
  {
    public TokenType type;
    public String data;

    public Token(TokenType type, String data)
    {
      this.type = type;
      this.data = data;
    }

    @Override
    public String toString() {
      return String.format("(%s %s)", type.name(), data);
    }
  }
  
  private final static Map<Character, Data> varmap = new HashMap<>();
  
  public static List<Token> lex(String input)
  {
    List<Token> tokens = new ArrayList<>();
    
    StringBuilder tokenPatternsBuffer = new StringBuilder();
    for (TokenType tokenType : TokenType.values())
      tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
    Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));
    
    Matcher matcher = tokenPatterns.matcher(input);
    while (matcher.find())
      {
	if (matcher.group(TokenType.COMMENT.name()) != null
	    || matcher.group(TokenType.WHITESPACE.name()) != null)
	  {
	    // -> HIDDEN
	    continue;
	  }
	
	if (matcher.group(TokenType.NUMBER.name()) != null)
	  {
	    tokens.add(new Token(TokenType.NUMBER, matcher.group(TokenType.NUMBER.name())));
	  }
	else if (matcher.group(TokenType.BINARYOP.name()) != null)
	  {
	    tokens.add(new Token(TokenType.BINARYOP, matcher.group(TokenType.BINARYOP.name())));
	  }
	else if (matcher.group(TokenType.READ.name()) != null)
	  {
	    tokens.add(new Token(TokenType.READ, matcher.group(TokenType.READ.name())));
	  }
	else if (matcher.group(TokenType.ASSIGN.name()) != null)
	  {
	    tokens.add(new Token(TokenType.ASSIGN, matcher.group(TokenType.ASSIGN.name())));
	  }
	else if (matcher.group(TokenType.TEXT.name()) != null)
	  {
	    tokens.add(new Token(TokenType.TEXT, matcher.group(TokenType.TEXT.name())));
	  }
	else
	  {
	    throw new RuntimeException("Syntax error. Start:" + matcher.start() + " Text:" + matcher.group());
	  }
      }
    
    return tokens;
  }
  
  public static void parse(final List<Token> toks)
  {
    final int toksSize = toks.size();
    if (toksSize == 0) return;
    
    int counter = 0;
    while (counter < toks.size() - 1)
      {
	final Token tok = toks.get(counter);
	if (tok.type == TokenType.ASSIGN)
	  {
	    char id = tok.data.charAt(0);
	    Tuple<Integer, Data> tuple = visitValue(counter + 1, toks);
	    counter = tuple.left;
	    final Data tmpd = tuple.right;
	    varmap.put(id, tmpd);
	    if (id == ' ') System.out.println(tmpd);
	  }
	else
	  {
	    throw new RuntimeException("Expected ASSIGN. Found " + tok);
	  }
      }
  }
  
  public final static class Tuple<T, U>
  {
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
  
  private static final Data EMPTY_DATA = new Data("");
  
  public static Tuple<Integer, Data> visitValue(int counter,
						final List<Token> toks)
  {
    Data result = EMPTY_DATA;
    Token tok = toks.get(counter);
    if (tok.type == TokenType.READ)
      {
	char id = tok.data.charAt(1);
	if (id == ' ')
	  {
	    if (SYSIN.hasNextLine()) result.set(SYSIN.nextLine());
	    // otherwise, result stores an empty string
	  }
	else result = varmap.get(id);
      }
    else if (tok.type == TokenType.NUMBER)
      {
	String rawnum = tok.data;
	if (rawnum.charAt(0) == '_') rawnum = "-" + rawnum.substring(1);
	result.set(Double.parseDouble(rawnum));
      }
    else if (tok.type == TokenType.TEXT)
      {
	String rawstr = tok.data;
	result.set(rawstr.substring(2));
      }
    else
      {
	throw new RuntimeException("Expected READ or NUMBER. Found " + tok);
      }
    if (counter < toks.size() - 1)
      {
	tok = toks.get(++counter);
	if (tok.type == TokenType.BINARYOP)
	  {
	    final String op = tok.data;
	    Data clone = result.copy();
	    Tuple<Integer, Data> tuple = visitValue(counter + 1, toks);
	    counter = tuple.left;
	    Data tmprst = tuple.right;
	    result = clone;
	    
	    switch (op)
	      {
	      case "+":
		result.plus(tmprst);
		break;
	      case "-":
		result.minus(tmprst);
		break;
	      case "*":
		result.times(tmprst);
		break;
	      case "/":
		result.divide(tmprst);
		break;
	      case "%":
		result.modulo(tmprst);
		break;
	      }
	  }
      }
    return new Tuple<>(counter, result);
  }

  static final Scanner SYSIN = new Scanner(System.in);
  
  public static void main(String[] args)
  {
    while (true)
      {
	System.out.print(">> ");
	if (SYSIN.hasNextLine())
	  {
	    String instr = SYSIN.nextLine();
	    List<Token> tokens = lex(instr);
	    parse(tokens);
	  }
	else break;
      }
  }
}
