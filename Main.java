import java.util.*;

public class Main
{
    public static Scanner input = new Scanner(System.in);
    static Turtle pen = new Turtle();

    static double[][] defaultAxis = {{1000,0},{0,1000},{-1000,0},{0,-1000}};



    static ArrayList<Body> simBodies =  new ArrayList<Body>();

    static Presets presets = new Presets();

    static double G = 6.67408 * Math.pow(10,-11);
    static double scalingFactor = 1;
    static double interval;
    static double tracer;


    public static void main(String[]args)
    {

        pen.shape("arrow");
        pen.fillColor("black");
        pen.shapeSize(10,10);
        pen.speed(0);
        pen.hide();

        drawAxes(1000, 10);
        boolean setup = true;


        while (setup)
        {
            System.out.println("press 1 to use a preset");
            System.out.println("press 2 to enter simulation parameters manually");
            int opt = input.nextInt();

            if (opt == 1)
            {
                System.out.println("1. two bodies of equal mass circling each other");
                System.out.println("2. two bodies of equal mass orbiting a third body with the same mass");
                System.out.println("3. figure 8");
                System.out.println("4. 345");
                System.out.println("5. three bodies of equal mass circling each other");

                int selected = input.nextInt();
                loadPreset(presets.presetList.get(selected-1));
                setup = false;
            }
            else if (opt == 2)
            {
                System.out.println("enter how may bodies you would like to simulate:");
                int numBodies = input.nextInt();
                System.out.println("enter the scaling factor of the simulation (1:x)");
                scalingFactor = input.nextDouble();

                for (int i = 0; i < numBodies; i++)
                {
                    Vec2[] vParams = getVecParams();
                    double[] sParams = getScalarParams();
                    Body tempBody = new Body(vParams[0], vParams[1], sParams[0], sParams[1], scalingFactor, simBodies);
                    System.out.println();
                }

                System.out.println("enter the universal interval of the simulation (delta T):");
                interval = input.nextDouble();
                System.out.println("enter per how many steps should the simulation render:");
                tracer = input.nextInt();
                setup = false;
            }
            else
            {
                System.out.println("invalid input. 1");
            }


        }

        System.out.println("simulating...");

        int n = 0;
        while (true) {
            boolean draw = n % tracer == 0;

            for (int i = 0; i < simBodies.size(); i++)
            {
                Body curBody = simBodies.get(i);
                curBody.forcePos = calcTotalForce(curBody);
            }

            for (int i = 0; i < simBodies.size(); i++)
            {

                Body curBody = simBodies.get(i);
                curBody.propPos(curBody.forcePos, interval, draw);
            }

            for(int i = 0; i < simBodies.size(); i++)
            {
                Body curBody = simBodies.get(i);
                curBody.forceVel = calcTotalForce(curBody);
                curBody.tempAcc = (curBody.forcePos.add(curBody.forceVel)).sMul(1 / (curBody.mass * 2));
                curBody.propVel(interval);
            }

            n++;
        }

    }


    static void drawAxes(int axisLen, int notchLen)
    {
        int heading = 0;
        double xPos;
        double yPos;
        for (int i = 0; i < 4; i++)
        {
            pen.setDirection(heading);
            for (int j = 0; j < 9; j++)
            {
                pen.forward(axisLen/10);
                xPos = pen.getX();
                yPos = pen.getY();

                pen.right(90);
                pen.forward(notchLen);

                pen.left(180);
                pen.forward(notchLen * 2);

                pen.setPosition(xPos, yPos);
                pen.setDirection(heading);
            }
            pen.forward(axisLen / 10);
            pen.stamp();

            pen.setPosition(0,0);
            heading += 90;
        }
    }

    static Vec2 calcGrav(Body i, Body j)
    {
        Vec2 vecDistance = j.pos.subs(i.pos);
        double distance = vecDistance.selfToPolar()[0];
        Vec2 force =  vecDistance.sMul((i.mass*j.mass) / Math.pow(distance, 3));
        return force;

    }

    static Vec2 calcTotalForce(Body A)
    {
        Vec2 totalForce = new Vec2(0,0);

        for (int i = 0; i < simBodies.size(); i++)
        {
            if (simBodies.get(i) != A)
            {
                totalForce = calcGrav(A, simBodies.get(i)).add(totalForce);
            }
        }

        return totalForce;
    }

    static Vec2[] getVecParams()
    {
        boolean velComplete = false;
        Vec2 pos;
        Vec2 vel = new Vec2(0,0);
        System.out.println("initializing body.");

        System.out.println("enter the body's initial x position:");
        double xPos = input.nextDouble();
        System.out.println("enter the body's initial y position:");
        double yPos = input.nextDouble();

        pos = new Vec2(xPos, yPos);

        while (!velComplete)
        {
            System.out.println("press 1 to enter the body's initial velocity as a quantity and angle,");
            System.out.println("press 2 to enter the body's initial velocity as x and y coordinates:");
            int opt = input.nextInt();

            if (opt == 1) {

                System.out.println("enter the length of the vector:");
                double len = input.nextDouble();
                System.out.println("enter the angle of the vector from the positive x direction:");
                double angle = input.nextDouble();

                vel = new Vec2(len, angle);
                vel.selfToCart();
                velComplete = true;
            }

            else if(opt == 2)
            {
                System.out.println("enter the body's initial velocity in the positive x direction:");
                double xVel  = input.nextDouble();
                System.out.println("enter the body's initial velocity in the positive y direction:");
                double yVel  = input.nextDouble();

                vel = new Vec2(xVel, yVel);
                velComplete = true;
            }

            else
            {
                System.out.println("invalid input 2");
            }
        }

        Vec2[] params = {pos, vel};
        return params;
    }

    static double[] getScalarParams()
    {
        double mass, radius;
        System.out.println("enter the mass of the body:");
        mass = input.nextDouble();
        System.out.println("enter the radius of the body:");
        radius= input.nextDouble();

        double[] params = {mass, radius};
        return params;
    }

    static void loadPreset(double[][] preset)
    {
        double xPos, yPos, xVel, yVel, mass, radius;
        Vec2 pos, vel;

        scalingFactor = preset[0][0];
        interval = preset[0][1];
        tracer = preset[0][2];
        for (int i = 1; i < preset.length; i++)
        {
            xPos = preset[i][0];
            yPos = preset[i][1];
            xVel = preset[i][2];
            yVel = preset[i][3];
            mass = preset[i][4];
            radius = preset[i][5];

            pos = new Vec2(xPos, yPos);
            vel = new Vec2(xVel, yVel);

            Body tempBody = new Body(pos, vel, mass, radius, scalingFactor, simBodies);
        }
    }

}
