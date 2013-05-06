package hawaii.edu.crawler;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * All crawlers will inherit the persistantConnect function. 
 * @author Keone Hiraide
 */
public abstract class Crawler {
  /**
   * Attempts to connect to a URL until it's successful in five attempts.
   * @param website The website URL that you want to crawl.
   * @return The html document of the requested URL.
   */
  public Document persistantConnect(String website) {
    Document htmlDoc = null;
    //int i = 0;
    while (true) { // Try 5 times to connect. Set to "true" if you forever want to retry.
      try {
        Thread.sleep(2000);
        Connection conn = Jsoup.connect(website); //NOPMD
        htmlDoc = conn.timeout(10000).get(); ////NOPMD
        break;
      }
      catch (IOException | InterruptedException e) {
       System.out.println("Tried to connect boss... but it timed-out =/");
      }
      //i++;
    }
    return htmlDoc;
  }
  /**
   * Crawls the discussion forum and grabs the specified number of posts.
   * @param postNum Amount of posts that you want to grab.
   * @param filename Output file to output the results to.
   */
  //public abstract void crawl(int postNum, String filename);
  //public abstract void crawl();
  
}
