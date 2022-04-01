package ru.clevertec.tasks.olga.util.printer;

import ru.clevertec.tasks.olga.util.resourceprovider.AppPropertiesService;

import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractPrinter {
    public static int MAX_SYMBOLS_PER_LINE;

    static {
        try {
            MAX_SYMBOLS_PER_LINE = Integer.parseInt(
                    AppPropertiesService.getMessage("bill.line_len"));
        } catch (Exception e){
            MAX_SYMBOLS_PER_LINE = 90;
        }
    }

    public abstract String print(List<String> content);
}
