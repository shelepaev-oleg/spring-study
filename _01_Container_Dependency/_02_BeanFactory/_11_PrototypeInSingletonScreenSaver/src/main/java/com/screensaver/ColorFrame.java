package com.screensaver;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Класс описывает МО, которое постоянно меняет местоположение
 */
@Component
public abstract class ColorFrame extends JFrame {

    public ColorFrame(){
        setSize(200, 200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Метод, меняющий местоположение и цвет
     */
    public void showOnRandomPlace() {
        Random random = new Random();
        setLocation(random.nextInt(1200), random.nextInt(700));
        getContentPane().setBackground(getColor());
        repaint();
    }

    /**
     * Цвет необходимо менять, поэтому здесь его сделаем abstract, а в конфиге будем создавать
     */
    protected abstract Color getColor();
}
