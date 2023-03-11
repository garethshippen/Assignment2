/*
 * PlayWriter.java
 *
 * PLayWriter class.
 * Creates the lovers, and writes the two lover's story (to an output text file).
 */


import java.net.Socket;
import java.net.InetAddress;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javafx.util.Pair;


public class PlayWriter {

    private Romeo  myRomeo  = null;
    private InetAddress RomeoAddress = null;
    private int RomeoPort = 0;
    private Socket RomeoMailbox = null;

    private Juliet myJuliet = null;
    private InetAddress JulietAddress = null;
    private int JulietPort = 0;
    private Socket JulietMailbox = null;

    double[][] theNovel = null;
    int novelLength = 0;

    public PlayWriter()
    {
        //TODO change this back
        //novelLength = 500; //Number of verses
        novelLength = 10;
        theNovel = new double[novelLength][2];
        theNovel[0][0] = 0;
        theNovel[0][1] = 1;
    }

    //Create the lovers
    public void createCharacters() {
        //Create the lovers
        System.out.println("PlayWriter: Romeo enters the stage.");

            //TODO
        double romeosLove = theNovel[0][0];
        myRomeo = new Romeo(romeosLove);
        myRomeo.start();
			
        System.out.println("PlayWriter: Juliet enters the stage.");

            //TODO
        double julietsLove = theNovel[0][1];
        myJuliet = new Juliet(julietsLove);
        myJuliet.start();
			
    }

    //Meet the lovers and start letter communication
    public void charactersMakeAcquaintances() {

            //TODO
        Pair<InetAddress,Integer> whereforeArtThou = myRomeo.getAcquaintance();
        RomeoAddress = whereforeArtThou.getKey();
        RomeoPort = whereforeArtThou.getValue();

        System.out.println("PlayWriter: I've made acquaintance with Romeo");

            //TODO
        Pair<InetAddress,Integer> itIsTheEast = myJuliet.getAcquaintance();
        JulietAddress = itIsTheEast.getKey();
        JulietPort = itIsTheEast.getValue();

        System.out.println("PlayWriter: I've made acquaintance with Juliet");
    }


    //Request next verse: Send letters to lovers communicating the partner's love in previous verse
    public void requestVerseFromRomeo(int verse) {
        System.out.println("PlayWriter: Requesting verse " + verse + " from Romeo. -> (" + theNovel[verse-1][1] + ")");

        try
        {
            RomeoMailbox = new Socket(RomeoAddress, RomeoPort);
        } catch (IOException e)
        {
            //TODO tidy
            System.out.println(e);
            System.out.println("ERROR");
            System.out.println("Problem setting up Romeo socket. requestVerseFromRomeo()");
            System.exit(1);
        }


            //TODO
        //Send value of [verse - 1][1] to Romeo server
        try
        {
            OutputStream aHeraldToSendMyLove = RomeoMailbox.getOutputStream();
            OutputStreamWriter myWordsOfLove = new OutputStreamWriter(aHeraldToSendMyLove);
            myWordsOfLove.write(theNovel[verse - 1][1] + "X");
            myWordsOfLove.flush();
        } catch (IOException e)
        {
            System.out.println(e);
            System.out.println("ERROR");
            System.out.println("Problem setting up Romeo OutputStream. requestVerseFromRomeo()");
            System.exit(1);
        }


    }

    //Request next verse: Send letters to lovers communicating the partner's love in previous verse
    public void requestVerseFromJuliet(int verse) {
        System.out.println("PlayWriter: Requesting verse " + verse + " from Juliet. -> (" + theNovel[verse-1][0] + ")");

        try
        {
            JulietMailbox = new Socket(JulietAddress, JulietPort);
        } catch (IOException e)
        {
            //TODO tidy
            System.out.println(e);
            System.out.println("ERROR");
            System.out.println("Problem setting up Juliet socket. requestVerseFromJuliet()");
            System.exit(1);
        }

            //TODO Send value of [verse - 1][0] to Juliet server
        try
        {
            OutputStream aHeraldToSendMyLove = JulietMailbox.getOutputStream();
            OutputStreamWriter myWordsOfLove = new OutputStreamWriter(aHeraldToSendMyLove);
            myWordsOfLove.write(theNovel[verse-1][0] + "x");
            myWordsOfLove.flush();
        } catch (IOException e)
        {
            System.out.println(e);
            System.out.println("ERROR");
            System.out.println("Problem setting up Juliet OutputStream. requestVerseFromJuliet()");
            System.exit(1);
        }
    }


    //Receive letter from Romeo with renovated love for current verse
    public void receiveLetterFromRomeo(int verse) {
        //System.out.println("PlayWriter: Receiving letter from Romeo for verse " + verse + ".");

            //TODO Process response and add to theNovel[verse][0]
        StringBuffer theWritingsOfRomeo = new StringBuffer("");
        try
        {
            InputStream aMissiveArrivesFromYoungMontague = RomeoMailbox.getInputStream();
            InputStreamReader hisLovingWords = new InputStreamReader(aMissiveArrivesFromYoungMontague);

            boolean read = true;
            char aLetter;
            final char HIS_FINAL_REMARK = 'x';
            while(read)
            {
                aLetter = (char)hisLovingWords.read();

                if(aLetter != HIS_FINAL_REMARK)
                {
                    theWritingsOfRomeo.append(aLetter);
                }
                else
                {
                    read = false;
                }
            }
            //TODO add closers?
            theNovel[verse][0] = Double.parseDouble(theWritingsOfRomeo.toString());
        } catch (IOException e)
        {
            System.out.println(e);
            System.out.println("ERROR");
            System.out.println("Problem receiving from Romeo. receiveLetterFromRomeo()");
            System.exit(1);
        }

        System.out.println("PlayWriter: Romeo's verse " + verse + " -> " + theNovel[verse][0]);
    }

    //Receive letter from Juliet with renovated love fro current verse
    public void receiveLetterFromJuliet(int verse) {
            
			//TO BE COMPLETED
            //TODO process response from Juliet and add to theNovel[verse][1]
        try
        {
            InputStream fairJulietSendsWord = JulietMailbox.getInputStream();
            InputStreamReader wordsLikeMusic = new InputStreamReader(fairJulietSendsWord);
            StringBuffer sweetSyllableLikeARose = new StringBuffer("");

            boolean read = true;
            char aLetter;
            final char FOR_SORROW_FOR_IT_HAS_ENDED = 'X';

            while(read)
            {
                aLetter = (char) wordsLikeMusic.read();

                if(aLetter != FOR_SORROW_FOR_IT_HAS_ENDED)
                {
                    sweetSyllableLikeARose.append(aLetter);
                }
                else
                {
                    read = false;
                }
            }

            theNovel[verse][1] = Double.parseDouble(sweetSyllableLikeARose.toString());

            //TODO close streams?
        } catch (IOException e)
        {
            System.out.println(e);
            System.out.println("ERROR");
            System.out.println("Problem receiving from Juliet. receiveLetterFromJuliet()");
            System.exit(1);
        }


        System.out.println("PlayWriter: Juliet's verse " + verse + " -> " + theNovel[verse][1]);
    }





    //Let the story unfold
    public void storyClimax() {
        for (int verse = 1; verse < novelLength; verse++) {
            //Write verse
            System.out.println("PlayWriter: Writing verse " + verse + ".");
            
			//TO BE COMPLETED
            //TODO
            //do romeo stuff
            requestVerseFromRomeo(verse);
            receiveLetterFromRomeo(verse);

            //do juliet stuff
            requestVerseFromJuliet(verse);
            receiveLetterFromJuliet(verse);
			
            System.out.println("PlayWriter: Verse " + verse + " finished.");
        }
    }

    //Character's death
    public void charactersDeath() {

            //TODO
        //Kill Juliet
        myJuliet.interrupt();

        //Kill Romeo
        myRomeo.interrupt();
    }


    //A novel consists of introduction, conflict, climax and denouement
    public void writeNovel() {
        System.out.println("PlayWriter: The Most Excellent and Lamentable Tragedy of Romeo and Juliet.");
        System.out.println("PlayWriter: A play in IV acts.");
        //Introduction,
        System.out.println("PlayWriter: Act I. Introduction.");
        this.createCharacters();
        //Conflict
        System.out.println("PlayWriter: Act II. Conflict.");
        this.charactersMakeAcquaintances();
        //Climax
        System.out.println("PlayWriter: Act III. Climax.");
        this.storyClimax();
        //Denouement
        System.out.println("PlayWriter: Act IV. Denouement.");
        this.charactersDeath();

    }


    //Dump novel to file
    public void dumpNovel() {
        FileWriter Fw = null;
        try {
            Fw = new FileWriter("RomeoAndJuliet.csv");
        } catch (IOException e) {
            System.out.println("PlayWriter: Unable to open novel file. " + e);
        }

        System.out.println("PlayWriter: Dumping novel. ");
        StringBuilder sb = new StringBuilder();
        for (int act = 0; act < novelLength; act++) {
            String tmp = theNovel[act][0] + ", " + theNovel[act][1] + "\n";
            sb.append(tmp);
            //System.out.print("PlayWriter [" + act + "]: " + tmp);
        }

        try {
            BufferedWriter br = new BufferedWriter(Fw);
            br.write(sb.toString());
            br.close();
        } catch (Exception e) {
            System.out.println("PlayWriter: Unable to dump novel. " + e);
        }
    }

    public static void main (String[] args) {
        PlayWriter Shakespeare = new PlayWriter();
        Shakespeare.writeNovel();
        Shakespeare.dumpNovel();
        System.exit(0);
    }


}
