//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package parser;



//#line 18 "rs2v_parser.y"
  import java.io.*;
import java.util.ArrayList;
import rs.RsRule;
import rs.RsSystem;
//#line 22 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Pval
String   yytext;//user variable to return contextual strings
Pval yyval; //used to return semantic vals from action routines
Pval yylval;//the 'lval' (result) I got from yylex()
Pval valstk[] = new Pval[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Pval();
  yylval=new Pval();
  valptr=-1;
}
final void val_push(Pval val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Pval[] newstack = new Pval[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Pval val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Pval val_peek(int relative)
{
  return valstk[valptr-relative];
}
final Pval dup_yyval(Pval val)
{
  return val.clone();
}
//#### end semantic value section ####
public final static short NB=257;
public final static short COMMA=258;
public final static short ARROW=259;
public final static short BAR=260;
public final static short DOT=261;
public final static short PLUS=262;
public final static short NL=263;
public final static short EOF=264;
public final static short INPUT=265;
public final static short OUTPUT=266;
public final static short INITIAL=267;
public final static short CONTEXT=268;
public final static short ID=269;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    6,    6,    6,    6,    6,    6,    6,    5,
    5,    3,    4,    4,    1,    1,    1,    1,    2,    2,
    2,    7,    7,    7,    7,
};
final static short yylen[] = {                            2,
    1,    4,    0,    2,    3,    3,    4,    4,    4,    1,
    1,    5,    5,    3,    0,    1,    2,    3,    1,    2,
    3,    0,    2,    3,    3,
};
final static short yydefred[] = {                         3,
    0,    0,   16,    4,    0,    0,    0,    0,    0,   10,
   11,    0,    0,    0,    0,   22,    0,    0,    0,   17,
    5,    6,    7,    8,    9,    0,    0,    0,   18,    0,
   23,    0,    0,    0,    0,    0,    0,   24,   25,    0,
    0,    0,
};
final static short yydgoto[] = {                          1,
    9,   33,   10,   11,   12,    2,   26,
};
final static short yysindex[] = {                         0,
    0, -226,    0,    0, -261, -261, -261, -241, -252,    0,
    0, -188, -200, -197, -195,    0, -261, -261, -255,    0,
    0,    0,    0,    0,    0, -212, -250, -239,    0,    0,
    0, -251, -185, -261, -261, -243,    0,    0,    0, -231,
 -231,    0,
};
final static short yyrindex[] = {                         0,
    0,    1,    0,    0, -192, -192, -192,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -249, -235,    0,    0,
    0,    0,    0,    0,    0,    2,    0, -183,    0, -219,
    0,    0,    0, -216, -216,    0, -208,    0,    0, -181,
 -179, -205,
};
final static short yygindex[] = {                         0,
   -2,    0,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=271;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          3,
    1,    2,   13,   14,   15,   17,   18,   34,   15,   19,
   36,   19,   15,   29,   27,   28,   20,   37,   20,   15,
   35,   16,   19,   32,   15,   42,   15,   15,   15,   20,
   19,   40,   41,   15,    3,    0,    4,   20,    5,    6,
    7,    8,   16,   19,   19,   15,   15,   15,   30,   16,
   31,    0,   15,   17,   20,   20,   18,   21,   21,    0,
   17,   19,   23,   18,   19,   24,   19,   25,   20,   15,
   15,   20,    0,   20,   21,   22,   15,   38,   39,   14,
   14,   12,   12,   13,   13,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   15,   15,
    0,    0,   15,   15,    0,    0,    0,    0,    0,   15,
   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        261,
    0,    0,    5,    6,    7,  258,  259,  258,  258,  262,
  262,  262,  262,  269,   17,   18,  269,  269,  269,  269,
  260,  263,  262,   26,  260,  269,  262,  263,  264,  269,
  262,   34,   35,  269,  261,   -1,  263,  269,  265,  266,
  267,  268,  262,  263,  264,  262,  263,  264,  261,  269,
  263,   -1,  269,  262,  263,  264,  262,  263,  264,   -1,
  269,  262,  263,  269,  262,  263,  262,  263,  269,  262,
  263,  269,   -1,  269,  263,  264,  269,  263,  264,  263,
  264,  263,  264,  263,  264,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  258,  259,
   -1,   -1,  262,  262,   -1,   -1,   -1,   -1,   -1,  269,
  269,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=269;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"NB","COMMA","ARROW","BAR","DOT","PLUS","NL","EOF","INPUT",
"OUTPUT","INITIAL","CONTEXT","ID",
};
final static String yyrule[] = {
"$accept : Start",
"Start : ListeI",
"Start : ListeI CONTEXT NL ContextDesc",
"ListeI :",
"ListeI : ListeI NL",
"ListeI : ListeI Instr NL",
"ListeI : ListeI Instr EOF",
"ListeI : ListeI INPUT IdList NL",
"ListeI : ListeI OUTPUT IdList NL",
"ListeI : ListeI INITIAL IdList NL",
"Instr : SimpleRule",
"Instr : ArrowRule",
"SimpleRule : IdList COMMA IdList COMMA IdList",
"ArrowRule : IdList ARROW IdList BAR IdList",
"ArrowRule : IdList ARROW IdList",
"IdList :",
"IdList : DOT",
"IdList : IdList ID",
"IdList : IdList PLUS ID",
"CIdList : DOT",
"CIdList : IdList ID",
"CIdList : IdList PLUS ID",
"ContextDesc :",
"ContextDesc : ContextDesc NL",
"ContextDesc : ContextDesc CIdList NL",
"ContextDesc : ContextDesc CIdList EOF",
};

//#line 78 "rs2v_parser.y"

  /* a reference to the lexer object */
  private Yylex lexer;
  private Environnement env;

  /* interface to the lexer */
  private int yylex () {
    int yyl_return = -1;
    try {
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }

  /* error reporting */
  public void yyerror (String error) {
    System.err.println ("Error: " + error+ " on line "+lexer.linenb);
  }

  /* lexer is created in the constructor */
  public Parser(Reader r) {
    env = new Environnement();
    lexer = new Yylex(r, this);
  }

  public Pval parse(){
     yyparse();
     return yyval;
  }

public void setDebug(boolean debug){
  yydebug = debug;
}
//#line 299 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 34 "rs2v_parser.y"
{yyval.rss=val_peek(0).rss;}
break;
case 2:
//#line 35 "rs2v_parser.y"
{val_peek(3).rss.setContext(val_peek(0).lli); yyval.rss = val_peek(3).rss;}
break;
case 3:
//#line 38 "rs2v_parser.y"
{yyval.rss = new RsSystem();}
break;
case 4:
//#line 39 "rs2v_parser.y"
{yyval.rss=val_peek(1).rss;}
break;
case 5:
//#line 40 "rs2v_parser.y"
{val_peek(2).rss.addRule(val_peek(1).rs); yyval.rss=val_peek(2).rss;}
break;
case 6:
//#line 41 "rs2v_parser.y"
{val_peek(2).rss.addRule(val_peek(1).rs); yyval.rss=val_peek(2).rss;}
break;
case 7:
//#line 42 "rs2v_parser.y"
{for (Symbol s: val_peek(1).li) {s.setInput(true); val_peek(3).rss.addInputSymbol(s);} yyval.rss=val_peek(3).rss;}
break;
case 8:
//#line 43 "rs2v_parser.y"
{for (Symbol s: val_peek(1).li) {s.setOutput(true); val_peek(3).rss.addOutputSymbol(s);} yyval.rss=val_peek(3).rss;}
break;
case 9:
//#line 44 "rs2v_parser.y"
{for (Symbol s: val_peek(1).li) {val_peek(3).rss.addInitialSymbol(s);} yyval.rss=val_peek(3).rss;}
break;
case 10:
//#line 47 "rs2v_parser.y"
{yyval.rs=val_peek(0).rs;}
break;
case 11:
//#line 48 "rs2v_parser.y"
{yyval.rs=val_peek(0).rs;}
break;
case 12:
//#line 51 "rs2v_parser.y"
{yyval.rs = new RsRule(val_peek(4).li,val_peek(2).li,val_peek(0).li);}
break;
case 13:
//#line 55 "rs2v_parser.y"
{yyval.rs = new RsRule(val_peek(4).li,val_peek(0).li,val_peek(2).li);}
break;
case 14:
//#line 56 "rs2v_parser.y"
{yyval.rs = new RsRule(val_peek(2).li,new ArrayList<Symbol>(),val_peek(0).li);}
break;
case 15:
//#line 59 "rs2v_parser.y"
{yyval.li = new ArrayList<Symbol>();}
break;
case 16:
//#line 60 "rs2v_parser.y"
{yyval.li = new ArrayList<Symbol>();}
break;
case 17:
//#line 61 "rs2v_parser.y"
{val_peek(1).li.add(env.getSym(val_peek(0).s)); yyval.li=val_peek(1).li;}
break;
case 18:
//#line 62 "rs2v_parser.y"
{val_peek(2).li.add(env.getSym(val_peek(0).s)); yyval.li=val_peek(2).li;}
break;
case 19:
//#line 66 "rs2v_parser.y"
{yyval.li = new ArrayList<Symbol>();}
break;
case 20:
//#line 67 "rs2v_parser.y"
{val_peek(1).li.add(env.getSym(val_peek(0).s)); yyval.li=val_peek(1).li;}
break;
case 21:
//#line 68 "rs2v_parser.y"
{val_peek(2).li.add(env.getSym(val_peek(0).s)); yyval.li=val_peek(2).li;}
break;
case 22:
//#line 72 "rs2v_parser.y"
{yyval.lli = new ArrayList<>();}
break;
case 23:
//#line 73 "rs2v_parser.y"
{yyval.lli=val_peek(1).lli;}
break;
case 24:
//#line 74 "rs2v_parser.y"
{val_peek(2).lli.add(val_peek(1).li); yyval.lli=val_peek(2).lli;}
break;
case 25:
//#line 75 "rs2v_parser.y"
{val_peek(2).lli.add(val_peek(1).li); yyval.lli=val_peek(2).lli;}
break;
//#line 548 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
