1) Locate your ojdbc6.jar, and install it into maven as follows:
mvn install:install-file -Dfile=<path to ojdbc6.jar> -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=<version, which can be found in its MANIFEST.MF> -Dpackaging=jar

2) Search the project for "HERE" to locate tokens that will need to be replaced with database credentials, etc.

3) Copy the log output from the conversion job to ./src/main/resources/,
Then modify App.java line 37, and compile.sh line 2 as appropriate (the converion job's log file's name)

4) Compile using:
./compile.sh

5) Execute using:
./execute.sh

Pro tip! Cayenne produces a lot of noise; direct your output to a file for clean output:
./execute.sh > temp.out
