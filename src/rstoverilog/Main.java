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

package rstoverilog;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Path;
import parser.Parser;

/**
 *
 * @author Sergey Verlan
 */
public class Main {

    /**
     * The main function.
     * <pre>
     * Usage: java -jar RStoVerilog.jar [&lt;filename&gt;] [options]
     * If filename is not specified, then standard input is used and no options are allowed     
     * The following options are available:
     *    -o  : generate output verilog and graph files (otherwise the result is sent to stdout)
     *    -g  : generate the accessible state graph
     *    -ga : generate the full state graph 
     * 
     * The syntax of the input file is the same as for WEBRSIM:
     * http://combio.abo.fi/research/reaction-systems/reaction-system-simulator/
     *</pre>
     * 
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        /* Initializing the parser */
        Parser yyparser;
        if (args.length > 0) {
            yyparser = new Parser(new FileReader(args[0]));
        } else {
            yyparser = new Parser(new InputStreamReader(System.in));
        }
        // Uncomment next line to debug the parser
        // yyparser.setDebug(true);

        // Get the parsed system
        var system = yyparser.parse().rss;

        // For testing purposes
        //   System.out.println(system);
        //   var map = RsDnf.build(system);
        //   System.out.println(map);
        //   System.out.println(RsDnf.toVerilog(map));
        
        String fname = null;
        if (args.length > 0) {
            Path p = Path.of(args[0]);
            var full_fname = p.getFileName().toString().split("\\.");
            fname = full_fname[0];
            system.setName(fname);
        }

        boolean generateGraph = false;
        boolean generateGraphAll = false;
        boolean generateFiles = false;
/*
        
        Usage: java -jar RStoVerilog.jar [<filename>] [options]
        If filename is not specified, then standard input is used and no options are allowed
        The following options are available:
            -o  : generate output verilog and graph files (otherwise the result is sent to stdout)
            -g  : generate the accessible state graph
            -ga : generate the full state graph 
        
        The syntax of the input file is the same as for WEBRSIM:
        http://combio.abo.fi/research/reaction-systems/reaction-system-simulator/
        
 */
        // Dealing with command line arguments
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "-ga":
                    generateGraphAll = true;
                    generateGraph = true;
                    break;
                case "-g":
                    generateGraph = true;
                    break;
                case "-o":
                    generateFiles = true;
                    break;
            }
        }

        Writer verilogOut = new PrintWriter(System.out);
        Writer verilogTbOut = new PrintWriter(System.out);
        Writer graphOut = new PrintWriter(System.out);

        if (generateFiles) {
            verilogOut = new FileWriter(fname + ".v");
            verilogTbOut = new FileWriter(fname + "_tb.v");
            if (generateGraph) {
                graphOut = new FileWriter(fname + ".dot");
            }
        }

        // Generate the Verilog output
        verilogOut.write(system.toVerilog());
        verilogOut.flush();
        verilogTbOut.write(system.toVerilogTb());
        verilogTbOut.flush();
        if (generateGraph) {
            graphOut.write(system.toMoore(generateGraphAll).toDot());
            graphOut.flush();
        }

    }

}
