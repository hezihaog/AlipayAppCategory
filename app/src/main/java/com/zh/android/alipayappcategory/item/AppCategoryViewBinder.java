package com.zh.android.alipayappcategory.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zh.android.alipayappcategory.R;
import com.zh.android.alipayappcategory.model.AppCategoryModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * App分类条目，内嵌分组内的App列表
 */
public class AppCategoryViewBinder extends ItemViewBinder<AppCategoryModel, AppCategoryViewBinder.ViewHolder> {
    /**
     * 回调
     */
    private Callback mCallback;

    public interface Callback {
        /**
         * 点击分类中的某个App时回调
         */
        void onClickCategoryApp(AppCategoryModel.AppModel model);
    }

    public AppCategoryViewBinder(Callback callback) {
        mCallback = callback;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_app_category, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull AppCategoryModel item) {
        Context context = holder.itemView.getContext();
        holder.vGroupName.setText(item.getGroupName());
        holder.vChildAppList.setLayoutManager(new GridLayoutManager(context, 4));
        MultiTypeAdapter childListAdapter = new MultiTypeAdapter(item.getAppList());
        childListAdapter.register(AppCategoryModel.AppModel.class, new ChildAppViewBinder());
        holder.vChildAppList.setAdapter(childListAdapter);
        holder.vChildAppList.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView vGroupName;
        private final RecyclerView vChildAppList;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            vGroupName = itemView.findViewById(R.id.group_name);
            vChildAppList = itemView.findViewById(R.id.child_app_list);
        }
    }

    class ChildAppViewBinder extends ItemViewBinder<AppCategoryModel.AppModel, ChildAppViewBinder.ChildViewHolder> {
        @NonNull
        @Override
        protected ChildViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            return new ChildViewHolder(inflater.inflate(R.layout.item_app_category_child_item, parent, false));
        }

        @Override
        protected void onBindViewHolder(@NonNull ChildViewHolder holder, @NonNull final AppCategoryModel.AppModel item) {
            holder.vAppIcon.setImageResource(item.getIconResId());
            holder.vAppName.setText(item.getAppName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClickCategoryApp(item);
                }
            });
        }

        class ChildViewHolder extends RecyclerView.ViewHolder {
            private final ImageView vAppIcon;
            private final TextView vAppName;

            ChildViewHolder(@NonNull View itemView) {
                super(itemView);
                vAppIcon = itemView.findViewById(R.id.app_icon);
                vAppName = itemView.findViewById(R.id.app_name);
            }
        }
    }
}