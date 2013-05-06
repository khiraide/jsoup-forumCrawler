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
 * This crawler will crawl http://www.topix.com/forum/us.
 * @author Keone Hiraide
 */
public class CrawlerTopix extends Crawler {
  /**
   * List that holds the initial posts of various threads.
   */
  private List<PostData> posts = new ArrayList<PostData>();
 
  /**
   * Crawls the topix discussion forums and grabs the specified number of initial posts.
   * @param postNum Amount of posts that you want to grab.
   * @param filename Output file to output the results to.
   */
  public void crawl(int postNum, String filename) {
    try {
      Path newFile = Paths.get(filename);
      BufferedWriter writer = Files.newBufferedWriter(newFile, Charset.forName("ISO-8859-1"));
      Document mainDoc = persistantConnect("http://www.topix.com/forum/us/p14");
      //Document mainDoc = persistantConnect("http://www.topix.com/forum/world/p44");
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
