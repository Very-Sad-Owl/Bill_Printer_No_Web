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

    private final Gson gson;
    private final MessageSource messageSource;
    private final ProductDiscountService productDiscountService;

    @Autowired
    public ProductDiscountController(Gson gson, MessageSource messageSource, CashierService cashierService, ProductDiscountService productDiscountService) {
        this.gson = gson;
        this.messageSource = messageSource;
        this.productDiscountService = productDiscountService;
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
        List<ProductDiscountType> productDiscountTypes = productDiscountService.getAll(nodesPerPage, page);
        return gson.toJson(productDiscountTypes);
    }

    @GetMapping(value = "/find", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String find(@RequestParam Integer id) {
        ProductDiscountType discountType = productDiscountService.findById(id);
        return gson.toJson(discountType);
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody String json) {
        ProductDiscountDTO paramsDTO = gson.fromJson(json, ProductDiscountDTO.class);
        ProductDiscountType discountType = productDiscountService.save(paramsDTO);
        return gson.toJson(discountType);
    }

    @PatchMapping(value = "/patch", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String patch(@RequestBody String json) {
        ProductDiscountDTO paramsDTO = gson.fromJson(json, ProductDiscountDTO.class);
        ProductDiscountType discountType = productDiscountService.patch(paramsDTO);
        return gson.toJson(discountType);
    }

    @PutMapping(value = "/put", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody String json) {
        ProductDiscountDTO paramsDTO = gson.fromJson(json, ProductDiscountDTO.class);
        ProductDiscountType discountType = productDiscountService.put(paramsDTO);
        return gson.toJson(discountType);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id) {
        productDiscountService.delete(id);
    }
}
