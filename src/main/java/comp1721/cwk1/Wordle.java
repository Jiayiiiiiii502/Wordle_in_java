// Main program for COMP1721 Coursework 1
// DO NOT CHANGE THIS!

package comp1721.cwk1;

import java.io.IOException;
import java.util.Objects;


public class Wordle {
public static void main(String[] args) throws IOException {
  Game game;
  //run visual impaired version with specific target
  if(args.length==2){
    if(args[0].equals("-a")){
      System.out.println("Welcome to the visual impaired version!\n");
      game=new Game(Integer.parseInt(args[1]),"data/words.txt");
      game.play_impair();
    }
    else{
      System.out.println("Please input valid command!\n");
    }
  }

  //run visual version or specific normal version
  else if(args.length==1){
    if(Objects.equals(args[0], "-a")){
      game=new Game("data/words.txt");
      game.play_impair();
    }
    else{
      game=new Game(Integer.parseInt(args[0]),"data/words.txt");
      game.play();
      game.save("build/lastgame.txt");
    }
  }

  //run normal version without specific target
  else{
    game=new Game("data/words.txt");
    game.play();
    game.save("build/lastgame.txt");
  }
}
}
