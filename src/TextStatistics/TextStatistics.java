/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextStatistics;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Interface containing the necessary method signatures.
 *
 * @author Vadim Mishutov
 */
public interface TextStatistics {

    /**
     * Method that allows the user to re-split the text based on provided Regex.
     *
     * @param splitter Regex for splitting the text.
     */
    public void reSplit(final String splitter);

    /**
     * Method that returns an array containing all the tokens within a text.
     *
     * @return String array with all the tokens inside.
     */
    public String[] allTokens();

    /**
     * Method that returns an array containing all the sorted tokens within a
     * text.
     *
     * @return Sorted String array with all the tokens inside.
     */
    public String[] allTokensSorted();

    /**
     * Method that is in charge of counting all the tokens within a text.
     *
     * @return Value representing the amount of tokens in a text.
     */
    public long tokensCount();

    /**
     * Method that is in charge of counting all the unique tokens within a text.
     *
     * @return Value representing the amount of unique tokens in a text.
     */
    public long uniqueTokensCount();

    /**
     * Method that is responsible for finding the amount of times every token
     * has occurred in a text.
     *
     * @return An array containing the token name and its occurrence.
     */
    public String[][] countsByTokens();

    /**
     * Method that is responsible for finding the exact points in the document
     * where each word has appeared.
     *
     * @return Map containing the token name along with all of its positions in
     * a document.
     */
    public Map<String, String> tokenPos();

    /**
     * Method in charge of finding the Term Frequency of a specified token.
     *
     * @param name Name of a token.
     * @param digits Number of digits after comma. Used when calculating the
     * Term Frequency.
     * @return A number describing the Term Frequency of a token.
     */
    public BigDecimal tokenTF(final String name,final int digits);

    /**
     * Method in charge of finding the Term Frequency of all the tokens in a
     * document.
     *
     * @param digits Number of digits after comma. Used when calculating the
     * Term Frequency.
     * @return A number describing the Term Frequency of all tokens.
     */
    public Map<String, BigDecimal> everyTF(final int digits);
}
