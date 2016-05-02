/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextStatistics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author SevenThree
 */
public class TextStatisticsPar implements TextStatistics {

    private final String text;
    private long wordCount = -1;
    private String[] splitArray;

    /**
     *
     * @param text Text of a document stored in a String.
     */
    public TextStatisticsPar(String text) {
        this.text = text.toLowerCase();
        splitArray = Pattern.compile("\\s+").splitAsStream(this.text).parallel().filter(i -> i.trim().isEmpty() == false).toArray(String[]::new);
    }

    /**
     *
     * @param splitter Custom Regex for text splitting.
     * @param text Text of a document stored in a String.
     */
    public TextStatisticsPar(String splitter, String text) {
        this.text = text.toLowerCase();
        splitArray = Pattern.compile(splitter).splitAsStream(this.text).parallel().filter(i -> i.trim().isEmpty() == false).toArray(String[]::new);
    }

    @Override
    public void reSplit(String splitter) {
        splitArray = Pattern.compile(splitter).splitAsStream(this.text).parallel().filter(i -> i.trim().isEmpty() == false).toArray(String[]::new);
        wordCount = -1;
    }

    @Override
    public String[] allTokens() {
        return splitArray;
    }

    @Override
    public String[] allTokensSorted() {
        return Arrays.stream(splitArray).parallel().sorted().toArray(String[]::new);
    }

    @Override
    public long uniqueTokensCount() {
        long count = Arrays.stream(splitArray).unordered().parallel().distinct().count();
        return count;
    }

    @Override
    public long tokensCount() {
        long count = Arrays.stream(splitArray).unordered().parallel().count();
        wordCount = count;
        return count;

    }

    @Override
    public String[][] countsByTokens() {
        String[][] countByTokenArray = Arrays.stream(splitArray).unordered().parallel().collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.reducing(
                0,
                o -> 1,
                Integer::sum))).entrySet().stream().unordered().parallel().map((i) -> new String[]{i.getKey(), i.getValue().toString()}).toArray(String[][]::new);
        return countByTokenArray;
    }

    @Override
    public Map<String, String> tokenPos() {
        return IntStream.range(0, splitArray.length).unordered().parallel().mapToObj(i -> Integer.toString(++i))
                .collect(Collectors.groupingByConcurrent(i -> splitArray[Integer.parseInt(i) - 1], Collectors.joining(",")));
    }

    @Override
    public BigDecimal tokenTF(String name, int digits) {
        if (wordCount == -1) {
            wordCount = tokensCount();
        }
        long tokenCount = Arrays.stream(splitArray).unordered().parallel().filter(i -> i.equals(name)).count();
        return BigDecimal.valueOf(tokenCount).divide(BigDecimal.valueOf(wordCount), digits, RoundingMode.HALF_UP);
    }

    @Override
    public Map<String, BigDecimal> everyTF(int digits) {
        if (wordCount == -1) {
            wordCount = tokensCount();
        }
        Map<String, BigDecimal> TfMap = Arrays.stream(splitArray).unordered().parallel().collect(Collectors.groupingByConcurrent(Function.identity(),
                Collectors.collectingAndThen(Collectors.reducing(new BigDecimal(0.0), e -> new BigDecimal(1.0), (a, b) -> (a.add(b))), i -> i.divide(BigDecimal.valueOf(wordCount), digits, RoundingMode.HALF_UP))));
        return TfMap;
    }
}
