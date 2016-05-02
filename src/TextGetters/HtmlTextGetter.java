/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextGetters;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Main class in charge of extracting text from .html files.
 * @author Vadim Mishutov
 */
public class HtmlTextGetter {

    private final String fileName;

    /**
     *
     * @param fileName Document name along with the entire path.
     */
    public HtmlTextGetter(String fileName) {
        this.fileName = fileName;
    }
/**
    * @return Entire document text contained in a string.
     * @throws java.io.IOException
 */
    public String toText() throws IOException {
        File input = new File(fileName);
        String text;
        Document doc = Jsoup.parse(input, "UTF-8");
        text = doc.text();
        return text;
    }
}
