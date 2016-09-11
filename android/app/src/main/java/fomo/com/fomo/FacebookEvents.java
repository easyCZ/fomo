package fomo.com.fomo;

import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Created by mehdi on 11/09/16.
 */

public class FacebookEvents {
    private CallbackManager callbackManager;
    private AppCompatActivity activateApp;
    public void something() {


        FacebookSdk.sdkInitialize(activateApp.getApplicationContext());
        AppEventsLogger.activateApp(activateApp.getApplication());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        LoginManager.getInstance().logInWithReadPermissions(activateApp, Arrays.asList("email","user_friends","user_events","read_custom_friendlists"));
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{event-id}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                    }
                }
        ).executeAsync();
    }
}
