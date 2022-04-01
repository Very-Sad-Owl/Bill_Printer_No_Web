package ru.clevertec.tasks.olga.util.argsparser.factory;

import lombok.Getter;
import ru.clevertec.tasks.olga.dto.*;
import ru.clevertec.tasks.olga.util.argsparser.*;

@Getter
public class SorterFactory {

    private SorterFactory(){}

    private static final SorterFactory instance = new SorterFactory();

    private final ArgumentsSorter<CartParamsDTO> cartSorter = new CartArgumentsSorter();
    private final ArgumentsSorter<ProductParamsDto> productSorter = new ProductArgumentsSorter();
    private final ArgumentsSorter<CashierParamsDTO> cashierSorter = new CashierArgumentsSorter();
    private final ArgumentsSorter<CardParamsDTO> cardSorter = new DiscountCardArgumentsSorter();
    private final ArgumentsSorter<CardTypeDto> cardTypeSorter = new CardTypeArgumentsSorter();
    private final ArgumentsSorter<ProductDiscountDTO> productDiscountSorter = new ProductDiscountSorter();

    public static SorterFactory getInstance(){
        return instance;
    }

}
