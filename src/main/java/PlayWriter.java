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
        novelLength = 500; //Number of verses
        theNovel = new double[novelLength][2];
        theNovel[0][0] = 0;
        theNovel[0][1] = 1;
    }

    //Create the lovers
    public void createCharacters() {
        //Create the lovers
        System.out.println("PlayWriter: Romeo enters the stage.");
            
			//TO BE COMPLETED
            //TODO
        double romeosLove = theNovel[0][0];
        myRomeo = new Romeo(romeosLove);
        myRomeo.start();
			
        System.out.println("PlayWriter: Juliet enters the stage.");
            
			//TO BE COMPLETED
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
        try
        {
            RomeoMailbox = new Socket(RomeoAddress, RomeoPort);
        } catch (IOException e)
        {
            //TODO tidy
            System.out.println("Problem setting up Romeo socket");
        }

        System.out.println("PlayWriter: I've made acquaintance with Romeo");

            //TODO
        Pair<InetAddress,Integer> itIsTheEast = myJuliet.getAcquaintance();
        JulietAddress = itIsTheEast.getKey();
        JulietPort = itIsTheEast.getValue();
        try
        {
            JulietMailbox = new Socket(JulietAddress, JulietPort);
        } catch (IOException e)
        {
            //TODO tidy
            System.out.println("Problem setting up Juliet socket.");
        }
        System.out.println("PlayWriter: I've made acquaintance with Juliet");
    }


    //Request next verse: Send letters to lovers communicating the partner's love in previous verse
    public void requestVerseFromRomeo(int verse) {
        System.out.println("PlayWriter: Requesting verse " + verse + " from Romeo. -> (" + theNovel[verse-1][1] + ")");

            //TODO
        //Send value of [verse - 1][1] to Romeo server
        try
        {
            OutputStream aHeraldToSendMyLove = RomeoMailbox.getOutputStream();
            OutputStreamWriter myWordsOfLove = new OutputStreamWriter(aHeraldToSendMyLove);
            myWordsOfLove.write(theNovel[verse - 1][1] + "x");
            myWordsOfLove.flush();
        } catch (IOException e)
        {
            System.out.println("Problem setting up Romeo OutputStream");
        }


    }

    //Request next verse: Send letters to lovers communicating the partner's love in previous verse
    public void requestVerseFromJuliet(int verse) {
        System.out.println("PlayWriter: Requesting verse " + verse + " from Juliet. -> (" + theNovel[verse-1][0] + ")");

            //TODO Send value of [verse - 1][0] to Juliet server
        try
        {
            OutputStream aHeraldToSendMyLove = JulietMailbox.getOutputStream();
            OutputStreamWriter myWordsOfLove = new OutputStreamWriter(aHeraldToSendMyLove);
            myWordsOfLove.write(theNovel[verse-1][0] + "X");
            myWordsOfLove.flush();
        } catch (IOException e)
        {
            System.out.println("Problem setting up Juliet OutputStream");
        }
    }


    //Receive letter from Romeo with renovated love for current verse
    public void receiveLetterFromRomeo(int verse) {
        //System.out.println("PlayWriter: Receiving letter from Romeo for verse " + verse + ".");

            
			//TO BE COMPLETED
            //TODO
			
        System.out.println("PlayWriter: Romeo's verse " + verse + " -> " + theNovel[verse][0]);
    }

    //Receive letter from Juliet with renovated love fro current verse
    public void receiveLetterFromJuliet(int verse) {
            
			//TO BE COMPLETED
            //TODO
			
        System.out.println("PlayWriter: Juliet's verse " + verse + " -> " + theNovel[verse][1]);
    }





    //Let the story unfold
    public void storyClimax() {
        for (int verse = 1; verse < novelLength; verse++) {
            //Write verse
            System.out.println("PlayWriter: Writing verse " + verse + ".");
            
			//TO BE COMPLETED
            //TODO
			
            System.out.println("PlayWriter: Verse " + verse + " finished.");
        }
    }

    //Character's death
    public void charactersDeath() {
            
			//TO BE COMPLETED
            //TODO
			
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
