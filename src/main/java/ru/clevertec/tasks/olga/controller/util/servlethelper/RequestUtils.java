package ru.clevertec.tasks.olga.controller.util.servlethelper;

import java.io.BufferedReader;
import java.io.Reader;

public class RequestUtils {

    public static String readBody(BufferedReader reader){
        return reader.lines()
                .reduce(" ", String::concat);
    }
}
