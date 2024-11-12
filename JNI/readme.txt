I'll explain all the concepts and theory behind JNI (Java Native Interface) from scratch:

1. **What is JNI (Java Native Interface)?**
```
- JNI is a framework that allows Java code to call and be called by:
  * Native applications 
  * Libraries written in other languages like C, C++
  * Assembly language programs

- Use Cases of JNI:
  * When you need direct hardware access
  * To reuse existing code written in C/C++
  * For performance-critical operations
  * To use system/platform-dependent features
```

2. **Basic Concepts**:
```plaintext
a) Native Methods:
   - Methods declared in Java with 'native' keyword
   - Implementation provided in another language (C/C++)
   
b) Native Libraries:
   - Shared libraries (.so in Linux, .dll in Windows)
   - Contain implementation of native methods
   
c) JNI Signatures:
   - Mapping between Java and C data types
   - Special naming conventions for C functions
```

3. **Architecture Flow**:
```
Java Code → JNI → Native Code (C/C++)
1. Java declares native methods
2. JNI provides interface layer
3. C/C++ implements the methods
4. Java calls these methods through JNI
```

4. **Data Type Mapping**:
```
Java Type  →  C Type         →  JNI Type
boolean    →  unsigned char  →  jboolean
byte       →  signed char    →  jbyte
char       →  unsigned short →  jchar
short      →  short          →  jshort
int        →  int           →  jint
long       →  long long     →  jlong
float      →  float         →  jfloat
double     →  double        →  jdouble
```

5. **Detailed Code Explanation**:

a) **Java Side** (JNI.java):
```java
public class JNI {
    // Load native library
    static {
        System.loadLibrary("native");
    }

    // Native method declaration
    private native int add(int n1, int n2);

    public static void main(String[] args) {
        // Create object and call native method
        System.out.println(new JNI().add(10, 20));
    }
}
```
Explanation:
```plaintext
1. static block: Loads native library when class is loaded
2. native keyword: Indicates method implemented in C/C++
3. System.loadLibrary(): Links to native library
```

b) **C Side** (JNI.c):
```c
#include <jni.h>

JNIEXPORT jint JNICALL Java_JNI_add
  (JNIEnv *env, jobject thisobj, jint n1, jint n2) {
    return n1 + n2;
}
```
Explanation:
```plaintext
1. JNIEXPORT: Makes function visible
2. JNICALL: Calling convention
3. Java_JNI_add: Naming convention
   - Java_: Prefix
   - JNI: Class name
   - add: Method name
4. Parameters:
   - JNIEnv *env: JNI environment
   - jobject thisobj: Java object reference
   - jint n1, jint n2: Method parameters
```

6. **Compilation Process**:
```plaintext
1. Compile Java Code:
   javac -h . JNI.java
   - Creates .class file
   - Generates header file (.h)

2. Compile C Code:
   gcc -c -fPIC [includes] JNI.c
   - Creates object file (.o)

3. Create Shared Library:
   gcc -shared [options] -o libnative.so
   - Creates shared library

4. Set Path and Execute:
   - Set LD_LIBRARY_PATH
   - Run Java program
```

7. **Important Components**:

```plaintext
a) Header Files:
   - jni.h: Core JNI definitions
   - platform-specific headers

b) Environment Variables:
   - JAVA_HOME: JDK location
   - LD_LIBRARY_PATH: Library path

c) Tools Used:
   - javac: Java compiler
   - gcc: C compiler
   - javah: Header generator (older versions)
```

8. **Common Issues and Solutions**:
```plaintext
1. UnsatisfiedLinkError:
   - Library not found
   - Wrong library name
   - Path issues

2. NoClassDefFoundError:
   - Class not found
   - Package structure issues

3. Symbol lookup errors:
   - Function naming mismatch
   - Missing exports
```

9. **Best Practices**:
```plaintext
1. Error Handling:
   - Check return values
   - Handle exceptions

2. Resource Management:
   - Release native resources
   - Clean up memory

3. Performance:
   - Minimize JNI calls
   - Batch operations when possible

4. Security:
   - Validate input data
   - Handle permissions
```

10. **Advantages and Disadvantages**:
```plaintext
Advantages:
- Platform-specific code
- Reuse existing libraries
- Better performance for certain operations

Disadvantages:
- Complex development
- Platform dependency
- Debugging difficulty
- Security risks
```

This theory should give you a solid understanding of JNI and how it works. Let me know if you need clarification on any specific concept!



step1 : Compile java file
javac -h . TestJNI.java

step2 : Compile C file 
gcc -c -fPIC -I /usr/lib/jvm/java-1.8.0-openjdk-amd64/include -I/usr/lib/jvm/java-1.8.0-openjdk-amd64/include/lunux TestJNI.c -o TestJNI.o

step3 : compile and link jni.h and jni_md.h
gcc -shared -fPIC -o libnative.so TestJNI.o -lc

step4 : Run java file
java -Djava.library.path=.TestJNI



### 1. **Difference between Static Link Library (SLL) and Dynamic Link Library (DLL)**:

| **Feature**                      | **Static Link Library (SLL)**                              | **Dynamic Link Library (DLL)**                              |
|----------------------------------|------------------------------------------------------------|-------------------------------------------------------------|
| **Linking Time**                 | Linked at compile time (during program build).            | Linked at runtime (when program is executed).               |
| **File Size**                    | Increases executable size since library is included within the executable. | Smaller executable as library is separate from the executable. |
| **Memory Usage**                 | Uses more memory as the library code is loaded into memory for every program that uses it. | Saves memory as DLL is shared among multiple programs using it. |
| **Flexibility**                  | Less flexible, as updates to the library require recompiling the program. | More flexible, updates to the DLL don't require recompiling the program. |
| **Performance**                  | May provide faster startup time as everything is prelinked. | May incur some overhead at runtime due to dynamic linking. |
| **Error Handling**               | Errors are detected during compile-time.                   | Errors are detected during runtime when the DLL cannot be found or loaded. |

### 2. **What is a Shared Object?**
   - A **Shared Object (SO)** is a file format for shared libraries in UNIX-like operating systems (e.g., Linux). It is similar to a DLL (Dynamic Link Library) in Windows. Shared objects contain code and data that multiple programs can use simultaneously, which reduces memory usage and allows for efficient updates. When a program calls a function from a shared object, the operating system dynamically links the shared object at runtime.

### 3. **Advantages and Disadvantages of Using JNI (Java Native Interface)**:

#### **Advantages**:
   - **Access to Native Code**: JNI allows Java programs to interact with code written in other programming languages (such as C or C++), enabling access to platform-specific features or existing native libraries.
   - **Performance**: For performance-critical operations, JNI can be used to call optimized native code, bypassing some of the overhead of the Java Virtual Machine (JVM).
   - **Reusability**: JNI allows the reuse of existing native libraries, reducing the need for re-implementing functionality in Java.

#### **Disadvantages**:
   - **Complexity**: Writing and maintaining JNI code can be complex, as it involves managing both Java and native code. It also requires understanding the intricacies of both programming languages and their memory management.
   - **Portability Issues**: JNI code is platform-dependent. A program using JNI will not be fully portable across different operating systems without modification or recompilation of the native code.
   - **Performance Overhead**: While JNI can improve performance in some cases, it also introduces additional overhead due to the need for transitioning between Java and native code, which may degrade performance in some scenarios.
   - **Error Handling**: Debugging JNI code is more challenging, as errors in native code can cause crashes or undefined behavior in the Java application, which is harder to detect than pure Java errors.