**Template master** - *My first JavaFX project*

````
non-stable version - has a lot of bugs
export, import, save as, about and user friendly usage will comming next version

Default folder to save templates is output of java package path
- out/production/gb_java_gui/sample
relative for project
````

How to use it?
````
Run sample.Main

At the opened window press Template->New to add first template.
Input left (name) and center (body) areas.
You can wrap some text [a-z] with {# of begin and #} of the end
like {#email#}. The programm parse those params.

Now you could to press Template->Save. 
This button saves your input to file.
Also you could update some text and repeat Save action 
to update template or press Close to close.

You could press Template->open for opening your template 
from file. After opening you will show params at the right 
side if it exists. Pass needle text to param fields and 
press Generate button. Now you will see the result.

Thx for attention =)
````

Example input
````
Sorry, dear {#client#}!

Your IP {#ip#} blocked by our system administrator after spam report.
````
Example output for params {client:Sergey, ip:77.222.55.9}
````
Sorry, dear Sergey!

Your IP 77.222.55.9 blocked by our system administrator after spam report.
````
Template files are compatible with JSON format like this
````
{"name":"You IP address blocked.","body":"Sorry, dear {#client#}!\n\nYour IP {#ip#} blocked by our system administrator after spam report.","params":["client","ip","client","ip"],"updateTimestamp":1611434103,"createTimestamp":1611434103}
````