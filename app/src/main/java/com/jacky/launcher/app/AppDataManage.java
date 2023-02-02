
package com.jacky.launcher.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppDataManage {

    private final Context mContext;

    private boolean isHidenApp(String appName) {
        if(appName == null) {
            return(true);
        }

        if ((appName.equals("com.jacky.launcher")) ||
                (appName.equals("com.mbx.settingsmbox")) ||
                (appName.equals("com.rockchip.wfd")) ||
                (appName.equals("android.rockchip.update.service")) ||
                (appName.equals("com.android.documentsui")) ||
                (appName.equals("com.rockchips.dlna")) ||
                (appName.equals("com.droidlogic.FileBrower")) ||
                (appName.equals("com.rockchips.mediacenter"))) {
            return(true);
        }
        return(false);
    }

    public AppDataManage(Context context) {
        mContext = context;
    }

    public ArrayList<AppModel> getLaunchAppList() {
        PackageManager localPackageManager = mContext.getPackageManager();
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> localList = localPackageManager.queryIntentActivities(localIntent, 0);
        ArrayList<AppModel> localArrayList = null;
        Iterator<ResolveInfo> localIterator = null;
        localArrayList = new ArrayList<>();
        if (localList.size() != 0) {
            localIterator = localList.iterator();
        }
        while (localIterator.hasNext()) {
            ResolveInfo localResolveInfo = (ResolveInfo) localIterator.next();
            AppModel localAppBean = new AppModel();
            localAppBean.setIcon(localResolveInfo.activityInfo.loadIcon(localPackageManager));
            localAppBean.setName(localResolveInfo.activityInfo.loadLabel(localPackageManager).toString());
            localAppBean.setPackageName(localResolveInfo.activityInfo.packageName);
            localAppBean.setDataDir(localResolveInfo.activityInfo.applicationInfo.publicSourceDir);
            localAppBean.setLauncherName(localResolveInfo.activityInfo.name);
            String pkgName = localResolveInfo.activityInfo.packageName;
            PackageInfo mPackageInfo;
            try {
                mPackageInfo = mContext.getPackageManager().getPackageInfo(pkgName, 0);
                if ((mPackageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {// 系统预装
                    localAppBean.setSysApp(true);
                }
                if ((mPackageInfo.applicationInfo.flags & ApplicationInfo.FLAG_IS_GAME) > 0) {// 游戏
                    continue;
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            if (isHidenApp(localAppBean.getPackageName())) {
                continue;
            }
            localArrayList.add(0, localAppBean);
        }
        return localArrayList;
    }

    public ArrayList<AppModel> getLaunchGameList() {
        PackageManager localPackageManager = mContext.getPackageManager();
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> localList = localPackageManager.queryIntentActivities(localIntent, 0);
        ArrayList<AppModel> localArrayList = null;
        Iterator<ResolveInfo> localIterator = null;
        localArrayList = new ArrayList<>();
        if (localList.size() != 0) {
            localIterator = localList.iterator();
        }
        while (localIterator.hasNext()) {
            ResolveInfo localResolveInfo = (ResolveInfo) localIterator.next();
            AppModel localAppBean = new AppModel();
            localAppBean.setIcon(localResolveInfo.activityInfo.loadIcon(localPackageManager));
            localAppBean.setName(localResolveInfo.activityInfo.loadLabel(localPackageManager).toString());
            localAppBean.setPackageName(localResolveInfo.activityInfo.packageName);
            localAppBean.setDataDir(localResolveInfo.activityInfo.applicationInfo.publicSourceDir);
            localAppBean.setLauncherName(localResolveInfo.activityInfo.name);
            String pkgName = localResolveInfo.activityInfo.packageName;
            PackageInfo mPackageInfo;
            try {
                mPackageInfo = mContext.getPackageManager().getPackageInfo(pkgName, 0);
                if ((mPackageInfo.applicationInfo.flags & ApplicationInfo.FLAG_IS_GAME) == 0) {// 游戏
                    continue;
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            if (isHidenApp(localAppBean.getPackageName())) {
                continue;
            }
            localArrayList.add(0, localAppBean);
        }
        return localArrayList;
    }
}
