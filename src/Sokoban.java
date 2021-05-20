public class Sokoban extends Engine {
	public Sokoban() {
		title = "Sokoban";
		width = 600;
		height = 600;
	}

	@Override
	public void onUserStart() {
		
	}

	@Override
	public void onUserUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		Sokoban s = new Sokoban();
		if (s.init()) {			
			s.start();
		}
	}
}
