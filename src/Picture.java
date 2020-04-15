public class Picture {
    private char[][] picture_data =
            {
                    {' ',' ','_','_','_','_','_'},
                    {' ',' ','|',' ',' ','\\','|'},
                    {' ',' ','O',' ',' ',' ','|'},
                    {' ','/','|','\\',' ',' ','|'},
                    {' ','/',' ','\\',' ',' ','|'},
                    {' ','_','_','_','_','_','|'},
                    {'|',' ',' ',' ',' ',' ','|'}
            };
    private int[][] reveal_positions = {{6,0},{6,6},{5,1},{5,2},{5,3},{5,4},{5,5},{5,6},{4,6},{3,6},{2,6},{1,6},{1,5},
                                        {0,6},{0,5},{0,4},{0,3},{0,2},{1,2},{2,2},{3,2},{3,1},{3,3},{4,1},{4,3}};

    private boolean table[][] = new boolean[7][7];

    public Picture(){
        for (int i = 0; i <= 18; i++){
            table[reveal_positions[i][0]][reveal_positions[i][1]] = true;
        }
    }

    private int position = 18;

    public boolean reveal(){
        if (position + 1 == 24){
            return false;
        }
        position++;
        table[reveal_positions[position][0]][reveal_positions[position][1]] = true;

        return true;
    }

    public void printOut(){
        for (int i = 0; i <= 6; i++){
            for (int j = 0; j <= 6; j++){
                if (table[i][j]){
                    System.out.print(picture_data[i][j]);
                }
                else{
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

    public void printOutWhole(){
        for (int i = 0; i <= 6; i++){
            for (int j = 0; j <= 6; j++) {
                System.out.print(picture_data[i][j]);
            }
            System.out.println();
        }
    }
}
