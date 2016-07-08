import scala.reflect._
import java.util.concurrent.CyclicBarrier
import scala.concurrent.Future
import scala.concurrent.forkjoin._


trait CellMem {
	def transition();
}

class Cell[Mem <: CellMem](val number:Int)(implicit m: ClassTag[Mem]) {
  
  var instances:Array[Mem] = new Array[Mem](number);
  
  for (i <- 0 to number-1) {
	  instances(i) = m.runtimeClass.newInstance.asInstanceOf[Mem];
  }
  
  def apply(i:Integer):Mem = {
	  instances(i);
  }
}

object MisoRuntime {


	def runPar(it:Int, os:Cell[_]*) {

		implicit val ec = scala.concurrent.ExecutionContext.global
		  	
		val total = os.map(_.instances.length).reduceLeft(_+_);
		
		//val barrier = new CyclicBarrier(total);
		
		for ( i <- 1 to it) {
		
			for ( o <- os) {
				//Future {
					val currentGen = o.instances.asInstanceOf[Array[CellMem]].clone
					currentGen.map { a => a.transition() }
				//	barrier.await();
				//}
			}
		}
	}	
	
	def runSeq(it:Int, os:Cell[_]*) {
		for ( i <- 1 to it) {
			for ( o <- os) {
				o.instances.asInstanceOf[Array[CellMem]].clone.map { a => a.transition() }
			}
		}
	}
}