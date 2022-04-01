package ru.clevertec.tasks.olga.util;

public interface Constant {
    //common
    String PAGINATION_PARAM = "pagination";
    String OFFSET_PARAM = "offset";
    String CATEGORY = "table";
    String ACTION_PARAM = "command";

    //card
    String CARD_ID_PARAM = "id";
    String CARD_BIRTHDAY = "birthday";
    String CART_CARD_TYPE_ID = "discountId";

    //card type
    String CARD_TYPE_ID = "id";
    String CARD_TYPE_TITLE = "title";
    String CARD_TYPE_DISCOUNT_VAL = "discountVal";

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

    //product discount
    String PRODUCT_DISCOUNT_ID = "id";
    String PRODUCT_DISCOUNT_TITLE = "title";
    String PRODUCT_DISCOUNT_VAL = "val";
    String PRODUCT_DISCOUNT_MIN_QUANTITY = "requiredQuantity";

    //cashier
    String CASHIER_ID_PARAM = "id";
    String CASHIER_NAME_PARAM = "name";
    String CASHIER_SURNAME_PARAM = "surname";

    //actions
    String ACTION_PRINT = "print";
    String ACTION_LOG = "log";
    String ACTION_UPDATE = "update";
    String ACTION_SAVE = "save";
    String CACHE_ALG = "cache.algorithm";
    String CACHE_CAPACITY = "cache.capacity";

    String FILENAME_PDF_FORMAT = "%s-%s.pdf";

}
