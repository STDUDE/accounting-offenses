package org.fr2eman.accountingoffenses.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.fr2eman.accountingoffenses.model.OffenseModel;
import org.fr2eman.accountingoffenses.view.ProtocolListItemView;
import org.fr2eman.accountingoffenses.view.ProtocolListItemView_;

import java.util.List;

/**
 * Created by Asus on 20.04.2016.
 */
@EBean
public class ProtocolsListAdapter extends BaseAdapter {

    private List<OffenseModel> list;

    @RootContext
    Context context;

    public void setItems(List<OffenseModel> items) {
        this.list = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public OffenseModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OffenseModel model = getItem(position);

        ProtocolListItemView itemView;
        if (convertView == null) {
            itemView = ProtocolListItemView_.build(context);
        } else {
            itemView = (ProtocolListItemView) convertView;
        }

        return itemView.bind(model);
    }
}
