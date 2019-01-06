import java.util.Random;
import static java.lang.Math.exp;


public class SimpleNN {

    Random r;
    double input_neuron_a, input_neuron_b, hidden_neuron, output_neuron, random_mutation_probability;
    double synapse_from_input_neuron_a[], synapse_from_input_neuron_b[], synapse_from_hidden_neuron[];
    int fits[], count, genomes_per_generation, current_genome=0, current_generation =0;

    public SimpleNN(int genomes_per_generation, double random_mutation_probability){

        r = new Random();
        this.random_mutation_probability = random_mutation_probability;
        this.genomes_per_generation = genomes_per_generation;
        fits = new int[genomes_per_generation];
        count = 5000;


        synapse_from_input_neuron_a = new double[genomes_per_generation];
        synapse_from_input_neuron_b = new double[genomes_per_generation];
        synapse_from_hidden_neuron = new double[genomes_per_generation];

        setRandomValuesToSynapses();

    }

    private void setRandomValuesToSynapses(){
        for(int i=0; i<genomes_per_generation; i++){
            synapse_from_input_neuron_a[i] = randNum(-1, 1);
            synapse_from_input_neuron_b[i] = randNum(-1, 1);
            synapse_from_hidden_neuron[i] = randNum(-1, 1);
        }
    }

    private void setInputs(double input_a, double input_b){
        input_neuron_a = input_a;
        input_neuron_b = input_b;
    }

    public double getOutput(double input_a, double input_b){
        setInputs(input_a, input_b);
        processValues();

        return output_neuron;
    }

    public void newGenome(int score){

        fits[current_genome]=score;
        current_genome++;
        if(current_genome>=genomes_per_generation){

            System.out.println(current_generation + " :Generation" + " last max : " + fits[0]);
            current_genome=0;
            swapFittestGenomeWithFirstGenome();
            if(fits[0]<=0){
                setRandomValuesToSynapses();
            }
            else {
                crossover();
            }
            current_generation++;
        }
    }

    private void processValues(){
        hidden_neuron =0;
        output_neuron =0;

        hidden_neuron += synapse_from_input_neuron_a[current_genome] * input_neuron_a;
        hidden_neuron += synapse_from_input_neuron_b[current_genome] * input_neuron_b;
        hidden_neuron = sigmoid(hidden_neuron);

        output_neuron = synapse_from_hidden_neuron[current_genome] * hidden_neuron;
    }

    private void swapFittestGenomeWithFirstGenome(){
        int max = Integer.MIN_VALUE, position_of_fittest = 0, tempfit;
        double temp_synapse_from_input_neuron_a, temp_synapse_from_input_neuron_b, temp_synapse_from_hidden_neuron;

        for (int i=0; i<genomes_per_generation; i++){
            if(fits[i]>max){
                max = fits[i];
                position_of_fittest = i;
            }
        }

        if(position_of_fittest!=0){
            tempfit = fits[0];
            temp_synapse_from_input_neuron_a = synapse_from_input_neuron_a[0];
            temp_synapse_from_input_neuron_b = synapse_from_input_neuron_b[0];
            temp_synapse_from_hidden_neuron = synapse_from_hidden_neuron[0];

            fits[0] = fits[position_of_fittest];
            synapse_from_input_neuron_a[0] = synapse_from_input_neuron_a[position_of_fittest];
            synapse_from_input_neuron_b[0] = synapse_from_input_neuron_b[position_of_fittest];
            synapse_from_hidden_neuron[0] = synapse_from_hidden_neuron[position_of_fittest];

            fits[position_of_fittest] = tempfit;
            synapse_from_input_neuron_a[position_of_fittest] = temp_synapse_from_input_neuron_a;
            synapse_from_input_neuron_b[position_of_fittest] = temp_synapse_from_input_neuron_b;
            synapse_from_hidden_neuron[position_of_fittest] = temp_synapse_from_hidden_neuron;
        }

    }

    private void  crossover(){

        double temp_synapse_from_input_neuron_a, temp_synapse_from_input_neuron_b, temp_synapse_from_hidden_neuron;

        temp_synapse_from_input_neuron_a = synapse_from_input_neuron_a[0];
        temp_synapse_from_input_neuron_b = synapse_from_input_neuron_b[0];
        temp_synapse_from_hidden_neuron = synapse_from_hidden_neuron[0];

        for (int i=1; i<genomes_per_generation; i++){

            if(checkProbability()){
                synapse_from_input_neuron_a[i] = randNum(-1, 1);
            }
            else{
                synapse_from_input_neuron_a[i] = temp_synapse_from_input_neuron_a;
            }

            if(checkProbability()){
                synapse_from_input_neuron_b[i] = randNum(-1, 1);
            }
            else{
                synapse_from_input_neuron_b[i] = temp_synapse_from_input_neuron_b;
            }

            if(checkProbability()){
                synapse_from_hidden_neuron[i] = randNum(-1, 1);
            }
            else{
                synapse_from_hidden_neuron[i] = temp_synapse_from_hidden_neuron;
            }

        }

    }

    private boolean checkProbability(){
        return (randNum(0, 1)<random_mutation_probability);
    }

    private double sigmoid(double x){
        return 1/(1+(exp(-x)));
    }

    private double randNum(double min, double max) {

        return min + (max - min) * r.nextDouble();
    }


}

