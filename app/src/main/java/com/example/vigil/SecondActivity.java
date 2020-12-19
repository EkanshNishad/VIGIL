package com.example.vigil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SecondActivity extends AppCompatActivity {

    AutoCompleteTextView emailid;
    String selectedEmail;
    String bodyEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);


        emailid = (AutoCompleteTextView)findViewById(R.id.emailid);

        getpermissions();
        emailid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> emails = new ArrayList<>();
                Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
                //get runtime permission

                /*ArrayList<String> names = new ArrayList<String>();
                Cursor emailids = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, null);

                names.add("iit2019182@iiita.ac.in");
                // Loop Through All The Emails
                while (emailids.moveToNext()) {

                    String name = emailids.getString(emailids.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String email = emailids.getString(emailids.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));

                    // Enter Into Hash Map
                    names.add(email);

                }

                for(String str : emails)
                {
                    if (emailPattern.matcher(str).matches()) {
                        String possibleEmail = str+"\n";
                        emails.add(possibleEmail);
                    }
                }*/

                Account[] accounts = AccountManager.get(SecondActivity.this).getAccounts();

                int i=0;
                for (Account account : accounts) {
                    if (emailPattern.matcher(account.name).matches()) {
                        String possibleEmail = account.name+"\n";
                        emails.add(possibleEmail);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (SecondActivity.this,android.R.layout.simple_list_item_1,emails);
                emailid.setAdapter(adapter);

                //default selected email
                selectedEmail = emails.get(0);
                emailid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // You can get the user selected email here
                        selectedEmail = emailid.getAdapter().getItem(position).toString();
                        emailid.setText(selectedEmail);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }


            @Override
            public void afterTextChanged(Editable s) {
                selectedEmail = emailid.getText().toString();
            }
        });


        final EditText body = (EditText)findViewById(R.id.body);
        body.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals(""))
                bodyEmail = body.getText().toString();
                Log.i("info", body.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void sendEmail(View view)
    {




        switch (view.getId())
        {
            case R.id.fire:
                String to = selectedEmail;
                String mess = bodyEmail;
                /*Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                email.putExtra(Intent.EXTRA_TEXT, message);

                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));*/
                try{
                    SendEmail.sendEmail(to, mess);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.medical:
                Log.i("info ", "hello");
                break;
            case R.id.others:
                break;
            case R.id.police:
                break;
        }
    }

    public void getpermissions(){
        int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.GET_ACCOUNTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.GET_ACCOUNTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

}
