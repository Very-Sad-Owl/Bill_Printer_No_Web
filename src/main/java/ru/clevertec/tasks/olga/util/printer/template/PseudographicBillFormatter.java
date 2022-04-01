package ru.clevertec.tasks.olga.util.printer.template;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.entity.Slot;
import ru.clevertec.tasks.olga.util.resourceprovider.MessageLocaleService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
public class PseudographicBillFormatter extends AbstractBillFormatter {

    private Locale locale = Locale.getDefault();

    @Override
    public List<String> format(Cart cart) {
        List<String> billBuilder = new ArrayListImpl<>();
        drawMetaInfo(cart.getId(), billBuilder);
        drawCashierInfo(cart.getCashier(), billBuilder);
        for (Slot slot : cart.getPositions()) {
            drawSlotInfo(slot, billBuilder);
        }
        drawPaymentInfo(cart, billBuilder);

        return billBuilder;
    }

    @Override
    public List<String> formatAll(List<Cart> cart) {
        List<String> billBuilder = new ArrayListImpl<>();
        for (Cart bill : cart) {
            List<String> billList = format(bill);
            billBuilder = Stream.concat(billBuilder.stream(), billList.stream())
                    .collect(Collectors.toList());
        }
        return billBuilder;
    }

    @Override
    public String drawLine(char delimiter) {
        return delimiter + "";
    }

    @Override
    public String centreLine(String line) {
        return "%s" + line + "%s";
    }

    @Override
    public void drawMetaInfo(long cartId, List<String> billBuilder) {
        billBuilder.add((MessageLocaleService.getMessage("label.pseudographics_char", locale)));
        billBuilder.add(centreLine(MessageLocaleService.getMessage("info.shop_title", locale)));
        billBuilder.add(centreLine(MessageLocaleService.getMessage("info.address", locale)));
        billBuilder.add(MessageLocaleService.getMessage("label.pseudographics_char", locale));
        billBuilder.add(centreLine(
                MessageLocaleService.getMessage("label.receipt_uid", locale) + " " + cartId));
        billBuilder.add(MessageLocaleService.getMessage("label.pseudographics_char", locale));
    }

    @Override
    public void drawCashierInfo(Cashier cashier, List<String> billBuilder) {
        billBuilder.add(
                drawSplittedLine(MessageLocaleService.getMessage("label.cashier_uid", locale) +
                                MessageLocaleService.getMessage("label.definition", locale),
                        cashier.getId() + "")
        );
        billBuilder.add(
                drawSplittedLine(
                        MessageLocaleService.getMessage("label.cashier", locale) +
                                MessageLocaleService.getMessage("label.definition", locale),
                        cashier.getFullName()
                )
        );
        billBuilder.add(drawLine(MessageLocaleService.getMessage("label.pseudographics_char", locale).charAt(0)));

    }

    public String drawSplittedLine(String firstHalf, String secondHalf) {
        return firstHalf + "%s" + secondHalf;
    }

    @Override
    public void drawSlotInfo(Slot slot, List<String> billBuilder) {
        billBuilder.add
                (drawSplittedLine(
                                slot.getProduct().getTitle(),
                                slot.getQuantity() +
                                        MessageLocaleService.getMessage("label.quantity_measure", locale)
                        )

                );
        billBuilder.add(drawSplittedLine(
                        MessageLocaleService.getMessage("label.original_price", locale) +
                                MessageLocaleService.getMessage("label.definition", locale) +
                                slot.getTotalPrice(),
                        MessageLocaleService.getMessage("label.total_price", locale) +
                                MessageLocaleService.getMessage("label.definition", locale) +
                                slot.getRawPrice()
                )
        );
        billBuilder.add(drawLine(MessageLocaleService.getMessage("label.pseudographics_char", locale).charAt(0)));
    }

    @Override
    public void drawPaymentInfo(Cart cart, List<String> billBuilder) {
        billBuilder.add(MessageLocaleService.getMessage("label.discount_card_uid", locale) + cart.getDiscountCard().getId());
        billBuilder.add(drawSplittedLine(
                        MessageLocaleService.getMessage("label.original_price", locale) +
                                MessageLocaleService.getMessage("label.definition", locale),
                        cart.getRawPrice() + ""
                )
        );
        billBuilder.add(drawSplittedLine(
                        MessageLocaleService.getMessage("label.tottal_discount", locale) +
                                MessageLocaleService.getMessage("label.definition", locale),
                        cart.getTotalDiscount() +
                                MessageLocaleService.getMessage("label.discount_percentage", locale) +
                                MessageLocaleService.getMessage("label.discount_percentage", locale)
                )
        );
        billBuilder.add(drawSplittedLine(
                        MessageLocaleService.getMessage("label.total_price", locale) +
                                MessageLocaleService.getMessage("label.definition", locale),
                        cart.getPrice() + ""
                )
        );
        billBuilder.add(drawLine(MessageLocaleService.getMessage("label.pseudographics_char", locale).charAt(0)));

    }
}