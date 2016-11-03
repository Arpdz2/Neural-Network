A forward feed backpropogation neural network made as an experiment in analytics techniques.

Input is given to the network in training.txt and validation.txt.
Only binary data is accepted.
Each line should be a data point with space delimited attributes.
The last n attributes for each point are intepreted as that point's expected output.
	- n is equal to the number of output nodes (the last index in numNodes)
Example files are provided with the code.

The numNodes array specifies how many layers the network has and how many nodes are in each.
Adding entries in the array will increase the number of layers.
The 0th entry is the input layer, and the length-1 entry is the output layer.

learningRate specifies how quickly the network changes during backpropogation (0-1 is appropriate).

momentum specifies the influence of previous backpropogation iterations on future ones (0-1 is appropriate).

skipChance specifies the probability that a training set entry will be skipped. This is useful when making multiple passes over training data to avoid overfitting. (0-1 is appropriate)

nIters specifies the number of passes over the training data the network should make before moving on to validation.

The current implementation trains itself on the training set and outputs the average error (distance from expected output) of the validation set.

Was written very hastily. Will come back and refactor eventually.