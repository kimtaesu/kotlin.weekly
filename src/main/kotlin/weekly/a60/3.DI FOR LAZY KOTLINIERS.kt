package weekly.a60

interface ImageLoader {
    fun loadImage(url: String, view: String)
}

class App() {
    companion object {
        var app: App? = null

        fun getConfig() = hashMapOf(
                "myName" to "Jan Kotlin",
                "imageLoader" to object : ImageLoader {
                    override fun loadImage(url: String, view: String) {
                        println(view)
                    }
                }
        )
    }
}

open interface Config {
    val properties: Map<String, Any?>
}

class ConfigDelegate : Config {
    override val properties: Map<String, Any?> by lazy {
        App.getConfig()
    }
}

class MainActivity : Config by ConfigDelegate() {

    private val myName: String by properties
    private val imageLoader: ImageLoader by properties


    fun onCreate() {
        println("${myName}")
        imageLoader.loadImage("http://www.ourhenhouse.org/wp-content/uploads/2013/01/kitten_lily.jpg", "ImageView")
    }
}

fun main(args: Array<String>) {
    val mainActivity = MainActivity()
    mainActivity.onCreate()
}