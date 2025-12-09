package com.example.mediconnect.model;

public class DokterModel {
    private String nama;
    private String spesialis;
    private int foto;
    private String deskripsi;

    public DokterModel(String nama, String spesialis, int foto, String deskripsi) {
        this.nama = nama;
        this.spesialis = spesialis;
        this.foto = foto;
        this.deskripsi = deskripsi;
    }

    public String getNama() { return nama; }
    public String getSpesialis() { return spesialis; }
    public int getFoto() { return foto; }
    public String getDeskripsi() { return deskripsi; }
}
