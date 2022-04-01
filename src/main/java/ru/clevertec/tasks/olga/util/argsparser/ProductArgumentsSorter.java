package ru.clevertec.tasks.olga.util.argsparser;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.dto.RequestParamsDto;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;
import ru.clevertec.tasks.olga.dto.ProductParamsDto;

import java.util.*;

import static ru.clevertec.tasks.olga.util.Constant.*;

public class ProductArgumentsSorter extends ArgumentsSorter<ProductParamsDto> {

    @Override
    public ProductParamsDto retrieveArgsFromMap(Map<String, String[]> args, RequestParamsDto requestParams){
        ProductParamsDto params = new ProductParamsDto();
        for (Map.Entry<String, String[]> pair : args.entrySet()){
            String key = pair.getKey();
            String[] values = pair.getValue();
            switch (key) {
                case PRODUCT_ID_PARAM:
                    params.id = Long.parseLong(values[0]);
                    break;
                case PRODUCT_DISCOUNT_PARAM:
                    params.discount_id = Long.parseLong(values[0]);
                    break;
                case PRODUCT_TITLE_PARAM:
                    params.title = values[0];
                    break;
                case PRODUCT_PRICE_PARAM:
                    params.price = Double.parseDouble(values[0]);
                    break;
            }
        }
        if (!checkRequiredArgs(args.keySet(), requestParams.action)){
            throw new NoRequiredArgsException();
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
                required.add(PRODUCT_ID_PARAM);
                break;
            case ACTION_SAVE:
                required.add(PRODUCT_TITLE_PARAM);
                required.add(PRODUCT_PRICE_PARAM);
                required.add(PRODUCT_DISCOUNT_PARAM);
                break;
        }
        return required;
    }
}
