package sgnuplot

import Util._

object Gnuplot {
  /** creates file in /tmp/, with provided lines in it */
  def create_file(lines: Seq[String]):String = {
    val fileName = "/tmp/gnuplot_%d.sgnuplot" format (math.random * 100000000 toInt)
    lines ##> new java.io.File(fileName)
    fileName
  }

  /** executes the script using gnuplot */
  def execute_script(fileName:String) = {
    import sys.process._;
    Seq("gnuplot", "-p", fileName) !
  }

  /** cleans up the files in /tmp */
  def clean_up {
    import sys.process._;
    Seq("bash", "-c", "rm /tmp/gnuplot_*") !
  }

  case class Line(title:String = "",
                  _with:String = "lines",
                  using:String = "",
                  style:String = "",
                  smooth:String = ""
                  )
  { line =>
    def title(s:String):Line = line.copy(title = s)
    def With(s:String):Line = line.copy(_with = s)
    def using(s:String):Line = line.copy(using = s)
    def style(s:String):Line = line.copy(style = s)
    def smooth(s:String):Line = line.copy(smooth = s)
    override def toString = "%s title '%s' %s with %s %s" format (using.iff("using " + using), title, smooth.iff("smooth " + smooth), _with, style)
  }
  
  case class Plot(data:Seq[Seq[Double]] = Seq(),
                  title:String = "",
                  lines:Seq[Line] = Seq(),
                  xlabel:String = "",
                  ylabel:String = "",
                  grid:Boolean = true,
                  offsets:String = "graph 0.1, graph 0.1, graph 0.1, graph 0.1",
                  xrange: String = "",
                  yrange: String = "",
                  custom:Seq[String] = Seq()
                  )
  { plot =>
    def data(dat:Seq[Seq[Double]]):Plot = plot.copy(data = dat)
    def dataInt(dat:Seq[Seq[Int]]):Plot = plot.copy(data = dat.map(_.map(_.toDouble)))
    def dataIntLine(dat:Seq[Int]):Plot = plot.copy(data = dat.map(d => Seq(d.toDouble)), lines = Seq((new Line).With("lines")))
    def dataLine(dat:Seq[Double]):Plot = plot.copy(data = dat.map(d => Seq(d)), lines = Seq((new Line).With("lines")))
    def title(s:String):Plot = plot.copy(title = s)
    def xlabel(s:String):Plot = plot.copy(xlabel = s)
    def ylabel(s:String):Plot = plot.copy(ylabel = s)
    def gridOn:Plot = plot.copy(grid = true)
    def gridOff:Plot = plot.copy(grid = false)
    def offsets(s:String):Plot = plot.copy(offsets = s)
    def xrange(s:String):Plot = plot.copy(xrange = s)
    import collection.immutable.Range
    def xrange(r:Range):Plot = plot.copy(xrange = "[%d:%d]" format (r.head, r.last))
    def yrange(s:String):Plot = plot.copy(yrange = s)
    def yrange(r:Range):Plot = plot.copy(yrange = "[%d:%d]" format (r.head, r.last))
    def line(fn: Line => Line):Plot = plot.copy(lines = lines :+ fn(new Line))
    def custom(s:Seq[String]):Plot = plot.copy(custom = s)
    /** Simply adds provided line to the end of the script, right before "plot" call */
    def custom(s:String):Plot = plot.copy(custom = custom :+ s)
    def plot {
      val dataFile = create_file(data.map(_.mkString("  ")))
      val scriptFile = create_file(
        Seq(
          "set offsets %s" format offsets,
          "set title '%s'" format title,
          "set xlabel '%s'" format xlabel,
          "set ylabel '%s'" format ylabel,
          (if (grid) "set grid" else "unset grid"),
          xrange iff ("set xr %s" format xrange),
          yrange iff ("set yr %s" format yrange)
        ) ++ custom ++
        Seq(("plot " + lines.map("'%1$s' " + _.toString).mkString(", ")) format dataFile)
      )
      execute_script(scriptFile)
      clean_up
    } 
  }
  def newPlot = new Plot 
  
}
