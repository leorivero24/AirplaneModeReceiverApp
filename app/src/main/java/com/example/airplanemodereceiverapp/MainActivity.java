package com.example.airplanemodereceiverapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private AirplaneModeReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Mantener el Edge-to-Edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Crear el BroadcastReceiver
        receiver = new AirplaneModeReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar dinámicamente el receiver
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(receiver, filter);

        // Revisar si el receiver envió un extra indicando que el Modo Avión se activó
        handleIntent(getIntent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar el receiver para evitar fugas de memoria
        unregisterReceiver(receiver);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.getBooleanExtra("airplane_mode_on", false)) {
            // Mostrar un diálogo para abrir la app de llamadas
            new AlertDialog.Builder(this)
                    .setTitle("Modo Avión activado")
                    .setMessage("¿Quieres abrir la app de llamadas?")
                    .setPositiveButton("Abrir", (dialog, which) -> {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:2664553747"));
                        startActivity(callIntent);
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }
    }
}
