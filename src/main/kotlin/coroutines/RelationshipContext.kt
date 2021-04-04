import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun main(){
    val coroutineExceptionHandler = CoroutineExceptionHandler(object: (CoroutineContext, Throwable) -> Unit {
        override fun invoke(p1: CoroutineContext, p2: Throwable) {
            println("Exception Handlers for coroutine with error:$p2")
        }
    })
    val scope = CoroutineScope(coroutineExceptionHandler)

    //so that the error is not propogated and cancels the second job
    scope.launch(SupervisorJob()) {
        println("launching of scope 1")
        val job = async {
            delay(2000)
            println("printing from inside of the async of job 1")
            println("doing some heavy jobs inside co-rountines inside job1")
            throw IllegalStateException("Some illegal state")
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
        val job = async {
            delay(3000)
            println("launching the second coroutine job:")
        }
        job.await()
    }

    //hold the app until all parallel excution gets completed.
    Thread.sleep(15000L)
}