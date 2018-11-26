# json-service
Demo Project to read JSON By URL or File

Pseudocode:

Get URL</br>
Call a service</br>
Get JSON as response</br>
Parse JSON</br>
Declare total as variable.</br>
Loop through Array</br>
&nbsp;&nbsp;&nbsp;Get values of number key as int Array.</br>
&nbsp;&nbsp;&nbsp;Sum all the integers.</br>
&nbsp;&nbsp;&nbsp;Print "Key" and the sum.</br>
&nbsp;&nbsp;&nbsp;Add sum to total.</br>
End Loop</br>
Print total.</br>

Assumption: 

JSON Structure will be [{"key":"value","numbers":[1,2,3,4,5]}, {"key":"value","numbers":[1,2,3,4,5]}]

Unit Testing:

Run the Main Program:

Type 1 for 'Reading JSON using URL' & Enter </br>
Type 2 for 'Reading JSON using File' & Enter </br>
Type 0 or exit to exit & Enter;</br>
Enter '' or any character to continue</br>

To test using URL:</br>
1: Type 1 & press Enter.</br>
2: Type the URL & press Enter.</br>
&nbsp;&nbsp;&nbsp;for eg: "http://demo3130008.mockable.io/json-service"</br>

To test using File:</br>
1: Type 2 & press Enter.</br>
2: Type the absolute path & press Enter.</br>
&nbsp;&nbsp;&nbsp;for eg:"sample.json" or "F:/home/anand/files/sample.json"</br>

Type 0 & press Enter to exit.</br>
Type any character & press Enter to retest again.</br>

