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

package rs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import parser.Symbol;

/**
 *
 * A class representing a sequential switching circuit in DNF form
 * Contains also a method for reaction systems to DNF switching circuit transformation 
 * as well as Verilog transformers.
 * 
 * @author Sergey Verlan
 */
public class RsDnf {

    ArrayList<Conjunction> disjunctions = new ArrayList<>();

    public void add(Conjunction c) {
        disjunctions.add(c);
    }

    @Override
    public String toString() {
       StringBuilder s = new StringBuilder();
       disjunctions.forEach(d->s.append(d).append(" | "));
       if (s.length()>0) s.delete(s.length()-2, s.length());
       return s.toString();
    }

    /**
     * Performs a Verilog translation
     * @return a string corresponding to the Verilog translation of the system
     */    
    public String toVerilog(){
        return toString();
    }
    
    /**
     * Evaluates the Boolean function according to the environment values
     * @param values the environment
     * @return the evaluation of the function
     */
    public boolean eval(Map<Symbol,Boolean> values){
        return disjunctions.stream().anyMatch(e->e.eval(values));
    }

    /**
     * Builds a Boolean circuit in DNF from a reaction system
     * @param rules the rules of the reaction system
     * @return the Boolean circuit in DNF
     */
    public static Map<Symbol, RsDnf> build(List<RsRule> rules) {
        LinkedHashMap<Symbol, RsDnf> map = new LinkedHashMap<>();
        for (RsRule r : rules) {
            var c = new Conjunction();
            
            r.getReactants().forEach(r1 -> c.add(new Atom(r1)));
            r.getInhibitors().forEach(i -> c.add(new Atom(i, true)));
            for (Symbol p : r.getProducts()) {
                if (!map.containsKey(p)) {
                    map.put(p, new RsDnf());
                }
                map.get(p).add(c);
            }
        }
        return map;
    }
    
    /**
     * Translates a Boolean circuit to Verilog
     * @param system the circuit
     * @return Verilog translation as String
     */
    public static String toVerilog(Map<Symbol,RsDnf> system){
        StringBuilder s = new StringBuilder();
        
        s.append("module RS(\n");
        for(Symbol e : system.keySet()){
            if (e.isInput()) s.append(" input ").append(e).append(",\n");
        }
        for(Symbol e : system.keySet()){
            if (e.isOutput()) s.append(" output ").append(e).append(",\n");
        }
             
        s.append(" input clk\n)\n");
        
        for(Symbol e : system.keySet()){
            if(!e.isInput())
              s.append("reg ").append(e).append(" = 0;\n");
        }
        
        s.append("\nalways @(posedge clk) begin\n");
        for(Symbol e : system.keySet()){
            s.append("  ").append(e).append(" = ").append(system.get(e).toVerilog()).append(";\n");
        }
        s.append("end\n");
        return s.toString();
    }

}

/**
 * An inner class representing a conjunction
 * 
 */
class Conjunction {

    ArrayList<Atom> conjunctions = new ArrayList<>();

    public void add(Atom a) {
        conjunctions.add(a);
    }

    @Override
    public String toString() {
//        return conjunctions.toString();
       StringBuilder s = new StringBuilder();
       conjunctions.forEach(c->s.append(c).append(" & "));
       if (s.length()>0) s.delete(s.length()-2, s.length());
       return s.toString();
    }
    
    public String toVerilog(){
        return toString();
    }
    
    public boolean eval(Map<Symbol,Boolean> values){
        return conjunctions.stream().allMatch(e->e.eval(values));
    }
    
}


/**
 * An inner class representing formula atoms
 * 
 */
class Atom {

    Symbol symbol;
    boolean negated;
    boolean value= false;

    public Atom(Symbol symbol, boolean negated) {
        this.symbol = symbol;
        this.negated = negated;
    }

    public Atom(Symbol symbol) {
        this.symbol = symbol;
        this.negated = false;
    }
    
    public void setValue(boolean value){
        this.value = value;
    }

    @Override
    public String toString() {
        if (negated) {
            return "!" + symbol;
        } else {
            return symbol.toString();
        }
    }
    
    public String toVerilog(){
        return toString();
    }
    
    public boolean eval(Map<Symbol,Boolean> values){
       // return value ^ negated ;
       if (!values.containsKey(symbol))
           throw new RuntimeException("Value for "+symbol+" does not exist. Maybe forgotten input declaration");
       return values.get(symbol) ^ negated;
    }
}
