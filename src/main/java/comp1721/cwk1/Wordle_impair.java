package comp1721.cwk1;

import java.io.IOException;

public class Wordle_impair {
    public static void main(String[] args) throws IOException, IOException {
        System.out.println("Welcome to visual impaired version of Wordle!\n");
        Game game;
        if(args.length>0){
            game=new Game(Integer.parseInt(args[1]),"data/words.txt");
        }
        else{
            game=new Game("data/words.txt");
        }

        game.play_impair();
    }

}
