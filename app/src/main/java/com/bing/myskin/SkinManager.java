package com.bing.myskin;

import android.content.Context;
import android.content.SyncStats;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.bing.myskin.Utils.SkinUtils;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2019/1/25.
 */

public class SkinManager {
    private static final SkinManager ourInstance = new SkinManager();

    public static SkinManager getInstance() {
        return ourInstance;
    }

    private SkinManager() {
    }
    private Context mContext;
    private Resources mSkinResources;
    private String mSkinPkg;

    public void init(Context context){
        mContext = context.getApplicationContext();
    }
    public void loadSkin(String apk){
        try {

            if(TextUtils.isEmpty(apk)){
                mSkinResources =null;
                mSkinPkg = null;
                return;
            }

            //模拟皮肤包下载过程
            String apkPath = SkinUtils.copyAssetAndWrite(mContext,apk);


            AssetManager manager = AssetManager.class.newInstance();

            Method method = AssetManager.class.getDeclaredMethod("addAssetPath",String.class);
            method.invoke(manager,apkPath);
            Resources res = mContext.getResources();
            mSkinResources = new Resources(manager,res.getDisplayMetrics(),res.getConfiguration());
            mSkinPkg = mContext.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES).packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getColor(int id){
        int color = mContext.getResources().getColor(id);  //得到默认的皮肤资源

        if(mSkinResources != null){
            //获取皮肤包颜色资源
            String name = mContext.getResources().getResourceEntryName(id);
            int resId = mSkinResources.getIdentifier(name,"color",mSkinPkg);
            if(resId>0){
                return mSkinResources.getColor(resId);
            }
        }

        return color;
    }

    public Drawable getDrawable(int id){
        Drawable drawable = mContext.getResources().getDrawable(id);  //得到默认的皮肤资源

        if(mSkinResources != null){
            //获取皮肤包颜色资源
            String name = mContext.getResources().getResourceEntryName(id);
            int resId = mSkinResources.getIdentifier(name,"drawable",mSkinPkg);
            if(resId>0){
                return mSkinResources.getDrawable(id);
            }
        }

        return drawable;
    }
}
