package bupt.cya.view;

import bupt.cya.algorithm.Solution;
import bupt.cya.algorithm.Square;
import bupt.cya.algorithm.SquareFactory;

import javax.swing.*;
import java.awt.*;

public class UI {
    private final JPanel panel;
    private JTextField NField;
    private JTextField factor1;
    private JTextField factor2;
    private JTextField interval;
    private JLabel hint;
    private JButton generate;
    private JButton start;
    private JPanel matrix;
    private Square square;
    public UI() {
        JFrame frame = new JFrame(Constants.TITLE);
        frame.setSize(540, 720);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel(new BorderLayout());
        frame.add(panel);
        setFont();
        placeComponents();
        setDefault();
        setListeners();
        frame.setVisible(true);
    }
    private void setFont() {
        String[] names = {"Label", "TextField", "Button"};
        for (String name : names)
            UIManager.put(name + ".font", new Font(Constants.FONT_FAMILY, Font.PLAIN, Constants.FONT_SIZE));
    }
    private void placeComponents() {
        placeForm();
        placeButton();
        placeSquareGrids();
    }
    private void setDefault() {
        NField.setText("15");
        factor1.setText("1");
        factor2.setText("0");
        interval.setText("100");
    }
    private void setListeners() {
        generate.addActionListener(e -> {
            hint.setText(Constants.HINT_NEW);
            SquareFactory.config(NField.getText(), factor1.getText(), factor2.getText());
            NField.setText(String.valueOf(Square.num - 1));
            this.square = SquareFactory.getSolvableSquare();
            updateSquareGrids();
        });
        start.addActionListener(e -> {
            SquareFactory.config(NField.getText(), factor1.getText(), factor2.getText());
            NField.setText(String.valueOf(Square.num - 1));
            Solution solution = new Solution();
            new Thread(new ShowTrace(solution)).start();
        });
    }
    private void placeForm() {
        JPanel form = new JPanel(new GridLayout(2, 2, 5, 5));

        JLabel NLabel = new JLabel(Constants.N);
        NField = new JTextField();
        form.add(NLabel);
        form.add(NField);

        JLabel manhattanLabel = new JLabel(Constants.F1);
        factor1 = new JTextField();
        JLabel depthLabel = new JLabel(Constants.F2);
        factor2 = new JTextField();
        form.add(manhattanLabel);
        form.add(factor1);
        form.add(depthLabel);
        form.add(factor2);

        JLabel refreshLabel = new JLabel(Constants.REF);
        interval = new JTextField();
        form.add(refreshLabel);
        form.add(interval);

        JPanel hints = new JPanel(new GridLayout(1, 1));
        hint = new JLabel(Constants.HINT_NEW);
        hints.add(hint);
        panel.add(hints, BorderLayout.NORTH);

        JPanel wrapper = new JPanel(new GridLayout(2, 1));
        wrapper.add(form);
        wrapper.add(hints);

        panel.add(wrapper, BorderLayout.NORTH);
    }
    private void placeButton() {
        JPanel buttons = new JPanel(new GridLayout(1, 2, 5, 5));
        generate = new JButton(Constants.BTN_GEN);
        start = new JButton(Constants.BTN_ST);
        buttons.add(generate);
        buttons.add(start);
        panel.add(buttons, BorderLayout.SOUTH);
    }
    private void placeSquareGrids() {
        square = SquareFactory.getSolvableSquare();
        matrix = new JPanel(new GridLayout(Square.side, Square.side));
        panel.add(matrix, BorderLayout.CENTER);
        updateSquareGrids();
    }
    private void updateSquareGrids() {
        matrix.removeAll();
        matrix.setLayout(new GridLayout(Square.side, Square.side));
        for (byte datum : square.getData()) {
            JLabel label = new JLabel(datum == 0 ? "" : String.valueOf(datum), JLabel.CENTER);
            if (datum != 0)
                label.setBorder(BorderFactory.createLineBorder(Color.getHSBColor((float)datum/Square.num, 1, 1), 5));
            matrix.add(label);
        }
        matrix.updateUI();
        panel.updateUI();
    }
    private class ShowTrace implements Runnable {
        private final Solution solution;
        public ShowTrace(Solution solution) {
            this.solution = solution;
        }
        @Override
        public void run() {
            try {
                generate.setEnabled(false);
                start.setEnabled(false);
                hint.setText(Constants.HINT_ING);
                Solution.Output output = solution.solve(square);
                hint.setText(String.format(Constants.HINT_CALC, output.iterNum));
                for (Square step : output.trace) {
                    square = step;
                    updateSquareGrids();
                    try {
                        Thread.sleep(Integer.parseInt(interval.getText()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                hint.setText(String.format(Constants.HINT_FIN, output.stepNum, output.iterNum));
            } catch (Exception e) {
                hint.setText(Constants.HINT_FAIL);
            } finally {
                generate.setEnabled(true);
                start.setEnabled(true);
            }
        }
    }
}
