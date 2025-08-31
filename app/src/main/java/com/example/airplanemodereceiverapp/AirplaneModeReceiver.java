package com.example.airplanemodereceiverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AirplaneModeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
            Toast.makeText(context, isAirplaneModeOn ?
                            "Modo Avión ACTIVADO" : "Modo Avión DESACTIVADO",
                    Toast.LENGTH_SHORT).show();

            if (isAirplaneModeOn) {
                // Lanzar MainActivity con un extra indicando que se activó el modo avión
                Intent mainIntent = new Intent(context, MainActivity.class);
                mainIntent.putExtra("airplane_mode_on", true);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(mainIntent);
            }
        }
    }
}
