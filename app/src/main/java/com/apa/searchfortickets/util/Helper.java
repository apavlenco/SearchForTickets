package com.apa.searchfortickets.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.apa.searchfortickets.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

import timber.log.Timber;

/**
 * Created by apavlenco on 8/23/20.
 */
public class Helper {

    public static final String REQUEST_DATE_FORMAT = "yyyy-MM-dd";

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } else {
            return false;
        }
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getAssetJsonData(AssetManager assetManager, String filename) {
        String json;
        try {
            InputStream is = assetManager.open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Timber.e(ex);
            return null;
        }

        return json;
    }

    public static AlertDialog createErrorDialog(Context context, DialogInterface.OnClickListener dialogInterface) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.error));
        builder.setMessage(context.getString(R.string.default_error_message));
        builder.setIcon(R.drawable.ic_error_black_24dp);
        builder.setPositiveButton(context.getString(R.string.ok), dialogInterface);
        return builder.create();
    }

    public static NumberFormat getCurrencyFormat(String currency, int fractionDig){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(fractionDig);
        format.setCurrency(Currency.getInstance(currency));
        return format;
    }

}
