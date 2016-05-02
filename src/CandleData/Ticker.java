/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CandleData;

import java.util.Locale;

/**
 * Class for storing all the necessary data needed for computing CandleSticks.
 *
 * @author Vadim Mishutov
 */
public class Ticker {

    private final String time;
    private final double open;
    private final double high;
    private final double low;
    private final double close;

    /**
     * @param time Time period of the current tick.
     * @param open Opening bid.
     * @param high Highest price.
     * @param low Lowest price.
     * @param close Closing bid.
     */
    Ticker(final String time, final double open, final double high, final double low, final double close) {
        this.time = time;
        this.open = open;
        this.low = low;
        this.high = high;
        this.close = close;
    }

    /**
     * @param time Time of the current tick.
     * @param open Opening bid.
     */
    Ticker(final String time, final double open) {
        this.time = time;
        this.open = open;
        this.low = 0.0;
        this.high = 0.0;
        this.close = 0.0;
    }

    /**
     * Opening bid getter method.
     *
     * @return Opening bid.
     */
    public double getOpen() {
        return this.open;
    }

    /**
     * Lowest price getter method.
     *
     * @return Lowest price.
     */
    public double getLow() {
        return this.low;
    }

    /**
     * Highest price getter method.
     *
     * @return Highest price.
     */
    public double getHigh() {
        return this.high;
    }

    /**
     * Closing bid getter method.
     *
     * @return Closing bid.
     */
    public double getClose() {
        return this.close;
    }

    /**
     * Time period getter method.
     *
     * @return Time period of a tick.
     */
    public String getTime() {
        return this.time;
    }

    /**
     * Helper method in charge of rounding the time period for one minute
     * CandleStricks.
     *
     * @return Rounded time period.
     */
    public String getRoundedTimeForOne() {
        return this.time.substring(0, 13);
    }

    /**
     * Helper method in charge of rounding the time period for five minute
     * CandleStricks.
     *
     * @return Rounded time period.
     */
    public String getRoundedTimeForFive() {
        String[] splitAr = this.time.split(" ");
        String hr = splitAr[1].substring(0, 2);
        byte min = Byte.parseByte(splitAr[1].substring(2, 4));
        byte newVal = (byte) ((min / 5) * 5);
        StringBuilder sb = new StringBuilder();
        sb.append(splitAr[0]);
        sb.append(" ");
        sb.append(hr);
        sb.append(String.format("%02d", newVal));
        return sb.toString();

    }

    /**
     * Helper method in charge of formatting price before outputting to file.
     *
     * @param price Price to format.
     * @return Formatted price.
     */
    public static String formattedPrice(final double price) {
        return String.format(Locale.US, "%-7f", price).replace(" ", "0");
    }
}
