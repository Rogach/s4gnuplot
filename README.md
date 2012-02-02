s4gnuplot
=========

Simple scala wrapper for Gnuplot, popular plotting utility.

# Requirements:
* gnuplot installed and on your path
* sbt 0.11.2+
* /tmp with write access somewhere on your system :)

Tested with gnuplot 4.4

# Usage:

I do not upload pre-built jars since the project is so small :)
But you can just copy-paste s4gnuplot source files into your project, or you can use a git dependency in your sbt's Build.scala:

    lazy val root = Project(id = "hello", base = file(".")) dependsOn
                    (RootProject( uri("git://github.com/Rogach/s4gnuplot")))

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
    