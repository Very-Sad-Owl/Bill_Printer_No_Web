package ru.clevertec.tasks.olga.util.argsparser;

import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.dto.RequestParamsDto;
import ru.clevertec.tasks.olga.exception.serviceexc.NoRequiredArgsExceptionHandled;
import ru.clevertec.tasks.olga.dto.AbstractDto;
import ru.clevertec.tasks.olga.util.resourceprovider.MessageLocaleService;

import java.util.Map;

import static ru.clevertec.tasks.olga.util.Constant.*;

@Component
public abstract class ArgumentsSorter<T extends AbstractDto> implements Sorter<T> {
    public abstract T retrieveArgsFromMap(Map<String, String> args, RequestParamsDto requestParams);

    public static RequestParamsDto retrieveBaseArgs(Map<String, String> args){
        RequestParamsDto dest = new RequestParamsDto();
        if (args.size() == 0) throw new NoRequiredArgsExceptionHandled(
                MessageLocaleService.getMessage("error.no_req_args"));
        for (Map.Entry<String, String> pair : args.entrySet()){
            String key = pair.getKey();
            String value = pair.getValue();
            switch (key) {
                case CATEGORY:
                    dest.category = value;
                    break;
                case ACTION_PARAM:
                    dest.action = value;
                    break;
                case OFFSET_PARAM:
                    dest.nodesPerPage = Integer.parseInt(value);
                    break;
                case PAGINATION_PARAM:
                    dest.offset = Integer.parseInt(value);
                    break;
                case LANGUAGE:
                    dest.locale = value;
            }
        }
        return dest;
    }
}
