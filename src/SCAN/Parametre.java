package SCAN;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Parametre {
    static Double cena,cenam2,uzitplocha = 0.00;
    static String lokalita,ulica,material,stav,aktualizacia,titulka,popis,druh_transakcie,link = "";

    static Integer identcislo;
    static Integer poschodie;
    static Integer pocetPoschodi;
    static Integer idInzeratu;
    static Integer idServer;
    static Integer typ_nehnutelnosti_id;
    static Boolean balkon,pivnica,vytah = false;

    public static String getLink() {
        return link;
    }

    public static void setLink(String link) {
        Parametre.link = link;
    }

    public static Integer getIdInzeratu() {
        return idInzeratu;
    }

    public static String getTitulka() {
        return titulka;
    }

    public static String getDruh_transakcie() {
        return druh_transakcie;
    }


    public static void setDruh_transakcie(String druh_transakcie) {
        String validretazec = "";
        Integer dlzkaRetazca1 = druh_transakcie.length();
        Pattern pattern = Pattern.compile("/");
        Matcher matcher = pattern.matcher(druh_transakcie);
        if (matcher.find()) {
            validretazec = druh_transakcie.substring(matcher.start() + 1, dlzkaRetazca1).trim();
        }
        Parametre.druh_transakcie = validretazec;
    }

    public static void setTitulka(String titulka) {
        Parametre.titulka = titulka;
    }

    public static void setIdInzeratu(Integer idInzeratu) {
        Parametre.idInzeratu = idInzeratu;
    }

    public static Integer getIdServer() {
        return idServer;
    }

    public static String getPopis() {
        return popis;
    }

    public static void setPopis(String popis) {
        Parametre.popis = popis;
    }

    public static void setIdServer(Integer idServer) {
        Parametre.idServer = idServer;
    }

    public static void setPoschodie(Integer poschodie) {
        Parametre.poschodie = poschodie;
    }

    public static Integer getTyp_nehnutelnosti_id() {
        return typ_nehnutelnosti_id;
    }

    public static void setTyp_nehnutelnosti_id(String typ_nehnutelnosti) {
        String validretazec = "";
        Integer dlzkaRetazca1 = typ_nehnutelnosti.length();
        Pattern pattern = Pattern.compile("/");
        Matcher matcher = pattern.matcher(typ_nehnutelnosti);
        if (matcher.find()) {
            validretazec = typ_nehnutelnosti.substring(0,matcher.start()).trim();
        }
        Integer id = 0;
        switch (validretazec){
            case "Garsónka":
                id=2;
                break;
            case "1 izbový byt":
                id=3;
                break;
            case "2 izbový byt":
                id=4;
                break;
            case "3 izbový byt":
                id=5;
                break;
            case "4 izbový byt":
                id=6;
                break;
            case "5 izbový byt":
                id=7;
                break;
            case "Rodinný dom":
                id=8;
                break;
            case "Pozemok pre rodinné domy":
                id=9;
                break;
            case "Rekreačný pozemok":
                id=10;
                break;

        }
        Parametre.typ_nehnutelnosti_id = id;
    }

    public static Integer getPoschodie() {
        return poschodie;
    }

    public static void setPoschodie(String poschodie) {
        Integer nPocetPoschodi = 0;
        Integer nPoschodie = 0;

        Pattern pattern = Pattern.compile("/");
        Matcher matcher = pattern.matcher(poschodie);

        if (matcher.find()){
            String valPoschodie = poschodie.substring(0,matcher.start()).trim();
            String valPocetPoschodi = poschodie.substring(matcher.end(),poschodie.length()).trim();
             nPoschodie = parseInt(valPoschodie);
             nPocetPoschodi = parseInt(valPocetPoschodi);
        }

        Parametre.setPocetPoschodi (nPocetPoschodi);
        Parametre.poschodie = nPoschodie;
    }

    public static Integer getPocetPoschodi() {
        return pocetPoschodi;
    }

    public static void setPocetPoschodi(Integer pocetPoschodi) {
        Parametre.pocetPoschodi = pocetPoschodi;
    }



    public Parametre() {
    }

    public static String getLokalita() {
        return lokalita;
    }

    public void setLokalita(String lokalita) {
        this.lokalita = lokalita;
    }

    public static String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public static String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public static String getStav() {
        return stav;
    }

    public void setStav(String stav) {
        this.stav = stav;
    }

    public static String getAktualizacia() {
        return aktualizacia;
    }

    /**
     *
     * @param aktualizacia
     */
    public void setAktualizacia(String aktualizacia) {
        this.aktualizacia = aktualizacia;
    }

    public static Integer getIdentcislo() {
        return identcislo;
    }

    public void setIdentcislo(Integer identcislo) {
        this.identcislo = identcislo;
    }

    public static Boolean getBalkon() {
        return balkon;
    }

    public void setBalkon(Boolean balkon) {
        this.balkon = balkon;
    }

    public static Boolean getPivnica() {
        return pivnica;
    }

    public void setPivnica(Boolean pivnica) {
        this.pivnica = pivnica;
    }

    public static Boolean getVytah() {
        return vytah;
    }

    public void setVytah(Boolean vytah) {
        this.vytah = vytah;
    }

    public static Double getCena() {
        return cena;
    }

    /**
     *
     * @param cena
     */
    public void setCena(Double cena) {
        this.cena = cena;
    }

    public static Double getCenam2() {
        return cenam2;
    }

    public void setCenam2(Double cenam2) {
        this.cenam2 = cenam2;
    }

    public static Double getUzitplocha() {
        return uzitplocha;
    }

    public void setUzitplocha(Double uzitplocha) {
        this.uzitplocha = uzitplocha;
    }


}
