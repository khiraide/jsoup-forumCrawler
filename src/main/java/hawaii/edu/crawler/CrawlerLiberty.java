package hawaii.edu.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This crawler will crawl http://www.libertynewsforum.com/.
 * @author Keone Hiraide
 */
public class CrawlerLiberty extends Crawler {
  /**
   * List that holds the initial posts of various threads.
   */
  private List<PostData> posts = new ArrayList<PostData>();
  private int nextPage = 1800;
 
  /**
   * Crawls the Liberty News discussion forum and grabs the specified number of initial posts.
   * @param postNum Amount of posts that you want to grab.
   * @param filename Output file to output the results to.
   */

  public void crawl(int postNum, String filename) {
    try {
      Path newFile = Paths.get(filename);
      BufferedWriter writer = Files.newBufferedWriter(newFile, Charset.forName("UTF-8"));
      Document mainDoc = persistantConnect("http://www.libertynewsforum.com/cgi-bin/politics/YaBB.pl?board=general/1775");
      while (this.posts.size() < postNum) {
        mainDoc.outputSettings().charset("UTF-8");
        Elements titles = mainDoc.select("div[style*=float: left; width: 95%;]"); // Title of posts
        Elements numPosts = mainDoc.select("td.windowbg[width$=4%"); // Amount of posts.
        int replyIndex = 0;
        for (int i = 0; i < titles.size(); i++) {
          Element numPost = null;
          // Getting the title of a thread.
          Element title = titles.get(i).select("a[href]").first();
          // Getting the amount of replies that a thread has.
          numPost = numPosts.get(replyIndex);
          replyIndex += 2;
          System.out.println(title.text() + " " + numPost.text());
          String postURL;
          postURL = title.attr("abs:href"); 
          // Connecting to the post pages.
          Document postDoc = persistantConnect(postURL);
          // Grabbing first post.
          Element postContent = postDoc.select("div.message").first(); 
          if (title != null && postContent != null && numPost != null) {
            PostData data = new PostData();
            // Removing default " - Topix" trail that is tacked to the end of
            // post titles. Setting title and first post content to our data.
            data.setPostContent(title.text() + 
                "&&&" + postContent.text());
            // Setting the amount of replies to our data.
            data.setReplies(numPost.text().replace(",",""));
            //System.out.println(data.getPostContent());
            this.posts.add(data);
            // Writing our data to a file.
            writer.append(data.getPostContent() + "&&&" + data.getReplies());
            writer.newLine();
          }
          if (this.posts.size() >= postNum) { // We got our desired amount of posts.
            System.out.println("We have over " + postNum + " of posts!");
            break;
          }
        }
        // Gathered all the posts on the page. We will go to the next page.
        String nextPageURL = "http://www.libertynewsforum.com/cgi-bin/politics/YaBB.pl?board=general/" + nextPage;
        System.out.println(nextPageURL);
        mainDoc = persistantConnect(nextPageURL);
        nextPage += 25;
      }
      writer.close();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
