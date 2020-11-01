# RStoVerilog
`RStoVerilog` is a Reaction systems to Verilog translator.  Given the
description of the reactions of a reaction system and a context sequence
in the `brsim` format [(see the description here)](https://github.com/scolobb/brsim/), 
`RStoVerilog` can produce a (synthesizable) Verilog translation of the corresponding 
reaction system and the test bench that contains the timed update of the input
symbols using the provided context. Optionally, the graph describing the 
corresponding Moore machine in GraphViz format can be generated.

`RStoVerilog` is distributed under the terms of apache-2.0 license.

[(See)](https://github.com/scolobb/brsim/) and [1] for more details on Reaction Systems.

A description of used algorithms is given in [2].


```
     Usage: java -jar RStoVerilog.jar [<filename>] [options]
     If filename is not specified, then standard input is used and no options are allowed     
     The following options are available:
        -o  : generate output verilog and graph files (otherwise the result is sent to stdout)
        -g  : generate the accessible state graph
        -ga : generate the full state graph 
```

## Enhancements to the file format

There are additional directives in the input file format:

```
# @input list
# @output list
# @initial list

e.g.

# @input a b c
# @output x y z
# @initial a x
```

They allow to manually specify input, output and initial symbols. If these directives
do not exist, an automatic identification is performed, see [2] for details. We remark
that the directives are inside comments, so they are normally ignored by `brsim`.

To comment out a directive use any character between `#` and `@`:
```
#- @input a b c
```

## References

[1] Robert Brijder, Andrzej Ehrenfeucht, Michael G. Main, Grzegorz
Rozenberg.  *A Tour of Reaction Systems*.
Int. J. Found. Comput. Sci., vol 22 (7), 2011,
pp. 1499--1517. [DOI](http://dx.doi.org/10.1142/S0129054111008842).

[2] Zeyi Shang, Sergey Verlan, Ion Petre, Gexiang Zhang, 
*Reaction Systems and Synchronous Digital Circuits*,  Molecules 2019, 24(10), 1961; [DOI](https://doi.org/10.3390/molecules24101961).
