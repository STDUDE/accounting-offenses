package org.fr2eman.accountingoffenses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.fr2eman.accountingoffenses.dialog.LogoutAction;
import org.fr2eman.accountingoffenses.dialog.LogoutDialog;
import org.fr2eman.accountingoffenses.model.DataService;
import org.fr2eman.accountingoffenses.network.ServerFacade;

/**
 * Created by Fr2eman on 26.03.2016.
 */
@EActivity
public class MenuActivity extends Activity implements View.OnClickListener, LogoutAction {

    private static final String TAG = "fr2eman";

    ProgressDialog progressDialog;

    protected SlidingMenu menu;

    @Bean
    protected ServerFacade webService;

    @Bean
    protected DataService data;

    RelativeLayout createOffenseButton;
    RelativeLayout historyOffensesButton;
    RelativeLayout privateOfficeButton;
    RelativeLayout articlesButton;
    RelativeLayout searchButton;

    protected void initMenu() {
        Log.i(TAG, "call initMenu()");
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.menu_shadow_width);
        menu.setShadowDrawable(R.drawable.menu_shadow);
        menu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.actionbar_menu);

        createOffenseButton = (RelativeLayout) menu.getMenu()
                .findViewById(R.id.add_new_offense_item);
        historyOffensesButton = (RelativeLayout) menu.getMenu()
                .findViewById(R.id.history_offenses_item);
        privateOfficeButton = (RelativeLayout) menu.getMenu()
                .findViewById(R.id.private_office_item);
        articlesButton = (RelativeLayout) menu.getMenu()
                .findViewById(R.id.articles_item);
        searchButton = (RelativeLayout) menu.getMenu()
                .findViewById(R.id.search_item);

        createOffenseButton.setOnClickListener(this);
        historyOffensesButton.setOnClickListener(this);
        privateOfficeButton.setOnClickListener(this);
        articlesButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
    }

    protected void openMenu() {
        Log.i(TAG, "call openMenu()");
        menu.toggle();
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, "user clicked on MenuItem()");
        switch(view.getId()) {
            case R.id.add_new_offense_item: {
                clickCreateProtocol();
                break;
            }
            case R.id.history_offenses_item: {
                clickHistoryProtocols();
                break;
            }
            case R.id.private_office_item: {
                clickPrivateOffice();
                break;
            }
            case R.id.search_item: {
                clickSearch();
                break;
            }
            case R.id.articles_item: {
                clickArticles();
                break;
            }
        }
    }

    protected void clickCreateProtocol() {
        Log.i(TAG, "user clicked on add protocol item");
        menu.toggle();
        Intent intent = new Intent(this, EditProtocolActivity_.class);
        finish();
        startActivity(intent);
    }

    protected void clickHistoryProtocols() {
        Log.i(TAG, "user clicked on history protocols item");
        menu.toggle();
        Intent intent = new Intent(this, HistoryActivity_.class);
        finish();
        startActivity(intent);
    }

    protected void clickPrivateOffice() {
        Log.i(TAG, "user clicked on private office item");
        menu.toggle();
        Intent intent = new Intent(this, PersonalActivity_.class);
        finish();
        startActivity(intent);
    }

    protected void clickSearch() {
        Log.i(TAG, "user clicked on search item");
        menu.toggle();
        Intent intent = new Intent(this, SearchViolatorActivity_.class);
        finish();
        startActivity(intent);
    }

    protected void clickArticles() {
        Log.i(TAG, "user clicked on articles item");
        menu.toggle();
        Intent intent = new Intent(this, SearchArticleActivity_.class);
        finish();
        startActivity(intent);
    }

    @Override
    @UiThread
    public void onLogout() {
        progressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                getString(R.string.logout_progress), true);
        Log.i(TAG, "logout comleted!");
        logout();
        progressDialog.hide();
        finish();
    }

    @Background
    public void logout() {
        webService.requestLogout();
        data.clearOffensesHistory();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            LogoutDialog dialog = new LogoutDialog();
            dialog.show(getFragmentManager(), TAG);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
