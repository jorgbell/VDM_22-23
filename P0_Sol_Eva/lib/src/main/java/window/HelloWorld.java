package window;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class HelloWorld implements MouseListener {

    JFrame frame;
    TextField txt;

    public HelloWorld() {}

    public void createWindow()
    {
        frame = new JFrame("Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 600));

        //region Button creation
        Button b = new Button("Send");
        b.setPreferredSize(new Dimension(30, 20));
        b.addMouseListener(this);
        {}
        //endregion

        //region Text creation
        txt = new TextField("¡Hola Mundo!");
        txt.setPreferredSize(new Dimension(100, 20));
        //endregion

        //region Panel creation
        Panel buttonPanel = new Panel();
        buttonPanel.add(b, BorderLayout.WEST);
        buttonPanel.add(txt, BorderLayout.EAST);
        //endregion

        frame.add(buttonPanel, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        JOptionPane.showMessageDialog(frame, txt.getText(),"Mensaje", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

}
