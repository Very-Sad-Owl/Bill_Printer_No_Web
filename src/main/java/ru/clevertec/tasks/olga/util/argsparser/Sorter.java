package ru.clevertec.tasks.olga.util.argsparser;

import ru.clevertec.tasks.olga.dto.AbstractDto;
import ru.clevertec.tasks.olga.dto.RequestParamsDto;

import java.util.Iterator;
import java.util.Map;

public interface Sorter <T extends AbstractDto> {
    T retrieveArgsFromMap(Map<String, String[]> args, RequestParamsDto requestParams);
//    T retrieveArgsFromCollection(Iterator<String> it);
}
