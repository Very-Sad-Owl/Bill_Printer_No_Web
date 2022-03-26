package ru.clevertec.tasks.olga.util.argsparser.factory;

import lombok.Getter;
import ru.clevertec.tasks.olga.model.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.model.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.model.dto.ProductParamsDto;
import ru.clevertec.tasks.olga.util.argsparser.ArgumentsSorter;
import ru.clevertec.tasks.olga.util.argsparser.CartArgumentsSorter;
import ru.clevertec.tasks.olga.util.argsparser.CashierArgumentsSorter;
import ru.clevertec.tasks.olga.util.argsparser.ProductArgumentsSorter;

@Getter
public class SorterFactory {

    private SorterFactory(){}

    private static final SorterFactory instance = new SorterFactory();

    private final ArgumentsSorter<CartParamsDTO> cartSorter = new CartArgumentsSorter();
    private final ArgumentsSorter<ProductParamsDto> productSorter = new ProductArgumentsSorter();
    private final ArgumentsSorter<CashierParamsDTO> cashierSorter = new CashierArgumentsSorter();

    public static SorterFactory getInstance(){
        return instance;
    }

}
