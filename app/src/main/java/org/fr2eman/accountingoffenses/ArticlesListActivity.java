package org.fr2eman.accountingoffenses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.fr2eman.accountingoffenses.adapter.ArticleListAdapter;
import org.fr2eman.accountingoffenses.model.ArticleModel;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.network.ServerFacade;

import java.util.List;

@EActivity(R.layout.activity_articles_list)
public class ArticlesListActivity extends Activity {

    private static final String TAG = "fr2eman";

    ProgressDialog progressDialog;

    @ViewById(R.id.list_view_articles)
    ListView listArticles;

    @Bean
    ArticleListAdapter articleListAdapter;

    @Bean
    ServerFacade webService;

    @Bean
    CacheService cache;

    private int id_article;

    @AfterViews
    void init() {
        List<ArticleModel> list = cache.getListArticles();
        articleListAdapter.setItems(list);
        listArticles.setAdapter(articleListAdapter);
    }

    @Click(R.id.back_button)
    void back() {
        finish();
    }

    @ItemClick(R.id.list_view_articles)
    void clickOffenseItem(ArticleModel model) {
        progressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                "Загрузка данных о правонарушителе", true);
        id_article = model.getId();
        Log.i(TAG, "click artcile_id = " + id_article);
        loadArticleData();
    }

    @Background
    void loadArticleData() {
        boolean status = webService.requestGetArticle(id_article);
        if(status) {
            success();
        } else {
            failed();
        }
    }

    @UiThread
    void success() {
        progressDialog.hide();
        Intent intent = new Intent(this, ArticleDetailsActivity_.class);
        startActivity(intent);
    }

    @UiThread
    void failed() {
        progressDialog.hide();
        Toast toast = Toast.makeText(this, cache.getErrorMessage(), Toast.LENGTH_LONG);
        toast.show();
    }
}
