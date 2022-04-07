package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.ProductParamsDto;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.service.ProductService;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final Gson gson;
    private final MessageSource messageSource;
    private final ProductService productService;

    @Autowired
    public ProductController(Gson gson, MessageSource messageSource, ProductService productService) {
        this.gson = gson;
        this.messageSource = messageSource;
        this.productService = productService;
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
        List<Product> products = productService.getAll(nodesPerPage, page);
        return gson.toJson(products);
    }

    @GetMapping(value = "/find", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String find(@RequestParam Integer id) {
        Product product = productService.findById(id);
        return gson.toJson(product);
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody String json) {
        ProductParamsDto productParams = gson.fromJson(json, ProductParamsDto.class);
        Product product = productService.save(productParams);
        return gson.toJson(product);
    }

    @PatchMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody String json) {
        ProductParamsDto productParams = gson.fromJson(json, ProductParamsDto.class);
        Product product = productService.update(productParams);
        return gson.toJson(product);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id) {
        productService.delete(id);
    }
}
