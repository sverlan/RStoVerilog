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

import java.util.HashMap;

/**
 * A simple environment for parser symbols
 * 
 */
public class Environnement {
    private HashMap<String, Symbol> data = new HashMap<String,Symbol>();

    public Environnement(){
    }

    public boolean exists(String name){
        return data.containsKey(name);
    }

    public Symbol getSym(String name){
        if (exists(name)) return data.get(name);
        else return putSym(name);
    }

    public Symbol putSym(String name){
        Symbol v = new Symbol(name);
        data.put(name, v);
        return v;
    }

}
