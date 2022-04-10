package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.CardTypeDto;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.service.CardTypeService;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/card_types")
public class CardTypeController {

    private final MessageSource messageSource;
    private final CardTypeService cardTypeService;

    @Autowired
    public CardTypeController(MessageSource messageSource, CardTypeService cardTypeService) {
        this.messageSource = messageSource;
        this.cardTypeService = cardTypeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String welcome(Locale loc) {
        return messageSource.getMessage("label.guide", null, loc);
    }

    @GetMapping(value = "/log", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<CardType> log(@RequestParam(value = "nodes", required = false, defaultValue = "${pagination.page_size}") Integer nodesPerPage,
                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        PageRequest pageRequest = PageRequest.of(page, nodesPerPage);
        return cardTypeService.getAll(pageRequest);
    }

    @GetMapping(value = "/find", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public CardType find(@RequestParam Integer id) {
        return cardTypeService.findById(id);
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public CardType save(@RequestBody CardTypeDto params) {
        return cardTypeService.save(params);
    }

    @PatchMapping(value = "/patch", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public CardType patch(@RequestBody CardTypeDto params) {
        return cardTypeService.patch(params);
    }

    @PutMapping(value = "/put", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public CardType update(@RequestBody CardTypeDto params) {
        return cardTypeService.put(params);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id) {
        cardTypeService.delete(id);
    }
}
