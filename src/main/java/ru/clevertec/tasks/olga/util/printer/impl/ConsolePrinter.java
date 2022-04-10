package ru.clevertec.tasks.olga.util.printer.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.util.printer.AbstractPrinter;

import java.util.List;
import java.util.Locale;

@Component
public class ConsolePrinter extends AbstractPrinter {

    MessageSource messageSource;
    @Value( "${bill.delimiter}" )
    private static char DELIMITER;
    @Value( "${bill.line_delimiter}")
    private static char LINE_DELIMITER;

    @Autowired
    public ConsolePrinter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String print(List<String> content) {
        StringBuilder res = new StringBuilder();
        for (String line : content){
          if (line.charAt(0) == DELIMITER){
              res.append(printMonocharLine(DELIMITER, MAX_SYMBOLS_PER_LINE));
          } else if (line.contains("%s")){
              int literals = StringUtils.countMatches(line, "%s");
              char[] varargs = new char[literals];
              for (int i = 0 ; i < literals ; i++){
                  varargs[i] = DELIMITER;
              }
              res.append(replaceFormatLiteral(line, varargs));
          } else if (line.charAt(0) == LINE_DELIMITER){
              res.append(printMonocharLine(LINE_DELIMITER, MAX_SYMBOLS_PER_LINE));
          }
        }
        return res.toString();
    }

    private String printMonocharLine(char ch, int len){
        return StringUtils.repeat(ch, len);
    }

    private String replaceFormatLiteral(String line, char...replacement){
        int literals = StringUtils.countMatches(line, "%s");
        int len = MAX_SYMBOLS_PER_LINE - line.length() - literals;
        String[] actualReplacement = new String[replacement.length];
        for (int i = 0; i < replacement.length; i++){
            actualReplacement[i] = StringUtils.repeat(replacement[i], len / replacement.length);
        }
        return String.format(line, actualReplacement);
    }
}
