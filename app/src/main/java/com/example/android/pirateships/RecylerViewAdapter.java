package com.example.android.pirateships;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ShipViewHolder>{
    private Context mContext;
    private ArrayList<Ship> shipList;

    public RecylerViewAdapter(Context context, ArrayList<Ship> shipList) {
        mContext = context;
        this.shipList = shipList;
    }

    @Override
    public ShipViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ShipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShipViewHolder holder, int pos) {
        final int position = pos;
        Ship currentItem = shipList.get(pos);

        final String imageURL = currentItem.getImageURL();
        final String title = currentItem.getTitle();
        final String price = currentItem.getPrice();

        holder.viewTitle.setText(title);
        holder.viewPrice.setText("$ " + price);
        Picasso.with(mContext).load(imageURL).fit().centerInside().into(holder.shipImage);

        final int objectId = currentItem.getId();
        holder.button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("ID", objectId);
                intent.putExtra("TITLE", title);
                intent.putExtra("PRICE", price);
                intent.putExtra("imageURL", imageURL);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return shipList.size();
    }

    public class ShipViewHolder extends RecyclerView.ViewHolder {
        public ImageView shipImage;
        public TextView viewTitle;
        public TextView viewPrice;
        public Button button;

        public ShipViewHolder(View itemView) {
            super(itemView);
            shipImage = itemView.findViewById(R.id.image_view);
            viewTitle = itemView.findViewById(R.id.text_view_title);
            viewPrice = itemView.findViewById(R.id.text_view_price);
            button = itemView.findViewById(R.id.more_button);
        }
    }
}
