package ru.clevertec.tasks.olga.util.printer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public abstract class AbstractPrinter {
    @Value( "${bill.line_len}" )
    public int MAX_SYMBOLS_PER_LINE;

    public abstract String print(List<String> content);
}
