package com.example.furyou.omisechallenge;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Acivity -> List of Charity screen
 **/

public class CharityListActivity extends AppCompatActivity {
    private ListView charity_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // hiding the Application title
        getSupportActionBar().hide(); // hiding the title bar
        setContentView(R.layout.activity_charity_list);

        charity_list_view = (ListView) findViewById(R.id.charity_list_view);
        // starting the AsyncTask to get the list of charities
        new GetCharityList().execute();
    }

    // AsyncTask to get the list of charities
    private class GetCharityList extends AsyncTask<Void, Void, List<Charity>> {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(CharityListActivity.this, android.R.style.Theme_Dialog));
        private ProgressDialog dialog = new ProgressDialog(CharityListActivity.this);
        private String inputLine;
        private String result;

        // display loading
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getString(R.string.LOADING_CHARITIES));
            this.dialog.show();
        }

        @Override
        protected List<Charity> doInBackground(Void... voids) {
            List<Charity> charities = new ArrayList<Charity>();
            try {
                // creating a URL object with the root to get charities from our server
                URL myUrl = new URL(MainActivity.URL + "/charities");
                // creating a connection
                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
                    // setting method and timeout
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(20000); // 20 seconds
                    connection.setConnectTimeout(20000); // 20 seconds

                    // connection to the url
                    connection.connect();
                    // if the server do not return a 200 response code, we return a null object
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        connection.disconnect();
                        return null;
                    }
                    // creating an InputStreamReader to read the answer from the server
                    InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    while((inputLine = reader.readLine()) != null){
                        stringBuilder.append(inputLine);
                    }
                    // closing the readers
                    reader.close();
                    streamReader.close();

                    result = stringBuilder.toString();

                    JSONArray jsonResult = new JSONArray(result);

                    // creating the list of charities
                    for (int i = 0; i < jsonResult.length(); i++) {
                        JSONObject objectResult = jsonResult.getJSONObject(i);
                        Charity c = new Charity(objectResult.getString("name"), objectResult.getString("logo_url"));
                        charities.add(c);
                    }
                    connection.disconnect();
            }catch (Exception e){
                return null;
            }
            // returning the list of charities
            return charities;
        }

        @Override
        protected void onPostExecute(List<Charity> charities) {
            super.onPostExecute(charities);
            // stop showing loading
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            // if the list of charities is empty there is a problem with the IP address or the connectivity
            if (charities == null || charities.size() == 0)
            {
                builder.setMessage(R.string.IP_ERROR_MSG)
                        .setTitle(R.string.IP_ERROR_TITLE)
                        .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(CharityListActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // setting the adapter and passing it the list of charities
                CharityAdapter adapter = new CharityAdapter(CharityListActivity.this, charities);
                charity_list_view.setAdapter(adapter);

                // Set an item click listener for each charities of the ListView
                charity_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Get the selected charity text from ListView
                        Charity selectedCharity = (Charity) parent.getItemAtPosition(position);

                        // starting a new intent when click on a charity to navigate to the charity donation screen
                        Intent charityDonationIntent = new Intent();
                        charityDonationIntent.setClass(CharityListActivity.this, CharityDonationActivity.class);
                        charityDonationIntent.putExtra("charity_name", selectedCharity.get_name());
                        charityDonationIntent.putExtra("charity_logo", selectedCharity.get_logo_url());
                        startActivity(charityDonationIntent);
                    }
                });
            }
        }
    }
}
