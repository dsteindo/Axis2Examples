call mvn package
call ant -buildfile wsdl2java.xml
call mvn clean package
call java -jar ./target/production/jaxwsExample.jar

pause