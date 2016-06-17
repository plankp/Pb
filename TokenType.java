public enum TokenType
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
