package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.dto.ProductDiscountDTO;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.exception.serviceexc.ServiceException;
import ru.clevertec.tasks.olga.exception.statusdefier.HandledGeneralException;
import ru.clevertec.tasks.olga.service.CashierService;
import ru.clevertec.tasks.olga.service.ProductDiscountService;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;

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
            List<ProductDiscountType> productDiscountTypes = productDiscountService.getAll(nodesPerPage, page);
            return JsonMapper.parseObject(productDiscountTypes);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public String find(@RequestParam Integer id){
        try {
            ProductDiscountType discountType = productDiscountService.findById(id);
            return JsonMapper.parseObject(discountType);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody String json) {
        try {
            ProductDiscountDTO paramsDTO = gson.fromJson(json, ProductDiscountDTO.class);
            ProductDiscountType discountType = productDiscountService.save(paramsDTO);
            return JsonMapper.parseObject(discountType);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody String json){
        try {
            ProductDiscountDTO paramsDTO = gson.fromJson(json, ProductDiscountDTO.class);
            ProductDiscountType discountType = productDiscountService.update(paramsDTO);
            return JsonMapper.parseObject(discountType);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id){
        try {
            productDiscountService.delete(id);
        } catch (ServiceException e) {
            throw new HandledGeneralException(e);
        }
    }
}
