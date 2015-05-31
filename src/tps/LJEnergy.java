package tps;

import java.util.*;
import javax.vecmath.Color3f;

public class LJEnergy implements Energy {
    
	@Override
        public double calcEnergy(double[] cart) throws InputException {
		int l = cart.length;
		int N = l / 3; //number of atoms in the simulation cluster
		if (l % 3 != 0) {
			String message = "input cartesian cooridnates is not 3 * number of atoms";
			throw new InputException(message);
		}
		double ene = 0.0;
		double[] x = new double[N];
		double[] y = new double[N];
		double[] z = new double[N];
		for(int i = 0; i < N; i ++) {
			x[i] = cart[3 * i];
			y[i] = cart[3 * i + 1];
			z[i] = cart[3 * i + 2];
		}
		for(int i = 0; i < N - 1; i ++) {
			for(int j = i + 1; j < N; j ++) {
				Position posi = new Position(x[i], y[i], z[i]);
				Position posj = new Position(x[j], y[j], z[j]);
				double mass = 1.0;
				double radius = 0.1;
				float r, g, b;
				r = 0; g = 0; b = 0;
				Color3f color = new Color3f(r, g, b);
				Particle pi = new Particle(mass, posi, radius, color);
				Particle pj = new Particle(mass, posj, radius, color);
				ene += LJ(pi, pj);
			}
		}
		return ene;
        }   
    
	@Override
        public double[] calcGradient(double[] cart) throws InputException {
		int n = cart.length;
		double[] force = new double[n];
		double epsilon = 1e-8;
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

	public double LJ(Particle pi, Particle pj) {
		double A = 4.0;
		double B = 4.0;
		double rsquare = Math.pow(pi.pos.x - pj.pos.x, 2) + Math.pow(pi.pos.y - pj.pos.y, 2) + Math.pow(pi.pos.z - pj.pos.z, 2);
		double r = Math.sqrt(rsquare);
		double VLJ = (1.0 / Math.pow(r, 12.0) - 2.0 / Math.pow(r, 6.0));
		return VLJ;
	}

};


