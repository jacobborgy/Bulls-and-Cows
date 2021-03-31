package bullscows;

import java.util.*;
import java.lang.*;

public class Main {

    ////////////////////////////////
    //            Main            //
    ////////////////////////////////
    public static void main(String[] args) {

        // Gets length
        int length = length();

        // Gets range of characters
        int range = rangeOfCharacters(length);

        // Fills a char array with the symbols to be used
        char[] symbolArray = fillSymbolArray(range);

        // Creates an array that has the random code
        char[] code = generateRandomCode(length, symbolArray);

        // Begins game of Bulls and Cows
        startGame(code);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Module gets the grade of the guess and returns true or false depending on if all the bulls have been guessed //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean grade(char[] code, char[] guess) {

        // Variables
        int bulls = 0;
        int cows = 0;
        String cowStr = " cow";
        String bullStr = " bull";

        // Nested for loop to keep track of how many bulls and cows are in each guess
        for (int i = 0; i < guess.length; i++) {
            if (guess[i] == code[i]) {
                bulls++;
                cows--;
            }
            for (int j = 0; j < code.length; j++) {
                if (guess[i] == code[j]) {
                    cows++;
                }
            }
        }

        // Conditionals to make the string variables plural if there is more than 1
        if (cows > 1) {
            cowStr += "s";
        }
        if (bulls > 1) {
            bullStr += "s";
        }

        // Conditionals to print the grade
        if (cows == 0 && bulls == 0) {
            System.out.println("Grade: 0 bulls and 0 cows");
        } else if (bulls == 0) {
            System.out.println("Grade: " + cows + cowStr);
        } else if (cows == 0) {
            System.out.println("Grade: " + bulls + bullStr);
        } else {
            System.out.println("Grade: " + bulls + bullStr + " and " + cows + cowStr);
        }

        // Conditional to check if all of bulls have been guessed
        if (bulls == code.length) {

            // If all bulls have been guessed, return True
            return true;
        }

        // If not all the bulls have been guessed, return False
        return false;
    }

    ///////////////////////////////////////////////////////////
    // Function to check if string has all unique characters //
    ///////////////////////////////////////////////////////////
    public static boolean uniqueCharacters(String str) {

        // If the same character is encountered, return false
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    return false;
                }
            }
        }

        // If the same character is not encountered, return true
        return true;
    }

    /////////////////////////////////////////////////////////
    // Module checks user input guess and checks its grade //
    /////////////////////////////////////////////////////////
    public static boolean guess(char[] code) {

        // Scanner
        Scanner sc = new Scanner(System.in);
        System.out.print("> ");

        // String variable
        String guess = sc.nextLine().toLowerCase();

        // Conditional to make sure guess is the same length as the secret code and that all characters are unique
        if (guess.length() != code.length) {
            System.out.println("Error! Your Guess must be the same length as the secret code, try again.");
            guess(code);
            return false;
        } /*else if (!uniqueCharacters(guess)) {
            System.out.println("Error! Your Guess must have all unique characters, try again.");
            guess(code);
            return false;
        }*/

        // Char array from sting variable
        char[] guessArr = new char[guess.length()];

        // For loop to covert from string to char array
        for (int i = 0; i < guess.length(); i++) {
            guessArr[i] = guess.charAt(i);
        }

        // Use code array and guess array and parameters for grade, returns boolean outcome
        return grade(code, guessArr);
    }

    /////////////////////////////////
    // Module that starts the game //
    /////////////////////////////////
    public static void startGame(char[] code) {
        System.out.println("Okay, let's start a game!");

        // Variables
        boolean check = false;
        int turnNum = 1;

        // While loop keeps looping until the user has guessed the right code
        while (!check) {
            System.out.println("Turn " + turnNum + ":");
            check = guess(code);
            turnNum++;
        }

        // Print statement saying the code has been guess
        System.out.println("Congratulations! You guessed the secret code.");
    }

    //////////////////////////////////////////////////////////////
    // Module to generate phrase for successful code generation //
    //////////////////////////////////////////////////////////////
    private static String phrase(int length, char[] symbolArray) {

        // Variable for start of phrase
        String phrase = "The secret is prepared: ";

        // For loop to add the amount of *'s needed
        for (int i = 0; i < length; i++) {
            phrase += "*";
        }

        // Conditional to get the correct range of symbols that will be used
        if (symbolArray.length < 11) {
            phrase += " (" + symbolArray[0] + "-" + symbolArray[symbolArray.length - 1] + ").";
        } else {
            phrase += " (0-9, a-" +  symbolArray[symbolArray.length - 1] + ").";
        }

        // Returns the completed phrase
        return phrase;
    }

    /////////////////////////////////////////////////
    // Function to remove an element from an array //
    /////////////////////////////////////////////////
    public static char[] removeTheElement(char[] arr, int index) {

        // Checks if array is empty or if index is within array range
        if (arr == null || index < 0 || index >= arr.length) {

            // Returns the original array
            return arr;
        }

        // Create another array with the size being one less than original array
        char[] anotherArray = new char[arr.length - 1];

        // Copies elements from original array to newly created array, besides the index
        for (int i = 0, j = 0; i < arr.length; i++) {

            // If I is equal to index, continue without copying the element
            if (i == index) {
                continue;
            }

            // If not, copy element
            anotherArray[j++] = arr[i];
        }

        // returns the copied array with the one element removed
        return anotherArray;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    // Module that returns a randomly generated code using the length and range of symbols provided //
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public static char[] generateRandomCode(int length, char[] symbolArray) {

        // Instance of the random class
        Random rand = new Random();

        // Char array using the length given in the parameter as size
        char[] randomCode = new char[length];

        // Gets phrase that will be printed at the end of the function
        String phrase = phrase(length, symbolArray);

        // For loop to fill char array with symbols from the symbolArray parameter
        for (int i = 0; i < length; i++) {
            int randomInt = rand.nextInt(symbolArray.length);
            randomCode[i] = symbolArray[randomInt];

            // Removes the symbol from symbolArray
            symbolArray = removeTheElement(symbolArray, randomInt);
        }

        // Prints phrase for successful code generation
        System.out.println(phrase);

        // Returns the randomly generated code
        return randomCode;
    }

    /////////////////////////////////////////////////////////////////////////////
    // Module that returns a char array that is filled with a range of symbols //
    /////////////////////////////////////////////////////////////////////////////
    private static char[] fillSymbolArray(int range) {

        // Char array
        char[] symbolArray = new char[range];

        // For loop to fill the char array with the range of symbols given
        for (int i = 0; i < range; i++) {

            // Temporary variable to help convert from a decimal value to a character
            int temp;

            // Conditional to make sure ranges (0-9) and (a-z) are being converted properly
            if (i < 10) {
                temp = (i+48);
                char c = (char)temp;
                symbolArray[i] = c;
            } else {
                temp = (i+87);
                char c = (char)temp;
                symbolArray[i] = c;
            }
        }

        // Returns filled char array
        return symbolArray;

    }

    ///////////////////////////////////////////////////////////////////////////////////
    // Module that returns the range of symbols that can be used for the random code //
    ///////////////////////////////////////////////////////////////////////////////////
    private static int rangeOfCharacters(int length) {

        // Scanner for reading input
        Scanner sc = new Scanner(System.in);

        // Variable
        int range = 0;

        // Do-while loop to check for incorrect input
        do {
            try {
                System.out.print("Input the number of possible symbols in the code:\n> ");
                range = sc.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("Error, Input needs to be a whole number! Try again.");
            }

            // Clears the buffer
            sc.nextLine();

        } while(range < length || range > 36);

        // Returns user given range
        return range;
    }

    //////////////////////////////////////////////
    // Module to get length for the random code //
    //////////////////////////////////////////////
    private static int length() {

        // Scanner for reading input
        Scanner sc = new Scanner(System.in);

        // Variable
        int length = 0;

        // Do-while loop to check for incorrect input
        do {
            try {
                System.out.print("Input the length of the secret code:\n> ");
                length = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error, Input needs to be a whole number! Try again.");
            }

            // Clears the buffer
            sc.nextLine();

        } while(length < 1 || length > 36);

        // Returns user given length
        return length;
    }
}
