package ru.clevertec.tasks.olga.util.argsparser;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;
import ru.clevertec.tasks.olga.dto.CartParamsDTO;

import java.util.*;

import static ru.clevertec.tasks.olga.util.Constant.*;

public class CartArgumentsSorter extends ArgumentsSorter<CartParamsDTO> {

    @Override
    public CartParamsDTO retrieveArgs(Map<String, String[]> args){
        CartParamsDTO params = new CartParamsDTO();
        retrieveBaseArgs(args, params);
        for (Map.Entry<String, String[]> pair : args.entrySet()){
            String key = pair.getKey();
            String[] values = pair.getValue();
            switch (key) {
                case BILL_CARD_ID_PARAM:
                    params.card_id = Long.parseLong(values[0]);
                    break;
                case BILL_CASHIER_ID_PARAM:
                    params.cashier_id = Long.parseLong(values[0]);
                    break;
                case ACTION_PARAM:
                    params.action = values[0];
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
                            params.goods = goods;
                        }
                    }
                    break;
            }
        }
        if (!checkRequiredArgs(args.keySet(), params.action)){
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
                required.add(ACTION_PARAM);
                break;
            case ACTION_PRINT:
            case ACTION_UPDATE:
                required.add(BILL_ID_PARAM);
                break;
            case ACTION_LOG:
                required.add(PAGINATION_PARAM);
                required.add(OFFSET_PARAM);
                break;
        }
        return required;
    }
}
