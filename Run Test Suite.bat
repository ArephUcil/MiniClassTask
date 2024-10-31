@echo off

call mvn test -Dsurefire.suiteXmlFiles=testng.xml

pause