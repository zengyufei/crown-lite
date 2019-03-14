@echo off

cd %~dp0
cd..

::另外一种形式 cmd /k mvn clean package -Ptest -Dmaven.test.skip=true
call ./bin/.mvn/mvnw.cmd clean package -Ptest -Dmaven.test.skip=true

pause