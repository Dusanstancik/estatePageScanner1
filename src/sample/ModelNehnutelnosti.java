package sample;




public class ModelNehnutelnosti {
    Integer id,server_id,idnaserveri,typ_nehnutelnosti_id;
    Double cena;
    String nazov,druh_transakcie,titulka;
    String nazovTypu;


    public ModelNehnutelnosti(Integer id, String nazov, Integer idnaserveri, String druh_transakcie,String nazovTypu, Double cena, String titulka) {
        this.id = id;
        this.server_id = server_id;
        this.idnaserveri = idnaserveri;
        this.typ_nehnutelnosti_id = typ_nehnutelnosti_id;
        this.cena = cena;
        this.nazov = nazov;

        this.druh_transakcie = druh_transakcie;
        this.titulka = titulka;
        this.nazovTypu = nazovTypu;
    }


    public Integer getIdnaserveri() {
        return idnaserveri;
    }


    public String getNazovTypu() {
        return nazovTypu;
    }

    public void setNazovTypu(String nazovTypu) {
        this.nazovTypu = nazovTypu;
    }

    public void setIdnaserveri(Integer idnaserveri) {
        this.idnaserveri = idnaserveri;
    }

    public Integer getTyp_nehnutelnosti_id() {
        return typ_nehnutelnosti_id;
    }

    public void setTyp_nehnutelnosti_id(Integer typ_nehnutelnosti_id) {
        this.typ_nehnutelnosti_id = typ_nehnutelnosti_id;
    }

    public String getDruh_transakcie() {
        return druh_transakcie;
    }

    public void setDruh_transakcie(String druh_transakcie) {
        this.druh_transakcie = druh_transakcie;
    }

    public String getTitulka() {
        return titulka;
    }

    public void setTitulka(String titulka) {
        this.titulka = titulka;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServer_id() {
        return server_id;
    }

    public void setServer_id(Integer server_id) {
        this.server_id = server_id;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }
}
