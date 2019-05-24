/*
 * Copyright (c) 2019. Dušan Stančík
 */

package SCAN;

import javafx.concurrent.Task;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.round;

public class SearchAndSave extends Task<Integer> {

    private final String linkVyhladavaci ;

    public SearchAndSave(String  linkVyhladavaci) {
        this.linkVyhladavaci = linkVyhladavaci;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Integer call() throws Exception {
        updateMessage("  ...  Vyhľadávam na stránke ... ");
        Integer noveCelkom = Search(linkVyhladavaci);
        updateMessage("    Koniec.  ");
        return noveCelkom;
    }

    public Integer Search (String linkVyhladavaci){
        String linkVyhladavaciIter;
        Topreality doc1 = null;
        Integer nove = 0;
        Integer pocet = 0;
        Integer noveCelkom = 0;
        Integer pocetStranok = 0;

        try {
            doc1 = new Topreality(linkVyhladavaci);
            pocet = doc1.getPocetInzeratov();
            updateMessage("Počet najdených inzerátov: "+Integer.toString(pocet));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pocetStranok = pocet/15;

        for (int i=1;i<round(pocetStranok+2);i++){
            linkVyhladavaciIter = linkVyhladavaci+"/"+Integer.toString(i)+".html";


            try {
                doc1 = new Topreality(linkVyhladavaciIter);
                nove = doc1.nahrajNoveInzeraty();
                updateMessage("Nahrávam na server: "+Integer.toString(pocet-i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            noveCelkom = noveCelkom + nove;
        }

 /*         pocetInzeratov.setText(Integer.toString(pocet));
            pocetNovychInzeratov.setText(Integer.toString(noveCelkom));*/
        return noveCelkom;

    }

}
