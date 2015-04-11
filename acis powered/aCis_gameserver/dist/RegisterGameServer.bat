@echo off
color 9f
title L2JxTreme gameserver registration console
@java -Djava.util.logging.config.file=config/console.cfg -cp ./libs/*; net.sf.l2j.gsregistering.GameServerRegister
@pause