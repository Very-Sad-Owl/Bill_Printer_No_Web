package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.service.CashierService;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;

import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/")
public class Controller {

    private final MessageSource messageSource;

    @Autowired
    public Controller(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public String welcome(Locale loc) {
        return JsonMapper.parseObject(
                messageSource.getMessage("label.guide",
                        null, loc));
    }

}
