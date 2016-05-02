/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CandleData;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.reducing;
import java.util.stream.Stream;

/**
 * Main class in charge of manipulating data for creating CandleSticks and creating output
 * files containing that data.
 * @author Vadim Mishutov
 */
public class CandleData {

    private String inputfile;
    private String outputfile = "Output.csv";
/**
 * 
 * @param filename Document name along with the entire path.
 */
    public CandleData(final String filename) {
        this.inputfile = filename;
    }
/**
 * Method in charge of grouping values by a certain time frame ( 1 or 5 minutes) and finding the
 * maximum value associated with it.
 * @param func Function used when grouping Ticker objects.
 * @param mod 0 or 1 depending whether we want to create a five minute interval from a one minute one or not.
 * @param cmpf Function used to compare Ticker objects.In this case it's by max value.
 * @return Map containing the max values and a time frames associated with it.
 * @throws IOException 
 */
    private Map<String, Optional<Ticker>> getMaxVal(final Function<Ticker, String> func, final byte mod, final Function<Ticker, Double> cmpf) throws IOException {
        Stream<Ticker> streamx;
        Map<String, Optional<Ticker>> getMaxVal;
        try (Stream<Ticker> stream = streamx = (mod == 0 ? Files.lines(Paths.get(this.inputfile), Charset.defaultCharset()).parallel().map(i -> i.split(",")).map(i -> new Ticker(i[0], Double.parseDouble(i[1])))
                : Files.lines(Paths.get(this.inputfile), Charset.defaultCharset()).parallel().map(i -> i.split(",")).map(i -> new Ticker(i[0], Double.parseDouble(i[1]), Double.parseDouble(i[2]), Double.parseDouble(i[3]), Double.parseDouble(i[4]))
                ))) {
            Comparator<Ticker> cmp = Comparator.comparing(cmpf);
            getMaxVal = stream.parallel().collect(Collectors.groupingByConcurrent(func, reducing(BinaryOperator.maxBy(cmp))));
        }
        return getMaxVal;
    }
/**
 * 
 * Method in charge of grouping values by a certain time frame ( 1 or 5 minutes) and finding the
 * minimum value associated with it.
 * @param func Function used when grouping Ticker objects.
 * @param mod 0 or 1 depending whether we want to create a five minute interval from a one minute one or not.
 * @param cmpf Function used to compare Ticker objects.In this case it's by min value.
 * @return Map containing the min values and a time frames associated with it.
 * @throws IOException 
 */
    private Map<String, Optional<Ticker>> getMinVal(final Function<Ticker, String> func, final byte mod, final Function<Ticker, Double> cmpf) throws IOException {
        Stream<Ticker> streamx;
        Map<String, Optional<Ticker>> getMinVal;
        try (Stream<Ticker> stream = streamx = (mod == 0 ? Files.lines(Paths.get(this.inputfile), Charset.defaultCharset()).parallel().map(i -> i.split(",")).map(i -> new Ticker(i[0], Double.parseDouble(i[1])))
                : Files.lines(Paths.get(this.inputfile), Charset.defaultCharset()).parallel().map(i -> i.split(",")).map(i -> new Ticker(i[0], Double.parseDouble(i[1]), Double.parseDouble(i[2]), Double.parseDouble(i[3]), Double.parseDouble(i[4]))
                ))) {
            Comparator<Ticker> cmp = Comparator.comparing(cmpf);
            getMinVal = stream.parallel().collect(Collectors.groupingByConcurrent(func, reducing(BinaryOperator.minBy(cmp))));
        }
        return getMinVal;
    }
/**
 * Method in charge of grouping values by a certain time frame ( 1 or 5 minutes) and finding the
 * closing bid associated with it.
 * @param func Function used when grouping Ticker objects.
 * @param mod 0 or 1 depending whether we want to create a five minute interval from a one minute one or not.
 * @param cmpf Function used to compare Ticker objects.In this case it's by time frame.
 * @return Map containing the closing bids and a time frames associated with it in a sorted fashion.
 * @throws IOException 
 */
    private Map<String, Optional<Ticker>> getEnd(final Function<Ticker, String> func, final byte mod, final Function<Ticker, String> cmpf) throws IOException {
        Stream<Ticker> streamx;
        Map<String, Optional<Ticker>> sortedValMap;
        try (Stream<Ticker> stream = streamx = (mod == 0 ? Files.lines(Paths.get(this.inputfile), Charset.defaultCharset()).parallel().map(i -> i.split(",")).map(i -> new Ticker(i[0], Double.parseDouble(i[1])))
                : Files.lines(Paths.get(this.inputfile), Charset.defaultCharset()).parallel().map(i -> i.split(",")).map(i -> new Ticker(i[0], Double.parseDouble(i[1]), Double.parseDouble(i[2]), Double.parseDouble(i[3]), Double.parseDouble(i[4]))))) {
            Comparator<Ticker> cmp = Comparator.comparing(cmpf);
            Map<String, Optional<Ticker>> unsortedValMap = stream.parallel().collect(Collectors.groupingByConcurrent(func, reducing(BinaryOperator.maxBy(cmp))));
            sortedValMap = new TreeMap<>();
            sortedValMap.putAll(unsortedValMap);

        }
        return sortedValMap;
    }
/**
 * Method in charge of grouping values by a certain time frame ( 1 or 5 minutes) and finding the
 * opening bid associated with it.
 * @param func Function used when grouping Ticker objects.
 * @param mod 0 or 1 depending whether we want to create a five minute interval from a one minute one or not.
 * @param cmpf Function used to compare Ticker objects.In this case it's by time frame.
 * @return Map containing the opening bids and a time frames associated with it.
 * @throws IOException 
 */
    private Map<String, Optional<Ticker>> getStart(final Function<Ticker, String> func, final byte mod, final Function<Ticker, String> cmpf) throws IOException {
        Stream<Ticker> streamx;
        Map<String, Optional<Ticker>> getMinStart;
        try (Stream<Ticker> stream = streamx = (mod == 0 ? Files.lines(Paths.get(this.inputfile), Charset.defaultCharset()).parallel().map(i -> i.split(",")).map(i -> new Ticker(i[0], Double.parseDouble(i[1])))
                : Files.lines(Paths.get(this.inputfile), Charset.defaultCharset()).parallel().map(i -> i.split(",")).map(i -> new Ticker(i[0], Double.parseDouble(i[1]), Double.parseDouble(i[2]), Double.parseDouble(i[3]), Double.parseDouble(i[4]))))) {
            Comparator<Ticker> cmp = Comparator.comparing(cmpf);
            getMinStart = stream.parallel().collect(Collectors.groupingByConcurrent(func, reducing(BinaryOperator.minBy(cmp))));
        }
        return getMinStart;
    }
/**
 * Method in charge of creating a file containing all the necessary data to create one or five minute
 * Candlesticks based on milliseconds.
 * @param getStart Map containing the opening bids and corresponding time frames.
 * @param getMaxVal Map containing the max values and corresponding time frames.
 * @param getMinVal Map containing the min values and corresponding time frames.
 * @param getEnd Map containing the closing bids and corresponding time frames.
 * @throws IOException 
 */
    private void printCsvOneOrFive(final Map<String, Optional<Ticker>> getStart, final Map<String, Optional<Ticker>> getMaxVal, final Map<String, Optional<Ticker>>getMinVal, final Map<String, Optional<Ticker>> getEnd) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.outputfile), "utf-8"))) {
            getEnd.entrySet().stream().map((j) -> j.getKey()).forEach((key) -> {
                IOwraperForOneOrFive(writer, key, getStart, getMaxVal, getMinVal, getEnd);
            });
        }

    }
/** 
 * Method in charge of creating a file containing all the necessary data to create five minute
 * Candlesticks based on one minute ticks created by printCsvOneOrFive.
 * @param getStart Map containing the opening bids and corresponding time frames.
 * @param getMaxVal Map containing the max values and corresponding time frames.
 * @param getMinVal Map containing the min values and corresponding time frames.
 * @param getEnd Map containing the closing bids and corresponding time frames.
 * @throws IOException 
 */
    private void printCsvFiveFromOne(final Map<String, Optional<Ticker>> getStart, final Map<String, Optional<Ticker>> getMaxVal, final Map<String, Optional<Ticker>> getMinVal, final Map<String, Optional<Ticker>> getEnd) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.outputfile), "utf-8"))) {
            getEnd.entrySet().stream().map((j) -> j.getKey()).forEach((key) -> {
                IOwraperForFive(writer, key, getStart, getMaxVal, getMinVal, getEnd);
            });
        }
    }
/**
 * Simple method that encompasses getStart, getMaxVal, getMinVal, getEnd and printCsvOneOrFive
 * to form a document containing all the necessary data to create one or five minute
 * Candlesticks based on milliseconds.
 * @param func Function used when grouping Ticker objects.
 * @throws IOException 
 */
    public void createOneOrFive(final Function<Ticker, String> func) throws IOException {
        Map<String, Optional<Ticker>> map1 = getStart(func, (byte) 0, Ticker::getTime);
        Map<String, Optional<Ticker>> map2 = getMaxVal(func, (byte) 0, Ticker::getOpen);
        Map<String, Optional<Ticker>> map3 = getMinVal(func, (byte) 0, Ticker::getOpen);
        Map<String, Optional<Ticker>> map4 = getEnd(func, (byte) 0, Ticker::getTime);
        printCsvOneOrFive(map1, map2, map3, map4);
    }
/**
 * Simple method that encompasses setFiles, getStart, getMaxVal, getMinVal, getEnd and printCsvFiveFromOne
 * to form a document containing all the necessary data to create five minute
 * Candlesticks based on one minute ticks created by printCsvOneOrFive.
 * @param out Name of the new output document containing all the data.
 * @throws IOException 
 */
    public void createFiveFromOne(final String out) throws IOException {
        setFiles(out);
        Map<String, Optional<Ticker>> map1 = getStart(Ticker::getRoundedTimeForFive, (byte) 1, Ticker::getTime);
        Map<String, Optional<Ticker>> map2 = getMaxVal(Ticker::getRoundedTimeForFive, (byte) 1, Ticker::getHigh);
        Map<String, Optional<Ticker>> map3 = getMinVal(Ticker::getRoundedTimeForFive, (byte) 1, Ticker::getLow);
        Map<String, Optional<Ticker>> map4 = getEnd(Ticker::getRoundedTimeForFive, (byte) 1, Ticker::getTime);
        printCsvFiveFromOne(map1, map2, map3, map4);
    }
/**
 * Helper method in charge of swapping input file name with output file name and giving a new name
 * to the output file. Used when creating five minute ticks from one minute ticks created by printCsvOneOrFive.
 * @param out Name of the new output document containing all the data.
 */
    private void setFiles(final String out) {
        this.inputfile = this.outputfile;
        this.outputfile = out + ".csv";
    }
/**
 * A wrapper method in charge of forming a final string and writing it out an output file.
 * Used by printCsvFiveFromOne method.
 * @param writer Writer object to write a string to an output file.
 * @param key A key by which to obtain values from maps.
 * @param getStart Map generated from getStart method.
 * @param getMaxVal Map generated from getMaxVal method.
 * @param getMinVal Map generated from getMinVal method.
 * @param getEnd Map generated from getEnd method.
 */
    private void IOwraperForFive(final Writer writer, final String key, final Map<String, Optional<Ticker>> getStart, final Map<String, Optional<Ticker>> getMaxVal, final Map<String, Optional<Ticker>> getMinVal, final Map<String, Optional<Ticker>> getEnd) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(key);
            sb.append(",");
            sb.append(Ticker.formattedPrice(getStart.get(key).get().getOpen()));
            sb.append(",");
            sb.append(Ticker.formattedPrice(getMaxVal.get(key).get().getHigh()));
            sb.append(",");
            sb.append(Ticker.formattedPrice(getMinVal.get(key).get().getLow()));
            sb.append(",");
            sb.append(Ticker.formattedPrice(getEnd.get(key).get().getClose()));
            sb.append(System.lineSeparator());
            writer.write(sb.toString());
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
/**
 * A wrapper method in charge of forming a final string and writing it out an output file.
 * Used by printCsvOneOrFive method.
 * @param writer Writer object to write a string to an output file.
 * @param key A key by which to obtain values from maps.
 * @param getStart Map generated from getStart method.
 * @param getMaxVal Map generated from getMaxVal method.
 * @param getMinVal Map generated from getMinVal method.
 * @param getEnd Map generated from getEnd method.
 */
    private void IOwraperForOneOrFive(final Writer writer, final String key, final Map<String, Optional<Ticker>> getStart, final Map<String, Optional<Ticker>> getMaxVal, final Map<String, Optional<Ticker>> getMinVal, final Map<String, Optional<Ticker>> getEnd) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(key);
            sb.append(",");
            sb.append(Ticker.formattedPrice(getStart.get(key).get().getOpen()));
            sb.append(",");
            sb.append(Ticker.formattedPrice(getMaxVal.get(key).get().getOpen()));
            sb.append(",");
            sb.append(Ticker.formattedPrice(getMinVal.get(key).get().getOpen()));
            sb.append(",");
            sb.append(Ticker.formattedPrice(getEnd.get(key).get().getOpen()));
            sb.append(System.lineSeparator());
            writer.write(sb.toString());
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
