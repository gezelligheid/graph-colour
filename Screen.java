import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen extends JPanel
{
  private boolean flag;
  JButton button= new JButton();
  //JButton stopGame= new JButton();
  //JButton buttonStop= new JButton();
  ActionListener listener1 = new MyListener();
  Timer timer=new Timer(1000,listener1); // 1000 milliseconds( 1 second) delay
  private int time;
  public class MyListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      button.setText(""+time);

      /*if(time==30)
      buttonStop.setText("Game Starts Now !!");
      else
      buttonStop.setText("");*/

      if(flag==true)// count up
      {
        time++;
      }

      else
      {
        if(time==0)
        {
          timer.stop();
          button.setText("Time's up!");
        }
        time--;
      }

    }
  }

  public void start()
  {
    timer.start(); // invokes the timer class
    button.addActionListener(listener1);
    add(button);
    //add(buttonStop);
  }

  public Screen(boolean flag)
  {
    this.flag=flag;

    if(flag==true)// count up
    {
      time=0;
    }
    else// count down
    {
      time=15; // 5 minutes to solve the graph
    }
  }

}
