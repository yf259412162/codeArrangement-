package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import bean.User
import com.example.amodule.AClass
import com.example.annotations.BindPath
import com.example.basearoute.Aroute
import com.example.bmodule.BCLASS
import http.HttpCallback
import http.HttpHandle
import kotlinx.android.synthetic.main.activity_main.*

@BindPath("main/main")
class MainActivity : AppCompatActivity(), View.OnClickListener {


    /**
     * 进行一次测试提交
     */
    override fun onClick(v: View?) {

        Log.i("tainan", "执行到了onClick")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_main.text = ""
        //调用a module中的方法来增加MAP
       // AClass.addPrint()
        tv_main.setOnClickListener(this)
        //调用 b module中的方法来增加MAP
        onClick(tv_main)
      //  BCLASS.addPrint()
        //自己 查看MAP
        Log.i("tainan", "当前MAP中的值" + Aroute.getInstance().printMap())




    /*    HttpHandle.obtain().post("我的天呀", object : HttpCallback<String>() {

            override fun onSuccesss(result: String) {
                Log.i("tainan", "执行到了onSuccesss$result")
            }

            override fun onFail(result: String) {
                Log.i("tainan", "执行到了onFail$result")
            }

        })*/

    }

}

