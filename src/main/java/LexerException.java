import java.util.regex.Matcher;

public class LexerException extends RuntimeException
{
  public LexerException(Matcher failedMatch)
  {
    super("Start: " + failedMatch.start() + " Text:" + failedMatch.group());
  }
}
