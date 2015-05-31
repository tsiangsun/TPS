import java.util.*;
import tps.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.ColorCube;
import javax.vecmath.Color3f;

public class TEST {
	public static void main(String[] args) {
		MDMC mdmc = new MDMC();
		double lengthx, lengthy, lengthz;
		lengthx = 5.0;
		lengthy = 5.0;
		lengthz = 5.0;
		SimBox sb = new SimBox(lengthx, lengthy, lengthz);
		int NAtom = 12;
		double x, y, z;
		double[] cart = new double[3 * NAtom];
		Particle[] plist = new Particle[NAtom];
		float red, green, blue;
		red = 0; green = 0; blue = 0;
		Color3f color = new Color3f(red, green, blue);
		double mass = 1.0;
		double radius = 0.1;
		Random r = new Random();
		for(int i = 0; i < NAtom; i ++) {
			x = r.nextDouble() * sb.lengthx;
			y = r.nextDouble() * sb.lengthy;
			z = r.nextDouble() * sb.lengthz;
			Position pos = new Position(x, y, z);
			plist[i] = new Particle(mass, pos, radius, color);	
			cart[3 * i] = plist[i].pos.x;
			cart[3 * i + 1] = plist[i].pos.y;
			cart[3 * i + 2] = plist[i].pos.z;
		}
		Energy lj = new LJEnergy();
		try {
			Visualizer vis = new Visualizer();
			//vis.draw(plist);
			mdmc.optimize(cart, lj);
		//update the coordinates of plist
			for(int i = 0; i < NAtom; i ++) {
				plist[i].pos.x = cart[3 * i];
				plist[i].pos.y = cart[3 * i + 1];
				plist[i].pos.z = cart[3 * i + 2];
			}
			vis.draw(plist);
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

