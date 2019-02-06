package com.somada.lope_mora.art3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.somada.lope_mora.art3.R;
import com.somada.lope_mora.art3.activities.PaintActivity;
import com.somada.lope_mora.art3.model.Paint;

import java.util.List;

public class PaintAdapter extends RecyclerView.Adapter<PaintAdapter.MyViewHolder> {

    RequestOptions options;
    private Context mContext;
    private List<Paint> mData;

    public PaintAdapter(Context mContext, List<Paint> mData){
        this.mContext = mContext;
        this.mData = mData;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.paintrow, viewGroup, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, PaintActivity.class);

                i.putExtra("paint_name", mData.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("paint_artista", mData.get(viewHolder.getAdapterPosition()).getArtista());
                i.putExtra("paint_imge", mData.get(viewHolder.getAdapterPosition()).getImage());
                i.putExtra("paint_cover", mData.get(viewHolder.getAdapterPosition()).getPhotoAutor());

                mContext.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.name12.setText(mData.get(position).getName());
        //holder.autor12.setText(mData.get(position).getArtista());
        //holder.name.setText("mi");
        Log.v(mData.get(0).getName(), "fazer");
        //load image from the internet using Glide
        //Glide.with(mContext).load(mData.get(position).getImage()).apply(options).into(holder.photo12);
        holder.name12.setText(mData.get(position).getName());
        holder.autor12.setText(mData.get(position).getArtista());

        Glide.with(mContext).load(mData.get(position).getImage()).apply(options).into(holder.photo12);
    }

    @Override
    public int getItemCount() {
        Log.v(Integer.toString(mData.size()), "tamanho");
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name12, autor12;
        ImageView cover12, photo12;
        LinearLayout view_container;

        public MyViewHolder(View itemView){
            super(itemView);
            view_container = itemView.findViewById(R.id.container);
            name12 = itemView.findViewById(R.id.paintTitle);
            autor12 = itemView.findViewById(R.id.paintArtista);
            cover12 = itemView.findViewById(R.id.cover);
            photo12 = itemView.findViewById(R.id.thumbnail);
        }
    }
}
