package com.cesar31.formsclient.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 *
 * @author cesar31
 */
public class FileControl {

    public FileControl() {
    }

    /**
     * Metodo para leer archivos
     *
     * @param path
     * @return
     */
    public String readData(String path) {
        String string = "";
        File file = new File(path);
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                while (line != null) {
                    string += line + "\n";
                    line = br.readLine();
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return string;
    }

    /**
     * Escribir en archivo
     *
     * @param file
     * @param txt
     */
    public void writeFile(File file, String txt) {
        try {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.write(txt);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Informacion de linea y columna de JTextArea
     *
     * @param component
     * @return
     */
    public String lineAndColumnInfo(JTextArea component) {
        String info = "";
        int linea = 1;
        int columna = 1;

        int caretPos = component.getCaretPosition();
        try {
            linea = component.getLineOfOffset(caretPos);
            columna = caretPos - component.getLineStartOffset(linea);
            linea++;
        } catch (BadLocationException ex) {
            ex.printStackTrace(System.out);
        }

        info = "Linea " + linea + ", Columna " + columna;
        return info;
    }
}
