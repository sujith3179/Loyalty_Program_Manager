# Reward Program Database Project
By Geoff Garrido (UnityID: ggarrid), 
Indu Chenchala (ichench), 
Matt Sohacki (mjsohack), and 
Sujith Tumma (UnityID:stumma2)


# Note
The SQL files can be found under "database_creation.sql" and "database_seeding.sql".

How to run and compile this code:
1. Within this zip file is a file named "config.properties". Please replace the values within with valid login credentials for the NCSU oracle remote database. Your file should be two lines long and look very similar to this:
```
user=ggarrid
passwd=abcd1234
```
With the change of your username and password. (You may also use these if required)

2. Access the remote oracle repository, as was described previously in class. The steps to do so are:
  a. login/ssh to remote.eos.ncsu.edu using your UnityID and Unity password
  b. transfer over all project files (including library files, sql files, and config.properties)
  c. run the following commands:
  ```
  add oracle12
  export CLASSPATH=.:/afs/eos.ncsu.edu/software/oracle12/oracle/product/12.2/client/jdbc/lib/ojdbc8.jar
  add jdk
  ```
  Doing so will set up the necessary drivers to communicate with oracle.
  
3. Run the jar file named "InitializeDatabase.jar" to initialize the database. This can be done using the following command:
```
java -jar InitializeDatabase.jar
```
NOTE: ALL SUBMITTED SQL FILES MUST BE IN THE SAME DIRECTORY AS THIS JAR FILE! The jar file reads directly from the SQL files, and all are necessary.

This file can be re-run at any time to reset the database, dropping all old tables and re-recreating the sample data.

4.  Run the jar file named "RewardProgram.jar" to launch the application. This can be done using the following command:
```
java -jar RewardProgram.jar
```
You should now be able to interact with your database and use the reward program.

An admin account has been created with userID "admin"

All accounts within the sample data (and the admin account) have a password of "password".