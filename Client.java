import java.io.*;
import java.net.*;

public class Client {
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public Client(){
        try{
            socket = new Socket("127.0.0.1",7777);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            
            Recieve();
            Send();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    
    // to read data from the socket
    public void Recieve(){

        Runnable r1 = ()->{
            try{
                while(!socket.isClosed()){
                    String msg = in.readLine();
                    System.out.println("Server: "+msg);
                    if(msg.equals("exit")){
                        socket.close();
                        break;
                    }
                    
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        };
        new Thread(r1).start();
    }


    // to take input from system.in and send socket output stream
    public void Send(){
        Runnable r2 = ()->{

            try{
                while(!socket.isClosed()){
                    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                    String msg = input.readLine();
                    out.println(msg);
                    out.flush();
                    if(msg.equals("exit") )
                    {
                        socket.close();
                        break;
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
        };
        new Thread(r2).start();

    }

    public static void main(String args[]){
        System.out.println("-----------------Client Program-------------------");
        new Client();
    }

}
