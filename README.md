1) Locate your ojdbc7.jar, and install it into maven as follows:
mvn install:install-file -Dfile=<path to ojdbc7.jar> -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=<version, which can be found in its MANIFEST.MF> -Dpackaging=jar

2) If your odjbc7 version differs from pom.xml, update the ojdbc dependency in pom.xml accordingly

3) Search the project for "HERE" to locate tokens that will need to be replaced with database credentials, etc.

4) Copy the log output from the conversion job to ./src/main/resources/,
Then modify App.java line 47, and compile.sh line 2 as appropriate (the converion job's log file's name)

5) Compile using:
./compile.sh

6) Execute using:
./execute.sh

Pro tip! Cayenne produces a lot of noise; direct your output to a file for clean output:
./execute.sh > temp.out
