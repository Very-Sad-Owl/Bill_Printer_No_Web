package ru.clevertec.tasks.olga.util.argsparser;

import org.springframework.stereotype.Component;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.dto.CardParamsDTO;
import ru.clevertec.tasks.olga.dto.RequestParamsDto;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsExceptionCustom;

import java.util.*;

import static ru.clevertec.tasks.olga.util.Constant.*;

@Component
public class DiscountCardArgumentsSorter extends ArgumentsSorter<CardParamsDTO> {

    @Override
    public CardParamsDTO retrieveArgsFromMap(Map<String, String[]> args, RequestParamsDto requestParams){
        CardParamsDTO params = new CardParamsDTO();
        for (Map.Entry<String, String[]> pair : args.entrySet()){
            String key = pair.getKey();
            String[] values = pair.getValue();
            switch (key) {
                case CARD_ID_PARAM:
                    params.id = Long.parseLong(values[0]);
                    break;
                case CARD_BIRTHDAY:
                    params.birthday = values[0];
                    break;
                case CART_CARD_TYPE_ID:
                    params.discountId = Long.parseLong(values[0]);
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
        if (required.isEmpty()) return true;
        List<String> listArgs = new ArrayList<>(args);
        return listArgs.containsAll(required);
    }


    private List<String> formCmdRequiredArgs(String action){
        List<String> required = new ArrayListImpl<>();
        switch (action){
            case ACTION_SAVE:
                required.add(CARD_ID_PARAM);
                required.add(CARD_BIRTHDAY);
                required.add(CART_CARD_TYPE_ID);
                break;
            case ACTION_PRINT:
            case ACTION_UPDATE:
            case ACTION_DELETE:
                required.add(CARD_ID_PARAM);
                break;
        }
        return required;
    }
}
