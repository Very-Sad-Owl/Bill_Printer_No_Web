package ru.clevertec.tasks.olga.util.printer.template;

import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.entity.Slot;

import java.util.List;

public abstract class AbstractBillFormatter {

    public abstract List<String> format(Cart cart);
    public abstract List<String> formatAll(List<Cart> cart);
    protected abstract void drawMetaInfo(long cartId, List<String> destination);
    protected abstract void drawCashierInfo(Cashier cashier, List<String> destination);
    protected abstract void drawSlotInfo(Slot slot, List<String> destination);
    protected abstract void drawPaymentInfo(Cart cart, List<String> destination);
    protected abstract String drawLine(char delimiter);
    protected abstract String centreLine(String line);
}
