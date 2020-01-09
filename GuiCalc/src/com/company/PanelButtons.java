package com.company;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelButtons extends JPanel {
    private JButton[] numbers = new JButton[10];
    private JButton addition = new JButton("+");
    private JButton subtraction = new JButton("-");
    private JButton multiplication = new JButton("*");
    private JButton division = new JButton("/");
    private JButton bracketOpen = new JButton("(");
    private JButton bracketClose = new JButton(")");
    private JButton point = new JButton(".");
    private JButton erase = new JButton("C");
    private JButton eraseAll = new JButton("AC");
    private JButton equally = new JButton("=");
    private JTextField output = new JTextField();
    private Font font = new Font("Times New Roman", Font.BOLD, 45);
    private Stack calculator = new Stack();


    PanelButtons() {
        setLayout(null);
        setBackground(new Color(219, 219, 219));
        output.setBounds(10,10, 420, 100);
        output.setFont(font);
        output.setHorizontalAlignment(SwingConstants.RIGHT);
        output.setMargin(new Insets(0, 15, 0, 15));
        output.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char sym = e.getKeyChar();
                if (sym == KeyEvent.VK_ENTER) {
                    equally.doClick();
                }
                if (((sym < '(') || (sym > '9')) && sym != ' ') {
                    e.consume();  // игнорим введенные буквы и пробел
                }

                bracketClose.setEnabled(true);
                bracketOpen.setEnabled(true);
                point.setEnabled(true);
                addition.setEnabled(true);
                subtraction.setEnabled(true);
                multiplication.setEnabled(true);
                division.setEnabled(true);
            }
        });
        add(output);

        ButtonsAdd();
        ButtonsListener();

        bracketClose.setEnabled(false);
        point.setEnabled(false);
        multiplication.setEnabled(false);
        division.setEnabled(false);
    }

    public void ButtonsAdd() {
        for (int i = 0; i < 10; i++) {
            numbers[i] = new JButton(String.valueOf(i));
            numbers[i].setFont(font);
            numbers[i].setBackground(Color.LIGHT_GRAY);
            numbers[i].setForeground(new Color(34, 50, 59));
            add(numbers[i]);
        }

        numbers[7].setBounds(10, 205, 80, 80);
        numbers[8].setBounds(95, 205, 80, 80);
        numbers[9].setBounds(180, 205, 80, 80);
        numbers[4].setBounds(10, 290, 80, 80);
        numbers[5].setBounds(95, 290, 80, 80);
        numbers[6].setBounds(180, 290, 80, 80);
        numbers[1].setBounds(10, 375, 80, 80);
        numbers[2].setBounds(95, 375, 80, 80);
        numbers[3].setBounds(180, 375, 80, 80);
        numbers[0].setBounds(10, 460, 165, 80);

        add(addition);
        addition.setFont(font);
        addition.setBounds(265, 205, 80, 123);
        addition.setBackground(Color.GRAY);
        addition.setForeground(new Color(33, 47, 252));

        add(subtraction);
        subtraction.setFont(font);
        subtraction.setBounds(350, 205, 80, 123);
        subtraction.setBackground(Color.GRAY);
        subtraction.setForeground(new Color(33, 47, 252));

        add(multiplication);
        multiplication.setFont(font);
        multiplication.setBounds(265, 332, 80, 123);
        multiplication.setBackground(Color.GRAY);
        multiplication.setForeground(new Color(33, 47, 252));

        add(division);
        division.setFont(font);
        division.setBounds(350, 332, 80, 123);
        division.setBackground(Color.GRAY);
        division.setForeground(new Color(33, 47, 252));

        add(bracketClose);
        bracketClose.setFont(font);
        bracketClose.setBounds(180, 120, 80, 80);
        bracketClose.setBackground(Color.GRAY);
        bracketClose.setForeground(new Color(33, 47, 252));

        add(bracketOpen);
        bracketOpen.setFont(font);
        bracketOpen.setBounds(95, 120, 80, 80);
        bracketOpen.setBackground(Color.GRAY);
        bracketOpen.setForeground(new Color(33, 47, 252));

        add(equally);
        equally.setFont(font);
        equally.setBounds(265, 460, 165, 80);
        equally.setBackground(new Color(49, 92, 212));
        equally.setForeground(Color.WHITE);

        add(point);
        point.setFont(font);
        point.setBounds(180, 460, 80, 80);
        point.setBackground(Color.GRAY);
        point.setForeground(new Color(33, 47, 252));

        add(erase);
        erase.setFont(font);
        erase.setBounds(265, 120, 165, 80);
        erase.setBackground(Color.GRAY);
        erase.setForeground(new Color(33, 47, 252));

        add(eraseAll);
        eraseAll.setFont(new Font("Times New Roman", Font.BOLD, 31));
        eraseAll.setBounds(10, 120, 80, 80);
        eraseAll.setBackground(Color.GRAY);
        eraseAll.setForeground(new Color(33, 47, 252));
    }

    public void ButtonsListener() {
        ActionListener clickedNumbers = (ActionEvent e) -> {
            JButton btn = (JButton) e.getSource();
            output.setText(output.getText() + btn.getText());
            bracketClose.setEnabled(true);
            bracketOpen.setEnabled(true);
            point.setEnabled(true);
            multiplication.setEnabled(true);
            division.setEnabled(true);
            subtraction.setEnabled(true);
            addition.setEnabled(true);
            output.requestFocusInWindow();
        };

        ActionListener clickedOperation = (ActionEvent e) -> {
            JButton btn = (JButton) e.getSource();
            output.setText(output.getText() + btn.getText());
            point.setEnabled(false);
            multiplication.setEnabled(false);
            division.setEnabled(false);
            if (btn.getText().equals(")")) {
                multiplication.setEnabled(true);
                division.setEnabled(true);
            } else {
                bracketClose.setEnabled(false);
            }
            if (btn.getText().equals(".")) {
                bracketClose.setEnabled(false);
                bracketOpen.setEnabled(false);
                subtraction.setEnabled(false);
                addition.setEnabled(false);
            }
            output.requestFocusInWindow();
        };

        ActionListener clickedErase = (ActionEvent e) -> {
            StringBuilder buffer = new StringBuilder(output.getText());
            if (!output.getText().equals("")) {
                buffer.deleteCharAt(buffer.length() - 1);
                output.setText(buffer.toString());
            }

            if (!output.getText().equals("")) {
                if (buffer.charAt(buffer.length() - 1) > 47 && buffer.charAt(buffer.length() - 1) < 58) {
                    bracketClose.setEnabled(true);
                    bracketOpen.setEnabled(true);
                    point.setEnabled(true);
                    multiplication.setEnabled(true);
                    division.setEnabled(true);
                    addition.setEnabled(true);
                    subtraction.setEnabled(true);
                } else {
                    bracketClose.setEnabled(false);
                    point.setEnabled(false);
                    multiplication.setEnabled(false);
                    division.setEnabled(false);
                }

                if (buffer.charAt(buffer.length() - 1) == '.') {
                    bracketClose.setEnabled(false);
                    point.setEnabled(false);
                    multiplication.setEnabled(false);
                    division.setEnabled(false);

                    subtraction.setEnabled(false);
                    addition.setEnabled(false);
                    bracketOpen.setEnabled(false);
                }

                if (buffer.charAt(buffer.length() - 1) == ')') {
                    bracketClose.setEnabled(true);
                    multiplication.setEnabled(true);
                    division.setEnabled(true);
                }
            } else {
                bracketOpen.setEnabled(true);
                addition.setEnabled(true);
                subtraction.setEnabled(true);
                bracketClose.setEnabled(false);
                point.setEnabled(false);
                multiplication.setEnabled(false);
                division.setEnabled(false);
            }
            output.requestFocusInWindow();
        };

        ActionListener clickedEraseAll = (ActionEvent e) -> {
            output.setText("");
            bracketOpen.setEnabled(true);
            addition.setEnabled(true);
            subtraction.setEnabled(true);
            bracketClose.setEnabled(false);
            point.setEnabled(false);
            multiplication.setEnabled(false);
            division.setEnabled(false);

            output.requestFocusInWindow();
        };

        ActionListener clickedEqually = (ActionEvent e) -> {

            calculator.setFormula(output.getText());
            if (!calculator.checkFormula()) {        // если выражение введено корректно
                calculator.formulaToStringArray();   // конвертирует его в массив
                calculator.formulaStrCorrection();   // редактирует его
                output.setText(String.valueOf(calculator.result()));   // считает его и выводит ответ


                bracketClose.setEnabled(true);
                bracketOpen.setEnabled(true);
                point.setEnabled(true);
                multiplication.setEnabled(true);
                division.setEnabled(true);
                subtraction.setEnabled(true);
                addition.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Вы ввели некорректное выражение!");// иначе ошибка ввода
            }

            output.requestFocusInWindow();
        };

        //Добавление Нажатия
        for (int i = 0; i < 10; i++) {
            numbers[i].addActionListener(clickedNumbers);
        }

        bracketOpen.addActionListener(clickedOperation);
        bracketClose.addActionListener(clickedOperation);
        addition.addActionListener(clickedOperation);
        subtraction.addActionListener(clickedOperation);
        multiplication.addActionListener(clickedOperation);
        division.addActionListener(clickedOperation);
        point.addActionListener(clickedOperation);

        erase.addActionListener(clickedErase);
        eraseAll.addActionListener(clickedEraseAll);

        equally.addActionListener(clickedEqually);
    }
}
