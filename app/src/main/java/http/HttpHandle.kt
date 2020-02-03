package http

/**
 *代理类
 */
class HttpHandle : IHttpHandle {


    /**
     * 通用写法
     * 注入的资源都当静态处理
     *
     */
    companion object {
         lateinit var mHttpHandle: IHttpHandle
         var instance: HttpHandle?=null


        /**
         * 核心方法
         */
        fun init(httpHandle: IHttpHandle){
            mHttpHandle = httpHandle
        }


        /**
         * 单例
         */
        fun obtain(): HttpHandle {
            synchronized(HttpHandle) {
                if (instance == null) {
                    instance = HttpHandle()
                }
            }
            return instance!!
        }
    }


    private fun HttpHandle() {}


    override fun post(url: String, callback: IHttpCallback) {
        mHttpHandle.post(url,callback)
    }


}