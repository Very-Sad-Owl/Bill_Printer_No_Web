package ru.clevertec.tasks.olga.util.printer.template;

import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.entity.Slot;

import java.util.List;
import java.util.Locale;

@Component
public abstract class AbstractBillFormatter {
    protected abstract void drawMetaInfo(long cartId, List<String> destination);
    protected abstract void drawCashierInfo(Cashier cashier, List<String> destination);
    protected abstract void drawSlotInfo(Slot slot, List<String> destination);
    protected abstract void drawPaymentInfo(Cart cart, List<String> destination);
    public abstract List<String> format(Cart cart, Locale locale);
    public abstract List<String> formatAll(List<Cart> cart, Locale locale);
    protected abstract String drawLine(char delimiter);
    protected abstract String centreLine(String line);
}
