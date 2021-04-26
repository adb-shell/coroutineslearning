package flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main(){
    val flow = sampleFlow()
    val flow1 = sampleFlow1()
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        //until the collect is called flow wont emit.
        flow.collect {
            println("The Flow value collected is: $it")
        }

        //Print in the
        flow1.collect{
            print("Collecting context: ${this.coroutineContext}")
            println("The Flow value collected is: $it")
        }
    }
    Thread.sleep(35000)
}

fun sampleFlow():Flow<Int> = flow {
    emit(1)
    emit(2)
    emit(3)
}

/**
 * The emit is happening in the dispatcher.default thread.
 */
fun sampleFlow1():Flow<String> = flow{
    println("Emitting context: ${currentCoroutineContext()}")
    emit("hello")
    kotlinx.coroutines.delay(500)
    emit("world")
}.flowOn(Dispatchers.IO)