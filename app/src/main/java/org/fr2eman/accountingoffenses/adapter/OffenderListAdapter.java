package org.fr2eman.accountingoffenses.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.fr2eman.accountingoffenses.model.ViolatorModel;
import org.fr2eman.accountingoffenses.view.OffenderListItemView;
import org.fr2eman.accountingoffenses.view.OffenderListItemView_;

import java.util.List;

/**
 * Created by Asus on 04.05.2016.
 */
@EBean
public class OffenderListAdapter  extends BaseAdapter {

    private List<ViolatorModel> list;

    @RootContext
    Context context;

    public void setItems(List<ViolatorModel> items) {
        this.list = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public ViolatorModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViolatorModel model = getItem(position);

        OffenderListItemView itemView;
        if (convertView == null) {
            itemView = OffenderListItemView_.build(context);
        } else {
            itemView = (OffenderListItemView) convertView;
        }

        return itemView.bind(model);
    }

}
