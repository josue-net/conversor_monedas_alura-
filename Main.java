
package com.converter;

import com.converter.ui.CurrencyConverterGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverterGUI().setVisible(true));
    }
}
