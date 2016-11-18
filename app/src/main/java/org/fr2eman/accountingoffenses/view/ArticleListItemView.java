package org.fr2eman.accountingoffenses.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.fr2eman.accountingoffenses.R;
import org.fr2eman.accountingoffenses.model.ArticleModel;

/**
 * Created by Asus on 05.05.2016.
 */
@EViewGroup(R.layout.article_list_item_layout)
public class ArticleListItemView extends LinearLayout {

    private static final String TAG = "fr2eman";

    @ViewById(R.id.article_number_item)
    TextView articleNumberText;

    @ViewById(R.id.article_name_item)
    TextView articleDescriptionText;

    private ArticleModel model;

    public ArticleListItemView(Context context) {
        super(context);
    }

    public ArticleListItemView bind(ArticleModel model) {
        articleNumberText.setText(model.getNumberArticle() + " " + model.getCodexType());
        articleDescriptionText.setText(model.getName());
        this.model = model;
        return this;
    }

    public ArticleModel getModel() {
        return this.model;
    }

}
