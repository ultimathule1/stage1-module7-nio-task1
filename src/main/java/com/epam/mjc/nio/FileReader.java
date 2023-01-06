package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();

        try(RandomAccessFile aFile = new RandomAccessFile(file.getPath(), "r"); FileChannel inChannel = aFile.getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) inChannel.size());
            inChannel.read(byteBuffer);
            byteBuffer.flip();
            for (int i = 0, limBuf = byteBuffer.limit(); i < limBuf; i++) {
                stringBuilder.append((char)byteBuffer.get());
            }
            byteBuffer.clear();
        } catch (IOException e) {
            e.getStackTrace();
        }

        String name = getName(stringBuilder.toString());
        int age = getAge(stringBuilder.toString());
        String email = getEmail(stringBuilder.toString());
        long phone = getPhone(stringBuilder.toString());

        return new Profile(name, age, email, phone);
    }

    private String getName(String str) {
        StringBuilder retStr = new StringBuilder();
        for (int i = str.indexOf("Name: ") + "Name: ".length(); str.charAt(i) != '\n'; i++) {
            retStr.append(str.charAt(i));
        }
        return retStr.toString();
    }

    private int getAge(String str) {
        StringBuilder retStr = new StringBuilder();
        for (int i = (str.indexOf("Age: ") + "Age: ".length()); str.charAt(i) != '\n'; i++) {
            retStr.append(str.charAt(i));
        }
        return Integer.parseInt(retStr.toString());
    }

    private String getEmail(String str) {
        StringBuilder retStr = new StringBuilder();
        for (int i = str.indexOf("Email: ") + "Email: ".length(); str.charAt(i) != '\n'; i++) {
            retStr.append(str.charAt(i));
        }
        return retStr.toString();
    }

    private Long getPhone(String str) {
        StringBuilder retStr = new StringBuilder();
        for (int i = str.indexOf("Phone: ") + "Phone: ".length(); str.charAt(i) != '\n'; i++) {
            retStr.append(str.charAt(i));
        }
        return Long.parseLong(retStr.toString());
    }
}
