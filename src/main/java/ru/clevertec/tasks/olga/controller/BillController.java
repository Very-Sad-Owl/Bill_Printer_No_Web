package ru.clevertec.tasks.olga.controller;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.exception.handeled.WritingException;
import ru.clevertec.tasks.olga.service.CartService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/bills")
public class BillController {

    private final Gson gson;
    private final MessageSource messageSource;
    private final CartService cartService;

    @Autowired
    public BillController(Gson gson, MessageSource messageSource, CartService cartService) {
        this.gson = gson;
        this.messageSource = messageSource;
        this.cartService = cartService;
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
        List<Cart> carts = cartService.getAll(nodesPerPage, page);
        return gson.toJson(carts);
    }

    @SneakyThrows
    @GetMapping(value = "/find", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Object find(@RequestParam (value = "id") Integer id, Locale locale,
                       @RequestParam (value = "pdf", defaultValue = "false", required = false) boolean printPdf) {
        Cart cart = cartService.findById(id);
        if (!printPdf) {
            return gson.toJson(cart);
        } else {
            Path savedBillPath = cartService.printBill(cart, locale);
            return formPdf(savedBillPath);
        }
    }

    @SneakyThrows
    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Object save(@RequestBody String json, Locale locale,
                       @RequestParam (value = "pdf", defaultValue = "false", required = false) boolean printPdf) {
        CartParamsDTO cartParamsDTO = gson.fromJson(json, CartParamsDTO.class);
        Cart cart = cartService.save(cartParamsDTO);
        if (!printPdf) {
            return gson.toJson(cart);
        } else {
            Path savedBillPath = cartService.printBill(cart, locale);
            return formPdf(savedBillPath);
        }
    }

    @PatchMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody String json) {
        CartParamsDTO cartParamsDTO = gson.fromJson(json, CartParamsDTO.class);
        Cart cart = cartService.update(cartParamsDTO);
        return gson.toJson(cart);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id) {
        cartService.delete(id);
    }

    @SneakyThrows
    private ResponseEntity<byte[]> formPdf(Path savedBillPath){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = savedBillPath.getFileName().toString();
            headers.setContentDispositionFormData(filename, filename);
            return new ResponseEntity<>(Files.readAllBytes(savedBillPath), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new WritingException(e);
        }
    }
}
