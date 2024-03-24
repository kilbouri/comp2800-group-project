package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class characterCustomizerScreen extends JFrame {

    ImageIcon background;

    public characterCustomizerScreen() throws IOException {

        setTitle("Character Customization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        //background image
        background = new ImageIcon("fancy pants.png");

        //space of where our character will be
        JLabel characterDisplay = new JLabel("Character display");


        //change button initialized
        JButton changeButton = new JButton("Change Sprite");
        changeButton.setFont(new Font("Futura", Font.ITALIC, 15));
        changeButton.setBackground(new Color(206, 237, 233));

        //save button
        JButton saveButton = new JButton("Save Sprite");
        saveButton.setFont(new Font("Futura", Font.ITALIC, 15));
        saveButton.setForeground(Color.BLACK); // Set text color
        saveButton.setBackground(new Color(206, 237, 233));

        //back button
        JButton backButton = new JButton("Back to start menu");
        backButton.setFont(new Font("Futura", Font.ITALIC, 15));
        backButton.setBackground(new Color(206, 237, 233));

        //setting size of buttons
        Dimension buttonSize = new Dimension(200, 50); // Width: 200, Height: 50
        changeButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        // Adding action listeners to buttons
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to change character sprite
                // You can implement this functionality as needed
                JOptionPane.showMessageDialog(characterCustomizerScreen.this, "Sprite Changed");
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to save character sprite
                // You can implement this functionality as needed
                JOptionPane.showMessageDialog(characterCustomizerScreen.this, "Sprite Saved");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to go back to start menu
                //JOptionPane.showMessageDialog(characterCustomizerScreen.this, "Back to Start Menu");
                try {
                    new startMenu();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        //creating panel for the character sprite to be placed in
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(characterDisplay);
        leftPanel.setBackground(Color.pink);

        //creating panel for the buttons
        // Set layout to center the buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        rightPanel.add(changeButton,gbc);
        rightPanel.add(saveButton,gbc);
        rightPanel.add(backButton,gbc);
        rightPanel.setBackground(Color.pink);
        //add right panel to left
        leftPanel.add(rightPanel, BorderLayout.EAST);
        //add left panel to frame
        add(leftPanel);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Draw background image
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
    }


}