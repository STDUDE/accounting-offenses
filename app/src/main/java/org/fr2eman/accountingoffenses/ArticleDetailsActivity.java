package org.fr2eman.accountingoffenses;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.fr2eman.accountingoffenses.model.ArticleModel;
import org.fr2eman.accountingoffenses.model.CacheService;

@EActivity(R.layout.activity_article_details)
public class ArticleDetailsActivity extends Activity {

    private static String TAG = "fr2eman";

    @ViewById(R.id.article_number_view)
    TextView articleNumberView;

    @ViewById (R.id.article_name_view)
    TextView articleNameView;

    @ViewById (R.id.article_description_view)
    TextView articleDescriptionView;

    @ViewById (R.id.article_punishment_view)
    TextView articlePunishmentView;

    @Bean
    CacheService cache;

    @Click(R.id.back_button)
    void back() {
        finish();
    }

    @AfterViews
    void init() {
        Log.i("fr2eman", "open article details");

        ArticleModel articleDetails = cache.getArticleDetails();

        articleNumberView.setText(articleDetails.getNumberArticle() + " "
                + articleDetails.getCodexType());
        articleNameView.setText(articleDetails.getName());
        articleDescriptionView.setText(articleDetails.getDescription());
        String punishment = "-, влечёт" + (articleDetails.getWarning()
                ? " предупреждение" : "");
        if(articleDetails.getWarning() && (articleDetails.getMaxMulct() != 0
                || articleDetails.getMinMulct() != 0)) {
            punishment += " или наложение штрафа в размере" +
                    (articleDetails.getMinMulct() != 0 ? " от " + articleDetails.getMinMulct()
                            : "") + " " + (articleDetails.getMaxMulct() != 0 ? "до " +
                    articleDetails.getMaxMulct() : "") + " базовых величин";
        } else if(!articleDetails.getWarning() && (articleDetails.getMaxMulct() != 0
                || articleDetails.getMinMulct() != 0)) {
            punishment += " наложение штрафа в размере" +
                    (articleDetails.getMinMulct() != 0 ? " от " + articleDetails.getMinMulct()
                            : "") + " " + (articleDetails.getMaxMulct() != 0 ? "до " +
                    articleDetails.getMaxMulct() : "") + " базовых величин";
        } else {
            punishment += "-, нет ответственности";
        }
        articlePunishmentView.setText(punishment);
    }
}
