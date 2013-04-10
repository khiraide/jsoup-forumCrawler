package hawaii.edu.crawler;

/**
 * Will contain entries to be stored in the {@link Dictionary} class.
 * 
 * @author Keone Hiraide
 */
public class DicEntry {
  
  /**
   * The polarity of word can either be positive or negative.
   */
  private String polarity;
  
  /**
   * A word in the dictionary.
   */
  private String word;
  
  /**
   * Set the polarity of the word.
   * @param polarity Will either be "positive" or "negative".
   */
  public void setPolarity(String polarity) {
    this.polarity = polarity;
  }
  
  /**
   * The vocabulary word to be set.
   * @param word The vocabulary word.
   */
  public void setWord(String word) {
    this.word = word;
  }
  
  /**
   * Retrieves the polarity of the word.
   * @return The polarity of the word.
   */
  public String getPolarity() {
    return this.polarity;
  }
  
  /**
   * Retrieves the vocabulary word.
   * @return The vocabulary word.
   */
  public String getWord() {
    return this.word;
  }
}
