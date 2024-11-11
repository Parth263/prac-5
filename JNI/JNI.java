public class JNI {
    static {
        System.loadLibrary("native"); 
    }

    public static void main(String[] args) {
        System.out.println("Addition is : " + new JNI().add(20, 40));
    }

    private native int add(int n1, int n2);
}