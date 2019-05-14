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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Topreality  {

    public Document doc;
    public Parametre aktualny;
    public Database database;
    public static String volanyLink;
    public static String upravenyRetazec;
    public Integer noveInzeraty;

    /**
     * Konstruktor
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



    public void getParametre (){
        String cenaString,cenaBezEur,transString,transString0;
        Integer lenCenaString;
        Double doubleCena;


        /*Double cena,cenam2,uzitplocha = 0.00;
        String lokalita,ulica,material,stav,aktualizacia = "";
        Integer identcislo = 0;
        Boolean balkon,pivnica,vytah = false;*/

        Parametre aktualny = new Parametre();
        Elements linky = doc.select("li");

        /*Nacitanie id Inzeratu na serveri*/
        Elements linky1 = doc.select("strong");
        Integer nIdInzeratu = 0;
        for ( Element link: linky1)
        {
            String clink = link.text().trim();
            if (clink.length()>3) {
                if (clink.substring(0, 3).equals("www")) {

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
        aktualny.setIdInzeratu(nIdInzeratu);

        /*Nacitanie title Inzeratu na serveri*/
        Elements linky2 = doc.select("h1");
        String clink2 = "";
        for ( Element link: linky2) {
            clink2 = link.text().trim();
        }
        aktualny.setTitulka(clink2);

        /*Nacitanie popisu */

        Elements linky3 = doc.select("p");
        String clink3 = "";
        for ( Element link: linky3) {
            clink3 = link.text().trim();
        }
        aktualny.setPopis(clink3);




        /* Nacitanie ceny*/
        cenaString = this.getParameter("Cena",linky);
        doubleCena = 0.0;
        Integer dlzkaRetazca = cenaString.length();
        if (cenaString.equals("cena dohodou")) {
            doubleCena = 0.00;
        }else if (cenaString.length()>14){
            if (cenaString.substring(0,14).equals("vrátaneprovízie")) {
                lenCenaString = cenaString.length();
                transString0 = (cenaString.substring(15, lenCenaString - 3)).replace(",", ".");
                transString = transString0.replace(" ", "");
                doubleCena = parseDouble(transString);
            }
        } else {
            lenCenaString = cenaString.length();
            transString0 = (cenaString.substring(0, lenCenaString - 3)).replace(",", ".");
            transString = transString0.replace(" ", "");
            doubleCena = parseDouble(transString);
        }
        aktualny.setCena(doubleCena);


        String poschodie = this.getParameter("Poschodie",linky);
        aktualny.setPoschodie(poschodie);


        /* Nacitanie typu nehnutelnosti*/
        String typ_nehnutelnosti = this.getParameter("Kategória",linky);
        aktualny.setTyp_nehnutelnosti_id(typ_nehnutelnosti);

        /* Nacitanie typu transakcie*/
        String typ_transakcie = this.getParameter("Kategória",linky);
        aktualny.setDruh_transakcie(typ_transakcie);

        /*Nacitanie popisu */



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
    public Integer getPocetInzeratov (){
        Elements polozky = doc.select(".count");
        Iterator<Element> iter = polozky.iterator();
        Element td = iter.next();
        Element child = td.children().first();
        return  parseInt(child.text().trim());
    }

    public Integer nahrajNoveInzeraty (){
        noveInzeraty = 0;
        ArrayList inzeraty = new ArrayList<String>();
        inzeraty = this.getInzeraty();
        inzeraty.forEach(inzerat->{
            Topreality doc2 = null;
            try {
                doc2 = new Topreality((String) inzerat);
            } catch (IOException e) {
                e.printStackTrace();
            }

            doc2.getParametre();
            Integer id=Parametre.getIdInzeratu();
            String[] columns3 = {"idnaserveri"};
            Object[] params3 = {id};
            ResultSet rs = null;

            try {
                Database database = new Database("kc014100db", "kc014100", "jgowihez");
                rs = database.select("nehnutelnosti",columns3,"idnaserveri=?",params3);

                if (!rs.next()){
                    /*Nenasiel som inzerat idem ho nahrat*/
                    String[] columns = {"server_id","idnaserveri","druh_transakcie", "typ_nehnutelnosti_id","cena", "poschodie","pocetposchodi","titulka","popis"};
                    Object[] params = {1, id,Parametre.getDruh_transakcie(),Parametre.getTyp_nehnutelnosti_id(),Parametre.getCena(),Parametre.getPoschodie(),Parametre.getPocetPoschodi(),Parametre.getTitulka(),Parametre.getPopis()};
                    int uspech1 = database.insertcolumn("nehnutelnosti",columns, params);
                    noveInzeraty++;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return noveInzeraty;
    }

    public static String getServerLink (String retazec){
        String upravenyRetazec = "";
        upravenyRetazec = "https://www."+retazec.toLowerCase().trim()+".sk";
        return upravenyRetazec;
    }


    public static String getBezDiakritikyMale (String retazec){
        String upravenyRetazec,bezDiakritiky,malePismena = "";
        malePismena = retazec.toLowerCase();
        bezDiakritiky = malePismena.replace("ľ","l");
        bezDiakritiky = bezDiakritiky.replace("š","s");
        bezDiakritiky = bezDiakritiky.replace("č","c");
        bezDiakritiky = bezDiakritiky.replace("ť","t");
        bezDiakritiky = bezDiakritiky.replace("ž","z");
        bezDiakritiky = bezDiakritiky.replace("ý","y");
        bezDiakritiky = bezDiakritiky.replace("á","a");
        bezDiakritiky = bezDiakritiky.replace("í","i");
        bezDiakritiky = bezDiakritiky.replace("é","e");
        bezDiakritiky = bezDiakritiky.replace("ú","u");
        bezDiakritiky = bezDiakritiky.replace("ň","n");
        upravenyRetazec = bezDiakritiky.replace(" ","-");
        return upravenyRetazec;
    }


    /**
     *
     * @return
     */
    public ArrayList getInzeraty (){
        ArrayList inzeraty = new ArrayList<String>();
        Integer i=0;
        Elements linky = doc.select("h2 a");
        for ( Element link: linky)
        {
            inzeraty.add(link.attr("abs:href"));
        }
        return inzeraty;
    }

    /**
     *
     * @param parameter
     * @return
     */
    public String getParameter (String parameter,Elements linky){
        String value ="";
        Integer lenParameter = parameter.length();


        for ( Element link: linky)
        {
            if (link.text().length()>2 && link.text().length() >= lenParameter) {

                String findLink = link.text().substring(0,lenParameter);
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
