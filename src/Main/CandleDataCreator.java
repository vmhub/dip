///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package Main;

import Helpers.CandleConsoleMethods;
import java.io.IOException;

//
///**Main class needed in order to run the program.
// *
// * @author vadim Mishutov
// */
public class CandleDataCreator {

    public static void main(String[] args) {
        try {
            CandleConsoleMethods.start();
        } catch (IOException ex) {
            System.out.println("Invalid file.");
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("No file extension.");
        }
    }

}
