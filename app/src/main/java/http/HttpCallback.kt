package http

import com.google.gson.Gson
import java.lang.Exception
import java.lang.reflect.ParameterizedType

abstract class HttpCallback<Result>:IHttpCallback {


    abstract fun onSuccesss(result:Result)

    abstract fun onFail(result:String)

    override fun onSuccess(result: String) {
        //通过反射,找到result的类型
        try {
            val clz = analysisClassInfo(this)
            //通过gson转
            val fromJson:Result = Gson().fromJson<Result>(result, clz)
            onSuccesss(fromJson)
        }catch (e:Exception){
            onFail(e.toString())
        }

    }


    /**
     * 获得泛型的类型!
     */
    private fun analysisClassInfo(httpCallback: HttpCallback<Result>): Class<Any> {
        val genericSuperclass = httpCallback.javaClass.genericSuperclass
        val actualTypeArguments = (genericSuperclass as ParameterizedType).actualTypeArguments
        return actualTypeArguments[0] as Class<Any>
    }

    override fun onFailure(error: String) {
        onFail(error)
     }
}