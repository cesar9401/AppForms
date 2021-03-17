package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.User;
import com.cesar31.formsweb.parser.db.DataLex;
import com.cesar31.formsweb.parser.db.DataParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class HandlerDB {

    public final String DB_URL = "forms.db";

    public HandlerDB() {
    }

    /**
     * Leer DB
     *
     * @param path
     * @return
     */
    public String readDate(String path) {
        String data = "";
        File file = new File(path);
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                while (line != null) {
                    data += line;
                    line = br.readLine();
                    if (line != null) {
                        line += "\n";
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }

        return data;
    }

    /**
     * Vereficar si el usuario esta disponible y agregar a DB
     *
     * @param users
     */
    public void addUser(List<User> users) {
        users.forEach(u -> {
            if (!isInSystem(u.getUser())) {
                // Usuario disponible
                // Agregar a DB
                addUser(u);
            } else {
                // Usuario ocupado
                System.out.println("El usuario: " + u.getUser() + ", no se encuentra disponible");
            }
        });
    }

    /**
     * ----------> DataLex and DataParser
     *
     * Obtener usuarios de DB
     *
     * @return
     */
    private List<User> readUsers() {
        List<User> users = new ArrayList<>();
        String data = readDate(DB_URL);

        DataLex lexer = new DataLex(new StringReader(data));
        DataParser parser = new DataParser(lexer);
        try {
            parser.parse();
            if (parser.isParsed()) {
                users = parser.getDaoDB().getUsers();
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }

        return users;
    }

    /**
     * Agregar al usuario a la DB
     *
     * @param u
     */
    private void addUser(User u) {
        List<User> users = readUsers();
        users.add(u);

        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writerWithDefaultPrettyPrinter().withView(User.class).writeValueAsString(users);
            System.out.println(json);
            mapper.writerWithDefaultPrettyPrinter().withView(User.class).writeValue(new File("forms.db"), users);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Verificar que el usuario este disponible
     *
     * @param user
     * @return
     */
    private boolean isInSystem(String user) {
        List<User> usersSystem = readUsers();
        return usersSystem.stream().anyMatch(u -> (u.getUser().equals(user)));
    }
}
