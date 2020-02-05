# arcade_console

To use, run arcade_console-1.0-SNAPSHOT.jar from the root directory, as follows:

java -jar ./target/arcade_console-1.0-SNAPSHOT.jar

This will initiate the wizard. Then, follow the menu prompts. Make sure to answer the prompts carefully, as the menus do not have exception handling built in yet.

To test, use any of the Arcade subject_systems. None are packaged with this submission as the smallest of them is several hundred MB in size.

To recompile, place the BatchClusteringEngine.jar and PipeExtractor.jar packages from Arcade inside of the lib folder. Again, they are not packaged with this submission as each of them is over 50MB in size. To recompile, use the following command:

mvn clean package -Dmaven.test.skip=true

Installation of the support libraries is tied to the clean phase, and generation of the jar is tied to the package phase. You may elect not to skip the tests, but currently they are neither working perfectly nor particularly helpful. This is due to the non-deterministic nature of ARC, which I have yet to find a way to test properly.