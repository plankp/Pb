import java.util.regex.Matcher;

public class LexerException extends RuntimeException
{
  public LexerException(Matcher failedMatch)
  {
    super("Start: " + failedMatch.start() + " End: " + failedMatch.end()
	  + " Text:" + failedMatch.group());
  }
}
