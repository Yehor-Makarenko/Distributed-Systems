package Lab1;
import javax.swing.*;

public class ProgramB {
  private static int semaphore = 1;

  public static void main(String[] args) {           
    JFrame f = new JFrame("Lab1");
    JButton startButton1 = new JButton("Start1");    
    JButton startButton2 = new JButton("Start2");    
    JButton stopButton1 = new JButton("Stop1");    
    JButton stopButton2 = new JButton("Stop2");    
    JSlider slider = new JSlider(0, 0, 100, 50); 
    JPanel panel = new JPanel(null);    
    Thread[] threads = new Thread[2];                

    startButton1.addActionListener(e -> {
      if (semaphore == 0) {
        if (threads[1] != null) {
          System.out.println("Another thread is working");
        }        
        return;
      }

      threads[0] = new Thread(() -> { 
        while(!Thread.interrupted()) {          
          try {
            Thread.sleep(10);
          } catch (InterruptedException e1) {
            break;
          }
          if(slider.getValue() > 10) {
            slider.setValue(slider.getValue() - 1);
          }          
        }
      });

      threads[0].setPriority(Thread.MIN_PRIORITY);      
      semaphore = 0;
      threads[0].start();
    });

    startButton2.addActionListener(e -> {
      if (semaphore == 0) {
        if (threads[0] != null) {
          System.out.println("Another thread is working");
        }        
        return;
      }

      threads[1] = new Thread(() -> { 
        while(!Thread.interrupted()) {
          try {
            Thread.sleep(10);
          } catch (InterruptedException e1) {
            break;
          }
          if(slider.getValue() < 90) {
            slider.setValue(slider.getValue() + 1);
          }          
        }
      });

      threads[1].setPriority(Thread.MAX_PRIORITY);      
      semaphore = 0;
      threads[1].start();
    });

    stopButton1.addActionListener(e -> {
      if (threads[0] == null) {
        return;
      }

      threads[0].interrupt();    
      threads[0] = null;  
      semaphore = 1;
    });

    stopButton2.addActionListener(e -> {
      if (threads[1] == null) {
        return;
      }

      threads[1].interrupt();   
      threads[1] = null;   
      semaphore = 1;
    });

    f.setSize(500, 800);           
    startButton1.setBounds(100, 150, 100, 30);    
    startButton2.setBounds(300, 150, 100, 30);    
    stopButton1.setBounds(100, 200, 100, 30);    
    stopButton2.setBounds(300, 200, 100, 30);        
    slider.setBounds(50, 70, 400, 50);

    slider.setMinorTickSpacing(5);
    slider.setMajorTickSpacing(10);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);    
  
    panel.add(slider);    
    panel.add(startButton1); 
    panel.add(startButton2);     
    panel.add(stopButton1);
    panel.add(stopButton2);
    f.setContentPane(panel);
    
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setLayout(null);
    f.setVisible(true);
  } 
}