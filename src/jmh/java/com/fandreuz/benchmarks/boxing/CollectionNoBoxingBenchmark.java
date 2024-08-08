package com.fandreuz.benchmarks.boxing;

import java.util.concurrent.TimeUnit;
import org.eclipse.collections.api.factory.primitive.ObjectIntMaps;
import org.eclipse.collections.api.map.primitive.MutableObjectIntMap;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class CollectionNoBoxingBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState extends Utils.AbstractBenchmarkState {
        public MutableObjectIntMap<String> map = ObjectIntMaps.mutable.empty();

        public BenchmarkState() {
            Utils.STRINGS.forEach(s -> map.put(s, s.hashCode()));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int testNoBoxingMap(BenchmarkState state) {
        return state.map.get(Utils.STRINGS.get(state.getIndexAndIncrement()));
    }

}
