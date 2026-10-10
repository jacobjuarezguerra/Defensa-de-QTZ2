@echo off
cd /d "%~dp0"
chcp 65001 >nul
java -Dfile.encoding=UTF-8 -cp bin qtz2.Main
pause
