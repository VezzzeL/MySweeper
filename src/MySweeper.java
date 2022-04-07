import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import bin.Box;
import bin.Coordinate;
import bin.Game;
import bin.Ranges;

public class MySweeper extends JFrame {
    private Game game;
    private JPanel panel, header;
    private JLabel label;

    private final int cols =12, rows =12;
    private int bombs = 30;
    private final int IMAGE_SIZE=50;
    public static void main(String[] args) {
        new MySweeper().setVisible(true);
    }

    private MySweeper(){

        game=new Game(cols, rows, bombs);
        game.start();
        Ranges.setSize(new Coordinate(cols, rows));
        setImages();
        initHeader();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initPanel() {
        panel = new JPanel(){

            @Override
            protected void paintComponent(Graphics graphics){
                super.paintComponent(graphics);
                for (Coordinate coordinate : Ranges.getCoordinates()){

                    graphics.drawImage((Image) game.getBox(coordinate).image,
                            coordinate.x*IMAGE_SIZE,coordinate.y*IMAGE_SIZE,this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x=e.getX()/IMAGE_SIZE;
                int y=e.getY()/IMAGE_SIZE;
                Coordinate coordinate=new Coordinate(x,y);
                if(e.getButton()==MouseEvent.BUTTON1){
                    game.pressLeftButton(coordinate);
                    label.setText(getMessage());
                }
                if(e.getButton()==MouseEvent.BUTTON2){
                    game.start();
                    label.setText(getMessage());
                }
                if(e.getButton()==MouseEvent.BUTTON3){
                    game.pressRightButton(coordinate);
                    label.setText(getMessage());
                }

                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(Ranges.getSize().x*IMAGE_SIZE,
                                                Ranges.getSize().y*IMAGE_SIZE));
        add(panel);
    }

    private void initFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MySweeper");
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("deminerMainIcon"));
        pack();
        setLocationRelativeTo(null);
    }

    private void initLabel(){
        label=new JLabel("Start");
        add(label,BorderLayout.SOUTH);
    }

    private void setImages(){
        for(Box box : Box.values()){
            box.image=getImage(box.name().toLowerCase()).getScaledInstance(50,50, Image.SCALE_DEFAULT);
        }
    }

    private Image getImage(String name){
        String fileName=name+".png";
        ImageIcon icon=new ImageIcon(getClass().getResource(fileName));
        return icon.getImage();
    }

    private String getMessage(){
        switch (game.getState()){
            case PLAYING : return "Keep playing";
            case WIN : return "Congrats, You won!";
            case LOST: return "Unfortunately, you lost(";
            default : return "Something went wrong";
        }
    }

    private void initHeader(){
        header=new JPanel();
        header.setPreferredSize(new Dimension(50,50));
        initButton(header);
        initDiffBox();
        add(header, BorderLayout.NORTH);
    }

    private void initButton(JPanel header){

        ImageIcon icon=new ImageIcon("res/sun.png");
        Image image=icon.getImage();
        Image tempImage=image.getScaledInstance(100,50,Image.SCALE_DEFAULT);
        icon=new ImageIcon(tempImage);

        JButton restart=new JButton(icon);
        restart.setPreferredSize(new Dimension(50,50));
        restart.getPressedIcon();
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.start();
                label.setText(getMessage());
                panel.repaint();
            }
        });
        header.add(restart);
    }

    private void initDiffBox(){

        JTextField totalBombsField=new JTextField();
        totalBombsField.setText("Total bombs: "+bombs);

        String[]difficultyArr={"Default","Easy","Medium","Hard"};
        JComboBox difficultyBox=new JComboBox(difficultyArr);
        difficultyBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (difficultyBox.getSelectedItem().toString()){
                    case "Default" -> {
                        game.countBombs=30;
                        totalBombsField.setText("Total bombs: "+game.countBombs);
                    }
                    case "Easy" -> {
                        game.countBombs=15;
                        totalBombsField.setText("Total bombs: "+game.countBombs);
                    }
                    case "Medium" -> {
                        game.countBombs=45;
                        totalBombsField.setText("Total bombs: "+game.countBombs);
                    }
                    case "Hard" -> {
                        game.countBombs=60;
                        totalBombsField.setText("Total bombs: "+game.countBombs);
                    }
                }
            }
        });
        header.add(totalBombsField).setLocation(10,10);
        game.start();
        header.add(difficultyBox);
    }
}

