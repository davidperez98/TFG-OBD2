package com.github.pires.obd.reader.activity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.commands.engine.RPMCommand;
import com.github.pires.obd.commands.engine.RuntimeCommand;
import com.github.pires.obd.enums.AvailableCommandNames;
import com.github.pires.obd.reader.R;
import com.github.pires.obd.reader.config.ObdConfig;
import com.github.pires.obd.reader.io.ObdCommandJob;
import com.github.pires.obd.reader.trips.TripLog;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.mostrar_info)
public class MostrarInfo extends BaseAplicacion {
    private static final String TAG = MostrarInfo.class.getName();

    private String modo;

    private int mem_speed[];
    private char point_mem_speed;
    private int mem_rpm[];
    private char point_mem_rpm;

    private boolean hayBotonInformacion;
    private String textBotonInformacion;
    private String textInformacion;
    private boolean hayBotonBuscar;
    private boolean hayBotonComenzarDetener;

    private Button botonInformacion;
    private Button botonBuscar;
    private Button botonComenzar;
    private Button botonDetener;

    private TextView avrg_rpm;
    private TextView avrg_speed;
    private TextView avrg_text_rpm;
    private TextView avrg_text_speed;

    @InjectView(R.id.data_table)
    private TableLayout tl;
    @InjectView(R.id.vehicle_view)
    private LinearLayout vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mem_speed = new int[100];
        point_mem_speed = 0;
        mem_rpm = new int[100];
        point_mem_rpm= 0;
        for(int i=0; i<100; i++){
            mem_speed[i] = -1;
            mem_rpm[i] = -1;
        }

        avrg_rpm = findViewById(R.id.textoAvrgRpm);
        avrg_speed = findViewById(R.id.textoAvrgSpeed);
        avrg_text_rpm = findViewById(R.id.textoAvrgTextRpm);
        avrg_text_speed = findViewById(R.id.textoAvrgTextSpeed);

        btStatusTextView = findViewById(R.id.BT_STATUS);
        obdStatusTextView = findViewById(R.id.OBD_STATUS);

        // Necesario para la busqueda
        final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter != null)
            bluetoothDefaultIsEnable = btAdapter.isEnabled();

        // create a log instance for use by this application
        triplog = TripLog.getInstance(this.getApplicationContext());

        obdStatusTextView.setText(getString(R.string.status_obd_disconnected));

        // Final Necesario para la busqueda

        Intent intent = getIntent();

        modo = intent.getStringExtra(MainActivity.MOSTRARINFO_MODO);

        switch (modo){
            case "BuscarErrores":
                TextView speed = findViewById(R.id.SPEED);
                speed.setVisibility(View.GONE);
                TextView rpm = findViewById(R.id.ENGINE_RPM);
                rpm.setVisibility(View.GONE);
                TextView fuel_conspumption = findViewById(R.id.FUEL_CONSUMPTION);
                fuel_conspumption.setVisibility(View.GONE);
                TextView engine_runtime = findViewById(R.id.ENGINE_RUNTIME);
                engine_runtime.setVisibility(View.GONE);

                hayBotonInformacion = true;
                textBotonInformacion = "¿Como se muestran los errores?";
                textInformacion = "Los errores se muestran en formato 'Troubles codes'. Si no aparecen errores en la pantalla, significa que todo va bien en el vehiculo";
                hayBotonBuscar = true;
                hayBotonComenzarDetener = false;
                break;

            case "IniciarTramo":
                hayBotonInformacion = true;
                textBotonInformacion = "¿Que es un tramo?";
                textInformacion = "Un tramo es un analisis con memoria, es decir, de un analisis en el tiempo se hace la media de sus datos, ofreciendo una imagen global del tramo recorrido";
                hayBotonBuscar = false;
                hayBotonComenzarDetener = true;
                break;

            case "IniciarAnalisis":
                hayBotonInformacion = false;
                textBotonInformacion = "";
                textInformacion = "";
                hayBotonBuscar = false;
                hayBotonComenzarDetener = true;
                break;
        }

        botonInformacion = findViewById(R.id.botonInformacion);
        if(botonInformacion != null){
            if(hayBotonInformacion){
                botonInformacion.setVisibility(View.VISIBLE);
                botonInformacion.setText(textBotonInformacion);
                botonInformacion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MostrarInfo.this, textInformacion, Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                botonInformacion.setVisibility(View.GONE);
            }
        }else{
            Log.d(TAG, "---------- BotonInformacion == NULL ----------");
        }

        botonBuscar = findViewById(R.id.botonBuscar);
        if(botonBuscar != null){
            if(hayBotonBuscar){
                botonBuscar.setVisibility(View.VISIBLE);
                botonBuscar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if((botonBuscar.getText()).equals("Buscar")){
                            botonBuscar.setText("Buscando");
                            startLiveData();
                        }else if((botonBuscar.getText()).equals("Buscando")){
                            botonBuscar.setText("Buscar");
                            stopLiveData();
                        }else{
                            Log.d(TAG, "---------- Fallo BotonBuscar Texto ----------");
                        }
                    }
                });
            }else{
                botonBuscar.setVisibility(View.GONE);
            }
        }else{
            Log.d(TAG, "---------- BotonInformacion == NULL ----------");
        }

        botonComenzar = findViewById(R.id.botonComenzar);
        botonDetener = findViewById(R.id.botonDetener);
        if(botonComenzar != null && botonDetener != null){
            if(hayBotonComenzarDetener){
                botonComenzar.setVisibility(View.VISIBLE);
                botonDetener.setVisibility(View.GONE);
                botonComenzar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(modo.equals("IniciarTramo")){
                            avrg_rpm.setVisibility(View.GONE);
                            avrg_speed.setVisibility(View.GONE);
                            avrg_text_rpm.setVisibility(View.GONE);
                            avrg_text_speed.setVisibility(View.GONE);
                        }
                        botonComenzar.setVisibility(View.GONE);
                        tl.removeAllViews();
                        startLiveData();
                        botonDetener.setVisibility(View.VISIBLE);
                    }
                });
                botonDetener.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        botonDetener.setVisibility(View.GONE);
                        Log.d(TAG, "---------- IF BotonDetener --> Modo = " + modo + " ----------");

                        if(modo.equals("IniciarTramo")){
                            Log.d(TAG, "---------- IF BotonDetener --> DetenerAnalisis() ----------");
                            detenerAnalisis();
                        }else{
                            Log.d(TAG, "---------- IF BotonDetener --> StopLiveData() ----------");
                            stopLiveData();
                        }
                        botonComenzar.setVisibility(View.VISIBLE);
                    }
                });
            }else{
                botonComenzar.setVisibility(View.GONE);
                botonDetener.setVisibility(View.GONE);
            }
        }else{
            if(botonComenzar == null){
                Log.d(TAG, "---------- BotonComenzar == NULL ----------");
            }
            if(botonDetener == null){
                Log.d(TAG, "---------- BotonDetener == NULL ----------");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "----------onStart--------SE USA----I2----E10");
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

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "----------onPause--------SE USA----E8----E12----E14");
        releaseWakeLockIfHeld();
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
    protected void addTableRow(String id, String key, String val) {
        Log.d(TAG, "----------addTableRow--------SE USA----A3----A7");

        TableRow tr = new TableRow(this);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(TABLE_ROW_MARGIN, TABLE_ROW_MARGIN, TABLE_ROW_MARGIN,
                TABLE_ROW_MARGIN);
        tr.setLayoutParams(params);

        TextView name = new TextView(this);
        name.setGravity(Gravity.RIGHT);
        name.setText(key + ": ");
        TextView value = new TextView(this);
        value.setGravity(Gravity.LEFT);
        value.setText(val);
        value.setTag(id);
        tr.addView(name);
        tr.addView(value);
        tl.addView(tr, params);
    }

    @Override
    public void stateUpdate(final ObdCommandJob job) {
        Log.d(TAG, "----------stateUpdate--------SE USA----A1----A5----E5");
        final String cmdName = job.getCommand().getName();
        String cmdResult = "";
        final String cmdID = LookUpCommand(cmdName);

        if (job.getState().equals(ObdCommandJob.ObdCommandJobState.EXECUTION_ERROR)) {
            cmdResult = job.getCommand().getResult();
            if (cmdResult != null && isServiceBound) {
                obdStatusTextView.setText(cmdResult.toLowerCase());
            }
        } else if (job.getState().equals(ObdCommandJob.ObdCommandJobState.BROKEN_PIPE)) {
            if (isServiceBound)
                stopLiveData();
        } else if (job.getState().equals(ObdCommandJob.ObdCommandJobState.NOT_SUPPORTED)) {
            cmdResult = getString(R.string.status_obd_no_support);
        } else {
            cmdResult = job.getCommand().getFormattedResult();
            if (isServiceBound)
                obdStatusTextView.setText(getString(R.string.status_obd_data));
        }

        if (vv.findViewWithTag(cmdID) != null) {
            TextView existingTV = (TextView) vv.findViewWithTag(cmdID);
            existingTV.setText(cmdResult);
        } else addTableRow(cmdID, cmdName, cmdResult);
        commandResult.put(cmdID, cmdResult);
        updateTripStatistic(job, cmdID);
    }

    @Override
    protected void updateTripStatistic(final ObdCommandJob job, final String cmdID) {
        if (currentTrip != null) {
            if (cmdID.equals(AvailableCommandNames.SPEED.toString())) {
                SpeedCommand command = (SpeedCommand) job.getCommand();
                currentTrip.setSpeedMax(command.getMetricSpeed());

                if(modo.equals("IniciarTramo")){
                    mem_speed[point_mem_speed] = command.getMetricSpeed();
                    point_mem_speed++;
                    point_mem_speed%=100;
                }
            } else if (cmdID.equals(AvailableCommandNames.ENGINE_RPM.toString())) {
                RPMCommand command = (RPMCommand) job.getCommand();
                currentTrip.setEngineRpmMax(command.getRPM());

                if(modo.equals("IniciarTramo")){
                    mem_rpm[point_mem_rpm] = command.getRPM();
                    point_mem_rpm++;
                    point_mem_rpm%=100;
                }
            } else if (cmdID.endsWith(AvailableCommandNames.ENGINE_RUNTIME.toString())) {
                RuntimeCommand command = (RuntimeCommand) job.getCommand();
                currentTrip.setEngineRuntime(command.getFormattedResult());
            }
        }
    }

    protected void detenerAnalisis(){
        Log.d(TAG, "---------- detenerAnalisis() ----------");

        stopLiveData();

        int temp_rpm = 0;
        if(mem_rpm[point_mem_rpm] != -1){
            for(int i=0; i<100; i++){
                temp_rpm += mem_rpm[i];
                mem_rpm[i] = -1;
            }
            temp_rpm /= 100;
        }else{
            for(int i=0; i<point_mem_rpm; i++){
                temp_rpm += mem_rpm[i];
                mem_rpm[i] = -1;
            }
            temp_rpm /= point_mem_rpm;
        }
        point_mem_rpm = 0;

        int temp_speed = 0;
        if(mem_speed[point_mem_speed] != -1){
            for(int i=0; i<100; i++){
                temp_speed += mem_speed[i];
                mem_speed[i] = -1;
            }
            temp_speed/=100;
        }else{
            for(int i=0; i<point_mem_speed; i++){
                temp_speed += mem_speed[i];
                mem_speed[i] = -1;
            }
            temp_speed/=point_mem_speed;
        }
        point_mem_speed = 0;

        avrg_rpm.setVisibility(View.VISIBLE);
        avrg_speed.setVisibility(View.VISIBLE);
        avrg_text_rpm.setVisibility(View.VISIBLE);
        avrg_text_speed.setVisibility(View.VISIBLE);

        avrg_rpm.setText(String.valueOf(temp_rpm));
        avrg_speed.setText(String.valueOf(temp_speed));
    }

    @Override
    protected void queueCommands() {
        Log.d(TAG, "---------- MostrarInfo --> queueCommands --------");

        if (isServiceBound) {
            if(modo.equals("BuscarErrores")){
                Log.d(TAG, "---------- MostrarInfo --> queueCommands --> BuscarErrores --------");

                for (ObdCommand Command : ObdConfig.getErrorCommands()) {
                    if (prefs.getBoolean(Command.getName(), true))
                        service.queueJob(new ObdCommandJob(Command));
                }
            }else{
                Log.d(TAG, "---------- MostrarInfo --> queueCommands --> IniciarTramo / IniciarAnalisis --------");

                for (ObdCommand Command : ObdConfig.getCommands()) {
                    if (prefs.getBoolean(Command.getName(), true))
                        service.queueJob(new ObdCommandJob(Command));
                }
            }
        }
    }
}
