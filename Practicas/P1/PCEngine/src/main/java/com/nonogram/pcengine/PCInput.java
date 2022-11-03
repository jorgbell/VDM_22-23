package com.nonogram.pcengine;

import com.nonogram.engine.AbstractInput;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PCInput extends AbstractInput implements MouseListener {

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        newEvent(mouseEvent.getX(),mouseEvent.getY(),
                mouseEvent.getID(), TouchEvent.InputType.PULSAR);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        newEvent(mouseEvent.getX(),mouseEvent.getY(),
                mouseEvent.getID(), TouchEvent.InputType.SOLTAR);    }



    //------------------------//

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        ;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        ;
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        ;
    }

}
