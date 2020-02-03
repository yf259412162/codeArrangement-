package com.example.basearoute;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import dalvik.system.DexFile;

/**
 * 到时候直接调用Aroute这个类
 * init方法,就可以调用生成的类的方法
 * 由于生成的类继承 IAroute,
 */
public class Aroute {

    private Aroute() {
    }

    private static Aroute aroute;

    private HashMap<String, String> map = new HashMap<>();

    private HashMap<String, Class<?>> activityMaps = new HashMap<>();


    private Context context;

    public static Aroute getInstance() {
        if (aroute == null) aroute = new Aroute();
        return aroute;
    }

    public void addData(String key, String valuse) {
        map.put(key, valuse);
    }

    public int printMap() {
        return map.size();
    }

    public void init(Application context) {
        this.context = context;
        List<String> className = getClassName("com.example.util");
        for (String clzz : className) {
            try {
                //通过反射获得类型 这个对象就是APT生成的那些文件类型!
                Class<?> aClass = Class.forName(clzz);
                 if(IAroute.class.isAssignableFrom(aClass)){
                     IAroute iroute = (IAroute) aClass.newInstance();
                     //调用put方法,将list导入内存中
                     iroute.putActivity();
                 }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 去拿包名下面的所有CLASS文件
     * @param
     */
    private List<String> getClassName(String packageName) {
        //创建一个class对象的集合
        List<String> classList = new ArrayList<>();
        String path = null;
        try {
            //获取到应用信息类然后获取到APK的完整路径
            path = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(),0).sourceDir;
            //根据APK的完整路径获得编译后的DEX文件
            DexFile dexFile = new DexFile(path);
            //获得编译后的DEX文件中的所有CLASS文件
            Enumeration<String> entries = dexFile.entries();
            //开始遍历
            while (entries.hasMoreElements()){
                //通过遍历所有的CLASS包名
                String name = entries.nextElement();
                if(name.contains(packageName)){
                    classList.add(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classList;
    }

    /**
     * 该方法,将会被 注解生成的JAVA类,通过反射来调用!!
     *
     * @param path
     * @param clazz
     */
    public void putActivity(String path, Class<?> clazz) {
        activityMaps.put(path, clazz);
    }
}
