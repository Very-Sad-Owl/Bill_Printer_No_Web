package ru.clevertec.tasks.olga.util.argsparser;

import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.dto.RequestParamsDto;
import ru.clevertec.tasks.olga.exception.handeled.NoRequiredArgsExceptionHandled;
import ru.clevertec.tasks.olga.dto.AbstractDto;
import ru.clevertec.tasks.olga.util.resourceprovider.MessageLocaleService;

import java.util.Map;

import static ru.clevertec.tasks.olga.util.Constant.*;

@Component
public abstract class ArgumentsSorter<T extends AbstractDto> implements Sorter<T> {
    public abstract T retrieveArgsFromMap(Map<String, String> args, RequestParamsDto requestParams);
}
