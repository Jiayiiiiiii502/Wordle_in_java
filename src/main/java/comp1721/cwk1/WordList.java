package comp1721.cwk1;


import java.util.ArrayList;
import java.util.List;

public class WordList {
    //represents the liar of words used by Wordle
    private List<String> words=new ArrayList<>();
  // TODO: Implement constructor with a String parameter
    public WordList(String filename){
        //words are loaded from this file, the name of file is supplied when creating a Wordlist object
    }
  // TODO: Implement size() method, returning an int
    public int size(){
        //the size of words list
        return 0;
    }
  // TODO: Implement getWord() with an int parameter, returning a String
    public String getWords(int n){
        //a word is chosen from the list using this method,int represent the game number
        //0--it should return the first word in the list,1--return the second word
        return "ok";
    }
}
