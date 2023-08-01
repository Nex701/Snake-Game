import javax.swing.JFrame;

public class GameFrame extends JFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

GameFrame() {
    add(new GamePanel());
    setTitle("Snake");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    pack();
    setVisible(true);
    setLocationRelativeTo(null);
  }
}
