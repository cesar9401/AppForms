package com.cesar31.formsweb.parser.main;

import com.cesar31.formsweb.control.Error;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class TestParserMain {

    public static void main(String[] args) {

        String input1 = "<!ini_solicitud  : \"\n\tCREAR_USUARIO\t\n\" \n >"
                + "{ \"    CREDENCIALES_USUARIO\" : [{ \n "
                + "\"PASSWORD\" : \"123._()321\"\n  ,"
                + "\"USUARIO\" : \"cesar@user_31\"\n,"
                + " }\n "
                + "  ] \n"
                + "} \n "
                + "<fiN_solicitud!>"
                + "\n";

        String input2 = "<!ini_solicitud:\"MODIFICAR_USUARIO\">\n"
                + "      { \"CREDENCIALES_USUARIO\":[{\n"
                + "            \"NUEVO_PASSWORD\": \"12345678910\",\n"
                + "            \"USUARIO_ANTIGUO\": \"juanito619\",\n"
                + "            \"USUARIO_NUEVO\": \"juanito619lopez\",\n"
                + "             \"FECHA_MODIFICACION\" : \"2002-12-21\"  \n"
                + "}    \n"
                + "          ]\n"
                + "      }\n"
                + "<fin_solicitud!>";

        String input3 = "< ! ini_solicitud : \"ELIMINAR_USUARIO\">\n"
                + "      { \"CREDENCIALES_USUARIO\":[{\n"
                + "            \"USUARIO\": \"juanito619lopez\"\n"
                + "           }         \n"
                + "         ]\n"
                + "      }\n"
                + "<fin_solicitud !>";

        String input4 = "<!ini_solicitud:\"LOGIN_USUARIO\">\n"
                + "     { \"CREDENCIALES_USUARIO\":[{\n"
                + "            \"PASSWORD\": \"12345678\",\n"
                + "\"USUARIO\": \"juanito619\"\n"
                + "           }         \n"
                + "         ]\n"
                + "      }      \n"
                + "<fin_solicitud!>";

        String input5 = "<!ini_solicitud:\"NUEVO_FORMULARIO\">\n"
                + "      { \"PARAMETROS_FORMULARIO\":[{\n"
                + "            \"TEMA\": \"Dark\"\n,"
                + "            \"ID\": \"$form1\",\n"
                + "            \"TITULO\": \"Formulario para encuesta 1\",\n"
                + "            \"NOMBRE\": \"formulario_encuesta_1\",\n"
                + "            \"FECHA_CREACION   \": \" 2020-12-31 \",\n"
                + "            \"USUARIO_CREACION\": \"cesar_31\"\n"
                + "           }         \n"
                + "         ]\n"
                + "      }\n"
                + "<fin_solicitud!>";

        String input6 = "<!ini_solicitud:\"ELIMINAR_FORMULARIO\">\n"
                + "      { \"PARAMETROS_FORMULARIO\":[{\n"
                + "            \"ID\": \"$form1\"\n"
                + "           }         \n"
                + "         ]\n"
                + "      }\n"
                + "<fin_solicitud!>";

        String input7 = "<!ini_solicitud:\"MODIFICAR_FORMULARIO\">"
                + "      { \"PARAMETROS_FORMULARIO\":[{\n"
                + "            \"ID\": \"$_ID1\",\n"
                + "            \"TITULO\": \"Formulario Modificado para encuesta 1\",\n"
                + "            \"NOMBRE\": \"fdsa\",\n"
                + "            \"TEMA\": \"1fadsf\"\n"
                + "           }         \n"
                + "         ]\n"
                + "      }\n"
                + "<fin_solicitud!>";

        String input8 = "<!ini_solicitud:\"AGREGAR_COMPONENTE\">\n"
                + "      { \"PARAMETROS_COMPONENTE\":[{\n"
                + "            \"ID\": \"$_text_cliente\",\n"
                + "            \"NOMBRE_CAMPO\": \"Cliente\",\n"
                + "            \"FILAS\": \"20\",\n"
                + "            \"COLUMNAS\": \"10\",\n"
                + "            \"FORMULARIO\": \"$form1\",\n"
                + "            \"CLASE\": \"AREA_TEXTO \",\n"
                + "            \"TEXTO_VISIBLE\": \"Nombre de cliente: \",\n"
                + "            \"ALINEACION\": \"CENTRO\",\n"
                + "            \"REQUERIDO\": \"SI\"\n"
                + "           }         \n"
                + "         ]\n"
                + "      }\n"
                + "<fin_solicitud!>";

        String input9 = "<!ini_solicitud:\"AGREGAR_COMPONENTE\">\n"
                + "      { \"PARAMETROS_COMPONENTE\":[{\n"
                + "            \"ID\": \"$_grupo_paises\",\n"
                + "            \"NOMBRE_CAMPO\": \"Pais\",\n"
                + "            \"FORMULARIO\": \"$form1\",\n"
                + "            \"CLASE\": \"CHECKBOX\",\n"
                + "            \"TEXTO_VISIBLE\": \"Pais de Origen: \",\n"
                + "            \"ALINEACION\": \"CENTRO\",\n"
                + "            \"REQUERIDO\": \"SI\",\n"
                + "            \"OPCIONES\": \"Guatemala|El Salvador|Honduras|OTRO\"\n"
                + "           }         \n"
                + "         ]\n"
                + "      }\n"
                + "<fin_solicitud!>";

        String input10 = "<!ini_solicitud:\"ELIMINAR_COMPONENTE\">\n"
                + "      { \"PARAMETROS_COMPONENTE\":[{\n"
                + "            \"ID\": \"$_grupo_paises\",\n"
                + "            \"FORMULARIO\": \"$form1\"\n"
                + "           }         \n"
                + "         ]\n"
                + "      }\n"
                + "<fin_solicitud!>";

        //debug(input2);
        FormsLex lexer = new FormsLex(new StringReader(input10));
        FormsParser parser = new FormsParser(lexer);

        try {
            parser.parse();
            if (parser.isParsed()) {
//                UserContainer u = parser.getContainer();
//                List<User> user = u.getAddUsers();
//                System.out.println("Agregar -> ");
//                user.forEach(t -> {
//                    System.out.println(t.toString());
//                });

            } else {
                List<Error> errors = parser.getContainer().getErrors();
                System.out.println("\n\nErrores: ");
                errors.forEach(e -> {
                    System.out.println(e.toString());
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void debug(String str) {
        FormsLex lex = new FormsLex(new StringReader(str));

        while (true) {
            Symbol s;
            try {
                s = lex.next_token();
                if (s.sym == 0) {
                    break;
                } else {
                    System.out.printf("Valor -> %s, fila -> %d, columna -> %d\n", s.value.toString(), s.left, s.right);
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }

        }
    }

}
