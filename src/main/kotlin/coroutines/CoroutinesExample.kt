import kotlinx.coroutines.*

fun main(){

    val scope = CoroutineScope(Dispatchers.Default)

    val foo1 = scope.async{
        println("From async")
        println(foo())
        println(this.coroutineContext[Job]?.job)
    }

    scope.launch {
        println("await function")
        println(foo1.await())
    }

    runBlocking {
        println("From runblocking")
        foo()
        println(this.coroutineContext[Job]?.job)
    }

    scope.launch {
        foo()
        println("From launch")
        println(this.coroutineContext[Job]?.job)
    }

    scope.launch {
        withContext(Dispatchers.IO){
            println("From withcontext")
            foo()
            println("With context job :"+this.coroutineContext[Job]?.job)
        }
        println("Launch job:"+this.coroutineContext[Job]?.job)
    }

    //highly discouraged
    CoroutineScope(context = GlobalScope.coroutineContext).launch {
        foo()
        println(this.coroutineContext[Job]?.job)
    }
}

suspend fun foo1() = coroutineScope {
    return@coroutineScope "abcncnc"
}

suspend fun foo() = "fjslfkjsdf"