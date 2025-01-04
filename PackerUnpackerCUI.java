import java.util.*;
import java.io.*;

class PackerUnpackerCUI
{
    private static final String KEY = "my_secret_key"; 

    public static void main(String Arg[]) throws Exception
    {
        int ichoice = 0;
        Scanner sobj = new Scanner(System.in);

        System.out.println("-----------------------------------------------------");
        System.out.println("------- Marvellous Packer Unpacker CUI Module -------");
        System.out.println("-----------------------------------------------------");

        System.out.println("Choose the operation you want to perform:");
        System.out.println("1: Packing");
        System.out.println("2: Unpacking");

        ichoice = sobj.nextInt();
        sobj.nextLine(); 

        switch (ichoice)
        {
            case 1:
                packingActivity(sobj);
                break;

            case 2:
                unpackingActivity(sobj);
                break;

            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    public static void packingActivity(Scanner sobj) throws Exception
    {
        System.out.println("----------------- Packing Activity ------------------");
        System.out.println();

        System.out.println("Enter the name of Directory that you want to open for packing : ");
        String FolderName = sobj.nextLine();

        File fobj = new File(FolderName);

        System.out.println("Enter the name of packed file that you want to create : ");
        String PackedFile = sobj.nextLine();

        File Packobj = new File(PackedFile);

        boolean bret = Packobj.createNewFile();
        if (bret == false)
        {
            System.out.println("Unable to create packed file");
            return;
        }

        FileOutputStream foobj = new FileOutputStream(Packobj);

        if (fobj.exists())
        {
            int i = 0, j = 0;
            int iCount = 0;
            int iRet = 0;
            
            File Arr[] = fobj.listFiles();

            String Header = null;
            
            byte Buffer[] = new byte[1024];
            FileInputStream fiobj = null;

            for (i = 0; i < Arr.length; i++)
            {
                Header = Arr[i].getName();

                if (Header.endsWith(".txt"))
                {
                    System.out.println("File packed with name : " + Header);

                    Header = Header + " " + Arr[i].length();

                    for (j = Header.length(); j < 100; j++)
                    {
                        Header = Header + " ";
                    }

                    foobj.write(Header.getBytes(), 0, 100);

                    fiobj = new FileInputStream(Arr[i]);

                    while ((iRet = fiobj.read(Buffer)) != -1)
                    {
                        byte[] encryptedBuffer = encryptDecrypt(Buffer, iRet);
                        foobj.write(encryptedBuffer, 0, encryptedBuffer.length);
                    }

                    fiobj.close();
                    iCount++;
                }
            }

            System.out.println("-----------------------------------------------------");
            System.out.println("Packing activity completed..");
            System.out.println("Number of files scanned : " + Arr.length);
            System.out.println("Number of files packed : " + iCount);
            System.out.println("-----------------------------------------------------");

            System.out.println("Thank you for using Marvellous Packer Unpacker tool");
            foobj.close();
        }
        else
        {
            System.out.println("There is no such directory");
        }
    }

    public static void unpackingActivity(Scanner sobj) throws Exception
    {
        System.out.println("---------------- Unpacking Activity -----------------");
        System.out.println();

        System.out.println("Enter the name of Packed file that you want to open : ");
        String PackedFile = sobj.nextLine();

        File fobj = new File(PackedFile);

        if (!fobj.exists())
        {
            System.out.println("Unable to proceed as Packed file is missing...");
            return;
        }

        FileInputStream fiobj = new FileInputStream(fobj);

        byte Header[] = new byte[100];
        int iRet = 0;
        String HeaderX = null;
        File obj = null;
        int FileSize = 0;
        FileOutputStream foobj = null;
        int iCount = 0;

        while ((iRet = fiobj.read(Header, 0, 100)) > 0)
        {
            HeaderX = new String(Header);
            HeaderX = HeaderX.trim();

            String Tokens[] = HeaderX.split(" ");

            obj = new File(Tokens[0]);
            System.out.println("File created with name : " + Tokens[0]);

            obj.createNewFile();

            FileSize = Integer.parseInt(Tokens[1]);
            byte Buffer[] = new byte[FileSize];

            fiobj.read(Buffer, 0, FileSize);

            byte[] decryptedBuffer = encryptDecrypt(Buffer, FileSize);
            foobj = new FileOutputStream(obj);
            foobj.write(decryptedBuffer, 0, FileSize);

            foobj.close();
            iCount++;
        }

        System.out.println("-----------------------------------------------------");
        System.out.println("Unpacking activity completed..");
        System.out.println("Number of files unpacked : " + iCount);
        System.out.println("-----------------------------------------------------");

        System.out.println("Thank you for using Marvellous Packer Unpacker tool");

        fiobj.close();
    }

    private static byte[] encryptDecrypt(byte[] data, int length)
    {
        byte[] result = new byte[length];
        byte[] keyBytes = KEY.getBytes();

        for (int i = 0; i < length; i++)
        {
            result[i] = (byte) (data[i] ^ keyBytes[i % keyBytes.length]);
        }
        return result;
    }
}
