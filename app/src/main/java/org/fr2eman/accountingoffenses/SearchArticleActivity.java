package org.fr2eman.accountingoffenses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.network.ServerFacade;

@EActivity(R.layout.activity_search_article)
public class SearchArticleActivity extends MenuActivity {

    private static final String TAG = "fr2eman";

    ProgressDialog progressDialog;

    @ViewById(R.id.search_article_number_edit)
    EditText articleNumberEdit;

    @ViewById(R.id.search_article_description_edit)
    EditText articleDescriptionEdit;

    @Bean
    ServerFacade webService;

    @Bean
    CacheService cache;

    @AfterViews
    public void init() {
        initMenu();
    }

    @Click({R.id.menu_button})
    void slidingMenuButtonClick() {
        menu.toggle();
    }

    @Click(R.id.search_articles_button)
    public void search() {
        if(articleDescriptionEdit.getText().toString().isEmpty() &&
                articleNumberEdit.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(this, "Введите данные для поиска", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        progressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                "Поиск статьи в КоАП и ПИКоАП", true);
        searchArticles();
    }

    @Background
    void searchArticles() {
        boolean status = webService.requestSearchArticles(
                articleNumberEdit.getText().toString(),
                articleDescriptionEdit.getText().toString());
        if(status) {
            success();
        } else {
            failed();
        }
    }

    @UiThread
    void success() {
        progressDialog.hide();
        Intent intent = new Intent(this, ArticlesListActivity_.class);
        startActivity(intent);
    }

    @UiThread
    void failed() {
        progressDialog.hide();
        Toast toast = Toast.makeText(this, cache.getErrorMessage(), Toast.LENGTH_LONG);
        toast.show();
    }
}
