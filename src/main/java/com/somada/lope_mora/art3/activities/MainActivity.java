package com.somada.lope_mora.art3.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.somada.lope_mora.art3.R;
import com.somada.lope_mora.art3.adapter.PaintAdapter;
import com.somada.lope_mora.art3.model.Paint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    //private Paint paint;
    private final String JSON_URL = "https://raw.githubusercontent.com/Enitos/art/master/paints.json";

    private ImageView cover, photo;
    private TextView title, author, title1, teste;
    private String ident, coverAutor;
    private int visita;
    Dialog mDialog;

    private List<Paint> lstPaint = new ArrayList<>();
    private RecyclerView recyclerView;
    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final App application = (App) getApplication();
        mDialog = new Dialog(this);

        lstPaint = new ArrayList<>();
        recyclerView = findViewById(R.id.rv);
        jsonrequest();

        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(
                        this,
                        new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                //startProximityContentManager();
                                application.enableBeaconNotifications();
                                return null;
                            }
                        },
                        new Function1<List<? extends Requirement>, Unit>() {
                            @Override
                            public Unit invoke(List<? extends Requirement> requirements) {
                                Log.d("app", "requirements missing: " + requirements);
                                return null;
                            }
                        },
                        new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.d("app", "requirements error: " + throwable);
                                return null;
                            }
                        }
                );

    }

    private void jsonrequest() {
        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Paint paint = new Paint();
                        paint.setName(jsonObject.getString("name"));
                        paint.setArtista(jsonObject.getString("Artista"));
                        paint.setMaterial(jsonObject.getString("Material"));
                        paint.setImage(jsonObject.getString("image"));
                        paint.setBeaconName(jsonObject.getString("beaconName"));
                        paint.setPhotoAutor(jsonObject.getString("cover"));

                        Log.v(paint.getArtista(), "errrorr");
                        lstPaint.add(paint);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Toast.makeText(MainActivity.this, lstPaint.get(1).toString(), Toast.LENGTH_SHORT).show();
                setPapter(lstPaint);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

    public void setPapter (List<Paint> lstPaint){
        PaintAdapter mAdapter = new PaintAdapter(this, lstPaint);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

   @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


    }

    @Override
    protected void onResume() {
        super.onResume();

        String name = getIntent().getStringExtra("NAME");
        String img = getIntent().getStringExtra("IMG");
        String art = getIntent().getStringExtra("ART");
        String phar = getIntent().getStringExtra("PHAR");
        //String notification = getIntent().getStringExtra("EXTRA");
        Log.d(name, "onNewIntent: ");
        if (name != null) {
            openDialog(name, img, art, phar);
        }

    }

    @Override
    protected void onDestroy() {
        //proximityObserverHandler.stop();
        super.onDestroy();
    }


    public void openDialog(String nome, String img, String art, String phar) {
        TextView txtclose, autor;
        ImageView paint;
        Button btnFollow;
        mDialog.setContentView(R.layout.popup);
        txtclose = (TextView) mDialog.findViewById(R.id.txtclose);
        title1 = (TextView) mDialog.findViewById(R.id.nome);
        title1.setText(nome);
        autor = mDialog.findViewById(R.id.artista);
        autor.setText(art);
        txtclose.setText("x");
        btnFollow = (Button) mDialog.findViewById(R.id.btn);

        paint = mDialog.findViewById(R.id.paint);
        Glide.with(this).load(img).into(paint);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();


    }


}
