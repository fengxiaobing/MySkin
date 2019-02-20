package com.bing.myskin;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.bing.myskin.Utils.SkinUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/25.
 */

public class SkinFactory implements LayoutInflater.Factory {

//    private Activity activity;
//
//    public SkinFactory(Activity activity) {
//        this.activity = activity;
//    }

    public static final String[] VIEW_PREFIX = {"android.widget.","android.view.","android.webkit."};


    private List<SkinItem> mSkinItems = new ArrayList<>();

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {


        Log.e("SkinFactory", "onCreateView: "+name );
        //创建View
        View view = createView(name,context,attrs);
        //
        if(view != null){
            //解析view的属性
            parseSkinAttr(view,context,attrs);
        }
        return view;
    }
    //android:background="@color/colorPrimary"
    private void parseSkinAttr(View view, Context context, AttributeSet attrs) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);    //background
            String attrValue = attrs.getAttributeValue(i); //@2130968615
            Log.e("SkinFactory", "attrName: "+attrName+"   attrValue: "+attrValue);
            //做一下资源属性过滤
            if(attrValue.startsWith("@")){
                int id = Integer.parseInt(attrValue.substring(1));
                String attrType = context.getResources().getResourceTypeName(id);  //color
                String entryName = context.getResources().getResourceEntryName(id); //colorPrimary
                SkinAttr skinAttr = new SkinAttr(id,attrName,attrType);
                skinAttrs.add(skinAttr);

            }
        }
        //当前界面可能需要换肤的所有view，以及对应属性
        if(!skinAttrs.isEmpty()){
            mSkinItems.add(new SkinItem(view,skinAttrs));
        }

    }

    /**
     * 开始换肤
     */
    public void apply(){

//        int resId = SkinUtils.getStatusBarResId(activity);
//        activity.getWindow().setStatusBarColor(SkinManager.getInstance().getColor(resId));

        for (SkinItem mSkinItem : mSkinItems) {
            mSkinItem.apply();
        }
    }


    private View createView(String name, Context context, AttributeSet attrs) {

        View view = null;
        if(name.contains(".")){  //自定义控件
            view = SkinUtils.getView(name,context,attrs);
        }else { //系统控件
            for (String viewPrefix : VIEW_PREFIX) {
                String className = viewPrefix + name;  //android.widget.Button
                view = SkinUtils.getView(className,context,attrs);
                if(view != null){
                    break;
                }
            }
        }


        return view;
    }
}
