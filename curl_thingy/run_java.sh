#!/bin/sh

# Execute the java main class
#cd ..
#mvn exec:java -Dexec.mainClass="fablab.MiniKit.SimuCapteur"
# if the program exits, we call the script that tries to connect with the server
dir=$HOME/ensimag/minikit/curl_thingy

while :
do
	source $dir/remote_shell.sh
done