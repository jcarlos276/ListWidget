package com.guillen.listwidget;

/**
 * Created by guillen on 04/12/17.
 */

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.guillen.listwidget.models.Tienda;
import com.guillen.listwidget.services.ApiService;
import com.guillen.listwidget.services.ApiServiceGenerator;

import retrofit2.Call;

@SuppressLint("NewApi")
public class WidgetDataProvider implements RemoteViewsFactory {

    private static final String TAG = "WidgetDataProvider";

    List mCollections = new ArrayList();

    Context mContext = null;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCollections.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews mView = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        mView.setTextViewText(android.R.id.text1, (CharSequence) mCollections.get(position));
        mView.setTextColor(android.R.id.text1, Color.BLACK);

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(WidgetProvider.ACTION_TOAST);
        final Bundle bundle = new Bundle();
        bundle.putString(WidgetProvider.EXTRA_STRING,
                (String) mCollections.get(position));
        fillInIntent.putExtras(bundle);
        mView.setOnClickFillInIntent(android.R.id.text1, fillInIntent);
        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {
        try {
            ApiService service = ApiServiceGenerator.createService(ApiService.class);
            Call<List<Tienda>> call = service.getTiendas();
            List<Tienda> tiendas = call.execute().body();
            Log.d(TAG, "eventos" + tiendas);
            for (Tienda tienda : tiendas){
                String nombre = tienda.getNombre();
                String direccion = tienda.getUbicacion();
                Log.d(TAG, "nombre: " + nombre);
                mCollections.add(nombre + "\n" +direccion);
            }
        }catch(Exception e){
            Log.e(TAG, "ERROR-JC" + e.toString());
        }
    }

    @Override
    public void onDestroy() {

    }

}