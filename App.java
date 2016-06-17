import java.util.Map;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class App
{
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
	else if (matcher.group(TokenType.RTEXT.name()) != null)
	  {
	    tokens.add(new Token(TokenType.RTEXT, matcher.group(TokenType.RTEXT.name())));
	  }
	else if (matcher.group(TokenType.LPAREN.name()) != null)
	  {
	    tokens.add(new Token(TokenType.LPAREN, matcher.group(TokenType.LPAREN.name())));
	  }
	else if (matcher.group(TokenType.RPAREN.name()) != null)
	  {
	    tokens.add(new Token(TokenType.RPAREN, matcher.group(TokenType.RPAREN.name())));
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
	    counter = tuple.getLeft();
	    final Data tmpd = tuple.getRight();
	    
	    if (id == ' ') System.out.println(tmpd);
	    else varmap.put(id, tmpd);
	  }
	else
	  {
	    throw new RuntimeException("Expected ASSIGN. Found " + tok);
	  }
      }
  }
  
  public static Tuple<Integer, Data> visitValue(int counter,
						final List<Token> toks)
  {
    Data result = Data.EMPTY_DATA;
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
    else if (tok.type == TokenType.RTEXT)
      {
	String rawstr = tok.data;
	int len = rawstr.length();
	if (len > 4)
	  {
	    result.set(rawstr.substring(2, len - 2));
	  }
      }
    else if (tok.type == TokenType.LPAREN)
      {
	int bracketsBalance = 1;
	List<Token> bracketsExpr = new ArrayList<>();
	for (counter++; counter < toks.size(); counter++)
	  {
	    final Token brtok = toks.get(counter);
	    if (brtok.type == TokenType.LPAREN) bracketsBalance++;
	    else if (brtok.type == TokenType.RPAREN) bracketsBalance--;
	    
	    if (bracketsBalance == 0) break;
	    bracketsExpr.add(brtok);
	  }
	Tuple<Integer, Data> expr = visitValue(0, bracketsExpr);
	result = expr.getRight();
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
	    counter = tuple.getLeft();
	    Data tmprst = tuple.getRight();
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
