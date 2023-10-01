import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScientificCalculator extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextField display;
    private JButton buttons[];
Color customGray = new Color(220, 220, 220);
    public ScientificCalculator() {
        super("Scientific Calculator");

        display = new JTextField("");
        display.setEditable(false);
        Font largerFont = new Font(display.getFont().getName(), Font.PLAIN, 55);
        display.setFont(largerFont);
        getContentPane().add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 5));
        
        String buttonLabels[] = {"%", "x²", "\u03C0", "n!", "\u2190",
            "7", "8", "9", "/", "C",
                "4", "5", "6", "*", "√'",
                "1", "2", "3", "-", "1/x",
                "+/-","0", ".", "+", "=",
                "Sin", "Cos", "Tan", "Log", "Expo"};
                // \u2190 is the Unicode for ← (left arrow)
                // \u03C0 is the Unicode for π (pi)

        buttons = new JButton[buttonLabels.length];
        
        buttonPanel.setLayout(new GridLayout(6, 5, 5, 5));
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].addActionListener(this);
             // Set the font for the button labels
             buttons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            buttons[i].setBackground(Color.white);      
            buttonPanel.add(buttons[i]);
        }
        buttons[3].setBackground(customGray);
        buttons[4].setBackground(customGray);
        buttons[8].setBackground(customGray);
        buttons[9].setBackground(customGray);
        buttons[13].setBackground(customGray);
        buttons[14].setBackground(customGray);
        buttons[18].setBackground(customGray);
        buttons[19].setBackground(customGray);
        buttons[20].setBackground(customGray);
        buttons[22].setBackground(customGray);
        buttons[23].setBackground(customGray);
        buttons[24].setBackground(customGray);
        buttons[25].setBackground(customGray);
        buttons[26].setBackground(customGray);
        buttons[27].setBackground(customGray);
        buttons[28].setBackground(customGray);
        buttons[29].setBackground(customGray);

        getContentPane().add(buttonPanel, BorderLayout.CENTER);

    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("C")) {
            // Clear the display
            display.setText("");
        } else if (command.equals("=")) {
            // Evaluate the expression and display the result
            String expression = display.getText();
            try {
                double result = evaluateExpression(expression);
                display.setText(Double.toString(result));
            } catch (IllegalArgumentException ex) {
                display.setText(ex.getMessage());
            }
        } else if (command.equals("√'")) {
            // Handle square root calculation
            double value = Double.parseDouble(display.getText());
            double result = Math.sqrt(value);
            display.setText(Double.toString(result));
        } else if (command.equals("1/x")) {
            // Handle reciprocal calculation
            double value = Double.parseDouble(display.getText());
            double result = 1.0 / value;
            display.setText(Double.toString(result));
        } else if (command.equals("+/-")) {
            // Handle negation
            double value = Double.parseDouble(display.getText());
            double result = -value;
            display.setText(Double.toString(result));
        } else if (command.equals("sin")) {
            // Handle sine calculation
            double value = Double.parseDouble(display.getText());
            double result = Math.sin(value);
            display.setText(Double.toString(result));
        } else if (command.equals("%")) {
            // Handle percentage calculation
            double value = Double.parseDouble(display.getText());
            double result = value / 100.0; // Divide by 100 to get the percentage
            display.setText(Double.toString(result));
        } else if (command.equals("x²")) {
            // Handle square calculation
            double value = Double.parseDouble(display.getText());
            double result = value * value; // Square the value
            display.setText(Double.toString(result));
        } else if (command.equals("\u03C0")) {
            // Handle pi (π)
            double pi = Math.PI;
            display.setText(Double.toString(pi));
        } else if (command.equals("n!")) {
            // Handle factorial calculation
            int value = Integer.parseInt(display.getText());
            int result = calculateFactorial(value);
            display.setText(Integer.toString(result));
        } else if (command.equals("\u2190")) {
            // Handle backspace
            String currentText = display.getText();
            if (!currentText.isEmpty()) {
                // Remove the last character
                String updatedText = currentText.substring(0, currentText.length() - 1);
                display.setText(updatedText);
            }
        } else {
            // Append the button label to the display
            display.setText(display.getText() + command);
        }
    }
    
    // Helper method to calculate factorial
    private int calculateFactorial(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return n * calculateFactorial(n - 1);
        }
    }
    

    private double evaluateExpression(String expression) {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        return evaluator.evaluate(expression);
    }


    private class ExpressionEvaluator {
        private String expression;
        private int index;

        public double evaluate(String expression) {
            this.expression = expression;
            index = 0;
            double result = parseExpression();
            if (index != expression.length()) {
                throw new IllegalArgumentException("Invalid expression");
            }
            return result;
        }

        private double parseExpression() {
            double result = parseTerm();
            while (index < expression.length()) {
                char operator = expression.charAt(index);
                if (operator != '+' && operator != '-') {
                    break;
                }
                index++;
                double operand = parseTerm();
                if (operator == '+') {
                    result += operand;
                } else {
                    result -= operand;
                }
            }
            return result;
        }

        private double parseTerm() {
            double result = parseFactor();
            while (index < expression.length()) {
                char operator = expression.charAt(index);
                if (operator != '*' && operator != '/') {
                    break;
                }
                index++;
                double operand = parseFactor();
                if (operator == '*') {
                    result *= operand;
                } else {
                    result /= operand;
                }
            }
            return result;
        }

        private double parseFactor() {
            char ch = expression.charAt(index);
            if (ch >= '0' && ch <= '9') {
                return parseNumber();
            } else if (ch == '(') {
                index++;
                double result = parseExpression();
                if (expression.charAt(index) != ')') {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                index++;
                return result;
            } else if (ch == '-') {
                index++;
                return -parseFactor();
            } else if (ch == '+') {
                index++;
                return parseFactor();
            } else {
                throw new IllegalArgumentException("Invalid character: " + ch);
            }
        }

        private double parseNumber() {
            int startIndex = index;
            while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                index++;
            }
            if (index < expression.length() && expression.charAt(index) == '.') {
                index++;
                while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                    index++;
                }
            }
            return Double.parseDouble(expression.substring(startIndex, index));
        }
    }
}
