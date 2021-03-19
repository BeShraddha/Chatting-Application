package GroupApplication;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable{
    
    Socket socket;
    
    //vector for storing multiple clients
    public static Vector client = new Vector(); 
    
    Server(Socket s){
        socket = s;
    }
    
    public void run(){
        
        
        try{
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            client.add(writer);
            
            //fetching multiple messgaes from multiple client
            while(true){
                String msg = reader.readLine().trim();
                System.out.println(msg);
                
                //sending message to all client
                for(int i=0;i<client.size();i++){
                    BufferedWriter bw = (BufferedWriter) client.get(i);
                    bw.write(msg);
                    bw.write("\r\n");
                    bw.flush();
                }
            }
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void main(String args[])throws Exception{
        ServerSocket sskt = new ServerSocket(9998);
        
        
        //used while loop for fetching multiple client
        while(true){
            
            Socket socket = sskt.accept();
            Server server = new Server(socket);
            
            //tread to access all the clients at same time
            Thread thread = new Thread(server);
            thread.start();
        }
    }
    
}
