package org.fr2eman.accountingoffenses.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.fr2eman.accountingoffenses.model.ArticleModel;
import org.fr2eman.accountingoffenses.view.ArticleListItemView;
import org.fr2eman.accountingoffenses.view.ArticleListItemView_;

import java.util.List;

/**
 * Created by Asus on 05.05.2016.
 */
@EBean
public class ArticleListAdapter extends BaseAdapter {

    private List<ArticleModel> list;

    @RootContext
    Context context;

    public void setItems(List<ArticleModel> items) {
        this.list = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public ArticleModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArticleModel model = getItem(position);

        ArticleListItemView itemView;
        if (convertView == null) {
            itemView = ArticleListItemView_.build(context);
        } else {
            itemView = (ArticleListItemView) convertView;
        }

        return itemView.bind(model);
    }

}
