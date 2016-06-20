public class ParserException extends RuntimeException
{
  public ParserException(Token found, String... expects)
  {
    super("Expects " + String.join(" or ", expects) + ". Found "
	  + (found == null ? "nothing" : found));
  }
}
