package Lab1;
import javax.swing.*;

public class ProgramA {
  public static void main(String[] args) {    
    JFrame f = new JFrame("Lab1");
    JButton startButton = new JButton("Start");    
    JSlider slider = new JSlider(0, 0, 100, 50); 
    JPanel panel = new JPanel(null);
    JSpinner spinnerMinus = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
    JSpinner spinnerPlus = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
    Thread tMinus = new Thread(() -> { 
      while(true) {
        synchronized(slider) {     
          try {
            Thread.sleep(10);
          } catch (InterruptedException e1) {
            break;
          }     
          if(slider.getValue() > 10) {
            slider.setValue(slider.getValue() - 1);
          }
        }        
      }
    });
    Thread tPlus = new Thread(() -> { 
      while(true) {               
        synchronized(slider) {  
          try {
            Thread.sleep(10);
          } catch (InterruptedException e1) {
            break;
          }         
          if(slider.getValue() < 90) {
            slider.setValue(slider.getValue() + 1);
          }
        }        
      }
    });   

    startButton.addActionListener(e -> {
      if (tMinus.isAlive()) {
        return;
      }
      
      tMinus.setDaemon(true);
      tPlus.setDaemon(true);
      tMinus.start();
      tPlus.start();
    });  

    spinnerMinus.addChangeListener(e -> {
      tMinus.setPriority((int)spinnerMinus.getValue());      
    });

    spinnerPlus.addChangeListener(e -> {
      tPlus.setPriority((int)spinnerPlus.getValue());
    });

    f.setSize(500, 800);           
    spinnerMinus.setBounds(100, 150, 50, 30);
    spinnerPlus.setBounds(350, 150, 50, 30);
    startButton.setBounds(200, 150, 100, 30);    
    slider.setBounds(50, 70, 400, 50);

    slider.setMinorTickSpacing(5);
    slider.setMajorTickSpacing(10);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true); 
  
    panel.add(slider);
    panel.add(spinnerMinus);
    panel.add(startButton); 
    panel.add(spinnerPlus);   
    f.setContentPane(panel);
    
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setLayout(null);
    f.setVisible(true);
  } 
}
