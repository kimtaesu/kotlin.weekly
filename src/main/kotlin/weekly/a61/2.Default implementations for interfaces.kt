package weekly.a61

interface View {
    fun showToast(text: String)
}

interface DefaultView : View {
    val context: String

    override fun showToast(text: String) {
        println("${context} ${text}")
    }
}

class BaseActivity : DefaultView {
    override val context: String
        get() = "BaseActivity"
}

fun main(args: Array<String>) {
    val b = BaseActivity()
    b.showToast("Test")
}