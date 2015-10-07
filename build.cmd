echo off

set ANT_HOME=%~dp0ant_build\ant
set CUR_PATH=%PATH%
set PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin;%PATH%
set CLASSPATH=%ANT_HOME%\lib\xalan.jar;%ANT_HOME%\lib\ant-junit.jar;

ant -f build.xml

set PATH=%CUR_PATH%

echo on
