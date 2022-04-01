package ru.clevertec.tasks.olga.util.argsparser;

import ru.clevertec.tasks.olga.dto.RequestParamsDto;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;
import ru.clevertec.tasks.olga.dto.AbstractDto;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.Map;

import static ru.clevertec.tasks.olga.util.Constant.*;

public abstract class ArgumentsSorter<T extends AbstractDto> implements Sorter<T> {
    public abstract T retrieveArgsFromMap(Map<String, String[]> args, RequestParamsDto requestParams);

    public static RequestParamsDto retrieveBaseArgs(Map<String, String[]> args){
        RequestParamsDto dest = new RequestParamsDto();
        if (args.size() == 0) throw new NoRequiredArgsException(
                MessageLocaleService.getMessage("error.no_req_args"));
        for (Map.Entry<String, String[]> pair : args.entrySet()){
            String key = pair.getKey();
            String[] values = pair.getValue();
            switch (key) {
                case CATEGORY:
                    dest.category = values[0];
                    break;
                case ACTION_PARAM:
                    dest.action = values[0];
                    break;
                case OFFSET_PARAM:
                    dest.nodesPerPage = Integer.parseInt(values[0]);
                    break;
                case PAGINATION_PARAM:
                    dest.offset = Integer.parseInt(values[0]);
                    break;
            }
        }
        return dest;
    }
}
