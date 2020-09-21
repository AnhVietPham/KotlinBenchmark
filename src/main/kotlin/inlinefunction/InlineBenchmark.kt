package inlinefunction

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import java.util.concurrent.TimeUnit

/*
* https://www.programmersought.com/article/4721643238/
* http://tutorials.jenkov.com/java-performance/index.html
* */
fun nonInlineRepeat(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}

inline fun inlineRepeat(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
open class InlineBenchmark {

    @Benchmark
    fun repeatInline() {
        var a = 2L
        inlineRepeat(100_000_000) {
            a += a / 2
        }
    }

    @Benchmark
    fun nonRepeatInline() {
        var a = 2L
        nonInlineRepeat(100_000_000) {
            a += a / 2
        }
    }


}

fun main(){
    val options = OptionsBuilder()
            .include(InlineBenchmark::class.java.simpleName)
            .build()
    Runner(options).run()
}