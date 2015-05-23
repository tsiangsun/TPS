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




class Particle {
	public double mass;
	public Position pos;
	public Particle(double mass, Position pos) {
		this.mass = mass;
		this.pos = pos;
	}
}; 




class Position {
	public double x;
	public double y;
	public double z;
	public Position(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
};
