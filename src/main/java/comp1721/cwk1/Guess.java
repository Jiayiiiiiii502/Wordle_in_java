package comp1721.cwk1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
      throw new GameException("Invalid choice!\n");
    }
  }



  // TODO: Implement constructor with int and String parameters
  public Guess(int num,String word){
    //validate guessNumber
    if((num<=6) &&(num>=1)){
        guessNumber=num;
    }
    else{
      throw new GameException("Invalid choice!\n");
    }
    //validate word length and type
    if(word.length()==5) {
      int i = 0;
      int m = 0;
      for (i = 0; i < 5; i++) {
        int chr = word.charAt(i);
        if ((chr < 65 || chr > 90) && (chr < 97 || chr > 122)) {
          System.out.println(chr);
          m++;
        }
        if (m > 0) {
          throw new GameException("Invalid choice!\n");
        } else {
          chosenWord = word.toUpperCase();
        }
      }
    }
    else{
      throw new GameException("Invalid choice!\n");
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
    chosenWord=INPUT.nextLine();
  }



  // TODO: Implement compareWith(), giving it a String parameter and String return type

  public String compareWith(String target){
    target=target.toUpperCase();
    char[] tarArray=target.toCharArray();
    char[] choArray=chosenWord.toCharArray();
    String[] choout=new String[5];
    //List<String> choArray_out=new ArrayList<>();
    int i=0;
    int j=0;
    int m=0;
    int temp=0;
    for(m=0;m<choArray.length;m++){
      choout[m]="\033[30;107m "+choArray[m]+" \033[0m";
    }
    for(i=0;i<choArray.length;i++) {
      for (j = 0; j < tarArray.length; j++) {
        if (choArray[i] == tarArray[i]) {
          choout[i] = "\033[30;102m " + choArray[i] + " \033[0m";
        }
        if (choArray[i] == tarArray[j] && i != j) {
          choout[i] = "\033[30;103m " + choArray[i] + " \033[0m";
          for(temp=0;temp<i;temp++){
            if(choArray[i]==choArray[temp]){
              choout[i]="\033[30;107m "+choArray[i]+" \033[0m";
            }
          }
        }
      }
    }

    String output = choout[0] + choout[1] + choout[2] + choout[3] + choout[4];
    return output;
  }
  // TODO: Implement matches(), giving it a String parameter and boolean return type
  public boolean matches(String target){//evaluate player's guess
    //return true if the player's chosen word matches the target one
    //return false if doesn't match
    target=target.toUpperCase();
    if(target.equals(chosenWord)){
      return true;
    }
    else{
      return false;
    }
  }
}
