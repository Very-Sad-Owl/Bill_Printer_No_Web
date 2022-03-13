package ru.clevertec.tasks.olga.util.argsparser;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;
import ru.clevertec.tasks.olga.model.ParamsDTO;
import ru.clevertec.tasks.olga.util.MessageLocaleService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.clevertec.tasks.olga.util.Constant.*;

public class ArgumentsSorter {

    public ParamsDTO retrieveArgs(String[] args){
        if (args.length == 0) throw new NoRequiredArgsException(MessageLocaleService.getMessage("error.no_req_args"));
        ParamsDTO params = new ParamsDTO();
        String[] pair;
        Map<Long, Integer> goods = new HashMap<>();
        for (String arg : args){
            pair = arg.split("-");
            switch (pair[0]) {
                case CARD_ID_PARAM:
                    params.setCard_id(Long.parseLong(pair[1]));
                    break;
                case CASHIER_ID_PARAM:
                    params.setCashier_id(Long.parseLong(pair[1]));
                    break;
                case PATH_PARAM:
                    params.setDataPath(pair[1]);
                    break;
                case ACTION_PARAM:
                    params.setAction(pair[1]);
                    break;
                case BILL_ID_PARAM:
                    params.setBill_id(Long.parseLong(pair[1]));
                    break;
                default:
                    goods.put(Long.parseLong(pair[0]), Integer.parseInt(pair[1]));
                    break;
            }
        }
        params.setGoods(goods);
        if (params.getAction() != null && !checkRequiredArgs(args, params.getAction())){
            throw new NoRequiredArgsException("error.no_req_args");
        }
        return params;
    }


    private boolean checkRequiredArgs(String[] args, String action) {
        List<String> required = formCmdRequiredArgs(action);
        if (required.isEmpty()) return true;
        boolean flag = false;
        for (String key : required) {
            for (String arg : args) {
                flag = key.equals(arg.split("-")[0]);
                if (flag) break;
            }
            if (!flag) return false;
        }
        return flag;
    }


    private List<String> formCmdRequiredArgs(String action){
        List<String> required = new ArrayListImpl<>();
        switch (action){
            case ACTION_PRINT:
                required.add(CARD_ID_PARAM);
                required.add(CASHIER_ID_PARAM);
                required.add(PATH_PARAM);
                required.add(ACTION_PARAM);
                break;
            case ACTION_FIND_BY_ID:
                required.add(BILL_ID_PARAM);
                required.add(PATH_PARAM);
                break;
            case ACTION_LOG:
                required.add(PATH_PARAM);
        }
        return required;
    }
}
