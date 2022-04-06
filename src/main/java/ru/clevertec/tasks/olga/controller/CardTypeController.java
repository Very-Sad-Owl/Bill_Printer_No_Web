package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.CardTypeDto;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.exception.serviceexc.ServiceException;
import ru.clevertec.tasks.olga.exception.statusdefier.HandledGeneralException;
import ru.clevertec.tasks.olga.service.CardTypeService;
import ru.clevertec.tasks.olga.service.CashierService;
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

    @GetMapping
    public String welcome(Locale loc) {
        return JsonMapper.parseObject(
                messageSource.getMessage("label.guide",
                        null, loc));
    }

    @GetMapping("/log")
    @ResponseStatus(HttpStatus.OK)
    public String log(@RequestParam(required = false, defaultValue = "0") Integer nodesPerPage,
                      @RequestParam(required = false, defaultValue = "0") Integer page) {
        List<CardType> cashiers = cardTypeService.getAll(nodesPerPage, page);
        return JsonMapper.parseObject(cashiers);
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public String find(@RequestParam Integer id) {
        CardType cardType = cardTypeService.findById(id);
        return JsonMapper.parseObject(cardType);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody String json) {
        CardTypeDto cardTypeDto = gson.fromJson(json, CardTypeDto.class);
        CardType cardType = cardTypeService.save(cardTypeDto);
        return JsonMapper.parseObject(cardType);
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody String json) {
        CardTypeDto cardTypeDto = gson.fromJson(json, CardTypeDto.class);
        CardType cardType = cardTypeService.update(cardTypeDto);
        return JsonMapper.parseObject(cardType);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id) {
        cardTypeService.delete(id);
    }
}
