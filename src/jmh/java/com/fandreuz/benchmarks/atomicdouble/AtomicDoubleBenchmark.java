package com.fandreuz.benchmarks.atomicdouble;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;

@Fork(warmups = 0, value = 2)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Threads(5)
public class AtomicDoubleBenchmark {

    private static final Class[] classes = {VolatileAtomicDouble.class, AtomicReferenceDouble.class};

    @Param({"0", "1"})
    private int classIdx;
    private double current = 0.0;
    private AtomicDouble atomicDouble;

    @Setup(Level.Trial)
    @SuppressWarnings("unchecked")
    public void setup() throws Exception {
        atomicDouble = (AtomicDouble) classes[classIdx].getConstructor().newInstance();
    }

    @Benchmark
    public void writeIntensive() {
        atomicDouble.set(current++);
    }

    @Benchmark
    public Double readAndWrite() {
        for (int i = 0; i < 100; ++i) {
            atomicDouble.set(current++);
        }
        return atomicDouble.getAndReset();
    }
}
