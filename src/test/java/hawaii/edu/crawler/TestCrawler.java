package hawaii.edu.crawler;

import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link Runner} class for web crawling. 
 * @author Keone Hiraide
 */

@RunWith(JUnit4.class)
public class TestCrawler {
  
  
  /**
   * Testing to ensure that our dictionary does not contain values
   * which are null.
   */
  @Test
  public void testForNullValuesInDictionary() {
    Dictionary dictionary = new Dictionary();
    List<DicEntry> entries = dictionary.getDictionaryList();
    for (DicEntry entry : entries) {
      assertNotNull("A word entry is null!", entry.getWord());
      assertNotNull("A polarity entry is null!", entry.getPolarity());
    }
  }
}
