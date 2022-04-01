package ru.clevertec.tasks.olga.util.jsonmapper;

import com.google.gson.*;
import lombok.Getter;
import ru.clevertec.tasks.olga.service.*;
import ru.clevertec.tasks.olga.service.impl.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class GsonFactory {

    private GsonFactory(){}

    private static final GsonFactory instance = new GsonFactory();

    private final Gson gson = registerAdapters();

    public static GsonFactory getInstance(){
        return instance;
    }

    private static Gson registerAdapters() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
    }

    private static class LocalDateSerializer implements JsonSerializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        @Override
        public JsonElement serialize(LocalDate localDate, Type srcType, JsonSerializationContext context) {
            return new JsonPrimitive(formatter.format(localDate));
        }
    }
}
