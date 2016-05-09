/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import CandleData.CandleData;
import CandleData.Ticker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for providing abstraction for user interaction.
 *
 * @author Vadim Mishutov
 */
public class CandleConsoleMethods {

    /**
     * Method for comparing file extension and serves as an entry point for
     * user interaction by combining all other helper methods.
     *
     * @throws java.io.IOException
     * @throws StringIndexOutOfBoundsException
     */
    public static void start() throws IOException, StringIndexOutOfBoundsException {
        int flag;
            System.out.println("Enter file name with extension (.csv , .txt).");
            String filename = readString().trim();
            int index = filename.lastIndexOf(".");
            switch (filename.substring(index, filename.length())) {
                case ".csv":
                case ".txt":
                    CandleData data = new CandleData(filename);
                    chooseTimeFrame(data);
                    flag = 1;
                    break;
                default:
                 throw new IOException();
            }
    }

    /**
     * Method for parsing files using the given time frame.
     *
     * @param data CandleData object for providing methods.
     * @throws java.io.IOException
     */
    private static void chooseTimeFrame(final CandleData data) throws IOException {
        int flag;
        do {
            System.out.println("One or five minute time frame? Enter 1 or 5.");
            String frame = readString().trim();
            switch (frame) {
                case "1":
                    System.out.println("Parsing...");
                    data.createOneOrFive(Ticker::getRoundedTimeForOne);
                    System.out.println("Done");
                    parseFive(data);
                    flag = 1;
                    break;
                case "5":
                    System.out.println("Parsing...");
                    data.createOneOrFive(Ticker::getRoundedTimeForFive);
                    System.out.println("Done.");
                    flag = 1;
                    break;
                default:
                    System.out.println("Wrong time frame.");
                    flag = 0;
                    break;
            }
        } while (flag == 0);
    }

    /**
     * Method for converting one minute time frame file into a five minute one.
     *
     * @param data CandleData object for providing methods.
     * @throws java.io.IOException
     */
    private static void parseFive(final CandleData data) throws IOException {
        int flag;
        do {
            System.out.println("Create a 5 minute time frame? Enter yes or no.");
            String answer = readString().trim();
            switch (answer.toLowerCase()) {
                case "yes":
                    System.out.println("Enter output file name.");
                    String output = readString();
                    System.out.println("Parsing...");
                    data.createFiveFromOne(output);
                    System.out.println("Done.");
                    flag = 1;
                    break;
                case "no":
                    return;
                default:
                    System.out.println("Invalid input.");
                    flag = 0;
                    break;
            }
        } while (flag == 0);
    }

    /**
     * Method in charge of storing user text input.
     *
     * @return User input contained in a String.s
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
}
