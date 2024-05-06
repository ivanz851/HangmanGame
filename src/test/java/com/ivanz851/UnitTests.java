package com.ivanz851;

import org.junit.Test;
///import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnitTests {
    @Test
    public void testRandomWordGenerator() {
        int wordsListLength = RandomWordGenerator.getWordsListLength();
        ArrayList<Character> alphabet = RandomWordGenerator.getAlphabet();

        for (int i = 0; i < wordsListLength; ++i) {
            String word = RandomWordGenerator.getWordByIndex(i);
            for (char c : word.toCharArray()) {
                assertTrue(alphabet.contains(c),
                    "Incorrect word \"" + word + "\" in words list - it contains character '" + "c" +
                        "' that is not contained in the alphabet.");
            }
        }
    }
}
