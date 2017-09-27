package MultipleConnectionServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import java.util.Scanner;

public class ClientConnection implements Runnable {
    // CL er vores chat log, hvor vi gemmer chat strenge
    private final static ArrayList<String> CL = new ArrayList<>();
    private final Socket s;
    //default client name
    private String name = "Guest ";

    public ClientConnection(Socket s) throws SocketException, IOException {

        this.s = s;
    }

    @Override
    public void run() {
        try {
            try {
                
                //input stream og output stream defineres. 
              
                InputStream input = s.getInputStream();
                OutputStream output = s.getOutputStream();

              
                //print writer og scanner
                Scanner in = new Scanner(input);
                PrintWriter out = new PrintWriter(output, true);

                //vores første besked til klienten
                out.println("Velkommen");
              
                
                
                //kører så længe der er inputstream fra connection
                boolean done = false;
                while (!done && in.hasNextLine()) {
                     
                    //vi sætter inputet fra klienten til string Stream
                    String stream = in.nextLine();
                    
                    
                    
                    //serveren forventer 5 start på strenge fra klienten ("luk ned","NAME:","PUT:","COUNT","GET:"). der er også en default som giver error
                    if (stream.equals("luk ned")) {
                        done = true;
                    } else {
                        int vælger=0;
                        if(stream.startsWith("NAME:")){
                            vælger=1;
                        }else if(stream.startsWith("PUT:")){
                            vælger=2;
                        }else if(stream.startsWith("COUNT")){
                            vælger=3;
                        }else if(stream.startsWith("GET:")){
                            vælger=4;
                        }else{
                            vælger=0;
                        }
                        
                        
                        
                        switch (vælger){
                            
                            case 1: 
                                //erstater guest med navnet på klienten
                                this.name = stream.replace("NAME:", "");
                                this.name += " ";
                                break;
                            
                            case 2:
                                //tilføjer strengen til CL, vha. PUT
                                CL.add(this.name+stream.replace("PUT", ""));
                                out.println(this.name+stream.replace("PUT", ""));
                                break;
                                
                            case 3:
                                
                                //sender størelsen på arrayet ud til klienten får en counter igen sammenligner differencen
                                out.println(CL.size());
                                
                                int clientCounter =Integer.parseInt(in.nextLine());
                                for (int i = clientCounter; i < CL.size(); i++) {
                                    out.println(CL.get(i));
                                    
                                }

                                break;
                            
                            case 4:
                                //får med det ønskede nr fra klienten
                                stream.replace("GET:", "");
                                out.println(CL.get(stream.charAt(0)));
                                break;
                                
                            default: out.println("ERROR");
                            break;
                            
                        }
                        

                    }
                }
            } finally {
                //lukker forbindelsen
                s.close();
            }
        } catch (IOException | NumberFormatException e) {
        }
    }

}
