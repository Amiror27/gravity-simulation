public class Vec2 {
    double x ;
    double y;

    Vec2(double xCoord, double yCoord)
    {
        this.x = xCoord;
        this.y = yCoord;
    }

    @Override public String toString()
    {
        return String.format("%s,%s",this.x,this.y);
    }

    Vec2 add(Vec2 V)
    {
        Vec2 res = new Vec2(this.x + V.x, this.y + V.y);
        return res;
    }

    Vec2 subs(Vec2 V)
    {
        Vec2 res = new Vec2(this.x - V.x, this.y - V.y);
        return res;
    }

    Vec2 inv()
    {
        Vec2 res = new Vec2(-1 * this.x, -1 * this.y);
        return res;
    }

    Vec2 sMul(double S)
    {
        Vec2 res = new Vec2(S * this.x, S * this.y);
        return res;
    }

    double[] selfToPolar()
    {
        double quant = Math.sqrt(this.x*this.x + this.y*this.y);
        double angle = Math.toDegrees(Math.atan2(this.y, this.x));
        double[] polar = {quant, angle};
        return polar;
    }

    double[] selfToCart()
    {
        this.x = this.x * Math.cos(this.y);
        this.y = this.x * Math.sin(this.y);
        double[] coords = {this.x, this.y};
        return coords;
    }

}
