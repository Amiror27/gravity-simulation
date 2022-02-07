import java.util.ArrayList;

public class Presets
{
    ArrayList<double[][]> presetList = new ArrayList<>();
    Presets()
    {
        presetList.add(twoPlanetCircle);
        presetList.add(twoPlanetCircle2);
        presetList.add(figure8);
        presetList.add(threeFourFive);
        presetList.add(threeBodyCircle);
    }

    double a = Math.pow(3,-0.25);
    double b = 0.5*Math.sqrt(3);

    double[][] twoPlanetCircle = {{100, 0.001, 0.1}, {1,0,0,0.5,1,50}, {-1,0,0,-0.5,1,50}};
    double[][] twoPlanetCircle2 = {{100,0.1,1}, {0,0,0,0,1,10},{1,0,0,1.118034,1,10},{-1,0,0,-1.118034,1,10}};
    double[][] figure8 = {{100,0.1,200},{0.97000436,-0.24308753,0.466203685,0.43236573,1,50},
            {0.97000436,-0.24308753,-0.97000436,0.24308753,1,50},{0,0,-0.93240737,-0.86473146,1,50}};
    double[][] threeFourFive = {{100,0.00001,200000},{1,3,0,0,3,50},{-2,-1,0,0,4,50},{1,-1,0,0,5,50}};
    double[][] threeBodyCircle = {{100,0.001,2000},{0,1,a,0,1,25},
            {b,-0.5,-0.5*a,-a*b,1,25},{-b,-0.5,-0.5*a,a*b,1,25}};
}
