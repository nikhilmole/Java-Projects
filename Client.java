import java.net.*;
import java.io.*;

class Client
{
    public static void main(String Arg[])  throws Exception
    {
        System.out.println("Client Application is runnig...");


        Socket sobj = new Socket("localhost",2100);
        System.out.println("Client is succefully connected");



        
        PrintStream ps = new PrintStream(sobj.getOutputStream());
        BufferedReader br1 = new BufferedReader(new InputStreamReader(sobj.getInputStream()));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
        String str1, str2;

        System.out.println("Marvellous massenger started");
        while(!(str1 = br2.readLine()).equals("end"))
        {
            ps.println(str1);
            str2 = br1.readLine();
            System.out.println("Enter massage for server :");
            System.out.println("Server says :" +str2);
        }
    }
}