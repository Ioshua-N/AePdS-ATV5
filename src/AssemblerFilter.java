import java.io.*;
import java.util.Scanner;


public class AssemblerFilter
{
    public static void main(String[] args)
    {
        // declarations
        Scanner scanner = new Scanner(System.in);
        int outputFormat = 0;

        // ask and get input file name
        System.out.print("Enter the name of the input file: ");
        String inputFileName = scanner.nextLine();

        // ask and get output file name
        System.out.print("Enter the name ou the output file: ");
        String outputFileName = scanner.nextLine();

        // sak and get output file type
        System.out.println("\n1 - Decimal\n2 - Hexadecimal\n3 - Binary\n");
        System.out.print("Enter the output file format: ");
        outputFormat = scanner.nextInt();

        try
        {
            InputStream inputStream = new FileInputStream(inputFileName);
            OutputStream outputStream = new FileOutputStream(outputFileName);
            int byteRead = -1, byteCount = 0, lineCount = 0;
            String content = "";

            // test if there are no more bytes to be read
            while ((byteRead = inputStream.read()) != -1)
            {
                byteCount += 1;
                String byteString = "";
                switch (outputFormat)
                {
                    case 1:
                        byteString = Integer.toString(byteRead);
                        break;
                    case 2:
                        byteString = Integer.toHexString(byteRead);
                        break;
                    case 3:
                        byteString = Integer.toBinaryString(byteRead);
                        break;
                    default:
                        System.out.println("Invalid output format.");
                        byteString = Integer.toString(byteRead);
                        break;
                }
                if (byteCount == 1)
                {
                    content += "db " + byteString + ",";
                }
                else if (byteCount % 8 == 0)
                {
                    lineCount++;
                    content += byteString + "; line " + (lineCount - 1) + " \ndb ";
                }
                else
                {
                    content += byteString + ",";
                }

                outputStream.write(content.getBytes());
                content = "";
            }

            if (byteCount % 8 != 0)
            {
                lineCount++;
                content += "; line " + (lineCount - 1);
            }

            outputStream.write(content.getBytes());
            content = "";

            outputStream.close();
            System.out.println("File generation succeeded!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
