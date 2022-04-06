package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.CardParamsDTO;
import ru.clevertec.tasks.olga.dto.CardTypeDto;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.entity.DiscountCard;
import ru.clevertec.tasks.olga.exception.serviceexc.ServiceException;
import ru.clevertec.tasks.olga.exception.statusdefier.HandledGeneralException;
import ru.clevertec.tasks.olga.service.CardTypeService;
import ru.clevertec.tasks.olga.service.CashierService;
import ru.clevertec.tasks.olga.service.DiscountCardService;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/card_types")
public class CardController {

    private final Gson gson;
    private final MessageSource messageSource;
    private final DiscountCardService cardService;

    @Autowired
    public CardController(Gson gson, MessageSource messageSource, DiscountCardService cardService) {
        this.gson = gson;
        this.messageSource = messageSource;
        this.cardService = cardService;
    }

    @GetMapping
    public String welcome(Locale loc) {
        return JsonMapper.parseObject(
                messageSource.getMessage("label.guide",
                        null, loc));
    }

    @GetMapping("/log")
    @ResponseStatus(HttpStatus.OK)
    public String log(@RequestParam (required = false, defaultValue = "0") Integer nodesPerPage,
                      @RequestParam (required = false, defaultValue = "0") Integer page) {
        try {
            List<DiscountCard> cards = cardService.getAll(nodesPerPage, page);
            return JsonMapper.parseObject(cards);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public String find(@RequestParam Integer id){
        try {
            DiscountCard card = cardService.findById(id);
            return JsonMapper.parseObject(card);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody String json) {
        try {
            CardParamsDTO cardParamsDTO = gson.fromJson(json, CardParamsDTO.class);
            DiscountCard card = cardService.save(cardParamsDTO);
            return JsonMapper.parseObject(card);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody String json){
        try {
            CardParamsDTO cardParamsDTO = gson.fromJson(json, CardParamsDTO.class);
            DiscountCard card = cardService.update(cardParamsDTO);
            return JsonMapper.parseObject(card);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id){
        try {
            cardService.delete(id);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }
}
