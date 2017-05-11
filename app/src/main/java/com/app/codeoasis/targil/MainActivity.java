package com.app.codeoasis.targil;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.app.codeoasis.targil.DB.ContactsRepo;
import com.app.codeoasis.targil.Recycler.ContactItem;
import com.app.codeoasis.targil.Recycler.RecyclerAdapter;
import com.app.codeoasis.targil.Utils.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    private ProgressDialog pDialog;

    private static String url = "http://api.androidhive.info/contacts/";

    private ArrayList<ContactItem> contactList;

    private RecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ContactsRepo contactsRepo;
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback;
    ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate begin");
        setContentView(R.layout.activity_main);
        contactList = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView

                final int position = viewHolder.getAdapterPosition();

                Log.d(TAG, "Deleting item" + contactList.get(position).getId());
                contactsRepo.deleteByID(contactList.get(position).getId());
                contactList.clear();
                Log.d(TAG, "List size in db is: " + String.valueOf(contactsRepo.getContactListASC().size()));
                contactList = contactsRepo.getContactListASC();
                mAdapter.notifyItemRemoved(position);
                mAdapter.setItems(contactList);
            }
        };

        itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mAdapter = new RecyclerAdapter(this, contactList);
        mRecyclerView.setAdapter(mAdapter);

        contactsRepo = new ContactsRepo(getApplicationContext());
        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);


            pDialog = new ProgressDialog(MainActivity.this,R.style.full_screen_dialog){
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);

                    setContentView(R.layout.splash_screen_dialog);
                    getWindow().setLayout(RecyclerView.LayoutParams.FILL_PARENT, RecyclerView.LayoutParams.FILL_PARENT);
                }
            };
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpRequest sh = new HttpRequest();

            // Making a request to url and getting response
            String jsonStr = sh.getJSONString(url);
//            Log.e(TAG, "Response from url: " + jsonStr);
            contactsRepo.deleteAll();
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        // tmp hash map for single contact
                        ContactItem item = new ContactItem(id,name,email,address,gender,mobile,home,office);

                        // adding contact to contact list
                        contactList.add(item);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            Log.d(TAG, "Starting to create DB");
            for (ContactItem item: contactList) {
                contactsRepo.insert(item);
            }
            contactList.clear();
            contactList = contactsRepo.getContactListASC();
            Log.d(TAG, "List size in db is: " + String.valueOf(contactsRepo.getContactListASC().size()));
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            mAdapter.setItems(contactList);
            mAdapter.notifyDataSetChanged();


        }

    }
}
