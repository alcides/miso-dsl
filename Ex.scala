// Example usage of the Miso Language, implemented as a Scala DSL

class ExState extends MisoState {
	
	// There can be fields in one state
	var a:Int = 0;
	var b:Float = 0;
	
	// There has to be a transition function for one state to the other
	def transition {
		/*
		Field accesses default to the last state and are stored in the new state.
		Global acccesses to other state objects refer to the last state of them.
		All objects are synchronized.
		*/ 
		a = a - Main.objects(0).a + Main.others(9).a + 1;
	}
}

object Main {
	
	/*
	In the Main object we can instanciate different sets of Cell States.
	In this example we are creating two sets of ExState, one with 20 states
	called "objects", and another with just 10 called "others".
	These sets can be of different Cell State Types.
	*/
	val objects = new Cell[ExState](20)
	val others = new Cell[ExState](10)
	
	def main(args:Array[String]) {
		
		/*
		In the main function of the program, we can control the Runtime to
		perform state transitions. We define the number of iterations (10)
		and state all the sets that we want to include in the runtime.
		
		There are two runtime modes, one sequential and other in parallel.
		*/

		//MisoRuntime.runSeq(10, objects, others)
		MisoRuntime.runPar(10, objects, others)

		// Finally, we can access the state of any set of objects.
		println(others(1).a)
	}
}

