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

grammar PBGrammar;

prog
  : stmt+
  ;

stmt
  : stmt DO_WHILE expr		# doWhile
  | WHILE_DO expr stmt		# whileDo
  | TEXT			# directive
  | ASSIGN expr			# assignVal
  | LBLOCK expr stmt+ RBLOCK	# ifBlock
  | CALL IDENT				# dirCall
  | MACRO IDENT stmt+ ENDMAC		# dirMacro
  | DEFINE IDENT expr			# dirDef
  | UNDEF IDENT				# dirUndef
  | dirIf r=dirElif? f=dirElse? ENDIF	# dirIfCond
  | INCLUDE expr			# dirInclude
  ;

expr
  : expr SUBSCRIPT expr			# exprSubscript
  | expr (TIMES | DIVIDE | MODULO) expr	# exprMulLike
  | expr (PLUS | MINUS) expr		# exprAddLike
  | expr (LT | GT) expr			# exprRelLike
  | expr NE expr			# exprNotEquals
  | LPAREN expr RPAREN			# exprBracket
  | LTUPLE expr* RTUPLE			# dataTuple
  | LBLOCK expr+ RBLOCK			# dataCond
  | TEXT				# dataText
  | RTEXT				# dataRtext
  | NUMBER				# dataNumber
  | READ				# dataRead
  | IDENT d=IS_DEFINED?			# dataDefined
  ;

dirIf
  : IF expr stmt
  ;

dirElif
  : ELSEIF expr stmt dirElif?
  ;

dirElse
  : ELSE stmt
  ;

IS_DEFINED
  : '?'
  ;

INCLUDE
  : '!include'
  | '!INCLUDE'
  ;

CALL
  : '!call'
  | '!CALL'
  ;

MACRO
  : '!macro'
  | '!MACRO'
  ;

ENDMAC
  : '!endmac'
  | '!ENDMAC'
  ;

IDENT
  : [a-zA-Z][_a-zA-Z0-9]+
  ;

DEFINE
  : '!define'
  | '!DEFINE'
  ;

UNDEF
  : '!undef'
  | '!UNDEF'
  ;

IF
  : '!if'
  | '!IF'
  ;

ELSEIF
  : '!elseif'
  | '!ELSEIF'
  ;

ELSE
  : '!else'
  | '!ELSE'
  ;

ENDIF
  : '!endif'
  | '!ENDIF'
  ;

LT
  : '<'
  ;

GT
  : '>'
  ;

NE
  : '<>'
  ;

DO_WHILE
  : '<<'
  ;

WHILE_DO
  : '>>'
  ;

PLUS
  : '+'
  ;

MINUS
  : '-'
  ;

TIMES
  : '*'
  ;

DIVIDE
  : '/'
  ;

MODULO
  : '%'
  ;

SUBSCRIPT
  : ':'
  ;

LPAREN
  : '('
  ;

RPAREN
  : ')'
  ;

LTUPLE
  : '{'
  ;

RTUPLE
  : '}'
  ;

LBLOCK
  : '['
  ;

RBLOCK
  : ']'
  ;

READ
  : '@' .
  ;

ASSIGN
  : . '='
  ;

NUMBER
  : ('0' | [1-9][0-9]*)('.' [0-9]+)?
  ;

RTEXT
  : '%%' .*? '%%'
  ;

TEXT
  : '# ' ~[\r\n]* [\r\n]
  ;

WHITESPACE
  : [ \t\r\n] -> Channel(HIDDEN)
  ;
