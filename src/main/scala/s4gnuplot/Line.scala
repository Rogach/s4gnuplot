package s4gnuplot

import Util._

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
  

