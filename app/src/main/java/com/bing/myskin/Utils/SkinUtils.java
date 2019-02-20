package com.bing.myskin.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;

/**
 * Created by Administrator on 2019/1/25.
 */

public class SkinUtils {
    /**
     * 根据控件名称，通过反射创建view
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    public static View getView(String name, Context context, AttributeSet attrs) {
        View view = null;
        try {
            //得到字节码
            Class<?> clazz = context.getClassLoader().loadClass(name);
            //拿到构造器
            Constructor<?> constructor = clazz.getConstructor(Context.class,AttributeSet.class);
            //用两个参数的方法创建
            view = (View) constructor.newInstance(context,attrs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    /**
     * 将Asset目录下的fileName文件拷贝到app的缓存目录
     * @param context
     * @param fileName
     * @return
     */
    public static String copyAssetAndWrite(Context context, String fileName) {
        try {
            File cacheDir = context.getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            }
            File outFile = new File(cacheDir, fileName);
            if (!outFile.exists()) {
                boolean res = outFile.createNewFile();
                if(res){
                    InputStream is = context.getAssets().open(fileName);
                    FileOutputStream fos = new FileOutputStream(outFile);
                    byte[] buffer = new byte[is.available()];
                    int byeCount;
                    while ((byeCount = is.read(buffer)) != -1){
                        fos.write(buffer,0,byeCount);
                    }
                    fos.flush();
                    is.close();
                    fos.close();
                    return outFile.getAbsolutePath();
                }
            }else {
                return outFile.getAbsolutePath();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
