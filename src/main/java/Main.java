import IO.JsonFileReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class Main {
    final static String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    final static String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final static String NUMBERS = "0123456789";
    final static String SPECIAL_CHARACTERS = "~`!@#$%^&*()_+-={}[]|\\:\";>?./<,'";
    static char[] CHARACTERS = {};
    static Random randObject = new Random();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Count: ");
        long count = input.nextInt();

        JsonFileReader js = new JsonFileReader("settings.json");

        int length = js.get("length").asInt();
        boolean includeLowerCase = js.get("lowerCase").asBoolean();
        boolean includeUpperCase = js.get("upperCase").asBoolean();
        boolean includeNumbers = js.get("numbers").asBoolean();
        boolean specialCharacters = js.get("specialCharacters").asBoolean();
        {
            String str = "";
            if (includeLowerCase)
                str += LOWERCASE_LETTERS;
            if (includeUpperCase)
                str += UPPERCASE_LETTERS;
            if (includeNumbers)
                str += NUMBERS;
            if (specialCharacters)
                str += SPECIAL_CHARACTERS;

            CHARACTERS = str.toCharArray();
        }

        String prefix = js.get("prefix").asText();
        String suffix = js.get("suffix").asText();

        long start = System.currentTimeMillis();
        makeRandomList(count, length, prefix, suffix);
        long end = System.currentTimeMillis();
        System.out.println("\nTime: "+(end-start)/1000.0+"s");
    }
    public static void makeRandomList(long count, int length, String prefix, String postfix) {
        StringBuilder str = new StringBuilder();
        for (long i=1; i<=count; i++) {
            String word =prefix+makeRandomWord(length)+postfix;
            str.append(word).append("\n");

            //System.out.printf("\r%,d -> %s", i, word);
        }
        saveToFile("output.txt", str);
    }
    public static void saveToFile(String fileLocation, StringBuilder str) {

        try {
            File f = new File(fileLocation);
            if (!f.exists())
                f.createNewFile();
            FileWriter fw = new FileWriter(fileLocation);
            fw.append(String.valueOf(str));
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static char randomChar() {
        int index = randObject.nextInt(0, CHARACTERS.length);
        return CHARACTERS[index];
    }
    public static String makeRandomWord(int length) {
        String w = "";
        for (int i=0; i<length; i++) {
            w = w.concat(String.valueOf(randomChar()));
        }
        return w;
    }
}