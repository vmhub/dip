/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextGetters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * Main class in charge of extracting text from .pdf files.
 *
 * @author Vadim Mishutov
 */
public class PdfTextGetter {

    private final String fileName;
     /**
     *
     * @param fileName  Document name along with the entire path.
     */
    public PdfTextGetter(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * Main method that handles the extraction process.
     *
     * @return Entire document text contained in a string.
     * @throws java.io.IOException
     */
    public String toText() throws IOException {
        PDFParser parser;
        String parsedText;
        File file = new File(fileName);
        if (!file.exists() || !file.isFile()) {
            System.err.println("error with " + fileName);
            return null;
        }
        parser = new PDFParser(new FileInputStream(file));
        parser.parse();
        try (PDDocument doc = parser.getPDDocument();) {
            parsedText = new PDFTextStripper().getText(doc);
        }
        return parsedText;
    }
}
