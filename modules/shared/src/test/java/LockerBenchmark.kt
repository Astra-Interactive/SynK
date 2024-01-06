import com.astrainteractive.synk.locker.ConcurrentHashMapLocker
import com.astrainteractive.synk.locker.ConcurrentSkipListSetLocker
import com.astrainteractive.synk.locker.HashSetLocker
import com.astrainteractive.synk.locker.Locker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.time.measureTime

class LockerBenchmark {

    private fun measureAndAssertLocker(locker: Locker<Int>) {
        measureTime {
            var isEnded = false
            runBlocking {
                GlobalScope.launch(Dispatchers.IO) {
                    val range = IntRange(0, 100000)
                    range.forEach {
                        async {
                            locker.lock(it)
                            locker.lock(it - 1)
                        }
                    }
                    range.map {
                        async {
                            locker.isLocked(it)
                        }
                    }.awaitAll().any { false }.also {
                        assert(!it)
                    }
                    isEnded = true
                }
                while (!isEnded) {
                    delay(100)
                }
            }
        }.also {
            println("Millis: ${it.inWholeMilliseconds / 1000.0}; Locker: $locker")
        }
        locker.clear()
    }

    @Test
    fun `Measure locker time`() {
        measureAndAssertLocker(ConcurrentHashMapLocker())
        measureAndAssertLocker(ConcurrentSkipListSetLocker())
        measureAndAssertLocker(HashSetLocker())
    }
}
