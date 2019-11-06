# JSON-Merge
Merge JSON files

## Language : JAVA
## Special Requirment : Install json-simple-1.1.1.jar and add it to class path in system enironmental variables
 
## Steps 
### Compile using the command:
javac filename.java

### Run using the command 
java filename

### The function takes 4 parameters:
1.Base folder path 
2.Input file base name 
3.Output file base name 
4.MaxFileSize

Note : Function inputs can be configured in the main function.

Time Complexity : O(N) where N is the number of objects being merged.

SAMPLE OUTPUT:

### Output1.json
{"strikers":["{\"name\":\"Alexis Sanchez\",\"club\":\"Manchester United\"}"]}
### Output2.json
{"strikers":["{\"name\":\"Robin van Persie\",\"club\":\"Feyenoord\"}","{\"name\":\"Nicolas Pepe\",\"club\":\"Arsenal\"}"]}
### Output3.json
{"strikers":["{\"name\":\"Gonzalo Higuain\",\"club\":\"Napoli\"}","{\"name\":\"Sunil Chettri\",\"club\":\"Bengaluru FC\"}"]}
