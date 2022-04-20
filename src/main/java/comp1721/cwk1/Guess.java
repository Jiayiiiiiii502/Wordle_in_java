package comp1721.cwk1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;



public class Guess {
  private int guessNumber;//range:1-6
  private String chosenWord;//store the chosen word by the player
  // Use this to get player input in readFromPlayer()
  private static final Scanner INPUT = new Scanner(System.in);

  // TODO: Implement constructor with int parameter
  public Guess(int num) {
    //initialize the guessNumber & check to throw
    if ((num <= 6) && (num >= 1)) {
      guessNumber = num;
    } else {
      throw new GameException("Invalid!");
    }
  }


  // TODO: Implement constructor with int and String parameters
  public Guess(int num, String word) {
    //validate guessNumber
    if ((num <= 6) && (num >= 1)) {
      guessNumber = num;
    } else {
      throw new GameException("Invalid choice!\n");
    }
    //validate word length and type
    if (word.length() == 5) {
      int i;
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
    } else {
      throw new GameException("Invalid choice!\n");
    }

  }


  // TODO: Implement getGuessNumber(), returning an int
  public int getGuessNumber() {
    return guessNumber;
  }

  // TODO: Implement getChosenWord(), returning a String
  public String getChosenWord() {  //用户输入，需要被猜的单词另外存储
    return chosenWord;
  }


  // TODO: Implement readFromPlayer()
  public void readFromPlayer() {
    //if Guess object is created without initializing the chosen word,
    // this can obtain the word from the player
    chosenWord = INPUT.nextLine();
  }


  public void compare_impair(String target) {
    target = target.toUpperCase();
    char[] tarArray = target.toCharArray();
    char[] choArray = chosenWord.toCharArray();
//    String[] choout = new String[5];

    int i;
    int j;

    HashMap<Integer, Integer> result = new HashMap<>();
    result.put(1, 0);
    result.put(2, 0);
    result.put(3, 0);
    result.put(4, 0);
    result.put(5, 0);

    for (i = 0; i < choArray.length; i++) {
      for (j = 0; j < tarArray.length; j++) {
        if (choArray[i] == tarArray[i]) {
          result.replace(i + 1, 2);
        }
        if (choArray[i] == tarArray[j] && i != j) {
          result.replace(i + 1, 1);
        }
      }
    }

    List<String> perfect = new ArrayList<>();
    List<String> correct = new ArrayList<>();
    int perfect_num = 0;
    int correct_num = 0;
    for (Integer m : result.keySet()) {
      if (result.get(m) == 2) {
        if (m == 1) {
          perfect.add("1st");
          perfect_num++;
        }
        if (m == 2) {
          perfect.add("2nd");
          perfect_num++;
        }
        if (m == 3) {
          perfect.add("3rd");
          perfect_num++;
        }
        if (m == 4) {
          perfect.add("4th");
          perfect_num++;
        }
        if (m == 5) {
          perfect.add("5th");
          perfect_num++;
        }
      }

      if (result.get(m) == 1) {
        if (m == 1) {
          correct.add("1st");
          correct_num++;
        }
        if (m == 2) {
          correct.add("2nd");
          correct_num++;
        }
        if (m == 3) {
          correct.add("3rd");
          correct_num++;
        }
        if (m == 4) {
          correct.add("4th");
          correct_num++;
        }
        if (m == 5) {
          correct.add("5th");
          correct_num++;
        }
      }

    }
    if (perfect_num > 0) {
      System.out.print("The ");
      for (int k = 0; k < perfect_num; k++) {
        if (k == perfect_num - 1) {
          System.out.print(perfect.get(k));
          break;
        }
        System.out.print((perfect.get(k)) + ", ");
      }
      System.out.print(" perfect!\n");
    }

    if (correct_num > 0) {
      System.out.print("The ");
      for (int k = 0; k < correct_num; k++) {
        if (k == correct_num - 1) {
          System.out.print(correct.get(k));
          break;
        }
        System.out.print((correct.get(k)) + ", ");
      }
      System.out.print(" correct but in the wrong place!\n");
    }
//    return "ok\n";
  }


  // TODO: Implement compareWith(), giving it a String parameter and String return type
  public String compareWith(String target){
    target=target.toUpperCase();
    char[] tarArray=target.toCharArray();
    char[] choArray=chosenWord.toCharArray();
    String[] choout=new String[5];
    int i;
    int j;
    int m;
    int temp;
    for(m=0;m<choArray.length;m++){
      choout[m]="\033[30;107m "+choArray[m]+" \033[0m";
    }
    for(i=0;i<choArray.length;i++) {
      for (j = 0; j < tarArray.length; j++) {
        if (choArray[i] == tarArray[i]) {
          choout[i] = "\033[30;102m " + choArray[i] + " \033[0m";
          break;
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



    return choout[0] + choout[1] + choout[2] + choout[3] + choout[4];
  }



  // TODO: Implement matches(), giving it a String parameter and boolean return type
  public boolean matches(String target){
    //return true if the player's chosen word matches the target one
    //return false if doesn't match
    target=target.toUpperCase();
    return target.equals(chosenWord);
  }
}
