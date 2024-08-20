package com.fandreuz.benchmarks.stream;

import com.fandreuz.benchmarks.boxing.Utils;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class StreamHashCodeBenchmark {

    private static final int N = 10_000_000;

    @State(Scope.Benchmark)
    public static class BenchmarkState extends Utils.AbstractBenchmarkState {
        public Set<String> strings = IntStream.range(0, N) //
                .mapToObj(i -> RandomStringUtils.random(50)) //
                .collect(Collectors.toUnmodifiableSet());
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int testStream(BenchmarkState state) {
        return state.strings.stream().mapToInt(String::hashCode).sum();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int testParallelStream2(BenchmarkState state) throws Exception {
        return parallel(state, 2);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int testParallelStream4(BenchmarkState state) throws Exception {
        return parallel(state, 4);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int testParallelStream16(BenchmarkState state) throws Exception {
        return parallel(state, 16);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int testParallelStream32(BenchmarkState state) throws Exception {
        return parallel(state, 32);
    }

    private static int parallel(BenchmarkState state, int threadsCount) throws Exception {
        try (var pool = new ForkJoinPool(2)) {
            return pool.submit(() -> state.strings.parallelStream().mapToInt(String::hashCode).sum()).get();
        }
    }

}
