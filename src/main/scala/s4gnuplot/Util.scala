package s4gnuplot

import java.io._

object Util {
  class RicherFile(f: File) {
    def printHere(append:Boolean = true)(op: java.io.PrintWriter => Unit) {
      if (!f.exists) {
        f.createNewFile
      }
      val p = new java.io.PrintWriter(new OutputStreamWriter(new FileOutputStream(f, append), "UTF-8"))
      try { op(p) } finally { p.close() }
    }
    /** So the caller can omit the parenthesis after "printHere" */
    def printHere(op: java.io.PrintWriter => Unit) {
      printHere(append = false)(op)
    }
  }
  implicit def wrapperFile(f: File):RicherFile = new RicherFile(f)


  class PimpedStringSeq(strings:Seq[String]) {
    def ##>(f:File) {
      f.printHere(append = false) { p =>
        strings.foreach(s => p.println(s))
      }
    }
    def ##>>(f:File) {
      f.printHere(append = true) { p =>
        strings.foreach(s => p.println(s))
      }
    }
    def ##>(s:String) {
      ##>(new File(s)) 
    }
    def ##>>(s:String) {
      ##>>(new File(s))
    }
  }
  implicit def toPimpedStringSeq(strings:Seq[String]) = new PimpedStringSeq(strings)

  class PimpedString(s:String) {
    // returns result of the function is s is empty
    def or(fn: => String) = if (s.trim.size > 0) s else fn
    // returns result of the function if s is non-empty
    def iff(fn: => String) = if (s.trim.size > 0) fn else s
  }
  implicit def toPimpedString(s:String) = new PimpedString(s)

}
