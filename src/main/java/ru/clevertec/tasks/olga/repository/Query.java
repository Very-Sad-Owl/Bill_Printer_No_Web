package ru.clevertec.tasks.olga.repository;

public interface Query {
    //product
    String INSERT_PRODUCT = "INSERT INTO products(title, price, discount_id) VALUES (:title, :price, :disc_id);";
    String FIND_PRODUCT_BY_ID = "select * from products join product_discounts " +
            "on products.discount_id = product_discounts.discount_id where products.product_id = :id;";
    String GET_PRODUCTS = "select * from products join product_discounts " +
            "on products.discount_id = product_discounts.discount_id" +
            "order by product_id limit :page_limit offset :page;";
    String UPDATE_PRODUCT = "UPDATE products SET title = :title, price = :price, discount_id = :disc_id WHERE products.product_id = :id;";
    String DELETE_PRODUCT = "delete from products where product_id = :id;";

    //cart
    String INSERT_CART = "INSERT INTO bills(card_id, cashier_id, price) " +
            "VALUES (:card, :cashier, :price);";
    String FIND_CART_BY_ID = "select * from bills " +
            "join cashiers on bills.cashier_id = cashiers.cashier_id " +
            "join cards on bills.card_id  = cards.card_id " +
            "join card_types on cards.discount_id = card_types.type_id " +
            "where bills.bill_id = :id;";
    String GET_CARTS = "select * from bills " +
            "join cashiers on bills.cashier_id = cashiers.cashier_id " +
            "join cards on bills.card_id  = cards.card_id " +
            "join card_types on cards.discount_id = card_types.type_id " +
            "order by bills.bill_id limit :page_limit offset :page;";
    String UPDATE_CART = "update bills set card_id = :card, cashier_id = :cashier, price = :price " +
            "WHERE bill_id = :id;";
    String DELETE_CART = "delete from bills " +
            "where bill_id = :id;";

    //slot
    String INSERT_SLOT = "INSERT INTO slots(product_id, quantity, price, cart_id) VALUES(:product, :quantity, :price, :cart);";
    String GET_SLOTS = "select * from slots " +
            "join products using(product_id) " +
            "join product_discounts using(discount_id) " +
            "order by slots.slot_id limit :page_limit offset :page;";
    String FIND_SLOT_BY_ID = "select * from slots " +
            "join products using(product_id) " +
            "join product_discounts using(discount_id) " +
            "where slots.slot_id = ?;";
    String UPDATE_SLOT = "UPDATE slots set product_id = :product, quantity = :quantity, price = :price " +
            "WHERE slot_id = ?;";
    String DELETE_SLOT = "DELETE FROM slots " +
            "WHERE slot_id = ?;";
    String SET_SLOT_CART_ID = "UPDATE slots set cart_id = :cart " +
            " WHERE slot_id = ?;";
    String FIND_SLOTS_BY_CART_ID = "select * from slots " +
            "join products on slots.product_id = products.product_id " +
            "join product_discounts on products.discount_id = product_discounts.discount_id " +
            "where cart_id = :cart;";
    String DELETE_SLOTS = "DELETE FROM slots WHERE cart_id IS NULL;";

    //cashier
    String INSERT_CASHIER = "INSERT INTO cashiers(name, surname) VALUES (:name, :surname);";
    String GET_CASHIERS = "select * from cashiers order by cashier_id limit :page_limit offset :page;";
    String FIND_CASHIER_BY_ID = "select * from cashiers where cashier_id = :id;";
    String UPDATE_CASHIER = "UPDATE cashiers set name = :name, surname = :surname WHERE cashier_id = :id;";
    String DELETE_CASHIER = "DELETE FROM cashiers WHERE cashier_id = :id;";

    //discount card
    String INSERT_DISCOUNT = "INSERT INTO cards(birthday, discount_id) VALUES (:bday, :disc_id);";
    String GET_DISCOUNTS = "select * from cards join card_types on cards.card_id = card_types.type_id order by cards.card_id limit :page_limit offset :page";
    String FIND_DISCOUNT_BY_ID = "select * from cards join card_types on cards.card_id = card_types.type_id where cards.card_id = :id";
    String UPDATE_DISCOUNT = "update cards set birthday = :bday, discount_id = :disc_id where card_id = :id";
    String DELETE_DISCOUNT = "delete from cards where card_id = :id;";

    //product discount type
    String INSERT_PRODUCT_DISCOUNT_TYPE = "INSERT INTO product_discounts(discount_title, discount_val, required_quantity) VALUES (:title, :val, :quantity);";
    String GET_PRODUCT_DISCOUNT_TYPES = "select * from product_discounts order by discount_id limit :page_limit offset :page;";
    String FIND_PRODUCT_DISCOUNT_TYPE = "select * from product_discounts where discount_id = :id;";
    String UPDATE_PRODUCT_DISCOUNT_TYPE = "update product_discounts set discount_title = :title, discount_val = :val, required_quantity = :quantity where discount_id = :id;";
    String DELETE_PRODUCT_DISCOUNT_TYPE = "delete from product_discounts where discount_id = :id;";

    //discount type
    String INSERT_DISCOUNT_TYPE = "INSERT INTO card_types(title, discount) VALUES (:title, :discount);";
    String GET_DISCOUNT_TYPES = "select * from card_types order by type_id limit :page_limit offset :page;";
    String FIND_DISCOUNT_TYPE = "select * from card_types where type_id = :id;";
    String UPDATE_DISCOUNT_TYPE = "UPDATE card_types set title = :title, discount = :discount WHERE type_id = :id;";
    String DELETE_DISCOUNT_TYPE = "delete from card_types WHERE type_id = :id;";
}
