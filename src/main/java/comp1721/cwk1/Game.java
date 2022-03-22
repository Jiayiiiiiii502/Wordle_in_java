package comp1721.cwk1;


import java.io.File;

public class Game {
  //maintains associations with a single WordList object and a sequence of Guess objects

    private int gameNumber;
    private String target;
  // TODO: Implement constructor with String parameter
    public Game(String filename){
      //File file=new File(filename);
      File file=new File("words.txt");

    }
  // TODO: Implement constructor with int and String parameters
    public Game(int num,String filename){

    }
  // TODO: Implement play() method
    public void play(){
      //play en entire game of wordle
      //need to create and store Guess objects
      //check each guess, print the string returned by the compareWith()
    }
  // TODO: Implement save() method, with a String parameter
    public void save(String filename){
      //save a summary (consist of one line for each guess made)
      //this line should contain the string returned by the comparesWith()
    }
}
