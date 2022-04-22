import org.junit.Test
import kotlin.test.assertEquals

class PlayGround {

    /**
     * Test sequence issue : https://stackoverflow.com/a/3693706/11555065
     */

    @Test
    fun testPlayground() {
//        val input = "Splash screen"
        val input = "123Test\$%"
//        val input = "@#test;'"

        val platforms = mutableListOf(
            Android(input),
            Flutter(input),
            IOS(input)
        )

        platforms.forEach {
            println("----------------${it.javaClass.simpleName}----------------")
            println()
            if (it is IOS) {
                val viewResult = it.getViewName()
                val modelResult = it.getModelName()
                println("View Name: $viewResult")
                println("ViewModel Name: $modelResult")
            } else {
                val layoutResult = it.getLayoutName()
                val controllerResult = it.getControllerName()
                val modelResult = it.getModelName()
                println("Layout Name: $layoutResult")
                println("Controller Name: $controllerResult")
                println("Model Name: $modelResult")
            }

            println()
            println("---------------------------------------------------")
        }
    }

    @Test
    fun testAndroidNameGenerator() {
        val inputs = listOf(
            "Splash screen",
            "123Test\$%",
            "@#test;'"
        )
        val expectedOutput = listOf(
            listOf("activity_splash_screen", "SplashScreenActivity", "SplashScreenModel"),
            listOf("activity_test", "TestActivity", "TestModel"),
            listOf("activity_test", "TestActivity", "TestModel"),
        )
        inputs.forEachIndexed { index, input ->
            val platform = Android(input)
            val layoutName = platform.getLayoutName()
            val controllerName = platform.getControllerName()
            val modelName = platform.getModelName()
            val expectedResult = expectedOutput[index]
            assertEquals(expectedResult[0], layoutName)
            assertEquals(expectedResult[1], controllerName)
            assertEquals(expectedResult[2], modelName)
        }
    }

    @Test
    fun testFlutterNameGenerator() {
        val inputs = listOf(
            "Splash screen",
            "123Test\$%",
            "@#test;'"
        )
        val expectedOutput = listOf(
            listOf("splash_screen", "splash_controller", "splash_model"),
            listOf("test", "test_controller", "test_model"),
            listOf("test", "test_controller", "test_model")
        )
        inputs.forEachIndexed { index, input ->
            val platform = Flutter(input)
            val layoutName = platform.getLayoutName()
            val controllerName = platform.getControllerName()
            val modelName = platform.getModelName()
            val expectedResult = expectedOutput[index]
            assertEquals(expectedResult[0], layoutName)
            assertEquals(expectedResult[1], controllerName)
            assertEquals(expectedResult[2], modelName)
        }
    }

    @Test
    fun testIOSNameGenerator() {
        val inputs = listOf(
            "Splash screen",
            "123Test\$%",
            "@#test;'"
        )
        val expectedOutput = listOf(
            listOf("SplashScreenView", "SplashScreenViewModel"),
            listOf("TestView", "TestViewModel"),
            listOf("TestView", "TestViewModel"),
        )
        inputs.forEachIndexed { index, input ->
            val platform = IOS(input)
            val viewName = platform.getViewName()
            val modelName = platform.getModelName()
            val expectedResult = expectedOutput[index]
            assertEquals(expectedResult[0], viewName)
            assertEquals(expectedResult[1], modelName)
        }
    }
}
