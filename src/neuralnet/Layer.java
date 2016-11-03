package neuralnet;

import java.util.ArrayList;

public class Layer
{
    ArrayList<Node> nodes;
    Layer last, next;

    public Layer(int size, double learningRate, double momentum)
    {
        last = null;
        next = null;
        nodes = new ArrayList();
        for(int i = 0; i < size; i++)
            nodes.add(new Node(learningRate, momentum));
    }

    public Layer(int size, double learningRate, double momentum, Layer last)
    {
        this.last = last;
        last.next = this;
        next = null;
        nodes = new ArrayList();
        for(int i = 0; i < size; i++)
        {
            Node node = new Node(learningRate, momentum);
            for(Node n : last.nodes)
            {
                Link l = new Link(Math.random(), n);
                node.inLinks.add(l);
                n.outLinks.add(l);
            }
            nodes.add(node);
        }
    }

    public void feedForward()
    {
        for(Node n : nodes)
            n.getValue();
    }

    public void backPropogate(double[] error)
    {
        for(int i = 0; i < error.length; i++)
            nodes.get(i).fixWeights(error[i]);
        if(last != null)
            last.backPropogate();
    }

    public void backPropogate()
    {
        double error;
        for(Node n : next.nodes)
        {
            error = 0;
            for(Link l : n.inLinks)
                error += l.weight * n.gradient;
            n.fixWeights(error);
        }
        if(last != null)
            last.backPropogate();
    }
}