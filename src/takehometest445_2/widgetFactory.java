
package takehometest445_2;


public class widgetFactory {
    public static int[][] CostMatrix = new int[30][30];
    public static int[][] C_orig = new int[30][30];
    public static int[][] MaskMatrix = new int[30][30];
    public static int[][] path = new int[61][2];
    public static int[] RowCover = new int[30];
    public static int[] ColCover = new int[30];
    public static int nrow;
    public static int ncol;
    public static int path_count = 0;
    public static int path_row_0;
    public static int path_col_0;
    public static int asgn = 0;
    public static int step;
    
    void widgetFactory(){
        RunMunkres();
    }
    
    private static void RunMunkres()
    {
        Boolean done = false;
        while (!done)
        {
            ShowCostMatrix();
            ShowMaskMatrix();
            switch (step)
            {
                case 1:
                    step_1( step);
                    break;
                case 2:
                    step_2( step);
                    break;
                case 3:
                    step_3( step);
                    break;
                case 4:
                    step_4( step);
                    break;
                case 5:
                    step_5( step);
                    break;
                case 6:
                    step_6( step);
                    break;
                case 7:
                    step_7( step);
                    done = true;
                    break;
            }
        }
    }

    private static void ShowCostMatrix()
    {
        System.out.println("\n");
        System.out.println("------------Step {0}-------------"+ step);
        for (int r = 0; r < nrow; r++)
        {
            System.out.println();
            System.out.print("     ");
            for (int c = 0; c < ncol; c++)
            {
                System.out.print((CostMatrix[r][c]) + " ");
            }
        }
    }

    private static void ShowMaskMatrix()
    {
        System.out.println();
        System.out.print("\n    ");
        for (int c = 0; c < ncol; c++)
            System.out.print(" " + ColCover[c]);
        for (int r = 0; r < nrow; r++)
        {
            System.out.print("\n  " + (RowCover[r]) + "  ");
            for (int c = 0; c < ncol; c++)
            {
                System.out.print((MaskMatrix[r][c]) + " ");
            }
        }
    }
    //For each row of the cost matrix, find the smallest element and subtract
    //it from every element in its row.  When finished, Go to Step 2.
    static void step_1(int x){
         int min_in_row;

            for (int r = 0; r < nrow; r++)
            {
                min_in_row = CostMatrix[r][0];
                for (int c = 0; c < ncol; c++)
                    if (CostMatrix[r][c] < min_in_row)
                        min_in_row = CostMatrix[r][c];
                for (int c = 0; c < ncol; c++)
                    CostMatrix[r][c] -= min_in_row;
            }
            step = 2;

    }
    //Find a zero (Z) in the resulting matrix.  If there is no starred 
    //zero in its row or column, star Z. Repeat for each element in the 
    //matrix. Go to Step 3.
    private static void step_2( int step)
    {
        for (int r = 0; r < nrow; r++)
            for (int c = 0; c < ncol; c++)
            {
                if (CostMatrix[r][c] == 0 && RowCover[r] == 0 && ColCover[c] == 0)
                {
                    MaskMatrix[r][c] = 1;
                    RowCover[r] = 1;
                    ColCover[c] = 1;
                }
            }
        for (int r = 0; r < nrow; r++)
            RowCover[r] = 0;
        for (int c = 0; c < ncol; c++)
            ColCover[c] = 0;
        step = 3;
    }

    //Cover each column containing a starred zero.  If K columns are covered, 
    //the starred zeros describe a complete set of unique assignments.  In this 
    //case, Go to DONE, otherwise, Go to Step 4.
    private static void step_3( int step)
    {
        int colcount;
        for (int r = 0; r < nrow; r++)
            for (int c = 0; c < ncol; c++)
                if (MaskMatrix[r] [c] == 1)
                    ColCover[c] = 1;

        colcount = 0;
        for (int c = 0; c < ncol; c++)
            if (ColCover[c] == 1)
                colcount += 1;
        if (colcount >= ncol || colcount >=nrow)
            step = 7;
        else
            step = 4;
    }

    //methods to support step 4
    private static void find_a_zero( int row,  int col)
    {
        int r = 0;
        int c;
        Boolean done;
        row = -1;
        col = -1;
        done = false;
        while (!done)
        {
            c = 0;
            while (true)
            {
                if (CostMatrix[r][c] == 0 && RowCover[r] == 0 && ColCover[c] == 0)
                {
                    row = r;
                    col = c;
                    done = true;
                }
                c += 1;
                if (c >= ncol || done)
                    break;
            }
            r += 1;
            if (r >= nrow)
                done = true;
        }
    }

    private static Boolean star_in_row(int row)
    {
        Boolean tmp = false;
        for (int c = 0; c < ncol; c++)
            if (MaskMatrix[row][c] == 1)
                tmp = true;
        return tmp;
    }

    private static void find_star_in_row(int row, int col)
    {
        col = -1;
        for (int c = 0; c < ncol; c++)
            if (MaskMatrix[row][c] == 1)
                col = c;
    }

    //Find a noncovered zero and prime it.  If there is no starred zero 
    //in the row containing this primed zero, Go to Step 5.  Otherwise, 
    //cover this row and uncover the column containing the starred zero. 
    //Continue in this manner until there are no uncovered zeros left. 
    //Save the smallest uncovered value and Go to Step 6.
    private static void step_4( int step)
    {
        int row = -1;
        int col = -1;
        Boolean done;

        done = false;
        while (!done)
        {
            find_a_zero(row, col);
            if (row == -1)
            {
                done = true;
                step = 6;
            }
            else
            {
                MaskMatrix[row][col] = 2;
                if (star_in_row(row))
                {
                    find_star_in_row(row, col);
                    RowCover[row] = 1;
                    ColCover[col] = 0;
                }
                else
                {
                    done = true;
                    step = 5;
                    path_row_0 = row;
                    path_col_0 = col;
                }
            }
        }
    }

    // methods to support step 5
    private static void find_star_in_col(int c, int r)
    {
        r = -1;
        for (int i = 0; i < nrow; i++)
            if (MaskMatrix[i][c] == 1)
                r = i;
    }

    private static void find_prime_in_row(int r, int c)
    {
        for (int j = 0; j < ncol; j++)
            if (MaskMatrix[r][j] == 2)
                c = j;
    }

    private static void augment_path()
    {
        for (int p = 0; p < path_count; p++)
            if (MaskMatrix[path[p][0]] [path[p][1]] == 1)
                MaskMatrix[path[p][0]] [path[p][1]] = 0;
            else
                MaskMatrix[path[p][0]] [path[p][1]] = 1;
    }

    private static void clear_covers()
    {
        for (int r = 0; r < nrow; r++)
            RowCover[r] = 0;
        for (int c = 0; c < ncol; c++)
            ColCover[c] = 0;
    }

    private static void erase_primes()
    {
        for (int r = 0; r < nrow; r++)
            for (int c = 0; c < ncol; c++)
                if (MaskMatrix[r][c] == 2)
                    MaskMatrix[r][c] = 0;
    }


    //Construct a series of alternating primed and starred zeros as follows.  
    //Let Z0 represent the uncovered primed zero found in Step 4.  Let Z1 denote 
    //the starred zero in the column of Z0 (if any). Let Z2 denote the primed zero 
    //in the row of Z1 (there will always be one).  Continue until the series 
    //terminates at a primed zero that has no starred zero in its column.  
    //Unstar each starred zero of the series, star each primed zero of the series, 
    //erase all primes and uncover every line in the matrix.  Return to Step 3.
    private static void step_5(int step)
    {
        Boolean done;
        int r = -1;
        int c = -1;

        path_count = 1;
        path[path_count - 1][0] = path_row_0;
        path[path_count - 1][1] = path_col_0;
        done = false;
        while (!done)
        {
            find_star_in_col(path[path_count - 1][1], r);
            if (r > -1)
            {
                path_count += 1;
                path[path_count - 1][0] = r;
                path[path_count - 1][1] = path[path_count - 2][1];
            }
            else
                done = true;
            if (!done)
            {
                find_prime_in_row(path[path_count - 1][0],c);
                path_count += 1;
                path[path_count - 1][0] = path[path_count - 2][0];
                path[path_count - 1][1] = c;
            }
        }
        augment_path();
        clear_covers();
        erase_primes();
        step = 3;
    }

    //methods to support step 6
    private static void find_smallest(int minval)
    {
        for (int r = 0; r < nrow; r++)
            for (int c = 0; c < ncol; c++)
                if (RowCover[r] == 0 && ColCover[c] == 0)
                    if (minval > CostMatrix[r][c])
                        minval = CostMatrix[r][c];
    }

    //Add the value found in Step 4 to every element of each covered row, and subtract 
    //it from every element of each uncovered column.  Return to Step 4 without 
    //altering any stars, primes, or covered lines.
    private static void step_6(int step)
    {
        int minval = Integer.MAX_VALUE;
        find_smallest(minval);
            
        for (int r = 0; r < nrow; r++)
            for (int c = 0; c < ncol; c++)
            {
                if (RowCover[r] == 1)
                    CostMatrix[r][c] += minval;
                if (ColCover[c] == 0)
                    CostMatrix[r] [c] -= minval;
            }
        step = 4;
    }

    private static void step_7(int step)
    {
        System.out.println("\n\n---------Run Complete----------");
    }
}
