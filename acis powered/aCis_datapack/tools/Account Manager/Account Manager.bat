@echo off
color 9f
title L2JxTreme loginserver console
:start
@java -Djava.util.logging.config.file=console.cfg -cp ./lib/*; SQLAccountManager.SQLAccountManager
if ERRORLEVEL 2 goto restart
if ERRORLEVEL 1 goto error
goto end
:restart
goto start
:error
:end
pause
