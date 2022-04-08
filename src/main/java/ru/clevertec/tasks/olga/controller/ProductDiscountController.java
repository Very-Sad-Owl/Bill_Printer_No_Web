package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.ProductDiscountDTO;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.service.CashierService;
import ru.clevertec.tasks.olga.service.ProductDiscountService;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/product_discounts")
public class ProductDiscountController {

    private final MessageSource messageSource;
    private final ProductDiscountService productDiscountService;

    @Autowired
    public ProductDiscountController(MessageSource messageSource, ProductDiscountService productDiscountService) {
        this.messageSource = messageSource;
        this.productDiscountService = productDiscountService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String welcome(Locale loc) {
        return messageSource.getMessage("label.guide", null, loc);
    }

    @GetMapping(value = "/log", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDiscountType> log(@RequestParam(value = "nodes", required = false, defaultValue = "0") Integer nodesPerPage,
                                         @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return productDiscountService.getAll(nodesPerPage, page);
    }

    @GetMapping(value = "/find", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ProductDiscountType find(@RequestParam Integer id) {
        return productDiscountService.findById(id);
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDiscountType save(@RequestBody ProductDiscountDTO params) {
        return productDiscountService.save(params);
    }

    @PatchMapping(value = "/patch", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ProductDiscountType patch(@RequestBody ProductDiscountDTO params) {
        return productDiscountService.patch(params);
    }

    @PutMapping(value = "/put", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ProductDiscountType update(@RequestBody ProductDiscountDTO params) {
        return productDiscountService.put(params);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id) {
        productDiscountService.delete(id);
    }
}
