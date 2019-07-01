# PetStore

Steps to Run the tests on local machine
1) Open gitbash
2) Change the directory to the folder where you want to clone the repository
3) run the command :
git clone https://github.com/monicangangurde/PetStore.git
4) The repository will create a local copy
5) Go to the local copy of the project and open the .classpath file in Textpad.
6) Delete all the entries referencing to my local machine and save it.
5) Go to Eclipse,--> Import Projects --> Existing Projects into Workspace --> Select the root directory as the local folder (PetStore). Click Finish
6) Select the root directory --> right Click--> Properties--> Java Build Path--> Under Libraries tab--> Select all the jar files pointing to the incorrect location--> Click Remove.
7) Click Add external jars --> Go to the "lib" folder which is present in the local folder(/PetStore)
7) Select all the jar files from the lib folder and click Apply and Close
8) Open the TestRunnerPetStore.java file present under (/src/TestRunner) and click Run As--> JUnit Test


The Test-Plan, Requirements, Test-Cases and Traceability Matrix are present at the root of the project.
