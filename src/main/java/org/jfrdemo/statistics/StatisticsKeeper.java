package org.jfrdemo.statistics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class StatisticsKeeper {

    private static final Map<String, Long> STATISTICS_STORAGE = new ConcurrentHashMap<>();

    private StatisticsKeeper() {
    }

    public static void setMaxVal(final String key, final Long value) {
        STATISTICS_STORAGE.compute(key, (k, v)->{
            if (v == null || value > v) {
                return value;
            } else {
                return v;
            }
        });
    }
}
