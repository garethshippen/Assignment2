/*
 * Juliet.java
 *
 * Juliet class.  Implements the Juliet subsystem of the Romeo and Juliet ODE system.
 */



import javafx.util.Pair;

import java.lang.Thread;
import java.net.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Juliet extends Thread {

    private ServerSocket ownServerSocket = null; //Juliet's (server) socket
    private Socket serviceMailbox = null; //Juliet's (service) socket

    private double currentLove = 0;
    private double b = 0;

    //Class construtor
    public Juliet(double initialLove) {
        currentLove = initialLove;
        b = 0.01;
        try {

            String thisIp = "localhost";
            final int THE_PORT = 8787;
            final int MAX_LOVERS = 1;

            ownServerSocket = new ServerSocket(THE_PORT, MAX_LOVERS, InetAddress.getByName(thisIp));
			
            System.out.println("Juliet: Good pilgrim, you do wrong your hand too much, ...");
        } catch(Exception e) {
            System.out.println("Juliet: Failed to create own socket " + e);
        }
    }

    //Get acquaintance with lover;
    public Pair<InetAddress,Integer> getAcquaintance() {
        System.out.println("Juliet: My bounty is as boundless as the sea,\n" +
                "       My love as deep; the more I give to thee,\n" +
                "       The more I have, for both are infinite.");

        return new Pair<InetAddress,Integer>(ownServerSocket.getInetAddress(), ownServerSocket.getLocalPort());
			
    }

    //Retrieves the lover's love
    public double receiveLoveLetter()
    {
        double tmp = 999999;
        System.out.println("Juliet: Awaiting letter.");
        StringBuffer myLovesWords = new StringBuffer(""); //StringBuffer is thread safe
        try
        {
            serviceMailbox = ownServerSocket.accept();
            InputStream aMessageFromMyLove = serviceMailbox.getInputStream();
            InputStreamReader handmaid = new InputStreamReader(aMessageFromMyLove);

            boolean read = true;
            char input;
            final char SEALED_WITH_A_KISS = 'x';

            while(read)
            {
                input = (char)handmaid.read();

                if(input != SEALED_WITH_A_KISS)
                {
                    myLovesWords.append(input);
                }
                else
                {
                    read = false;
                }
            }


            tmp = Double.parseDouble(myLovesWords.toString());
            System.out.println("Juliet: Letter received.");
        }
        catch (IOException e)
        {
            System.out.println("ERROR. Juliet.receiveLoveLetter()");
            System.out.println(e);
            System.exit(1);
        }

        System.out.println("Juliet: Romeo, Romeo! Wherefore art thou Romeo? (<-" + tmp + ")");
        return tmp;
    }


    //Love (The ODE system)
    //Given the lover's love at time t, estimate the next love value for Romeo
    public double renovateLove(double partnerLove){
        System.out.println("Juliet: Come, gentle night, come, loving black-browed night,\n" +
                "       Give me my Romeo, and when I shall die,\n" +
                "       Take him and cut him out in little stars.");
        currentLove = currentLove+(-b*partnerLove);
        return currentLove;
    }


    //Communicate love back to playwriter
    public void declareLove(){
        try
        {
            OutputStream aMessageToMyLove = serviceMailbox.getOutputStream();
            OutputStreamWriter fairHerald = new OutputStreamWriter(aMessageToMyLove);
            String takeThisMissiveToRomeo = currentLove + "X";
            System.out.println("Good night, good night! Parting is such sweet sorrow, That I shall say good night till it be morrow (->" + takeThisMissiveToRomeo + ")");
            fairHerald.write(takeThisMissiveToRomeo);
            fairHerald.flush();

        }
        catch (IOException e)
        {
            System.out.println("ERROR. Juliet.declareLove()");
            System.out.println(e);
            System.exit(1);
        }
    }

    //Execution
    public void run () {
        try {
            while (!this.isInterrupted()) {
                //Retrieve lover's current love
                double RomeoLove = this.receiveLoveLetter();

                //Estimate new love value
                this.renovateLove(RomeoLove);

                //Communicate back to lover, Romeo's love
                this.declareLove();
            }
        }catch (Exception e){
            System.out.println("Juliet: " + e);
        }
        if (this.isInterrupted()) {
            System.out.println("Juliet: I will kiss thy lips.\n" +
                    "Haply some poison yet doth hang on them\n" +
                    "To make me die with a restorative.");
        }

    }

}
