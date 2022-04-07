package comp1721.cwk1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Guess {
  private int guessNumber;//range:1-6
  private String chosenWord;//store the chosen word by the player
  // Use this to get player input in readFromPlayer()
  private static final Scanner INPUT = new Scanner(System.in);

  // TODO: Implement constructor with int parameter
  public Guess(int num){
    //initialize the guessNumber & check to throw
    if((num<=6) &&(num>=1)){
      guessNumber=num;
    }
    else{
      throw new GameException("Invalid num!\n");
    }
  }



  // TODO: Implement constructor with int and String parameters
  public Guess(int num,String word){
    //guessNumber,chosen word
    if((num<=6) &&(num>=1)){
        guessNumber=num;
    }
    else{
      throw new GameException("Invalid num!\n");
    }
    if(word.length()==5){
      word.toUpperCase();
      chosenWord=word;
    }
    else{
      throw new GameException("Invalid words!\n");
    }
    }




  // TODO: Implement getGuessNumber(), returning an int
  public int getGuessNumber(){
    return guessNumber;
  }
  // TODO: Implement getChosenWord(), returning a String
  public String getChosenWord(){  //用户输入，需要被猜的单词另外存储
    return chosenWord;
  }



  // TODO: Implement readFromPlayer()
  public void readFromPlayer(){
    //if Guess object is created without initializing the chosen word,
    // this can obtain the word from the player

  }



  // TODO: Implement compareWith(), giving it a String parameter and String return type
//target是答案，chosen是用户输入
  public String compareWith(String target){
    //return a string
    //use ANSI escape codes to 标记颜色
    //green:letter+position right
    //yellow:letter
    //white:letter not
    target.toUpperCase();
    char[] tarArray=target.toCharArray();
    char[] choArray=chosenWord.toCharArray();
    String[] choout=new String[5];
    //List<String> choArray_out=new ArrayList<>();
    int i=0;
    int j=0;
    for(i=0;i<choArray.length;i++) {
      for (j = 0; j < tarArray.length; j++) {
        if (choArray[i] == tarArray[i]) {
          choout[i] = "\033[30;102m " + choArray[i] + "  \\033[0m";
        }
        if (choArray[i] == tarArray[j] && i != j) {
          choout[i] = "\033[30;103m " + choArray[i] + "  \\033[0m";
        }
      }
    }
//
//        else{
//          choout[i]="\033[30;107m "+choArray[i]+"  \\033[0m";
//        }
//      }

    String output = choout[0] + choout[1] + choout[2] + choout[3] + choout[4];
    return output;
  }
  // TODO: Implement matches(), giving it a String parameter and boolean return type
  public boolean matches(String target){//evaluate player's guess
    //return true if the player's chosen word matches the target one
    //return false if doesn't match
    return true;
  }
}
