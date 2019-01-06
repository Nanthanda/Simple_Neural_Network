# Simple_Neural_Network
Just a simple neural network with three synapses, two input neurons, one output neuron and one hidden neuron.

Instructions:

1.Create an int variable (Eg.int score) to calculate the fitness;

2.Create an object of class SimpleNN with number of genomes per generation as first parameter and random mutation probability as second parameter or just use as shown in example.

3.To get the output use getOutput function, It tackes two parameters (double)input1 and (double)input2 then returns a double value.

4.If the output is correct, increase the fitness.

5.If the output is wrong get new genome by using newGenome function with fitness of current genome as only parameter.
  
  Eg.
  
  int score =0;
  double input_a, input_b, output;
  
  SimpleNN nn = new SimpleNN(10,0.5);
  
  output = nn.getOutput(input_a, input_b);
  
  if(output==expected_value){
    score++;
  }
  else{
  nn.newGenome(score);
  }
