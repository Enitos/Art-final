package com.somada.lope_mora.art3.model;

public class Paint {
    private String name;
    private String image;
    private String artista;
    private String tamanho;
    private String periodos;
    private String material;
    private int visitor;
    private String beaconName;
    private int id;
    private String photoAutor;

    public Paint() {
    }

    public Paint(String name, String photoAutor, String image, String artista, String tamanho, String periodos, String material, int id, String beaconName, int visitor) {
        this.name = name;
        this.image = image;
        this.artista = artista;
        this.tamanho = tamanho;
        this.periodos = periodos;
        this.material = material;
        this.id = id;
        this.beaconName = beaconName;
        this.visitor = visitor;
        this.photoAutor = photoAutor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getPeriodos() {
        return periodos;
    }

    public void setPeriodos(String periodos) {
        this.periodos = periodos;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }

    public int getVisitor() {
        return visitor;
    }

    public void setVisitor(int visitor) {
        this.visitor = visitor;
    }

    public String getPhotoAutor() {
        return photoAutor;
    }

    public void setPhotoAutor(String photoAutor) {
        this.photoAutor = photoAutor;
    }
}
