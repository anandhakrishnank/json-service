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

for file : use sample.json in src/main/resources (or provide absolute path of the json file)

for url : use http://demo3130008.mockable.io/json-service as URL (or register new URL and provide as input