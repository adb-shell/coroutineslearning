package flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun main(){
    val flow = sampleFlow()
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        //until the collect is called flow wont emit.
        flow.collect {
            println("The Flow value collected is: $it")
        }
    }
    Thread.sleep(15000)
}

fun sampleFlow():Flow<Int> = flow {
    emit(1)
    emit(2)
    emit(3)
}