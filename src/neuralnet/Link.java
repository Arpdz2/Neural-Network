package neuralnet;

public class Link
{
    double weight, lastChange;
    Node from;

    public Link(double weight, Node from)
    {
        this.weight = weight;
        this.from = from;
        lastChange = 0;
    }

    public double passThrough()
    {
        return from.value * weight;
    }
}