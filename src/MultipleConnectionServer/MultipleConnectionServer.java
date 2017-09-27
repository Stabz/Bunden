
package MultipleConnectionServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MultipleConnectionServer {
    
    public static void main(String[] args){
        
        
        //server socket oprettes
        ServerSocket ss;
        
        try{
            ss= new ServerSocket(8001);
            
            System.out.println("multiple connection Server kører");
            
            while(true){
                Socket incoming = ss.accept();
                
                
                Runnable r = new ClientConnection(incoming);
                
                Thread t = new Thread(r);
                t.start();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
    }
    
}
