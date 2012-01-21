package sgnuplot

object Test {
  def main(args:Array[String]) {
    import sgnuplot.Gnuplot
    Gnuplot.newPlot
      .dataInt(
        (1 to 5).map { i => Seq(i, i + 4, i + 3 - (math.random * 3 toInt), i + 7 + (math.random * 5 toInt)) }
      )
      .title("Hello")
      .xrange(0 to 6)
      .xlabel("Grass")
      .ylabel("Rabbits")
      .gridOn
      .custom("set title 'Rabbits & grass'")
      .line(_.title("std. err").With("yerrorbars").using("1:2:3:4").style("ps 0.1"))
      .line(_.title("ordinary rabbit").With("linespoints").using("1:2").style("pt 7 ps 0.8"))
      .plot
      
    println("Done.")
  }
}
