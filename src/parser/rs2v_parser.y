/*
 * Copyright 2019 Sergey Verlan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

%{
  import java.io.*;
import java.util.ArrayList;
import rs.RsRule;
import rs.RsSystem;
%}

%token <i> NB
%token COMMA ARROW BAR DOT PLUS NL EOF INPUT OUTPUT INITIAL CONTEXT
%token <s> ID
%type <li> IdList CIdList
%type <rs> SimpleRule ArrowRule Instr
%type <rss> ListeI Start
%type <lli> ContextDesc

%%

Start: ListeI {$$=$1;}
| ListeI CONTEXT NL ContextDesc {$1.setContext($4); $$ = $1;}
;

ListeI: {$$ = new RsSystem();}
       | ListeI NL {$$=$1;}
       | ListeI Instr NL {$1.addRule($2); $$=$1;}
       | ListeI Instr EOF {$1.addRule($2); $$=$1;}
       | ListeI INPUT IdList NL {for (Symbol s: $3) {s.setInput(true); $1.addInputSymbol(s);} $$=$1;}
       | ListeI OUTPUT IdList NL {for (Symbol s: $3) {s.setOutput(true); $1.addOutputSymbol(s);} $$=$1;}
       | ListeI INITIAL IdList NL {for (Symbol s: $3) {$1.addInitialSymbol(s);} $$=$1;}
;

Instr:   SimpleRule {$$=$1;}
| ArrowRule {$$=$1;}
;

SimpleRule: IdList COMMA IdList COMMA IdList {$$ = new RsRule($1,$3,$5);}
;

ArrowRule: 
 IdList ARROW IdList BAR IdList {$$ = new RsRule($1,$5,$3);}
| IdList ARROW IdList {$$ = new RsRule($1,new ArrayList<Symbol>(),$3);}
;

IdList:     {$$ = new ArrayList<Symbol>();}
|DOT       {$$ = new ArrayList<Symbol>();}
| IdList ID {$1.add(env.getSym($2)); $$=$1;}
| IdList PLUS ID {$1.add(env.getSym($3)); $$=$1;}
;

CIdList:     
DOT       {$$ = new ArrayList<Symbol>();}
| IdList ID {$1.add(env.getSym($2)); $$=$1;}
| IdList PLUS ID {$1.add(env.getSym($3)); $$=$1;}
;


ContextDesc:   {$$ = new ArrayList<>();}
| ContextDesc NL {$$=$1;}
| ContextDesc CIdList NL {$1.add($2); $$=$1;}
| ContextDesc CIdList EOF {$1.add($2); $$=$1;}

%%

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