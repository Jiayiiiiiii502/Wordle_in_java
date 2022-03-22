package comp1721.cwk1;

import java.util.Scanner;


public class Guess {
  private int guessNumber;//range:1-6
  private String chosenWord;//store the chosen word by the player
  // Use this to get player input in readFromPlayer()
  private static final Scanner INPUT = new Scanner(System.in);

  // TODO: Implement constructor with int parameter
  public Guess(int num){
    //guessNumber

  }
  // TODO: Implement constructor with int and String parameters
  public Guess(int num,String word){
    //guessNumber,chosen word

  }
  // TODO: Implement getGuessNumber(), returning an int
  public int getGuessNumber(){
    return 0;
  }
  // TODO: Implement getChosenWord(), returning a String
  public String getChosenWord(){
    return "ok";
  }
  // TODO: Implement readFromPlayer()
  public void readFromPlayer(){
    //if Guess object is created without initializing the chosen word,
    // this can obtain the word from the player

  }
  // TODO: Implement compareWith(), giving it a String parameter and String return type
  public String compareWith(String target){
    //return a string
    //use ANSI escape codes to 标记颜色
    //green:letter+position right
    //white:letter not
    return "sd";
  }
  // TODO: Implement matches(), giving it a String parameter and boolean return type
  public boolean matches(String target){//evaluate player's guess
    //return true if the player's chosen word matches the target one
    //return false if doesn't match
    return true;
  }
}
