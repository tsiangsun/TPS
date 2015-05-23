import java.util.*;
import tps.*;

public class TEST {
	public static void main(String[] args) {
		MDMC mdmc = new MDMC();
		double lengthx, lengthy, lengthz;
		lengthx = 10.0;
		lengthy = 10.0;
		lengthz = 10.0;
		SimBox sb = new SimBox(lengthx, lengthy, lengthz);
		int NAtom = 100;
		double[] x = new double[NAtom];
		double[] y = new double[NAtom];
		double[] z = new double[NAtom];
		double[] cart = new double[3 * NAtom];
		double[] mass = new double[NAtom];
		Random r = new Random();
		for(int i = 0; i < NAtom; i ++) {
			mass[i] = 1.0;
			x[i] = r.nextDouble() * sb.lengthx;
			y[i] = r.nextDouble() * sb.lengthy;
			z[i] = r.nextDouble() * sb.lengthz;
			cart[3 * i] = x[i];
			cart[3 * i + 1] = y[i];
			cart[3 * i + 2] = z[i];
		}
		Energy lj = new LJEnergy();
		try {
			mdmc.optimize(cart, lj);
		}
		catch (InputException e) {
			System.err.println("InputException: " + e.getMessage());
		}
		catch (OptException e) {
			System.err.println("OptException: " + e.getMessage());
		}
		return;
	}
};


class SimBox {
        public double lengthx;
        public double lengthy;
        public double lengthz;
        public SimBox (double lengthx, double lengthy, double lengthz) {
                this.lengthx = lengthx;
                this.lengthy = lengthy;
                this.lengthz = lengthz;
        }   
};

