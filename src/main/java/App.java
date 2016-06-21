/*
The MIT License (MIT)

Copyright (c) 2016 Plankp T.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.FileNotFoundException;

public class App
{
  private final static Map<Character, Data> varmap = new HashMap<>();
  
  public static List<Token> lex(String input)
  {
    List<Token> tokens = new ArrayList<>();
    
    StringBuilder tokenPatternsBuffer = new StringBuilder();
    for (TokenType tokenType : TokenType.values())
      tokenPatternsBuffer.append(String.format("|(?<%s>%s)",
					       tokenType.name(),
					       tokenType.pattern));
    Pattern tokenPatterns = Pattern.compile
      (new String(tokenPatternsBuffer.substring(1)));
    
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
	    tokens.add(new Token(TokenType.NUMBER,
				 matcher.group(TokenType.NUMBER.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.REDO.name()) != null)
	  {
	    tokens.add(new Token(TokenType.REDO,
				 matcher.group(TokenType.REDO.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.SKIP.name()) != null)
	  {
	    tokens.add(new Token(TokenType.SKIP,
				 matcher.group(TokenType.SKIP.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.BINARYOP.name()) != null)
	  {
	    tokens.add(new Token(TokenType.BINARYOP,
				 matcher.group(TokenType.BINARYOP.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.READ.name()) != null)
	  {
	    tokens.add(new Token(TokenType.READ,
				 matcher.group(TokenType.READ.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.ASSIGN.name()) != null)
	  {
	    tokens.add(new Token(TokenType.ASSIGN,
				 matcher.group(TokenType.ASSIGN.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.TEXT.name()) != null)
	  {
	    tokens.add(new Token(TokenType.TEXT,
				 matcher.group(TokenType.TEXT.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.RTEXT.name()) != null)
	  {
	    tokens.add(new Token(TokenType.RTEXT,
				 matcher.group(TokenType.RTEXT.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.LPAREN.name()) != null)
	  {
	    tokens.add(new Token(TokenType.LPAREN,
				 matcher.group(TokenType.LPAREN.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.RPAREN.name()) != null)
	  {
	    tokens.add(new Token(TokenType.RPAREN,
				 matcher.group(TokenType.RPAREN.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.LTUPLE.name()) != null)
	  {
	    tokens.add(new Token(TokenType.LTUPLE,
				 matcher.group(TokenType.LTUPLE.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.RTUPLE.name()) != null)
	  {
	    tokens.add(new Token(TokenType.RTUPLE,
				 matcher.group(TokenType.RTUPLE.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.LBLOCK.name()) != null)
	  {
	    tokens.add(new Token(TokenType.LBLOCK,
				 matcher.group(TokenType.LBLOCK.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else if (matcher.group(TokenType.RBLOCK.name()) != null)
	  {
	    tokens.add(new Token(TokenType.RBLOCK,
				 matcher.group(TokenType.RBLOCK.name()),
				 matcher.start(),
				 matcher.end()));
	  }
	else throw new LexerException(matcher);
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
	counter = visitStmt(counter, toks);
      }
  }

  public static int visitStmt(int counter, final List<Token> toks)
  {
    int oldCounter = counter;
    try
      {
	final Token tok = toks.get(counter);
	if (tok.type == TokenType.ASSIGN)
	  {
	    char id = tok.data.charAt(0);
	    Tuple<Integer, Data> tuple = visitValue(counter + 1, toks);
	    counter = tuple.getLeft();
	    final Data tmpd = tuple.getRight().copy();
	    
	    switch (id)
	      {
	      case ' ':
		System.out.println(tmpd);
		break;
	      case '\t':
		System.err.println(tmpd);
	      case '_':
		break;
	      case '!':
		throw new RuntimeException(tmpd.toString());
	      default:
		varmap.put(id, tmpd);
	      }
	  }
	else if (tok.type == TokenType.TEXT)
	  {
	    // These can be directives?
	  }
	else
	  {
	    throw new ParserException(tok, "ASSIGN", "TEXT");
	  }
	try
	  {
	    Token postAct = toks.get(counter++);
	    if (postAct.type == TokenType.REDO)
	      {
		visitStmt(oldCounter, toks);
	      }
	    else if (postAct.type == TokenType.SKIP)
	      {
		counter++;
	      }
	    else counter--;
	  }
	catch (IndexOutOfBoundsException ex)
	  {
	  }
	return counter;
      }
    catch (IndexOutOfBoundsException ex)
      {
	throw new ParserException(null, "ASSIGN", "TEXT");
      }
  }
  
  public static Tuple<Integer, Data> visitValue(int counter,
						final List<Token> toks)
  {
    try
      {
	Data result = DEmpty.getInstance();
	Token tok = toks.get(counter);
	if (tok.type == TokenType.READ)
	  {
	    char id = tok.data.charAt(1);
	    switch (id)
	      {
	      case ' ':		// Yields user input
		if (SYSIN.hasNextLine()) result = new DString(SYSIN.nextLine());
		else result = new DString("");
		break;
	      case '\t':
		if (SYSIN.hasNextDouble())
		  {
		    result = new DNumber(SYSIN.nextDouble());
		  }
		else result = new DNumber(0);
		break;
	      case '_':
		result = new DNumber(Math.random());
		break;
	      case '!':		// Yields DEmpty (Like Null in Java)
		break;
	      default:		// Yields variable value
		if (varmap.containsKey(id)) result = varmap.get(id);
	      }
	  }
	else if (tok.type == TokenType.NUMBER)
	  {
	    String rawnum = tok.data;
	    if (rawnum.charAt(0) == '_') rawnum = "-" + rawnum.substring(1);
	    result = new DNumber(Double.parseDouble(rawnum));
	  }
	else if (tok.type == TokenType.TEXT)
	  {
	    String rawstr = tok.data;
	    result = new DString(rawstr.substring(2));
	  }
	else if (tok.type == TokenType.RTEXT)
	  {
	    String rawstr = tok.data;
	    int len = rawstr.length();

	    if (len == 4) result = new DString("");
	    else result = new DString(rawstr.substring(2, len - 2));
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
	else if (tok.type == TokenType.LTUPLE)
	  {
	    int bracketsBalance = 1;
	    List<Token> bracketsExpr = new ArrayList<>();
	    for (counter++; counter < toks.size(); counter++)
	      {
		final Token brtok = toks.get(counter);
		if (brtok.type == TokenType.LTUPLE) bracketsBalance++;
		else if (brtok.type == TokenType.RTUPLE) bracketsBalance--;
		
		if (bracketsBalance == 0) break;
		bracketsExpr.add(brtok);
	      }
	    int oldCounter = -1;
	    int elmCounter = 0;

	    List<Data> tupleData = new ArrayList<>();
	    while (oldCounter != elmCounter)
	      {
		try
		  {
		    final Tuple<Integer, Data> expr =
		      visitValue(elmCounter, bracketsExpr);
		    oldCounter = elmCounter;
		    elmCounter = expr.getLeft();
		    tupleData.add(expr.getRight().copy());
		  }
		catch (ParserException ex)
		  {
		    break;
		  }
	      }
	    result = new DTuple(tupleData);
	  }
	else if (tok.type == TokenType.LBLOCK)
	  {
	    int bracketsBalance = 1;
	    List<Token> bracketsExpr = new ArrayList<>();
	    for (counter++; counter < toks.size(); counter++)
	      {
		final Token brtok = toks.get(counter);
		if (brtok.type == TokenType.LBLOCK) bracketsBalance++;
		else if (brtok.type == TokenType.RBLOCK) bracketsBalance--;
		
		if (bracketsBalance == 0) break;
		bracketsExpr.add(brtok);
	      }
	    int oldCounter = -1;
	    int elmCounter = 0;
	    
	    boolean processed = false;
	    Boolean condition = null;
	    while (oldCounter != elmCounter)
	      {
		try
		  {
		    if (condition == null)
		      {
			final Tuple<Integer, Data> expr =
			  visitValue(elmCounter, bracketsExpr);
			oldCounter = elmCounter;
			elmCounter = expr.getLeft();
			Data tmp = expr.getRight();
			condition = tmp.isTruthy();
		      }
		    else if (condition)
		      {
			processed = true;
			int tmp = visitStmt(elmCounter, bracketsExpr) - 1;
			oldCounter = elmCounter;
			elmCounter = tmp;
		      }
		    else break;
		  }
		catch (ParserException ex)
		  {
		    processed = false;
		    break;
		  }
	      }
	    if (condition == null)
	      throw new ParserException(null, "rule value");
	    result = new DBoolean(processed ? condition : !condition);
	  }
	else
	  {
	    throw new ParserException
	      (tok,
	       "READ", "NUMBER", "LTUPLE", "LBLOCK", "LPAREN", "TEXT", "RTEXT");
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
		    result = result.plus(tmprst);
		    break;
		  case "-":
		    result = result.minus(tmprst);
		    break;
		  case "*":
		    result = result.times(tmprst);
		    break;
		  case "/":
		    result = result.divide(tmprst);
		    break;
		  case "%":
		    result = result.modulo(tmprst);
		    break;
		  case ":":
		    result = result.subscript(tmprst);
		    break;
		  case "<":
		    result = result.lessThan(tmprst);
		    break;
		  case ">":
		    result = result.moreThan(tmprst);
		    break;
		  }
	      }
	  }
	return new Tuple<>(counter, result);
      }
    catch (IndexOutOfBoundsException ex)
      {
	throw new ParserException
	  (null,
	   "READ", "NUMBER", "LTUPLE", "LBLOCK", "LPAREN", "TEXT", "RTEXT");
      }
  }
  
  static final Scanner SYSIN = new Scanner(System.in);

  public static void scanLine(Scanner scanner)
  {
    String instr = scanner.nextLine();
    List<Token> tokens = lex(instr);
    parse(tokens);
  }

  public static void readFile(String path)
  {
    File src = new File(path);
    try
      {
	Scanner stream = new Scanner(src);
	if (stream.hasNextLine())
	  {
	    do scanLine(stream);
	    while (stream.hasNextLine());
	  }
	else System.out.println("Hello, world!");
      }
    catch (FileNotFoundException ex)
      {
	System.out.println("File " + path + " cannot be found");
      }
  }
  
  public static void main(String[] args)
  {
    if (args.length == 1)
      {
	readFile(args[0]);
	return;
      }
    while (true)
      {
	System.out.print(">> ");
	if (SYSIN.hasNextLine()) scanLine(SYSIN);
	else break;
      }
  }
}
