#!/bin/bash
exec java -cp ./build/classes/peerrmitresettecardgame:./dist/PeerRMITresetteCardGame.jar -Djava.security.policy=./src/peerrmitresettecardgame/peer.policy -Djava.rmi.server.hostname=$1 peerrmitresettecardgame.PeerRMITresetteSplashScreen
