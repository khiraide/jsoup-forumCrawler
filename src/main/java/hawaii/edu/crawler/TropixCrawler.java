package hawaii.edu.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This crawler will crawl http://www.topix.com/forum/city/honolulu-hi.
 * @author Keone Hiraide
 */
public class TropixCrawler {
  /**
   * List that holds the initial posts of various threads.
   */
  private List<PostData> posts = new ArrayList<PostData>();
  /**
   * Attempts to connect to a URL until it's successful in five attempts.
   * @param website The website URL that you want to crawl.
   * @return The html document of the requested URL.
   */
  public Document persistantConnect(String website) {
    Document mainDoc = null;
    int i = 0;
    while (i < 5) { // Try 5 times to connect.
      try {
        Thread.sleep(2000);
        Connection conn = Jsoup.connect(website); //NOPMD
        mainDoc = conn.timeout(10000).get(); ////NOPMD
        break;
      }
      catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
      i++;
    }
    return mainDoc;
  }
  /**
   * Crawls the tropix discussion forums and grabs the specified number of initial posts.
   * @param postNum Amount of posts that you want to grab.
   * @param filename Output file to output the results to.
   */
  public void run(int postNum, String filename) {
    try {
      Path newFile = Paths.get(filename);
      BufferedWriter writer = Files.newBufferedWriter(newFile, Charset.forName("ISO-8859-1"));
      Document mainDoc = persistantConnect("http://www.topix.com/forum/city/honolulu-hi/p95/");
      while (this.posts.size() < postNum) {
        mainDoc.outputSettings().charset("ISO-8859-1");
        Elements links = mainDoc.getElementsByClass("threadtitle"); // Title of posts
        Elements numPosts = mainDoc.getElementsByClass("numposts"); // Amount of posts.
        for (int i = 0; i < links.size(); i++) {
          String postURL = links.get(i).attr("abs:href"); 
          // Connecting to the post pages.
          Document postDoc = persistantConnect(postURL);
          // Grabbing title of post.
          Element title = postDoc.select("title").first(); 
          // Grabbing first post.
          Element postContent = postDoc.select("div.postcontent").first(); 
          if (title != null && postContent != null) {
            PostData data = new PostData();
            // Removing default " - Topix" trail that is tacked to the end of
            // post titles. Setting title and first post content to our data.
            data.setPostContent(title.text().replace(" - Topix", "") + 
                "&&&" + postContent.ownText());
            // Setting the amount of replies to our data.
            data.setReplies(numPosts.get(i).text().replace(",",""));
            //System.out.println(data.getPostContent());
            this.posts.add(data);
            // Writing our data to a file.
            writer.append(data.getPostContent() + "&&&" + data.getReplies());
            writer.newLine();
          }
          if (this.posts.size() >= postNum) { // We got our desired amount of posts.
            break;
          }
        }
        // Gathered all the posts on the page. We will go to the next page.
        Element pageNext = mainDoc.select("[t=forum-next]").first();
        String nextPageURL = pageNext.attr("abs:href");
        System.out.println(nextPageURL);
        mainDoc = persistantConnect(nextPageURL);
      }
      writer.close();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
