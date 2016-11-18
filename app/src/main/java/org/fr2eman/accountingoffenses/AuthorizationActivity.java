package org.fr2eman.accountingoffenses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Click;
import org.fr2eman.accountingoffenses.dialog.LogoutDialog;
import org.fr2eman.accountingoffenses.model.CacheService;
import org.fr2eman.accountingoffenses.network.ServerFacade;

// ngrok
@EActivity(R.layout.activity_authorization)
public class AuthorizationActivity extends AppCompatActivity {

    private static String TAG = "fr2eman";

    ProgressDialog progressDialog;

    @ViewById(R.id.authorization_edit_private_number)
    EditText privateNumberEdit;

    @ViewById(R.id.authorization_edit_password)
    EditText passwordEdit;

    @ViewById(R.id.button_authorization)
    Button authorizationButton;

    @Bean
    ServerFacade webService;

    @Bean
    CacheService cache;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @AfterViews
    public void init() {

    }

    @Click(R.id.button_authorization)
    public void clickAuthorization() {
        if (privateNumberEdit.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(this, getString(R.string.login_toast),
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if (passwordEdit.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(this, getString(R.string.password_toast),
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        cache.setInputUsername(privateNumberEdit.getText().toString());
        cache.setInputPassword(passwordEdit.getText().toString());
        progressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                getString(R.string.authorization_progress), true);
        login();
    }

    @Background
    public void login() {
        boolean status = webService.requestAuthorization();
        if (status) {
            successAuthorization();
        } else {
            errorAuthorization();
        }
    }

    @UiThread
    public void successAuthorization() {
        progressDialog.hide();
        cache.setCurrentMode(CacheService.Mode.MODE_AUTHORIZATION);
        Intent intent = new Intent(this, PersonalActivity_.class);
        startActivity(intent);
    }

    @UiThread
    public void errorAuthorization() {
        progressDialog.hide();
        Toast toast = Toast.makeText(this, cache.getErrorMessage(), Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Authorization Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://org.fr2eman.accountingoffenses/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Authorization Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://org.fr2eman.accountingoffenses/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
