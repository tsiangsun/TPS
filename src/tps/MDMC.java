package tps;
import java.util.*;

public class MDMC {
	
	public void optimize(double[] cart, Energy ene) throws InputException, OptException {
		//use gradient descent method to optimize the structure
		// to local minima
		double e1, e2, gnorm, gnorm2;
		int n = cart.length;
		double[] gradient;
		double alpha; // gradient descent step size
		e1 = ene.calcEnergy(cart);
		int step, NSTEP;
		step = 0;
		NSTEP = 100000;
		do {
			e2 = e1;
			gradient = ene.calcGradient(cart);
			double m = 0.0;
			for(int i = 0; i < n; i ++) {
				m -= gradient[i] * gradient[i];
			}
			// backtracking linear search for finding optimal stepsize in gradient descent
			double c = 0.85;
			double tor = 0.85;
			double t = -c * m;
			double[] tmpcart;
			alpha = 2.0;
			do {
				alpha *= tor;
				tmpcart = cart.clone();
				for(int i = 0; i < n; i ++) {
					tmpcart[i] -= alpha * gradient[i];
				}
				e1 = ene.calcEnergy(tmpcart);
			} while(e2 - e1 < alpha * t);
			// optimal step size returned as alpha

			for(int i = 0; i < n; i ++) {
				cart[i] -= alpha * gradient[i];
			}
			e1 = ene.calcEnergy(cart);
			step ++;
			//calculate gradient norm
                        gnorm2 = 0.0;
                        for(int i = 0; i < n; i ++) {
                                gnorm2 += Math.pow(gradient[i], 2.0);
                        }   
                        gnorm = Math.sqrt(gnorm2);
			// check whether maximum steps exceeded
			if(step > NSTEP) { 
				String message = "NSTEP of Optimization exceeds " + NSTEP +" , Optimization stopped";
				throw new OptException(message);
			}
			else System.out.println(".......... " + step + " of size " + alpha + " Energy " + e1 + " and gnorm " + gnorm + " ...........");
		} while (Math.abs(e1 - e2) > 1e-6 || gnorm > 1e-5);					
		System.out.println(".....Optimization converged in " + step + " ......");
		System.out.println(".....Energy of .. " + e1 + " and gradient of ... " + gnorm + " ...");
		return;
	}	

	public void trajectory(double[] mass, double[] cart) throws InputException{
		return;
	}
};

