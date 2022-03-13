package ru.clevertec.tasks.olga.util.formatter;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.model.Slot;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PseudographicBillFormatter extends AbstractBillFormatter {

    private final int SYMBOL_LIMIT_PER_LINE = 48;

    @Override
    public List<String> format(Cart cart) {
        List<String> billBuilder = new ArrayListImpl<>();
        drawMetaInfo(cart.getId(), billBuilder);
        drawCashierInfo(cart.getCashier(), billBuilder);
        for (Slot slot : cart.getPositions()){
            drawSlotInfo(slot, billBuilder);
        }
        drawPaymentInfo(cart, billBuilder);

        return billBuilder;
    }

    @Override
    public List<String> formatAll(List<Cart> cart) {
        List<String> billBuilder = new ArrayListImpl<>();
        for (Cart bill : cart){
            List<String> billList = format(bill);
            billBuilder = Stream.concat(billBuilder.stream(), billList.stream())
                    .collect(Collectors.toList());
        }
        return billBuilder;
    }

    @Override
    public String drawLine(char delimiter, int len) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < len; i++){
            line.append(delimiter);
        }
        return line.toString();
    }

    @Override
    public String centreLine(String line, char delimiter) {
        StringBuilder offsetLine = new StringBuilder();
        int offset = (SYMBOL_LIMIT_PER_LINE - line.length()) / 2;
        offsetLine.append(drawLine(delimiter, offset));
        offsetLine.append(line);
        offsetLine.append(drawLine(delimiter, offset));
        return offsetLine.toString();
    }

    @Override
    public void drawMetaInfo(long cartId, List<String> billBuilder) {
        billBuilder.add(drawLine(MessageLocaleService.getMessage("label.pseudographics_char").charAt(0), SYMBOL_LIMIT_PER_LINE));
        billBuilder.add(centreLine(MessageLocaleService.getMessage("info.shop_title"), ' '));
        billBuilder.add(centreLine(MessageLocaleService.getMessage("info.address"), ' '));
        billBuilder.add(drawLine(MessageLocaleService.getMessage("label.pseudographics_char").charAt(0), SYMBOL_LIMIT_PER_LINE));
        billBuilder.add(centreLine(
                MessageLocaleService.getMessage("label.receipt_uid") + " " + cartId, ' '));
        billBuilder.add(drawLine(MessageLocaleService.getMessage("label.pseudographics_delimiter").charAt(0), SYMBOL_LIMIT_PER_LINE));
    }

    @Override
    public void drawCashierInfo(Cashier cashier, List<String> billBuilder) {
        billBuilder.add(
                MessageLocaleService.getMessage("label.cashier_uid") +
                        MessageLocaleService.getMessage("label.definition") +
                        cashier.getId()
        );
        billBuilder.add(
                MessageLocaleService.getMessage("label.cashier") +
                        MessageLocaleService.getMessage("label.definition") +
                            cashier.getFullName()
        );
        billBuilder.add(drawLine(MessageLocaleService.getMessage("label.pseudographics_delimiter").charAt(0),
                SYMBOL_LIMIT_PER_LINE));

    }

    @Override
    public void drawSlotInfo(Slot slot, List<String>billBuilder) {
        billBuilder.add(
                slot.getProduct().getId() + " " +
                        slot.getProduct().getTitle() + " " +
                        slot.getQuantity()

        );
        billBuilder.add(
                MessageLocaleService.getMessage("label.original_price") +
                        MessageLocaleService.getMessage("label.definition") +
                        slot.getTotalPrice() + " " +
                        MessageLocaleService.getMessage("label.total_price") +
                        MessageLocaleService.getMessage("label.definition") +
                        slot.getRawPrice()
        );
        billBuilder.add(drawLine(MessageLocaleService.getMessage("label.pseudographics_delimiter").charAt(0),
                SYMBOL_LIMIT_PER_LINE));
    }

    @Override
    public void drawPaymentInfo(Cart cart, List<String> billBuilder) {
        billBuilder.add(MessageLocaleService.getMessage("label.discount_card_uid") + cart.getDiscountCard().getId());
        billBuilder.add(
                MessageLocaleService.getMessage("label.original_price") +
                        MessageLocaleService.getMessage("label.definition") +
                        cart.getRawPrice()
        );
        billBuilder.add(
                MessageLocaleService.getMessage("label.tottal_discount") +
                        MessageLocaleService.getMessage("label.definition") +
                        cart.getTotalDiscount() +
                        MessageLocaleService.getMessage("label.discount_percentage")
        );
        billBuilder.add(
                MessageLocaleService.getMessage("label.total_price") +
                        MessageLocaleService.getMessage("label.definition") +
                        cart.getPrice()
        );
        billBuilder.add(drawLine(MessageLocaleService.getMessage("label.pseudographics_delimiter").charAt(0),
                SYMBOL_LIMIT_PER_LINE));

    }
}
