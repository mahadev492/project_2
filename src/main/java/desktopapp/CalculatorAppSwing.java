package desktopapp;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorAppSwing {

    private JFrame frame;
    private JTextField display;
    private String current = "";

    public CalculatorAppSwing() {
        frame = new JFrame("Calculator");
        frame.setSize(250, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,4,5,5));

        String[] buttons = {
            "7","8","9","/",
            "4","5","6","*",
            "1","2","3","-",
            "0","C","=","+"
        };

        for(String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.addActionListener(e -> handleButton(text));
            panel.add(btn);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void handleButton(String text) {
        switch(text) {
            case "C":
                current = "";
                display.setText("");
                break;
            case "=":
                try {
                    double result = eval(current);
                    display.setText(String.valueOf(result));
                    current = String.valueOf(result);
                } catch(Exception e) {
                    display.setText("Error");
                    current = "";
                }
                break;
            default:
                current += text;
                display.setText(current);
        }
    }

    // Simple eval for + - * /
    private double eval(String expr) {
        return new Object() {
            int pos = -1, ch;
            void nextChar() { ch = (++pos < expr.length()) ? expr.charAt(pos) : -1; }
            boolean eat(int charToEat) {
                while(ch==' ') nextChar();
                if(ch==charToEat){ nextChar(); return true; }
                return false;
            }
            double parse() {
                nextChar();
                double x = parseExpression();
                if(pos < expr.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }
            double parseExpression() {
                double x = parseTerm();
                while(true){
                    if(eat('+')) x += parseTerm();
                    else if(eat('-')) x -= parseTerm();
                    else return x;
                }
            }
            double parseTerm() {
                double x = parseFactor();
                while(true){
                    if(eat('*')) x *= parseFactor();
                    else if(eat('/')) x /= parseFactor();
                    else return x;
                }
            }
            double parseFactor() {
                if(eat('+')) return parseFactor();
                if(eat('-')) return -parseFactor();
                int startPos = this.pos;
                while((ch>='0' && ch<='9') || ch=='.') nextChar();
                return Double.parseDouble(expr.substring(startPos, this.pos));
            }
        }.parse();
    }

    public static void main(String[] args) {
        new CalculatorAppSwing();
    }
}

