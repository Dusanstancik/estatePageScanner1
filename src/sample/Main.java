package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.*;

import static com.sun.xml.internal.bind.WhiteSpaceProcessor.replace;
import static java.lang.Math.round;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Locale locale = new Locale("English","EN");
        Parent root = FXMLLoader.load(getClass().getResource("../windows/sample.fxml"), ResourceBundle.getBundle("string",locale));
        primaryStage.setTitle("Page Scanner");
        primaryStage.setScene(new Scene(root, 800, 500));





        primaryStage.show();

        /*System.out.println(" 62 263,23 ".trim());
        Pattern pattern = Pattern.compile("/");
        Matcher matcher = pattern.matcher("5 / 5");
        if (matcher.find()){
            System.out.println(matcher.start());
            System.out.println(matcher.end());

        }*/

  /*      try {

            Database database = new Database("kc014100db", "kc014100", "jgowihez");
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

            String[] columns = {"server_id","idnaserveri", "druh_transakcie", "typ_nehnutelnosti_id"};
            Object[] params = {1, 2323698,1,3};
            int uspech1 = database.insertcolumn("nehnutelnosti",columns, params);


        /*    Object[] firstParams = {0,"Dušan", "Stančík", "admin", "12345",timeStamp,timeStamp};
            int uspech1 = database.insert("user", firstParams);
            System.out.println("Uložení uživatele = "+uspech1);

            String[] columns = {"meno","update_at"};
            Object[] params = {"Galileo", timeStamp,6};
            int uspech4 = database.update("user", columns, "iduser = ?", params);
            System.out.println("Přepsání uživate = "+uspech4);

            } catch (SQLException ex) {
            System.out.println("error - "+ex.getMessage());
        }


        /*Document html = Jsoup.connect("https://www.topreality.sk/obec-martin/byty/").get();
        /*Document doc3 = Jsoup.connect("https://www.topreality.sk/na-predaj-2-izb-byt-povodny-stav-martin-r6805152.html").get();
        /*Document doc = Jsoup.connect("http://kataster.skgeodesy.sk/EsknBo/Bo.svc/GeneratePrf?prfNumber=4066&cadastralUnitCode=865788&outputType=html").get();*/

        /*Elements content = doc3.select("li");*/

        /*String html = "<span id=\"foundresults\" class=\"foundresults\"><strong>114</strong><br>vydsledkov</span>";*/
        /*Document doc3 = Jsoup.parse(html);*/


        /*for(Node n : child) {
            System.out.println(n);
        }
        polozky.forEach((polozka)->System.out.println(polozka.children().text()));
        polozky.forEach((polozka)->System.out.println(polozka.children().text()));

        /*System.out.println(polozky);
        /*tring masthead1 = masthead.next();*/

        /*polozky.forEach((n) -> System.out.println(n.text()));*/


            /*System.out.println(""+content.attr("itemprop","description").text());*/

        /*Topreality doc1 = new Topreality("www.topreality.sk/id6794710");*/




 /*       Topreality doc1 = new Topreality("https://www.topreality.sk/banska-bystrica/byty/2-izbovy-byt/predaj/1.html");

        ArrayList inzeraty = new ArrayList<String>();
        inzeraty = doc1.getInzeraty();


        inzeraty.forEach(inzerat->{
            Topreality doc2 = null;
            try {
                doc2 = new Topreality((String) inzerat);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(inzerat);
            doc2.getParametre();
            Parametre.getCena();
            Parametre.getPoschodie();
            Parametre.getPocetPoschodi();
            Parametre.getIdInzeratu();
            System.out.println(Parametre.getCena());
            System.out.println(Parametre.getPoschodie());
            System.out.println(Parametre.getPocetPoschodi());
            System.out.println(Parametre.getIdInzeratu());

        });









        /*String polozky3 = doc1.getParameter("Poschodie");*/

        /*polozky3.forEach((n) -> System.out.println(n.text()));


        /*System.out.println(content1);*/

        /*System.out.println(polozka.getParameter("Cena"));*/






        /*String title = doc.title();
        Elements cena = doc.getElementsByClass("price");

        /*Elements textcena = cena1.select("title");*/
        /*String text = doc.body().text();



        System.out.println(""+cena);
        System.out.println("cena = ");
        /*System.out.println("cena = "+text);*/

    }





    public static void main(String[] args) {
        launch(args);
    }
}
