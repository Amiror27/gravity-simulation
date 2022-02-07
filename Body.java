import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Body
{
    Turtle pen  = new Turtle();

    Vec2 forcePos;
    Vec2 forceVel;

    Vec2 acc;
    Vec2 tempAcc;
    Vec2 vel;
    Vec2 pos;
    double mass;
    double scalingFactor;

    Body(Vec2 position, Vec2 velocity, double mass, double radius, double factor, ArrayList<Body> sim)
    {
        this.pos = position;
        this.mass = mass;
        this.vel = velocity;
        this.scalingFactor = factor;

        int R = (int)(Math.random()*256);
        int G = (int)(Math.random()*256);
        int B= (int)(Math.random()*256);
        Color color = new Color(R, G, B);

        pen.shape("circle");
        pen.shapeSize((int)radius,(int) radius);
        pen.penColor(color);
        pen.fillColor(color);

        pen.up();
        pen.setPosition(this.pos.x * scalingFactor, this.pos.y * scalingFactor);
        pen.down();

        this.addToSim(sim);

    }

    /**
     *
     * @param force
     * @param interval
     * @param draw
     */
    void propPos(Vec2 force, double interval, boolean draw)
    {
        this.acc = force.sMul(1 / this.mass);
        this.pos = this.acc.sMul(interval*interval).sMul(0.5).add(this.vel.sMul(interval).add(this.pos));
        if (draw)
        {
            pen.setPosition(this.pos.x * scalingFactor, this.pos.y * scalingFactor);
        }
    }

    void propVel(double interval)
    {
        this.vel = this.tempAcc.sMul(interval).add(this.vel);
    }

    void addToSim(ArrayList<Body> sim)
    {
        sim.add(this);
    }

}
