public class PlayField {
    //Our playfield is 10x10 which will contain walls, player, boxes and target
    private char[][] field = new char[10][10];

    //Make the field an Obervable? Each time there is a change call gui observer to update

    public char[][] setUpLvlOne(){
        for (int i = 0; i <10 ; i++) {
            //Making a boarder which the player shall not pass
            field[0][i] = '*'; //row 1       * * * *
            field[9][i] = '*'; //row 10      *     *
            field[i][0] = '*'; //column 1    *     *
            field[i][9] = '*'; // column 10  * * * *
        }
        field[1][1] = 'P'; //Player starts in left top corner
        field[4][4] = 'B'; //Box is in middle
        field[8][8] = 'T'; //Target for box

        return field;
    }

    public void playerMoved(){

    }
}
