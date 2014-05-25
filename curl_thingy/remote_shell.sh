# check if the user entered a command
dir=/Users/nabilbenabbou1/ensimag/minikit/curl_thingy
curl 'http://172.23.7.138:8080/fablab/receive_data' \
--data 'id=1&action=get&data=' > $dir/result.sh

if [[ -s $dir/result.sh ]] ; then
	echo " " > $dir/result.txt
	source $dir/result.sh > $dir/result.txt


	# send back the results
	curl  'http://172.23.7.138:8080/fablab/receive_data?id=1&action=result' -F data=@$dir/result.txt
else
	echo "Empty command !"
fi;
	sleep 3;
