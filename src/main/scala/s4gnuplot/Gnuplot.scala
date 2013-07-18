package s4gnuplot

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
  def clean_up() {
    import sys.process._;
    Seq("bash", "-c", "rm /tmp/gnuplot_*") !
  }

  /** Execute gnuplot on provided plot object. */
  def plot(plot:Plot) {
    import plot._;
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

  /** Creates a default plot builder */
  def newPlot = new Plot

}
