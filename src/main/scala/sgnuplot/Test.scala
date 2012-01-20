object Test {
  def main(args:Array[String]) {
    
    Gnuplot.newPlot
      .dataIntLine(Seq(1,2,3,4))
      .plot

    val data = (0 to 5) map (i => Seq(i, i*i))
    Gnuplot.newPlot
      .dataInt(data)
      .gridOn
      .title("Square function")
      .xlabel("X")
      .ylabel("Y")
      .line(_.title("x^2").With("lines").smooth("bezier").using("1:2"))
      .plot
  
 /*   
    val data = (0 to 5) map (_.toDouble) map (i => Seq(i, i*i))
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
      .plot*/
    println("Done.")
  }
}
