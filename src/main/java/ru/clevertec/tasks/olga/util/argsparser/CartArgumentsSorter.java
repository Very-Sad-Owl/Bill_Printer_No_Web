package ru.clevertec.tasks.olga.util.argsparser;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;
import ru.clevertec.tasks.olga.model.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.*;

import static ru.clevertec.tasks.olga.util.Constant.*;

public class CartArgumentsSorter implements ArgumentsSorter<CartParamsDTO> {

    public CartParamsDTO retrieveArgs(Map<String, String[]> args){
        if (args.size() == 0) throw new NoRequiredArgsException(
                MessageLocaleService.getMessage("error.no_req_args"));
        CartParamsDTO params = new CartParamsDTO();
        for (Map.Entry<String, String[]> pair : args.entrySet()){
            String key = pair.getKey();
            String[] values = pair.getValue();
            switch (key) {
                case CARD_ID_PARAM:
                    params.setCard_id(Long.parseLong(values[0]));
                    break;
                case CASHIER_ID_PARAM:
                    params.setCashier_id(Long.parseLong(values[0]));
                    break;
                case ACTION_PARAM:
                    params.setAction(values[0]);
                    break;
                case BILL_ID_PARAM:
                    params.setBill_id(Long.parseLong(values[0]));
                    break;
                case PRODUCTS:
                    Map<Long, Integer> goods = new HashMap<>();
                    for (String el : values){
                        String[]idQPairVals = el.split(",");
                        for (String idQPair : idQPairVals) {
                            String[] vals = idQPair.split("-");
                            goods.put(Long.parseLong(vals[0]), Integer.parseInt(vals[1]));
                            params.setGoods(goods);
                        }
                    }
                    break;
            }
        }
        if (!checkRequiredArgs(args.keySet(), params.getAction())){
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
                required.add(CARD_ID_PARAM);
                required.add(CASHIER_ID_PARAM);
                required.add(ACTION_PARAM);
                break;
            case ACTION_FIND_BY_ID:
                required.add(BILL_ID_PARAM);
                break;
        }
        return required;
    }
}
