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
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/cashiers")
public class CashierController {

    private final Gson gson;
    private final MessageSource messageSource;
    private final CashierService cashierService;

    @Autowired
    public CashierController(Gson gson, MessageSource messageSource, CashierService cashierService) {
        this.gson = gson;
        this.messageSource = messageSource;
        this.cashierService = cashierService;
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
        List<Cashier> cashiers = cashierService.getAll(nodesPerPage, page);
        return gson.toJson(cashiers);
    }

    @GetMapping(value = "/find", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String find(@RequestParam Integer id) {
        Cashier cashier = cashierService.findById(id);
        return gson.toJson(cashier);
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody String json) {
        CashierParamsDTO cashierParams = gson.fromJson(json, CashierParamsDTO.class);
        Cashier cashier = cashierService.save(cashierParams);
        return gson.toJson(cashier);
    }

    @PostMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody String json) {
        CashierParamsDTO cashierParams = gson.fromJson(json, CashierParamsDTO.class);
        Cashier updatedCashier = cashierService.update(cashierParams);
        return gson.toJson(updatedCashier);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id) {
        cashierService.delete(id);
    }
}
