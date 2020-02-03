package com.example.annotation_compiler;

import com.example.annotations.BindPath;
import com.google.auto.service.AutoService;


import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;


/**
 * 由于该类处理的注解是在编译时执行的
 * 固其实这里的作用是,新生成一些JAVA文件(相当于拼字符拼出来)
 * 再由其它地方,通过反射,去调用生成的JAVA类的中方法!!!
 * 以达到使用时,只需要要加一个注解,就能完成业务的目的!
 * 这里的业务就是,将被注解的 类名 写入生成的JAVA文件A中的某个方法B中(编译时)
 * 这样其它地方,(运行时)就可以反射出A类,调用其B方法!以实现自己的业务
 */
@AutoService(Processor.class)  //注册注解处理器 这个类会自动调起处理
public class AnnotationCompiler extends AbstractProcessor {

    //生成文件的对象
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        System.out.println("tainan, init");
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    /**
     * 申明注解处理器需要处理的注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        System.out.println("tainan, getSupportedAnnotationTypes");
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        System.out.println("tainan, getSupportedSourceVersion ,currentVersion:"+SourceVersion.latestSupported());
        return SourceVersion.RELEASE_7;
    }

    /**
     * 核心方法
     * 获得注解得到的 类名,写入新的JAVA文件中
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("tainan,执行到了 process!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //拿到所有BindPath的节点
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        //key为注解的value,value(右边的)代表的完整类名
        Map<String, String> map = new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            TypeElement typeElement = (TypeElement) element;
            //获取到key
            BindPath annotation = typeElement.getAnnotation(BindPath.class);
            String value = annotation.value();
            //获取带包名的类名
            String activityType = typeElement.getQualifiedName().toString();
            map.put(value, activityType);
        }
        System.out.println("tainan,there has been process ,the number of map::"+map.size());
        if (map.size() > 0) {
            //开始写文件
            Writer writer = null;
            String activityName = "ActivityUtil" + System.currentTimeMillis();
            try {
                JavaFileObject classFile = filer.createClassFile("com.example.util." + activityName);
                writer = classFile.openWriter();
                writer.write("package com.example.util;\n" +
                        "\n" +
                        "import com.example.aroute.Aroute;\n" +
                        "import com.example.aroute.IAroute;\n" +
                        "\n" +
                        "public class " + activityName + "implements IAroute {\n" +
                        "    @Override\n" +
                        "    public void putActivity() {\n");
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    String path = iterator.next();//注解解
                    String value = map.get(path); //完整类名
                    writer.write("Aroute.getInstance().putActivity(\"" + path + "\"," +
                            value + ".class);\n");
                }
                writer.write("}\n}");

            } catch (Exception e) {
                System.out.println("tainan,there are sometingWrong:"+e.toString());
                e.printStackTrace();
            }finally {
                if(writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}



