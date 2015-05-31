package tps;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.jogamp.opengl.*;
import com.jogamp.nativewindow.*;
import java.util.*;
import com.sun.j3d.utils.behaviors.mouse.*;

public class Visualizer {
	public void draw(Particle[] plistoriginal) {
		int n = plistoriginal.length;	
		//make a deep copy of particle list
		//Particle[] plist = Arrays.copyOf(plistoriginal, n);	
		Particle[] plist = plistoriginal.clone();
		//find scaling factor to scale the particles in screen;
		double sf = 0.0;
		double centerx, centery, centerz, totmass, masscoordx, masscoordy, masscoordz;
		masscoordx = 0.0; masscoordy = 0.0; masscoordz = 0.0;
		totmass = 0.0;
		double bradius = 0.02;  //bond radius
		Appearance appBond = new Appearance();  //bond appearance
		//calculate center of mass of particle list
		double bondlength = 1.2;
		for(int i = 0; i < n; i ++) {
			masscoordx += plist[i].mass * plist[i].pos.x;
			masscoordy += plist[i].mass * plist[i].pos.y;
			masscoordz += plist[i].mass * plist[i].pos.z;	
			totmass += plist[i].mass;
		}
		centerx = masscoordx / totmass; centery = masscoordy / totmass; centerz = masscoordz / totmass;
		//center the particle list
		for(int i = 0; i < n; i ++) {
			plist[i].pos.x -= centerx;
			plist[i].pos.y -= centery;
			plist[i].pos.z -= centerz;
		}
		//calculate the scaling factor to scale particle list to fit the display window
		for(int i = 0; i < n; i ++) {
			sf = Math.max(sf, Math.abs(plist[i].pos.x));
			sf = Math.max(sf, Math.abs(plist[i].pos.y));
			sf = Math.max(sf, Math.abs(plist[i].pos.z));
		}
		//test whether sf equals 0, if not scale all coordinates by 2 * sf and radius by 2 * sf also;
		//center the cluster
		if (sf > 1e-6) {
			for(int i = 0; i < n; i ++) {
				plist[i].pos.x = plist[i].pos.x / (2.0 * sf);
				plist[i].pos.y = plist[i].pos.y / (2.0 * sf);
				plist[i].pos.z = plist[i].pos.z / (2.0 * sf);
				plist[i].radius = plist[i].radius / (2.0 * sf);
			}
			bradius /= (2.0 * sf);
			bondlength /= (2.0 * sf);
		}
								
		//calculate bond connectivity;
		ArrayList<Double[]> coordsBond = new ArrayList<Double[]> ();
		for(int i = 0; i < n; i ++) {
			for(int j = i + 1; j < n; j ++) {
				double dx, dy, dz, d;
				dx = plist[i].pos.x - plist[j].pos.x;
				dy = plist[i].pos.y - plist[j].pos.y;
				dz = plist[i].pos.z - plist[j].pos.z;
				d = Math.sqrt(dx * dx + dy * dy + dz * dz);
				if (d < bondlength) {
					Double[] coords = new Double[6];
					coords[0] = plist[i].pos.x;
					coords[1] = plist[i].pos.y;
					coords[2] = plist[i].pos.z;
					coords[3] = plist[j].pos.x;
					coords[4] = plist[j].pos.y;
					coords[5] = plist[j].pos.z;
					coordsBond.add(coords);
				}
			}
		}	

   		// Create the universe
   		SimpleUniverse universe = new SimpleUniverse();
   		// Create a structure to contain objects
   		BranchGroup group = new BranchGroup();
		// Create a molecular group 
		TransformGroup mol = new TransformGroup();
		mol.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		mol.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

   		// Create a ball and add it to the group of objects

		for(int i = 0; i < n; i ++) {
			Sphere sphere = new Sphere((float)plist[i].radius);
			TransformGroup tg = new TransformGroup();
			Transform3D transform = new Transform3D();
			Vector3f vector = new Vector3f((float)plist[i].pos.x, (float)plist[i].pos.y, (float)plist[i].pos.z);
			transform.setTranslation(vector);
			tg.setTransform(transform);
			tg.addChild(sphere);
			mol.addChild(tg);
//			group.addChild(tg);
		}	
		Bond bond = new Bond();
		TransformGroup bonds = bond.drawBonds(coordsBond, appBond, (float) bradius);
		mol.addChild(bonds);
		//add mouse rotation behavior
		BoundingSphere bound = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		MouseRotate behavior = new MouseRotate();
		behavior.setTransformGroup(mol);
		mol.addChild(behavior);
		behavior.setSchedulingBounds(bound);

		//add mol to branchgroup
		group.addChild(mol);
   		// Create a red light that shines for 100m from the origin
   		Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
   		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
   		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
   		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
   		light1.setInfluencingBounds(bounds);
   		group.addChild(light1);
   		// look towards the ball
   		universe.getViewingPlatform().setNominalViewingTransform();
   		// add the group of objects to the Universe
   		universe.addBranchGraph(group);

		return;
	}
};
