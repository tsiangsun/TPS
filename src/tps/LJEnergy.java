package tps;

import java.util.*;

public class LJEnergy implements Energy {
    
	@Override
        public double calcEnergy(double[] cart) throws InputException {
		int l = cart.length;
		int N = l / 3; //number of atoms in the simulation cluster
		if (l % 3 != 0) {
			String message = "input cartesian cooridnates is not 3 * number of atoms";
			throw new InputException(message);
		}
		return 0;
        }   
    
	@Override
        public double[] calcGradient(double[] cart) throws InputException {
		int n = cart.length;
		double[] force = new double[n];
		double epsilon = 1e-6;
		double ene = calcEnergy(cart);
		double eneif,eneib;
		for(int i = 0; i < n; i ++) {
			cart[i] += epsilon;
			eneif = calcEnergy(cart);
			cart[i] -= 2 * epsilon;
			eneib = calcEnergy(cart);
			force[i] = (eneif - eneib) / (2 * epsilon);
			cart[i] += epsilon;
		}
		return force;
        }   

	@Override
        public double[][] calcHession(double[] cart) throws InputException {
		int n = cart.length;
		double[][] hessian = new double[n][n];
		double[] gradientf, gradientb;
		double epsilon = 1e-6;
		for(int i = 0; i < n; i ++) {
			cart[i] += epsilon;
			gradientf = calcGradient(cart);
			cart[i] -= 2 * epsilon;
			gradientb = calcGradient(cart);
			for(int j = 0; j < n; j ++) {
				hessian[i][j] = (gradientf[j] - gradientb[j]) / (2 * epsilon);
			}
			cart[i] += epsilon;
		}
		return hessian;
        }   
};

