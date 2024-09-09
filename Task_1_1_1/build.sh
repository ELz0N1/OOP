#!/usr/bin/env sh

source=src/main/java/ru/nsu/HeapSort.java

javac -d out $source
javadoc -d out/docs $source
java -classpath out ru.nsu.HeapSort
