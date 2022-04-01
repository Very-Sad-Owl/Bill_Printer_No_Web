package ru.clevertec.tasks.olga.util.argsparser;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.dto.RequestParamsDto;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.clevertec.tasks.olga.util.Constant.*;

public class CashierArgumentsSorter extends ArgumentsSorter<CashierParamsDTO> {

    @Override
    public CashierParamsDTO retrieveArgsFromMap(Map<String, String[]> args, RequestParamsDto requestParams){
        CashierParamsDTO params = new CashierParamsDTO();
        for (Map.Entry<String, String[]> pair : args.entrySet()){
            String key = pair.getKey();
            String[] values = pair.getValue();
            switch (key) {
                case CASHIER_ID_PARAM:
                    params.id = Long.parseLong(values[0]);
                    break;
                case CASHIER_NAME_PARAM:
                    params.name = values[0];
                    break;
                case CASHIER_SURNAME_PARAM:
                    params.surname = values[0];
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
                required.add(CASHIER_ID_PARAM);
                break;
            case ACTION_SAVE:
                required.add(CASHIER_NAME_PARAM);
                required.add(CASHIER_SURNAME_PARAM);
                break;
        }
        return required;
    }
}
