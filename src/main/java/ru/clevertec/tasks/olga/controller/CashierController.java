package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.service.CashierService;
import java.util.List;
import java.util.Locale;
import static ru.clevertec.tasks.olga.util.Constant.*;

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

    @GetMapping(value = ACTION_LOG, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<Cashier> log(@RequestParam(value = "nodes", required = false, defaultValue = "${pagination.page_size}") Integer nodesPerPage,
                             @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        PageRequest pageRequest = PageRequest.of(page, nodesPerPage);
        return cashierService.getAll(pageRequest);
    }

    @GetMapping(value = ACTION_FIND, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Cashier find(@RequestParam Integer id) {
        return cashierService.findById(id);
    }

    @PostMapping(value = ACTION_SAVE, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Cashier save(@RequestBody CashierParamsDTO params) {
        return cashierService.save(params);
    }

    @PatchMapping(value = ACTION_PATCH, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Cashier patch(@RequestBody CashierParamsDTO params) {
        return cashierService.patch(params);
    }

    @PutMapping(value = ACTION_PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Cashier update(@RequestBody CashierParamsDTO params) {
        return cashierService.put(params);
    }

    @DeleteMapping(ACTION_DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id) {
        cashierService.delete(id);
    }
}
