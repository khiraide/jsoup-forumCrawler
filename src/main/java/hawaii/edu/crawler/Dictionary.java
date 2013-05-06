package hawaii.edu.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Class that will import a dictionary file of words into a list. Contains methods to parse
 * sentences with words contained in the dictionary.
 * 
 * @author Keone Hiraide
 */
public class Dictionary {
  /**
   * Holds the words contained in our dictionary.
   */
  private List<DicEntry> entries = new ArrayList<DicEntry>();
  
  /**
   * Parses a dictionary file and populates the entries private member ArrayList.
   * 
   * @param filename The dictionary file name.
   */
  public void fillEntries(String filename) {
    DicEntry entry = new DicEntry();
    Path path = FileSystems.getDefault().getPath(".", filename);
    try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"))) {
      String lineFromFile = "";
      while ((lineFromFile = reader.readLine()) != null) {
        // Grab the word.
        entry.setWord(lineFromFile.split(" ", 6)[2].split("word1=")[1].trim());
        // Grab the polarity of the word.
        entry.setPolarity(lineFromFile.split(" ", 6)[5].split("priorpolarity=")[1].trim());
        this.entries.add(entry);
        entry = new DicEntry();
      }
    } catch (IOException exception) {
      System.out.println("Error while reading file");
    }
  }
  
  /**
   * Getter method to grab the list of dictionary words.
   * @return Our list holding the dictionary words.
   */
  public List<DicEntry> getDictionaryList() {
    return this.entries;
  }
  
  /**
   * Add a word to this hashmap. If it's not in the hashmap add it. If it is in the hashmap, increase
   * its count by one.
   * @param hashMap Hashmap that stores all your unique words.
   * @param word The word you want to check with your hashmap.
   */
  public void checkUnique(Map<String, Integer> hashMap, String word) {
    if (hashMap.get(word.trim().toLowerCase()) != null) {
      int newVal = hashMap.get(word.trim().toLowerCase());
      hashMap.put(word.trim().toLowerCase(), newVal + 1);
    }
    else {
      hashMap.put(word.trim().toLowerCase(), 1);
    }
  }

  /**
   * Prints the contents of the hashMap with its value to a file.
   * @param hashMap 
   * @param filename The name of your output file.
   */
  public void printUnique(Map<String, Integer> hashMap, String filename) {
    Path newFile = Paths.get(filename);
    BufferedWriter writer;
    try {
      writer = Files.newBufferedWriter(newFile, Charset.defaultCharset());
      int i = 1;
      for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
        if (entry.getValue() >= 5) {
          writer.append(i + "\t" + entry.getKey() + "\t" + entry.getValue());
          writer.newLine();
          i++;
        }
        
      }
      writer.close();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
  
  
  /**
   * Parses a sentence and outputs results of our parsing to a text file..
   * 
   * @param postData Data to be parsed.
   * @param filename Name of the output file containing our results of the parse.
   * @param parseFlag 0 = Parse by looking at thread titles. 1 = Parse by looking at the 
   * initial post content. 
   */
  public void parse(List<PostData> postData, int parseFlag, String filename) {
    /*
     * Map holding a list of words and the frequency count.
     */
    Map<String, Integer> positiveCount = new HashMap<String, Integer>();
    Map<String, Integer> negativeCount = new HashMap<String, Integer>();
    Map<String, Integer> neutralCount = new HashMap<String, Integer>();
    Map<String, Integer> unknownCount = new HashMap<String, Integer>();
    
    if (!postData.isEmpty()) {
      for (PostData d : postData) {
        int positive = 0; 
        int negative = 0;
        int neutral = 0;
        int unknown = 0;
        String[] textArray = null;
        // Depending on our parseFlag, we will parse posts by their title or by their initial
        // post contents.
        if (parseFlag == 0) {
          textArray = d.getThreadTitle().split(" ");
        }
        else if (parseFlag == 1) {
          textArray = d.getPostContent().split(" ");
        }
        if (textArray != null) {
          for (String s : textArray) {
            for (int i = 0; i < this.entries.size(); i++) {
              if (s.trim().equalsIgnoreCase(this.entries.get(i).getWord())) {
                if (this.entries.get(i).getPolarity().equals("positive")) {
                  checkUnique(positiveCount, s);
                  positive++;
                  break;
                }
                else if (this.entries.get(i).getPolarity().equals("negative")) {
                  checkUnique(negativeCount, s);
                  negative++;
                  break;
                }
                else if (this.entries.get(i).getPolarity().equals("neutral")) {
                  checkUnique(neutralCount, s);
                  neutral++;
                  break;
                }
              } 
              else if (i >= this.entries.size() - 1 &&
                  !s.trim().isEmpty()) {
                checkUnique(unknownCount, s);
                unknown++;
              }
            }
          }
          d.setPositive(positive);
          d.setNegative(negative);
          d.setNeutral(neutral);
          d.setUnknown(unknown);
        }
      }
      
      // Writing out results to an output file.
      try {
        // Writing sentence parsing results to a file.
        Path newFile = Paths.get(filename);
        BufferedWriter writer = Files.newBufferedWriter(newFile, Charset.defaultCharset());
        int i = 1;
        for (PostData d : postData) {
          writer.append(i + "\t" + d.getReplies() + "\t" + 
          d.getPositive() + "\t" + d.getNegative() + "\t" + 
          d.getNeutral() + "\t" + d.getUnknown() + "\t");
          if (parseFlag == 0) {
            writer.append(d.getThreadTitle().toLowerCase());
          }
          else if (parseFlag == 1) {
            writer.append(d.getPostContent().toLowerCase());
          }
          writer.newLine();
          i++;
        }
        writer.close();
        printUnique(positiveCount, "positiveCount.txt");
        printUnique(negativeCount, "negativeCount.txt");
        printUnique(neutralCount, "neutralCount.txt");
        printUnique(unknownCount, "unknownCount.txt");
      }
      
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
}
