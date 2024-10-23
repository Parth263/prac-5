# spos-prac
# prac5


Commands for Compling and Running:
javac -h . testJni2.java
gcc -c -fPIC -I/usr/lib/jvm/java-1.11.0-openjdk-amd64/include -I/usr/lib/jvm/java- 1.11.0openjdk-amd64/include/linux testJni2.c -o testJni2.o
gcc -shared -fPIC -o libnative.so testJni2.o -lc
java -Djava.library.path=. TestJni2
