package com.ivanz851;

import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("RegexpSinglelineJava")
public final class HangmanGame {
    private static final String STOP_WORD = "quit";
    private static final int MAX_MISTAKES_MAX_VALUE = 13;
    private final RandomWordGenerator randomWordGenerator = new RandomWordGenerator();
    private String hiddenWord;
    private StringBuilder guessedWord;
    private int maxMistakes;
    private int mistakes;
    private int currentScore;
    private boolean[] guessedLetters;
    private ArrayList<ArrayList<Integer>> lettersPositionsInGuessedWord;

    public HangmanGame() {

    }

    private void initGame() {
        hiddenWord = randomWordGenerator.getRandomWord();
        guessedWord = new StringBuilder("_".repeat(hiddenWord.length()));
        maxMistakes = MAX_MISTAKES_MAX_VALUE;
        mistakes = 0;
        currentScore = 0;

        lettersPositionsInGuessedWord = new ArrayList<>(RandomWordGenerator.getAlphabetSize());
        for (int i = 0; i < RandomWordGenerator.getAlphabetSize(); i++) {
            lettersPositionsInGuessedWord.add(new ArrayList<>());
        }

        guessedLetters = new boolean[RandomWordGenerator.getAlphabetSize()];

        for (int i = 0; i < hiddenWord.length(); ++i) {
            int letterInd = hiddenWord.charAt(i) - 'a';
            lettersPositionsInGuessedWord.get(letterInd).add(i);
        }
    }

    public void startGame() {
        initGame();
        Scanner scanner = new Scanner(System.in);

        printGameIntro();
        playGame(scanner);
    }

    private static void printGameIntro() {
        System.out.println("Welcome to Hangman game!");
        System.out.println("Your goal is to guess the hidden word. The hidden word consists of small Latin letters.");
        System.out.println("If you guess the word by making a mistake no more than X times, "
                + "you will get 2^(" + MAX_MISTAKES_MAX_VALUE + "-X) points");
    }

    private void playGame(Scanner scanner) {
        System.out.println("Please, enter X - integer number from 1 to " + MAX_MISTAKES_MAX_VALUE + ".");
        setMaxMistakes(scanner);

        System.out.println("Enter \"" + STOP_WORD + "\" to quit.\n");

        while (mistakes < maxMistakes && guessedWord.indexOf("_") >= 0) {
            System.out.println("The word: " + guessedWord);
            System.out.print("Guess a letter: ");
            String guessString = scanner.nextLine().toLowerCase().trim();
            System.out.println();

            if (guessString.equals(STOP_WORD)) {
                sumUpTheGame();
                return;
            }

            if (guessString.length() == 1 && isLatinLetter(guessString.charAt(0))) {
                processLetter(guessString.charAt(0));
            } else {
                System.out.println("Please, enter a single Latin letter.");
            }

            System.out.println("\n\n");
        }

        sumUpTheGame();
    }

    private void setMaxMistakes(Scanner scanner) {
        int x;
        while (true) {
            if (scanner.hasNextInt()) {
                x = scanner.nextInt();
                if (1 <= x && x <= MAX_MISTAKES_MAX_VALUE) {
                    System.out.println("OK! If you make " + x + " mistakes, you lose!\n");
                    break;
                }
            }

            System.out.println("ERROR. Please, enter integer number from 1 to " + MAX_MISTAKES_MAX_VALUE + ".");
            scanner.nextLine();
        }

        scanner.nextLine();
        maxMistakes = x;
    }

    private void sumUpTheGame() {
        if (guessedWord.indexOf("_") < 0) {
            System.out.println("You won!");
            currentScore += 1 << (maxMistakes - mistakes);
        } else {
            System.out.println("You lost!");
        }
        System.out.println("The hidden word is " + hiddenWord + ".");
        System.out.println("Final score is " + currentScore + ".");
    }

    private static boolean isLatinLetter(char ch) {
        return Character.isLetter(ch) && (ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z');
    }

    private void processLetter(char c) {
        int guessLetterInd = c - 'a';
        if (guessedLetters[guessLetterInd]) {
            remindAboutAskedLetter(guessLetterInd);
        } else {
            guessedLetters[guessLetterInd] = true;
            if (lettersPositionsInGuessedWord.get(guessLetterInd).isEmpty()) {
                ++mistakes;
                System.out.println("Missed, mistake " + mistakes + " out of " + maxMistakes + ".");
            } else {
                updateGuessedWord(guessLetterInd);
                System.out.println("Hit!");
            }
        }
    }

    private void remindAboutAskedLetter(int guessLetterInd) {
        System.out.println("You have already asked about this letter! ");
        if (lettersPositionsInGuessedWord.get(guessLetterInd).isEmpty()) {
            System.out.println("Hidden word doesn't contain it.");
        } else {
            System.out.println("Hidden word contains it.");
        }
    }

    private void updateGuessedWord(int guessLetterInd) {
        for (Integer pos : lettersPositionsInGuessedWord.get(guessLetterInd)) {
            guessedWord.setCharAt(pos, (char) ('a' + guessLetterInd));
        }
    }
}
