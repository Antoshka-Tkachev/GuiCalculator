package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	    JFrame window = new JFrame("Calculator");
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(50,50, 455,585);
		window.setResizable(false);
	    window.setContentPane(new PanelButtons());
	    window.setVisible(true);
    }
}
