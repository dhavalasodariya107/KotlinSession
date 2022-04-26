import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime


data class Result(
    val totalTime: Long,
    val singleCardBuildTime: Long,
    val totalCars: Int,
)

class Tesla {

    @OptIn(ExperimentalTime::class)
    suspend fun build(numberOfCars: Int, onComplete: (Result) -> Unit) {
        var carBuild = 0
        var startTime = 0L
        var endTime = 0L
        val arrayOfTime = mutableListOf<Long>()
        coroutineScope {
            val buildJob = launch {
                startTime = System.currentTimeMillis()
                println("Building cars...")
                repeat((1..numberOfCars).count()) {
                    launch {
                        arrayOfTime.add(measureTimeMillis {
                            carBuild = prepareCar(it)
                        })
                    }
                }
            }

            val timerTask = launch {
                var index = 1
                while (isActive) {
                    delay(1000)
                    println("$index")
                    index++
                }
            }

            val cancellationJob = launch {
                delay(27.seconds.inWholeMilliseconds)
                buildJob.cancel("❌❌❌❌❌❌❌ TIME UP ❌❌❌❌❌❌❌")
            }

            buildJob.invokeOnCompletion {
                cancellationJob.cancel()
                timerTask.cancel()
                it?.message?.let { msg -> println(msg) }
                endTime = System.currentTimeMillis()
                val totalTime = endTime - startTime
                onComplete.invoke(
                    Result(
                        totalTime,
                        arrayOfTime.average().toLong(),
                        carBuild
                    )
                )
            }
        }
    }

    private suspend fun prepareCar(currentCar: Int): Int {
        delay(5.toSecond())
        delay(5.toSecond())
        delay(10.toSecond())
        delay(15.toSecond())
        delay(15.toSecond())
        return currentCar + 1
    }
}

fun Int.toSecond(): Long {
    return this * 1000L / 2
}

fun Long.toSecondUnit(): String {
    return "${this / 1000} seconds"
}
