package org.example;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class NetworkApp {

    public final static File trainingPictures = new File("src/train");

    public static void main(String[] args) throws IOException {

        List<double[][]> trainingData = new ArrayList<>();
        int counter = 0;

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


        SigmoidNetwork net = new SigmoidNetwork(784,100,50,10);



        net.SGD(trainingData.subList(0,1542), 500000, 257, 3, trainingData);

        //training data is correct
    }
}
