package com.fandreuz.benchmarks.boxing;

import java.util.List;
import java.util.stream.IntStream;
import org.apache.commons.lang3.RandomStringUtils;

public class Utils {

     public static final int STRINGS_COUNT = 1_000_000;
     public static final int STRINGS_SIZE = 50;
     public static final List<String> STRINGS = IntStream.range(0, STRINGS_COUNT) //
             .mapToObj(ignored -> RandomStringUtils.randomAlphanumeric(STRINGS_SIZE)) //
             .toList();

    public abstract static class AbstractBenchmarkState {
        private int currentIndex;

        public int getIndexAndIncrement() {
            int tmp = currentIndex;
            currentIndex = (currentIndex+1) % Utils.STRINGS.size();
            return tmp;
        }
    }

}
