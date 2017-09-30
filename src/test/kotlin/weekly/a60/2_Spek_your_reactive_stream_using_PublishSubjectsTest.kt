package weekly.a60

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.jetbrains.spek.api.Spek
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
        }
        afterEachTest {
            reset(distancesProvider)
            reset(unitProvider)
        }
    }
})