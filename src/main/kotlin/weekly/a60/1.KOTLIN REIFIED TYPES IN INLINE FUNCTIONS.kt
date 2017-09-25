package weekly.a60



//fun <T> getTNameOriginal(t: T): String {
//    Compile Error
//    return T::class.java.name
//}


fun <T> getTNameWithOutReified(t: Class<T>): String {
    return t.name
}

inline fun <reified T> getTName(t: T): String {
    return T::class.java.name
}




fun main(args: Array<String>) {
println(getTName(String))
}