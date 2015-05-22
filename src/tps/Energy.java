package tps;
public interface Energy {
	//calculate energy from coordinates
	public double calcEnergy (double[] cart) throws InputException;
	
	//calculate energy gradient
	public double[] calcGradient (double[] cart) throws InputException;

	//calculate hession
	public double[][] calcHession(double[] cart) throws InputException;
};


class InputException extends Exception {
        public InputException() {
                super();
        }   

	public InputException(String message) {
		super(message);
	}
};

