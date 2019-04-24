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

import java.util.List;

import rs.RsRule;
import rs.RsSystem;

/**
 *
 * Pval definition for ByaccJ
 */
public class Pval implements Cloneable{
   public int i;

   public Symbol v;

   public String s;

   public List<Symbol> li;

   public List<List<Symbol>> lli;
   
   public RsRule rs;
   
   public RsSystem rss;
   
    @Override
   public Pval clone(){
        try {
            return (Pval) super.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return null;
   }

   public Pval(){
       
   }

   public Pval(int i){
       this.i = i;
   }

   public Pval(Symbol v){
       this.v = v;
   }

   public Pval(String s){
       this.s=s;
   }


}
