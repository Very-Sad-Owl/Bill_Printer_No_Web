package ru.clevertec.tasks.olga.printer.impl;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.clevertec.tasks.olga.printer.AbstractPrinter;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.List;

@NoArgsConstructor
public class ConsolePrinter extends AbstractPrinter {
    private final char delimiter = MessageLocaleService
            .getMessage("label.pseudographics_delimiter").charAt(0);
    private final char lineDelimiter = MessageLocaleService
            .getMessage("label.pseudographics_char").charAt(0);

    @Override
    public void print(List<String> content) {
        for (String line : content){
          if (line.charAt(0) == delimiter){
              printMonocharLine(delimiter, MAX_SYMBOLS_PER_LINE);
          } else if (line.contains("%s")){
              int literals = StringUtils.countMatches(line, "%s");
              char[] varargs = new char[literals];
              for (int i = 0 ; i < literals ; i++){
                  varargs[i] = delimiter;
              }
              replaceFormatLiteral(line, varargs);
          } else if (line.charAt(0) == lineDelimiter){
              printMonocharLine(lineDelimiter, MAX_SYMBOLS_PER_LINE);
          }
        }
    }

    private void printMonocharLine(char ch, int len){
        System.out.println(StringUtils.repeat(ch, len));
    }

    private void replaceFormatLiteral(String line, char...replacement){
        int literals = StringUtils.countMatches(line, "%s");
        int len = MAX_SYMBOLS_PER_LINE - line.length() - literals;
        String[] actualReplacement = new String[replacement.length];
        for (int i = 0; i < replacement.length; i++){
            actualReplacement[i] = StringUtils.repeat(replacement[i], len / replacement.length);
        }
        System.out.println(String.format(line, actualReplacement));
    }
}
