package weekly.a60

import io.reactivex.Observable
import io.reactivex.functions.BiFunction


class DistancesProvider {
    fun distances(): Observable<List<Int>> {
        return Observable.just(listOf(30, 19))
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


fun main(args: Array<String>) {

}