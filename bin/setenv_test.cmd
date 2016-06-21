@echo off
rem ----------------------------------------------------------------------------
rem Author: Tri H. Nguyen
rem 
rem Description:
rem   Set up environment
rem ----------------------------------------------------------------------------

call setenv.cmd

@echo off

set CLASSPATH=%CLASSPATH%;%APP_LIB%\sample-ant_test.jar

@echo on
