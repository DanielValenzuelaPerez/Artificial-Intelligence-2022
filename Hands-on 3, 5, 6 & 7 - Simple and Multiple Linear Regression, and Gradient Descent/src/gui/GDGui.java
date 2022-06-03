package gui;

import agents.GDAgent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GDGui extends JFrame {
    private GDAgent myAgent;
    private JTextField [] textField;
    
    public GDGui(GDAgent a){
        super(a.getLocalName());
        myAgent = a;
        textField = new JTextField[myAgent.getNumberOfFactors()-1];
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 2));
        for(int i = 0; i < myAgent.getNumberOfFactors()-1; i++){
            p.add(new JLabel("Factor " + (i+1) + ": "));
            textField[i] = new JTextField(15);
            p.add(textField[i]);
        }
        getContentPane().add(p, BorderLayout.CENTER);
        JButton addButton = new JButton("Predict");
        addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                        try {
                            String [] f = new String[myAgent.getNumberOfFactors()-1];
                            double [] x = new double[myAgent.getNumberOfFactors()-1];
                                for(int i = 0; i < myAgent.getNumberOfFactors()-1; i++){
                                    f[i] = textField[i].getText().trim();
                                    x[i] = Double.parseDouble(f[i]);
                                    textField[i].setText("");
                                }
                                myAgent.predict(x);
                        }
                        catch (Exception e) {
                                JOptionPane.showMessageDialog(GDGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
                        }
                }
        } );
        p = new JPanel();
        p.add(addButton);
        getContentPane().add(p, BorderLayout.SOUTH);

        // Make the agent terminate when the user closes 
        // the GUI using the button on the upper right corner	
        addWindowListener(new	WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                        myAgent.doDelete();
                }
        } );

        setResizable(true);
    }
    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() / 2;
        int centerY = (int)screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }
}
