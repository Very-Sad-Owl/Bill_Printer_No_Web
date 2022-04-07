package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.CardTypeDto;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.service.CardTypeService;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/card_types")
public class CardTypeController {

    private final Gson gson;
    private final MessageSource messageSource;
    private final CardTypeService cardTypeService;

    @Autowired
    public CardTypeController(Gson gson, MessageSource messageSource, CardTypeService cardTypeService) {
        this.gson = gson;
        this.messageSource = messageSource;
        this.cardTypeService = cardTypeService;
    }

    @GetMapping(produces = {"application/json"})
    public String welcome(Locale loc) {
        return gson.toJson(
                messageSource.getMessage("label.guide",
                        null, loc));
    }

    @GetMapping(value = "/log", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String log(@RequestParam(value = "nodes", required = false, defaultValue = "0") Integer nodesPerPage,
                      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        List<CardType> cashiers = cardTypeService.getAll(nodesPerPage, page);
        return gson.toJson(cashiers);
    }

    @GetMapping(value = "/find", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String find(@RequestParam Integer id) {
        CardType cardType = cardTypeService.findById(id);
        return gson.toJson(cardType);
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody String json) {
        CardTypeDto cardTypeDto = gson.fromJson(json, CardTypeDto.class);
        CardType cardType = cardTypeService.save(cardTypeDto);
        return gson.toJson(cardType);
    }

    @PatchMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody String json) {
        CardTypeDto cardTypeDto = gson.fromJson(json, CardTypeDto.class);
        CardType cardType = cardTypeService.update(cardTypeDto);
        return gson.toJson(cardType);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id) {
        cardTypeService.delete(id);
    }
}
