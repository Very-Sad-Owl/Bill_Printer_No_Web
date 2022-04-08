package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.CardParamsDTO;
import ru.clevertec.tasks.olga.entity.DiscountCard;
import ru.clevertec.tasks.olga.service.DiscountCardService;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/cards")
public class CardController {

    private final MessageSource messageSource;
    private final DiscountCardService cardService;

    @Autowired
    public CardController(MessageSource messageSource, DiscountCardService cardService) {
        this.messageSource = messageSource;
        this.cardService = cardService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String welcome(Locale loc) {
        return messageSource.getMessage("label.guide", null, loc);
    }

    @GetMapping(value = "/log", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<DiscountCard> log(@RequestParam(value = "nodes", required = false, defaultValue = "0") Integer nodesPerPage,
                      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return cardService.getAll(nodesPerPage, page);
    }

    @GetMapping(value = "/find", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public DiscountCard find(@RequestParam Integer id) {
        return cardService.findById(id);
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountCard save(@RequestBody CardParamsDTO params) {
        return cardService.save(params);
    }

    @PatchMapping(value = "/patch", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public DiscountCard patch(@RequestBody CardParamsDTO params) {
        return cardService.patch(params);
    }

    @PutMapping(value = "/put", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public DiscountCard update(@RequestBody CardParamsDTO params) {
        return cardService.put(params);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id) {
        cardService.delete(id);
    }
}
