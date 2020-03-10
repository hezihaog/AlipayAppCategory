package com.zh.android.alipayappcategory;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.zh.android.alipayappcategory.item.AppCategoryViewBinder;
import com.zh.android.alipayappcategory.model.AppCategoryModel;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author wally
 */
public class MainActivity extends AppCompatActivity {
    private TabLayout vTab;
    private RecyclerView vAppList;

    /**
     * 分类列表
     */
    private List<AppCategoryModel> mAppCategoryList = new ArrayList<>();

    /**
     * 当前是否正在滚动
     */
    private boolean isScrolling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setData();
        bindView();
    }

    private void findView() {
        vTab = findViewById(R.id.tab);
        vAppList = findViewById(R.id.app_list);
    }

    private void bindView() {
        setupTab();
        setupAppList();
    }

    private void setData() {
        mAppCategoryList.add(generateAppCategory("便民生活", 6));
        mAppCategoryList.add(generateAppCategory("财富管理", 6));
        mAppCategoryList.add(generateAppCategory("资金往来", 6));
        mAppCategoryList.add(generateAppCategory("购物娱乐", 6));
        mAppCategoryList.add(generateAppCategory("教育公益", 6));
        mAppCategoryList.add(generateAppCategory("第三方服务", 18));
    }

    /**
     * 生成分组
     *
     * @param groupName 分组名
     * @param count     生成的数量
     */
    private AppCategoryModel generateAppCategory(String groupName, int count) {
        return new AppCategoryModel(
                groupName,
                generateAppCategoryChildAppList(groupName, count));
    }

    /**
     * 生成分组内的App列表
     *
     * @param groupName 分组名
     * @param count     生成的数量
     */
    private List<AppCategoryModel.AppModel> generateAppCategoryChildAppList(String groupName, int count) {
        List<AppCategoryModel.AppModel> appModels = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String appName = groupName + (i + 1);
            appModels.add(new AppCategoryModel.AppModel(appName, R.mipmap.ic_launcher));
        }
        return appModels;
    }

    private void setupTab() {
        //按分组增加Tab
        for (AppCategoryModel categoryModel : mAppCategoryList) {
            vTab.addTab(vTab.newTab().setText(categoryModel.getGroupName()));
        }
        //设置Tab切换监听
        vTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Tab切换选择时，滚动列表到指定位置
                if (!isScrolling) {
                    int position = tab.getPosition();
                    vAppList.smoothScrollToPosition(position);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupAppList() {
        vAppList.setLayoutManager(new LinearLayoutManager(this));
        MultiTypeAdapter appCategoryListAdapter = new MultiTypeAdapter(mAppCategoryList);
        appCategoryListAdapter.register(AppCategoryModel.class, new AppCategoryViewBinder(new AppCategoryViewBinder.Callback() {
            @Override
            public void onClickCategoryApp(AppCategoryModel.AppModel model) {
                Toast.makeText(MainActivity.this, "点击：" + model.getAppName(), Toast.LENGTH_SHORT).show();
            }
        }));
        vAppList.setAdapter(appCategoryListAdapter);
        vAppList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //滚动状态切换
                isScrolling = newState != RecyclerView.SCROLL_STATE_IDLE;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //查找第一个显示的条目，切换Tab到对应的分组
                LinearLayoutManager layoutManager = (LinearLayoutManager) vAppList.getLayoutManager();
                if (layoutManager == null) {
                    return;
                }
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                TabLayout.Tab tab = vTab.getTabAt(firstVisibleItemPosition);
                //避免重复选中
                if (tab != null && !tab.isSelected()) {
                    tab.select();
                }
            }
        });
    }
}