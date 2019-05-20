package Models;


import javafx.scene.control.Hyperlink;

public class ModelNehnutelnosti {
    Integer id,server_id,idnaserveri,typ_nehnutelnosti_id, balkon, lokalita_id;
    Double cena, cenam2;
    String nazov,druh_transakcie,titulka,aktualizacia,lokalita;
    String nazovTypu,linkserver;
    Hyperlink hplinkserver;


    public ModelNehnutelnosti(Integer id, String nazov, Integer idnaserveri, String linkserver, String aktualizacia, String lokalita,
                              String druh_transakcie,String nazovTypu, Double cena, Double cenam2,String titulka, Integer lokalita_id) {
        this.id = id;
        this.server_id = server_id;
        this.idnaserveri = idnaserveri;
        this.linkserver = linkserver;
        this.aktualizacia = aktualizacia;
        this.lokalita = lokalita;
        this.typ_nehnutelnosti_id = typ_nehnutelnosti_id;
        this.cena = cena;
        this.nazov = nazov;
        this.cenam2 = cenam2;
        this.druh_transakcie = druh_transakcie;
        this.lokalita_id = lokalita_id;
        this.titulka = titulka;
        this.nazovTypu = nazovTypu;
    }

    public Integer getBalkon() {
        return balkon;
    }

    public void setBalkon(Integer balkon) {
        this.balkon = balkon;
    }

    public Integer getLokalita_id() {
        return lokalita_id;
    }

    public void setLokalita_id(Integer lokalita_id) {
        this.lokalita_id = lokalita_id;
    }

    public Hyperlink getLinkserver() {
        return hplinkserver = new Hyperlink(linkserver);
    }

    public void setLinkserver(String linkserver) {
        this.linkserver = linkserver;
    }

    public Double getCenam2() { return cenam2; }

    public void setCenam2(Double cenam2) {
        this.cenam2 = cenam2;
    }

    public Hyperlink getHplinkserver() {
        return hplinkserver;
    }

    public void setHplinkserver(Hyperlink hplinkserver) {
        this.hplinkserver = hplinkserver;
    }

    public String getAktualizacia() {
        return aktualizacia;
    }

    public void setAktualizacia(String aktualizacia) {
        this.aktualizacia = aktualizacia;
    }

    public String getLokalita() {
        return lokalita;
    }

    public void setLokalita(String lokalita) {
        this.lokalita = lokalita;
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
