fun main(args: Array<String>) {

}

abstract class Platform(input: String) {
    private val regex = Regex("[^A-Za-z ]")
    val filteredInput =
        regex.replace(input, "-")
            .split("-")
            .joinToString("") {
                it.replaceFirstChar { firstChar -> firstChar.uppercaseChar() }
            }

    abstract fun getLayoutName(): String
    abstract fun getControllerName(): String
    abstract fun getModelName(): String

    fun String.toNormaliseName(): String {
        val output = this
            .split(" ")
            .joinToString("") {
                it.replaceFirstChar { firstChar -> firstChar.titlecaseChar() }
            }
        return output
    }
}

class Android(input: String) : Platform(input) {
    override fun getLayoutName(): String {
        val output = filteredInput
            .split(" ")
            .joinToString("_") {
                it.lowercase()
            }
        return "activity_$output"
    }

    override fun getControllerName(): String {
        return "${filteredInput.toNormaliseName()}Activity"
    }

    override fun getModelName(): String {
        return "${filteredInput.toNormaliseName()}Model"
    }
}

class Flutter(input: String) : Platform(input) {
    override fun getLayoutName(): String {
        val output = filteredInput
            .split(" ")
            .joinToString("_") {
                it.lowercase()
            }
        return output
    }

    override fun getControllerName(): String {
        val output = if (filteredInput.endsWith(" screen", true)) {
            filteredInput.replace(" screen", "")
        } else {
            filteredInput
        }.split(" ")
            .joinToString("_") { it.lowercase() }
        return "${output}_controller"
    }

    override fun getModelName(): String {
        val output = if (filteredInput.endsWith(" screen", true)) {
            filteredInput.replace(" screen", "")
        } else {
            filteredInput
        }.split(" ")
            .joinToString("_") {
                it.lowercase()
            }
        return "${output}_model"
    }
}

class IOS(input: String) : Platform(input) {
    override fun getLayoutName(): String {
        return "iOS doesn't support layouts."
    }

    override fun getControllerName(): String {
        return "iOS doesn't support controllers."
    }

    fun getViewName(): String {
        return "${filteredInput.toNormaliseName()}View"
    }

    override fun getModelName(): String {
        return "${filteredInput.toNormaliseName()}ViewModel"
    }
}
