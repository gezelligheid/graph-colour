import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Visualiser
{
  public static void main(String[]args)
  {
    JFrame frame = new JFrame();

    final int FRAME_WIDTH = 400;
    final int FRAME_HEIGHT = 400;

    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    frame.setTitle("GAME: Chromatic Numbers");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    JPanel panel=new JPanel();
    //Container contain=new Container();
    Screen time= new Screen(false); // false if game mode 2 , true if game mode 3
    panel.add(time);

    // Score
    JButton scoreButton = new JButton("Score: ");

    // Hint Functions
    JButton hintButton = new JButton("Hint");
    panel.add(hintButton);

    hintButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            // add code for hints
        }

    });

    // Start the game
    JButton startButton = new JButton("Start Game");
    panel.add(startButton);

    startButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            time.start();
        }

    });

    // Quitting the game
    JButton quitButton = new JButton("Quit Game");
    panel.add(quitButton);

    quitButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }

    });


    frame.add(panel);
  }
}
