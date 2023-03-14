/*
 * Romeo.java
 *
 * Romeo class.  Implements the Romeo subsystem of the Romeo and Juliet ODE system.
 */


import java.io.*;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

import javafx.util.Pair;

public class Romeo extends Thread {

    private ServerSocket ownServerSocket = null; //Romeo's (server) socket
    private Socket serviceMailbox = null; //Romeo's (service) socket


    private double currentLove = 0;
    private double a = 0; //The ODE constant

    //Class construtor
    public Romeo(double initialLove) {
        currentLove = initialLove;
        a = 0.02;
        try {

            String thisIp = "localhost";
            final int THE_PORT = 6289;
            final int MAX_LOVERS = 1;

            ownServerSocket = new ServerSocket(THE_PORT, MAX_LOVERS, InetAddress.getByName(thisIp));

            System.out.println("Romeo: What lady is that, which doth enrich the hand\n" +
                    "       Of yonder knight?");
        } catch(Exception e) {
            System.out.println("Romeo: Failed to create own socket " + e);
        }
   }

    //Get acquaintance with lover;
    public Pair<InetAddress,Integer> getAcquaintance() {
        System.out.println("Romeo: Did my heart love till now? forswear it, sight! For I ne'er saw true beauty till this night.");

        return new Pair<InetAddress,Integer>(ownServerSocket.getInetAddress(), ownServerSocket.getLocalPort());
			
    }


    //Retrieves the lover's love
    public double receiveLoveLetter()
    {

        double tmp = 999999;
        System.out.println("Romeo: Entering service iteration.\nAwaiting letter.");
        StringBuffer myLovesWords = new StringBuffer(""); //StringBuffer is thread safe
        try
        {
            serviceMailbox = ownServerSocket.accept();
            InputStream harkAletterFromJuliet = serviceMailbox.getInputStream();
            InputStreamReader mercutio = new InputStreamReader(harkAletterFromJuliet);

            boolean read = true;
            char input;
            final char A_KISS_FROM_MY_LOVE = 'X';
            while(read)
            {
                input = (char) mercutio.read();
                if(input != A_KISS_FROM_MY_LOVE)
                {
                    myLovesWords.append(input);
                }
                else
                {
                    read = false;
                }
            }
            System.out.println("Romeo: Letter received");
            tmp = Double.parseDouble(myLovesWords.toString());
        }
        catch (IOException e)
        {
            System.out.println("ERROR Romeo.receiveLoveLetter()");
            System.out.println(e);
            System.exit(1);
        }
        System.out.println("Romeo: O sweet Juliet... (<-" + tmp + ")");
        return tmp;
    }


    //Love (The ODE system)
    //Given the lover's love at time t, estimate the next love value for Romeo
    public double renovateLove(double partnerLove){
        System.out.println("Romeo: But soft, what light through yonder window breaks?\n" +
                "       It is the east, and Juliet is the sun.");
        currentLove = currentLove+(a*partnerLove);
        return currentLove;
    }


    //Communicate love back to playwriter
    public void declareLove(){
        try
        {
            OutputStream aMessageToMyLove = serviceMailbox.getOutputStream();
            OutputStreamWriter fairHerald = new OutputStreamWriter(aMessageToMyLove);
            String messageToFairJuliet = currentLove + "x";
            fairHerald.write(messageToFairJuliet);
            fairHerald.flush();

            fairHerald.close();
            aMessageToMyLove.close();
            System.out.println("I would were thy bird. (->" + messageToFairJuliet + ")");
            System.out.println("Romeo: Exiting service iteration. ");
        }
        catch (IOException e)
        {
            System.out.println("ERROR Romeo.declareLove()");
            System.out.println(e);
            System.exit(1);
        }
    }



    //Execution
    public void run () {
        try {
            while (!this.isInterrupted()) {
                //Retrieve lover's current love
                double JulietLove = this.receiveLoveLetter();

                //Estimate new love value
                this.renovateLove(JulietLove);

                //Communicate love back to playwriter
                this.declareLove();
            }
        }catch (Exception e){
            System.out.println("Romeo: " + e);
        }
        if (this.isInterrupted()) {
            System.out.println("Romeo: Here's to my love. O true apothecary,\n" +
                    "Thy drugs are quick. Thus with a kiss I die." );
        }
    }

}
