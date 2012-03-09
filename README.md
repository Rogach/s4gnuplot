s4gnuplot
=========

Simple scala wrapper for Gnuplot, popular plotting utility.

# Requirements:
* gnuplot installed and on your path
* sbt 0.11.2+
* /tmp with write access somewhere on your system :)

Tested with gnuplot 4.4

# Usage:

Add the following lines to your build.sbt:

    resolvers += "s4gnuplot gihub maven repo" at "https://github.com/Rogach/org.rogach/raw/master/"

    libraryDependencies += "org.rogach" % "s4gnuplot" % "0.2"

Or you can just manually download the project jar from https://github.com/Rogach/org.rogach/raw/master/org/rogach/s4gnuplot/0.2/s4gnuplot-0.2.jar

# Samples:

    import s4gnuplot.Gnuplot
        
    // simple plot - just a straight line
    Gnuplot.newPlot
      .dataIntLine(Seq(1,2,3,4))
      .plot
    
    // a bit more complex plot - a square function
    val data = (0 to 5) map (i => Seq(i, i*i))
    Gnuplot.newPlot
      .dataInt(data)
      .gridOn
      .title("Square function")
      .xlabel("X")
      .ylabel("Y")
      .line(_.title("x^2").With("lines").smooth("bezier").using("1:2"))
      .plot
    