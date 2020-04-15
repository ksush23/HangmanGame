import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Hangman implements Game {
    private List<String> words = new ArrayList<>();
    public Scanner scanner = new Scanner(System.in);
    private char[] word;
    private List<Character> missedLetters = new ArrayList<>();
    private HashMap<Character, Integer> quantity = new HashMap<>();
    private int counter = 0;
    private Picture picture = new Picture();
    private String randomWord;

    public void play() throws FileNotFoundException{
        if (getName() == 1){
            getLevel();

            generateWord();
            word = new char[randomWord.length()];
            for (int i = 0; i < randomWord.length(); i++){
                word[i] = '0';
            }

            char letter = ' ';
            boolean willContinue = true;
            while (willContinue) {
                display(letter);
                counter++;
                int letterOrWord;
                do {
                    System.out.println("Press 1 to enter a letter");
                    System.out.println("Press 2 to enter the whole word");
                    System.out.println("Enter your number of choice:");
                    letterOrWord = scanner.nextInt();
                }while (letterOrWord != 1 && letterOrWord != 2);

                if (letterOrWord == 1){
                    System.out.println("Enter letter: ");
                    scanner.nextLine();
                    letter = scanner.nextLine().charAt(0);
                    if (!Character.isUpperCase(letter)){
                        letter = Character.toUpperCase(letter);
                    }

                    if (quantity.containsKey(letter)){
                        int quant = quantity.get(letter);
                        quantity.replace(letter, quant + 1);
                    }
                    else{
                        quantity.put(letter, 1);
                    }

                    if (randomWord.contains(Character.toString(letter))){
                        int beginIndex = 0;
                        int index;
                        while (randomWord.indexOf(letter, beginIndex) != -1){
                            index = randomWord.indexOf(letter, beginIndex);
                            word[index] = letter;
                            beginIndex = index + 1;
                            if (beginIndex >= randomWord.length()){
                                break;
                            }
                        }
                        willContinue = checkWord(word);
                        if (!willContinue){
                            results(true);
                        }
                    }
                    else {
                        if (!missedLetters.contains(letter)) {
                            missedLetters.add(letter);
                        }
                        willContinue = picture.reveal();
                        if (!willContinue){
                            results(false);
                        }
                    }
                }
                if (letterOrWord == 2){
                    System.out.println("Enter the whole word: ");
                    scanner.nextLine();
                    String wholeWord = scanner.nextLine();
                    wholeWord = wholeWord.toUpperCase();
                    if (wholeWord.equals(randomWord)){
                        results(true);
                    }
                    else{
                        System.out.println("Wrong guess!");
                        willContinue = picture.reveal();
                        if (!willContinue){
                            results(false);
                        }
                    }
                }
            }
        }
    }

    public int getName() {
        int choice;
        do{
            System.out.println("Press 1 to play Hangman");
            System.out.println("Other games are coming soon...");
            System.out.println("Enter your game of choice:");

            choice = scanner.nextInt();
        }while(choice != 1);

        return choice;
    }

    public void getLevel() throws FileNotFoundException {
        int choice;
        do{
            System.out.println("1 is an easy level");
            System.out.println("2 is a medium level");
            System.out.println("3 is a hard level");
            System.out.println("4 is a challenging level");
            System.out.println("Enter your level of choice:");
            choice = scanner.nextInt();
        } while(choice != 1 && choice != 2 && choice != 3 && choice != 4);

        FileReader fileReader = new FileReader("word_database.txt");
        Scanner fileScanner = new Scanner(fileReader);

        String line;
        do {
            line = fileScanner.nextLine();
        }while (!line.equals(Integer.toString(choice)));

        while (true){
            line = fileScanner.nextLine();
            if (line.equals(Integer.toString(choice + 1))){
                break;
            }
            words.add(line);
        }
    }

    public void display(char guess){
        System.out.print("Word: ");
        for (int i = 0; i < randomWord.length(); i++){
            if (word[i] != '0'){
                System.out.print(word[i]);
            }
            else {
                System.out.print("_");
            }
        }
        System.out.println();
        System.out.println("Guess: " + guess);
        System.out.print("Misses:");
        for (Character ch: missedLetters
             ) {
            System.out.print(ch + " ");
        }
        System.out.println();

        picture.printOut();
    }

    public void generateWord(){
        Random random = new Random();
            int rand = random.nextInt(words.size());
            randomWord = words.get(rand);
    }

    public void results (boolean won) throws FileNotFoundException{
        if (won){
            System.out.println("Congrats! You won");
        }
        else {
            System.out.println("Word: " + randomWord);
            picture.printOutWhole();
            System.out.println("Sorry, you lost");
        }
        System.out.println("Game statistics: ");
        System.out.println("It took " + counter + " tries");
        System.out.println("You used: ");
        for (Character ch: quantity.keySet()
             ) {
            System.out.println(ch + ": " + quantity.get(ch) + " time(s)");
        }

        int choice;
        do {
            System.out.println("\nWanna continue?\n");
            System.out.println("Press 1 to start a new game");
            System.out.println("Press 2 to quit");
            choice = scanner.nextInt();
        }while(choice != 1 && choice != 2);

        if (choice == 1){
            counter = 0;
            picture = new Picture();
            quantity.clear();
            missedLetters.clear();
            play();
        }
        else{
            return;
        }
    }

    public boolean checkWord(char[]word){
        if (String.copyValueOf(word).equals(randomWord))
            return false;
        return true;
    }
}
