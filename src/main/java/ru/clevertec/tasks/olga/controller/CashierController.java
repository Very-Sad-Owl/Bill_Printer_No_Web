package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.service.CashierService;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/cashiers")
public class CashierController {

    private final MessageSource messageSource;
    private final CashierService cashierService;

    @Autowired
    public CashierController(MessageSource messageSource, CashierService cashierService) {
        this.messageSource = messageSource;
        this.cashierService = cashierService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String welcome(Locale loc) {
        return messageSource.getMessage("label.guide", null, loc);
    }

    @GetMapping(value = "/log", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<Cashier> log(@RequestParam(value = "nodes", required = false, defaultValue = "0") Integer nodesPerPage,
                             @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return cashierService.getAll(nodesPerPage, page);
    }

    @GetMapping(value = "/find", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Cashier find(@RequestParam Integer id) {
        return cashierService.findById(id);
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Cashier save(@RequestBody CashierParamsDTO params) {
        return cashierService.save(params);
    }

    @PatchMapping(value = "/patch", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Cashier patch(@RequestBody CashierParamsDTO params) {
        return cashierService.patch(params);
    }

    @PutMapping(value = "/put", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Cashier update(@RequestBody CashierParamsDTO params) {
        return cashierService.put(params);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id) {
        cashierService.delete(id);
    }
}
