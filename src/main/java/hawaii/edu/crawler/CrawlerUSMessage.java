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
 * This crawler crawls http://www.usmessageboard.com/current-events/.
 * @author Keone Hiraide
 */
public class CrawlerUSMessage extends Crawler implements Runnable {
  /**
   * List that holds the initial posts of various threads.
   */
  private List<PostData> posts = new ArrayList<PostData>();
  private int nextPage;
  private int postNum;
  private String initURL;
  private String filename;
  
  public CrawlerUSMessage(int postNum, String filename, String initURL, int nextPage) {
    this.postNum = postNum;
    this.filename = filename;
    this.initURL = initURL;
    this.nextPage = nextPage;
  }
  /**
   * Crawls the usMessageBoard News discussion forum and grabs the specified number of initial posts.
   * @param postNum Amount of posts that you want to grab.
   * @param filename Output file to output the results to.
   */
  public void crawl() {
    try {
      Path newFile = Paths.get(this.filename);
      BufferedWriter writer = Files.newBufferedWriter(newFile, Charset.forName("UTF-8"));
      //Document mainDoc = persistantConnect("http://www.usmessageboard.com/current-events/");
      Document mainDoc = persistantConnect(this.initURL);
      while (this.posts.size() < this.postNum) {
        mainDoc.outputSettings().charset("UTF-8");
        Elements threadBody = mainDoc.select("tbody[id=threadbits_forum_20]").select("tr");
        for (int i = 0; i < threadBody.size(); i++) {
          Element numPost = null;
          String textNumPost = null;
        
       // Getting the amount of replies that a thread has.
          numPost = threadBody
              .get(i)
              .select("td.alt2[title*=Replies]").first();
          if (numPost != null) {
               textNumPost = numPost.attr("title")
              .split(", V")[0]
              .replaceAll("[Replies:Vw,]", "")
              .trim();
          }
          
          String postURL;
          postURL = threadBody
              .get(i)
              .select("td.alt1").get(1)
              .select("div").first()
              .select("a[href]").get(0)
              .attr("href"); 
          
          // Connecting to the post pages.
          Document postDoc = persistantConnect(postURL);
       // Getting the title of a thread.
          Element title = postDoc.select("h1.h1").first();
          // Grabbing first post.
          Element postContent = null;
          if (title != null && !title.text().equals("Current Events")) {
            postContent = postDoc
                .select("div[id=posts]").first()
                .select("div[id*=post_message]").first();
          }
          
          //System.out.println(postContent.text());
          //System.exit(0);
          if (title != null && postContent != null && numPost != null && !textNumPost.isEmpty()) {
            System.out.println(title.text() + " " + textNumPost);
            PostData data = new PostData();
            // Removing default " - Topix" trail that is tacked to the end of
            // post titles. Setting title and first post content to our data.
            data.setPostContent(title.text() + 
                "&&&" + postContent.text().replace("Quote:", ""));
            // Setting the amount of replies to our data.
            data.setReplies(textNumPost.replace(",",""));
            //System.out.println(data.getPostContent());
            this.posts.add(data);
            // Writing our data to a file.
            writer.append(data.getPostContent() + "&&&" + data.getReplies());
            writer.newLine();
          }
          if (this.posts.size() >= this.postNum) { // We got our desired amount of posts.
            System.out.println("We have over " + this.postNum + " of posts!");
            break;
          }
        }
        // Gathered all the posts on the page. We will go to the next page.
        String nextPageURL = "http://www.usmessageboard.com/current-events/index" + this.nextPage + ".html";
        System.out.println(nextPageURL);
        mainDoc = persistantConnect(nextPageURL);
        nextPage += 1;
      }
      writer.close();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    this.crawl();
  }
}
