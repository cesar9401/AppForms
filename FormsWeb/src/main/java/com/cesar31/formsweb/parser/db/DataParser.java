
//----------------------------------------------------
// The following code was generated by CUP v0.11b 20160615 (GIT 4ac7450)
//----------------------------------------------------

package com.cesar31.formsweb.parser.db;

import com.cesar31.formsweb.parser.db.DataLex;
import com.cesar31.formsweb.control.*;
import com.cesar31.formsweb.model.*;
import java_cup.runtime.Symbol;
import java.util.ArrayList;
import java.util.List;
import java_cup.runtime.XMLElement;

/** CUP v0.11b 20160615 (GIT 4ac7450) generated parser.
  */
@SuppressWarnings({"rawtypes"})
public class DataParser extends java_cup.runtime.lr_parser {

 public final Class getSymbolContainer() {
    return DataParserSym.class;
}

  /** Default constructor. */
  @Deprecated
  public DataParser() {super();}

  /** Constructor which sets the default scanner. */
  @Deprecated
  public DataParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public DataParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\014\000\002\002\005\000\002\002\004\000\002\003" +
    "\003\000\002\003\005\000\002\003\002\000\002\005\023" +
    "\000\002\004\003\000\002\004\003\000\002\004\003\000" +
    "\002\004\003\000\002\004\003\000\002\004\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\040\000\004\004\005\001\002\000\004\002\042\001" +
    "\002\000\006\005\ufffd\006\006\001\002\000\004\014\014" +
    "\001\002\000\006\005\uffff\011\012\001\002\000\004\005" +
    "\011\001\002\000\004\002\001\001\002\000\006\005\ufffd" +
    "\006\006\001\002\000\004\005\ufffe\001\002\000\004\010" +
    "\015\001\002\000\016\013\021\014\023\015\016\016\020" +
    "\017\017\020\022\001\002\000\006\007\ufff9\011\ufff9\001" +
    "\002\000\006\007\ufff7\011\ufff7\001\002\000\006\007\ufff8" +
    "\011\ufff8\001\002\000\006\007\ufffb\011\ufffb\001\002\000" +
    "\006\007\ufff6\011\ufff6\001\002\000\006\007\ufffa\011\ufffa" +
    "\001\002\000\004\011\025\001\002\000\004\015\026\001" +
    "\002\000\004\010\027\001\002\000\016\013\021\014\023" +
    "\015\016\016\020\017\017\020\022\001\002\000\004\011" +
    "\031\001\002\000\004\016\032\001\002\000\004\010\033" +
    "\001\002\000\016\013\021\014\023\015\016\016\020\017" +
    "\017\020\022\001\002\000\004\011\035\001\002\000\004" +
    "\017\036\001\002\000\004\010\037\001\002\000\016\013" +
    "\021\014\023\015\016\016\020\017\017\020\022\001\002" +
    "\000\004\007\041\001\002\000\006\005\ufffc\011\ufffc\001" +
    "\002\000\004\002\000\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\040\000\004\002\003\001\001\000\002\001\001\000" +
    "\006\003\007\005\006\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\006\003" +
    "\012\005\006\001\001\000\002\001\001\000\002\001\001" +
    "\000\004\004\023\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\004\004\027\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\004\004\033\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\004\004\037\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$DataParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$DataParser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$DataParser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}




	private boolean parsed;
	private DaoDB dao;

	public DataParser(DataLex lex) {
		super(lex);
		this.parsed = true;
		this.dao = new DaoDB();
	}

	public void report_fatal_error(String message, Object info) {
        /* parsed */
        this.parsed = false;


	}

	public void syntax_error(Symbol cur_token) {
        /* parsed */
        this.parsed = false;

        System.out.printf("syntax_error: Linea: %d, columna: %d, sym: %s, nombre: %s, value: \"%s\", parse_state: %d\n", cur_token.left, cur_token.right, cur_token.sym, symbl_name_from_id(cur_token.sym), cur_token.value, cur_token.parse_state);
        List<Integer> tokens = expected_token_ids();
        System.out.print("Se esperaba ->");
        for(Integer i : tokens) {
			System.out.printf("%s, ", symbl_name_from_id(i));
        }
        System.out.println("");
	}

	public boolean isParsed() {
		return parsed;
	}

	public DaoDB getDaoDB() {
		return dao;
	}


/** Cup generated class to encapsulate user supplied action code.*/
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
class CUP$DataParser$actions {
  private final DataParser parser;

  /** Constructor */
  CUP$DataParser$actions(DataParser parser) {
    this.parser = parser;
  }

  /** Method 0 with the actual generated action code for actions 0 to 300. */
  public final java_cup.runtime.Symbol CUP$DataParser$do_action_part00000000(
    int                        CUP$DataParser$act_num,
    java_cup.runtime.lr_parser CUP$DataParser$parser,
    java.util.Stack            CUP$DataParser$stack,
    int                        CUP$DataParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$DataParser$result;

      /* select the action based on the action number */
      switch (CUP$DataParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // json_u ::= LBRACKET make_u RBRACKET 
            {
              Object RESULT =null;

              CUP$DataParser$result = parser.getSymbolFactory().newSymbol("json_u",0, ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-2)), ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), RESULT);
            }
          return CUP$DataParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= json_u EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-1)).right;
		Object start_val = (Object)((java_cup.runtime.Symbol) CUP$DataParser$stack.elementAt(CUP$DataParser$top-1)).value;
		RESULT = start_val;
              CUP$DataParser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-1)), ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$DataParser$parser.done_parsing();
          return CUP$DataParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // make_u ::= user 
            {
              Object RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).right;
		User p = (User)((java_cup.runtime.Symbol) CUP$DataParser$stack.peek()).value;
		 //System.out.println(p.toString()); 
              CUP$DataParser$result = parser.getSymbolFactory().newSymbol("make_u",1, ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), RESULT);
            }
          return CUP$DataParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // make_u ::= user COMMA make_u 
            {
              Object RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-2)).left;
		int pright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-2)).right;
		User p = (User)((java_cup.runtime.Symbol) CUP$DataParser$stack.elementAt(CUP$DataParser$top-2)).value;
		 //System.out.println(p.toString()); 
              CUP$DataParser$result = parser.getSymbolFactory().newSymbol("make_u",1, ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-2)), ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), RESULT);
            }
          return CUP$DataParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // make_u ::= 
            {
              Object RESULT =null;

              CUP$DataParser$result = parser.getSymbolFactory().newSymbol("make_u",1, ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), RESULT);
            }
          return CUP$DataParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // user ::= LBRACE USER COLON str COMMA PASS COLON str COMMA C_DATE COLON str COMMA E_DATE COLON str RBRACE 
            {
              User RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-13)).left;
		int pright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-13)).right;
		String p = (String)((java_cup.runtime.Symbol) CUP$DataParser$stack.elementAt(CUP$DataParser$top-13)).value;
		int qleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-9)).left;
		int qright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-9)).right;
		String q = (String)((java_cup.runtime.Symbol) CUP$DataParser$stack.elementAt(CUP$DataParser$top-9)).value;
		int rleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-5)).left;
		int rright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-5)).right;
		String r = (String)((java_cup.runtime.Symbol) CUP$DataParser$stack.elementAt(CUP$DataParser$top-5)).value;
		int sleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-1)).left;
		int sright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-1)).right;
		String s = (String)((java_cup.runtime.Symbol) CUP$DataParser$stack.elementAt(CUP$DataParser$top-1)).value;
		
				User u = dao.createUser(p, q, r, s);
				dao.setUser(u);
			
              CUP$DataParser$result = parser.getSymbolFactory().newSymbol("user",3, ((java_cup.runtime.Symbol)CUP$DataParser$stack.elementAt(CUP$DataParser$top-16)), ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), RESULT);
            }
          return CUP$DataParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // str ::= STR 
            {
              String RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).right;
		String p = (String)((java_cup.runtime.Symbol) CUP$DataParser$stack.peek()).value;
		 RESULT = p; 
              CUP$DataParser$result = parser.getSymbolFactory().newSymbol("str",2, ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), RESULT);
            }
          return CUP$DataParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // str ::= USER 
            {
              String RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).right;
		String p = (String)((java_cup.runtime.Symbol) CUP$DataParser$stack.peek()).value;
		 RESULT = p; 
              CUP$DataParser$result = parser.getSymbolFactory().newSymbol("str",2, ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), RESULT);
            }
          return CUP$DataParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // str ::= PASS 
            {
              String RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).right;
		String p = (String)((java_cup.runtime.Symbol) CUP$DataParser$stack.peek()).value;
		 RESULT = p; 
              CUP$DataParser$result = parser.getSymbolFactory().newSymbol("str",2, ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), RESULT);
            }
          return CUP$DataParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // str ::= C_DATE 
            {
              String RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).right;
		String p = (String)((java_cup.runtime.Symbol) CUP$DataParser$stack.peek()).value;
		 RESULT = p; 
              CUP$DataParser$result = parser.getSymbolFactory().newSymbol("str",2, ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), RESULT);
            }
          return CUP$DataParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // str ::= E_DATE 
            {
              String RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).right;
		String p = (String)((java_cup.runtime.Symbol) CUP$DataParser$stack.peek()).value;
		 RESULT = p; 
              CUP$DataParser$result = parser.getSymbolFactory().newSymbol("str",2, ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), RESULT);
            }
          return CUP$DataParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // str ::= NULL 
            {
              String RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()).right;
		String p = (String)((java_cup.runtime.Symbol) CUP$DataParser$stack.peek()).value;
		 RESULT = p; 
              CUP$DataParser$result = parser.getSymbolFactory().newSymbol("str",2, ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DataParser$stack.peek()), RESULT);
            }
          return CUP$DataParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number "+CUP$DataParser$act_num+"found in internal parse table");

        }
    } /* end of method */

  /** Method splitting the generated action code into several parts. */
  public final java_cup.runtime.Symbol CUP$DataParser$do_action(
    int                        CUP$DataParser$act_num,
    java_cup.runtime.lr_parser CUP$DataParser$parser,
    java.util.Stack            CUP$DataParser$stack,
    int                        CUP$DataParser$top)
    throws java.lang.Exception
    {
              return CUP$DataParser$do_action_part00000000(
                               CUP$DataParser$act_num,
                               CUP$DataParser$parser,
                               CUP$DataParser$stack,
                               CUP$DataParser$top);
    }
}

}