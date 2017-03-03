package com.company;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Main
{
    BufferedReader reader1;
    JTextField textField;
    JTextArea textArea;
    PrintWriter writer;
    public static void main(String[] args) {
        new Main().go();
    }

    public void go()
    {
        JFrame frame = new JFrame("Клиентская приложуха");
        JButton sendButton = new JButton("Отправить");
        sendButton.addActionListener(new sendListener());
        JPanel mainpanel = new JPanel();
        textField = new JTextField(20);
        textArea = new JTextArea(15, 50);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        JScrollPane pane = new JScrollPane(textArea);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainpanel.add(pane);
        mainpanel.add(textField);
        mainpanel.add(sendButton);
        frame.getContentPane().add(BorderLayout.CENTER, mainpanel);
        networksetting();
        Thread thread = new Thread(new Servercon());
        thread.start();


        frame.setSize(650, 500);
        frame.setVisible(true);
    }

    public void networksetting()
    {
        try {
            Socket sock = new Socket("64.233.164.108", 993);
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader1 = new BufferedReader(streamReader);
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("networking established");
            System.out.println("wsssssssssssssssssssssssssss");
        }catch (IOException ex)
        {System.out.println("fdsfds");}
    }
    class sendListener implements ActionListener
    {
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                writer.println(textField.getText());
                writer.flush();

            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            textField.setText("");
            textField.requestFocus();
        }
    }
    class Servercon implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = reader1.readLine()) != null) {
                    System.out.println("client read " + message);
                    textArea.append(message + "\n");
                }
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}

