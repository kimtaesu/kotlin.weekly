package weekly.a60

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

class SpekPublishSubjectTest : Spek({

    val distancesProvider: DistancesProvider = mock()
    val unitProvider: UnitProvider = mock()
    val tested = DistancesUseCase(distancesProvider, unitProvider)

    var distanceSub: PublishSubject<List<Int>> = PublishSubject.create()
    var unitSub: PublishSubject<Int> = PublishSubject.create()

    var testSubscriber = TestObserver<Int>()

    given("a GPS system") {

        beforeEachTest {
            distanceSub = PublishSubject.create()
            unitSub = PublishSubject.create()
            whenever(distancesProvider.distances()).thenReturn(distanceSub)
            whenever(unitProvider.unit()).thenReturn(unitSub)

            testSubscriber = tested.distances().test()
        }
        on("a distance update of 30 km") {
            distanceSub.onNext(listOf(30, 19)) // [30km, 19miles]
            unitSub.onNext(0) // km

            it("should emit the value 30") {
                testSubscriber.assertValue(30)
            }
            it("should not complete") {
                testSubscriber.assertNotComplete()
            }
            it("should not throw errors") {
                testSubscriber.assertNoErrors()
            }
        }
        context("distance update of 30 km") {
            beforeEachTest {
                distanceSub.onNext(listOf(30, 19))
                unitSub.onNext(0)
            }

            it("should emit the value 30") {
                testSubscriber.assertValue(30)
            }

            context("unit update to miles") {
                beforeEachTest {
                    unitSub.onNext(1)
                }

                it("should emit 19 as second value") {
                    testSubscriber.assertValues(30, 19)
                }

                context("no more units") {
                    beforeEachTest {
                        unitSub.onComplete()
                    }

                    it("should not complete") {
                        testSubscriber.assertNotComplete()
                    }
                }
            }
        }
        afterEachTest {
            reset(distancesProvider)
            reset(unitProvider)
        }
    }
})

class DistancesProvider {
    fun distances(): Observable<List<Int>> {
        return Observable.just(listOf())
    }

}

class UnitProvider {

    fun unit(): Observable<Int> {
        return Observable.just(0)
    }
}

class DistancesUseCase(val distancesProvider: DistancesProvider,
                       val unitProvider: UnitProvider) {
    fun distances(): Observable<Int> {
        return Observable.combineLatest(
                distancesProvider.distances(),
                unitProvider.unit(),
                BiFunction({ distances, unit -> distances[unit] })
        )
    }
}