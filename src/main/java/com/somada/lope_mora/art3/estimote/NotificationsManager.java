package com.somada.lope_mora.art3.estimote;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;
import com.somada.lope_mora.art3.R;
import com.somada.lope_mora.art3.activities.App;
import com.somada.lope_mora.art3.activities.MainActivity;
import com.somada.lope_mora.art3.model.Paint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationsManager {

    private Context context;
    private NotificationManager notificationManager;
    private Notification helloNotification;
    private Notification goodbyeNotification;
    private int notificationId = 1;
    private String title1, author, title;
    private JsonArrayRequest request, request1;
    private RequestQueue requestQueue, requestQueue1;
    private ArrayList<Paint> paintList = new ArrayList<Paint>();

    private final String JSON_URL = "https://raw.githubusercontent.com/Enitos/art/master/paints.json";
    private final String photoPatch = "https://raw.githubusercontent.com/Enitos/Art-app/master/";

    public NotificationsManager(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //this.goodbyeNotification = buildNotification("Bye bye", "You've left the proximity of your beacon");

    }

    private Notification buildNotification(List<Paint> paintList) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel contentChannel = new NotificationChannel(
                    "content_channel", "Things near you", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(contentChannel);
        }

        Intent actionIntent = new Intent(context, MainActivity.class);
        actionIntent.putExtra("NAME", paintList.get(0).getName());
        actionIntent.putExtra("IMG", paintList.get(0).getImage());
        actionIntent.putExtra("ART", paintList.get(0).getArtista());
        actionIntent.putExtra("PHAR", paintList.get(0).getPhotoAutor());
        actionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        actionIntent.setAction("View");


        //Log.v("testar2", title);

        PendingIntent contentIntent = PendingIntent.getActivity(context,
               0, actionIntent, PendingIntent.FLAG_CANCEL_CURRENT );

        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);
        String CONTENT_TEXT = "FAlta pouco";
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.title, "Art");
        contentView.setOnClickPendingIntent(R.id.left_button, PendingIntent.getService(context, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        return new NotificationCompat.Builder(context.getApplicationContext(), "content_channel")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Art")
                .setContentText(CONTENT_TEXT)
                .setAutoCancel(true)
                .setCustomContentView(contentView)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //.setStyle(new NotificationCompat.BigPictureStyle())
                //.addExtras("enitos")
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(android.R.drawable.star_on, "Ver", contentIntent)
                .addAction(android.R.drawable.star_on, "fechar", contentIntent)
                .setContentIntent(contentIntent)

                .build();
    }



    public void startMonitoring() {
        ProximityObserver proximityObserver =
                new ProximityObserverBuilder(context, ((App) context).cloudCredentials)
                        .onError(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("app", "proximity observer error: " + throwable);
                                return null;
                            }
                        })
                        .withBalancedPowerMode()
                        .build();

        ProximityZone zone = new ProximityZoneBuilder()
                .forTag("teste-9uh")
                .inCustomRange(3.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        title1 = proximityContext.getAttachments().get("teste-9uh/title");
                        //helloNotification = buildNotification(title1, "You're near your beacon");
                        Log.d(title1, "eu sei");
                        //notificationManager.notify(notificationId, helloNotification);
                        jsonrRequest(title1);

                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManager.notify(notificationId, goodbyeNotification);
                        return null;
                    }
                })
                .build();
        proximityObserver.startObserving(zone);
    }

    private void jsonrRequest(final String notification) {
        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        if (notification.equalsIgnoreCase(jsonObject.getString("beaconName"))){

                            Paint paint = new Paint();
                            paint.setName(jsonObject.getString("name"));
                            paint.setArtista(jsonObject.getString("Artista"));
                            paint.setMaterial(jsonObject.getString("Material"));
                            paint.setImage(jsonObject.getString("image"));
                            paint.setBeaconName(jsonObject.getString("beaconName"));
                            paint.setPhotoAutor(jsonObject.getString("cover"));
                            Log.d(paint.getArtista(), "mi bom na java");

                            paintList.add(paint);
                            String titlePaint = paint.getName();
                            author = paint.getArtista();
                            //ident = paint.getBeaconName();
                            //coverAutor = paint.getPhotoAutor();
                            //Log.d(coverAutor, "cover-ter");
                            //renderImage(ident, coverAutor);*/
                            upnotify(paintList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    private void upnotify(List<Paint> paintList){
        helloNotification = buildNotification(paintList);
        notificationManager.notify(notificationId, helloNotification);
    }
}
