package tps;
import java.util.*;

public class MDMC {
	
	public void optimize(double[] cart, Energy ene) throws InputException {
		//use gradient descent method to optimize the structure
		// to local minima
		double e1, e2;
		int n = cart.length;
		double[] gradient = ene.calcGradient(cart);
		double alpha = 0.1; // gradient descent step size
		e1 = ene.calcEnergy(cart);
		do {
			for(int i = 0; i < n; i ++) {
				cart[i] -= alpha * gradient[i];
			}
			e2 = ene.calcEnergy(cart);
		} while (Math.abs(e2 - e1) > 1e-6);					
		return;
	}	

	public void trajectory(double[] mass, double[] cart) throws InputException{
		return;
	}
};
