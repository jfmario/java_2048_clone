package com.adampjr.java2048;

import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.*;

public class GUI {

	Game game;

	int frameHeight = 394;
	int frameWidth = 328;
	int gameBoardSize = 296;
	int marginSize = 16;
	Color backgroundColor = new Color(255, 225, 120); // light golden yellow
	
	Font largeFeedbackFont = new Font("SansSerif", 0, 40);
	Font smallFeedbackFont = new Font("SansSerif", 0, 20);
	
	JLabel scoreLabel;
	JLabel highScoreLabel;
	
	Map numberTiles;
	GameBoard gb;
	MyFrame frame;
	
	public GUI() {
		game = new Game();
		frame = new MyFrame();
		frame.setFocusable(true);
		frame.addKeyListener(new MyFrame());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loadNumberTiles();
		
		gb = new GameBoard();
		// gb.setFocusable(true);
		
		// North Panel
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout());
		northPanel.setPreferredSize(new Dimension(frameWidth, 82));
		
		JLabel gameLabel = new JLabel("1024", SwingConstants.CENTER);
		gameLabel.setFont(new Font("Serif", Font.BOLD, 20));
		northPanel.add(gameLabel);
		scoreLabel = new JLabel("<html>Score:<br>0</html>", SwingConstants.CENTER);
		northPanel.add(scoreLabel);
		highScoreLabel = new JLabel("<html>High Score:<br>" + getHighScore() + "</html>", SwingConstants.CENTER);
		northPanel.add(highScoreLabel);
		northPanel.setBackground(backgroundColor);
		
		// Other Panels
		JPanel westBuffer = new JPanel();
		westBuffer.setPreferredSize(new Dimension(marginSize, gameBoardSize));
		westBuffer.setBackground(backgroundColor);
		
		JPanel eastBuffer = new JPanel();
		eastBuffer.setPreferredSize(new Dimension(marginSize, gameBoardSize));
		eastBuffer.setBackground(backgroundColor);
		
		JPanel southBuffer = new JPanel();
		southBuffer.setPreferredSize(new Dimension(frameWidth, marginSize));
		southBuffer.setBackground(backgroundColor);
		
		// add panels to frame
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		frame.getContentPane().add(westBuffer, BorderLayout.WEST);
		frame.getContentPane().add(eastBuffer, BorderLayout.EAST);
		frame.getContentPane().add(southBuffer, BorderLayout.SOUTH);
		frame.getContentPane().add(gb, BorderLayout.CENTER);
		
		frame.getContentPane().setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.pack();
		frame.setVisible(true);
	}
	
	private void loadNumberTiles() {
		numberTiles = new Hashtable();
		ClassLoader cldr = this.getClass().getClassLoader();
		URL url0000 = cldr.getResource("images/tile00_64.png");
		URL url0001 = cldr.getResource("images/tile01_64.png");
		URL url0002 = cldr.getResource("images/tile02_64.png");
		URL url0004 = cldr.getResource("images/tile04_64.png");
		URL url0008 = cldr.getResource("images/tile08_64.png");
		URL url0016 = cldr.getResource("images/tile16_64.png");
		URL url0032 = cldr.getResource("images/tile32_64.png");
		URL url0064 = cldr.getResource("images/tile64_64.png");
		URL url0128 = cldr.getResource("images/tile128_64.png");
		URL url0256 = cldr.getResource("images/tile256_64.png");
		URL url0512 = cldr.getResource("images/tile512_64.png");
		URL url1024 = cldr.getResource("images/tile1024_64.png");
		numberTiles.put(0, new ImageIcon(url0000));
		numberTiles.put(1, new ImageIcon(url0001));
		numberTiles.put(2, new ImageIcon(url0002));
		numberTiles.put(4, new ImageIcon(url0004));
		numberTiles.put(8, new ImageIcon(url0008));
		numberTiles.put(16, new ImageIcon(url0016));
		numberTiles.put(32, new ImageIcon(url0032));
		numberTiles.put(64, new ImageIcon(url0064));
		numberTiles.put(128, new ImageIcon(url0128));
		numberTiles.put(256, new ImageIcon(url0256));
		numberTiles.put(512, new ImageIcon(url0512));
		numberTiles.put(1024, new ImageIcon(url1024));
	}
	
	class GameBoard extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(new Color(20, 20, 20));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			int[][] board = game.getGameBoard();
			for (int y = 1; y < 5; y++) {
				for (int x = 1; x < 5; x++) {
					int X = (8 * x) + (64 * (x - 1));
					int Y = (8 * y) + (64 * (y - 1));
					
					int thisNumber = board[y-1][x-1];
					
					if (numberTiles.containsKey(thisNumber)) {
						ImageIcon thisTile = (ImageIcon) numberTiles.get(thisNumber);
						thisTile.paintIcon(this, g, X, Y);
					}
				}
			}
		}
	}
	class WinBoard extends JPanel {
	    @Override
		protected void paintComponent(Graphics g) {
		    g.setColor(new Color(20, 20, 20));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setFont(largeFeedbackFont);
			g.setColor(new Color(0, 80, 0));
			g.drawString("You Win!", 20, 40);
            g.setFont(smallFeedbackFont);
            g.setColor(new Color(255, 255, 255));
            g.drawString("Press ENTER to play again..", 20, 70);
		}
	}
	class LoseBoard extends JPanel {
	    @Override
		protected void paintComponent(Graphics g) {
		    g.setColor(new Color(20, 20, 20));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setFont(largeFeedbackFont);
			g.setColor(new Color(200, 0, 0));
			g.drawString("You Lose!", 20, 40);
            g.setFont(smallFeedbackFont);
            g.setColor(new Color(255, 255, 255));
            g.drawString("Press ENTER to try again..", 20, 70);
		}
	}
	
	
	class MyFrame extends JFrame implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {}
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			if (game.getState() == GameState.CONTINUE) {
			    if (key == KeyEvent.VK_UP) {
				    System.out.println("Up key pressed...");
				    game.pushUp();
				    game.addNewNumber();
				    game.checkState();
				    game.printArray();
				    gb.repaint();
				    updateScore();
			    }
			    else if (key == KeyEvent.VK_DOWN) {
				    System.out.println("Down key pressed...");
				    game.pushDown();
				    game.addNewNumber();
				    game.checkState();
				    game.printArray();
				    gb.repaint();
				    updateScore();
			    }
			    else if (key == KeyEvent.VK_LEFT) {
				    System.out.println("Left key pressed...");
				    game.pushLeft();
				    game.addNewNumber();
				    game.checkState();
				    game.printArray();
				    gb.repaint();
				    updateScore();
			    }
			    else if (key == KeyEvent.VK_RIGHT) {
				    System.out.println("Right key pressed...");
				    game.pushRight();
				    game.addNewNumber();
				    game.checkState();
				    game.printArray();
				    gb.repaint();
				    updateScore();
			    }
			    GameState gs = game.getState();
			    if (gs == GameState.LOSE) {
			        frame.getContentPane().remove(gb);
			        frame.getContentPane().add(new LoseBoard(), BorderLayout.CENTER);
			        frame.getContentPane().invalidate();
			        frame.getContentPane().validate();
			    } else if (gs == GameState.WIN) {
			        frame.getContentPane().remove(gb);
			        frame.getContentPane().add(new WinBoard(), BorderLayout.CENTER);
			        frame.getContentPane().invalidate();
			        frame.getContentPane().validate();
			    }
			    if ((gs == GameState.LOSE) || (gs == GameState.WIN)) {
			        int currentHighScore = getHighScore();
			        int currentScore = game.getScore();
			        if (currentScore > currentHighScore) {
			            updateHighScore(currentScore);
			            updateHighScore(currentScore);
			        }
			    }
		    } else {
		        if (key == KeyEvent.VK_ENTER) {
		            game = new Game();
		            frame.getContentPane().remove(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER));
		            frame.getContentPane().add(gb);
		            gb.repaint();
		            frame.getContentPane().invalidate();
			        frame.getContentPane().validate();
		        }
		    }
		}
		@Override
		public void keyTyped(KeyEvent e) {}
	}
	
	public void updateScore() {
	    scoreLabel.setText("<html>Score:<br>" + game.getScore() + "</html>");
	}
	public void updateHighScore(int highScore) {
	    highScoreLabel.setText("<html>High Score:<br>" + highScore + "</html>");
	}
	public void updateHighScoreFile(int highScore) {
	    String highScoreString = Integer.toString(highScore);
	    List<String> stringsForFile = new ArrayList<String>();
	    stringsForFile.add(highScoreString);
	    File highScoreFile = new File("highscore");
	    highScoreFile.delete();
	    try {
	        Files.write(Paths.get(highScoreFile.getPath()), stringsForFile, StandardCharsets.UTF_8);
	     } catch (IOException e) {
	        e.printStackTrace();
	     }
	}
	public int getHighScore() {
	    File highScoreFile = new File("highscore");
	    int highScore;
	    List<String> highScoreFileLines = new ArrayList<String>();
	    if (highScoreFile.exists()) {
	        try {
	            highScoreFileLines = Files.readAllLines(Paths.get(highScoreFile.getPath()), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
	        String highScoreString = highScoreFileLines.get(0);
	        highScore = Integer.parseInt(highScoreString);
	    } else {
	        highScore = 0;
	    }
	    return highScore;
	}

}
