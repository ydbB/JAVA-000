代码如下：

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class HelloClassloader extends ClassLoader {

    public static void main(String[] args) {
        try {
            // 获取类
            Class helloClass = new HelloClassloader().
                    findClass("Hello");
            //反射获取 hello 方法
            Method hello = helloClass.getMethod("hello");
            //执行hello 方法
            hello.invoke(helloClass.newInstance());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] helloClassBytes = readBytes("./Hello.xlass");
            for (int i = 0; i < Objects.requireNonNull(helloClassBytes).length; i ++) {
                helloClassBytes[i] = (byte) (255 -  helloClassBytes[i]);
            }

            return defineClass(name, helloClassBytes, 0, helloClassBytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    public static byte[] readBytes(String name) throws IOException {
        File file = new File(name);
        long fileSize = file.length();

        if (fileSize > Integer.MAX_VALUE) return null;

        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0, numRead = 0;
        while (offset < buffer.length && (numRead = fis.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset != buffer.length) throw new IOException("Can't read file completely " + file.getName());

        fis.close();
        return buffer;
    }

}
