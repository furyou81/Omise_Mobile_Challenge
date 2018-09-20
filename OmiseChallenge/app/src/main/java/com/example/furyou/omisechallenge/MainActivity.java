package com.example.furyou.omisechallenge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;

/**
 * Activity -> entry point of the application (first screen)
 **/

public class MainActivity extends AppCompatActivity {
    // HERE YOU NEED TO PUT THE URL OF YOUR SERVER
    static final String URL = "http://"+ "PUT_YOUR_IP_ADDRESS_HERE" + ":3000";
    // HERE YOU NEED TO PUT YOUR OMISE PUBLIC KEY
    static final String OMISE_PKEY = "PUT_YOUR_PKEY_TEST_HERE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // hiding the Application title
        getSupportActionBar().hide(); // hiding the title bar

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // checking connectivity
        if (!isNetworkAvailable(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Dialog));
            builder.setMessage(R.string.CONNECTIVITY_ERROR_MSG)
                    .setTitle(R.string.CONNECTIVITY_ERROR_TITLE)
                    .setPositiveButton(R.string.OK, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    // on click on the button go to the charity list screen
    public void go_to_charity_list_screen(View v)
    {
            Intent intent = new Intent();
            intent.setClass(this, CharityListActivity.class);
            startActivity(intent);
    }

    // checking if the phone is connected to the internet
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
