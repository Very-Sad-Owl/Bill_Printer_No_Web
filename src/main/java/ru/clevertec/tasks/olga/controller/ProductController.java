package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.dto.ProductParamsDto;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.exception.serviceexc.ServiceException;
import ru.clevertec.tasks.olga.exception.statusdefier.HandledGeneralException;
import ru.clevertec.tasks.olga.service.CashierService;
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
            List<Product> products = productService.getAll(nodesPerPage, page);
            return JsonMapper.parseObject(products);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public String find(@RequestParam Integer id){
        try {
            Product product = productService.findById(id);
            return JsonMapper.parseObject(product);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody String json) {
        try {
            ProductParamsDto productParams = gson.fromJson(json, ProductParamsDto.class);
            Product product = productService.save(productParams);
            return JsonMapper.parseObject(product);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody String json){
        try {
            ProductParamsDto productParams = gson.fromJson(json, ProductParamsDto.class);
            Product product = productService.update(productParams);
            return JsonMapper.parseObject(product);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id){
        try {
            productService.delete(id);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }
}