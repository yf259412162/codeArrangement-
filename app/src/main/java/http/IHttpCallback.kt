package http

interface IHttpCallback {
    fun onSuccess(result:String)

    fun onFailure(error:String)
}