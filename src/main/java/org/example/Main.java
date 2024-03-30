package org.example;

import org.jblas.DoubleMatrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DoubleMatrix picture = new DoubleMatrix(PictureProcessor.process("src/train/000003-num1.png"));
        SigmoidNetwork network = (SigmoidNetwork) deserialize();
        System.out.println(evaluate( network.feedForward(picture)));
    }





    public static int evaluate(DoubleMatrix matrix) {
        double[] buffer = matrix.toArray();
        int indexOfArgMax = 0;
        for (int i = 0; i < buffer.length; i++) {
            if(buffer[i] > buffer[indexOfArgMax]) {
                indexOfArgMax = i;
            }
        }
        return indexOfArgMax;
    }



    public static final String FILE_SERIALIZATION = "net.ser";
    public static Object deserialize() throws IOException, ClassNotFoundException {
        Object obj;
        FileInputStream fileInputStream = new FileInputStream(FILE_SERIALIZATION);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            obj = objectInputStream.readObject();
        }
        return obj;
    }
    public static void serialize(Object obj) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_SERIALIZATION);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
        }
        System.out.println("Serialized");
    }

}