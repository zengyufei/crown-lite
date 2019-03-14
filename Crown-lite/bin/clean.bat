@echo off

cd %~dp0
cd..

::另外一种形式 cmd /k mvn clean
call ./bin/.mvn/mvnw.cmd clean

pause