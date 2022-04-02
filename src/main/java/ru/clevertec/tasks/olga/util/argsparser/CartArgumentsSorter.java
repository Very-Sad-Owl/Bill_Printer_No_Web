package ru.clevertec.tasks.olga.util.argsparser;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.dto.RequestParamsDto;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;
import ru.clevertec.tasks.olga.dto.CartParamsDTO;

import java.util.*;

import static ru.clevertec.tasks.olga.util.Constant.*;

public class CartArgumentsSorter extends ArgumentsSorter<CartParamsDTO> {

    @Override
    public CartParamsDTO retrieveArgsFromMap(Map<String, String[]> args, RequestParamsDto requestParams){
        CartParamsDTO params = new CartParamsDTO();
        for (Map.Entry<String, String[]> pair : args.entrySet()){
            String key = pair.getKey();
            String[] values = pair.getValue();
            switch (key) {
                case BILL_CARD_ID_PARAM:
                    params.card_uid = Long.parseLong(values[0]);
                    break;
                case BILL_CASHIER_ID_PARAM:
                    params.cashier_uid = Long.parseLong(values[0]);
                    break;
                case BILL_ID_PARAM:
                    params.id = Long.parseLong(values[0]);
                    break;
                case BILL_PRODUCTS:
                    Map<Long, Integer> goods = new HashMap<>();
                    for (String el : values){
                        String[]idQPairVals = el.split(",");
                        for (String idQPair : idQPairVals) {
                            String[] vals = idQPair.split("-");
                            goods.put(Long.parseLong(vals[0]), Integer.parseInt(vals[1]));
                            params.products = goods;
                        }
                    }
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
        if (required.isEmpty()) return true;
        List<String> listArgs = new ArrayList<>(args);
        return listArgs.containsAll(required);
    }


    private List<String> formCmdRequiredArgs(String action){
        List<String> required = new ArrayListImpl<>();
        switch (action){
            case ACTION_SAVE:
                required.add(BILL_CARD_ID_PARAM);
                required.add(BILL_CASHIER_ID_PARAM);
                required.add(BILL_PRODUCTS);
                break;
            case ACTION_PRINT:
            case ACTION_UPDATE:
            case ACTION_DELETE:
                required.add(BILL_ID_PARAM);
                break;
        }
        return required;
    }
}
