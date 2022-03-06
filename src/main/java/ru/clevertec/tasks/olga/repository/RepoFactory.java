package ru.clevertec.tasks.olga.repository;//package com.epam.news.repository;
//
//import com.epam.news.repository.impl.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RepoFactory {
//
//    private static final RepoFactory instance = new RepoFactory();
//
//    @Autowired
//    private final ProductRepositoryImpl productRepo;
//
//    private final AbstractRepository cardRepo;
//    private final AbstractRepository cashierRepo;
//
//    @Autowired
//    public void setProductRepo(ProductRepositoryImpl productRepo){this.productRepo = productRepo;}
//
//    private RepoFactory(){}
//
//    public AbstractRepository getProductRepo(){
//        return productRepo;
//    }
//    public AbstractRepository getCardRepo(){
//        return cardRepo;
//    }
//    public AbstractRepository getCashierRepo(){
//        return cashierRepo;
//    }
//
//    public static RepoFactory getInstance() {
//        return instance;
//    }
//}
