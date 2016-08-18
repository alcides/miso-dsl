import scala.util.Random

class Individual extends MisoState {
	
	var fitness:Int = 0;
	var genotype:Array[Int] = Array(0, 0, 0, 0, 0, 0);

	def transition {
		if (Random.nextInt(100) < 60) {
			val mate = GA.population.instances.sortBy(-_.fitness).apply(Random.nextInt(5))
			for ( i <- 0 to Random.nextInt(7)) {
				genotype(i) = mate.genotype(i)
			}
		}
		if (Random.nextInt(100) < 5) {
			val p = Random.nextInt(7) 
			genotype(p) = (1 + genotype(p)) % 2
		}
		fitness = genotype.sum
	}
}

object GA {
	val population = new Cell[Individual](100)
	def main(args:Array[String]) {
		//MisoRuntime.runSeq(10000, objects, others)
		MisoRuntime.runPar(10000, population)
		population.instances.sortBy(-_.fitness).apply(0).genotype.foreach(print)
		println
	}
}

