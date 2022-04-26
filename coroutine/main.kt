import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    runBlocking {
        val tesla = Tesla()
        val numberOfCars = 10_00_000
        tesla.build(numberOfCars) { result ->
            println("---------------------------")
            println()
            println(
                "Total time taken to build ${result.totalCars} cars: ${result.totalTime} milli-seconds"
            )
            println(
                "Single car took: ${result.singleCardBuildTime} milli-seconds"
            )
            println()
            println("---------------------------")
        }
    }
}
