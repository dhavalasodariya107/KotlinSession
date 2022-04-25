import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

abstract class Car {
    suspend fun build(currentCar: String) {
        println("👇👇👇👇👇👇👇👇👇👇👇")
        println("$currentCar: Preparing body...")
        val bodyDuration = measureTimeMillis { prepareBody() }
        println("$currentCar: Body prepared(${bodyDuration.toSecondUnit()})")
        println()

        println("👇👇👇👇👇👇👇👇👇👇👇")
        println("$currentCar: Painting body...")
        val paintDuration = measureTimeMillis { paint() }
        println("$currentCar: Painting done(${paintDuration.toSecondUnit()})")
        println()

        println("👇👇👇👇👇👇👇👇👇👇👇")
        println("$currentCar: Installing drive train...")
        val dtDuration = measureTimeMillis { installDriveTrain() }
        println("$currentCar: Drive train installed(${dtDuration.toSecondUnit()})")
        println()

        println("👇👇👇👇👇👇👇👇👇👇👇")
        println("$currentCar: Installing batteries...")
        val batteriesDuration = measureTimeMillis { installBatteries() }
        println("$currentCar: Batteries installed(${batteriesDuration.toSecondUnit()})")
        println()

        println("👇👇👇👇👇👇👇👇👇👇👇")

        println("$currentCar: Installing interiors...")
        val interiorsDuration = measureTimeMillis { addInterior() }
        println("$currentCar: Interior installed(${interiorsDuration.toSecondUnit()})")
    }

    protected abstract suspend fun prepareBody()
    protected abstract suspend fun paint()
    protected abstract suspend fun installDriveTrain()
    protected abstract suspend fun installBatteries()
    protected abstract suspend fun addInterior()
}

class Tesla : Car() {

    @OptIn(ExperimentalTime::class)
    suspend fun build(numberOfCars: Int, onComplete: (Pair<Long, Long>) -> Unit) {
        val arrayOfMillis = arrayListOf<Long>()
        val totalDuration = measureTimeMillis {
            coroutineScope {
                val job = launch {
                    repeat((1..numberOfCars).count()) {
                        launch {
                            val timeTaken = prepareCar(it, numberOfCars)
                            arrayOfMillis.add(timeTaken)
                        }
                    }
                }
                job.join()
            }
        }

//        val singleCarDuration = arrayOfMillis.sum() / arrayOfMillis.count().toLong()
        val singleCarDuration = arrayOfMillis.average().toLong()
        onComplete.invoke(Pair(totalDuration, (singleCarDuration)))
    }

    private suspend fun prepareCar(currentCar: Int, numberOfCars: Int): Long {
        return measureTimeMillis {
            super.build("Car ${currentCar + 1}")
            println("${currentCar + 1}/$numberOfCars ready for delivery")
            println("🚗".repeat(currentCar + 1))
            println()
        }
    }

    override suspend fun prepareBody() {
        delay(5.toSecond())
    }

    override suspend fun paint() {
        delay(5.toSecond())
    }

    override suspend fun installDriveTrain() {
        delay(10.toSecond())
    }

    override suspend fun installBatteries() {
        delay(15.toSecond())
    }

    override suspend fun addInterior() {
        delay(15.toSecond())
    }
}

fun Int.toSecond(): Long {
    return this * 1000L
}

fun Long.toSecondUnit(): String {
    return "${this / 1000} seconds"
}
