rm -rf *.class
scalac  -deprecation *.scala
scala Fib
rm -rf *.class