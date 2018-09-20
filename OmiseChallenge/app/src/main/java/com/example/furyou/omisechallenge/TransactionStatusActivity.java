package com.example.furyou.omisechallenge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Activity -> sending the transaction to the server & showing the confirmation screen
 **/

public class TransactionStatusActivity extends AppCompatActivity {
    private Transaction transaction;
    private TextView transaction_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_status);
        transaction_status = (TextView) findViewById(R.id.transaction_status);

        // getting the intent from CharityDonationActivity containing the information of the transaction
        Intent tr = getIntent();
        this.transaction = new Transaction(tr.getStringExtra("NAME"), tr.getStringExtra("TOKEN"), tr.getStringExtra("AMOUNT"));
        // starting an AsyncTask with the information of the transaction to send the transaction on the server
        new SendTransaction().execute(transaction);
    }

    private class SendTransaction extends AsyncTask<Transaction, Void, String> {
        private ProgressDialog dialog = new ProgressDialog(TransactionStatusActivity.this);

        // display loading
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getString(R.string.TRANSACTION_ON_GOING));
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Transaction... t) {
            // sending a post with the transaction information to the server then getting the answer from the server
            try {
                URL url = new URL(MainActivity.URL + "/donations");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Accept","application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                String par = "name=" + t[0].getName() +"&token=" + t[0].getToken() + "&amount=" + t[0].getAmount();

                DataOutputStream os = new DataOutputStream(connection.getOutputStream());

                os.writeBytes(par);

                os.flush();
                os.close();

                // checking response code status, if not 200 we return an error
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    connection.disconnect();
                    return (getString(R.string.ERROR));
                }

                Log.i("MSG" , connection.getResponseMessage());

                connection.disconnect();

                BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                byte[] contents = new byte[1024];

                int bytesRead = 0;
                String strFileContents = "";
                while ((bytesRead = in.read(contents)) != -1) {
                    strFileContents += new String(contents, 0, bytesRead);
                }

                Log.i("STATUS", strFileContents);
                return strFileContents;

            } catch (Exception e) {
                e.printStackTrace();
                return getString(R.string.ERROR);
            }
        }

        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            transaction_status.setText("Transaction " + result);
        }
    }

    public void go_to_main_screen(View v)
    {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }
}
