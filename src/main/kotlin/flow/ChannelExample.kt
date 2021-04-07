package flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main(){
    val scope = CoroutineScope(Dispatchers.Default)
    val channel = sample()
    scope.launch {
        for (y in channel){
            println("RECEIVING ITEMS $y")
        }
    }
    Thread.sleep(15000L)
}


fun sample():ReceiveChannel<Int>{
    val basket = Channel<Int>()

    /**
     * This has to be launched in separate coroutine and func cannot be marked as suspend
     * reason being everything else will be executed sequentially channel will send and then
     * we return channel for receive which might be of no use.
     */
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        for (x in 1..5){
            //after delay 1.5 sec channel will start sending data
            delay(1000)
            println("SENDING ITEMS $x")
            basket.send(x)
        }
        basket.close()
    }
    return basket
}