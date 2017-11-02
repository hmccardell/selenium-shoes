# Running the Project

1. Clone the project to a desired workspace directory
2. Use Intellij to import the project using the Maven external model
3. Allow the Maven imports to resolve
4a. Either use the Intellij Execute Maven Goal functionality to execute `integration-test` or
4b. Open the SearchTest class in src/test/java/amazon directory and run the test from the sidebar


Note:  

This application was developed and tested on a Windows machine.
The application does check to determine the OS and initializes the Mac version of the 
Chrome driver for the test if detected, but I did not have the availability to test this
functionality on a Mac.
