# graphdb-custom-analyzer

The project demonstrates has an complex custom analyzer implementation.

1 Checkout the project
   
git clone https://github.com/Nujjy/graphdb-custom-analyzer.git

2 Build the jar using gradle.

Note: I am using IntelliJ and using the following steps to build the project.

a. Open gradle.build

b. Select Open as Project

c. Check use default gradle wrapper.

3 Import Changes

4 Run `$gradle jar`

5 Copy jar to 

`$GDB_HOME/lib/plugins/lucene-connector`

6 Restart GraphDB

7 Create the Lucene Connector (Point to CustomAnalyzer2)