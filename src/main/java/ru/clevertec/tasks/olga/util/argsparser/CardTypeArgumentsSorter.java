package ru.clevertec.tasks.olga.util.argsparser;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.dto.CardTypeDto;
import ru.clevertec.tasks.olga.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.dto.RequestParamsDto;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;

import java.util.*;

import static ru.clevertec.tasks.olga.util.Constant.*;

public class CardTypeArgumentsSorter extends ArgumentsSorter<CardTypeDto> {

    @Override
    public CardTypeDto retrieveArgsFromMap(Map<String, String[]> args, RequestParamsDto requestParams){
        CardTypeDto params = new CardTypeDto();
        for (Map.Entry<String, String[]> pair : args.entrySet()){
            String key = pair.getKey();
            String[] values = pair.getValue();
            switch (key) {
                case CARD_TYPE_ID:
                    params.id = Long.parseLong(values[0]);
                    break;
                case CARD_TYPE_TITLE:
                    params.title = values[0];
                    break;
                case CARD_TYPE_DISCOUNT_VAL:
                    params.discountVal = Double.parseDouble(values[0]);
                    break;
            }
        }
        if (!checkRequiredArgs(args.keySet(), requestParams.action)){
            throw new NoRequiredArgsException();
        }
        return params;
    }

//    @Override
    public CartParamsDTO retrieveArgsFromCollection(Iterator<String> it) {
        return null;
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
                required.add(CARD_TYPE_ID);
                required.add(CARD_TYPE_TITLE);
                required.add(CARD_TYPE_DISCOUNT_VAL);
                break;
            case ACTION_PRINT:
            case ACTION_UPDATE:
                required.add(CARD_TYPE_ID);
                break;
        }
        return required;
    }
}
