package hawaii.edu.crawler;

/**
 * Contains data of posts that we want to crawl.
 * 
 * @author Keone Hiraide
 */
public class PostData {
  
  /**
   * The total amount of replies that are contained in a post.
   */
  private String replies = "";
  
  /**
   * The thread's title.
   */
  private String threadTitle = "";
  /**
   * The thread's initial post's content.
   */
  private String postContent = "";
  
  /**
   * The amount of positive, negative, neutral, or unknown words in the text.
   */
  private int positive = 0, negative = 0, neutral = 0, unknown = 0;
  

  /**
   * The content of the post we will be storing.
   * @param postContent The post content.
   */
  public void setPostContent(String postContent) {
    this.postContent = postContent;
  }
  
  /**
   * Sets the total amount of replies of a post.
   * @param replies Amount of replies that a post has.
   */
  public void setReplies(String replies) {
    this.replies = replies;
  }
  
  /**
   * Sets the total amount of positive words in the post text.
   * @param positive Total amount of positive words in the post text.
   */
  public void setPositive(int positive) {
    this.positive = positive;
  }
  
  /**
   * Sets the total amount of negative words in the post text.
   * @param negative Total amount of negative words in the post text.
   */
  public void setNegative(int negative) {
    this.negative = negative;
  }
  
  /**
   * Sets the total amount of neutral words in the post text.
   * @param neutral Total amount of neutral words in the post text.
   */
  public void setNeutral(int neutral) {
    this.neutral = neutral;
  }
  
  /**
   * Sets the title of the thread.
   * @param threadTitle Sets the thread title data.
   */
  public void setThreadTitle(String threadTitle) {
    this.threadTitle = threadTitle;
  }
  
  /**
   * Sets the total amount of unknown words in the post text.
   * @param unknown Total amount of unknown words in the post text.
   */
  public void setUnknown(int unknown) {
    this.unknown = unknown;
  }
  
  /**
   * Retrieves the first post's content.
   * @return First post's content.
   */
  public String getPostContent() {
    return this.postContent;
  }
  
  /**
   * Retrieves the title of the thread.
   * @return The thread's title.
   */
  public String getThreadTitle() {
    return this.threadTitle;
  }
  /**
   * Gets the total amount of replies of a post.
   * @return The total amount of replies of a post.
   */
  public String getReplies() {
    return this.replies;
  }
  
  /**
   * Retrieves the total amount of positive words in the post text.
   * @return The total amount of positive words in the post text.
   */
  public int getPositive() {
    return this.positive;
  }
  
  /**
   * Retrieves the total amount of negative words in the post text.
   * @return The total amount of negative words in the post text.
   */
  public int getNegative() {
    return this.negative;
  }
  
  /**
   * Retrieves the total amount of neutral words in the post text.
   * @return The total amount of neutral words in the post text.
   */
  public int getNeutral() {
    return this.neutral;
  }
  
  /**
   * Retrieves the total amount of unknown words in the post text.
   * @return The total amount of unknown words in the post text.
   */
  public int getUnknown() {
    return this.unknown;
  }
}
