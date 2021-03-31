package com.cesar31.formsweb.control;

import java.util.HashMap;

/**
 *
 * @author cesar31
 */
public class HandleError {

    private HashMap<String, String> gram;

    public HandleError() {
        initGram();
    }

    public String getValue(String key) {
        return (this.gram.containsKey(key)) ? gram.get(key) : key;
    }

    private void initGram() {
        this.gram = new HashMap<>();
        put("INIT_SOL", "ini_solicitud");
        put("FIN_SOL", "fin_solicitud");
        put("INIT_MANY_SOL", "ini_solicitudes");
        put("FIN_MANY_SOL", "fin_solicitudes");
        put("DATE", "fecha en formato yyyy-mm-dd");
        put("STR_NUMBER", "Entero entre comillas");
        put("ADD_USER", "\"CREAR_USUARIO\"");
        put("CRED", "\"CREDENCIALES_USUARIO\"");
        put("USER", "\"USUARIO\"");
        put("PASS", "\"PASSWORD\"");
        put("DATE_ADD", "\"FECHA_CREACION\"");
        put("EDIT_USER", "\"MODIFICAR_USUARIO\"");
        put("DEL_USER", "\"ELIMINAR_USUARIO\"");
        put("LOGIN", "\"LOGIN_USUARIO\"");
        put("OLD_USER", "\"USUARIO_ANTIGUO\"");
        put("NEW_USER", "\"USUARIO_NUEVO\"");
        put("NEW_PASS", "\"NUEVO_PASSWORD\"");
        put("DATE_MOD", "\"FECHA_MODIFICACION\"");
        put("NEW_FORM", "\"NUEVO_FORMULARIO\"");
        put("PARAM_F", "\"PARAMETROS_FORMULARIO\"");
        put("ID", "\"ID\"");
        put("ID_", "identificador(debe empezar con '$', '_' o '-') e ir entre comillas sin espacios");
        put("TITLE", "\"TITULO\"");
        put("NAME", "\"NOMBRE\"");
        put("THEME", "\"TEMA\"");
        put("DARK", "\"DARK\"");
        put("LIGHT", "\"LIGHT\"");
        put("USER_C", "\"USUARIO_CREACION\"");
        put("DEL_FORM", "\"ELIMINAR_FORMULARIO\"");
        put("EDIT_FORM", "\"EDITAR_FORMULARIO\"");
        put("ADD_COMP", "\"AGREGAR_COMPONENTE\"");
        put("PARAM_C", "\"PARAMETROS_COMPONENTE\"");
        put("FIELD_N", "\"NOMBRE_CAMPO\"");
        put("FORM", "\"FORMULARIO\"");
        put("CLASS", "\"CLASE\"");
        put("TEXT_FIELD", "\"CAMPO_TEXTO\"");
        put("TEXT_AREA", "\"AREA_TEXTO\"");
        put("CHECKBOX", "\"CHECKBOX\"");
        put("RADIO", "\"RADIO\"");
        put("FILE", "\"FICHERO\"");
        put("IMG", "\"IMAGEN\"");
        put("COMBO", "\"COMBO\"");
        put("BTN", "\"BOTON\"");
        put("INDEX", "\"INDICE\"");
        put("TEXT", "\"TEXTO_VISIBLE\"");
        put("ALIGN", "\"ALINEACION\"");
        put("CENTER", "\"CENTRO\"");
        put("LEFT", "\"IZQUIERDA\"");
        put("RIGHT", "\"DERECHA\"");
        put("JUSTIFY", "\"JUSTIFICAR\"");
        put("REQUIRED", "\"REQUERIDO\"");
        put("YES", "\"SI\"");
        put("NO", "\"NO\"");
        put("OPTION", "\"OPCIONES\"");
        put("ROWS", "\"FILAS\"");
        put("COLUMNS", "\"COLUMNAS\"");
        put("URL", "\"URL\"");
        put("DEL_COMP", "\"ELIMINAR_COMPONENTE\"");
        put("EDIT_COMP", "\"EDITAR_COMPONENTE\"");
        put("STR", "String entre comillas");
        put("OPTION_V", "Opciones separadas por |, entre comillas");
        put("COLON", ":");
        put("SMALLER", "<");
        put("EXCL", "!");
        put("GREATER", ">");
        put("LBRACE", "{");
        put("RBRACE", "}");
        put("LBRACKET", "[");
        put("RBRACKET", "]");
        put("COMMA", ",");
        put("PLUS", "+");
        put("MINUS", "-");
        put("TIMES", "*");
        put("DIVIDE", "/");
        put("INTEGER", "Entero (0 en adelante)");
        put("PARAM", "\"Cadena de caracteres alfanumericos\"");
        put("SYMB", "Conjunto de simbolos");
        put("QUOTE", "\"");
        put("EMPTY", "Cadena con espacios entre comillas(\"\")");
        put("STR_SPACE", "Cadena entre comillas con saltos de linea.");
        put("OPTION_SPACE", "Cadena de opciones entre comillas con saltos de linea.");

        put("ERROR", "Caracter que no pertenece al lenguaje");

        /* SQForm */
        put("SEL", "SELECT");
        put("TO", "TO");
        put("FM", "FORM");
        put("ARROW", "->");
        put("WHERE", "WHERE");
        put("AND", "AND");
        put("OR", "OR");
        put("NOT", "NOT");

        put("GR_EQ", ">=");
        put("SM_EQ", "<=");
        put("EQ", "=");
        put("NEQ", "<>");
        put("DECIMAL", "Numero decimal");
        
    }

    private void put(String key, String value) {
        gram.put(key, value);
    }
}
