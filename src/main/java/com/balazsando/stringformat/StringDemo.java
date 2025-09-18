package com.balazsando.stringformat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.StringJoiner;

public class StringDemo {
    public static void main(String[] args) throws IOException {
//        joinDemo();
//        formatDemo();
        streamFormatDemo();
    }

    public static void joinDemo() {
//        StringJoiner joiner = new StringJoiner(", ");
        StringJoiner joiner = new StringJoiner("], [", "[", "]");
//        joiner.add("One").add("Two").add("Three");
        joiner.add("One");
        joiner.add("Two");
        joiner.add("Three");
        joiner.setEmptyValue("EMPTY");
        String result = joiner.toString();
        System.out.println(result);
    }

    //other format specifiers
    // %[argument_index$][flags][width][.precision]conversion
    // argument_index - position of the argument in the argument list (1-based)
    // flags - formatting options (e.g., left-justified, leading zeros)
    // width - minimum number of characters to be written to the output
    // .precision - for floating point numbers, number of digits after the decimal point; for
    //               strings, maximum number of characters to be written
    // conversion - type of data to be formatted
    // common conversions:
    //   d - decimal integer
    //   f - floating point number
    //   s - string
    //   c - character
    //   b - boolean
    //   e - scientific notation
    //   x - hexadecimal integer
    // common flags:
    //   - - left-justified
    //   0 - leading zeros
    //   # - alternate form (e.g., for hexadecimal, adds 0x prefix)
    //   , - includes locale-specific grouping separators (e.g., commas in the US)
    //   + - includes a plus sign for positive numbers
    //   (space) - includes a leading space for positive numbers
    //   - - negative numbers are enclosed in parentheses
    //   ( - negative numbers are enclosed in parentheses
    //     Note: flags can be combined, e.g., "%-+10.2f" means left-justified, always show sign
    // argument_index:
    //   not specified - arguments are formatted in the order they appear
    //   index$ - specifies the position of the argument in the argument list (1-based)
    //   < - reuses the previous argument
    //
    // eg:
    //   %-10s meaning left-justified, minimum width 10, string
    //   %10s meaning right-justified, minimum width 10, string
    //   %2$04d meaning the second argument, minimum width 4, leading zeros, decimal integer
    //   %.2f meaning floating point number with 2 digits after the decimal point
    public static void formatDemo() {
        int david = 17, dawson = 15, dillon = 8, gordon = 6;
        System.out.println(String.format("My nephews are %d, %d, %d and %d years old.", david, dawson, dillon, gordon));
        double avgDiff = ((david - dawson) + (dawson - dillon) + (dillon - gordon)) / 3.0;
        System.out.println(String.format("The average age between each is %.1f years", avgDiff));

        int iVal = 32;
        System.out.println(String.format("%d", iVal));
        System.out.println(String.format("%x", iVal));
        System.out.println(String.format("%#x", iVal));
        System.out.println(String.format("%#X", iVal));

        int w = 5, x = 235, y = 481, z = 12;
        System.out.println(String.format("W:%d X:%d", w, x));
        System.out.println(String.format("Y:%d Z:%d", y, z));
        System.out.println(String.format("W:%5d X:%5d", w, x));
        System.out.println(String.format("Y:%5d Z:%5d", y, z));
        System.out.println(String.format("W:%04d X:%04d", w, x));
        System.out.println(String.format("Y:%04d Z:%04d", y, z));
        System.out.println(String.format("W:%-4d X:%-4d", w, x));
        System.out.println(String.format("Y:%-4d Z:%-4d", y, z));

        iVal = 1234567;
        double dVal = 1234567.0d;
        System.out.println(String.format("%d", iVal));
        System.out.println(String.format("%,d", iVal));
        System.out.println(String.format("%,.2f", dVal));
//        System.out.println(String.format("%,+(20.2f", dVal));

        int iPos = 123, iNeg = -456;
        System.out.println(String.format("%d", iPos));
        System.out.println(String.format("%d", iNeg));
        System.out.println(String.format("% d", iPos));
        System.out.println(String.format("% d", iNeg));
        System.out.println(String.format("%+d", iPos));
        System.out.println(String.format("%+d", iNeg));
        System.out.println(String.format("%(d", iPos));
        System.out.println(String.format("%(d", iNeg));
        System.out.println(String.format("% (d", iPos));
        System.out.println(String.format("% (d", iNeg));

        int valA = 100, valB = 200, valC = 300;
        System.out.println(String.format("%d %d %d", valA, valB, valC));
        System.out.println(String.format("%2$d %1$d %3$d", valA, valB, valC));
        System.out.println(String.format("%2$d %<d %1$d", valA, valB));
    }

    public static void streamFormatDemo() throws IOException {
        int david = 17, dawson = 15, dillon = 8, gordon = 6;
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("myfile.txt"));
                Formatter f = new Formatter(writer)
        ) {
            f.format("My nephews are %d, %d, %d and %d years old.%n", david, dawson, dillon, gordon);
            double avgDiff = ((david - dawson) + (dawson - dillon) + (dillon - gordon)) / 3.0;
            f.format("The average age between each is %.1f years%n", avgDiff);
        }
    }

}
