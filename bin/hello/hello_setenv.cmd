@echo off
rem ----------------------------------------------------------------------------
rem Author: Tri H. Nguyen
rem 
rem Description:
rem   Set up environment
rem ----------------------------------------------------------------------------

set CWD=%~dp0
set APP_LIB=%CWD%..\lib
set APP_CONFIG=%CWD%..\config

set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;%APP_CONFIG%
set CLASSPATH=%CLASSPATH%;%APP_LIB%\hello.jar
set CLASSPATH=%CLASSPATH%;%APP_LIB%\sample-ant_libs.jar

@echo on
