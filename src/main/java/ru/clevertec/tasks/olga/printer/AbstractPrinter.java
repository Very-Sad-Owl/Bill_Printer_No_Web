package ru.clevertec.tasks.olga.printer;

import java.util.List;

public abstract class AbstractPrinter {
    public static final int MAX_SYMBOLS_PER_LINE = 48;

    public abstract void print(List<String> content);
}
