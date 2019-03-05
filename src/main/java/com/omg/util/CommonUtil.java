package com.omg.util;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Author: CYB
 * @Date: 2019/9/25 16:31
 */
public class CommonUtil {
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> {
            if (keyExtractor.apply(t) == null) {
                return Boolean.FALSE;
            } else {
                return seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
            }
        };

    }

    public static String formatList(List<String> list, String delimiter) {
        StringJoiner result = new StringJoiner(delimiter);
        for (String str : list) {
            result.add(str);
        }
        return result.toString();
    }
}
