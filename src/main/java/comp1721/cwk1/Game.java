package comp1721.cwk1;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
  //maintains associations with a single WordList object and a sequence of Guess objects
  private int gameNumber;
  private String target;
  private List<String> result = new ArrayList<String>();
  private boolean out;
  //private List<String> outcome=new ArrayList<>();//win or loss of each game
  private String outcome;

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
      System.out.printf("Enter guess (" + i + "/6): ");
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
    if (out == false) {
      System.out.println("Sorry! You lose the game!\n");
      outcome="0";
    }
    save_all("data/history.txt");
    System.out.println("The number of game is " + all_num("data/history.txt"));
    percentage("data/history.txt");
    win_steak("data/outcome.txt");

  }

  // TODO: Implement save() method, with a String parameter
  public void save(String filename) throws IOException {
    //save a summary (consist of one line for each guess made)
    //this line should contain the string returned by the comparesWith()
    FileWriter fw = new FileWriter(filename);
    int i = 0;
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
    InputStreamReader read = null;
    BufferedReader reader = null;
    read = new InputStreamReader(new FileInputStream(file), "utf-8");
    reader = new BufferedReader(read);
    String line;
    while ((line = reader.readLine()) != null) {
      all.add(line);
    }
    if (read != null) {
      read.close();
    }
    if (reader != null) {
      reader.close();
    }
    //write the new one
    int i = 0;
    all.add("The game number is " + gameNumber );
    if (out == true) {
      all.add("Player has won the game!");
    } else if (out == false) {
      all.add("Player has loss the game!");
    }
    all.add("The guess number that player has made is " + result.size());
    //add all list content to the file
    FileWriter fw = new FileWriter(filename);
    //input all list into file
    int j = 0;
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
    String result = "";
    //read txt content
    InputStreamReader read = null;
    BufferedReader reader = null;
    read = new InputStreamReader(new FileInputStream(file), "utf-8");
    reader = new BufferedReader(read);
    String line;
    while ((line = reader.readLine()) != null) {
      result+=line;
    }
    if (read != null) {
      read.close();
    }
    if (reader != null) {
      reader.close();
    }
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
    String result = "";
    //read txt content
    InputStreamReader read = null;
    BufferedReader reader = null;
    read = new InputStreamReader(new FileInputStream(file), "utf-8");
    reader = new BufferedReader(read);
    String line;
    while ((line = reader.readLine()) != null) {
      result+=line;
    }
    if (read != null) {
      read.close();
    }
    if (reader != null) {
      reader.close();
    }
    //count appearance number
    int index = 0;
    int count_win = 0;
    int count_loss=0;
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
    String result = "";
    //read txt content
    InputStreamReader read = null;
    BufferedReader reader = null;
    read = new InputStreamReader(new FileInputStream(file), "utf-8");
    reader = new BufferedReader(read);
    String line;
    while ((line = reader.readLine()) != null) {
      result+=line;
    }
    if (read != null) {
      read.close();
    }
    if (reader != null) {
      reader.close();
    }
    //write this game outcome
    FileWriter fw = new FileWriter(filename);
    int i = 0;
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
      int j=0;
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
      System.out.printf("Enter guess (" + i + "/6): ");
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
    if (out == false) {
      System.out.println("Sorry! You lose the game!\n");
      outcome="0";
    }
  }



}
