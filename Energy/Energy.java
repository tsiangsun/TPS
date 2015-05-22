package energy;
public interface Energy {
	//calculate energy from coordinates
	public double calcEnergy(double[] cart);
	
	//calculate energy gradient
	public double[] calcGradient(double[] cart);

	//calculate hession
	public double[][] calcHession(double[] cart);
};
