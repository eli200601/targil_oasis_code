package com.app.codeoasis.targil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.codeoasis.targil.Recycler.ContactItem;

public class ContactInfoActivity extends ActionBarActivity {

    private  static final String TAG= "ContactInfoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Intent mIntent  = getIntent();
        ContactItem contact = (ContactItem) mIntent.getParcelableExtra("contact");
        Log.d(TAG, "Name= " + contact.getName());

        TextView name = (TextView) findViewById(R.id.contact_name);
        TextView id = (TextView) findViewById(R.id.id);
        TextView email = (TextView) findViewById(R.id.email);
        TextView address = (TextView) findViewById(R.id.address);
        TextView gender = (TextView) findViewById(R.id.gender);
        TextView mobile = (TextView) findViewById(R.id.mobile);
        TextView home = (TextView) findViewById(R.id.home);
        TextView office = (TextView) findViewById(R.id.office);

        name.setText(contact.getName());
        id.setText("ID: " + contact.getId());
        email.setText("E-mail: " + contact.getEmail());
        address.setText("Address: " + contact.getAddress());
        gender.setText("Gender: " + contact.getGender());
        mobile.setText("Mobile: " + contact.getMobile());
        home.setText("Home: " + contact.getHome());
        office.setText("Office: " + contact.getOffice());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
