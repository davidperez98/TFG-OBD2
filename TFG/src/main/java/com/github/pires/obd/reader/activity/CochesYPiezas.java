package com.github.pires.obd.reader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pires.obd.reader.R;
import java.util.ArrayList;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.gestionar_coches)
public class CochesYPiezas extends RoboActivity {
    private static final String TAG = MostrarInfo.class.getName();

    private String modo;

    private TextView textoPortadaCochesPiezas;
    private EditText textoEditMatricula;
    private EditText textoEditMarcaYModelo;
    private EditText textoEditAnioCoche;
    private EditText textoEditKmCoche;
    private EditText textoEditNombrePieza;
    private EditText textoEditCostePieza;
    private EditText textoEditKmPieza;
    private EditText textoEditDatePieza;
    private Button botonAniadirCoches;
    private Button botonQuitarCoches;
    private Button botonAniadirPieza;
    private Button botonMostrarPiezas;
    private ScrollView dataScrollVehiculosPiezas;
    private TableLayout dataTableVehiculosPiezas;
    private TableRow tablaKmPieza;
    private TextView textoKmRecambioMostrar;
    private TextView textoProximaPiezaMostrar;

    // ---------------------------------- editor ------------------------------
    /*private ArrayList<String> matriculas;

    private ArrayList<String> piezas;
    private String proximaPieza;
    private int kmProximaPieza;

    private PiezasIndexKm indexKm;
    private PiezasIndexAnios indexAnios;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "----------onCreate--------SE USA----I1");

        Intent intent = getIntent();
        modo = intent.getStringExtra(MainActivity.EDITORCOCHES_MODO);

        textoPortadaCochesPiezas = findViewById(R.id.textoPortadaCochesPiezas);
        textoEditMatricula = findViewById(R.id.textoEditMatricula);
        textoEditMarcaYModelo = findViewById(R.id.textoEditMarcaYModelo);
        textoEditAnioCoche = findViewById(R.id.textoEditAnioCoche);
        textoEditKmCoche = findViewById(R.id.textoEditKmCoche);
        textoEditNombrePieza = findViewById(R.id.textoEditNombrePieza);
        textoEditCostePieza = findViewById(R.id.textoEditCostePieza);
        textoEditKmPieza = findViewById(R.id.textoEditKmPieza);
        textoEditDatePieza = findViewById(R.id.textoEditDatePieza);
        botonAniadirCoches = findViewById(R.id.botonAniadirCoches);
        botonQuitarCoches = findViewById(R.id.botonQuitarCoches);
        botonAniadirPieza = findViewById(R.id.botonAniadirPieza);
        botonMostrarPiezas = findViewById(R.id.botonMostrarPiezas);
        dataScrollVehiculosPiezas = findViewById(R.id.dataScrollVehiculosPiezas);
        dataTableVehiculosPiezas = findViewById(R.id.dataTableVehiculosPiezas);
        tablaKmPieza = findViewById(R.id.tablaKmPieza);
        textoKmRecambioMostrar = findViewById(R.id.textoKmRecambioMostrar);
        textoProximaPiezaMostrar = findViewById(R.id.textoProximaPiezaMostrar);

        //--------------------------------------- editor --------------------------------
        /*piezas = new ArrayList<String>();
        matriculas = new ArrayList<String>();
        kmProximaPieza = 999999;
        indexKm = new PiezasIndexKm();
        indexAnios = new PiezasIndexAnios();*/

        switch(modo){
            case "AniadirCoches":
                textoPortadaCochesPiezas.setText("Añadir Coche nuevo");

                textoEditMarcaYModelo.setVisibility(View.VISIBLE);
                textoEditAnioCoche.setVisibility(View.VISIBLE);
                textoEditKmCoche.setVisibility(View.VISIBLE);
                botonAniadirCoches.setVisibility(View.VISIBLE);
                botonAniadirCoches.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        aniadirCoches();
                    }
                });
                break;

            case "QuitarCoches":
                textoPortadaCochesPiezas.setText("Quitar Coche de La Lista");

                botonQuitarCoches.setVisibility(View.VISIBLE);
                botonQuitarCoches.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        quitarCoches();
                    }
                });
                break;

            case "MostrarCoches":
                textoPortadaCochesPiezas.setText("Mostrar todos los Coches");

                textoEditMatricula.setVisibility(View.GONE);
                dataScrollVehiculosPiezas.setVisibility(View.VISIBLE);

                mostrarCoches();
                break;

            case "AniadirPiezas":
                textoPortadaCochesPiezas.setText("Añadir Piezas a un Coche");

                textoEditNombrePieza.setVisibility(View.VISIBLE);
                textoEditCostePieza.setVisibility(View.VISIBLE);
                textoEditKmPieza.setVisibility(View.VISIBLE);
                textoEditDatePieza.setVisibility(View.VISIBLE);
                botonAniadirPieza.setVisibility(View.VISIBLE);
                botonAniadirPieza.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        aniadirPiezas();
                    }
                });
                break;

            case "MostrarPiezas":
                textoPortadaCochesPiezas.setText("Mostrar las Piezas de un Coche");

                botonMostrarPiezas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mostrarPiezas();
                    }
                });
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "----------onStart--------SE USA----I2----E10");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "----------onDestroy--------SE USA----E14");
    }

    private void addTableRow(String fila){
        TableRow tr = new TableRow(this);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(7, 7, 7, 7);
        tr.setLayoutParams(params);

        TextView filaTemporal = new TextView(this);
        filaTemporal.setGravity(Gravity.RIGHT);
        filaTemporal.setText(fila);
        tr.addView(filaTemporal);
        dataTableVehiculosPiezas.addView(tr, params);
    }

    private void aniadirCoches(){
        String matricula = textoEditMatricula.getText().toString();
        String marcaModelo = textoEditMarcaYModelo.getText().toString();
        String anio = textoEditAnioCoche.getText().toString();
        String km = textoEditKmCoche.getText().toString();

        try{
            int temp = Integer.valueOf(km);
        }catch (Exception e){
            Toast.makeText(CochesYPiezas.this, "Los Kilometros puestos tienen letras y ha fallado la prediccion, se le colocara a cuantos kilometros necesita el cambio", Toast.LENGTH_LONG).show();
        }

        escribirArchivoGlobal(matricula, marcaModelo, anio, km);

        textoPortadaCochesPiezas.setText("Coche añadido con exito");
        textoEditMatricula.setVisibility(View.GONE);
        textoEditMarcaYModelo.setVisibility(View.GONE);
        textoEditAnioCoche.setVisibility(View.GONE);
        textoEditKmCoche.setVisibility(View.GONE);
        botonAniadirCoches.setVisibility(View.GONE);
    }

    private void quitarCoches(){
        String matricula = textoEditMatricula.getText().toString();

        textoEditMatricula.setVisibility(View.GONE);
        botonQuitarCoches.setVisibility(View.GONE);

        if(borrarArchivoGlobal(matricula)){
            textoPortadaCochesPiezas.setText("El coche con matricula " + matricula + " ha sido borrado");
        }else{
            textoPortadaCochesPiezas.setText("El coche con matricula " + matricula + " no existe en el archivo");
        }
    }

    private void mostrarCoches(){
        ArrayList<String> coches = leerArchivoGlobal();

        dataTableVehiculosPiezas.removeAllViews();
        for(int i=0; i<coches.size(); i++){
            addTableRow(coches.get(i));
        }
    }

    private void aniadirPiezas(){
        String matricula = textoEditMatricula.getText().toString();
        String nombre = textoEditNombrePieza.getText().toString();
        String coste = textoEditCostePieza.getText().toString();
        String km = textoEditKmPieza.getText().toString();
        String date = textoEditDatePieza.getText().toString();

        try{
            int temp = Integer.valueOf(km);
        }catch (Exception e){
            Toast.makeText(CochesYPiezas.this, "Los Kilometros puestos tienen letras y ha fallado la prediccion, se le colocara a cuantos kilometros necesita el cambio", Toast.LENGTH_LONG).show();
        }

        if(escribirArchivoRelativoX(matricula, nombre, coste, km, date)){
            textoPortadaCochesPiezas.setText("Pieza añadida con exito");
            textoEditMatricula.setVisibility(View.GONE);
            textoEditNombrePieza.setVisibility(View.GONE);
            textoEditCostePieza.setVisibility(View.GONE);
            textoEditKmPieza.setVisibility(View.GONE);
            textoEditDatePieza.setVisibility(View.GONE);
            botonAniadirPieza.setVisibility(View.GONE);
        }else{
            textoPortadaCochesPiezas.setText("El coche con matricula " + matricula + " no existe en el archivo");
        }
    }

    private void mostrarPiezas(){
        String matricula = textoEditMatricula.getText().toString();

        if(leerPiezasVehiculoX(matricula)){
            dataTableVehiculosPiezas.removeAllViews();
            for(int i=0; i<piezas.size(); i++){
                addTableRow(piezas.get(i));
            }

            textoEditMatricula.setVisibility(View.GONE);
            botonMostrarPiezas.setVisibility(View.GONE);
            textoPortadaCochesPiezas.setText("Piezas de " + matricula);
            dataScrollVehiculosPiezas.setVisibility(View.VISIBLE);
            tablaKmPieza.setVisibility(View.VISIBLE);
            textoKmRecambioMostrar.setText(kmProximaPieza);
            textoProximaPiezaMostrar.setVisibility(View.VISIBLE);
            textoProximaPiezaMostrar.setText(proximaPieza);
        }else{
            textoPortadaCochesPiezas.setText("El coche con matricula " + matricula + " no existe en el archivo");
        }
    }

    // ------------------------------ Editor -------------------------------
    /*private void escribirArchivoGlobal(String matricula, String marcaModelo, String anio, String km){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG,"Permission is granted");
        } else {
            Log.v(TAG,"Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        BufferedWriter bw = null;
        FileWriter fw = null;
        String fileName = matricula + "Piezas.txt";
        String coche = matricula + ";" + marcaModelo + ";" + anio;

        try{
            int kmtemp = Integer.valueOf(km);

            coche = coche + ";" + km;
        }catch (Exception e){
            coche = coche + ";0";
        }

        try {
            String dir = CochesYPiezas.class.getResource("/").getFile();
            File file = new File(dir + "/Coches.txt");
            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(coche);

            File fileMatricula = new File(dir + "/" + fileName);
            // Si el archivo no existe, se crea!
            if (!fileMatricula.exists()) {
                fileMatricula.createNewFile();
            }

            matriculas.add(matricula);

            System.out.println("información agregada!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private ArrayList<String> leerArchivoGlobal(){
        ArrayList<String> coches = new ArrayList<String>();
        BufferedReader br = null;
        FileReader fr = null;

        try{
            File file = new File("Coches.txt");
            fr = new FileReader(file.getAbsoluteFile());
            br = new BufferedReader(fr);
        }catch (Exception e){
            e.printStackTrace();
        }

        String cocheTemporal;
        String[] cocheTemporalSeparada;
        boolean seguir = true;
        while(seguir == true){
            try{
                cocheTemporal = br.readLine();
                cocheTemporalSeparada = cocheTemporal.split("//;");
                coches.add(cocheTemporalSeparada[0] + " " + cocheTemporalSeparada[1] + " " + cocheTemporalSeparada[2] + " " + cocheTemporalSeparada[3]);
            }catch (Exception e){
                seguir = false;
            }
        }

        try {
            //Cierra instancias de FileReader y BufferedReader
            if (br != null)
                br.close();
            if (fr != null)
                fr.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return coches;
    }

    private ArrayList<String[]> leerArchivoGlobalSeparado(){
        ArrayList<String[]> cochesSeparados = new ArrayList<String[]>();
        BufferedReader br = null;
        FileReader fr = null;

        try{
            File file = new File("Coches.txt");
            fr = new FileReader(file.getAbsoluteFile());
            br = new BufferedReader(fr);
        }catch (Exception e){
            e.printStackTrace();
        }

        String cocheTemporal;
        String[] cocheTemporalSeparada;
        boolean seguir = true;
        while(seguir == true){
            try{
                cocheTemporal = br.readLine();
                cocheTemporalSeparada = cocheTemporal.split("//;");
                cochesSeparados.add(cocheTemporalSeparada);
            }catch (Exception e){
                seguir = false;
            }
        }

        try {
            //Cierra instancias de FileReader y BufferedReader
            if (br != null)
                br.close();
            if (fr != null)
                fr.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return cochesSeparados;
    }

    private boolean borrarArchivoGlobal(String matricula){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG,"Permission is granted");
        } else {
            Log.v(TAG,"Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        boolean existe = matriculas.contains(matricula);

        if(existe){
            ArrayList<String[]> cochesSeparados = leerArchivoGlobalSeparado();

            try{
                File file = new File("Coches.txt");
                if(file.exists()){
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            String[] cocheTemporal;
            for(int i=0; i<cochesSeparados.size(); i++){
                cocheTemporal = cochesSeparados.get(i);
                if(!cocheTemporal[0].equals(matricula)){
                    escribirArchivoGlobal(cocheTemporal[0], cocheTemporal[1], cocheTemporal[2], cocheTemporal[3]);
                }
            }

            try{
                File file = new File(matricula + "Piezas.txt");
                if(file.exists()){
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            matriculas.remove(matricula);

            return true;
        }

        return false;
    }

    private boolean escribirArchivoRelativoX(String matricula, String nombre, String coste, String km, String fecha){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG,"Permission is granted");
        } else {
            Log.v(TAG,"Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        boolean existe = matriculas.contains(matricula);

        if(existe){
            BufferedWriter bw = null;
            FileWriter fw = null;
            String filePath = matricula + "Piezas.txt";
            String pieza = nombre + ";" + coste + ";" + km;

            int kmSiguiente = 0;
            try{
                kmSiguiente = Integer.valueOf(km);
            }catch(Exception e){}
            int aniosSiguiente = 0;
            switch (nombre){
                case "Aceite":
                    kmSiguiente += indexKm.getAceite();
                    aniosSiguiente = indexAnios.getAceite();
                    break;
                case "Bujías":
                    kmSiguiente += indexKm.getBujias();
                    aniosSiguiente = indexAnios.getBujias();
                    break;
                case "Correa de transmision":
                    kmSiguiente += indexKm.getCorrea_de_Transmision();
                    aniosSiguiente = indexAnios.getCorrea_de_Transmision();
                    break;
                case "Filtro del aceite":
                    kmSiguiente += indexKm.getFiltro_del_Aceite();
                    aniosSiguiente = indexAnios.getFiltro_del_Aceite();
                    break;
                case "Filtro del aire":
                    kmSiguiente += indexKm.getFiltro_del_Aire();
                    aniosSiguiente = indexAnios.getFiltro_del_Aire();
                    break;
                case "Filtro del diesel":
                    kmSiguiente += indexKm.getFiltro_del_Diesel();
                    aniosSiguiente = indexAnios.getFiltro_del_Diesel();
                    break;
                case "Frenos":
                    kmSiguiente += indexKm.getFrenos();
                    aniosSiguiente = indexAnios.getFrenos();
                    break;
                case "Inyectores":
                    kmSiguiente += indexKm.getIyectores();
                    aniosSiguiente = indexAnios.getIyectores();
                    break;
                case "Ruedas":
                    kmSiguiente += indexKm.getRuedas();
                    aniosSiguiente = indexAnios.getRuedas();
                    break;
            }
            pieza = pieza + ";" + String.valueOf(kmSiguiente) + ";" + fecha + ";" + String.valueOf(aniosSiguiente);

            try {
                File file = new File(filePath);
                // Si el archivo no existe, salta una excepcion
                // flag true, indica adjuntar información al archivo.
                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                bw.write(pieza);

                System.out.println("información agregada!");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    //Cierra instancias de FileWriter y BufferedWriter
                    if (bw != null)
                        bw.close();
                    if (fw != null)
                        fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Se lee y muestran todas las piezas cambiadas del vehiculo cuya matricula es dada, y se calcula la pieza mas proxima al cambio y su kilometraje esperado
     * @param matricula La matricula del vehiculo al que queremos extraer las piezas
     * @return Los kilometros a los que se debe cambiar la proxima pieza
     */
    /*private boolean leerPiezasVehiculoX(String matricula){
        boolean existe = matriculas.contains(matricula);

        if(existe){
            BufferedReader br = null;
            FileReader fr = null;
            String filePath = matricula + "Piezas.txt";

            try{
                File file = new File(filePath);
                fr = new FileReader(file.getAbsoluteFile());
                br = new BufferedReader(fr);
            }catch (Exception e){
                e.printStackTrace();
            }

            String piezaTemporal;
            String[] piezaTemporalSeparada;
            int kmPiezaTemporal = 0;
            boolean seguir = true;

            while(seguir == true){
                try{
                    piezaTemporal = br.readLine();
                    piezaTemporalSeparada = piezaTemporal.split("//;");
                    piezas.add(piezaTemporalSeparada[0] + " " + piezaTemporalSeparada[1] + " " + piezaTemporalSeparada[2] + " " + piezaTemporalSeparada[3] + " " + piezaTemporalSeparada[4] + " " + piezaTemporalSeparada[5]);
                    kmPiezaTemporal = Integer.valueOf(piezaTemporalSeparada[3]);

                    if(kmPiezaTemporal < kmProximaPieza){
                        kmProximaPieza = kmPiezaTemporal;
                        proximaPieza = piezaTemporalSeparada[0] + " " + piezaTemporalSeparada[1] + " " + piezaTemporalSeparada[2] + " " + piezaTemporalSeparada[3] + " " + piezaTemporalSeparada[4] + " " + piezaTemporalSeparada[5];
                    }
                }catch (Exception e){
                    seguir = false;
                }
            }

            try {
                //Cierra instancias de FileReader y BufferedReader
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return true;
        }

        return false;
    }*/
}
