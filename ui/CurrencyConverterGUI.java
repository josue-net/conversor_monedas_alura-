package com.converter.ui;

import com.converter.service.CurrencyService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class CurrencyConverterGUI extends JFrame {

    private JComboBox<String> comboFrom;
    private JComboBox<String> comboTo;
    private JTextField inputAmount;
    private JLabel resultLabel;

    private JTextArea historyArea;  // NUEVO: área de historial

    private final CurrencyService service;

    public CurrencyConverterGUI() {
        this.service = new CurrencyService();

        setTitle("Conversor de Monedas (Modular)");
        setSize(480, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(12, 1));

        JLabel title = new JLabel("Conversor de Monedas", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title);

        comboFrom = new JComboBox<>();
        comboTo = new JComboBox<>();

        add(new JLabel("Convertir de:"));
        add(comboFrom);
        add(new JLabel("Convertir a:"));
        add(comboTo);

        inputAmount = new JTextField();
        add(new JLabel("Monto:"));
        add(inputAmount);

        // BOTÓN CONVERTIR
        JButton convertButton = new JButton("Convertir");
        convertButton.addActionListener(this::convertAction);
        add(convertButton);

        // BOTÓN LIMPIAR
        JButton clearButton = new JButton("Limpiar");
        clearButton.addActionListener(e -> clearFields());
        add(clearButton);

        resultLabel = new JLabel("Resultado: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(resultLabel);

        // ----- HISTORIAL DE CONVERSIONES -----
        JLabel historyLabel = new JLabel("Historial:", SwingConstants.CENTER);
        historyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(historyLabel);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(historyArea);
        add(scroll);

        loadSymbolsAsync();
    }

    private void loadSymbolsAsync() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Map<String, String> symbols = service.loadSymbols();
                SwingUtilities.invokeLater(() -> {
                    symbols.keySet().forEach(k -> {
                        comboFrom.addItem(k);
                        comboTo.addItem(k);
                    });
                    comboFrom.setSelectedItem("USD");
                    comboTo.setSelectedItem("PEN");
                });
                return null;
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(null, "Monedas cargadas correctamente");
            }
        };
        worker.execute();
    }

    private void convertAction(ActionEvent e) {
        try {
            String from = comboFrom.getSelectedItem().toString();
            String to = comboTo.getSelectedItem().toString();
            double amount = Double.parseDouble(inputAmount.getText());

            double result = service.convert(from, to, amount);
            resultLabel.setText("Resultado: " + result + " " + to);

            // AGREGAR AL HISTORIAL
            addToHistory(amount + " " + from + " → " + result + " " + to);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Limpia campos
    private void clearFields() {
        inputAmount.setText("");
        resultLabel.setText("Resultado: ");
        comboFrom.setSelectedItem("USD");
        comboTo.setSelectedItem("PEN");
    }

    // ----- MÉTODO PARA AÑADIR HISTORIAL -----
    private void addToHistory(String text) {
        historyArea.append(text + "\n");
    }
}
