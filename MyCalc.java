import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class MyCalc extends WindowAdapter implements ActionListener {
    Frame f;
    Label l1;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0;
    Button badd, bsub, bmult, bdiv, bcalc, bclr;
    StringBuilder currentInput = new StringBuilder();

    MyCalc() {
        f = new Frame("My Calculator");
        l1 = new Label();
        l1.setBackground(Color.LIGHT_GRAY);
        l1.setBounds(50, 50, 260, 60);
        l1.setAlignment(Label.RIGHT);
        l1.setFont(new Font("Arial", Font.PLAIN, 24));

        // Number buttons
        b1 = new Button("1"); b2 = new Button("2"); b3 = new Button("3");
        b4 = new Button("4"); b5 = new Button("5"); b6 = new Button("6");
        b7 = new Button("7"); b8 = new Button("8"); b9 = new Button("9");
        b0 = new Button("0");

        // Operation buttons
        badd = new Button("+"); bsub = new Button("-"); bmult = new Button("*");
        bdiv = new Button("/"); bcalc = new Button("="); bclr = new Button("CE");

        // Set button bounds
        setButtonBounds();

        // Add action listeners
        addActionListeners();

        // Add components to frame
        f.add(l1);
        addButtonsToFrame();

        f.setSize(360, 500);
        f.setLayout(null);
        f.setVisible(true);
        f.addWindowListener(this);
    }

    private void setButtonBounds() {
        b1.setBounds(50, 340, 50, 50);
        b2.setBounds(120, 340, 50, 50);
        b3.setBounds(190, 340, 50, 50);
        b4.setBounds(50, 270, 50, 50);
        b5.setBounds(120, 270, 50, 50);
        b6.setBounds(190, 270, 50, 50);
        b7.setBounds(50, 200, 50, 50);
        b8.setBounds(120, 200, 50, 50);
        b9.setBounds(190, 200, 50, 50);
        b0.setBounds(120, 410, 50, 50);
        badd.setBounds(260, 200, 50, 50);
        bsub.setBounds(260, 270, 50, 50);
        bmult.setBounds(260, 340, 50, 50);
        bdiv.setBounds(260, 130, 50, 50);
        bcalc.setBounds(190, 410, 50, 50);
        bclr.setBounds(50, 130, 65, 50);
    }

    private void addActionListeners() {
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        b0.addActionListener(this);
        badd.addActionListener(this);
        bsub.addActionListener(this);
        bmult.addActionListener(this);
        bdiv.addActionListener(this);
        bcalc.addActionListener(this);
        bclr.addActionListener(this);
    }

    private void addButtonsToFrame() {
        f.add(b1); f.add(b2); f.add(b3);
        f.add(b4); f.add(b5); f.add(b6);
        f.add(b7); f.add(b8); f.add(b9);
        f.add(b0); f.add(badd); f.add(bsub);
        f.add(bmult); f.add(bdiv); f.add(bcalc);
        f.add(bclr);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Handle number and operator buttons
        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9') || 
            command.equals("+") || command.equals("-") || 
            command.equals("*") || command.equals("/")) {
            currentInput.append(command);
            l1.setText(currentInput.toString());
        } 
        // Handle equals button
        else if (command.equals("=")) {
            double result = evaluateExpression(currentInput.toString());
            l1.setText(String.valueOf(result));
            currentInput.setLength(0); // Clear input for the next calculation
        } 
        // Handle clear button
        else if (command.equals("CE")) {
            currentInput.setLength(0);
            l1.setText("");
        }
    }

    // Evaluate the full expression string using stacks
    private double evaluateExpression(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        
        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);
            
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                numbers.push(Double.parseDouble(sb.toString()));
                i--; // step back as we moved forward in inner loop
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(c);
            }
            i++;
        }
        
        // Apply remaining operations in stack
        while (!operators.isEmpty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }
        
        return numbers.pop();
    }

    // Returns precedence of operators
    private int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    // Applies an operation to two numbers
    private double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': if (b != 0) return a / b; else throw new ArithmeticException("Cannot divide by zero");
        }
        return 0;
    }

    public static void main(String[] args) {
        new MyCalc();
    }
}
