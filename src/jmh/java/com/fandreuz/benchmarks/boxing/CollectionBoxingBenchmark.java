package com.fandreuz.benchmarks.boxing;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.eclipse.collections.api.factory.primitive.ObjectIntMaps;
import org.eclipse.collections.api.map.primitive.MutableObjectIntMap;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class CollectionBoxingBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState extends Utils.AbstractBenchmarkState {
        public Map<String, Integer> map = Utils.STRINGS.stream() //
                .collect(Collectors.toMap( //
                        Function.identity(), //
                        String::hashCode, //
                        (integer, integer2) -> integer, //
                        HashMap::new) //
                );

        public MutableObjectIntMap<String> primitiveMap = ObjectIntMaps.mutable.empty();

        public BenchmarkState() {
            Utils.STRINGS.forEach(s -> primitiveMap.put(s, s.hashCode()));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int testStandardHashMap(BenchmarkState state) {
        return state.map.get(Utils.STRINGS.get(state.getIndexAndIncrement()));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int testPrimitiveMap(BenchmarkState state) {
        return state.primitiveMap.get(Utils.STRINGS.get(state.getIndexAndIncrement()));
    }

}
