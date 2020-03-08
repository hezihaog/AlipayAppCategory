package com.zh.android.alipayappcategory.model;

import java.io.Serializable;
import java.util.List;

/**
 * App分组模型
 */
public class AppCategoryModel implements Serializable {
    private static final long serialVersionUID = 5186025966806603971L;

    /**
     * 分组名
     */
    private String groupName;
    /**
     * 分组内的App列表
     */
    private List<AppModel> appList;

    public AppCategoryModel() {
    }

    public AppCategoryModel(String groupName, List<AppModel> appList) {
        this.groupName = groupName;
        this.appList = appList;
    }

    public static class AppModel implements Serializable {
        private static final long serialVersionUID = 597951759629202359L;

        /**
         * App名称
         */
        private String appName;

        /**
         * 图标的资源Id
         */
        private int iconResId;

        public AppModel() {
        }

        public AppModel(String appName, int iconResId) {
            this.appName = appName;
            this.iconResId = iconResId;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public int getIconResId() {
            return iconResId;
        }

        public void setIconResId(int iconResId) {
            this.iconResId = iconResId;
        }
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<AppModel> getAppList() {
        return appList;
    }

    public void setAppList(List<AppModel> appList) {
        this.appList = appList;
    }
}