package ru.clevertec.tasks.olga.util.argsparser;

import org.springframework.stereotype.Component;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.dto.ProductDiscountDTO;
import ru.clevertec.tasks.olga.dto.RequestParamsDto;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsExceptionCustom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.clevertec.tasks.olga.util.Constant.*;

@Component
public class ProductDiscountSorter extends ArgumentsSorter<ProductDiscountDTO> {

    @Override
    public ProductDiscountDTO retrieveArgsFromMap(Map<String, String[]> args, RequestParamsDto requestParams){
        ProductDiscountDTO params = new ProductDiscountDTO();
        for (Map.Entry<String, String[]> pair : args.entrySet()){
            String key = pair.getKey();
            String[] values = pair.getValue();
            switch (key) {
                case PRODUCT_DISCOUNT_ID:
                    params.id = Long.parseLong(values[0]);
                    break;
                case PRODUCT_DISCOUNT_TITLE:
                    params.title = values[0];
                    break;
                case PRODUCT_DISCOUNT_VAL:
                    params.val = Double.parseDouble(values[0]);
                    break;
                case PRODUCT_DISCOUNT_MIN_QUANTITY:
                    params.requiredQuantity = Integer.parseInt(values[0]);
                    break;
            }
        }
        if (!checkRequiredArgs(args.keySet(), requestParams.action)){
            throw new NoRequiredArgsExceptionCustom();
        }
        return params;
    }


    private boolean checkRequiredArgs(Set<String> args, String action) {
        List<String> required = formCmdRequiredArgs(action);
        List<String> listArgs = new ArrayList<>(args);
        return listArgs.containsAll(required);
    }


    private List<String> formCmdRequiredArgs(String action){
        List<String> required = new ArrayListImpl<>();
        switch (action){
            case ACTION_PRINT:
            case ACTION_UPDATE:
            case ACTION_DELETE:
                required.add(PRODUCT_DISCOUNT_ID);
                break;
            case ACTION_SAVE:
                required.add(PRODUCT_DISCOUNT_TITLE);
                required.add(PRODUCT_DISCOUNT_VAL);
                required.add(PRODUCT_DISCOUNT_MIN_QUANTITY);
                break;
        }
        return required;
    }
}
