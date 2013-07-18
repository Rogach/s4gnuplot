package s4gnuplot

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
  def plot() {
    Gnuplot.plot(plot)
  }
}
