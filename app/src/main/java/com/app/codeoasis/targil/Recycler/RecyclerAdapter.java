package com.app.codeoasis.targil.Recycler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.codeoasis.targil.ContactInfoActivity;
import com.app.codeoasis.targil.R;

import java.util.ArrayList;

/**
 * Created by eliran.alon on 11-May-17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static String TAG = "RecyclerAdapter";
    private Context context;

    public ArrayList<ContactItem> contactList;

    public ViewHolder mHolder;

    public RecyclerAdapter(Context context, ArrayList<ContactItem> list) {
        this.contactList = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        mHolder = new ViewHolder(view);

        return mHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        final int pos = position;
        mHolder.setIsRecyclable(false);
        mHolder.contactName.setText(contactList.get(position).getName());
        mHolder.contactPhone.setText((contactList.get(position).getMobile()));

        mHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                //Clicking on item
                if (view.getId() == R.id.more_info) {
                    Log.d(TAG, "info Clicked");
                    Log.d(TAG, "Position clicked: " + pos);
                    Intent intent = new Intent(context, ContactInfoActivity.class);
                    intent.putExtra("contact", contactList.get(pos));
                    context.startActivity(intent);
                }
                else {
                    if (view.getId() == R.id.item_view) {
                        Log.d(TAG, "Layout Clicked");
                        Log.d(TAG, "Position clicked: " + pos);
                        String numberStr = contactList.get(pos).getMobile();
                        Log.d(TAG, "Mobile is:" + numberStr);
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+numberStr));
                        context.startActivity(intent);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void setItems(ArrayList<ContactItem> list){
        contactList = list;
    }

}
