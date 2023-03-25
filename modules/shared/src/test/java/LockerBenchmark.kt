import com.astrainteractive.synk.utils.*
import kotlinx.coroutines.*
import org.jetbrains.kotlin.gradle.utils.`is`
import ru.astrainteractive.astralibs.di.Factory
import ru.astrainteractive.astralibs.di.factory
import java.util.*
import kotlin.test.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class LockerBenchmark {
    private val lockerFactory: Factory<Locker<Int>> = factory {
        ConcurrentSkipListSetLocker<Int>()
    }

    @OptIn(ExperimentalTime::class)
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
                while(!isEnded){
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