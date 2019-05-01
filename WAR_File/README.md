# Explanation of the WAR files
## Test Plan
### Single-instance cases

1. Use HTTP, without using prepared statements, 10 threads in JMeter.
2. Use HTTP, without using connection pooling, 10 threads in JMeter.
3. Use HTTP, 1 thread in JMeter.
4. Use HTTP, 10 threads in JMeter.
5. Use HTTPS, 10 threads in JMeter.

### Scaled-version cases
1. Use HTTP, without using prepared statements, 10 threads in JMeter.
2. Use HTTP, without using connection pooling, 10 threads in JMeter.
3. Use HTTP, 1 thread in JMeter.
4. Use HTTP, 10 threads in JMeter.

## General function
- There are three WAR files in this folder
- full function file is with connection pooling and prepared statement.[master branch](https://github.com/UCI-Chenli-teaching/cs122b-winter19-team-10/tree/master). This is used to performance the test for task 3,4,5 in Single-instance cases and task 3, 4 in Scaled-version cases.
- no connection pooling file is a version without connection pooling.[noConnectionPooling branch](https://github.com/UCI-Chenli-teaching/cs122b-winter19-team-10/tree/noConnectionPooling). This is used to performance the test for task 2 in Single-instance cases and task 2 in Scaled-version cases.
- no prepared statement is a version without prepared statement.[noPreparedStatement branch](https://github.com/UCI-Chenli-teaching/cs122b-winter19-team-10/tree/noPreparedStatement).This is used to performance the test for task 1 in Single-instance cases and task 1 in Scaled-version cases.

## Notice
-  When you need to deploy any of the three WAR files, you need to change the WAR file name to cs122b-project to launch it.
