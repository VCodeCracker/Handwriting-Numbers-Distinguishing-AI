package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class NetworkApp {

    public final static File trainingPictures = new File("src/MNISTtrain");
    public final static File testPictures = new File("src/MNISTtest");

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        System.out.println("program started");


//        serializeTrainingData("testData.ser","trainingData.ser");

        List<double[][]> trainingData = (List<double[][]>) deserialize("trainingData.ser");
        List<double[][]> testData = (List<double[][]>) deserialize("testData.ser");

        System.out.println(trainingData.size());
        System.out.println(testData.size());

        SigmoidNetwork net = new SigmoidNetwork(784,100,50,10);



        net.SGD(trainingData, 500000, 60, 2, testData);



    }


    public static void serializeTrainingData(String destinationTestData,String destinationTrainData) throws IOException {


        List<double[][]> trainingData = new ArrayList<>();
        int trainingDataCounter = 0;

        for (final File fileEntry : trainingPictures.listFiles()) {
            double[][] io = new double[2][];
            double[] input = PictureProcessor.process(fileEntry.getAbsolutePath());
            int resultLabel = Integer.parseInt(String.valueOf(fileEntry.getName().charAt(10)));
            double[] output = Stream.iterate(0, n -> 0).limit(10).mapToDouble(Double::new).toArray();
            output[resultLabel] = 1.00;
            io[0] = input;
            io[1] = output;
            trainingData.add(io);
        }

        System.out.println("training data complete");

        List<double[][]> testData = new ArrayList<>();
        int testDataCounter = 0;

        for (final File fileEntry : testPictures.listFiles()) {
            double[][] io = new double[2][];
            double[] input = PictureProcessor.process(fileEntry.getAbsolutePath());
            int resultLabel = Integer.parseInt(String.valueOf(fileEntry.getName().charAt(10)));
            double[] output = Stream.iterate(0, n -> 0).limit(10).mapToDouble(Double::new).toArray();
            output[resultLabel] = 1.00;
            io[0] = input;
            io[1] = output;
            testData.add(io);
        }

        System.out.println("test data complete");



        FileOutputStream fileOutputStream = new FileOutputStream(destinationTestData);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(testData);
            objectOutputStream.flush();
        }
        fileOutputStream.close();
        System.out.println("Serialized test data");

        FileOutputStream fileOutputStream2 = new FileOutputStream(destinationTrainData);
        try (ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(fileOutputStream2)) {
            objectOutputStream2.writeObject(trainingData);
            objectOutputStream2.flush();
        }
        fileOutputStream2.close();
        System.out.println("Serialized train data");


    }

    public static Object deserialize(String from) throws IOException, ClassNotFoundException {
        Object obj;
        FileInputStream fileInputStream = new FileInputStream(from);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            obj = objectInputStream.readObject();
        }
        return obj;
    }
}
