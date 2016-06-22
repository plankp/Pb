/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Plankp T.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class App
{
  public static void main(String[] args)
  {
    try
      {
	ANTLRInputStream ins = new ANTLRFileStream(args[0]);
	PBGrammarLexer lexer = new PBGrammarLexer(ins);
	TokenStream tokens = new CommonTokenStream(lexer);
	PBGrammarParser parser = new PBGrammarParser(tokens);
	new PBImplVisitor().visit(parser.prog());
      }
    catch (java.io.IOException ex)
      {
	System.out.println("Exception when reading file " + args[0]);
      }
  }
}

class PBImplVisitor extends PBGrammarBaseVisitor<Data>
{
  private final Map<Character, Data> varmap = new HashMap<>();

  private final Map<String, Data> definemap = new HashMap<>();

  private static final Scanner SYSIN = new Scanner(System.in);

  @Override
  public Data visitProg(PBGrammarParser.ProgContext ctx)
  {
    for (int i = 0; i < ctx.getChildCount(); i++)
      {
	visit(ctx.getChild(i));  
      }
    return DEmpty.getInstance();
  }

  @Override
  public Data visitDoWhile(PBGrammarParser.DoWhileContext ctx)
  {
    do
      {
	visit(ctx.getChild(0));
      }
    while (visit(ctx.getChild(2)).isTruthy());
    return DEmpty.getInstance();
  }

  @Override
  public Data visitWhileDo(PBGrammarParser.WhileDoContext ctx)
  {
    while (visit(ctx.getChild(1)).isTruthy())
      {
	visit(ctx.getChild(2));
      }
    return DEmpty.getInstance();
  }

  @Override
  public Data visitDirective(PBGrammarParser.DirectiveContext ctx)
  {
    return DEmpty.getInstance();
  }

  private final Map<String, List<PBGrammarParser.StmtContext>> macromap =
    new HashMap<>();

  @Override
  public Data visitDirMacro(PBGrammarParser.DirMacroContext ctx)
  {
    List<PBGrammarParser.StmtContext> stmts = new ArrayList<>();
    for (int i = 2; i < ctx.getChildCount() - 1; i++)
      {
	stmts.add((PBGrammarParser.StmtContext) ctx.getChild(i));
      }
    macromap.put(ctx.getChild(1).getText(), stmts);
    return DEmpty.getInstance();
  }

  @Override
  public Data visitDirCall(PBGrammarParser.DirCallContext ctx)
  {
    String name = ctx.getChild(1).getText();
    List<PBGrammarParser.StmtContext> stmts = macromap.get(name);
    for (PBGrammarParser.StmtContext stmt : stmts)
      {
	visit(stmt);
      }
    return DEmpty.getInstance();
  }

  @Override
  public Data visitDirDef(PBGrammarParser.DirDefContext ctx)
  {
    Data val = visit(ctx.getChild(2));
    definemap.put(ctx.getChild(1).getText(), val);
    return DEmpty.getInstance();
  }

  @Override
  public Data visitDirUndef(PBGrammarParser.DirUndefContext ctx)
  {
    definemap.remove(ctx.getChild(1).getText());
    return DEmpty.getInstance();
  }

  @Override
  public Data visitDirIfdef(PBGrammarParser.DirIfdefContext ctx)
  {
    Data rst = visit(ctx.getChild(0));
    if (!rst.isTruthy())
      {
	if (ctx.r != null) rst = visit(ctx.r);
      }
    if (!rst.isTruthy())
      {
	if (ctx.f != null) visit(ctx.f);
      }
    return DEmpty.getInstance();
  }

  @Override
  public Data visitDirIf(PBGrammarParser.DirIfContext ctx)
  {
    if (definemap.containsKey(ctx.getChild(1).getText()))
      {
	visit(ctx.getChild(2));
	return new DBoolean(true);
      }
    return new DBoolean(false);
  }

  @Override
  public Data visitDirElif(PBGrammarParser.DirElifContext ctx)
  {
    if (definemap.containsKey(ctx.getChild(1).getText()))
      {
	visit(ctx.getChild(2));
	return new DBoolean(true);
      }
    if (ctx.getChildCount() == 4) return visit(ctx.getChild(4));
    return new DBoolean(false);
  }

  @Override
  public Data visitDirElse(PBGrammarParser.DirElseContext ctx)
  {
    return visit(ctx.getChild(1));
  }

  @Override
  public Data visitAssignVal(PBGrammarParser.AssignValContext ctx)
  {
    char id = ctx.getText().charAt(0);
    Data val = visit(ctx.getChild(1));
    switch (id)
      {
      case ' ':
	System.out.println(val);
	break;
      case '\t':
	System.err.println(val);
      case '_':
	break;
      case '!':
	throw new RuntimeException(val.toString());
      default:
	varmap.put(id, val);
      }
    return DEmpty.getInstance();
  }

  @Override
  public Data visitIfBlock(PBGrammarParser.IfBlockContext ctx)
  {
    if (visit(ctx.getChild(1)).isTruthy())
      {
	for (int i = 2; i < ctx.getChildCount() - 1; i++)
	  {
	    visit(ctx.getChild(i));
	  }
      }
    return DEmpty.getInstance();
  }

  @Override
  public Data visitExprAddLike(PBGrammarParser.ExprAddLikeContext ctx)
  {
    Data left = visit(ctx.getChild(0));
    Data right = visit(ctx.getChild(2));
    switch (ctx.getChild(1).getText())
      {
      case "+":
	return left.plus(right);
      case "-":
	return left.minus(right);
      }
    return DEmpty.getInstance();
  }


  @Override
  public Data visitExprMulLike(PBGrammarParser.ExprMulLikeContext ctx)
  {
    Data left = visit(ctx.getChild(0));
    Data right = visit(ctx.getChild(2));
    switch (ctx.getChild(1).getText())
      {
      case "*":
	return left.times(right);
      case "/":
	return left.divide(right);
      case "%":
	return left.modulo(right);
      }
    return DEmpty.getInstance();
  }


  @Override
  public Data visitExprSubscript(PBGrammarParser.ExprSubscriptContext ctx)
  {
    Data left = visit(ctx.getChild(0));
    Data right = visit(ctx.getChild(2));
    return left.subscript(right);
  }

  @Override
  public Data visitExprNotEquals(PBGrammarParser.ExprNotEqualsContext ctx)
  {
    Data left = visit(ctx.getChild(0));
    Data right = visit(ctx.getChild(2));
    return new DBoolean(!left.equals(right));
  }

  @Override
  public Data visitExprRelLike(PBGrammarParser.ExprRelLikeContext ctx)
  {
    Data left = visit(ctx.getChild(0));
    Data right = visit(ctx.getChild(2));
    switch (ctx.getChild(1).getText())
      {
      case "<":
	return left.lessThan(right);
      case ">":
	return left.moreThan(right);
      }
    return DEmpty.getInstance();
  }

  @Override
  public Data visitExprBracket(PBGrammarParser.ExprBracketContext ctx)
  {
    return visit(ctx.getChild(1));
  }

  @Override
  public Data visitDataTuple(PBGrammarParser.DataTupleContext ctx)
  {
    final int childCount = ctx.getChildCount();
    if (childCount == 2) return new DTuple();
    final List<Data> lst = new ArrayList<>(childCount - 2);
    for (int i = 1; i < childCount - 1; i++)
      {
	lst.add(visit(ctx.getChild(i)));
      }
    return new DTuple(lst);
  }

  @Override
  public Data visitDataCond(PBGrammarParser.DataCondContext ctx)
  {
    final int childCount = ctx.getChildCount() - 1;
    boolean d = visit(ctx.getChild(1)).isTruthy();
    if (childCount - 1 > 1)
      {
        if (d)
	  {
	    Data lastOp = DEmpty.getInstance();
	    for (int i = 2; i < childCount; i++)
	      {
		lastOp = visit(ctx.getChild(i));
	      }
	    return lastOp;
	  }
	else return DEmpty.getInstance();
      }
    else return new DBoolean(!d);
  }

  @Override
  public Data visitDataText(PBGrammarParser.DataTextContext ctx)
  {
    String raw = ctx.getText();
    if (raw.length() == 2) return new DString("");
    int newlen = raw.length();
    char c;
    do
      {
        c = raw.charAt(--newlen);
      }
    while(c == '\r' || c == '\n');
    return new DString(raw.substring(2, newlen + 1));
  }

  @Override
  public Data visitDataRtext(PBGrammarParser.DataRtextContext ctx)
  {
    String raw = ctx.getText();
    if (raw.length() == 4) return new DString("");
    return new DString(raw.substring(2, raw.length() - 2));
  }

  @Override
  public Data visitDataNumber(PBGrammarParser.DataNumberContext ctx)
  {
    return new DNumber(Double.parseDouble(ctx.getText()));
  }

  @Override
  public Data visitDataRead(PBGrammarParser.DataReadContext ctx)
  {
    char id = ctx.getText().charAt(1);
    switch (id)
      {
      case ' ':
	if (SYSIN.hasNextLine()) return new DString(SYSIN.nextLine());
	return new DString("");
      case '\t':
	if (SYSIN.hasNextDouble()) return new DNumber(SYSIN.nextDouble());
	return new DNumber(0);
      case '_':
	return new DNumber(Math.random());
      default:
	if (varmap.containsKey(id)) return varmap.get(id).copy();
      case '!':
	return DEmpty.getInstance();
      }
  }
}

