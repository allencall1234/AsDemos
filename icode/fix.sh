cd src/
rm *.dex
rm *.apatch
cd ../
sh apkpatch.sh -f app_v1.apk -t app_v0.apk -o src/ -k 3corder.jks -p "dfjr&3corder&2017" -a "corder" -e "dfjr&3corder&2017"
cd src/
mv *.apatch v0.apatch

cp v0.apatch ../../../UpdateZone/
