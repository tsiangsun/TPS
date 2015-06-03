# TPS
usage

This libary use J3D and JOGL to visualize the results. You will need to download J3D package
from the following link
http://create.ife.no/vr/tools/j3d/java3d_1_5_2-macosx.pkg.zip

you will also need JOGL package, which will be availabe at
http://jogamp.org/deployment/jogamp-current/archive/jogamp-all-platforms.7z

after installing J3D. Three jar files will be added to you
/Library/Java/Extension folder, they are j3dcore.jar, j3dutils.jar, vecmath.jar, you should download the lastest version from http://jogamp.org/deployment/java3d/1.6.0-pre12/ and replace the 3 files with newer version.

after unziped jogamp-all-platforms.7z file, you need to copy jogl-all.jar, gluegen-rt.jar in to the /Library/Java/Extension folder (you will need adminstrator previlege to do this)
you also need to add the lib folder in jogamp-all-platforms to your /Libarary/Java/Extension folder

check your Java version by running java -version, version 1.8.0 or newer is desirable. If not, go to Mac System Reference to update Java. 

now you are ready to download the TPS package

download the whole directory in to your local computer

cd to the TPS directory

javac -d ./classes ./src/tps/*.java  (to compile the source files and put the *.class in classes folder)

cd to test folder

javac -d ../classes -cp ../classes TEST.java (to compile the test file)

java -Djava.library.path=/Library/Java/Extensions/lib/macosx-universal/ -cp ../classes/ TEST (run the test)

