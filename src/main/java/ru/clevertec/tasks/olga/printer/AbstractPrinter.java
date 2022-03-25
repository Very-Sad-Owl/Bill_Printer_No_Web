package ru.clevertec.tasks.olga.printer;

import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractPrinter {
    public static int MAX_SYMBOLS_PER_LINE;

    static {
        try {
            MAX_SYMBOLS_PER_LINE = Integer.parseInt(
                    ResourceBundle.getBundle("db").getString("bill.line_len"));
        } catch (Exception e){
            MAX_SYMBOLS_PER_LINE = 90;
        }
    }

    public abstract void print(List<String> content);
}
