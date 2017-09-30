package weekly.a60

//open class Base
//class Extended: Base()
//fun Base.foo() = "Base!"
//fun Extended.foo() = "Extended!"


//fun main(args: Array<String>) {
//    val instance: Base = Extended()
//    val instance2 = Extended()
//    println(instance.foo())
//    println(instance2.foo())
//}

//open class Base
//class Extended : Base()
//open class A {
//    open fun Base.foo() {
//        println("Base.foo in A")
//    }
//    open fun Extended.foo() {
//        println("Extended.foo in A")
//    }
//    fun caller(base: Base) {
//        base.foo()
//    }
//}
//class B : A() {
//    override fun Base.foo() {
//        println("Base.foo in B")
//    }
//    override fun Extended.foo() {
//        println("Extended.foo in B")
//    }
//}
//
//fun main(args: Array<String>) {
//    A().caller(Base())   // prints "Base.foo in A"
//    B().caller(Base())  // prints "Base.foo in B" - dispatch receiver is resolved dynamically
//    A().caller(Extended())  // prints "Base.foo in A" - extension receiver is resolved statically
//}

class Person {
    fun walk() {
        println("walk in Person")
    }
    fun Person.walk() {
        println("Person.walk in Person")
    }
}
fun Person.walk() {
    println("Person.walk")
}

fun main(args: Array<String>) {
    val p : Person = Person()
    p.walk()
}

//class Hero(
//        val name: String,
//        protected val surname: String,
//        private val nickname: String
//)
//fun Hero.getIdentity() = "$name $surname, $nickname"