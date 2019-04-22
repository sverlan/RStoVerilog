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

import java.util.List;
import parser.Symbol;

/**
 * A class representing a Reaction Systems rule
 * 
 * @author Sergey Verlan
 */
public class RsRule {
    private List<Symbol> reactants;
    private List<Symbol> inhibitors;
    private List<Symbol> products;

    public RsRule(List<Symbol> reactants, List<Symbol> inhibitors, List<Symbol> products) {
        this.reactants = reactants;
        this.inhibitors = inhibitors;
        this.products = products;
    }

    public List<Symbol> getReactants() {
        return reactants;
    }

    public void setReactants(List<Symbol> reactants) {
        this.reactants = reactants;
    }

    public List<Symbol> getInhibitors() {
        return inhibitors;
    }

    public void setInhibitors(List<Symbol> inhibitors) {
        this.inhibitors = inhibitors;
    }

    public List<Symbol> getProducts() {
        return products;
    }

    public void setProducts(List<Symbol> products) {
        this.products = products;
    }
    
    @Override
    public String toString(){
        String s = " { ";
        for(Symbol r: reactants)
            s = s + " " + r;
        s = s + ", ";
        for(Symbol i: inhibitors)
            s = s + " " + i;
        s = s + ", ";
        for(Symbol p: products)
            s = s + " " + p;
        s = s + " } ";
        return s;
    }
    
}
