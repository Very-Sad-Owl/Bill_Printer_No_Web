package ru.clevertec.tasks.olga.util.printer.impl;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.util.printer.AbstractPrinter;
import ru.clevertec.tasks.olga.util.resourceprovider.MessageLocaleService;

import java.util.List;

@Component
public class ConsolePrinter extends AbstractPrinter {
    private final char delimiter = MessageLocaleService
            .getMessage("label.pseudographics_delimiter").charAt(0);
    private final char lineDelimiter = MessageLocaleService
            .getMessage("label.pseudographics_char").charAt(0);

    @Override
    public String print(List<String> content) {
        StringBuilder res = new StringBuilder();
        for (String line : content){
          if (line.charAt(0) == delimiter){
              res.append(printMonocharLine(delimiter, MAX_SYMBOLS_PER_LINE));
          } else if (line.contains("%s")){
              int literals = StringUtils.countMatches(line, "%s");
              char[] varargs = new char[literals];
              for (int i = 0 ; i < literals ; i++){
                  varargs[i] = delimiter;
              }
              res.append(replaceFormatLiteral(line, varargs));
          } else if (line.charAt(0) == lineDelimiter){
              res.append(printMonocharLine(lineDelimiter, MAX_SYMBOLS_PER_LINE));
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
