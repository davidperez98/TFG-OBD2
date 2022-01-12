package com.github.pires.obd.reader.activity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pires.obd.reader.R;
import com.github.pires.obd.reader.trips.TripLog;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

// Some code taken from https://github.com/barbeau/gpstest

@ContentView(R.layout.main)
public class MainActivity extends BaseAplicacion {
    private static final String TAG = MainActivity.class.getName();
    public static final String MOSTRARINFO_MODO = "MostrarInfo_Modo";
    public static final String EDITORCOCHES_MODO = "EditorCoches_modo";



    @InjectView(R.id.textoInicio1)
    private TextView textoInicio1;
    @InjectView(R.id.textoInicio2)
    private TextView textoInicio2;
    @InjectView(R.id.textoInicioCoche)
    private TextView textoInicioCoche;
    private Button botonInicioConectarOBD;
    private Button botonInicioPruebaOBD;
    private Button botonInicioBuscarErr;
    private Button botonConfiguracionOBDCommands;
    private Button botonInicioIniTramo;
    private Button botonInicioIniAnalisis;
    private Button botonConectarCoche;
    private Button botonMostrarCoches;
    private Button botonAniadirCoches;
    private Button botonQuitarCoches;
    private Button botonAniadirPiezas;
    private Button botonMostrarPiezas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "----------onCreate--------SE USA----I1");
        super.onCreate(savedInstanceState);

        btStatusTextView = findViewById(R.id.BT_STATUS0);
        obdStatusTextView = findViewById(R.id.OBD_STATUS0);

        final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter != null)
            bluetoothDefaultIsEnable = btAdapter.isEnabled();

        // create a log instance for use by this application
        triplog = TripLog.getInstance(this.getApplicationContext());

        obdStatusTextView.setText(getString(R.string.status_obd_disconnected));

        botonInicioConectarOBD = findViewById(R.id.botonInicioConectarOBD);
        botonInicioConectarOBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateConfig();
                Toast.makeText(MainActivity.this, "Debe ir a Bluetooth --> Dispisitivos emparejados, y buscar el dispositivo a emparejar", Toast.LENGTH_LONG).show();
            }
        });
        botonInicioPruebaOBD = findViewById(R.id.botonInicioPruebaOBD);
        botonInicioPruebaOBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doBindService();
                    if(isServiceBound){
                    textoInicio1.setVisibility(View.GONE);
                    textoInicio2.setVisibility(View.GONE);
                    botonInicioConectarOBD.setVisibility(View.GONE);
                    botonInicioPruebaOBD.setVisibility(View.GONE);

                    doUnbindService();

                    botonInicioBuscarErr.setVisibility(View.VISIBLE);
                    botonConfiguracionOBDCommands.setVisibility(View.VISIBLE);
                    botonInicioIniTramo.setVisibility(View.VISIBLE);
                    botonInicioIniAnalisis.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(MainActivity.this, "No hay ningun dispositivo emparejado.", Toast.LENGTH_LONG).show();
                }
            }
        });
        botonInicioBuscarErr = findViewById(R.id.botonInicioBuscarErr);
        botonInicioBuscarErr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarInfoBuscarErrores();
            }
        });
        botonConfiguracionOBDCommands = findViewById(R.id.botonConfiguracionOBDCommands);
        botonConfiguracionOBDCommands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateConfig();
                Toast.makeText(MainActivity.this, "Debe ir al fondo, a Comandos OBD --> OBD Commands.", Toast.LENGTH_LONG).show();
            }
        });
        botonInicioIniTramo = findViewById(R.id.botonInicioIniTramo);
        botonInicioIniTramo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarInfoIniciarTramo();
            }
        });
        botonInicioIniAnalisis = findViewById(R.id.botonInicioIniAnalisis);
        botonInicioIniAnalisis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarInfoIniciarAnalisis();
            }
        });

        botonConectarCoche = findViewById(R.id.botonConectarCoche);
        botonMostrarCoches = findViewById(R.id.botonVerCoches);
        botonAniadirCoches = findViewById(R.id.botonAniadirCoches);
        botonQuitarCoches = findViewById(R.id.botonQuitarCoches);
        botonAniadirPiezas = findViewById(R.id.botonAniadirPiezas);
        botonMostrarPiezas= findViewById(R.id.botonMostrarPiezas);
        if(hayCoches()){
            botonConectarCoche.setVisibility(View.GONE);
            textoInicioCoche.setVisibility(View.GONE);

            botonMostrarCoches.setVisibility(View.VISIBLE);
            botonMostrarCoches.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mostrarCoches();
                }
            });
            botonAniadirCoches.setVisibility(View.VISIBLE);
            botonAniadirCoches.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aniadirCoches();
                }
            });
            botonQuitarCoches.setVisibility(View.VISIBLE);
            botonQuitarCoches.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    retirarCoches();
                }
            });
            botonAniadirPiezas.setVisibility(View.VISIBLE);
            botonAniadirPiezas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aniadirPieza();
                }
            });
            botonMostrarPiezas.setVisibility(View.VISIBLE);
            botonMostrarPiezas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mostrarPiezas();
                }
            });
        }else{
            botonConectarCoche.setVisibility(View.VISIBLE);
            botonConectarCoche.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aniadirCoches();
                }
            });
            textoInicioCoche.setVisibility(View.VISIBLE);

            botonMostrarCoches.setVisibility(View.GONE);
            botonAniadirCoches.setVisibility(View.GONE);
            botonQuitarCoches.setVisibility(View.GONE);
            botonAniadirPiezas.setVisibility(View.GONE);
            botonMostrarPiezas.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "----------onStart--------SE USA----I2----E10");

        Log.d(TAG, "----------onStart--------SE USA----I2----E10");
        if(hayCoches()){
            botonConectarCoche.setVisibility(View.GONE);
            textoInicioCoche.setVisibility(View.GONE);

            botonMostrarCoches.setVisibility(View.VISIBLE);
            botonMostrarCoches.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mostrarCoches();
                }
            });
            botonAniadirCoches.setVisibility(View.VISIBLE);
            botonAniadirCoches.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aniadirCoches();
                }
            });
            botonQuitarCoches.setVisibility(View.VISIBLE);
            botonQuitarCoches.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    retirarCoches();
                }
            });
            botonAniadirPiezas.setVisibility(View.VISIBLE);
            botonAniadirPiezas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aniadirPieza();
                }
            });
            botonMostrarPiezas.setVisibility(View.VISIBLE);
            botonMostrarPiezas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mostrarPiezas();
                }
            });
        }else{
            botonConectarCoche.setVisibility(View.VISIBLE);
            botonConectarCoche.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aniadirCoches();
                }
            });
            textoInicioCoche.setVisibility(View.VISIBLE);

            botonMostrarCoches.setVisibility(View.GONE);
            botonAniadirCoches.setVisibility(View.GONE);
            botonQuitarCoches.setVisibility(View.GONE);
            botonAniadirPiezas.setVisibility(View.GONE);
            botonMostrarPiezas.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "----------onDestroy--------SE USA----E14");

        releaseWakeLockIfHeld();
        if (isServiceBound) {
            doUnbindService();
        }

        endTrip();

        final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter != null && btAdapter.isEnabled() && !bluetoothDefaultIsEnable)
            btAdapter.disable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "----------onPause--------SE USA----E8----E12----E14");
        releaseWakeLockIfHeld();
    }

    @SuppressLint("InvalidWakeLockTag")
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "----------onResume--------SE USA----I3----E11");

        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,
                "ObdReader");

        // get Bluetooth device
        final BluetoothAdapter btAdapter = BluetoothAdapter
                .getDefaultAdapter();

        preRequisites = btAdapter != null && btAdapter.isEnabled();
        if (!preRequisites && prefs.getBoolean(ConfigActivity.ENABLE_BT_KEY, false)) {
            preRequisites = btAdapter != null && btAdapter.enable();
        }

        if (!preRequisites) {
            showDialog(BLUETOOTH_DISABLED);
            btStatusTextView.setText(getString(R.string.status_bluetooth_disabled));
        } else {
            btStatusTextView.setText(getString(R.string.status_bluetooth_ok));
        }
    }

    private void mostrarInfoBuscarErrores() {
        Log.d(TAG, "---------- mostrarInfoBuscarErrores --------");

        Intent intentBuscarErrores = new Intent(this, MostrarInfo.class);

        String modo = "BuscarErrores";

        intentBuscarErrores.putExtra(MOSTRARINFO_MODO, modo);

        startActivity(intentBuscarErrores);
    }

    private void mostrarInfoIniciarTramo() {
        Log.d(TAG, "---------- mostrarInfoIniciarTramo --------");

        Intent intentIniciarTramo = new Intent(this, MostrarInfo.class);

        String modo = "IniciarTramo";

        intentIniciarTramo.putExtra(MOSTRARINFO_MODO, modo);

        startActivity(intentIniciarTramo);
    }

    private void mostrarInfoIniciarAnalisis() {
        Log.d(TAG, "---------- mostrarInfoIniciarAnalisis --------");

        Intent intentIniciarAnalisis = new Intent(this, MostrarInfo.class);

        String modo = "IniciarAnalisis";

        intentIniciarAnalisis.putExtra(MOSTRARINFO_MODO, modo);

        startActivity(intentIniciarAnalisis);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "----------onCreateOptionsMenu--------SE USA----I4");
        menu.add(0, SETTINGS, 0, getString(R.string.menu_settings));
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "----------onOptionsItemSelected--------SE USA----S2----E1");
        switch (item.getItemId()) {
            case SETTINGS:
                updateConfig();
                return true;
        }
        return false;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "----------onPrepareOptionsMenu--------SE USA----I5----S1");
        MenuItem settingsItem = menu.findItem(SETTINGS);

        if (service != null && service.isRunning()) {
            settingsItem.setEnabled(false);
        } else {
            settingsItem.setEnabled(true);
        }

        return true;
    }

    public void aniadirCoches(){
        Log.d(TAG, "---------- aniadirCoches --------");

        Intent intentIniciarAnalisis = new Intent(this, CochesYPiezas.class);

        String modo = "AniadirCoches";

        intentIniciarAnalisis.putExtra(EDITORCOCHES_MODO, modo);

        startActivity(intentIniciarAnalisis);
    }

    public void retirarCoches(){
        Log.d(TAG, "---------- quitarCoches --------");

        Intent intentIniciarAnalisis = new Intent(this, CochesYPiezas.class);

        String modo = "QuitarCoches";

        intentIniciarAnalisis.putExtra(EDITORCOCHES_MODO, modo);

        startActivity(intentIniciarAnalisis);
    }

    public void mostrarCoches(){
        Log.d(TAG, "---------- mostrarCoches --------");

        Intent intentIniciarAnalisis = new Intent(this, CochesYPiezas.class);

        String modo = "MostrarCoches";

        intentIniciarAnalisis.putExtra(EDITORCOCHES_MODO, modo);

        startActivity(intentIniciarAnalisis);
    }

    public void aniadirPieza(){
        Log.d(TAG, "---------- aniadirPieza --------");

        Intent intentIniciarAnalisis = new Intent(this, CochesYPiezas.class);

        String modo = "AniadirPieza";

        intentIniciarAnalisis.putExtra(EDITORCOCHES_MODO, modo);

        startActivity(intentIniciarAnalisis);
    }

    public void mostrarPiezas(){
        Log.d(TAG, "---------- mostrarPiezas --------");

        Intent intentIniciarAnalisis = new Intent(this, CochesYPiezas.class);

        String modo = "MostrarPiezas";

        intentIniciarAnalisis.putExtra(EDITORCOCHES_MODO, modo);

        startActivity(intentIniciarAnalisis);
    }
}
