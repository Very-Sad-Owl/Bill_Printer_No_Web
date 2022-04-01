package ru.clevertec.tasks.olga.controller.util.servlethelper;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;

public class ResponseUtils {
    public static void setJsonType(HttpServletResponse response){
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    public static void setPlainTextType(HttpServletResponse response){
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
    }

    public static void setPdfType(HttpServletResponse response, Path filename){
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + filename);
    }
}
