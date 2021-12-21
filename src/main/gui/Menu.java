package main.gui;

import java.awt.event.ActionEvent;

import main.Launcher;
import main.sound.SoundEffect;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

import static main.entities.bomb.Bomb.explosion;
import static main.entities.bomb.BombSet.bombSet;
import static main.gfx.CreatureDieAnimation.playerDie;

public class Menu {

    JFrame window;
    JPanel titleNamePanel, startButtonPanel, optionButtonPanel, mainTextPanel, mainTextPanel1,  choiceButtonPanel1,
            choiceButtonPanel2, exitButtonPanel, backButtonPanel, volUpButtonPanel, volDownButtonPanel, muteButtonPanel, slider1;
    JLabel titleNameLabel;
    Font titleFont = new Font ("Comic Kings", Font.BOLD, 50);
    Font normalFont = new Font("SVN-Have Heart 2", Font.BOLD, 25);
    JButton startButton, optionButton, exitButton, choice1, choice2, back, volumeUpB, volumeDownB, muteB;
    JTextArea mainTextArea, mainTextArea1;
    JSlider slider;

    LevelScreenHandler lvHandler = new LevelScreenHandler();
    OptionScreenHandler opHandler = new OptionScreenHandler();
    ExitScreenHandler exHandler = new ExitScreenHandler();
    BackScreenHandler bHandler = new BackScreenHandler();


    private static SoundEffect menu = new SoundEffect(SoundEffect.MENU);

    public static void main(String[] args) {

        menu.loop();
        new Menu();
    }

    public Menu() {

        window = new JFrame();
        window.setSize(800, 450);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.getContentPane().setBackground(Color.black);
        window.setContentPane(new JLabel(new ImageIcon(".\\src\\resource\\image\\background.png")));
        window.setLayout(null);
        window.setResizable(false);


//        titleNamePanel = new JPanel();
//        titleNamePanel.setBounds(180, 100, 500, 50);
//        titleNamePanel.setBackground(Color.black);
//        titleNameLabel = new JLabel("BOMBERMAN");
//        titleNameLabel.setForeground(Color.white);
//        titleNameLabel.setFont(titleFont);

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(340, 220, 120, 50);
        startButtonPanel.setBackground(new Color(0, 0, 0, 5));

        startButton = new JButton("START");
//        startButton = new JButton(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Máy tính\\1.png"));
//        startButton.setBackground(Color.yellow);
        startButton.setForeground(Color.red);
        startButton.setFont(normalFont);
        startButton.setFocusPainted(false);
        startButton.addActionListener(lvHandler);

        optionButtonPanel = new JPanel();
        optionButtonPanel.setBounds(340, 270, 120, 50);
        optionButtonPanel.setBackground(new Color(0, 0, 0, 5));

        optionButton = new JButton("OPTION");
//        optionButton.setBackground(Color.yellow);
        optionButton.setForeground(Color.red);
        optionButton.setFont(normalFont);
        optionButton.setFocusPainted(false);
        optionButton.addActionListener(opHandler);

        exitButtonPanel = new JPanel();
        exitButtonPanel.setBounds(340, 320, 120, 50);
        exitButtonPanel.setBackground(new Color(0, 0, 0, 5));

        exitButton = new JButton("EXIT");
//        optionButton.setBackground(Color.yellow);
        exitButton.setForeground(Color.red);
        exitButton.setFont(normalFont);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(exHandler);

        //  titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(startButton);
        optionButtonPanel.add(optionButton);
        exitButtonPanel.add(exitButton);

//        window.add(titleNamePanel);
        window.add(startButtonPanel);
        window.add(optionButtonPanel);
        window.add(exitButtonPanel);
        window.setVisible(true);
    }

    public void levelScreen() {

//        titleNamePanel.setVisible(false);
        startButtonPanel.setVisible(false);
        optionButtonPanel.setVisible(false);
        exitButtonPanel.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(200, 140, 400, 50);
        mainTextPanel.setBackground(new Color(0, 0, 0, 5));
        window.add(mainTextPanel);

//        JTextAreaPlus textAreaPlus = new JTextAreaPlus("aaaaaaa");
//        ImageIcon icon = new ImageIcon("C:\\Users\\DELL\\OneDrive\\Máy tính\\01.png");
//        textAreaPlus.setImage(icon, 250, 100, 500, 500);
//
//        window.add(textAreaPlus);
//        window.setVisible(true);

//        titleNamePanel = new JPanel();
//        titleNamePanel.setBounds(250, 100, 300, 200);
//        titleNamePanel.setBackground(new Color(255, 0, 0, 5));
//        titleNameLabel = new JLabel(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Máy tính\\01.png"));
////        titleNameLabel.setForeground(Color.red);
////        titleNameLabel.setFont(titleFont);
//
////        titleNamePanel.add(titleNameLabel);
//        window.add(titleNamePanel);

        mainTextArea = new JTextArea("SELECT LEVEL");
        mainTextArea.setBounds(190, 150, 500, 50);
        mainTextArea.setBackground(new Color(0, 0, 0, 5));
        mainTextArea.setForeground(Color.red);
        mainTextArea.setFont(titleFont);
        mainTextArea.setLineWrap(false);
        mainTextArea.setEditable(false);
        mainTextArea.setWrapStyleWord(true);
        mainTextPanel.add(mainTextArea);

        choiceButtonPanel1 = new JPanel();
        choiceButtonPanel1.setBounds(340, 220, 120, 100);
        choiceButtonPanel1.setBackground(new Color(0, 0, 0, 5));
//        choiceButtonPanel.setLayout(new GridLayout(3,1));
        window.add(choiceButtonPanel1);

        choice1 = new JButton("Level 1");
        choice1.setForeground(Color.red);
        choice1.setFont(normalFont);
        startButton.setFocusPainted(false);
        choiceButtonPanel1.add(choice1);
        choice1.addActionListener(new Level1());

        choiceButtonPanel2 = new JPanel();
        choiceButtonPanel2.setBounds(340, 270, 120, 100);
        choiceButtonPanel2.setBackground(new Color(0, 0, 0, 5));
//        choiceButtonPanel.setLayout(new GridLayout(3,1));
        window.add(choiceButtonPanel2);

        choice2 = new JButton("Level 2");
        choice2.setForeground(Color.red);
        choice2.setFont(normalFont);
        startButton.setFocusPainted(false);
        choiceButtonPanel2.add(choice2);
        choice2.addActionListener(new Level2());

        backButtonPanel = new JPanel();
        backButtonPanel.setBounds(340, 320, 120, 50);
        backButtonPanel.setBackground(new Color(0, 0, 0, 5));

        back = new JButton("Back");
//        optionButton.setBackground(Color.yellow);
        back.setForeground(Color.red);
        back.setFont(normalFont);
        back.setFocusPainted(false);
        back.addActionListener(bHandler);

        backButtonPanel.add(back);
        window.add(backButtonPanel);
    }

    public void optionScreen() {

//        titleNamePanel.setVisible(false);
        startButtonPanel.setVisible(false);
        optionButtonPanel.setVisible(false);
        exitButtonPanel.setVisible(false);

        mainTextPanel1 = new JPanel();
        mainTextPanel1.setBounds(265, 140, 280, 50);
        mainTextPanel1.setBackground(new Color(0, 0, 0, 0));
        window.add(mainTextPanel1);

        mainTextArea1 = new JTextArea("VOLUME");
        mainTextArea1.setBounds(190, 290, 300, 50);
        mainTextArea1.setBackground(new Color(0, 0, 0, 5));
        mainTextArea1.setForeground(Color.red);
        mainTextArea1.setFont(titleFont);
        mainTextArea1.setLineWrap(false);
        mainTextArea1.setEditable(false);
        mainTextArea1.setWrapStyleWord(true);
        mainTextPanel1.add(mainTextArea1);


        //   public Main1 (){
//            JFrame window = new JFrame();
//            window.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
//            window.setLayout (new GridLayout (3, 1) );

        volUpButtonPanel = new JPanel();
        volUpButtonPanel.setBounds(160, 220, 170, 50);
        volUpButtonPanel.setBackground(new Color(0, 0, 0, 5));

        volumeUpB = new JButton ("Volume Up");
        volumeUpB.setForeground(Color.red);
        volumeUpB.setFont(normalFont);
        volumeUpB.setFocusPainted(false);
        volUpButtonPanel.add(volumeUpB);
        window.add(volUpButtonPanel);
        volumeUpB.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerDie.volumeUp();
                menu.volumeUp();
                explosion.volumeUp();
                bombSet.volumeUp();
            }
        });
//            window.add(volumeUpB);

        volDownButtonPanel = new JPanel();
        volDownButtonPanel.setBounds(330, 220, 170, 50);
        volDownButtonPanel.setBackground(new Color(0, 0, 0, 5));

        volumeDownB = new JButton ("Volume Down");
        volumeDownB.setForeground(Color.red);
        volumeDownB.setFont(normalFont);
        volumeDownB.setFocusPainted(true);
        volDownButtonPanel.add(volumeDownB);
        window.add(volDownButtonPanel);
        volumeDownB.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerDie.volumeDown();
                menu.volumeDown();
                explosion.volumeDown();
                bombSet.volumeDown();
            }
        });
//            window.add(volumeDownB);

        muteButtonPanel = new JPanel();
        muteButtonPanel.setBounds(478, 220, 170, 50);
        muteButtonPanel.setBackground(new Color(0, 0, 0, 5));

        muteB = new JButton ("Mute");
        muteB.setForeground(Color.red);
        muteB.setFont(normalFont);
        muteB.setFocusPainted(false);
        muteButtonPanel.add(muteB);
        window.add(muteButtonPanel);
        muteB.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.volumeMute();
                playerDie.volumeMute();
                explosion.volumeMute();
                bombSet.volumeMute();
            }
        });
//        window.add(muteB);

        slider = new JSlider(-80, 6);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                menu.curVolume = slider.getValue();
                menu.fc.setValue(menu.curVolume);
            }
        });
        slider1 = new JPanel();
        slider1.setBounds(200, 290, 400, 50);
        slider1.setBackground(new Color(0, 0, 0, 5));
        slider1.add(slider);
        window.add(slider1);

        backButtonPanel = new JPanel();
        backButtonPanel.setBounds(340, 320, 120, 50);
        backButtonPanel.setBackground(new Color(0, 0, 0, 5));

        back = new JButton("Back");
//        optionButton.setBackground(Color.yellow);
        back.setForeground(Color.red);
        back.setFont(normalFont);
        back.setFocusPainted(false);
        back.addActionListener(bHandler);

        backButtonPanel.add(back);
        window.add(backButtonPanel);

        window.setVisible(true);

//        mainTextPanel = new JPanel();
//        mainTextPanel.setBounds(260, 200, 250, 50);
//        mainTextPanel.setBackground(Color.white);
//        window.add(mainTextPanel);


//        titleNamePanel = new JPanel();
//        titleNamePanel.setBounds(250, 100, 300, 50);
//        titleNamePanel.setBackground(Color.yellow);
//        titleNameLabel = new JLabel("SELECT LEVEL");
//        titleNameLabel.setForeground(Color.red);
//        titleNameLabel.setFont(titleFont);
//
//        titleNamePanel.add(titleNameLabel);
//        window.add(titleNamePanel);

//        mainTextArea = new JTextArea("SELECT LEVEL");
//        mainTextArea.setBounds(250, 300, 300, 50);
//        mainTextArea.setBackground(new Color(255, 0, 0, 5));
//        mainTextArea.setForeground(Color.red);
//        mainTextArea.setFont(normalFont);
//        mainTextArea.setLineWrap(false);
//        mainTextPanel.add(mainTextArea);

    }

    public class LevelScreenHandler implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            levelScreen();
        }
    }

    public class Level1 implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            Launcher.launch(1);

        }
    }

    public class Level2 implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            Launcher.launch(2);
        }
    }

    public class OptionScreenHandler implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            optionScreen();
        }
    }

    public class ExitScreenHandler implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }

    public class BackScreenHandler implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            window.dispose();
            new Menu();
        }
    }
}

