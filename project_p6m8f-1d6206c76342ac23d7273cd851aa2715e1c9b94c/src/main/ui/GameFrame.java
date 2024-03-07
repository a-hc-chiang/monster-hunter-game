package ui;

import javax.swing.*;
import java.awt.*;

/**
represents all gui elements of the game
 */

public class GameFrame extends JFrame {
    public static final String ACTION_PERFORMED = "Action performed: \n";

    private JTextArea textSide;
    private JTextField inputTurns;
    private JTextArea turnPrompt;
    private JTextArea score;
    private JTextArea remainingTurns;
    private JTextArea actionPerformed;
    private JButton saveButton;
    private JButton loadButton;
    private JButton inputEntry;
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton attackButton;
    private JButton quitButton;

    private JPanel centrePanel;
    private JLabel gif;

    public GameFrame() {
        super(">:]");
        this.setSize(825, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        setUpUpCenter();
        setUpWest();
        setUpNorth();
        setUpSouth();
        setUpEast();
        this.setVisible(true);
    }

    //EFFECTS: adds all gui elements in center format of the jframe
    public void setUpUpCenter() {
        centrePanel = new JPanel();
        textSide = new JTextArea();
        textSide.setFont(new Font("Courier New", Font.BOLD, 14));
        textSide.setBackground(Color.darkGray);
        textSide.setForeground(Color.white);
        textSide.setSize(375, 375);
        textSide.setEditable(false);
        centrePanel.add(textSide);
        this.add(centrePanel, BorderLayout.CENTER);
    }

    //EFFECTS: adds all gui elements in north side of the jframe
    public void setUpNorth() {
        JPanel panelContainer = new JPanel(new GridLayout(1, 2));

        score = new JTextArea("Current score: 0");
        score.setEditable(false);
        score.setFont(new Font("Times", Font.BOLD, 20));
        score.setBackground(Color.darkGray);
        score.setForeground(Color.white);

        remainingTurns = new JTextArea("Remaining turns: 0");
        remainingTurns.setFont(new Font("Times", Font.BOLD, 20));
        remainingTurns.setEditable(false);
        remainingTurns.setBackground(Color.darkGray);
        remainingTurns.setForeground(Color.white);

        panelContainer.add(remainingTurns);
        panelContainer.add(score);

        this.add(panelContainer, BorderLayout.NORTH);
    }

    //EFFECTS: adds all gui elements in south side of the jframe
    public void setUpSouth() {
        JPanel panelContainer = new JPanel(new GridLayout(1, 3));

        String prompt = "Input number of turns";
        turnPrompt = new JTextArea(prompt);
        turnPrompt.setEditable(false);

        inputEntry = new JButton("Enter Input");

        inputTurns = new JTextField();
        inputTurns.setEditable(false);
        inputTurns.setEditable(true);
        inputTurns.setPreferredSize(new Dimension(20, 5));

        panelContainer.add(turnPrompt);
        panelContainer.add(inputTurns);
        panelContainer.add(inputEntry);

        this.add(panelContainer, BorderLayout.SOUTH);
    }

    //EFFECTS: sets up all gui elements in west side of the jframe
    public void setUpWest() {

        saveButton = new JButton("Save game");
        loadButton = new JButton("Load game");
        quitButton = new JButton("Quit game");

        ImageIcon atk = new ImageIcon(GameFrame.this.getClass().getResource("atk.gif"));
        gif = new JLabel();
        gif.setIcon(atk);

        actionPerformed = new JTextArea(ACTION_PERFORMED);
        actionPerformed.setFont(new Font("Courier New", Font.BOLD, 14));
        actionPerformed.setEditable(false);
        setUpWest2();
    }

    //EFFECTS: adds all gui elements in west side of the jframe
    public void setUpWest2() {
        JPanel panelContainer = new JPanel(new GridLayout(5, 1));

        gif.setVisible(false);

        panelContainer.add(saveButton);
        panelContainer.add(loadButton);
        panelContainer.add(quitButton);
        panelContainer.add(gif);
        panelContainer.add(actionPerformed);

        this.add(panelContainer, BorderLayout.WEST);
    }

    //EFFECTS: adds all gui elements in east side of the jframe
    public void setUpEast() {
        JPanel allButtons = new JPanel(new GridLayout(4, 1));
        JPanel leftAndRight = new JPanel(new GridLayout(1, 2));

        upButton = new JButton("Up");
        downButton = new JButton("Down");
        leftButton = new JButton("Left");
        rightButton = new JButton("Right");
        attackButton = new JButton("Attack!");

        leftAndRight.add(leftButton);
        leftAndRight.add(rightButton);

        allButtons.add(upButton);
        allButtons.add(leftAndRight);
        allButtons.add(downButton);
        allButtons.add(attackButton);

        this.add(allButtons, BorderLayout.EAST);
    }

    public JTextArea getTextSide() {
        return textSide;
    }

    public JTextField getInputTurns() {
        return inputTurns;
    }

    public JTextArea getScore() {
        return score;
    }

    public JTextArea getRemainingTurns() {
        return remainingTurns;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getInputEntry() {
        return inputEntry;
    }

    public JLabel getGif() {
        return gif;
    }

    public JButton getUpButton() {
        return upButton;
    }

    public JButton getDownButton() {
        return downButton;
    }

    public JButton getLeftButton() {
        return leftButton;
    }

    public JButton getRightButton() {
        return rightButton;
    }

    public JButton getAttackButton() {
        return attackButton;
    }

    public JButton getQuitButton() {
        return quitButton;
    }

    public JTextArea getActionPerformed() {
        return actionPerformed;
    }

}
