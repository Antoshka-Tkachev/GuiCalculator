package com.company;

import java.lang.String;
import java.lang.Double;
import java.math.BigDecimal;
import java.util.ArrayList;

class Stack implements Priority_operations {
    private StringBuilder formula; // пременная для начального хранения выражения
    private int size;              // размер formula
    private ArrayList <String> formulaStr = new ArrayList<>(); // выражение разбитая цифры и операции
    private ArrayList <BigDecimal> digits = new ArrayList<>(); // стек для чисел
    private ArrayList <String> operations = new ArrayList<>(); // стек для операций

    Stack() { } // конструктор

    void setFormula(String formula){
        this.formula = new StringBuilder(formula);
    }

    StringBuilder getFormula(){
        return formula;
    }

    void formulaStrCorrection() { // редактирует формулу(добавляет нужные '*','(',')','0')

        if (formulaStr.get(0).equals("-")) {      // если первым '-'
            formulaStr.add(0, "0"); // добаляем перед ним '0'
        }

        for (int i = 0; i < formulaStr.size() - 1; i++){ // если после скобки отрицательное число
            if (formulaStr.get(i).equals("(") && (formulaStr.get(i + 1).equals("-") || formulaStr.get(i + 1).equals("+"))){ // если после скобки + или -, то писать перед ними 0
                formulaStr.add(i + 1, "0");
            }

            if (formulaStr.get(i).equals(")") && formulaStr.get(i + 1).equals("(")){ // если есть конструкция ")(", то добавить * между скобок
                formulaStr.add(i + 1, "*");
            }

            if (formulaStr.get(i).charAt(0) >= '0' && formulaStr.get(i).charAt(0) <= '9' && formulaStr.get(i + 1).equals("(")) { // если после числа '(', то длбавить между ними '*'
                formulaStr.add(i + 1, "*");
            }

            if (formulaStr.get(i).equals(")") && formulaStr.get(i + 1).charAt(0) >= '0' && formulaStr.get(i + 1).charAt(0) <= '9') { // если после ')' число , то длбавить между ними '*'
                formulaStr.add(i + 1, "*");
            }

            if ((formulaStr.get(i).equals("+") || formulaStr.get(i).equals("-") || formulaStr.get(i).equals("*") || formulaStr.get(i).equals("/")) && (formulaStr.get(i + 1).equals("+") || formulaStr.get(i + 1).equals("-"))) { // если после операции или '-' или '+'
                if (!formulaStr.get(i + 2).equals("(")) {     // если после этого '-' или '+' стоит не '(', те число
                    formulaStr.add(i + 3, ")"); // то заносим его в скобки
                    formulaStr.add(i + 1, "(");
                } else {
                    int index = i + 4;
                    while(!formulaStr.get(index).equals(")")) {   // ищем закрывающую скобку
                        index++; //запоминаем ее индекс
                    }
                    formulaStr.add(index + 1, ")"); // заносим эту конструкцию в скобки
                    formulaStr.add(i + 1, "(");
                }
            }
        }
    }

    void formulaToStringArray() { // конвертируем formula в массив стринговый массив formulaStr
        StringBuilder temp = new StringBuilder(); //временная переменная для хранения конвертируемого элемента


        int markStart = 0; // Две метки начала и конца числа
        int markEnd = 0;

        for (int i = 0; i < size; i++) { // от начала до конца formula
            if (formula.charAt(i) == '+' || formula.charAt(i) == '-' || formula.charAt(i) == '/' || formula.charAt(i) == '*' || formula.charAt(i) == '(' || formula.charAt(i) == ')'){ // если найден знак или скобка
                markEnd = i;             // концу присваиваем индекс первого занака
                for (int j = markStart; j < markEnd; j++) {
                    temp.append(formula.charAt(j));  // между началом и концом числа записывать символы в темп
                }

                if(!temp.toString().equals("")) {    // если темп не пустой, то заносим темп в массив
                    formulaStr.add(temp.toString());
                }

                formulaStr.add(String.valueOf(formula.charAt(markEnd))); // заносим операцию в массив formulaStr
                temp = new StringBuilder(); // обнуляем темп
                markStart = markEnd + 1;    // старт теперь после конца
            }
        }
        markEnd = size; // присваеваем концу числа, конец строки
        for (int j = markStart; j < markEnd; j++) { // записываем последнее число в темп
            temp.append(formula.charAt(j));
        }

        if (!temp.toString().equals("")) { // если темп не пустой, то заносим в массив
            formulaStr.add(temp.toString());
        }
    }

    boolean checkFormula() {   // проверка введенного выражения в formula, возвращает true, если ошибка, иначе false
        boolean error = false; // флаг ошибки
        String buffer = formula.toString();  // дубликат переменной formula в типе String
        buffer = buffer.replace(',', '.'); // смена ',' на '.'
        buffer = buffer.replace(" ", ""); // удаление пробелов
        formula = new StringBuilder(buffer); // заносим в formula ее измененную копию

        if (formula.toString().isEmpty()) {  // если выражение пустое возвращаем ошибку
            return true;
        }

        size = formula.length();

        if (size > 100) { // проверка на длину
            error = true;
        }

        if ((formula.charAt(0) > 40 && formula.charAt(0) < 48) && formula.charAt(0) != '-') { // первый символ число или '(' или '-'
            error = true;
        }

        if (formula.charAt(size - 1) > 39 && formula.charAt(size - 1) < 48 && formula.charAt(size - 1) != ')') { // последний символ число или ')'
            error = true;
        }

        for (int i = 0; i < size - 1; i++) { // от первого до предпоследнего элемента formula
            if (i != size - 2) {
                if ((formula.charAt(i) == '+' || formula.charAt(i) == '-' || formula.charAt(i) == '*' || formula.charAt(i) == '/' || formula.charAt(i) == '.') &&
                        (formula.charAt(i + 1) == '+' || formula.charAt(i + 1) == '-' || formula.charAt(i + 1) == '*' || formula.charAt(i + 1) == '/' || formula.charAt(i + 1) == '.') &&
                        (formula.charAt(i + 2) == '+' || formula.charAt(i + 2) == '-' || formula.charAt(i + 2) == '*' || formula.charAt(i + 2) == '/' || formula.charAt(i + 2) == '.')) { // проверка на 3 знака подряд
                    error = true;
                }

                if ((formula.charAt(i) == '+' || formula.charAt(i) == '-' || formula.charAt(i) == '*' || formula.charAt(i) == '/' || formula.charAt(i) == '.') &&
                        (formula.charAt(i + 1) == '*' || formula.charAt(i + 1) == '/' || formula.charAt(i + 1) == '.')) { // проверка на 2 знака подряд кроме '+' , '-'
                    error = true;
                }
            }

            if (formula.charAt(i) == '(' && formula.charAt(i + 1) == ')') { // проверка на пустые скобки
                error = true;
            }

            if (formula.charAt(i) == '(' && (formula.charAt(i + 1) == '*' || formula.charAt(i + 1) == '/')) { // проверка после '(' не знак, кроме '-' и '+'
                error = true;
            }

            if ((formula.charAt(i) == '-' || formula.charAt(i) == '+' || formula.charAt(i) == '*' || formula.charAt(i) == '/') && formula.charAt(i + 1) == ')') { // проверка, что перед ')' не знак
                error = true;
            }
        }

        int open = 0;  // счетчики открывающих
        int close = 0; // и закрывающих скобок

        for (int i = 0; i < size; i++) {    // от начала до конца formula
            if (!(formula.charAt(i) > 39 && formula.charAt(i) < 58 && formula.charAt(i) != ' ')) // проверка на допустимые символы
            {
                error = true;
                break;
            }

            if (formula.charAt(i) == '(') { // подсчет скобочек
                open++;
            }
            if (formula.charAt(i) == ')') {
                close++;
            }

            if (close > open) { // сначала должна идти открывающаяся скобка
                error = true;
                break;
            }
        }

        if (open != close) {    // проверка на соответствие количества скобочек
            error = true;
        }

        return error;
    }

    private int priority(String operation) { // возвращает приоритет операций
        switch (operation) {
            case "+":
                return PLUS;
            case "-":
                return MINUS;
            case "*":
                return MULTIPLICATION;
            case "/":
                return DIVISION;
            default:
                return 0;
        }
    }

    private BigDecimal operate(BigDecimal first, BigDecimal second, String operation) { // возвращает результат, выполненной операции
        switch (operation) {
            case "+":
                return first.add(second);
            case "-":
                return first.subtract(second);
            case "*":
                return first.multiply(second);
            case "/":
                return first.divide(second);
            default:
                return first;
        }
    }

    BigDecimal result() {        // считает выражение и возвращает ответ
        String givenElement; // буфер для данного элемента
        BigDecimal first;        // предыдущий элемент "стека"
        BigDecimal second;       // последний элемент "стека"

        for (String s : formulaStr) { // от первого до последенго элемента
            givenElement = s;         // дублируем данный элемент
            switch (givenElement) {
                case "+":
                case "-":
                case "/":
                case "*":
                    if (operations.isEmpty()) {       // если стек операций пуст
                        operations.add(givenElement); // то добавляем операцию в operations
                    } else {                          // если стек операций не пустой
                        while (!operations.isEmpty() && digits.size() > 1 && priority(givenElement) <= priority(operations.get(operations.size() - 1))) { // пока стек операций не пустой, и в стеке числ больше 1 числа, и приоритет данной операции <= приоритету предыдущей операции
                            second = digits.get(digits.size() - 1); // считываем последний элемент стека чисел
                            digits.remove(digits.size() - 1); // удаляем digits[digits.length - 1]
                            first = digits.get(digits.size() - 1);  // считываем последний элемент стека чисел (по факту предпоследний, тк последний считали в second)
                            digits.remove(digits.size() - 1); // удаляем digits[digits.length - 1]

                            digits.add(operate(first, second, operations.get(operations.size() - 1))); // в стек чисел добавляем результат операции над first и second
                            operations.remove(operations.size() - 1); // удаляем последний элемент и стека операций (соверщенную операцию)
                        }
                        operations.add(givenElement); // добавляем считанную операцию в стек операций
                    }
                    break;
                case "(":
                    operations.add(givenElement);     // добавялем в стек операций
                    break;
                case ")":
                    while (!operations.get(operations.size() - 1).equals("(") && digits.size() > 1) { // пока не '('  и в стеке числел больше одного числа
                        second = digits.get(digits.size() - 1); // считываем последний элемент стека чисел
                        digits.remove(digits.size() - 1); // удаляем digits[digits.length - 1]
                        first = digits.get(digits.size() - 1);  // считываем последний элемент стека чисел (по факту предпоследний, тк последний считали в second)
                        digits.remove(digits.size() - 1); // удаляем digits[digits.length - 1]

                        digits.add(operate(first, second, operations.get(operations.size() - 1))); // в стек чисел добавляем результат операции над first и second
                        operations.remove(operations.size() - 1); // удаляем последний элемент и стека операций (соверщенную операцию)
                    }
                    operations.remove(operations.size() - 1);     // удаляем последний элемент в стеке операций (открывающую скобку '(')
                    break;
                default:
                    digits.add(new BigDecimal(givenElement));       // если это не операция, то данный элемент добавляем в стек чисел
                    break;
            }
        }

        while (!operations.isEmpty() && digits.size() > 1) {
            second = digits.get(digits.size() - 1); // считываем последний элемент стека чисел
            digits.remove(digits.size() - 1); // удаляем digits[digits.length - 1]
            first = digits.get(digits.size() - 1);  // считываем последний элемент стека чисел (по факту предпоследний, тк последний считали в second)
            digits.remove(digits.size() - 1); // удаляем digits[digits.length - 1]

            digits.add(operate(first, second, operations.get(operations.size() - 1))); // в стек чисел добавляем результат операции над first и second
            operations.remove(operations.size() - 1); // удаляем последний элемент и стека операций (соверщенную операцию)
        }

        BigDecimal result = digits.get(0); // в результат записываем первое (единственное) число в стеке чисел

        formulaStr.clear(); // отчищает массив
        digits.clear();     // отчищает стек чисел
        operations.clear(); // отчищает стек оперций
        return result;      // возвращает результат
    }
}