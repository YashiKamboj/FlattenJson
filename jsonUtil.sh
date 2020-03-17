#!/bin/bash
javac -cp lib/json-20190722.jar src/FlattenJsonBFS.java
java -cp lib/json-20190722.jar:src/. FlattenJsonBFS
