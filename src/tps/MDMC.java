package tps;
import java.util.*;

public class MDMC {
	
	public void optimize(double[] cart, Energy ene) throws InputException, OptException {
		//use gradient descent method to optimize the structure
		// to local minima
		double e1, e2;
		int n = cart.length;
		double[] gradient;
		double alpha = 0.1; // gradient descent step size
		e1 = ene.calcEnergy(cart);
		int step, NSTEP;
		step = 0;
		NSTEP = 1000;
		do {
			e2 = e1;
			gradient = ene.calcGradient(cart);
			for(int i = 0; i < n; i ++) {
				cart[i] -= alpha * gradient[i];
			}
			e1 = ene.calcEnergy(cart);
			step ++;
			if(step > NSTEP) { 
				String message = "NSTEP of Optimization exceeds " + NSTEP +" , Optimization stopped";
				throw new OptException(message);
			}
			else System.out.println("......... " + step + " ............. Energy " + e1 + " ........");
			//update step size
			for(int i = 0; i < n; i ++) {
				alpha = Math.min(alpha, Math.abs((e1 - e2) / gradient[i] / n));
			}
		} while (Math.abs(e1 - e2) > 1e-6);					
		System.out.println(".....Optimization converged in " + step + " ......");
		return;
	}	

	public void trajectory(double[] mass, double[] cart) throws InputException{
		return;
	}
};

