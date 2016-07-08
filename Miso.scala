import scala.reflect._


class Cell[Mem](val number:Int, val transition: Mem => Unit)(implicit m: ClassTag[Mem]) {
  
  val instances:Array[Mem] = new Array[Mem](number);
  
  for (i <- 0 to number-1) {
	  instances(i) = m.runtimeClass.newInstance.asInstanceOf[Mem];
  }
  
  def apply(i:Integer):Mem = {
	  instances(i);
  }
}


trait CellMem[C <: CellMem[C]] {
	def transition(c:C);
}

object MisoRuntime {
	def run(it:Int, os:Cell[_]*) {
		for ( o <- os) {
			println(o)
		}
	}
}