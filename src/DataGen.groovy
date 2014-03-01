class DataGen {

    //Utility Timing Function
    static def timeit = {
        String message, int numThreads=1, int count=1, int warmup=3, Closure cl->
        warmup.times { cl() }
        def startTime = System.currentTimeMillis()
        count.times {
            def threads = []
            numThreads.times { threads << new Thread(cl as Runnable) }
            threads*.start()
            threads*.join()
        }
        def deltaTime = System.currentTimeMillis() - startTime
        def average = deltaTime / count
        println "$message:\tcount: $count \ttime: $deltaTime \taverage: $average"
    }
}
