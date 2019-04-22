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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;
import parser.Symbol;

/**
 *
 * A class representing a reaction system
 * @author Sergey Verlan
 */
public class RsSystem {

    List<RsRule> rules = new ArrayList<>();
    Set<Symbol> inputSymbols = null;
    Set<Symbol> outputSymbols = null;
    Set<Symbol> initialSymbols = null;
    Set<Symbol> allContextSymbols = null;
    Set<Symbol> allLhsSymbols = null;
    Set<Symbol> allRhsSymbols = null;

    Set<Symbol> overlapSymbols = null;

    String name = "RS";

    List<List<Symbol>> context = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List<Symbol>> getContext() {
        return context;
    }

    public void setContext(List<List<Symbol>> context) {
        this.context = context;
    }

    public List<RsRule> getRules() {
        return rules;
    }

    public Set<Symbol> getInputSymbols() {
        if (inputSymbols == null) {
            inputSymbols = new TreeSet<>();
            inputSymbols.addAll(getAllLhsSymbols());
            inputSymbols.removeAll(getAllRhsSymbols());
            inputSymbols.addAll(getAllContextSymbols());
        }
        return inputSymbols;
    }

    public Set<Symbol> getOutputSymbols() {
        if (outputSymbols == null) {
            outputSymbols = new TreeSet<>();
            outputSymbols.addAll(getAllRhsSymbols());
        }
        return outputSymbols;
    }

    public void addRule(RsRule r) {
        rules.add(r);
    }

    public void addInitialSymbol(Symbol s) {
        if (initialSymbols == null) {
            initialSymbols = new TreeSet<>();
        }
        initialSymbols.add(s);
    }

    public void addInputSymbol(Symbol s) {
        if (inputSymbols == null) {
            inputSymbols = new TreeSet<>();
        }
        inputSymbols.add(s);
    }

    public void addOutputSymbol(Symbol s) {
        if (outputSymbols == null) {
            outputSymbols = new TreeSet<>();
        }
        outputSymbols.add(s);
    }

    private void updateInitial() {
        if (initialSymbols == null) {
            initialSymbols = new TreeSet<>();
            if (context != null && context.size()>0) {
                initialSymbols.addAll(context.get(0));
//            context.set(0, new ArrayList<>());
                context.remove(0);
            }
        }
    }

    private Set<Symbol> getAllContextSymbols() {
        if (allContextSymbols == null) {
            allContextSymbols = new TreeSet<>();
            if (context != null) {
                context.forEach((c) -> {
                    allContextSymbols.addAll(c);
                });
            }
        }
        return allContextSymbols;
    }

    private Set<Symbol> getAllRhsSymbols() {
        if (allRhsSymbols == null) {
            allRhsSymbols = new TreeSet<>();
            if (rules != null) {
                rules.forEach((r) -> {
                    allRhsSymbols.addAll(r.getProducts());
                });
            }
        }
        return allRhsSymbols;
    }

    private Set<Symbol> getAllLhsSymbols() {
        if (allLhsSymbols == null) {
            allLhsSymbols = new TreeSet<>();
            if (rules != null) {
                rules.forEach((r) -> {
                    allLhsSymbols.addAll(r.getReactants());
                    allLhsSymbols.addAll(r.getInhibitors());
                });
            }
        }
        return allLhsSymbols;
    }

    public Set<Symbol> getOverlapSymbols() {
        if (overlapSymbols == null) {
            overlapSymbols = new TreeSet<>();
            overlapSymbols.addAll(getInputSymbols());
            overlapSymbols.retainAll(getOutputSymbols());
        }
        return overlapSymbols;
    }

    public static Set<Set<Symbol>> powerSet(Set<Symbol> originalSet) {
        Set<Set<Symbol>> sets = new HashSet<Set<Symbol>>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<Symbol>());
            return sets;
        }
        List<Symbol> list = new ArrayList<Symbol>(originalSet);
        Symbol head = list.get(0);
        Set<Symbol> rest = new HashSet<Symbol>(list.subList(1, list.size()));
        for (Set<Symbol> set : powerSet(rest)) {
            Set<Symbol> newSet = new HashSet<Symbol>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

    private void updateOverlap() {
        Map<String, Symbol> translate = new HashMap<>();
        var overlap = getOverlapSymbols();
        for (var s : overlap) {
            var newS = new Symbol(s.toString() + "_i");
            translate.put(s.toString(), newS);
            // Replace in input 
            if (inputSymbols.remove(s)) {
                inputSymbols.add(newS);
            }
            // replace in context
            if (context != null) {
                for (var c : context) {
                    if (c.remove(s)) {
                        c.add(newS);
                    }
                }
            }
        }
        // replace in rules
        List<RsRule> newRules = new ArrayList<>();
        for (var r : rules) {
            var rlist = new TreeSet<>(r.getReactants());
            rlist.retainAll(overlap);
            var ilist = new TreeSet<>(r.getInhibitors());
            ilist.retainAll(overlap);
            for (var s1 : powerSet(rlist)) {
                var pnew = new ArrayList<>(r.getProducts());
                var rnew = new ArrayList<>(r.getReactants());
                if (!s1.isEmpty()) {
                    for (var l : s1) {
                        if (rnew.remove(l)) {
                            rnew.add(translate.get(l.toString()));
                        }
                    }
                }
                for (var s2 : powerSet(ilist)) {
                    var inew = new ArrayList<>(r.getInhibitors());
                    if (!s2.isEmpty()) {
                        for (var l : s2) {
                            if (inew.remove(l)) {
                                inew.add(translate.get(l.toString()));
                            }
                        }
                    }
                    if (!(s1.isEmpty() && s2.isEmpty())) {
                        RsRule rr = new RsRule(rnew, inew, pnew);
                        newRules.add(rr);
                    }

                }

            }
        }
        rules.addAll(newRules);
        updateSymbolTypes();
    }

    private void updateSymbolTypes(){
        inputSymbols.forEach(s->s.setInput(true));
        outputSymbols.forEach(s->{
            // there might be some symbols that were initially marked as input
            s.setInput(false);
            s.setOutput(true);
                });
    }
    
    
    // todo: add templates
    public String toVerilog() {

        // check if first line of context is the initial configuration
        updateInitial();

        // Resolve overlapping symbols
        updateOverlap();

        var system = RsDnf.build(rules);

        StringBuilder s = new StringBuilder();

        s.append("module ").append(name).append("(\n");
        outputSymbols.forEach(e -> s.append(" output reg ").append(e).append(" = ").append(initialSymbols.contains(e) ? "1" : "0").append(",\n"));
        inputSymbols.forEach(e -> s.append(" input ").append(e).append(",\n"));

        s.append(" input clk\n);\n");

        s.append("\nalways @(posedge clk) begin\n");
        system.keySet().forEach((e) -> {
            s.append("  ").append(e).append(" <= ").append(system.get(e).toVerilog()).append(";\n");
        });
        s.append("end\nendmodule\n");

        return s.toString();
    }

    public String toVerilogTb() {
        // check if first line of context is the initial configuration
        updateInitial();
        // We suppose that the overlapping symbols are resolved (e.g. by a previos toVerilog call).

        var system = RsDnf.build(rules);
        
        // testbench generation
        StringBuilder ss = new StringBuilder();

        ss.append("module ").append(name).append("_tb( );\n");

        ss.append("reg clk=0;\n");
        ss.append("initial forever #10 clk=~clk;\n");

        outputSymbols.forEach(e -> ss.append("wire ").append(e).append(";\n"));
        inputSymbols.forEach(e -> ss.append("reg ").append(e).append(" = ")
                .append(initialSymbols.contains(e) ? "1" : "0").append(";\n"));

        ss.append(name).append(" ").append(name).append("_inst (");
        outputSymbols.forEach(
                e -> ss.append(".").append(e).append("(").append(e).append("),")
        );
        inputSymbols.forEach(
                e -> ss.append(".").append(e).append("(").append(e).append("),")
        );

        ss.append(".clk(clk));\n");

        ss.append("initial begin\n");
        ss.append(" $dumpfile(\"").append(name).append(".vcd\");\n");
        ss.append(" $dumpvars;\n");
        int step = 1;
        ss.append("// Step: ").append(step).append("\n");

        // The input values are updated at each 9th tick
        ss.append("#9").append(" ;\n");
        // After step 1 we should reset initial signals 
        // Remember: Step 1 is special, as it evolves only initialValues, the first context is not taken
        // Hence initialValues contain both the internal state and the initil input data
        ss.append("#20").append(" ;\n");
        inputSymbols.forEach(
                e -> {
                    if (initialSymbols.contains(e)) {
                        ss.append(e).append(" = 0;\n");
                    }
                }
        );

        if (context != null) {
            for (var list : context) {
                step++;
                ss.append("// Step: ").append(step).append("\n");
                list.forEach(e -> ss.append(e).append(" = 1;\n"));
                ss.append("#20 ;\n");
                list.forEach(e -> ss.append(e).append(" = 0;\n"));
            }
        }
        ss.append("$finish;\n");
        ss.append("end\nendmodule\n\n\n");

        return ss.toString();
    }

    /**
     * Helper function for Moore automata generation
     * @param state
     * @return 
     */
    private static int encodeState(boolean[] state) {
        int res = 0;
        for (int i = 0; i < state.length; i++) {
            res = res + (state[i] ? 1 << i : 0);
        }
        return res;
    }

    /**
     * Helper function for Moore automata generation
     * @param i
     * @param state 
     */
    private static void decodeState(int i, boolean[] state) {
        for (int k = 0; k < state.length; k++) {
            state[k] = false;
        }
        int j = 0;
        while (i > 0) {
            state[j] = i % 2 == 1;
            i = i >> 1;
            j++;
        }
    }

    /**
     * Pads a number in string s to the width
     * @param s
     * @param width
     * @return 
     */
    private static String zeroPad(String s, int width) {
        String s1 = s;
        for (int i = 0; i < width - s.length(); i++) {
            s1 = "0" + s1;
        }
        return s1;
    }

    /**
     * The method produces a Moore automaton graph.
     * <br>
     * It constructs only reachable configurations.
     *
     * @return Graph
     */
    public Graph toMoore() {
        return toMoore(false);
    }

    /**
     * The method produces a Moore automaton graph.
     * <br>
     * It constructs all configurations.
     *
     * @return Graph
     */
    public Graph toMoore(boolean generateAllStates) {
        var system = RsDnf.build(rules);
        int width = system.keySet().size();
        // limit to 32 bits
        if (width > 32) {
            throw new RuntimeException("Too many variables for graph generation");
        }
        int size = 1 << width;
        var g = new Graph(size);
        var symbols = new ArrayList<>(system.keySet());
        var isymbols = new ArrayList<>(inputSymbols);
        Collections.reverse(symbols);
        Collections.reverse(isymbols);
        g.setSymbolLegend(symbols.toString());
        g.setEdgeLegend(isymbols.toString());
        var s = new boolean[width];
        var s2 = new boolean[width];
        var marked = new boolean[size];
        for (int i = 0; i < size; i++) {
            marked[i] = generateAllStates ? true : false;
        }
        var control = new LinkedList<Integer>();

        int k = 0;
        for (Symbol x : system.keySet()) {
            if (initialSymbols.contains(x)) {
                s[k] = true;
            }
            k++;
        }
        int iState = encodeState(s);
        if (initialSymbols.size() > 0) {
            g.setInitialNode(iState);
        }
        marked[iState] = true;
        control.add(iState);
        if (generateAllStates) {
            IntStream.range(0, size).forEach(control::add);
        }
        int input_width = inputSymbols.size();
        int input_size = 1 << input_width;
        var is = new boolean[input_width];
        var map = new HashMap<Symbol, Boolean>();

        // breadth-first search of possibilities
        while (!control.isEmpty()) {
            var state = control.poll();
            decodeState(state, s);
            int j = 0;
            for (Symbol x : system.keySet()) {
                map.put(x, s[j]);
                j++;
            }
            for (int i = 0; i < input_size; i++) {
                decodeState(i, is);
                j = 0;
                for (Symbol x : inputSymbols) {
                    map.put(x, is[j]);
                    j++;
                }
                j = 0;
                for (Symbol x : system.keySet()) {
                    s2[j] = system.get(x).eval(map);
                    j++;
                }
                int nextState = encodeState(s2);

                g.setEdge(state, nextState, zeroPad(Integer.toBinaryString(encodeState(is)), input_width));
                if (!marked[nextState]) {
                    marked[nextState] = true;
                    control.add(nextState);
                }

            }
        }

        for (int i = 0; i < size; i++) {
            g.setNodeLabel(i, zeroPad(Integer.toBinaryString(i), width));
        }

        return g;
    }

    @Override
    public String toString() {
        return rules.toString();
    }

}
