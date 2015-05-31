package tps;
import javax.vecmath.Color3f;

public class Particle {
        public double mass;
        public Position pos;
        public double radius;
        public Color3f color;
        public Particle(double mass, Position pos, double radius, Color3f color) {
                this.mass = mass;
                this.pos = pos;
                this.radius = radius;
                this.color = color;
        }   

	public Particle(Particle p) {
		mass = p.mass;
		pos.x = p.pos.x;
		pos.y = p.pos.y;
		pos.z = p.pos.z;
		radius = p.radius;
		color = p.color;
	}
};
