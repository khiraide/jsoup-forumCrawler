package hawaii.edu.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * This class runs various Jsoup crawlers.
 * @author Keone Hiraide
 */
public class CrawlerRunner {
  
  /**
   * List containing post data. {@link postData} is object that contains data such as the
   * the post content and the amount of replies that this post contained. 
   */
  private List<PostData> postData = new ArrayList<PostData>();
  /**
   * Get post data from our file.
   * @param filename The file that contains the post data which is used for input.
   */
  public void fillPostData(String filename) {
    PostData data = new PostData();
    Path path = FileSystems.getDefault().getPath(".", filename);
    try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"))) {
      String lineFromFile = "";
      while ((lineFromFile = reader.readLine()) != null) {
        // Grab the thread title.
        data.setThreadTitle(lineFromFile.split("&&&")[0]
            .replaceAll("[-+.^:,\"~`/;!@*#$%&()?]"," ").replace("'", ""));
        // Grab the first post content.
        data.setPostContent(lineFromFile.split("&&&")[1]
            .replaceAll("[-+.^:,\"~`;/!@*#$%&()?]"," ").replace("'", ""));
        // Grab the amount of replies that the read has.
        data.setReplies(lineFromFile.split("&&&")[2]);
        //System.out.println(data.getThreadTitle());
        this.postData.add(data);
        data = new PostData();
      }
     
    } catch (IOException exception) {
      System.out.println("Error while reading file");
    }
  }
  
  /**
   * Retrieves the list full of post data.
   * @return A list containing post data.
   */
  public List<PostData> getPostData() {
    return this.postData;
  }
  /**
  * Main method begins.
  * @param args We won't use args parameters for this program.
  */
  public static void main(String[] args) {
    Dictionary dic = new Dictionary();
    dic.fillEntries("subjclueslen1-HLTEMNLP05.tff");
    //TropixCrawler tropix = new TropixCrawler();
    //tropix.run(2000, "tropixTitleData3.txt");
    CrawlerRunner runner = new CrawlerRunner();
    runner.fillPostData("tropixData.txt");
    if (runner.getPostData().isEmpty()) {
      System.out.println("There is no post data to be parsed!");
    }
    else {
      dic.parse(runner.getPostData(), 1, "output2.txt");
    }
  }  
}