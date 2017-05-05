
package takehometest445_2;

import java.io.*;
import java.util.Scanner;

public class tributeTour {
    
    //The code beyond this point was worked on at 3 AM 
    //and I honestly have no idea where it came from
    public static int[][] readMat()
    {    
        try    
        {

            Scanner input = new Scanner(new File("adjmat.txt"));
            int nrows = input.nextInt();
            int ncols = input.nextInt();
            int[][] m = new int[nrows][ncols];
            
            for(int i = 0; i < nrows; i++)
                for(int j = 0; j < ncols; j++)
                    m[i][j] = input.nextInt();
            
            input.close();
            return m;
            }
        
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    public static void showMat(int[][] m)
    {
        for(int i=0;i<m.length;i++)
        {
            for(int j=0;j<m[0].length;j++)
                System.out.print(m[i][j] + "\t");
            System.out.println();
        }
    }





}
