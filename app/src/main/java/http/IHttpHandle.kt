package http


/**
 * HTTP基类
 */
interface IHttpHandle {
    fun post(url:String,callback: IHttpCallback)
}