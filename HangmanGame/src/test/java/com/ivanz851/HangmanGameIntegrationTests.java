package com.ivanz851;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

@PrepareForTest(HangmanGame.class)
public class HangmanGameIntegrationTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Случай, когда превышено число попыток угадать букву")
    public void testHangmanGameNumberOfAttemptsExceeded() throws Exception {
        String input = "3\na\nb\nc\nquit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        RandomWordGenerator randomWordGenerator = Mockito.mock(RandomWordGenerator.class);
        Mockito.when(randomWordGenerator.getRandomWord()).thenReturn("apple");

        HangmanGame hangmanGame = new HangmanGame();

        Field field = HangmanGame.class.getDeclaredField("randomWordGenerator");
        field.setAccessible(true);
        field.set(hangmanGame, randomWordGenerator);

        hangmanGame.startGame();

        String expectedOutput = "Welcome to Hangman game!\n" +
            "Your goal is to guess the hidden word. The hidden word consists of small Latin letters.\n" +
            "If you guess the word by making a mistake no more than X times, you will get 2^(13-X) points\n" +
            "Please, enter X - integer number from 1 to 13.\n" +
            "OK! If you make 3 mistakes, you lose!\n" +
            "\n" +
            "Enter \"quit\" to quit.\n" +
            "\n" +
            "The word: _____\n" +
            "Guess a letter: \n" +
            "Hit!\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: a____\n" +
            "Guess a letter: \n" +
            "Missed, mistake 1 out of 3.\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: a____\n" +
            "Guess a letter: \n" +
            "Missed, mistake 2 out of 3.\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: a____\n" +
            "Guess a letter: \n" +
            "You lost!\n" +
            "The hidden word is apple.\n" +
            "Final score is 0.\n";

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @DisplayName("Случай, когда игра завершается победой игрока")
    public void testHangmanGameSuccess() throws Exception {
        String input = "10\na\nb\nc\nx\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        RandomWordGenerator randomWordGenerator = Mockito.mock(RandomWordGenerator.class);
        Mockito.when(randomWordGenerator.getRandomWord()).thenReturn("abx");

        HangmanGame hangmanGame = new HangmanGame();

        Field field = HangmanGame.class.getDeclaredField("randomWordGenerator");
        field.setAccessible(true);
        field.set(hangmanGame, randomWordGenerator);

        hangmanGame.startGame();

        String expectedOutput = "Welcome to Hangman game!\n" +
            "Your goal is to guess the hidden word. The hidden word consists of small Latin letters.\n" +
            "If you guess the word by making a mistake no more than X times, you will get 2^(13-X) points\n" +
            "Please, enter X - integer number from 1 to 13.\n" +
            "OK! If you make 10 mistakes, you lose!\n" +
            "\n" +
            "Enter \"quit\" to quit.\n" +
            "\n" +
            "The word: ___\n" +
            "Guess a letter: \n" +
            "Hit!\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: a__\n" +
            "Guess a letter: \n" +
            "Hit!\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: ab_\n" +
            "Guess a letter: \n" +
            "Missed, mistake 1 out of 10.\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: ab_\n" +
            "Guess a letter: \n" +
            "Hit!\n" +
            "\n" +
            "\n" +
            "\n" +
            "You won!\n" +
            "The hidden word is abx.\n" +
            "Final score is 512.\n";

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @DisplayName("Рассматриваем различные варианты ввода, в том числе некорректные")
    public void testHangmanInputCOrrectness() throws Exception {
        String input = "5\na\nпривет\nпока\na\nc\nc\ng\ng\nm\nn\ni\nj\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        RandomWordGenerator randomWordGenerator = Mockito.mock(RandomWordGenerator.class);
        Mockito.when(randomWordGenerator.getRandomWord()).thenReturn("acbb");

        HangmanGame hangmanGame = new HangmanGame();

        Field field = HangmanGame.class.getDeclaredField("randomWordGenerator");
        field.setAccessible(true);
        field.set(hangmanGame, randomWordGenerator);

        hangmanGame.startGame();

        String expectedOutput = "Welcome to Hangman game!\n" +
            "Your goal is to guess the hidden word. The hidden word consists of small Latin letters.\n" +
            "If you guess the word by making a mistake no more than X times, you will get 2^(13-X) points\n" +
            "Please, enter X - integer number from 1 to 13.\n" +
            "OK! If you make 5 mistakes, you lose!\n" +
            "\n" +
            "Enter \"quit\" to quit.\n" +
            "\n" +
            "The word: ____\n" +
            "Guess a letter: \n" +
            "Hit!\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: a___\n" +
            "Guess a letter: \n" +
            "Please, enter a single Latin letter.\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: a___\n" +
            "Guess a letter: \n" +
            "Please, enter a single Latin letter.\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: a___\n" +
            "Guess a letter: \n" +
            "You have already asked about this letter! \n" +
            "Hidden word contains it.\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: a___\n" +
            "Guess a letter: \n" +
            "Hit!\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: ac__\n" +
            "Guess a letter: \n" +
            "You have already asked about this letter! \n" +
            "Hidden word contains it.\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: ac__\n" +
            "Guess a letter: \n" +
            "Missed, mistake 1 out of 5.\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: ac__\n" +
            "Guess a letter: \n" +
            "You have already asked about this letter! \n" +
            "Hidden word doesn't contain it.\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: ac__\n" +
            "Guess a letter: \n" +
            "Missed, mistake 2 out of 5.\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: ac__\n" +
            "Guess a letter: \n" +
            "Missed, mistake 3 out of 5.\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: ac__\n" +
            "Guess a letter: \n" +
            "Missed, mistake 4 out of 5.\n" +
            "\n" +
            "\n" +
            "\n" +
            "The word: ac__\n" +
            "Guess a letter: \n" +
            "Missed, mistake 5 out of 5.\n" +
            "\n" +
            "\n" +
            "\n" +
            "You lost!\n" +
            "The hidden word is acbb.\n" +
            "Final score is 0.\n";

        assertEquals(expectedOutput, outContent.toString());
    }
}
