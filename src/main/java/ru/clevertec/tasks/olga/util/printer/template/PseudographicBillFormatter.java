package ru.clevertec.tasks.olga.util.printer.template;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.entity.Slot;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class PseudographicBillFormatter extends AbstractBillFormatter {

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public List<String> format(Cart cart, Locale locale) {
        List<String> billBuilder = new ArrayListImpl<>();
        drawMetaInfo(cart.getId(), billBuilder, locale);
        drawCashierInfo(cart.getCashier(), billBuilder, locale);
        for (Slot slot : cart.getPositions()) {
            drawSlotInfo(slot, billBuilder, locale);
        }
        drawPaymentInfo(cart, billBuilder, locale);

        return billBuilder;
    }

    @Override
    public List<String> formatAll(List<Cart> cart, Locale locale) {
        List<String> billBuilder = new ArrayListImpl<>();
        for (Cart bill : cart) {
            List<String> billList = format(bill, locale);
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
    public void drawMetaInfo(long cartId, List<String> billBuilder, Locale locale) {
        billBuilder.add(messageSource.getMessage("label.pseudographics_char", null, locale));
        billBuilder.add(centreLine(messageSource.getMessage("info.shop_title", null, locale)));
        billBuilder.add(centreLine(messageSource.getMessage("info.address", null, locale)));
        billBuilder.add(messageSource.getMessage("label.pseudographics_char", null, locale));
        billBuilder.add(centreLine(
                messageSource.getMessage("label.receipt_uid", null, locale) + " " + cartId));
        billBuilder.add(messageSource.getMessage("label.pseudographics_char", null, locale));
    }

    @Override
    public void drawCashierInfo(Cashier cashier, List<String> billBuilder, Locale locale) {
        billBuilder.add(
                drawSplittedLine(messageSource.getMessage("label.cashier_uid", null, locale) +
                                messageSource.getMessage("label.definition", null, locale),
                        cashier.getId() + "", locale)
        );
        billBuilder.add(
                drawSplittedLine(
                        messageSource.getMessage("label.cashier", null, locale) +
                                messageSource.getMessage("label.definition", null, locale),
                        cashier.getFullName(), locale
                )
        );
        billBuilder.add(drawLine(messageSource.getMessage("label.pseudographics_char", null, locale).charAt(0)));

    }

    public String drawSplittedLine(String firstHalf, String secondHalf, Locale locale) {
        return firstHalf + "%s" + secondHalf;
    }

    @Override
    public void drawSlotInfo(Slot slot, List<String> billBuilder, Locale locale) {
        billBuilder.add
                (drawSplittedLine(
                        slot.getProduct().getTitle(),
                        slot.getQuantity() +
                                messageSource.getMessage("label.quantity_measure", null, locale)
                        , locale)

                );
        billBuilder.add(drawSplittedLine(
                        messageSource.getMessage("label.original_price", null, locale) +
                                messageSource.getMessage("label.definition", null, locale) +
                                slot.getTotalPrice(),
                        messageSource.getMessage("label.total_price", null, locale) +
                                messageSource.getMessage("label.definition", null, locale) +
                                slot.getRawPrice(), locale
                )
        );
        billBuilder.add(drawLine(messageSource.getMessage("label.pseudographics_char", null, locale).charAt(0)));
    }

    @Override
    public void drawPaymentInfo(Cart cart, List<String> billBuilder, Locale locale) {
        billBuilder.add(messageSource.getMessage("label.discount_card_uid", null, locale) + cart.getDiscountCard().getId());
        billBuilder.add(drawSplittedLine(
                        messageSource.getMessage("label.original_price", null, locale) +
                                messageSource.getMessage("label.definition", null, locale),
                        cart.getRawPrice() + "", locale
                )
        );
        billBuilder.add(drawSplittedLine(
                        messageSource.getMessage("label.total_discount", null, locale) +
                                messageSource.getMessage("label.definition", null, locale),
                        cart.getTotalDiscount() +
                                messageSource.getMessage("label.discount_percentage", null, locale) +
                                messageSource.getMessage("label.discount_percentage", null, locale), locale
                )
        );
        billBuilder.add(drawSplittedLine(
                        messageSource.getMessage("label.total_price", null, locale) +
                                messageSource.getMessage("label.definition", null, locale),
                        cart.getPrice() + "", locale
                )
        );
        billBuilder.add(drawLine(messageSource.getMessage("label.pseudographics_char", null, locale).charAt(0)));

    }
}