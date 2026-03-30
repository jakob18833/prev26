#!/bin/bash
set -xeuo pipefail
make clean
echo "<h1>`date` `hostname`</h1>" > all.html
errstr=""
for i in *.p26; do
	j=`cut -d. -f1 <<<$i`
	make $j TARGETPHASE=seman TARGETPHASE=seman;
	if [ -s $j-seman.html ]
	then
		cat <(echo "<hr><h2>$i</h2><pre>") $j.p26 <(echo "</pre>") $j-seman.html | grep -v "script src=.*p26.js" >> all.html
		if grep '^err' <<<"$i"
		then
			errstr="NAPAKA! datoteka $i se prevede, pa se ne bi smela"
			break
		fi
	else
		if grep '^test' <<<"$i"
		then
			errstr="NAPAKA! datoteka $i se ne prevede, pa bi se morala"
			break
		fi
	fi
done 2>&1 > <(tee testcases-log.txt)
if [ ! x"$errstr" = x"" ]
then
	echo "$errstr" >&2
	exit 1
fi
echo "<script src=https://splet.4a.si./dir/p26.js></script>" >> all.html
[ `hostname` = "e" ] && scp * 4a.si:/splstr/dir/p26/

