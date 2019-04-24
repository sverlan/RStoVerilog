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

/**
 *
 * Parser symbol representation
 */
public class Symbol implements Comparable<Symbol>{
    String name;
    boolean input=false;
    boolean output=false;

    public Symbol(String name){
        this(name,false,false);
    }

    public Symbol(String name, boolean input, boolean output){
        this.name=name.replace(':', '_');
        this.input = input;
        this.output = output;
    }

    
    public String toString(){
        return name;
    }

    public boolean isInput() {
        return input;
    }

    public void setInput(boolean input) {
        this.input = input;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    @Override
    public int compareTo(Symbol s) {
        return this.name.compareTo(s.name);
    }

}
