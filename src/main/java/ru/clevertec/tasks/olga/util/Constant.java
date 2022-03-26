package ru.clevertec.tasks.olga.util;

public interface Constant {
    //common
    String PAGINATION_PARAM = "pagination";
    String OFFSET_PARAM = "offset";

    //cart
    String BILL_ID_PARAM = "id";
    String BILL_CARD_ID_PARAM = "card_uid";
    String BILL_CASHIER_ID_PARAM = "cashier_uid";
    String BILL_PRODUCTS = "products";

    //product
    String PRODUCT_ID_PARAM = "id";
    String PRODUCT_DISCOUNT_PARAM = "discount_id";
    String PRODUCT_TITLE_PARAM = "title";
    String PRODUCT_PRICE_PARAM = "price";

    //cashier
    String CASHIER_ID_PARAM = "id";
    String CASHIER_NAME_PARAM = "name";
    String CASHIER_SURNAME_PARAM = "surname";

    //actions
    String ACTION_PARAM = "command";
    String ACTION_PRINT = "print";
    String ACTION_LOG = "log";
    String ACTION_FIND_BY_ID = "find";
    String ACTION_SAVE = "save";
    String CACHE_ALG = "cache.algorithm";
    String CACHE_CAPACITY = "cache.capacity";

    String FILENAME_PDF_FORMAT = "%s-%s.pdf";

}
