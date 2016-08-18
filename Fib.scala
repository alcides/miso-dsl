class FibState extends MisoState {
	
	type T <: FibState
	
	// There can be fields in one state
	var fib:Int = 1;
	var it:Int = 1;
	
	def transition {
		fib = if (it <= 2) 1 else (this%0).fib + (this%1).fib
		it = it + 1
	}
}

object Fib {
	val number = new Cell[FibState](1, historySize=2)
	def main(args:Array[String]) {
		//MisoRuntime.runSeq(10, number)
		MisoRuntime.runPar(5, number)

		println(number(0).fib)
	}
}

