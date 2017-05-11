package com.app.codeoasis.targil.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.codeoasis.targil.R;

/**
 * Created by eliran.alon on 11-May-17.
 */

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView contactName;
    TextView contactPhone;
    ImageView info;
    RelativeLayout item;

    ItemClickListener listener;

    public ViewHolder(View itemView) {
        super(itemView);
        contactName = (TextView) itemView.findViewById(R.id.contact_name);
        contactPhone = (TextView) itemView.findViewById(R.id.contact_phone);
        info = (ImageView) itemView.findViewById(R.id.more_info);

        item = (RelativeLayout) itemView.findViewById(R.id.item_view);

        item.setOnClickListener(this);
        info.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.listener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        this.listener.onItemClick(view, getLayoutPosition());
    }
}
