package gui;

import agents.MLR1Agent;
import jade.core.AID;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
  @author Giovanni Caire - TILAB
 */
public class MLRGui extends JFrame {	
	private MLR1Agent myAgent;
	
	private JTextField factor1, factor2;
	
	public MLRGui(MLR1Agent a) {
            super(a.getLocalName());

            myAgent = a;

            JPanel p = new JPanel();
            p.setLayout(new GridLayout(2, 2));
            p.add(new JLabel("Factor1:"));
            factor1 = new JTextField(15);
            p.add(factor1);
            p.add(new JLabel("Factor2:"));
            factor2 = new JTextField(15);
            p.add(factor2);
            getContentPane().add(p, BorderLayout.CENTER);

            JButton addButton = new JButton("Predict");
            addButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {
                            try {
                                    String f1 = factor1.getText().trim();
                                    String f2 = factor2.getText().trim();
                                    myAgent.predict(Double.parseDouble(f1), Double.parseDouble(f2));
                                    factor1.setText("");
                                    factor2.setText("");
                            }
                            catch (Exception e) {
                                    JOptionPane.showMessageDialog(MLRGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
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

