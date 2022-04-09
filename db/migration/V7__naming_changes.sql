ALTER TABLE IF EXISTS public.card
    RENAME TO cards;

ALTER TABLE IF EXISTS public.cards
    RENAME id TO card_id;

ALTER TABLE IF EXISTS public.cart
    RENAME TO bills;

ALTER TABLE IF EXISTS public.bills
    RENAME id TO bill_id;

ALTER TABLE IF EXISTS public.cashier
    RENAME TO cashiers;

ALTER TABLE IF EXISTS public.cashiers
    RENAME id TO cashier_id;

ALTER TABLE IF EXISTS public.general_discount
    RENAME TO card_types;

ALTER TABLE IF EXISTS public.card_types
    RENAME id TO type_id;

ALTER TABLE IF EXISTS public.product
    RENAME TO products;

ALTER TABLE IF EXISTS public.products
    RENAME id TO product_id;

ALTER TABLE IF EXISTS public.product_discount
    RENAME TO product_discounts;

ALTER TABLE IF EXISTS public.product_discounts
    RENAME id TO discount_id;

ALTER TABLE IF EXISTS public.slot
    RENAME TO slots;

ALTER TABLE IF EXISTS public.slots
    RENAME id TO slot_id;