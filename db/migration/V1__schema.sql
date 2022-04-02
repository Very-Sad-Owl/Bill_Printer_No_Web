


SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 207 (class 1259 OID 24632)
-- Name: card; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.card (
                             id bigint NOT NULL,
                             birthday date NOT NULL,
                             discount_id bigint NOT NULL
);


ALTER TABLE public.card OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 24630)
-- Name: card_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.card ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.card_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- TOC entry 211 (class 1259 OID 24654)
-- Name: cart; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cart (
                             id bigint NOT NULL,
                             card_id bigint NOT NULL,
                             cashier_id bigint NOT NULL,
                             price double precision NOT NULL
);


ALTER TABLE public.cart OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 24652)
-- Name: cart_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.cart ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.cart_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- TOC entry 209 (class 1259 OID 24644)
-- Name: cashier; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cashier (
                                id bigint NOT NULL,
                                name text NOT NULL,
                                surname text NOT NULL
);


ALTER TABLE public.cashier OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 24642)
-- Name: cashier_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.cashier ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.cashier_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- TOC entry 201 (class 1259 OID 24592)
-- Name: product_discount; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_discount (
                                         id bigint NOT NULL,
                                         discount_title text NOT NULL,
                                         discount_val double precision NOT NULL,
                                         required_quantity integer NOT NULL
);


ALTER TABLE public.product_discount OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 24590)
-- Name: discount_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.product_discount ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.discount_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- TOC entry 213 (class 1259 OID 24676)
-- Name: general_discount; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.general_discount (
                                         id bigint NOT NULL,
                                         title text NOT NULL,
                                         discount double precision NOT NULL
);


ALTER TABLE public.general_discount OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 24674)
-- Name: general_discount_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.general_discount ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.general_discount_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- TOC entry 203 (class 1259 OID 24602)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
                                id bigint NOT NULL,
                                title text NOT NULL,
                                price double precision NOT NULL,
                                discount_id bigint NOT NULL
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 24600)
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.product ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- TOC entry 205 (class 1259 OID 24617)
-- Name: slot; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.slot (
                             id bigint NOT NULL,
                             product_id bigint NOT NULL,
                             quantity integer NOT NULL,
                             price double precision NOT NULL,
                             cart_id bigint
);


ALTER TABLE public.slot OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 24615)
-- Name: slot_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.slot ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.slot_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- TOC entry 2897 (class 2606 OID 24636)
-- Name: card card_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT card_pkey PRIMARY KEY (id);


--
-- TOC entry 2901 (class 2606 OID 24658)
-- Name: cart cart_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_pkey PRIMARY KEY (id);


--
-- TOC entry 2899 (class 2606 OID 24651)
-- Name: cashier cashier_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cashier
    ADD CONSTRAINT cashier_pkey PRIMARY KEY (id);


--
-- TOC entry 2891 (class 2606 OID 24599)
-- Name: product_discount discount_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_discount
    ADD CONSTRAINT discount_pkey PRIMARY KEY (id);


--
-- TOC entry 2903 (class 2606 OID 24683)
-- Name: general_discount general_discount_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.general_discount
    ADD CONSTRAINT general_discount_pkey PRIMARY KEY (id);


--
-- TOC entry 2893 (class 2606 OID 24609)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 2895 (class 2606 OID 24621)
-- Name: slot slot_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.slot
    ADD CONSTRAINT slot_pkey PRIMARY KEY (id);


--
-- TOC entry 2908 (class 2606 OID 24704)
-- Name: cart card_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT card_id FOREIGN KEY (card_id) REFERENCES public.card(id) NOT VALID;


--
-- TOC entry 2905 (class 2606 OID 24699)
-- Name: slot cart_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.slot
    ADD CONSTRAINT cart_id FOREIGN KEY (cart_id) REFERENCES public.cart(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2907 (class 2606 OID 24664)
-- Name: cart cashier_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cashier_id FOREIGN KEY (cashier_id) REFERENCES public.cashier(id);


--
-- TOC entry 2904 (class 2606 OID 24610)
-- Name: product discount; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT discount FOREIGN KEY (discount_id) REFERENCES public.product_discount(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2906 (class 2606 OID 24684)
-- Name: card discount; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT discount FOREIGN KEY (discount_id) REFERENCES public.general_discount(id) NOT VALID;


-- Completed on 2022-04-01 18:22:34

--
-- PostgreSQL database dump complete
--