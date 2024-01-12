package org.example;//Titenko

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CryptoGUI extends JFrame {

    private JRadioButton encryptionRadioButton = new JRadioButton("Шифрование");
    private JRadioButton decryptionRadioButton = new JRadioButton("Дешифрование");
    private JTextField fileTextField = new JTextField(30);
    private JTextField keyTextField = new JTextField(30);
    private JTextField generatedKeyTextField = new JTextField(30);
    private JButton browseButton = new JButton("Обзор");
    private JButton submitButton = new JButton("Выполнить");
    private JButton generateKeyButton = new JButton("Сгенерировать ключ");
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JFileChooser fileChooser = new JFileChooser();

    public CryptoGUI() {
        setTitle("Шифрование и дешифрование файлов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        buttonGroup.add(encryptionRadioButton);
        buttonGroup.add(decryptionRadioButton);

        JPanel radioPanel = new JPanel();
        radioPanel.add(new JLabel("Выберите действие:"));
        radioPanel.add(encryptionRadioButton);
        encryptionRadioButton.setSelected(true);
        radioPanel.add(decryptionRadioButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(radioPanel, gbc);

        gbc.gridy++;
        add(new JLabel("Выберите файл:"), gbc);

        gbc.gridx++;
        add(fileTextField, gbc);

        gbc.gridx++;
        add(browseButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Введите ключ:"), gbc);

        gbc.gridx++;
        add(keyTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Сгенерированный ключ:"), gbc);

        gbc.gridx++;
        add(generatedKeyTextField, gbc);

        gbc.gridx++;
        add(generateKeyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);

        browseButton.addActionListener(e -> browseFile());
        submitButton.addActionListener(e -> displayInput());
        generateKeyButton.addActionListener(e -> generateAESKey());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void browseFile() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            fileTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void displayInput() {
        String selectedAction = encryptionRadioButton.isSelected() ? "Шифрование" : "Дешифрование";
        String filePath = fileTextField.getText();
        String key = keyTextField.getText();

        if (filePath.isEmpty() || key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, введите файл и ключ", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        generatedKeyTextField.setText("");

        try {
            if ("Шифрование".equals(selectedAction)) {
                FileEncryptor.encryptFile(filePath, key);
                JOptionPane.showMessageDialog(this, "Файл успешно зашифрован", "Результат", JOptionPane.INFORMATION_MESSAGE);
            } else if ("Дешифрование".equals(selectedAction)) {
                FileEncryptor.decryptFile(filePath, key);
                JOptionPane.showMessageDialog(this, "Файл успешно расшифрован", "Результат", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка при обработке файла", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateAESKey() {
        String generatedKey = FileEncryptor.generateAESKey();
        generatedKeyTextField.setText(generatedKey);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CryptoGUI::new);
    }
}
