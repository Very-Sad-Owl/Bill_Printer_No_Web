package ru.clevertec.tasks.olga.repository;

public interface Query {
    //product
    String INSERT_PRODUCT = "INSERT INTO product(title, price, discount_id) VALUES (?, ?, ?);";
    String FIND_PRODUCT_BY_ID = "select * from product join product_discount " +
            "on product.discount_id = product_discount.id where product.id = ?;";
    String GET_PRODUCTS = "select * from product join product_discount on product.discount_id = product_discount.id" +
            " order by product.id limit ? offset ?;";
    String UPDATE_PRODUCT = "UPDATE product SET title=?, price=?, discount_id=? WHERE product.id = ?;";
    String DELETE_PRODUCT = "delete from product where id = ?;";

    //cart
    String INSERT_CART = "INSERT INTO public.cart(card_id, cashier_id, price) VALUES (?, ?, ?);";
    String FIND_CART_BY_ID = "select * from cart join cashier on cart.cashier_id = cashier.id join card on cart.card_id  = card.id join general_discount on card.discount_id = general_discount.id where cart.id = ?;";
    String GET_CARTS = "select * from cart join cashier on cart.cashier_id = cashier.id join card on cart.card_id  = card.id join general_discount on card.discount_id = general_discount.id order by cart.id limit ? offset ?;";
    String UPDATE_CART = "update cart set card_id = ?, cashier_id = ?, price = ? WHERE id = ?;";
    String DELETE_CART = "delete from cart where id = ?;";

    //cashier
    String INSERT_CASHIER = "INSERT INTO cashier(name, surname) VALUES (?, ?);";
    String GET_CASHIERS = "select * from cashier order by id limit ? offset ?;";
    String FIND_CASHIER_BY_ID = "select * from cashier where id = ?;";
    String UPDATE_CASHIER = "UPDATE cashier set name=?, surname=? WHERE id = ?;";
    String DELETE_CASHIER = "DELETE FROM cashier WHERE id = ?;";

    //slot
    String INSERT_SLOT = "INSERT INTO slot(product_id, quantity, price) VALUES (?, ?, ?);";
    String GET_SLOTS = "select * from slot join product using(id) join product_discount using(id) order by slot.id limit ? offset ?;";
    String FIND_SLOT_BY_ID = "select * from slot join product using(id) join product_discount using(id) where slot.id = ?;";
    String UPDATE_SLOT = "UPDATE slot set product_id=?, quantity=?, price=? WHERE id = ?;";
    String DELETE_SLOT = "DELETE FROM slot WHERE id = ?;";
    String SET_SLOT_CART_ID = "UPDATE slot set cart_id=? WHERE id = ?;";
    String FIND_SLOTS_BY_CART_ID = "select * from slot join product on slot.product_id = product.id join product_discount on product.discount_id = product_discount.id where cart_id = ?;";

    //discount card
    String INSERT_DISCOUNT = "INSERT INTO card(birthday, discount_id) VALUES (?, ?);";
    String GET_DISCOUNTS = "select * from card join general_discount using(id) order by card.id limit ? offset ?";
    String FIND_DISCOUNT_BY_ID = "select * from card join general_discount on card.discount_id = general_discount.id where card.id = ?";
    String UPDATE_DISCOUNT = "update card set birthday = ?, discount_id = ? where id = ?";
    String DELETE_DISCOUNT = "delete from card where id = ?;";

    //product discount type
    String INSERT_PRODUCT_DISCOUNT_TYPE = "INSERT INTO product_discount(discount_title, discount_val, required_quantity) VALUES (?, ?, ?);";
    String GET_PRODUCT_DISCOUNT_TYPES = "select * from product_discount order by id limit ? offset ?";
    String FIND_PRODUCT_DISCOUNT_TYPE = "select * from product_discount where id = ?;";
    String UPDATE_PRODUCT_DISCOUNT_TYPE = "update product_discount set discount_title = ?, discount_val = ?, required_quantity = ? where id = ?;";
    String DELETE_PRODUCT_DISCOUNT_TYPE = "delete from product_discount where id = ?;";

    //discount type
    String INSERT_DISCOUNT_TYPE = "INSERT INTO general_discount(title, discount) VALUES (?, ?);";
    String GET_DISCOUNT_TYPES = "select * from general_discount order by id limit ? offset ?;";
    String FIND_DISCOUNT_TYPE = "select * from general_discount where id = ?;";
    String UPDATE_DISCOUNT_TYPE = "UPDATE general_discount set title = ?, discount = ? WHERE id = ?;";
    String DELETE_DISCOUNT_TYPE = "delete from general_discount WHERE id = ?;";
}
