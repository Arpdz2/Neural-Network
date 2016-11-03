package neuralnet;

import java.util.ArrayList;

public class Node
{
    double value, bias, learningRate, momentum, lastChange, gradient;
    ArrayList<Link> inLinks, outLinks;

    public Node(double learningRate, double momentum)
    {
        this.learningRate = learningRate;
        this.momentum = momentum;
        value = 0;
        lastChange = 0;
        gradient = 0;
        bias = Math.random() - .5;
        inLinks = new ArrayList();
        outLinks = new ArrayList();
    }

    private double sigmoid(double val)
    {
        return 1 / (1 + Math.exp(-val));
    }

    public void getValue()
    {
        double val = bias;
        for(Link l : inLinks)
            val += l.passThrough();
        value = sigmoid(val);
    }

    public void fixWeights(double error)
    {
        gradient = value * (1 - value) * error;
        lastChange = learningRate * gradient + momentum * lastChange;
        bias += lastChange;
        for(Link l : inLinks)
        {
            l.lastChange = learningRate * gradient * l.from.value + momentum * l.lastChange;
            l.weight += l.lastChange;
        }
    }
}