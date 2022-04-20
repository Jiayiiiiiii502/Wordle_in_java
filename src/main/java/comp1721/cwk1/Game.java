package comp1721.cwk1;


//import org.jfree.chart.ChartFactory;
////import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.CategoryAxis;
//import org.jfree.chart.axis.ValueAxis;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.DefaultCategoryDataset;
///////import org.jfree.data.category.CategoryDataset;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;



public class Game {
  //maintains associations with a single WordList object and a sequence of Guess objects
  private int gameNumber;
  private String target;
  private final List<String> result = new ArrayList<>();
  private static final List<String> chosens=new ArrayList<>();
  static Map<String,Integer> map=new HashMap<>();
  private boolean out;
  //private List<String> outcome=new ArrayList<>();//win or loss of each game
  private String outcome;
  //picture
  //public static ChartPanel frame1;
  private static List<Integer> times_chosens=new ArrayList<>();


  // TODO: Implement constructor with String parameter
  public Game(String filename) throws IOException {
    //gameNumber= today escape from the 2021.7.19,decide the target word
    LocalDate start = LocalDate.of(2021, 6, 19);
    LocalDate now = LocalDate.now();
    gameNumber = (int) (now.toEpochDay() - start.toEpochDay());
    WordList obj = new WordList(filename);
    if (gameNumber > obj.size()) {
      throw new IOException();
    } else {
      target = obj.getWord(gameNumber);
    }
  }


  // TODO: Implement constructor with int and String parameters
  public Game(int num, String filename) throws IOException {
    //num is read from user to get target
    gameNumber = num;
    WordList obj = new WordList(filename);
    if (gameNumber > obj.size()) {
      throw new IOException();
    } else {
      target = obj.getWord(gameNumber);
    }
  }
  public Game(){

  }




  // TODO: Implement play() method
  public void play() throws IOException {
    //play en entire game of wordle
    //need to create and store Guess objects
    //check each guess, print the string returned by the compareWith()
    System.out.println("WORLDE " + gameNumber);
    System.out.println("\n");
    //remember to delete
    System.out.println("Answer is: " + target);
    int i = 1;
    out = false;
    while (i <= 6) {
      System.out.print("Enter guess (" + i + "/6): ");
      Scanner scan = new Scanner(System.in);
      //the input word
      String chosen = scan.nextLine();
      chosens.add(chosen);
      //create Guess object
      Guess guess;
      //check use which method
      if (chosen.length() > 0) {
        guess = new Guess(i, chosen);
      } else {
        guess = new Guess(i);
        guess.readFromPlayer();
      }
      System.out.println(guess.compareWith(target));
      result.add(guess.compareWith(target));
      //check if it is over
      if (guess.matches(target)) {
        if (guess.getGuessNumber() == 1) {
          System.out.println("Superb - Got it in one!");
          out = true;
          outcome="1";
          break;
        } else if (guess.getGuessNumber() >= 2 && guess.getGuessNumber() <= 5) {
          System.out.println("Well done!\n");
          out = true;
          outcome="1";
          break;
        } else if (guess.getGuessNumber() == 6) {
          System.out.println("That was a close call!\n");
          out = true;
          outcome="1";
          break;
        }
      }
      i++;
    }
    if (!out) {
      System.out.println("Sorry! You lose the game!\n");
      outcome="0";
    }
    save_all("data/history.txt");
    System.out.println("The number of game is " + all_num("data/history.txt"));
    percentage("data/history.txt");
    win_steak("data/outcome.txt");


    //draw the picture
    Collections.reverse(chosens);
    BufferedImage image = paintPlaneHistogram("Histogram",
            times(),chosens,  new Color[] {Color.RED, Color.BLACK});
    File output = new File("data/img.jpg");
    ImageIO.write(image, "jpg", output);

//
//
    JFrame frame=new JFrame("Guess distribution");
    frame.setLayout(new GridLayout(2,2,10,10));
    //frame.add(frame1);
    //frame.add(new BarChart().getChartPanel());   //
    frame.add(new Game().setPic());
    frame.setBounds(0, 0, 900, 800);
    frame.setVisible(true);

  }

  // TODO: Implement save() method, with a String parameter
  public void save(String filename) throws IOException {
    //save a summary (consist of one line for each guess made)
    //this line should contain the string returned by the comparesWith()
    FileWriter fw = new FileWriter(filename);
    int i;
    for (i = 0; i < result.size(); i++) {
      fw.write(result.get(i) + "\n");
    }
    fw.close();
  }


  //this method is to save all history of this game
  public void save_all(String filename) throws IOException {
    //create new all list to store all history
    List<String> all = new ArrayList<>();
    File file = new File(filename);
    //build if txt doesn't exist
    if (!file.exists()) {
      file.createNewFile();
    }
    //read txt file content and store to the List
    InputStreamReader read;
    BufferedReader reader;
    read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
    reader = new BufferedReader(read);
    String line;
    while ((line = reader.readLine()) != null) {
      all.add(line);
    }
    read.close();
    reader.close();
    //write the new one
    all.add("The game number is " + gameNumber );
    if (out) {
      all.add("Player has won the game!");
    } else {
      all.add("Player has loss the game!");
    }
    all.add("The guess number that player has made is " + result.size());
    //add all list content to the file
    FileWriter fw = new FileWriter(filename);
    //input all list into file
    int j;
    for (j = 0; j < all.size(); j++) {
      fw.write(all.get(j) + "\n");
    }
    fw.write("-------------------------------------------------------------------------------------------------");
    fw.write("\n");
    fw.close();
  }


  public int all_num(String filename) throws IOException {
    File file = new File(filename);
    if (!file.exists()) {
      file.createNewFile();

    }
    StringBuilder result = new StringBuilder();
    //read txt content
    InputStreamReader read;
    BufferedReader reader;
    read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
    reader = new BufferedReader(read);
    String line;
    while ((line = reader.readLine()) != null) {
      result.append(line);
    }
    read.close();
    reader.close();
    //count appearance number
    int index = 0;
    int count = 0;
    String specialStr = "Player";
    int len = specialStr.length();
    while ((index = result.indexOf(specialStr, index)) != -1) {
      index += len;
      count++;
    }
    return count;
  }


  public void percentage(String filename) throws IOException {
    File file = new File(filename);
    if (!file.exists()) {
      file.createNewFile();

    }
    StringBuilder result = new StringBuilder();
    //read txt content
    InputStreamReader read;
    BufferedReader reader;
    read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
    reader = new BufferedReader(read);
    String line;
    while ((line = reader.readLine()) != null) {
      result.append(line);
    }
    read.close();
    reader.close();
    //count appearance number
    int index = 0;
    int count_win = 0;
    int count_loss;
    String specialStr = "won";
    int len = specialStr.length();
    while ((index = result.indexOf(specialStr, index)) != -1) {
      index += len;
      count_win++;
    }
    count_loss=all_num(filename)-count_win;
    System.out.println("Win: "+count_win);
    System.out.println("Loss: "+count_loss);
    double count_win_2=count_win;
    double per=count_win_2/all_num(filename);
    //turn into the percentage format
    NumberFormat format= NumberFormat.getPercentInstance();
    format.setMaximumFractionDigits(2);
    System.out.println("The percentages of wins are "+format.format(per));
  }



  public void win_steak(String filename) throws IOException {
    //create a new file to store outcome
    File file = new File(filename);
    if (!file.exists()) {
      file.createNewFile();

    }
    StringBuilder result = new StringBuilder();
    //read txt content
    InputStreamReader read;
    BufferedReader reader;
    read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
    reader = new BufferedReader(read);
    String line;
    while ((line = reader.readLine()) != null) {
      result.append(line);
    }
    read.close();
    reader.close();
    //write this game outcome
    FileWriter fw = new FileWriter(filename);
    int i;
    fw.write(result+outcome);
    fw.close();

    String all=result+outcome;
    String desStr="1";
    String desStr1=desStr;
    for(i=0;i<all.length();i++){
      if(all.contains(desStr)){
        desStr+=desStr1;
      }
      else{
        break;
      }
    }
    int win_length=desStr.length()-1;
    System.out.println("Longest win steak is "+win_length);


    //current win steak
    String reverse=new StringBuffer(all).reverse().toString();
    //System.out.println(reverse);
    char[] revers_char=reverse.toCharArray();
    if(revers_char[0]=='0'){
      System.out.println("Sorry! Your current win steak is 0!\n");
    }
    else if(revers_char[0]=='1'){
      int j;
      int win_length_now=0;
      for(j=0;j<reverse.length();j++){
        if(revers_char[j]=='1'){
          win_length_now+=1;
        }
        else{
          break;
        }
      }
      System.out.println("Your current win steak is "+win_length_now);
    }
  }




  public void play_impair() throws IOException {
    //play en entire game of wordle
    //need to create and store Guess objects
    //check each guess, print the string returned by the compareWith()
    System.out.println("WORLDE " + gameNumber);
    System.out.println("\n");
    //remember to delete
    System.out.println("Answer is: " + target);
    int i = 1;
    out = false;
    while (i <= 6) {
      System.out.print("Enter guess (" + i + "/6): ");
      Scanner scan = new Scanner(System.in);
      String chosen = scan.nextLine();
      //create Guess object
      Guess guess;
      //check use which method
      if (chosen.length() > 0) {
        guess = new Guess(i, chosen);
      } else {
        guess = new Guess(i);
        guess.readFromPlayer();
      }
      guess.compare_impair(target);
      //result.add(guess.compareWith(target));
      //check if it is over
      if (guess.matches(target)) {
        if (guess.getGuessNumber() == 1) {
          System.out.println("Superb - Got it in one!");
          out = true;
          outcome="1";
          break;
        } else if (guess.getGuessNumber() >= 2 && guess.getGuessNumber() <= 5) {
          System.out.println("Well done!\n");
          out = true;
          outcome="1";
          break;
        } else if (guess.getGuessNumber() == 6) {
          System.out.println("That was a close call!\n");
          out = true;
          outcome="1";
          break;
        }
      }
      i++;
    }
    if (!out) {
      System.out.println("Sorry! You lose the game!\n");
      outcome="0";
    }



    //added functions for imparied
    save_all("data/history_impaired.txt");
    System.out.println("The number of game is " + all_num("data/history_impaired.txt"));
    percentage("data/history_impaired.txt");
    win_steak("data/outcome_impaired.txt");


    //draw the picture
    BufferedImage image = paintPlaneHistogram("Histogram",
            times(),chosens,  new Color[] {Color.RED,  Color.BLACK });
    File output = new File("data/img.jpg");
    ImageIO.write(image, "jpg", output);
  }

  public BufferedImage paintPlaneHistogram(String title, List<Integer> v,
                                           List<String> str, Color[] color) {
    //histogram
    int histogramWidth = 15;
    int histogramPitch = 10;
    int width = str.size() * histogramWidth +str.size()* histogramPitch +250;
//        int height = 255;
    int height=200;
    float scaling = calculateScale(v, height);//ratio of picture

    BufferedImage bufferImage = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_RGB);
    Graphics g = bufferImage.getGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
    FontMetrics metrics;

    g.setFont(new Font(null, Font.BOLD, 18));
    g.setColor(Color.RED);

    g.drawString(title, (bufferImage.getWidth() - g.getFontMetrics()
            .stringWidth(title)) >> 1, 40);

    g.setFont(new Font(null, Font.PLAIN, 10));

    metrics = g.getFontMetrics();

    g.setColor(Color.BLACK);

    g.drawLine(10, 0, 10, height - 15);
    g.drawLine(10, height - 15, width, height - 15);




    int j = 0;
    int colorCount=color.length;

    //set color for different guess words
    for (int i = 0; i < v.size(); ++i) {
      g.setColor(color[j]);
      if(j+1<colorCount){
        j++;
      }else{
        j=0;
      }

      //set the departure between two x labels
      int x = 30 + i * (histogramPitch + histogramWidth+30);
      //set th start position of y labels
      int y = height - 16 - (int) (v.get(i) * scaling);

      g.drawString(v.get(i) + "", x - ((metrics.stringWidth(v.get(i) + "") - histogramWidth) >> 1), y);


      g.drawRect(x, y, histogramWidth, (int) (v.get(i) * scaling));
      g.fillRect(x, y, histogramWidth, (int) (v.get(i) * scaling));

      //set the x font under the x label
      g.drawString(str.get(i), x
                      - ((metrics.stringWidth(str.get(i)) - histogramWidth) >> 1),
              height - 2);
    }
    //set words
    int x = 30 + v.size() * (histogramPitch + histogramWidth+40);
//    g.drawString("Guess words",50,0 );
    g.drawString("Appearances",1,50);
    //g.drawString("Guess Words",200,195);
    if(v.size()==1){
      g.drawString("Guess Words",200,195);
    }
    else if(v.size()==2){
      g.drawString("Guess Words",230,195);
    }
    else if(v.size()==3){
      g.drawString("Guess Words",260,195);
    }
    else if(v.size()==4){
      g.drawString("Guess Words",290,195);
    }
    else if(v.size()==5){
      g.drawString("Guess Words",320,195);
    }
    else if(v.size()==6){
      g.drawString("Guess Words",340,195);
    }



    return bufferImage;
  }

  public float calculateScale(List<Integer> v , int h){
    float scale = 1f;
    int max = Integer.MIN_VALUE;
    for (Integer integer : v) {
      if (integer > h && integer > max) {
        max = integer;
      }
    }
    if(max > h){
      scale=((int)(h*1.0f/max*1000))*1.0f/1000;
    }
    return scale;
  }



  public static List<Integer> times(){
    int i;
    for(i=0;i<chosens.size();i++){
      if(map.containsKey(chosens.get(i))){
        int val=map.get(chosens.get(i));
        map.put(chosens.get(i),val+1);
      }
      else{
        map.put(chosens.get(i),1);
      }
    }



    for(int m=0;m<map.size();m++){
      times_chosens.add(map.get(chosens.get(m)));
    }
    Collections.reverse(times_chosens);
    return new ArrayList<>(map.values());
  }






//
  //another way to draw picture:
  public Component setPic(){
    //CategoryDataset dataset = getDataSet();//
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for(int i=0;i<chosens.size();i++){
      System.out.println(times_chosens.get(i));
      dataset.addValue(times_chosens.get(i),chosens.get(i),chosens.get(i));
    }

    JFreeChart chart = ChartFactory.createBarChart3D(
            "Histogram", //
            "Guess Words", //
            "Appearances", //
            dataset, //
            PlotOrientation.VERTICAL, //
            true, //
            false,  //
            false  //
    );

    CategoryPlot plot=chart.getCategoryPlot();//
    CategoryAxis domainAxis=plot.getDomainAxis();         //
    domainAxis.setLabelFont(new Font("Arial",Font.BOLD,14));         //
    domainAxis.setTickLabelFont(new Font("Arial",Font.BOLD,12));  //
    ValueAxis rangeAxis=plot.getRangeAxis();//
    rangeAxis.setLabelFont(new Font("Arial",Font.BOLD,15));
    chart.getLegend().setItemFont(new Font("Arial", Font.BOLD, 15));
    chart.getTitle().setFont(new Font("Arial",Font.BOLD,20));//

    ChartPanel frame1 = new ChartPanel(chart, true);  //

    return frame1;
  }


}
