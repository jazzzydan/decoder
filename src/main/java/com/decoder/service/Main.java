package main.java.com.decoder.service;


import main.java.com.decoder.entity.SMS;
import main.java.com.decoder.util.AlphabetFunction;
import main.java.com.decoder.util.HexBinaryFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        SMS sms = new SMS();
        System.out.println(decode(sms.getSender()));
    }

    private static String decode(String input) {
        List<Character> reversedHexString = reverseToList(input);
        List<String> binaryList = hexListToBinaryList(reversedHexString);
        List<String> reformattedBinaryList = reformatBinaryList(binaryList);
        List<String> characters = sevenBitChunksToCharacters(reformattedBinaryList);
        return charactersListToString(characters);
    }

    public static List<Character> reverseToList(String hexString) {
        List<Character> charList = new ArrayList<>();
        for (int i = hexString.length() - 1; i >= 0; i--) {
            Character hexChar = hexString.charAt(i);
            charList.add(hexChar);
        }
        return charList;
    }

    public static List<String> hexListToBinaryList(List<Character> hexList) {
        Map<Character, String> hexToBinaryMap = HexBinaryFunction.hex2BinHashMap();
        List<String> binaryList = new ArrayList<>();
        for (Character hexChar : hexList) {
            String binaryString = hexToBinaryMap.get(hexChar);
            binaryList.add(binaryString);
        }
        return binaryList;
    }

    public static List<String> reformatBinaryList(List<String> binaryList) {

        StringBuilder binaryString = new StringBuilder();
        for (String binary : binaryList) {
            binaryString.append(binary);
        }

        int i = 0;
        while (i < binaryString.length() && binaryString.charAt(i) == '0') {
            i++;
        }
        binaryString.delete(0, i);

        List<String> sevenBitChunks = new ArrayList<>();
        String currentChunk = "";
        for (i = binaryString.length() - 1; i >= 0; i--) {
            currentChunk = binaryString.charAt(i) + currentChunk;
            if (currentChunk.length() == 7 || i == 0) {
                sevenBitChunks.add(0, currentChunk);
                currentChunk = "";
            }
        }

        return sevenBitChunks;
    }

    public static List<String> sevenBitChunksToCharacters(List<String> binaryChunks) {
        Map<String, String> smsAlphabet = AlphabetFunction.bin2CharacterHashMap();
        List<String> characters = new ArrayList<>();
        for (String binaryChunk : binaryChunks) {
            String string = smsAlphabet.get(binaryChunk);
            characters.add(string);
        }
        return characters;
    }

    public static String charactersListToString(List<String> characterList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String character : characterList) {
            stringBuilder.insert(0, character);
        }
        return stringBuilder.toString();
    }
}