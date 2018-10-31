1 To Run simulator:
----------------
1.1 From IDE:
 ------
Run the main class and pass fileLocation as args

MainClass = com.mylearning.SiteClearingSimulator

args = siteMap //or path to a local file

1.2 Using Java Executable jar:
 ----------------

To generate Jar ------> ./gradlew clean build Jar

Jar found @ /build/libs/construction-site-clearing-1.0-SNAPSHOT.jar

1.2.1.Commands:
-------------------------

./gradlew clean build Jar

cd build/libs

java -jar construction-site-clearing-1.0-SNAPSHOT.jar ../resources/main/siteMap.txt 

//siteMap in project Resources

or

java -jar construction-site-clearing-1.0-SNAPSHOT.jar ../resources/main/siteMap.txt 

//siteMap in project Resources


1.3 Using Gradle - 4.8:
 ------------------
./gradlew execute '-Pfile=siteMap' //for siteMap in project resources

./gradlew execute '-Pfile=Users/ABC/Downloads/sitetest-master/siteMap' //File on local System

./gradlew execute '-Pfile=Users/ABC/Downloads/sitetest-master/siteMap.txt' //Filenames can be with or without extension
                                                                           //default is .txt

2 To Run Tests:
-------------
./gradlew clean test jacocoTestReport

2.1 Coverage Report @:
-----------------
../build/jacocoHtml/index.html

3.1 Solution logic:
---------------
The Site map is generated as a grid of squares.
Each square has a default orientation as facing front.
They are all uncleared. The land type comes from the file loaded.
The coordinates are the position in the 2D array. Each operation has a navigation Strategy.

•	Advance -> move front in the existing orientation

•	Left -> move one back to the current orientation in the cycle of orientations (Front,down,back,up)

•	Right -> move one ahead to the current orientation in the cycle of orientations – eg: Right of down is back

•	Quit - > terminate


Each orientation has a logic to get the next square.

For example: current square [1][1]

•	Front -> [1][2] -> current + [0][1]

•	Back -> [1][0] -> current + [0][-1]

•	Down -> [2][1] -> current + [1][0]

•	UP -> [0][1] -> current + [-1][0]


Using this logic, the next coordinates are determined for each operation.
The costs are calculated and squares cleared as we navigate to the new square identified.
Cost are calculated based on land type. Exceptions and terminal conditions are handled.

4.1 High level design:
-----------------
 The Controller will control the overall sequence of events
1.	Setup Activities
2.	Execute Operations
3.	Shutdown Activities
The controller Delegates the implementation of the above item to the Delegates.This way Controller only focus on the how we wish to run the application. Any modifications to the overall flow of events will be done at the controller and controller Only.

The Delegate will govern the finer steps involved in orchestrating the activity. It uses the services and has sequence logic specific to each activity.Any change in future on how an event is implemented, will be handled here.
The Service layer does the actual work of servicing an operation.
1.	SetupService
2.	OperateService
3.	CostService

The Strategy helps is maintain different implementations for different Functionalities.
For Example: All operations, Advance, Quit Left or Right has to be executed. But how each of it Executed will differ based on the Operation. This is achieved by overriding the executions in these strategies.
1.	AdvanceNavigationStrategy
2.	LeftNavigationStrategy
3.	RightNavigationStrategy
4.	QuitNavigationStrategy

The Enum Constants help is regulating the the Business requirements. The Data is restricted to that specified in the business requirement and enums help implement different strategies for each type.
1.	Land Enum -> o, r, t, T
2.	Operation Enum -> a, r, l, q
3.	CostItems Enums -> fuel Cost, paint cost, communication cost, legal cost, uncleared land cost
4.	Orientation Enum -> front, back, down, up

Configurations – All the static text are configured in property files. Values like credits for each cost item are also configured, as these are subjected to change with time. System Error codes are configured for better debugging and analysis.





