package com.bing.myskin;

import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2019/1/25.
 */

public class SkinItem {
    public View view;
    public List<SkinAttr> mSkinAttrs;

    public SkinItem(View view, List<SkinAttr> mSkinAttrs) {
        this.view = view;
        this.mSkinAttrs = mSkinAttrs;
    }

    public void apply() {
        for (SkinAttr mSkinAttr : mSkinAttrs) {
            if("background".equals(mSkinAttr.attrName)){
                if("drawable".equals(mSkinAttr.attrType)){  //换图片
                    view.setBackgroundDrawable(SkinManager.getInstance().getDrawable(mSkinAttr.id));
                }else if("color".equals(mSkinAttr.attrType)){  //换颜色
                    view.setBackgroundColor(SkinManager.getInstance().getColor(mSkinAttr.id));
                }
            }else  if("textColor".equals(mSkinAttr.attrName) && view instanceof TextView){
                ((TextView)view).setTextColor(SkinManager.getInstance().getColor(mSkinAttr.id));
            }
        }
    }
}
