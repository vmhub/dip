/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import TextGetters.HtmlTextGetter;
import TextGetters.PdfTextGetter;
import TextStatistics.TextStatistics;
import TextStatistics.TextStatisticsPar;
import TextStatistics.TextStatisticsSeq;

/**
 * Class for providing abstraction for user interaction.
 *
 * @author Vadim Mishutov
 */
public class StatisticsConsoleMethods {
/** Method in charge of executing the function that was chosen by the user in the
 * menu() method.
 * 
 * @param stat TextStatistics object required to call the functions with.
 */
    private static void loop(TextStatistics stat) {
        for (;;) {
            int key = menu();
            switch (key) {
                case 1:
                    System.out.println(Arrays.toString(stat.allTokens()));
                    break;
                case 2:
                    System.out.println(Arrays.toString(stat.allTokensSorted()));
                    break;
                case 3:
                    System.out.println(stat.tokensCount());
                    break;
                case 4:
                    System.out.println(stat.uniqueTokensCount());
                    break;
                case 5:
                    System.out.println(Arrays.deepToString(stat.countsByTokens()));
                    break;
                case 6:
                    System.out.println(stat.tokenPos());
                    break;
                case 7:
                    System.out.print("Enter number of digits to show after comma.\n");
                    int digits = readInt();
                    System.out.print("Enter the word to get Term Frequency of.");
                    String word = "";
                    do {
                        word = readString().trim();
                    } while (word.isEmpty());
                    System.out.println(stat.tokenTF(word, digits));
                    break;
                case 8:
                    System.out.print("Enter number of digits to show after comma.");
                    digits = readInt();
                    System.out.print(stat.everyTF(digits));
                    break;
                case 9:
                    System.out.print("New Regex to re-split.");
                    String splitter = "\\s+";
                    do {
                        splitter = readString().trim();
                    } while (splitter.isEmpty());
                    stat.reSplit(splitter);
                    break;
                case 10:
                    return;
            }
        }
    }
    /**
     * Method in charge of providing a list of possible functions to a user each
     * of which has its own unique number.
     *
     * @return A number of a function that was chosen by the user.
     */
    private static int menu() {
        int key;
        final String[] menuItem = {"1 - Get all tokens.", "2 - Get all sorted tokens.",
            "3 - Get total amount of tokens.", "4 - Get total amount of unique tokens.", "5 - Get total amount of each token.",
            "6 - Get positions for every token.", "7 - Specific token Term Frequency.",
            "8 - Term Frequency of every token", "9 - Re-split the text using new Regex.", "10 - Exit program."};
        for (String str : menuItem) {
            System.out.println(str);
        }
        System.out.print(":");
        key = readInt();
        return key;
    }

    /**
     * Method in charge of storing user text input.
     *
     * @return User input contained in a String.
     */
    private static String readString() {
        String s = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            s = in.readLine();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return s;
    }
    /**
     * Method in charge of storing user number input.
     *
     * @return User given number contained in an int.
     */
    private static int readInt() {
        int n = 0;
        String s = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean flag;
        do {
            flag = true;
            try {
                s = in.readLine();
            } catch (IOException ex) {
                System.out.println("Error parsing string. " + ex.getMessage());
            }
            try {
                n = Integer.parseInt(s);
            } catch (NumberFormatException exc) {
                System.out.println("Cannot parse that.Enter again:");
                flag = false;
            }
        } while (!flag);
        return n;
    }
    /**
     * Method for comparing file extension and serves as an entry point for user
     * interaction by combining all other helper methods.Also in charge of creating either
     * TextStatisticsSeq or TextStatisticsPar objects depending on user's processing choice.
     * @throws java.io.IOException
     * @throws StringIndexOutOfBoundsException
     */
    public static void start() throws IOException, StringIndexOutOfBoundsException {
        HtmlTextGetter htmlParser;
        PdfTextGetter pdfParser;
        int flag;
        String text = null;
        do {
            System.out.println("Enter file name with path and extension.(.html / .pdf)");
            String filename = readString().trim();

            int index = filename.lastIndexOf(".");
            switch (filename.substring(index, filename.length())) {
                case ".html":
                    htmlParser = new HtmlTextGetter(filename);
                    System.out.println("Parsing...");
                    text = htmlParser.toText();
                    System.out.println("Done.");
                    flag = 1;
                    break;
                case ".pdf":
                    pdfParser = new PdfTextGetter(filename);
                    System.out.println("Parsing...");
                    text = pdfParser.toText();
                    System.out.println("Done.");
                    flag = 1;
                    break;
                default:
                    System.out.println("Cannot parse format.");
                    flag = 0;
                    break;
            }

        } while (flag == 0);
        do {
            System.out.println("Choose processing method: \n 1 - sequential \n 2 - parallel");
            int proc = readInt();
            switch (proc) {
                case 1:
                    loop(new TextStatisticsSeq(text));
                    flag = 1;
                    break;
                case 2:
                    loop(new TextStatisticsPar(text));
                    flag = 1;
                    break;
                default:
                    flag = 0;
            }
        } while (flag == 0);
    }
}
