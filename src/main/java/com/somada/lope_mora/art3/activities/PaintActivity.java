package com.somada.lope_mora.art3.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.somada.lope_mora.art3.R;

public class PaintActivity extends AppCompatActivity {

    private final String PHOTO_URL = "https://raw.githubusercontent.com/Enitos/art/master/paints_img/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paint);

        // hide the default actionbar
        //getSupportActionBar().hide();

        // Recieve data

        String name = getIntent().getExtras().getString("paint_name");
        String art = getIntent().getExtras().getString("paint_artista");
        String img_url = getIntent().getExtras().getString("paint_imge");
        String cover_url = getIntent().getExtras().getString("paint_cover");


        TextView nome1 = findViewById(R.id.titlep);
        TextView autor = findViewById(R.id.author);
        ImageView cover = findViewById(R.id.photo);
        ImageView photo = findViewById(R.id.cover);

        // set values

        nome1.setText(name);
        autor.setText(art);


        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading)
                .error(R.drawable.loading);

        //set image
        Glide.with(this).load(img_url).into(photo);
        // set
        Glide.with(this)
                .load(PHOTO_URL + cover_url + ".jpg")
                .apply(RequestOptions.circleCropTransform())
                .into(cover);
        String foto = PHOTO_URL + cover_url + ".jpg";
        Log.v("foto", foto);
    }
}
