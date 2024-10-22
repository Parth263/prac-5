

I'll guide you through executing the JNI code step by step:

1. SETUP ENVIRONMENT:
-------------------
First, ensure you have:
- JDK (Java Development Kit)
- C Compiler (GCC or MinGW for Windows)
- A text editor or IDE (VS Code, Eclipse, etc.)

Check installations:
```bash
# Check Java
javac -version
java -version

# Check GCC
gcc --version
```

2. PROJECT STRUCTURE:
-------------------
Create this directory structure:
```
JNIProject/
├── src/
│   └── JNI/
│       ├── JNI.java
│       ├── JNI.c
│       └── JNI.h (will be generated)
└── lib/
    └── (for compiled .dll/.so files)
```

3. STEP-BY-STEP EXECUTION:
------------------------

STEP 1: Create Java File
```bash
# Create directories
mkdir -p JNIProject/src/JNI
cd JNIProject/src/JNI

# Create Java file
# Copy this Java code into JNI.java:
```



```java
package JNI;

import java.util.Scanner;
public class JNI {
    public native void JniAdd(int no1,int no2);
    public native void JniSub(int no1,int no2);
    public native void JniMult(int no1,int no2);
    public native void JniDiv(double no1,double no2);
    public native void JniPow(int no1,int no2);
    public native void JniSqrt(int no1);
    public native void JniMod(int no1,int no2);
    
    // Modify this path according to your system
    static { 
        System.loadLibrary("JNI"); // Load libJNI.dll (Windows) or libJNI.so (Linux)
    }
    
    public static void main(String[] args) {
        int no1, no2;
        Scanner in = new Scanner(System.in);
        JNI MJ = new JNI();
        System.out.println("JNI using C");
        System.out.print("Enter first number: ");
        no1 = in.nextInt(); 
        System.out.print("Enter second number: ");
        no2 = in.nextInt();
        MJ.JniAdd(no1,no2);
        MJ.JniSub(no1,no2);
        MJ.JniMult(no1,no2);
        MJ.JniDiv((double)no1,(double)no2);
        MJ.JniPow(no1,no2);
        MJ.JniSqrt(no2);
        MJ.JniMod(no1,no2);
    }
}

```

STEP 2: Generate Header File
```bash
# From src directory
javac -h . JNI/JNI.java
```

STEP 3: Create C File
```bash
# Copy this C code into JNI.c:
```

```c
#include <jni.h>
#include <math.h>
#include <stdio.h>

#define PI 3.14159265

JNIEXPORT void JNICALL Java_JNI_JNI_JniAdd
  (JNIEnv *e, jobject obj, jint no1, jint no2)
{
    int add = no1 + no2;
    printf("Addition of nos.= %d", add);
}

JNIEXPORT void JNICALL Java_JNI_JNI_JniSub
  (JNIEnv *e, jobject obj, jint no1, jint no2)
{
    int sub = no1 - no2;
    printf("\nSubtraction of nos. is= %d", sub);
}

JNIEXPORT void JNICALL Java_JNI_JNI_JniMult
  (JNIEnv *e, jobject obj, jint no1, jint no2)
{
    int mult = no1 * no2;
    printf("\nMultiplication of nos. is= %d", mult);
}

JNIEXPORT void JNICALL Java_JNI_JNI_JniDiv
  (JNIEnv *e, jobject obj, jdouble no1, jdouble no2)
{
    double div = no1/no2;
    printf("\nDivision of nos. is= %.3f", div);
}

JNIEXPORT void JNICALL Java_JNI_JNI_JniMod
  (JNIEnv *e, jobject obj, jint no1, jint no2)
{
    printf("\nRemainder is= %.3f", fmod(no1,no2));
}

JNIEXPORT void JNICALL Java_JNI_JNI_JniPow
  (JNIEnv *e, jobject obj, jint no1, jint no2)
{
    printf("\nPower is= %.3f", pow(no1,no2));
}

JNIEXPORT void JNICALL Java_JNI_JNI_JniSqrt
  (JNIEnv *e, jobject obj, jint no1)
{
    printf("\nSquare root %d is= %.3f", no1, sqrt(no1));
}

```

STEP 4: Compile C Code into Shared Library

For Windows:
```bash
# Using MinGW
gcc -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -shared -o JNI.dll JNI.c
```

For Linux:
```bash
# Using GCC
gcc -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -shared -fPIC -o libJNI.so JNI.c
```

STEP 5: Set Library Path

For Windows:
```bash
# Add DLL directory to PATH or copy DLL to working directory
set PATH=%PATH%;path\to\dll\directory
```

For Linux:
```bash
# Add library directory to LD_LIBRARY_PATH
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:path/to/library/directory
```

STEP 6: Run the Program
```bash
# From src directory
java JNI.JNI
```

4. TROUBLESHOOTING:
-----------------

1. UnsatisfiedLinkError:
```bash
# Check if library path is correct
# Verify library name matches System.loadLibrary() call
# Ensure correct architecture (32/64 bit)
```

2. NoClassDefFoundError:
```bash
# Check package structure
# Verify CLASSPATH
```

3. Compilation Errors:
```bash
# Check JDK installation
# Verify include paths
# Check C compiler installation
```

5. COMMON ISSUES AND SOLUTIONS:
----------------------------

1. Library Not Found:
```java
// Modify library path in Java code
static {
    System.load("/absolute/path/to/library");
}
```spdzfjgoajojgja'ojofkg

2. JNI Include Not Found:
```bash
# Add JDK include paths to compilation command
gcc -I"/path/to/jdk/include" ...
```

3. Missing Functions:
```bash
# Regenerate header file after any Java changes
javac -h . JNI.java
```

Would you like me to:
1. Explain any specific error you're encountering?
2. Provide more detailed steps for your specific OS?
3. Help with setting up the environment?