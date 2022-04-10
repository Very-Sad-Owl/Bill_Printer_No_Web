package ru.clevertec.tasks.olga.util;

public interface Constant {
    //config
    String BASE_PACKAGES_TO_SCAN = "ru.clevertec.tasks.olga";
    String CACHING_ALG_ANNOTATION_PACKAGE = "ru.clevertec.tasks.olga.annotation.CachingAlgorithm";

    //common
    String NODES = "nodes";
    String PAGE = "page";

    //actions
    String ACTION_FIND = "/find";
    String ACTION_LOG = "/log";
    String ACTION_PUT = "/put";
    String ACTION_PATCH = "/patch";
    String ACTION_SAVE = "/save";
    String ACTION_DELETE = "/delete";

    String CACHE_ALG = "cache.algorithm";
    String CACHE_CAPACITY = "cache.capacity";

    String FILENAME_PDF_FORMAT = "%s-%s.pdf";

}
