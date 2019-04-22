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
import java.util.List;

/**
 *
 * @author Sergey Verlan
 *
 * A simple class for graph representation using adjacency matrix.
 *
 */
public class Graph {

    List<String>[][] aMatrix;
    String[] nodeLabels;
    String symbolLegend;
    String edgeLegend;
    int initialNode = -1;

   /**
    * Creates a new instance of a graph 
    * @param n size of the graph
    */
    
   public Graph(int n) {
        this.aMatrix = new List[n][n];
        this.nodeLabels = new String[n];
        for (int i = 0; i < nodeLabels.length; i++) {
            nodeLabels[i] = Integer.toString(i);
        }
    }

   /**
    * Adds an edge between node i and j
    * @param i source node
    * @param j destination node
    */
    public void setEdge(int i, int j) {
        if (aMatrix[i][j] == null) {
            aMatrix[i][j] = new ArrayList<>();
        }
    }

    /**
     * Adds an edge between node i and j
     * This method can be applied several times adding several labels
     * @param i source node
     * @param j destination node
     * @param label label of the edge
     */
    public void setEdge(int i, int j, String label) {
        if (aMatrix[i][j] == null) {
            aMatrix[i][j] = new ArrayList<>();
        }
        aMatrix[i][j].add(label);
    }

    /**
     * Edge getter
     * @param i
     * @param j
     * @return all edge labels or null if there is no edge 
     */
    public List<String> getEdgeLabel(int i, int j) {
        return aMatrix[i][j];
    }

    public boolean hasEdge(int i, int j) {
        return aMatrix[i][j] != null;
    }

    public void setNodeLabel(int i, String label) {
        nodeLabels[i] = label;
    }

    public String getNodeLabel(int i) {
        return nodeLabels[i];
    }
    
    /**
     * Legend for GraphViz generation
     * @param symbolLegend 
     */
    public void setSymbolLegend(String symbolLegend) {
        this.symbolLegend = symbolLegend;
    }

    /**
     * Sets edge legend for GraphViz generation
     * @param edgeLegend 
     */
    public void setEdgeLegend(String edgeLegend) {
        this.edgeLegend = edgeLegend;
    }

    public void setInitialNode(int initialNode) {
        this.initialNode = initialNode;
    }

 
    /**
     * Transforms the graph to a GraphViz representation
     * @return the dot code of the graph
     */
    public String toDot() {
        String res = "digraph {\n  rankdir = LR;\n";
        res = res + "   label=\"Used state vector: " + symbolLegend + "\\nInputVector: " + edgeLegend + "\";\n labelloc = \"t\";\n";
        if (initialNode >= 0) {
            res = res + "  " + nodeLabels[initialNode] + "[shape=\"doublecircle\"];\n";
        }
        for (int i = 0; i < aMatrix.length; i++) {
            for (int j = 0; j < aMatrix.length; j++) {
                if (hasEdge(i, j)) {
                    res = res + "\n  " + nodeLabels[i] + " -> " + nodeLabels[j];
                    List<String> labels = getEdgeLabel(i, j);
                    String labelStr;
                    // check if we have some labels
                    if (labels.size() > 0) {
                        if (labels.size() == 1) {
                            labelStr = labels.get(0);
                        } else {
                            labelStr = labels.toString();
                        }
                        res = res + " [label=\"" + labelStr + "\"]";
                    }
                }
            }
        }
        res = res + "\n}\n";
        return res;
    }

}
