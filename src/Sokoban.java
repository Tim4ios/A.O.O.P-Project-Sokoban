public class Sokoban extends Engine {
	private char[][] level = new char[10][10];
	private Sprite box;
	private Sprite wall;
	private Sprite player;
	private Sprite blank;
	
	public Sokoban() {
		title = "Sokoban";
		width = 600;
		height = 600;

	}
	
	@Override
	public void onUserStart() {
		for (int i = 0; i <10 ; i++) {
			 level[0][i] = '*'; //row 1       * * * *
			 level[9][i] = '*'; //row 10      *     *
			 level[i][0] = '*'; //column 1    *     *
			 level[i][9] = '*';
		}
		level[1][1] = 'P';
        level[4][4] = 'B'; //Box is in middle
        level[8][8] = 'T';
		box = new Sprite("res/Textures/sokoban_icons/crate.png");
		wall = new Sprite("res/Textures/sokoban_icons/wall.png");
		blank = new Sprite("res/Textures/sokoban_icons/blank.png");
	}

	@Override
	public void onUserUpdate() {
		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[i].length; j++) {
				switch(level[i][j]) {
				case '*':
					draw(wall, i * width / 10, j * height / 10);
					break;
				case 'B':
					draw(box, i * width / 10, j * width / 10);
					break;
				default:
					draw(blank, i*width/10, j*height/10);
					break;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Sokoban s = new Sokoban();
		if (s.init()) {
			s.start();			
		} else {
			System.out.println("error during init");
			System.exit(1);
		}
	}
}
