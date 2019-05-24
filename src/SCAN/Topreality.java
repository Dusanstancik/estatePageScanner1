/*
 * Copyright (c) 2019. Dušan Stančík
 */

package SCAN;

import DB.Database;
import javafx.concurrent.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static SCAN.Parametre.*;
import static SCAN.Parametre.setIdInzeratu;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.util.Calendar.*;

public class Topreality {

    public Document doc;
    public Parametre aktualny;
    public Database database;
    public static String volanyLink;
    public static String upravenyRetazec;
    public Integer noveInzeraty;

    /**
     * Konstruktor
     *
     * @param link
     * @throws IOException
     */
    public Topreality(String link) throws IOException {
        doc = Jsoup.connect(link).get();
        volanyLink = link;
        try {
            Database database = new Database("kc014100db", "kc014100", "jgowihez");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getParametre() throws ParseException {
        String cenaString, cenaBezEur, transString, transString0;
        Integer lenCenaString;
        Double doubleCena;


        /*Double cena,cenam2,uzitplocha = 0.00;
        String lokalita,ulica,material,stav,aktualizacia = "";
        Integer identcislo = 0;
        Boolean balkon,pivnica,vytah = false;*/

        Parametre aktualny = new Parametre();
        Elements linky = doc.select("li");

        /*Nacitanie id Inzeratu na serveri a link servera*/
        Elements linky1 = doc.select("strong");
        Integer nIdInzeratu = 0;
        for (Element link : linky1) {
            String clink = link.text().trim();
            if (clink.length() > 3) {
                if (clink.substring(0, 3).equals("www")) {
                    aktualny.setLink("https://"+clink);
                    Integer dlzkaRetazca = clink.length();
                    Pattern pattern = Pattern.compile("d");
                    Matcher matcher = pattern.matcher(clink);

                    if (matcher.find()) {
                        String validretazec = clink.substring(matcher.start() + 1, dlzkaRetazca).trim();
                        nIdInzeratu = parseInt(validretazec);

                    }
                }
            }
        }
        setIdInzeratu(nIdInzeratu);

        /*Nacitanie title Inzeratu na serveri*/
        Elements linky2 = doc.select("h1");
        String clink2 = "";
        for (Element link : linky2) {
            clink2 = link.text().trim();
        }
        setTitulka(clink2);


        /*Nacitanie popisu */
        Elements linky3 = doc.select("p");
        String clink3 = "";
        Iterator<Element> iter = linky3.iterator();
        Element td = iter.next();
        Element td1 = iter.next();
        clink3 = td1.text().trim();
        aktualny.setPopis(clink3);

        /* Nacitanie typu nehnutelnosti*/
        String typ_nehnutelnosti = this.getParameter("Kategória", linky);
        aktualny.setTyp_nehnutelnosti_id(typ_nehnutelnosti);

        /* Nacitanie typu transakcie*/
        String typ_transakcie = this.getParameter("Kategória", linky);
        aktualny.setDruh_transakcie(typ_transakcie);

        /* Nacitanie ceny*/
        cenaString = this.getParameter("Cena", linky);
        transString = "";
        doubleCena = 0.0;
        Integer dlzkaRetazca = cenaString.length();
        if (aktualny.getDruh_transakcie().contains("Prenájom")){
            if (cenaString.contains("vrátane provízie")) {
                cenaString = (cenaString.substring(16));
            } else {
                if (cenaString.contains("Cena bez provízie")) {
                    cenaString = (cenaString.substring(17));
                } else {
                    if (cenaString.contains("bez provízie")){
                        cenaString = (cenaString.substring(12));
                    } else {
                        if (cenaString.contains("cena dohodou")) {
                            cenaString = (cenaString.substring(12));
                        }
                    }
                }
            }
            lenCenaString = cenaString.length();
            Pattern pattern = Pattern.compile("E");
            Matcher matcher = pattern.matcher(cenaString);
            if (matcher.find()) {
                cenaString = cenaString.substring(0,matcher.start()).trim();
            }
        } else {
            if (cenaString.contains("cena dohodou")) {
                cenaString = (cenaString.substring(12));
            } else {
                if (cenaString.contains("vrátane provízie")) {
                    cenaString = (cenaString.substring(16));
                }

            }
            lenCenaString = cenaString.length();
            Pattern pattern = Pattern.compile("E");
            Matcher matcher = pattern.matcher(cenaString);
            if (matcher.find()) {
                cenaString = cenaString.substring(0, matcher.start()).trim();
            }
        }
        transString0 = cenaString.replace(",", ".");
        transString = transString0.replace(" ", "");
        if (transString.length()==0) {
            transString = "0.00";
        }
        doubleCena = parseDouble(transString);
        aktualny.setCena(doubleCena);


        /* Nacitanie ceny za meter*/
        String cenazameter = "0.00";
        Elements linky4 = doc.select("strong.price");
        Iterator<Element> iter1 = linky4.iterator();
        if (linky4.size() == 1) { ;
        } else {
        Element td2 = iter1.next();
        Element td3 = iter1.next();
        String cena = td3.text().trim();
        Integer dlzka=cena.length();

        Pattern pattern = Pattern.compile("E");
        Matcher matcher = pattern.matcher(cena);
        if (matcher.find()) {
             cena = cena.substring(0,matcher.start()).trim();
        }
        cenazameter = cena.replace(",", "."); }
        aktualny.setCenam2(parseDouble(cenazameter.replace(" ", "")));

        /* Nacitanie Poschodia */
        String poschodie = this.getParameter("Poschodie", linky);
        aktualny.setPoschodie(poschodie);


        /* Nacitanie typu transakcie*/
        typ_transakcie = this.getParameter("Kategória", linky);
        aktualny.setDruh_transakcie(typ_transakcie);

        /*Nacitanie lokality*/
        String lokalita = this.getParameter("Lokalita",linky);
        aktualny.setLokalita(lokalita);
        aktualny.setLokalita_id(lokalita);

        /* Nacitanie aktualizacie inzeratu*/
        String aktualizacia = this.getParameter("Aktualizácia",linky);
        SimpleDateFormat povodnyFormat = new SimpleDateFormat("dd.mm.yyyy HH:MM:SS");
        SimpleDateFormat novyFormat = new SimpleDateFormat("yyyy.mm.dd HH:MM:SS");
        String preformatovany = novyFormat.format(povodnyFormat.parse(aktualizacia));
        aktualny.setAktualizacia(preformatovany);

        /* Načítanie užitkovej plochy */
        String uzitkovaplocha = "";
        uzitkovaplocha = this.getParameter("Úžitková plocha",linky);
        Integer dlzka=uzitkovaplocha.length();
        if (dlzka ==0 ){uzitkovaplocha = "0.00";
        } else {
        uzitkovaplocha = uzitkovaplocha.substring(0,dlzka-3); }
        aktualny.setUzitplocha(parseDouble(uzitkovaplocha));

        /* nacitanie ulice */
        String ulica = this.getParameter("Ulica",linky);
        aktualny.setUlica(ulica.trim());

        /* nacitanie materialu*/
        String material = this.getParameter("Materiál",linky);
        aktualny.setMaterial(material.trim());

        /* nacitanie balkon*/
        String balkon = this.getParameter("Balkón / loggia",linky);
        Integer intBalkon = 0;
        if (balkon.trim().equals("Áno")){ intBalkon = 1; };
        aktualny.setBalkon(intBalkon);

        /* nacitanie vytah*/
        String vytah = this.getParameter("Výťah",linky);
        Integer intvytah = 0;
        if (vytah.trim().equals("Áno")){ intvytah = 1; };
        aktualny.setVytah(intvytah);

        /* nacitanie pivnica*/
        String pivnica = this.getParameter("Pivnica",linky);
        Integer intpivnica = 0;
        if (pivnica.trim().equals("Áno")){ intpivnica = 1; };
        aktualny.setPivnica(intpivnica);

        /*aktualny.setCenam2(parseDouble(this.getParameter("&nbsp")));*/
        /*if (this.getParameter("Balkón / loggia").equals("Áno")) {
            aktualny.setBalkon(true);}

        /*cena = parseDouble(this.getParameter("Cena"));
        cenam2 = parseDouble(this.getParameter("&nbsp"));
        lokalita = this.getParameter("Lokalita");
        ulica = this.getParameter("Ulica");
        aktualizacia = this.getParameter("Aktualizácia");
        identcislo = parseInt(this.getParameter("Identifikačné číslo:"));
        uzitplocha = parseDouble(this.getParameter("Úžitková plocha"));
        material = this.getParameter("Materiál");
        stav = this.getParameter("Stav");


        if (this.getParameter("Balkón / loggia").equals("Áno")) {
            balkon = true; }
        if (this.getParameter("Pivnica").equals("Áno")) {
            pivnica = true;}
        if (this.getParameter("Výťah").equals("Áno")) {
            vytah = true; };*/


    }

    public Integer getPocetInzeratov() {
        Elements polozky = doc.select(".count");
        String pocetInzeratov = polozky.text().trim();
        Integer dlzka=pocetInzeratov.length();

        Pattern pattern = Pattern.compile("o");
        Matcher matcher = pattern.matcher(pocetInzeratov);
        if (matcher.find()) {
            pocetInzeratov = pocetInzeratov.substring(0,matcher.start()).trim();
        }
        return parseInt(pocetInzeratov);
    }

    public Integer nahrajNoveInzeraty() {
        noveInzeraty = 0;
        ArrayList inzeraty = new ArrayList<String>();
        inzeraty = this.getInzeraty();
        inzeraty.forEach(inzerat -> {
            Topreality doc2 = null;
            try {
                doc2 = new Topreality((String) inzerat);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                doc2.getParametre();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Integer id = getIdInzeratu();
            String[] columns3 = {"idnaserveri"};
            Object[] params3 = {id};
            ResultSet rs = null;

            try {
                Database database = new Database("kc014100db", "kc014100", "jgowihez");
                rs = database.select("nehnutelnosti", columns3, "idnaserveri=?", params3);

                if (!rs.next()) {
                    /*Nenasiel som inzerat idem ho nahrat*/
                    String[] columns = {"server_id", "idnaserveri", "druh_transakcie", "linkserver","typ_nehnutelnosti_id",
                            "cena", "cenam2","uzitplocha","poschodie", "pocetposchodi", "titulka", "popis", "lokalita_id","lokalita",
                            "aktualizacia", "material", "balkon", "pivnica", "vytah", "ulica"};
                    Object[] params = {1, id, getDruh_transakcie(), getLink(),getTyp_nehnutelnosti_id(),
                            getCena(),getCenam2(),getUzitplocha(), getPoschodie(), getPocetPoschodi(), getTitulka(), getPopis(), getLokalita_id(),getLokalita(),
                            getAktualizacia(), getMaterial(),getBalkon(), getPivnica(),getVytah(), getUlica()};
                    int uspech1 = database.insertcolumn("nehnutelnosti", columns, params);
                    noveInzeraty++;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return noveInzeraty;
    }

    public static String getServerLink(String retazec) {
        String upravenyRetazec = "";
        upravenyRetazec = "https://www." + retazec.toLowerCase().trim() + ".sk";
        return upravenyRetazec;
    }


    public static String getBezDiakritikyMale(String retazec) {
        String upravenyRetazec, bezDiakritiky, malePismena = "";
        malePismena = retazec.toLowerCase();
        bezDiakritiky = malePismena.replace("ľ", "l");
        bezDiakritiky = bezDiakritiky.replace("š", "s");
        bezDiakritiky = bezDiakritiky.replace("č", "c");
        bezDiakritiky = bezDiakritiky.replace("ť", "t");
        bezDiakritiky = bezDiakritiky.replace("ž", "z");
        bezDiakritiky = bezDiakritiky.replace("ý", "y");
        bezDiakritiky = bezDiakritiky.replace("á", "a");
        bezDiakritiky = bezDiakritiky.replace("í", "i");
        bezDiakritiky = bezDiakritiky.replace("é", "e");
        bezDiakritiky = bezDiakritiky.replace("ú", "u");
        bezDiakritiky = bezDiakritiky.replace("ň", "n");
        upravenyRetazec = bezDiakritiky.replace(" ", "-");
        return upravenyRetazec;
    }


    /**
     * @return
     */
    public ArrayList getInzeraty() {
        ArrayList inzeraty = new ArrayList<String>();
        Integer i = 0;
        Elements linky = doc.select("h2 a");
        for (Element link : linky) {
            inzeraty.add(link.attr("abs:href"));
        }
        return inzeraty;
    }

    /**
     * @param parameter
     * @return
     */
    public String getParameter(String parameter, Elements linky) {
        String value = "";
        Integer lenParameter = parameter.length();


        for (Element link : linky) {
            if (link.text().length() > 2 && link.text().length() >= lenParameter) {

                String findLink = link.text().substring(0, lenParameter);
                if (findLink.equals(parameter)) {
                    value = link.text().substring(lenParameter).trim();
                    return value;
                }
            }
        }
        return value;

    }

    public static Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(2000);
                    updateMessage("2000 milliseconds");
                    updateProgress(i + 1, 10);
                }
                return true;
            }
        };
    }


}
