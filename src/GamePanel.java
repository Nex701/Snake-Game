import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
  static final int SCREEN_WIDTH = 600;
  
  static final int SCREEN_HEIGHT = 600;
  
  static final int UNIT_SIZE = 25;
  
  static final int GAME_UNITS = 14400;
  
  static final int DELAY = 75;
  
  final int[] x = new int[14400];
  
  final int[] y = new int[14400];
  
  int bodyParts = 6;
  
  int applesEaten;
  
  int appleX;
  
  int appleY;
  
  char direction = 'R';
  
  boolean running = false;
  
  Timer timer;
  
  Random random;
  
  GamePanel() {
    this.random = new Random();
    setPreferredSize(new Dimension(600, 600));
    setBackground(Color.black);
    setFocusable(true);
    addKeyListener(new MyKeyAdapter());
    startGame();
  }
  
  public void startGame() {
    newApple();
    this.running = true;
    this.timer = new Timer(75, this);
    this.timer.start();
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
  }
  
  public void draw(Graphics g) {
    if (this.running) {
      g.setColor(Color.pink);
      g.fillOval(this.appleX, this.appleY, 25, 25);
      for (int i = 0; i < this.bodyParts; i++) {
        if (i == 0) {
          g.setColor(Color.green);
          g.fillRect(this.x[i], this.y[i], 25, 25);
        } else {
          g.setColor(new Color(45, 180, 0));
          g.fillRect(this.x[i], this.y[i], 25, 25);
        } 
      } 
      g.setColor(Color.pink);
      g.setFont(new Font("LCD", 1, 40));
      FontMetrics metrics = getFontMetrics(g.getFont());
      g.drawString("Score: " + this.applesEaten, (600 - metrics.stringWidth("Score: " + this.applesEaten)) / 2, g.getFont().getSize());
    } else {
      gameOver(g);
    } 
  }
  
  public void newApple() {
    this.appleX = this.random.nextInt(24) * 25;
    this.appleY = this.random.nextInt(24) * 25;
  }
  
  public void move() {
    for (int i = this.bodyParts; i > 0; i--) {
      this.x[i] = this.x[i - 1];
      this.y[i] = this.y[i - 1];
    } 
    switch (this.direction) {
      case 'U':
        this.y[0] = this.y[0] - 25;
        break;
      case 'D':
        this.y[0] = this.y[0] + 25;
        break;
      case 'L':
        this.x[0] = this.x[0] - 25;
        break;
      case 'R':
        this.x[0] = this.x[0] + 25;
        break;
    } 
  }
  
  public void checkApple() {
    if (this.x[0] == this.appleX && this.y[0] == this.appleY) {
      this.bodyParts++;
      this.applesEaten++;
      newApple();
    } 
  }
  
  public void checkCollisions() {
    for (int i = this.bodyParts; i > 0; i--) {
      if (this.x[0] == this.x[i] && this.y[0] == this.y[i])
        this.running = false; 
    } 
    if (this.x[0] < 0)
      this.running = false; 
    if (this.x[0] > 600)
      this.running = false; 
    if (this.y[0] < 0)
      this.running = false; 
    if (this.y[0] > 600)
      this.running = false; 
    if (!this.running)
      this.timer.stop(); 
  }
  
  public void gameOver(Graphics g) {
    g.setColor(Color.pink);
    g.setFont(new Font("LCD", 1, 40));
    FontMetrics metrics1 = getFontMetrics(g.getFont());
    g.drawString("Score: " + this.applesEaten, (600 - metrics1.stringWidth("Score: " + this.applesEaten)) / 2, g.getFont().getSize());
    g.setColor(Color.PINK);
    g.setFont(new Font("LCD", 1, 75));
    FontMetrics metrics2 = getFontMetrics(g.getFont());
    g.drawString("Game Over", (600 - metrics2.stringWidth("Game Over")) / 2, 300);
  }
  
  public void actionPerformed(ActionEvent e) {
    if (this.running) {
      move();
      checkApple();
      checkCollisions();
    } 
    repaint();
  }
  
  public class MyKeyAdapter extends KeyAdapter {
    @Override
	  public void keyPressed(KeyEvent e) {
    	
      switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
          if (direction != 'R')
            direction = 'L'; 
          break;
        case KeyEvent.VK_RIGHT:
          if (direction != 'L')
            direction = 'R'; 
          break;
        case KeyEvent.VK_UP:
          if (direction != 'D')
            direction = 'U'; 
          break;
        case KeyEvent.VK_DOWN:
          if (direction != 'U')
            direction = 'D'; 
          break;
      } 
    }
  }
}
