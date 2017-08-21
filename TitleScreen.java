import sun.audio.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreen extends JPanel implements ActionListener {
 private String name = "Chess Master Express XD";
 private Container c;
 private JFrame menuScreen;
 private JButton startButton;
 private JButton instructButton;
 private JButton instructButton2;
 public static final int WIDTH = 730;
 public static final int HEIGHT = 1070;
 String[] args = new String[0];
 public boolean start = false;
 public boolean instruct = false;
 private static int counter = 0;
 private static AudioStream as;
 private boolean instructions = false;
 private boolean instructions2 = false;
 public TitleScreen() {

  menuScreen = new JFrame("Chess Master Express XD");
  startButton = new JButton("Start Game!");
  instructButton = new JButton("Instructions");
  instructButton2 = new JButton("Instructions More");

  instructButton.setPreferredSize(new Dimension(100, 100));
  instructButton.setBounds(600, 550, 325, 50);
  instructButton.setActionCommand("Instruct");
  instructButton.addActionListener(this);
  
  instructButton2.setPreferredSize(new Dimension(100, 100));
  instructButton2.setBounds(720, 500, 325, 50);
  instructButton2.setActionCommand("Instruct more");
  instructButton2.addActionListener(this);
  
  

  startButton.setPreferredSize(new Dimension(100, 100));
  startButton.setBounds(600, 450, 325, 50); //600, 450, 325, 50
  startButton.setActionCommand("Game on");
  startButton.addActionListener(this);

  menuScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  c = menuScreen.getContentPane();
  this.setLayout(null);

  this.add(startButton);
  this.add(instructButton);

  c.add(this);
  this.setPreferredSize(new Dimension(HEIGHT, WIDTH));
  menuScreen.pack();
  menuScreen.setVisible(true);
  menuScreen.setLocationRelativeTo(null);
 }

 @Override
 public void paint(Graphics g) {

  {
   BufferedImage screen = null;
   try {
    screen = ImageIO.read(new File("ChessMaster.png"));
   } catch (IOException e) {
   }
    BufferedImage instr = null;
   try {
    instr = ImageIO.read(new File("Instructions.png"));
   } catch (IOException e) {
   }
   BufferedImage instr2 = null;
   try {
    instr2 = ImageIO.read(new File("FURTHER INSTRUCTIONS.png"));
   } catch (IOException e) {
   }
   g.drawImage(screen,0,0,null);
   String name = "START GAME";
   String inst = "INSTRUCTIONS";
   g.setFont(new Font(name, 5, 50));
   g.setColor(Color.white);
   g.drawString(name, 600, 500);
   g.drawString(inst, 600, 600);
    if(instructions)
    {
      g.drawImage(instr,0,0,null);
       startButton.setBounds(720, 350, 325, 50);
       this.add(instructButton2); 
       
     g.drawString(name, 720, 400);
    }
    if(instructions2)
    {
    	
    g.drawImage(instr2, 0, 0, null);
    startButton.setBounds(770, 500, 325, 50);
    }
  }
  
 }

 public static void playMusic() {
  counter++;
  AudioPlayer MGP = AudioPlayer.player;
  AudioData MD;

  ContinuousAudioDataStream loop = null;

  try {
   InputStream test = new FileInputStream("a.wav");
   as = new AudioStream(test);
   AudioPlayer.player.start(as);

  } catch (FileNotFoundException e) {
   System.out.print(e.toString());
   System.out.println(e.getMessage());
  } catch (IOException error) {
   System.out.print(error.toString());
  }
  MGP.start(loop);
 }

 public static void playSoundEffect() {
  AudioPlayer MGP = AudioPlayer.player;
  AudioStream BGM;
  AudioData MD;

  ContinuousAudioDataStream loop = null;

  try {
   InputStream test = new FileInputStream("b2uzzer.wav");
   BGM = new AudioStream(test);
   AudioPlayer.player.start(BGM);

  } catch (FileNotFoundException e) {
   System.out.print(e.toString());
  } catch (IOException error) {
   System.out.print(error.toString());
  }
  MGP.start(loop);
 }

 public void run() throws InterruptedException { // always running method

  while (!start) {
   repaint();
   Thread.sleep(10);
   if (counter == 0)
    playMusic();
  }

  if (start) {
   // menuScreen.setVisible(false);
   menuScreen.dispose();
   try {
    // don't try and do things with a null object!
    if (as != null) {
     AudioPlayer.player.stop(as);
    }
    System.out.println("stopping");
   } finally {
   }
  }
 }

 public boolean getStart() {
  return start;
 }

 @Override
 public void actionPerformed(ActionEvent a) {
  if ("Game on".equals(a.getActionCommand())){
   start = true;
  }
  else if ("Instruct".equals(a.getActionCommand())){
   instructions = true;
   //playSoundEffect();
  }
  else
	  if("Instruct more".equals(a.getActionCommand()))
	  {
		  instructions2 = true;
	  }
	  }
 }
