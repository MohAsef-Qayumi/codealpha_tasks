import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GradeTrackerGUI extends JFrame {
    private JTextField studentCountField;
    private JTextArea scoreInputArea;
    private JTextArea resultArea;
    private JButton submitButton;

    public GradeTrackerGUI() {
        // Set up the JFrame
        setTitle("Grade Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Define constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Create input panel
        JLabel studentLabel = new JLabel("Number of students:");
        studentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(studentLabel, gbc);

        studentCountField = new JTextField(10);
        studentCountField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridx = 1;
        add(studentCountField, gbc);

        JLabel scoreLabel = new JLabel("Enter scores (one per line):");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(scoreLabel, gbc);

        scoreInputArea = new JTextArea(5, 20);
        scoreInputArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(scoreInputArea);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(scrollPane, gbc);

        // Submit Button
        submitButton = new JButton("Calculate");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(50, 150, 250));
        submitButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(submitButton, gbc);

        // Result Area
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(resultScrollPane, gbc);

        // Add action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResults();
            }
        });
    }

    private void calculateResults() {
        try {
            int size = Integer.parseInt(studentCountField.getText());
            if (size <= 0) {
                throw new IllegalArgumentException("Number of students must be positive.");
            }

            String[] scoreStrings = scoreInputArea.getText().split("\n");
            ArrayList<Double> scores = new ArrayList<>();

            for (String scoreString : scoreStrings) {
                double score = Double.parseDouble(scoreString.trim());
                if (score < 0) {
                    throw new IllegalArgumentException("Scores cannot be negative.");
                }
                scores.add(score);
            }

            if (scores.size() != size) {
                throw new IllegalArgumentException("Number of scores does not match the number of students.");
            }

            double average = calculateAverage(scores);
            double max = findMax(scores);
            double min = findMin(scores);

            resultArea.setText("Average Score: " + average + "\n" +
                    "Max Score: " + max + "\n" +
                    "Min Score: " + min);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double calculateAverage(ArrayList<Double> list) {
        double sum = 0;
        for (Double score : list) {
            sum += score;
        }
        return sum / list.size();
    }

    private double findMax(ArrayList<Double> list) {
        double max = list.get(0);
        for (Double score : list) {
            if (score > max) {
                max = score;
            }
        }
        return max;
    }

    private double findMin(ArrayList<Double> list) {
        double min = list.get(0);
        for (Double score : list) {
            if (score < min) {
                min = score;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradeTrackerGUI().setVisible(true));
    }
}
