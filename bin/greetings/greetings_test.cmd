@echo off
rem ----------------------------------------------------------------------------
rem Author: Tri H. Nguyen
rem 
rem Description:
rem   Launch JUnit Test
rem ----------------------------------------------------------------------------

set CLASSPATH_OLD=%CLASSPATH%

set CWD=%~dp0
set APP_LIB=%CWD%..\lib
set APP_CONFIG=%CWD%..\config

set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;%APP_CONFIG%
set CLASSPATH=%CLASSPATH%;%APP_LIB%\greetings.jar
set CLASSPATH=%CLASSPATH%;%APP_LIB%\greetings_test.jar
set CLASSPATH=%CLASSPATH%;%APP_LIB%\sample-ant_libs.jar

if "%1" == "test.greetings" goto TEST_GREETINGS
goto USAGE

:TEST_GREETINGS
set TEST_CLASS=sample.greetings.GreetingsTest
goto TEST

:TEST
%JAVA_HOME%/bin/java org.junit.runner.JUnitCore %TEST_CLASS%
goto END

:USAGE
echo.
echo Usage: %0 [options]
echo where options are
echo   test.greetings Test sample.greetings.Greetings module
echo.
goto END

:END
set CLASSPATH=%CLASSPATH_OLD%
set CLASSPATH_OLD=

@echo on

