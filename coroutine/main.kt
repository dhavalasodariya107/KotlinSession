import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    runBlocking {
        val tesla = Tesla()
        tesla.build(10) { duration ->
            println("---------------------------")
            println()
            println(
                "Total time taken: ${duration.first} milli-seconds"
            )
            println(
                "Single car took: ${duration.second} milli-seconds"
            )
            println()
            println("---------------------------")
        }
    }
}
