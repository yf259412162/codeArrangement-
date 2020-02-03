package http

/**
 * 具体实现类
 */
class RetrofitHttp :IHttpHandle {



    override fun post(url: String, callback: IHttpCallback) {
        callback.onSuccess(url)
    }
}