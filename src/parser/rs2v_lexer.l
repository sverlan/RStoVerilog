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


package parser;

%%

%{
  StringBuffer string = new StringBuffer();

  public int linenb = 1;

  private Parser parser;

  public Yylex(java.io.Reader r, Parser parser) {
    this(r);
    this.parser = parser;
  }
%}


%byaccj

nb=[0-9]+
id=[a-zA-Z][a-zA-Z0-9:_]*

%%

\#[ ]*@input {return parser.INPUT;}
\#[ ]*@output {return parser.OUTPUT;}
\#[ ]*@initial {return parser.INITIAL;}

--- {return parser.CONTEXT;}

\# (([^@\r\n]*) | ((([^ \r\n]+[ ]+ [^ \r\n]*) | ([^ \r\n]*[ ]+ [^ \r\n]+)) @.*)) (\r|\n|\r\n) { linenb++;}

{nb} {parser.yylval = new Pval(Integer.parseInt(yytext())); return parser.NB;}

\, {return parser.COMMA;}

\-\> {return parser.ARROW;}

\| {return parser.BAR;}

\. {return parser.DOT;}

\+ {return parser.PLUS;}

{id}(\_\{[a-zA-Z0-9]+\})? { parser.yylval = new Pval(yytext()); return parser.ID;}

\r\n| \r | \n {linenb++; return parser.NL;}
[ ] | \t {}
//<<EOF>> {return parser.EOF;}



