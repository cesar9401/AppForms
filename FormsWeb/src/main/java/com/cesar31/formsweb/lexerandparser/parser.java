
//----------------------------------------------------
// The following code was generated by CUP v0.11b 20160615 (GIT 4ac7450)
//----------------------------------------------------

package com.cesar31.formsweb.lexerandparser;

import com.cesar31.formsweb.lexerandparser.FormsLex;
import java_cup.runtime.Symbol;
import java.util.ArrayList;
import java.util.List;
import java_cup.runtime.XMLElement;

/** CUP v0.11b 20160615 (GIT 4ac7450) generated parser.
  */
@SuppressWarnings({"rawtypes"})
public class parser extends java_cup.runtime.lr_parser {

 public final Class getSymbolContainer() {
    return sym.class;
}

  /** Default constructor. */
  @Deprecated
  public parser() {super();}

  /** Constructor which sets the default scanner. */
  @Deprecated
  public parser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\035\000\002\005\012\000\002\002\004\000\002\004" +
    "\017\000\002\002\011\000\002\003\011\000\002\006\003" +
    "\000\002\006\003\000\002\007\003\000\002\007\003\000" +
    "\002\010\003\000\002\010\003\000\002\011\003\000\002" +
    "\011\003\000\002\012\003\000\002\012\003\000\002\013" +
    "\003\000\002\013\003\000\002\014\003\000\002\014\003" +
    "\000\002\015\003\000\002\015\003\000\002\017\003\000" +
    "\002\017\003\000\002\016\003\000\002\016\003\000\002" +
    "\020\003\000\002\020\003\000\002\021\003\000\002\021" +
    "\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\076\000\006\003\006\004\004\001\002\000\006\003" +
    "\ufffc\024\ufffc\001\002\000\006\003\011\024\013\001\002" +
    "\000\006\003\ufffb\024\ufffb\001\002\000\004\002\010\001" +
    "\002\000\004\002\000\001\002\000\010\003\ufff3\023\ufff3" +
    "\031\ufff3\001\002\000\006\003\016\023\015\001\002\000" +
    "\010\003\ufff4\023\ufff4\031\ufff4\001\002\000\006\003\017" +
    "\010\021\001\002\000\026\003\ufff2\010\ufff2\011\ufff2\012" +
    "\ufff2\013\ufff2\020\ufff2\024\ufff2\026\ufff2\030\ufff2\033\ufff2" +
    "\001\002\000\026\003\ufff1\010\ufff1\011\ufff1\012\ufff1\013" +
    "\ufff1\020\ufff1\024\ufff1\026\ufff1\030\ufff1\033\ufff1\001\002" +
    "\000\006\003\ufff7\023\ufff7\001\002\000\006\003\016\023" +
    "\015\001\002\000\006\003\ufff8\023\ufff8\001\002\000\006" +
    "\003\024\026\023\001\002\000\006\003\ufff0\027\ufff0\001" +
    "\002\000\006\003\uffef\027\uffef\001\002\000\006\003\030" +
    "\027\026\001\002\000\006\003\uffee\023\uffee\001\002\000" +
    "\006\003\016\023\015\001\002\000\006\003\uffed\023\uffed" +
    "\001\002\000\006\003\033\005\032\001\002\000\004\002" +
    "\ufffa\001\002\000\004\002\ufff9\001\002\000\004\002\001" +
    "\001\002\000\006\003\037\011\036\001\002\000\006\003" +
    "\ufff6\023\ufff6\001\002\000\006\003\ufff5\023\ufff5\001\002" +
    "\000\006\003\016\023\015\001\002\000\006\003\011\024" +
    "\013\001\002\000\006\003\044\031\045\001\002\000\006" +
    "\003\030\027\026\001\002\000\006\003\uffe7\027\uffe7\001" +
    "\002\000\006\003\uffe8\027\uffe8\001\002\000\006\003\016" +
    "\023\015\001\002\000\004\012\073\001\002\000\006\003" +
    "\053\033\051\001\002\000\006\003\uffea\023\uffea\001\002" +
    "\000\006\003\016\023\015\001\002\000\006\003\uffe9\023" +
    "\uffe9\001\002\000\006\003\064\030\065\001\002\000\004" +
    "\013\056\001\002\000\006\003\016\023\015\001\002\000" +
    "\006\003\011\024\013\001\002\000\006\003\016\023\015" +
    "\001\002\000\004\020\062\001\002\000\006\003\016\023" +
    "\015\001\002\000\006\003\ufffd\030\ufffd\001\002\000\010" +
    "\003\uffeb\005\uffeb\032\uffeb\001\002\000\010\003\uffec\005" +
    "\uffec\032\uffec\001\002\000\006\003\067\032\070\001\002" +
    "\000\006\003\uffe5\030\uffe5\001\002\000\006\003\uffe6\030" +
    "\uffe6\001\002\000\006\003\064\030\065\001\002\000\006" +
    "\003\uffff\005\uffff\001\002\000\006\003\016\023\015\001" +
    "\002\000\006\003\011\024\013\001\002\000\006\003\016" +
    "\023\015\001\002\000\004\020\077\001\002\000\006\003" +
    "\016\023\015\001\002\000\006\003\ufffe\033\ufffe\001\002" +
    "" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\076\000\006\005\006\006\004\001\001\000\002\001" +
    "\001\000\004\012\011\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\004\013" +
    "\013\001\001\000\002\001\001\000\004\010\017\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\004\013\021\001\001\000\002\001\001\000\004\014\024" +
    "\001\001\000\002\001\001\000\002\001\001\000\006\004" +
    "\030\015\026\001\001\000\002\001\001\000\004\013\034" +
    "\001\001\000\002\001\001\000\004\007\033\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\004" +
    "\011\037\001\001\000\002\001\001\000\002\001\001\000" +
    "\004\013\040\001\001\000\004\012\041\001\001\000\004" +
    "\020\042\001\001\000\004\015\045\001\001\000\002\001" +
    "\001\000\002\001\001\000\006\002\047\013\046\001\001" +
    "\000\002\001\001\000\004\016\051\001\001\000\002\001" +
    "\001\000\006\003\053\013\054\001\001\000\002\001\001" +
    "\000\004\017\065\001\001\000\002\001\001\000\004\013" +
    "\056\001\001\000\004\012\057\001\001\000\004\013\060" +
    "\001\001\000\002\001\001\000\004\013\062\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\004" +
    "\021\070\001\001\000\002\001\001\000\002\001\001\000" +
    "\004\017\071\001\001\000\002\001\001\000\004\013\073" +
    "\001\001\000\004\012\074\001\001\000\004\013\075\001" +
    "\001\000\002\001\001\000\004\013\077\001\001\000\002" +
    "\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$parser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$parser$actions(this);
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
    return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}




	private Symbol cur_token;
	private boolean parsed;

	// public FormsParser(FormsLex lex) {
	// 	super(lex);
	// 	this.parsed = true;
	// }

	public parser(FormsLex lex) {
		super(lex);
		this.parsed = true;
	}

	public void report_fatal_error(String message, Object info) {
        /* parsed */
        this.parsed = false;

        //System.out.println("report_fatal_error");
        //System.out.println("Message: " + message);
        //System.out.println("Object info: " + info);

        /* Armando reportes de errores */
        List<String> expected = new ArrayList<>();
        List<Integer> tokens = expected_token_ids();
        for(Integer i : tokens) {
            expected.add(symbl_name_from_id(i));
        }
	}

	public void syntax_error(Symbol cur_token) {
        /* parsed */
        this.parsed = false;
        /**/

        List<String> expected = new ArrayList<>();

        this.cur_token = cur_token;
        System.out.printf("syntax_error: Linea: %d, columna: %d, sym: %s, nombre: %s, value: \"%s\", parse_state: %d\n", cur_token.left, cur_token.right, cur_token.sym, symbl_name_from_id(cur_token.sym), cur_token.value, cur_token.parse_state);

        List<Integer> tokens = expected_token_ids();
        System.out.print("Se esperaba ->");
        for(Integer i : tokens) {
			System.out.printf("%s, ", symbl_name_from_id(i));
            expected.add(symbl_name_from_id(i));
        }
        System.out.println("");
	}

	protected int error_sync_size() {
		return 1;
	}

	public boolean isParsed() {
		return parsed;
	}



/** Cup generated class to encapsulate user supplied action code.*/
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
class CUP$parser$actions {
  private final parser parser;

  /** Constructor */
  CUP$parser$actions(parser parser) {
    this.parser = parser;
  }

  /** Method 0 with the actual generated action code for actions 0 to 300. */
  public final java_cup.runtime.Symbol CUP$parser$do_action_part00000000(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$parser$result;

      /* select the action based on the action number */
      switch (CUP$parser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // sol_user_nl ::= ini colon quote new_user quote greater user_nl fin 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("sol_user_nl",3, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-7)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= sol_user_nl EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Object start_val = (Object)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		RESULT = start_val;
              CUP$parser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$parser$parser.done_parsing();
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // user_nl ::= lbrace quote cred quote colon lbracket lbrace user_user comma user_pass rbrace rbracket rbrace 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("user_nl",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-12)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // user_user ::= quote USER quote colon quote PARAM quote 
            {
              Object RESULT =null;
		int userleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int userright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Object user = (Object)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		
					System.out.printf("User = %s\n", user);
				
              CUP$parser$result = parser.getSymbolFactory().newSymbol("user_user",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-6)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // user_pass ::= quote PASS quote colon quote PARAM quote 
            {
              Object RESULT =null;
		int passleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int passright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Object pass = (Object)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		
					System.out.printf("Pass = %s\n", pass); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("user_pass",1, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-6)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // ini ::= INIT_SOL 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("ini",4, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // ini ::= error 
            {
              Object RESULT =null;
		 System.out.println("ini ::= error"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ini",4, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // fin ::= FIN_SOL 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("fin",5, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // fin ::= error 
            {
              Object RESULT =null;
		 System.out.println("fin ::= error"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("fin",5, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // new_user ::= NEW_USER 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("new_user",6, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // new_user ::= error 
            {
              Object RESULT =null;
		 System.out.println("new_user ::= error"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("new_user",6, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // cred ::= CRED 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("cred",7, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // cred ::= error 
            {
              Object RESULT =null;
		 System.out.println("cred ::= error"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("cred",7, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // colon ::= COLON 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("colon",8, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // colon ::= error 
            {
              Object RESULT =null;
		 System.out.println("colon ::= error"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("colon",8, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // quote ::= QUOTE 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("quote",9, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // quote ::= error 
            {
              Object RESULT =null;
		 System.out.println("quote ::= error"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("quote",9, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // greater ::= GREATER 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("greater",10, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // greater ::= error 
            {
              Object RESULT =null;
		 System.out.println("greater ::= error"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("greater",10, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // lbrace ::= LBRACE 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("lbrace",11, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // lbrace ::= error 
            {
              Object RESULT =null;
		 System.out.println("lbrace ::= error"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("lbrace",11, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // rbrace ::= RBRACE 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("rbrace",13, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // rbrace ::= error 
            {
              Object RESULT =null;
		 System.out.println("rbrace ::= error"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("rbrace",13, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // comma ::= COMMA 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("comma",12, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // comma ::= error 
            {
              Object RESULT =null;
		 System.out.println("comma ::= error"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("comma",12, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // lbracket ::= LBRACKET 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("lbracket",14, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // lbracket ::= error 
            {
              Object RESULT =null;
		 System.out.println("lbracket ::= error"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("lbracket",14, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // rbracket ::= RBRACKET 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("rbracket",15, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // rbracket ::= error 
            {
              Object RESULT =null;
		 System.out.println("rbracket ::= error"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("rbracket",15, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number "+CUP$parser$act_num+"found in internal parse table");

        }
    } /* end of method */

  /** Method splitting the generated action code into several parts. */
  public final java_cup.runtime.Symbol CUP$parser$do_action(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
              return CUP$parser$do_action_part00000000(
                               CUP$parser$act_num,
                               CUP$parser$parser,
                               CUP$parser$stack,
                               CUP$parser$top);
    }
}

}
