package com.example.furyou.omisechallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import co.omise.android.models.Token;
import co.omise.android.ui.CreditCardActivity;

/**
 * Activity -> Donation screen
 *
 **/

public class CharityDonationActivity extends AppCompatActivity {
    private TextView charity_donation_name;
    private ImageView charity_donation_logo;
    private static final int REQUEST_CC = 100;
    private String transaction_token;
    private String donator_name;
    private Button submit_donation_button;
    private EditText donation_amount_form;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // hiding the Application title
        getSupportActionBar().hide(); // hiding the title bar
        setContentView(R.layout.activity_charity_donation);

        submit_donation_button = (Button) findViewById(R.id.submit_donation_button);
        submit_donation_button.setEnabled(false); // disabling the submit transaction button to make sure that no transaction is submit before the mandatory information are filled
        submit_donation_button.setBackgroundColor(getResources().getColor(R.color.grey,null));

        charity_donation_name = (TextView) findViewById(R.id.charity_donation_name);

        // getting the intent from CharityListAcivity which contains the information regarding the Charity (name & logo url)
        Intent charity_intent = getIntent();
        charity_donation_name.setText(charity_intent.getStringExtra("charity_name"));
        charity_donation_logo = (ImageView) findViewById(R.id.charity_donation_logo);

        // getting the logo from the url by starting an AsyncTask
        new DownloadImageTask(charity_donation_logo).execute(charity_intent.getStringExtra("charity_logo"));

        donation_amount_form = (EditText) findViewById(R.id.donation_amount_form);
        // adding a TextWatcher to the TextImput with the amount to enable or disable the submit button when necessary
        donation_amount_form.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(donation_amount_form.getText())) {
                    submit_donation_button.setEnabled(false);
                    submit_donation_button.setBackgroundColor(getResources().getColor(R.color.grey,null));
                } else if (!TextUtils.isEmpty((transaction_token))) {
                    submit_donation_button.setEnabled(true);
                    submit_donation_button.setBackgroundColor(getResources().getColor(R.color.omiseBlue,null));
                }
            }
        });
        transaction_token = "";
    }

    // getting the result from the CreditCardActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CC:
                if (resultCode == CreditCardActivity.RESULT_CANCEL) {
                    return;
                }

                Token token = data.getParcelableExtra(CreditCardActivity.EXTRA_TOKEN_OBJECT);
                // getting the transaction information from CreditCardActivity (token & name)
                transaction_token = token.id;
                donator_name = token.card.name;

                // enabling or disabling the submit button when necessary
                if (TextUtils.isEmpty(donation_amount_form.getText())) {
                    submit_donation_button.setEnabled(false);
                    submit_donation_button.setBackgroundColor(getResources().getColor(R.color.grey,null));
                } else {
                    submit_donation_button.setEnabled(true);
                    submit_donation_button.setBackgroundColor(getResources().getColor(R.color.omiseBlue,null));
                }

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // on click on the add credit card infos navigate to the CreditCardActivity
    public void go_to_credit_card_form(View v)
    {
        Intent intent = new Intent(this, co.omise.android.ui.CreditCardActivity.class);
        intent.putExtra(co.omise.android.ui.CreditCardActivity.EXTRA_PKEY, MainActivity.OMISE_PKEY);
        startActivityForResult(intent, REQUEST_CC);
    }

    // on click on the submit button navigate to the TransactionStatusActivity
    public void go_to_transaction_status_screen(View v)
    {
        String transaction_amount = donation_amount_form.getText().toString();

        int amt = Integer.parseInt(transaction_amount);
        if (amt < 2000) {
            Toast.makeText(this, "Amount should be at least 2000 satang (20.00 baht)", Toast.LENGTH_LONG).show();
            submit_donation_button.setEnabled(false);
            submit_donation_button.setBackgroundColor(getResources().getColor(R.color.grey,null));
        } else if (amt > 100000000)
        {
            Toast.makeText(this, "Amount should be less than 100000000 satang (1000000.00 baht)", Toast.LENGTH_LONG).show();
            submit_donation_button.setEnabled(false);
            submit_donation_button.setBackgroundColor(getResources().getColor(R.color.grey,null));
        } else {
            // adding extra to the intent to send to the TransactionStatusActivity
            Intent transaction_status_intent = new Intent(this, TransactionStatusActivity.class);
            transaction_status_intent.putExtra("TOKEN", transaction_token);
            transaction_status_intent.putExtra("NAME", donator_name);
            transaction_status_intent.putExtra("AMOUNT", transaction_amount);
            startActivity(transaction_status_intent);
        }
    }
}
