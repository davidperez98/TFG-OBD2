package com.github.pires.obd.reader.config;

public class PiezasIndexKm {
    private int Aceite;
    private int Bujias;
    private int Correa_de_Transmision;
    private int Filtro_del_Aire;
    private int Filtro_del_Aceite;
    private int Filtro_del_Diesel;
    private int Frenos;
    private int Iyectores;
    private int Ruedas;

    public PiezasIndexKm() {
        Aceite = 0;
        Bujias = 0;
        Correa_de_Transmision = 0;
        Filtro_del_Aire = 0;
        Filtro_del_Aceite = 0;
        Filtro_del_Diesel = 0;
        Frenos = 0;
        Iyectores = 0;
        Ruedas = 0;
    }

    public int getAceite() {
        return Aceite;
    }

    public int getBujias() {
        return Bujias;
    }

    public int getCorrea_de_Transmision() {
        return Correa_de_Transmision;
    }

    public int getFiltro_del_Aire() {
        return Filtro_del_Aire;
    }

    public int getFiltro_del_Aceite() {
        return Filtro_del_Aceite;
    }

    public int getFiltro_del_Diesel() {
        return Filtro_del_Diesel;
    }

    public int getFrenos() {
        return Frenos;
    }

    public int getIyectores() {
        return Iyectores;
    }

    public int getRuedas() {
        return Ruedas;
    }
}
