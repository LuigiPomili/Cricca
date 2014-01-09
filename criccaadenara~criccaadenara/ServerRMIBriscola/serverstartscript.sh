#!/bin/bash
exec java -cp ./build/classes/serverrmitresette:./dist/ServerRMITresette.jar -Djava.rmi.server.codebase=file:./dist/ServerRMITresette.jar -Djava.security.policy=./src/serverrmitresette/server.policy -Djava.rmi.server.hostname=$1 serverrmitresette.ServerRMITresette
