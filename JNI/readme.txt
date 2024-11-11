Here are the complete execution steps that will work on your Ubuntu terminal:

1. **First, create a new directory and navigate to it**:
```bash
mkdir JNIProject
cd JNIProject
```

2. **Create Java file** (JNI.java):
```bash
nano JNI.java
```
Copy this code:
```java
public class JNI {
    static {
        System.loadLibrary("native");
    }

    public static void main(String[] args) {
        System.out.println("Addition is: " + new JNI().add(10, 20));
    }

    private native int add(int n1, int n2);
}
```
Save with: `Ctrl + X`, then `Y`, then `Enter`

3. **Create C file** (JNI.c):
```bash
nano JNI.c
```
Copy this code:
```c
#include <jni.h>
#include <stdio.h>

JNIEXPORT jint JNICALL Java_JNI_add
  (JNIEnv *env, jobject thisobj, jint n1, jint n2) {
    jint res;
    res = n1 + n2;
    return res;
}
```
Save with: `Ctrl + X`, then `Y`, then `Enter`

4. **Install required packages** (if not installed):
```bash
sudo apt-get update
sudo apt-get install default-jdk
sudo apt-get install build-essential
```

5. **Set JAVA_HOME**:
```bash
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
```

6. **Compile and Run** (step by step):
```bash
# 1. Compile Java file
javac -h . JNI.java

# 2. Compile C file
gcc -c -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" JNI.c -o JNI.o

# 3. Create shared library
gcc -shared -fPIC -o libnative.so JNI.o

# 4. Set library path
export LD_LIBRARY_PATH=.:$LD_LIBRARY_PATH

# 5. Make library executable
chmod +x libnative.so

# 6. Run program
java -Djava.library.path=. JNI
```

7. **Verify files exist**:
```bash
ls -l
```
You should see:
- JNI.java
- JNI.class
- JNI.h
- JNI.c
- JNI.o
- libnative.so

8. **If you want to clean and recompile**:
```bash
# Remove old files
rm -f *.class *.h *.o *.so

# Recompile everything
javac -h . JNI.java && \
gcc -c -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" JNI.c -o JNI.o && \
gcc -shared -fPIC -o libnative.so JNI.o && \
chmod +x libnative.so && \
export LD_LIBRARY_PATH=.:$LD_LIBRARY_PATH && \
java -Djava.library.path=. JNI
```

**Complete Steps in Order**:
```bash
# 1. Create directory
mkdir JNIProject
cd JNIProject

# 2. Create JNI.java
nano JNI.java
# (copy Java code and save)

# 3. Create JNI.c
nano JNI.c
# (copy C code and save)

# 4. Set up environment
sudo apt-get update
sudo apt-get install default-jdk build-essential
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64

# 5. Compile and run
javac -h . JNI.java
gcc -c -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" JNI.c -o JNI.o
gcc -shared -fPIC -o libnative.so JNI.o
chmod +x libnative.so
export LD_LIBRARY_PATH=.:$LD_LIBRARY_PATH
java -Djava.library.path=. JNI
```

**Expected Output**:
```
Addition is: 30
```

If you get any errors:
1. Check if Java is installed: `java -version`
2. Check if GCC is installed: `gcc --version`
3. Verify JAVA_HOME: `echo $JAVA_HOME`
4. Check if files exist: `ls -l`
5. Check library: `ldd libnative.so`

Copy and paste these commands exactly as shown. They should work on any Ubuntu system. Let me know if you face any specific errors!