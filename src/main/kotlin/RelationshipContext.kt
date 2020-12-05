import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun main(){
    val coroutineExceptionHandler = CoroutineExceptionHandler(object: (CoroutineContext, Throwable) -> Unit {
        override fun invoke(p1: CoroutineContext, p2: Throwable) {
            println("Exception Handlers for coroutine with error:$p2")
        }
    })
    val scope = CoroutineScope(coroutineExceptionHandler)

    scope.launch {
        println("launching of scope 1")
        val job = async {
            delay(2000)
            println("printing from inside of the async of job 1")
            println("doing some heavy jobs inside co-rountines inside job1")
        }
        val job1 = async {
            delay(5000)
            println("printing from inside of the async of job 2")
            println("doing some heavy jobs inside co-rountines inside job2")
        }
        job1.await()
        job.await()
    }

    scope.launch {
        println("launching the second coroutine:")
        val job12 = async {
            delay(3000)
            println("launching the second coroutine job:")
        }
        job12.await()
    }

    Thread.sleep(10000L)
}