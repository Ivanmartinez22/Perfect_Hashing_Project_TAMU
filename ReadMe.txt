Project1 Info
Author: Ivan Martinez 
Course: CSCE314-599
UIN: 529006731
hash341 package
    City.java stores city information - name, longitude, latitude, etc.
    CityTable.java stores city information - implements perfect hashing scheme.
    Pair.java - additional class for CityTable.
    Hash24.java - given code for Hash Functions. 
driver package
	First.java - First program as stated in instructions
    The first program implemented reads in the text file US_Cities_LL.txt, constructs a hash table using perfect hashing, prints out the statistics described above and writes out the hash table to a file named US_Cities_LL.ser.
	
    Second.java - Second program as stated in instructions
    The second program implemented reads in the hash table from the file named US_Cities_LL.ser and prompts the user to type in a city name (including the state). If the city is in the hash table, your program prints out the city's name and coordinates and also a URL that can be cut-and-pasted into a web browser.

    Tester.java - my own test program to test if all cities are in hash table

    Proj4Test.java - provided test program
Change Directory to src

Linux compilation command:
javac hash341/City.java hash341/Pair.java hash341/CityTable.java hash341/Hash24.java driver/First.java driver/Second.java driver/Tester.java driver/Proj4Test.java

Linux execution commands:
java SpecifiedClass 

java driver/First
java driver/Second

Windows Compilation Command: 
javac hash341\City.java hash341\Pair.java hash341\CityTable.java hash341\Hash24.java driver\First.java driver\Second.java driver\Tester.java driver\Proj4Test.java

Windows execution commands:
java package.SpecifiedClass

java driver.First
java driver.Second

Note to run on eclipse:
    The file structure is different for eclipse so within my submission folder is another folder called Project1Eclipse that is modified so that the project can be run on Eclipse. 
    First run driver/First
    Then run driver/Second
Note on src:
    Directory may need to be changed
Note on run time:
    On eclipse on my machine it seems to run fast
    On windows on my machine it seems to run fast
    On ubuntu wsl it seems to run slow on my machine not sure why - it has to do with file writing 
    On putty/linux server it seems to run fast
    If it is seems like it is not working please give a few seconds(10 to 30 seconds) to run
    In the pdf attached there is screen shots of my output/testing


