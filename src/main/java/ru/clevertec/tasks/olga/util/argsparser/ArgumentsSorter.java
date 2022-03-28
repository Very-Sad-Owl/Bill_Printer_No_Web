package ru.clevertec.tasks.olga.util.argsparser;

import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;
import ru.clevertec.tasks.olga.dto.AbstractDto;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.Map;

import static ru.clevertec.tasks.olga.util.Constant.*;

public abstract class ArgumentsSorter<T extends AbstractDto> implements Sorter {
    public abstract T retrieveArgs(Map<String, String[]> args);

    public void retrieveBaseArgs(Map<String, String[]> args, T dest){
        if (args.size() == 0) throw new NoRequiredArgsException(
                MessageLocaleService.getMessage("error.no_req_args"));
        for (Map.Entry<String, String[]> pair : args.entrySet()){
            String key = pair.getKey();
            String[] values = pair.getValue();
            switch (key) {
                case OFFSET_PARAM:
                    dest.nodesPerPage = Integer.parseInt(values[0]);
                    break;
                case PAGINATION_PARAM:
                    dest.offset = Integer.parseInt(values[0]);
                    break;
            }
        }
    }
}
