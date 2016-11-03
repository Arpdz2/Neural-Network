package neuralnet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        int[] numNodes = {31, 11, 6};
        int numLayers = numNodes.length;
        double learningRate = .7;
        double momentum = .9;
        double skipChance = 0;
        int nIters = 1;

        Layer[] layers = new Layer[numLayers];
        layers[0] = new Layer(numNodes[0], learningRate, momentum);
        for(int i = 1; i < numLayers; i++)
            layers[i] = new Layer(numNodes[i], learningRate, momentum, layers[i - 1]);

        for(int iters = 1; iters <= nIters; iters++)
        {
            double[] error = new double[numNodes[numNodes.length - 1]];

            File inFile = new File("training.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inFile));

            String input = new String();
            String[] data = new String[numNodes[0] + numNodes[numNodes.length - 1]];

            input = reader.readLine();
            while(input != null)
            {
                if(Math.random() < skipChance && iters != nIters - 1)
                {
                    input = reader.readLine();
                    continue;
                }
                data = input.split(" ");

                for(int i = 0; i < numNodes[0]; i++)
                    layers[0].nodes.get(i).value = Double.parseDouble(data[i]);

                for(int i = 1; i < numLayers; i++)
                    layers[i].feedForward();

                for(int i = 0; i < numNodes[numNodes.length - 1]; i++)
                    error[i] = Double.parseDouble(data[numNodes[0]]) - layers[numLayers - 1].nodes.get(i).value;

                layers[numLayers - 1].backPropogate(error);

                input = reader.readLine();
            }

            reader.close();
        }

        double totalError = 0;
        int numVals = 0;
        File inFile = new File("validation.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inFile));
        String input = reader.readLine();
        String[] data = new String[numNodes[0] + numNodes[numNodes.length - 1]];

        while(input != null)
        {
            data = input.split(" ");

            for(int i = 0; i < numNodes[0]; i++)
                layers[0].nodes.get(i).value = Double.parseDouble(data[i]);

            for(int i = 1; i < numLayers; i++)
                layers[i].feedForward();

            for(int i = 0; i < numNodes[numNodes.length - 1]; i++)
            {
                double output;
                if(layers[numLayers - 1].nodes.get(i).value >= .5)
                    output = 1;
                else
                    output = 0;
                totalError += Math.abs(Double.parseDouble(data[numNodes[0]]) - output);
                numVals++;
            }

            input = reader.readLine();
        }
        
        System.out.println("Average Error: " + totalError/numVals);
    }
}