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

Type 1 for 'Reading JSON using URL' & Enter 
Type 2 for 'Reading JSON using File' & Enter 
Type 0 or exit to exit & Enter;
Enter '' or any character to continue

To test using URL:
1: Type 1 & press Enter.
2: Type the URL & press Enter.
   for eg: "http://demo3130008.mockable.io/json-service"

To test using File:
1: Type 2 & press Enter.
2: Type the absolute path & press Enter.
   for eg:"sample.json" or "F:/home/anand/files/sample.json"

Type 0 to exit

