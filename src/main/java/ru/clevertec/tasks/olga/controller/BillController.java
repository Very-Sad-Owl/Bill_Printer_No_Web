package ru.clevertec.tasks.olga.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.service.BillService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import static ru.clevertec.tasks.olga.util.Constant.*;

@Slf4j
@RestController
@RequestMapping("/bills")
public class BillController {

    private final MessageSource messageSource;
    private final BillService billService;

    @Autowired
    public BillController(MessageSource messageSource, BillService billService) {
        this.messageSource = messageSource;
        this.billService = billService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String welcome(Locale loc) {
        return messageSource.getMessage("label.guide",null, loc);
    }

    @GetMapping(value = ACTION_LOG, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<Cart> log(@RequestParam(value = "nodes", required = false, defaultValue = "${pagination.page_size}") Integer nodesPerPage,
                          @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        PageRequest pageRequest = PageRequest.of(page, nodesPerPage);
        return billService.getAll(pageRequest);
    }

    @SneakyThrows
    @GetMapping(ACTION_FIND)
    public ResponseEntity<?> find(@RequestParam(value = "id") Integer id, Locale locale,
                                  @RequestParam(value = "pdf", defaultValue = "false", required = false) boolean printPdf) {
        Cart cart = billService.findById(id);
        if (!printPdf) {
            return formResponse(cart, MediaType.APPLICATION_JSON, HttpStatus.OK);
        } else {
            Path savedBillPath = billService.printBill(cart, locale);
            return formPdf(savedBillPath);
        }
    }

    @SneakyThrows
    @PostMapping(ACTION_SAVE)
    public ResponseEntity<?> save(@RequestBody CartParamsDTO params, Locale locale,
                                  @RequestParam(value = "pdf", defaultValue = "false", required = false) boolean printPdf) {
        Cart cart = billService.save(params);
        if (!printPdf) {
            return formResponse(cart, MediaType.APPLICATION_JSON, HttpStatus.CREATED);
        } else {
            Path savedBillPath = billService.printBill(cart, locale);
            return formPdf(savedBillPath);
        }
    }

    @PatchMapping(value = ACTION_PATCH, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Cart patch(@RequestBody CartParamsDTO params) {
        return billService.patch(params);
    }

    @PutMapping(value = ACTION_PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Cart update(@RequestBody CartParamsDTO params) {
        return billService.put(params);
    }

    @DeleteMapping(ACTION_DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Integer id) {
        billService.delete(id);
    }

    @SneakyThrows
    private ResponseEntity<byte[]> formPdf(Path savedBillPath) {
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

    @SneakyThrows
    private ResponseEntity<Cart> formResponse(Cart model, MediaType mediaType, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return new ResponseEntity<>(model, headers, status);
    }
}
