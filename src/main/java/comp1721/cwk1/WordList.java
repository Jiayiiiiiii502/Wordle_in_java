package comp1721.cwk1;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WordList {
    private List<String> words=new ArrayList<String>();


  // TODO: Implement constructor with a String parameter
    public WordList(String filename) throws IOException {
        //words are loaded from this file, the name of file is supplied when creating a Wordlist object
        File file=new File(filename);
        FileReader fileReader=new FileReader(file);
        BufferedReader bufferedReader=new BufferedReader(fileReader);
        String str=null;
        while((str=bufferedReader.readLine())!=null){
            if(str.trim().length()>2){
                words.add(str);
            }
        }
    }



  // TODO: Implement size() method, returning an int
    public int size(){
        //the size of words list
        return words.size();
    }



  // TODO: Implement getWord() with an int parameter, returning a String
    public String getWord(int n){
        if(n>=0 && n<size()){
            return words.get(n);
        }
        else{
            throw new GameException("Invalid choice!\n");
        }
    }
}
