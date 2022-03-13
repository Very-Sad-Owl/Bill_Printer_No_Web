package ru.clevertec.tasks.olga.printer.impl;

import lombok.NoArgsConstructor;
import ru.clevertec.tasks.olga.printer.AbstractPrinter;

import java.util.List;

@NoArgsConstructor
public class ConsolePrinter extends AbstractPrinter {
    @Override
    public void print(List<String> content) {
        for (String line : content){
            if (line.length() > MAX_SYMBOLS_PER_LINE){
                System.out.println(line.substring(0, MAX_SYMBOLS_PER_LINE));
                System.out.println(line.substring(MAX_SYMBOLS_PER_LINE,
                        line.length() - 1));
            } else {
                System.out.println(line);
            }
        }
    }
}
