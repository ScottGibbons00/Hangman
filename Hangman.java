import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Hangman {
    private ArrayList<String> words;
    private BufferedReader file;
    private HashMap<Character, Integer> frequencies;
    private String finalguess;
    private String origGuess;
    private int rounds;
    private ArrayList<Character> guessedLetters;
    private boolean firstround;


    public Hangman(String guess){

        words=new ArrayList<String>();
        firstround=true;
        guessedLetters= new ArrayList<Character>();
        frequencies=new HashMap<Character, Integer>();
        finalguess="";
        origGuess=guess;
        rounds=0;
        for (int i=0; i<guess.length(); i++){
            finalguess+="-";
        }

        try {
            file = new BufferedReader(new FileReader("DALI/words.txt"));
            String val = file.readLine();

            while (val != null) {
                if (val.length() == guess.length()) {
                    words.add(val);

                }
                val = file.readLine();


            }
            System.out.println(words);

            buildmap();



        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public ArrayList<String> getWords(){return words;}

    public HashMap<Character, Integer> getMap(){return frequencies;}

    public static void main(String[] args) {

        boolean temp=false;
        String word="";
        while (!temp) {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter your word: ");
            word = in.nextLine();
            if (word.length()<5){
                System.out.println("Word must be longer than 5 letters");
            }
            else{
                temp=true;
            }
        }
        Hangman game=new Hangman(word);
        game.play();

    }
    public void play(){
        while (!gameDone()){
            char c=guess();
            if (origGuess.indexOf(""+c)==-1){
                System.out.println("Wrong guess!");
                rounds++;
                printGame(rounds);
                frequencies.remove(c);
            }
            else {
                for (int i = 0; i < finalguess.length(); i++) {
                    if (origGuess.charAt(i) == c) {
                        finalguess = finalguess.substring(0, i) + c + finalguess.substring(i + 1);
                    }
                }
                buildmap();
            }
        }
    }
    public void buildmap(){
        ArrayList<String> temp=new ArrayList<String>();
        for (int i=0; i<words.size(); i++){
            String tempword=words.get(i);
            for (int k=0; k<tempword.length(); k++){
                if (!finalguess.substring(k,k+1).equals("-") && tempword.substring(k,k+1).equals(finalguess.substring(k,k+1))){
                    temp.add(tempword);
                }
            }
        }

        if (!firstround) {
            words = temp;
            firstround=false;
        }
        frequencies=new HashMap<Character, Integer>();
        for (int j=0; j<words.size(); j++) {
            String word = words.get(j);
            int i=0;
            while (i<word.length()-1){
                char c=word.charAt(i);
                if (frequencies.containsKey(c) && !guessedLetters.contains(c)){
                    frequencies.replace(c, frequencies.get(c)+1);
                }
                else if (!guessedLetters.contains(c)){
                    frequencies.put(c,1);
                }
                i++;
            }
        }


    }
    public char guess(){
        char guess='a';
        int max=0;
        for (Character c: frequencies.keySet()){
            if (max<frequencies.get(c).intValue() && !guessedLetters.contains(c)){
                max=frequencies.get(c).intValue();
                guess=c.charValue();
            }
        }
        guessedLetters.add(guess);
        System.out.println("The letter I guess is "+guess);
        return guess;
    }

    public boolean gameDone(){
        if (rounds==6){
            return true;
        }
        if (finalguess.equals(origGuess)){
            System.out.println("I got it right! Your word is "+finalguess);
        }
        return (finalguess.equals(origGuess));
    }
    public void printGame(int round){

        if (round==0){
            System.out.println();
            System.out.println();
            System.out.println("word: "+finalguess);
            System.out.println("  |  ");

        }
        if (round==1){
            printGame(round-1);
            System.out.println("  O  ");
        }
        if (round==2) {
            printGame(round - 1);
            System.out.println("  |  ");


        }
        if (round==3){
            printGame(1);
            System.out.println(" /|\\");
            System.out.println("  |  ");

        }
        if (round==4){
            printGame(2);
            System.out.println(" /|\\");
            System.out.println("  |  ");
        }
        if (round==5){
            printGame(4);
            System.out.println(" /   ");
        }
        if (round==6){
            printGame(4);
            System.out.println(" / \\");
        }
    }

}
