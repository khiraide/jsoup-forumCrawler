package hawaii.edu.crawler;

/**
 * This class runs various Jsoup crawlers.
 * @author Keone Hiraide
 */
public class Runner2 extends Runner {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    Dictionary dic = new Dictionary();
    dic.fillEntries("dictionary.txt");
    //Crawler topix = new CrawlerTopix();
    //topix.crawl(5000, "topixUSData2.txt");
    //CrawlerLiberty liberty = new CrawlerLiberty();
    //liberty.crawl(5000, "libertyUSData.txt");
    Runnable r = new CrawlerUSMessage(1000, "usMessageUSData.txt",
        "http://www.usmessageboard.com/current-events/",2);
    new Thread(r).start();
    Runnable r2 = new CrawlerUSMessage(1000, "usMessageUSData2.txt",
        "http://www.usmessageboard.com/current-events/index50.html",51);
    new Thread(r2).start();
    
    Runnable r3 = new CrawlerUSMessage(1000, "usMessageUSData3.txt",
        "http://www.usmessageboard.com/current-events/index100.html",101);
    new Thread(r3).start();
    
    Runnable r4 = new CrawlerUSMessage(1000, "usMessageUSData4.txt",
        "http://www.usmessageboard.com/current-events/index150.html",151);
    new Thread(r4).start();
    
    Runnable r5 = new CrawlerUSMessage(1000, "usMessageUSData5.txt",
        "http://www.usmessageboard.com/current-events/index200.html",201);
    new Thread(r5).start();
  }

}
